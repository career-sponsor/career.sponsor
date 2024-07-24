"use client";

import { useSearchCompany } from "../services";
import SearchInput from "./search-input";
import SearchResultTable from "./search-result-table";

const SearchContainer = () => {
  const { data, keyword, onChangeKeyword, onSubmit } = useSearchCompany();
  if (!data) {
    return null;
  }
  console.log(data);

  return (
    <div className="w-full max-w-6xl mt-8">
      <h2 className="text-2xl font-semibold text-gray-800 mb-6">
        Search for a Company
      </h2>
      <SearchInput
        value={keyword}
        onSubmit={onSubmit}
        onChange={onChangeKeyword}
      />
      <SearchResultTable searchResults={data} />
    </div>
  );
};

export default SearchContainer;
