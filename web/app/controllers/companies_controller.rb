class CompaniesController < ApplicationController
  def index
    search_term = params[:search] || 'Limited'
    @companies = ApiCompanyClient.search_companies(search_term)
    if @companies.empty?
      flash[:error] = "Error: Unable to retrieve companies"
    end
  end

  def new
    @company = Company.new
  end

  def create
    @company = Company.new(company_param)
    if @company.save
      redirect_to companies_path
    else
      render :new
    end
  end

  private

  def company_param
    params.require(:company).permit(:name, :city, :county, :route)
  end
end