class GuestCleanupJob < ApplicationJob
  queue_as :default

  def perform(*guest_ids)
    guest_ids.each do |id|
      puts "Cleaning up guest with ID: #{id}"
    end
  end
end