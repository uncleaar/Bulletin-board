import React, { useState, createContext } from "react";

export const ModalContext = createContext({
  isOpen: false,
  open: () => {},
  close: () => {},
});

export const ModalProvider = ({ children }) => {
  const [isOpen, toggle] = useState(false);

  const open = () => toggle(true);

  const close = () => toggle(false);

  return (
    <ModalContext.Provider value={{ isOpen, open, close }}>
      {children}
    </ModalContext.Provider>
  );
};
