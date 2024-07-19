"use client";

import { PropsWithChildren, useState } from "react";

import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { ReactQueryDevtools } from "@tanstack/react-query-devtools";

export const ReactQueryProvider = ({ children }: PropsWithChildren) => {
  const [reactQueryClient] = useState(
    () =>
      new QueryClient({
        defaultOptions: {
          queries: {
            refetchIntervalInBackground: false,
          },
        },
      })
  );

  return (
    <QueryClientProvider client={reactQueryClient}>
      {children}
      <ReactQueryDevtools initialIsOpen={false} />
    </QueryClientProvider>
  );
};

export default ReactQueryProvider;
