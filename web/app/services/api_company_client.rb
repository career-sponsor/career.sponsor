class ApiCompanyClient
  include HTTParty
  base_uri 'http://zincsearch:4080/'

  def self.search_companies(term, from=0, max_results=20)
    response = post('/api/companies/_search',
                    headers: {
                      'Content-Type' => 'application/json',
                      'Authorization' => 'Basic YWRtaW46Q29tcGxleHBhc3MjMTIz'
                    },
                    body: {
                      search_type: 'match',
                      query: {
                        term: term,
                        field: '_all'
                      },
                      from: from,
                      max_results: max_results,
                      _source: []
                    }.to_json
    )

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