import json
import uuid
from io import StringIO

from airflow import DAG
from datetime import datetime, timedelta
from kubernetes import client, config

from airflow.operators.python import PythonOperator
import requests
from bs4 import BeautifulSoup
import os

import boto3
import time
import pandas as pd

from dotenv import load_dotenv

# .env 파일 로드
load_dotenv()

# 환경 변수 가져오기
AWS_ACCESS_KEY = os.getenv('AWS_ACCESS_KEY')
AWS_SECRET_KEY = os.getenv('AWS_SECRET_KEY')
REGION_NAME = os.getenv('REGION_NAME')

BUCKET_NAME = 'uk-job'
OBJECT_NAME = 'csv/companies.csv'
FILE_NAME = 'asset/companies.csv'
OUTPUT_PATH = 'athena'
JSON_PATH = 'json'

S3_LOCATION = f's3://{BUCKET_NAME}'
S3_OUTPUT_LOCATION = f'{S3_LOCATION}/{OUTPUT_PATH}'

CRAWLER_NAME = 'visa-sponsor-company-crawler'
DATABASE_NAME = 'visa-sponsor-companies'
TABLE_NAME = 'csv'

# Define the callback function
def on_failure(context):
    print("Task failed")

def download_file():
    # 웹사이트의 URL
    url = 'https://www.gov.uk/government/publications/register-of-licensed-sponsors-workers'
    # 요청을 보내고 HTML을 받아옴
    response = requests.get(url)
    soup = BeautifulSoup(response.text, 'html.parser')

    # 다운로드할 파일 링크 찾기
    link = soup.find('a', {'class': 'govuk-link gem-c-attachment__link'})['href']
    file_url = link

    directory = './asset'
    if not os.path.exists(directory):
        os.makedirs(directory)

    # 파일 다운로드 및 저장
    file_response = requests.get(file_url)
    with open(f'{directory}/companies.csv', 'wb') as f:
        f.write(file_response.content)
    print("File downloaded successfully")

def upload_to_s3():
    # Boto3 클라이언트 설정
    s3_client = boto3.client('s3',
        aws_access_key_id=AWS_ACCESS_KEY,
        aws_secret_access_key=AWS_SECRET_KEY,
        region_name=REGION_NAME
    )

    # 업로드할 파일 경로와 S3 버킷 및 파일 이름 지정
    s3_client.put_object(
        Bucket=BUCKET_NAME,
        Key=OBJECT_NAME,
        Body=open(FILE_NAME, 'rb'))

    print("File uploaded to S3 successfully")

def start_glue_crawler():
    glue_client = boto3.client('glue',
       aws_access_key_id=AWS_ACCESS_KEY,
       aws_secret_access_key=AWS_SECRET_KEY,
       region_name=REGION_NAME
    )

    try:
        response = glue_client.start_crawler(Name=CRAWLER_NAME)
        print("크롤러가 성공적으로 실행되었습니다.")
        print(response)
    except Exception as e:
        print("크롤러 실행 중 오류가 발생했습니다:", e)

    # 크롤러의 상태를 확인하여 완료될 때까지 대기
    while True:
        crawler_status = glue_client.get_crawler(Name=CRAWLER_NAME)
        status = crawler_status['Crawler']['State']
        if status == 'READY':
            print("크롤러 작업이 완료되었습니다.")
            break
        else:
            print("크롤러 작업 중... 상태:", status)
            time.sleep(10)  # 60초 간격으로 상태 확인

def query_athena_and_fetch_data():
    # Athena 설정
    athena_client = boto3.client('athena',
         aws_access_key_id=AWS_ACCESS_KEY,
         aws_secret_access_key=AWS_SECRET_KEY,
         region_name=REGION_NAME
    )
    # SQL 쿼리
    query_string = f"""
        SELECT DISTINCT
            col0 as name,
            col1 as city,
            col2 as county,
            col3 as type,
            col4 as route
        FROM {TABLE_NAME} 
        ;
    """

    # 쿼리 실행
    response = athena_client.start_query_execution(
        QueryString=query_string,
        QueryExecutionContext={'Database': (DATABASE_NAME)},
        ResultConfiguration={'OutputLocation': (S3_OUTPUT_LOCATION)}
    )
    query_execution_id = response['QueryExecutionId']
    print(f"---> 쿼리 완료: {query_execution_id}")

    # 쿼리 완료 대기
    while True:
        query_status = athena_client.get_query_execution(QueryExecutionId=query_execution_id)
        print(f"---> output : {query_status}")
        query_execution_status = query_status['QueryExecution']['Status']['State']
        if query_execution_status in ['SUCCEEDED', 'FAILED', 'CANCELLED']:
            break
        time.sleep(5)

    print(f"---> 쿼리 완료: {query_execution_status}")
    if query_execution_status == 'SUCCEEDED':
        # 결과 파일 위치
        result_location = S3_OUTPUT_LOCATION + "/" + query_execution_id + '.csv'

        s3_client = boto3.client('s3',
                                 aws_access_key_id=AWS_ACCESS_KEY,
                                 aws_secret_access_key=AWS_SECRET_KEY,
                                 region_name=REGION_NAME)

        response = s3_client.get_object(
            Bucket=BUCKET_NAME,
            Key=OUTPUT_PATH+ "/" + query_execution_id + ".csv")

        print(f"--->response: {response}")
        data = response['Body'].read().decode('utf-8')
        df = pd.read_csv(StringIO(data))

        json_data = df.to_json(orient='records')

        print(f"--->json: {json_data}")
        print(f"--->size={len(json_data)}")

        current_date = datetime.now()
        formatted_date = current_date.strftime('%Y%m%d')

        s3_client.put_object(
            Bucket=BUCKET_NAME,
            Key=f"{JSON_PATH}/{formatted_date}.json",
            Body=json.dumps(json_data))

        print("--->저장 완료")


    # 결과 데이터 로드
        # data = pd.read_csv(result_location)
        # print(f"---> data : {data}")
        #
        # # 데이터를 dict 형태로 변환
        # data_dicts = data.to_dict(orient='records')
        # print(f"---> dicts: {data_dicts}")
    else:
        print("쿼리 실패:", query_status['QueryExecution']['Status']['StateChangeReason'])

def create_cronjob():
    print("----> start")
    # IMPORTANT! config 경로를 지정 안하면 기본 경로 (~/.kube/config를 바라본다)
    KUBE_CONFIG_PATH = "/.kube/config"
    config.load_kube_config(KUBE_CONFIG_PATH)
    print("----> load config")

    batch_v1 = client.BatchV1Api()  # BatchV1beta1Api 대신 BatchV1Api 사용
    print("----> create api")

    cronjob = client.V1CronJob(     # client.V1beta1CronJob 대신 client.V1CronJob 사용
        api_version="batch/v1",     # API 버전 변경
        kind="CronJob",
        metadata=client.V1ObjectMeta(name=f"zincsearch-{uuid.uuid4()}"), # uuid 추가 안하면 이미 존재하여 conflict 발생
        spec=client.V1CronJobSpec(  # V1beta1CronJobSpec 대신 V1CronJobSpec 사용
            schedule="*/5 * * * *",
            job_template=client.V1JobTemplateSpec(
                spec=client.V1JobSpec(
                    template=client.V1PodTemplateSpec(
                        spec=client.V1PodSpec(
                            containers=[client.V1Container(
                                name="rails-zincsearch",
                                image="bgpark82/company-visa-web:latest",
                                image_pull_policy="Always",
                                command=["bundle", "exec", "rake", "zincsearch:add_documents"]
                            )],
                            restart_policy="OnFailure"
                        )
                    )
                )
            )
        )
    )

    print("----> create cronjob")
    batch_v1.create_namespaced_cron_job(
        body=cronjob,
        namespace="default")  # create_namespaced_cron_job 메소드는 사용 가능
    print("----> cronjob created!")

dag = DAG(
    'create_kubernetes_cronjob',
    description='Create Kubernetes CronJob',
    schedule_interval='@daily',
    start_date=datetime(2023, 1, 1),
    catchup=False)

create_job = PythonOperator(
    task_id='create_kubernetes_cronjob_task',
    python_callable=create_cronjob,
    execution_timeout=timedelta(minutes=1), # 1분 후 타임아웃
    on_failure_callback=on_failure, # 타임 아웃 시 실행하는 콜백
    dag=dag)

# download_task = PythonOperator(
#     task_id='download_file',
#     python_callable=download_file,
#     dag=dag,
# )
#
# upload_to_s3_task = PythonOperator(
#     task_id='upload_to_s3',
#     python_callable=upload_to_s3,
#     dag=dag,
# )
#
# run_glue_crawler = PythonOperator(
#     task_id='run_glue_crawler',
#     python_callable=start_glue_crawler,
#     dag=dag
# )
#
# query_and_convert_data_task = PythonOperator(
#     task_id='query_athena_and_convert_to_dict',
#     python_callable=query_athena_and_fetch_data,
#     dag=dag
# )
#
# download_task >> upload_to_s3_task >> run_glue_crawler >> query_and_convert_data_task
