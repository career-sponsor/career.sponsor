import {
  Table,
  TableBody,
  TableCaption,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "components/table";

const SearchResultTable = () => {
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
        <TableRow>
          <TableCell className="font-medium">ABC company</TableCell>
          <TableCell>London</TableCell>
          <TableCell>London</TableCell>
          <TableCell>Worker</TableCell>
          <TableCell className="text-right">Skilled Worker</TableCell>
        </TableRow>
      </TableBody>
    </Table>
  );
};

export default SearchResultTable;
