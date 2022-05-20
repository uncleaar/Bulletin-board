import { useState, useCallback } from "react";

export const useModal = () => {
  const [isOpen, toggle] = useState(false);

  const open = () => toggle(true);

  const close = () => toggle(false);

  //useCallback(() => toggleState(!isOpen), []);

  return { isOpen, open, close };
};
