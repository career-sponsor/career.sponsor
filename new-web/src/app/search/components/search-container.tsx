"use client";
import { createQueryString } from "lib/utils";
import SearchInput from "./search-input";
import SearchResultTable from "./search-result-table";
import { usePathname, useRouter, useSearchParams } from "next/navigation";
import { useDebouncedCallback } from "@react-hookz/web";

interface SearchResults {
  content: Content[];
  pageable: Pageable;
}

interface Pageable {
  size: number;
  pageNumber: number;
  offset: number;
}

interface Content {
  name: string;
  city: string;
  county: string;
  type: string;
  route: string;
}

const mockData = {
  content: [
    {
      name: "ABC company",
      city: "London",
      county: "London",
      type: "Worker",
      route: "Skilled Worker",
    },
    {
      name: "ABC company",
      city: "London",
      county: "London",
      type: "Worker",
      route: "Skilled Worker",
    },
    {
      name: "ABC company",
      city: "London",
      county: "London",
      type: "Worker",
      route: "Skilled Worker",
    },
    {
      name: "ABC company",
      city: "London",
      county: "London",
      type: "Worker",
      route: "Skilled Worker",
    },
  ],
  pageable: {
    size: 10,
    pageNumber: 0,
    offset: 0,
  },
};

const SearchContainer = () => {
  const router = useRouter();
  const pathname = usePathname();
  const searchParams = useSearchParams();

  const keyword = (searchParams.get("keyword") as string) ?? "";

  const changeKeyword = (value: string) => {
    const keywordQueryString = createQueryString({
      searchParams,
      name: "keyword",
      value,
    });
    router.replace(`${pathname}?${keywordQueryString}`, {
      scroll: true,
    });
  };

  const onChangeKeyword = useDebouncedCallback(
    changeKeyword,
    [changeKeyword],
    500
  );

  const onSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    const formData = new FormData(e.currentTarget);
    const value = formData.get("search-input") as string;
    changeKeyword(value);
  };

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
      <SearchResultTable searchResults={mockData} />
    </div>
  );
};

export default SearchContainer;
