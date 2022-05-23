import React from "react";
import { useModal } from "../hooks/useModal";
import Header from "../components/Header";
import Section from "../components/Section";
import Footer from "../components/Footer";
import Modal from "../components/Modal";

const DefaultLayout = ({ children }) => {
  const { isOpen, close } = useModal();
  return (
    <>
      <Header />
      {children}
      <Section />
      <Footer />
      <Modal isOpen={isOpen} close={close} />
    </>
  );
};

export default DefaultLayout;
