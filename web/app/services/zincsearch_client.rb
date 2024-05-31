require 'httparty'

class ZincsearchClient
  include HTTParty
  base_uri "http://zincsearch:4080" # ZincSearch 인스턴스 URL

  def initialize(auth_token)
    @auth_token = auth_token
  end

  def add_documents(index, documents)
    bulk_data = {
      index: index,
      records: documents
    }

    options = {
      body: bulk_data.to_json,
      headers: {
        'Content-Type' => 'application/json',
        'Authorization' => "Basic #{@auth_token}"
      }
    }

    response = self.class.post("/api/_bulkv2", options)

    if response.success?
      puts "Documents added successfully!"
    else
      puts "Error adding documents: #{response.body}"
    end
  end
end