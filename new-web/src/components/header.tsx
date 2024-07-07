import Link from "next/link";
import { PropsWithChildren } from "react";

function Header({ children }: PropsWithChildren) {
  return (
    <header className=" w-full py-4 border-b border-gray-100">
      <div className="container mx-auto px-6 text-2xl font-bold text-gray-800">
        <Link href="/">{children}</Link>
      </div>
    </header>
  );
}

export default Header;
