import { Header } from "components";
import { SearchContainer } from "./search/components";
import { Suspense } from "react";

const SearchPage = () => {
  return (
    <>
      <Header>Companies</Header>
      <main className="flex flex-col items-center w-full px-4 sm:px-6 lg:px-8">
        <Suspense fallback={<></>}>
          <SearchContainer />
        </Suspense>
      </main>
    </>
  );
};

export default SearchPage;
