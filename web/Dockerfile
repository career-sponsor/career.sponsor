# 베이스 이미지 설정 (여기서는 Ruby 2.7을 사용)
FROM ruby:2.7.7

# 필요한 패키지 설치
RUN apt-get update -qq && apt-get install -y nodejs yarn

# 작업 디렉토리 설정
WORKDIR /myapp

# Gemfile 및 Gemfile.lock 복사
COPY Gemfile /myapp/Gemfile
COPY Gemfile.lock /myapp/Gemfile.lock

# 번들 설치
RUN bundle install

# 애플리케이션 코드 복사
COPY . /myapp

# 마이그레이션
RUN bin/rails db:migrate

# 포트 설정
EXPOSE 3000

# 서버 실행 명령
CMD ["rails", "server", "-b", "0.0.0.0"]
