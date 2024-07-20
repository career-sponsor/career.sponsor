import type { Metadata } from "next";
import { Inter } from "next/font/google";
import "lib/styles/globals.css";
import { GoogleTagManager } from "@next/third-parties/google";
import { GTM_ID } from "lib/env";
import { ReactQueryProvider } from "components";
import { MSWComponent } from "mocks/msw-component";

const inter = Inter({ subsets: ["latin"] });

export const metadata: Metadata = {
  title: "Visa",
  description: "Visa",
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en">
      <GoogleTagManager gtmId={GTM_ID} />
      <ReactQueryProvider>
        <MSWComponent>
          <body className={inter.className}>{children}</body>
        </MSWComponent>
      </ReactQueryProvider>
    </html>
  );
}
