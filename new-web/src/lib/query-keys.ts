export const queryKeys = {
  COMPANIES: "companies" as const,
};

export const getQueryKeys = {
  companies: () => [queryKeys.COMPANIES] as const,
};
