import {
  Table,
  TableBody,
  TableCaption,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "components/table";

interface Props {
  searchResults: any;
}

const SearchResultTable = ({ searchResults }: Props) => {
  return (
    <Table className="min-w-full bg-white">
      <TableCaption>A list of companies</TableCaption>
      <TableHeader>
        <TableRow>
          <TableHead className="w-[100px]">Name</TableHead>
          <TableHead>City</TableHead>
          <TableHead>Country</TableHead>
          <TableHead>Type</TableHead>
          <TableHead className="text-right">Route</TableHead>
        </TableRow>
      </TableHeader>
      <TableBody>
        {searchResults.content.map((result: any) => (
          <TableRow key={result.name}>
            <TableCell className="font-medium">{result.name}</TableCell>
            <TableCell>{result.city}</TableCell>
            <TableCell>{result.county}</TableCell>
            <TableCell>{result.type}</TableCell>
            <TableCell className="text-right">{result.route}</TableCell>
          </TableRow>
        ))}
      </TableBody>
    </Table>
  );
};

export default SearchResultTable;
