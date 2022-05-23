import React from "react";
import { ModalProvider } from "../../context/ModalContext";
import DefaultLayout from "../../layouts/DefaultLayout";

const HomePage = () => {
  return (
    <ModalProvider>
      <DefaultLayout>
        <div>HomePage</div>
      </DefaultLayout>
    </ModalProvider>
  );
};

export default HomePage;
