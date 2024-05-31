namespace :zincsearch do
  desc "Add documents to ZincSearch"
  task add_documents: :environment do
    # 예시 데이터
    documents = [
      {
        name: "Zalando",
        city: "Berlin",
        county: "Germany",
        type: "Worker (A rating)",
        route: "Skilled Worker"
      },
    ]

    # ZincsearchClient 인스턴스 생성
    auth_token = "YWRtaW46Q29tcGxleHBhc3MjMTIz" # Base64로 인코딩된 인증 토큰
    client = ZincsearchClient.new(auth_token)

    # 인덱스와 문서 추가
    client.add_documents("companies", documents)

    puts "Batch process completed"
  end
end