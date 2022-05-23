import React from "react";
import styles from "./index.module.css";

const Button = ({ title, onClick, type }) => {
  return (
    <button className={styles.button} onClick={onClick} type={type}>
      {title}
    </button>
  );
};

export default Button;
