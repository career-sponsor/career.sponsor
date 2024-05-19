class ApiClient
  include HTTParty
  base_uri 'https://jsonplaceholder.typicode.com'

  def self.get_posts
    get('/posts')
  end
end