import { Input } from "components";

import { SearchIcon } from "lucide-react";

const SearchInput = () => {
  return (
    <div className="relative mb-10 flex items-center w-full sm:w-1/2 md:w-1/3 lg:w-1/4">
      <label htmlFor="company-search" className="sr-only">
        Company Name
      </label>
      <Input id="company-search" placeholder="Company Name" />
      <button className="absolute inset-y-0 right-0 pr-3 flex items-center ">
        <SearchIcon className="h-5 w-5 text-gray-400" />
      </button>
    </div>
  );
};
export default SearchInput;
