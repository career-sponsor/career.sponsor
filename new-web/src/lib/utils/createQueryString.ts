import { ReadonlyURLSearchParams } from "next/navigation";

interface Params {
  searchParams: ReadonlyURLSearchParams;
  name: string;
  value: string;
}

export const createQueryString = ({
  searchParams: currentParams,
  name,
  value,
}: Params): string => {
  const params = new URLSearchParams(currentParams.toString());
  params.set(name, value);
  return params.toString();
};
