import React from "react";
import styles from "./index.module.css";

const Card = ({ image, name, price, locality, date, onClick }) => {
  return (
    <div className={styles.card}>
      <div className={styles.image}>
        <img src={image} />
      </div>
      <div className={styles.info}>
        <div className={styles.name}>{name}</div>
        <div className={styles.price}>{price} руб.</div>
        <div className={styles.locality}>{locality}</div>
        <div className={styles.date}>{date}</div>
      </div>
    </div>
  );
};

export default Card;
