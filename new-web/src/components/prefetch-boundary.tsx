import {
  QueryKey,
  HydrationBoundary,
  dehydrate,
  QueryFunction,
} from "@tanstack/react-query";
import { getQueryClient } from "lib";

interface PrefetchOptions {
  queryKey: QueryKey;
  queryFn: QueryFunction;
  options?: {
    staleTime?: number;
    cacheTime?: number;
    retry?:
      | boolean
      | number
      | ((failureCount: number, error: unknown) => boolean);
    retryDelay?: number | ((failureCount: number, error: unknown) => number);
  };
}

interface Props {
  prefetchOptions: PrefetchOptions[] | PrefetchOptions;
  children: React.ReactNode;
}

export async function PrefetchBoundary({ prefetchOptions, children }: Props) {
  const queryClient = getQueryClient();

  /**
   * @TODO suspensive 라이브러리의 병렬적으로 실행하는 supenseQueris와 비슷한 것 같다.
   */
  Array.isArray(prefetchOptions)
    ? await Promise.all(
        prefetchOptions.map((prefetchOption) =>
          queryClient.prefetchQuery(prefetchOption)
        )
      )
    : await queryClient.prefetchQuery(prefetchOptions);

  return (
    <HydrationBoundary state={dehydrate(queryClient)}>
      {children}
    </HydrationBoundary>
  );
}
