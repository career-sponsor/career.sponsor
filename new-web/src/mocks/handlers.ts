import { http, HttpResponse } from "msw";
import { mockData } from "./mock-data";
import { PostCompanyRequest } from "lib/apis/companies";

export const handlers = [
  http.post<never, PostCompanyRequest>(
    "/companies/search",
    async ({ request }) => {
      const { query, filter, sort, pagination } = await request.json();

      const mockResponse: any = {
        content: mockData,
        pageable: {
          size: pagination.size,
          pageNumber: Math.floor(pagination.from / pagination.size),
          offset: pagination.from,
        },
      };

      // JSON 응답 반환
      return HttpResponse.json(mockResponse, { status: 200 });
    }
  ),
];
