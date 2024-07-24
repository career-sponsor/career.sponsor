import {
  QueryFunction,
  QueryKey,
  useSuspenseQuery,
} from "@tanstack/react-query";
import { getQueryKeys } from "lib";
import {
  PostCompanyRequest,
  PostCompanyResponse,
  postCompanies,
} from "lib/apis/companies";

interface SuspenseQueryOptions<TQueryFnData = unknown> {
  queryKey: QueryKey;
  queryFn: QueryFunction<TQueryFnData, QueryKey, never>;
}

const postCompanyRequest: PostCompanyRequest = {
  query: {
    name: "foo",
  },
  filter: {
    city: "London",
  },
  sort: {
    name: "asc",
  },
  pagination: {
    from: 0,
    size: 10,
  },
};

export const companyQueryOptions = (
  CompanySearchRequest: PostCompanyRequest
): SuspenseQueryOptions<PostCompanyResponse> => ({
  queryKey: getQueryKeys.companies(),
  queryFn: () => postCompanies(CompanySearchRequest),
});

export default function usePostCompany() {
  return useSuspenseQuery(companyQueryOptions(postCompanyRequest));
}
