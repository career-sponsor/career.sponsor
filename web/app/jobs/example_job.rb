class ExampleJob < ApplicationJob
  queue_as :default

  def perform(*args)
    # 여기에 작업 로직 추가
    puts "Hello from ExampleJob!"
  end
end