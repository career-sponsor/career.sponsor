import { Header } from "components";

import { SearchInput, SearchResultTable } from "./search/components";

export default function SearchPage() {
  return (
    <>
      <Header>Companies</Header>
      <main className="flex flex-col items-center w-full px-4 sm:px-6 lg:px-8">
        <div className="w-full max-w-6xl mt-8">
          <h2 className="text-2xl font-semibold text-gray-800 mb-6">
            Search for a Company
          </h2>
          <SearchInput />
          <SearchResultTable />
        </div>
      </main>
    </>
  );
}
