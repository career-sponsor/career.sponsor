import { Header } from "components";
import { SearchContainer } from "./search/components";

const SearchPage = () => {
  return (
    <>
      <Header>Companies</Header>
      <main className="flex flex-col items-center w-full px-4 sm:px-6 lg:px-8">
        <SearchContainer />
      </main>
    </>
  );
};

export default SearchPage;
