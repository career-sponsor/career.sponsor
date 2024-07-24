import { httpFetch } from "../http/http-fetch";

export interface CompanyQuery {
  name: string;
}

export interface CompanyFilter {
  city: string;
}

export interface CompanySort {
  name: "asc" | "desc";
}

export interface CompanyPagination {
  from: number;
  size: number;
}

export interface PostCompanyRequest {
  query: CompanyQuery;
  filter: CompanyFilter;
  sort: CompanySort;
  pagination: CompanyPagination;
}

interface CompanyInfo {
  name: string;
  city: string;
  county: string;
  type: string;
  route: string;
}

export interface PostCompanyResponse {
  content: CompanyInfo[];
  pageable: {
    size: number;
    pageNumber: number;
    offset: number;
  };
}

export const postCompanies = async ({
  query,
  filter,
  sort,
  pagination,
}: PostCompanyRequest) => {
  const response = await httpFetch(`/companies/search `, {
    method: "POST",
    body: JSON.stringify({
      query,
      filter,
      sort,
      pagination,
    }),
  });
  const data = await response.json();
  return data as PostCompanyResponse;
};
