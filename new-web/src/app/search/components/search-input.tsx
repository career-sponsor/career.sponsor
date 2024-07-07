import { Input } from "components";

import { SearchIcon } from "lucide-react";

interface Props {
  value: string;
  onChange: (value: string) => void;
  onSubmit: (value: any) => void;
}

const SearchInput = ({ value, onSubmit, onChange }: Props) => {
  return (
    <form onSubmit={onSubmit}>
      <div className="relative mb-10 flex items-center w-full sm:w-1/2 md:w-1/3 lg:w-1/4">
        <Input
          id="company-search"
          name="search-input"
          placeholder="Company Name"
          defaultValue={value}
          onChange={(e) => onChange(e.target.value)}
        />
        <button className="absolute inset-y-0 right-0 pr-3 flex items-center">
          <SearchIcon className="h-5 w-5 text-gray-400" />
        </button>
      </div>
    </form>
  );
};
export default SearchInput;
