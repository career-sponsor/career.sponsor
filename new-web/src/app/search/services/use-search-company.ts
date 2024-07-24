"use client";

import { usePathname, useRouter, useSearchParams } from "next/navigation";
import { useDebouncedCallback } from "@react-hookz/web";
import { createQueryString } from "lib/utils";
import { usePostCompany } from "../hooks";

export default function useSearchCompany() {
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

  const { data } = usePostCompany();

  return {
    keyword,
    onChangeKeyword,
    onSubmit,
    data,
  };
}
