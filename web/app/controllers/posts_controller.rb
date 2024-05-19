class PostsController < ApplicationController
  def index
    @posts = ApiClient.get_posts.parsed_response
  end
end
