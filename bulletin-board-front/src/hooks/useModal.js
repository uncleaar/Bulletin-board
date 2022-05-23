import { useContext } from "react";
import { ModalContext } from "../context/ModalContext";

export const useModal = () => {
  const { isOpen, open, close } = useContext(ModalContext);

  return { isOpen, open, close };
};
