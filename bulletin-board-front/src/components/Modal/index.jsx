import React from "react";
import { ReactComponent as Mark } from "../../assets/img/mark.svg";
import styles from "./index.module.css";

const Modal = ({ children, isOpen, close }) => {
  return (
    <>
      {isOpen && (
        <div className={styles.modal}>
          <div className={styles.icon} onClick={() => close()}>
            <Mark />
          </div>
          {children}
        </div>
      )}
    </>
  );
};

export default Modal;
