class ApiCompanyClient
  include HTTParty
  base_uri 'http://elasticsearch:9200'

  def self.search_companies(term, from=0, max_results=20)
    response = post('/companies/_search',
                    headers: {
                      'Content-Type' => 'application/json',
                    },
                    body: {
                      query: {
                        bool: {
                          must: [
                            {
                              match: {
                                name: term
                              }
                            }
                          ]
                        }
                      },
                      from: 0,
                      size: 10
                    }.to_json
    )
    puts "-----> 응답"
    puts response

    if response.success?
      response.parsed_response['hits']['hits'].map do |hit|
        Company.new(
          name: hit['_source']['name'],
          city: hit['_source']['city'],
          county: hit['_source']['county'],
          route: hit['_source']['route']
        )
      end
    else
      []
    end
  end
end