namespace :cleanup do
  desc "Trigger guest cleanup job"
  task clean_temp: :environment do
    # Rails 환경 로드 후 Job 호출
    puts "Starting Guest Cleanup Job..."
    GuestCleanupJob.perform_now([1, 2, 3])  # 예시로 1, 2, 3 ID의 게스트를 정리
    puts "Guest Cleanup Job enqueued."
  end
end