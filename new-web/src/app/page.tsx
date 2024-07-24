import { Header } from "components";
import { Suspense } from "react";
import { SearchContainer } from "./search/components";
import { PrefetchBoundary } from "components/prefetch-boundary";
import { companyQueryOptions } from "./search/hooks/use-post-company";

const SearchPage = () => {
  return (
    <>
      <Header>Companies</Header>
      <main className="flex flex-col items-center w-full px-4 sm:px-6 lg:px-8">
        <Suspense fallback={null}>
          <PrefetchBoundary
            prefetchOptions={companyQueryOptions({
              query: {
                name: "",
              },
              filter: {
                city: "",
              },
              sort: {
                name: "asc",
              },
              pagination: {
                from: 0,
                size: 10,
              },
            })}
          >
            <SearchContainer />
          </PrefetchBoundary>
        </Suspense>
      </main>
    </>
  );
};

export default SearchPage;
