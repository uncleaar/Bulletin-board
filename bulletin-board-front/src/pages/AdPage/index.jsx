import React, { useState, useCallback, useEffect } from "react";
import { getAd, getPhoto, getLocality } from "../../requests/ad";
import DefaultLayout from "../../layouts/DefaultLayout";
import styles from "./index.module.css";

const AdPage = (id) => {
  const [ad, setAd] = useState();
  const [photo, setPhoto] = useState();
  const [locality, setLocality] = useState();

  const fetchAd = useCallback(async () => {
    const { data } = await getAd(id);
    setAd(data);
  }, [id]);

  const fetchPhoto = useCallback(async () => {
    const { data } = await getPhoto(id);
    setPhoto(data);
  }, [id]);

  const fetchLocality = useCallback(async () => {
    const { data } = await getLocality(id);
    setLocality(data);
  }, [id]);

  useEffect(() => {
    fetchAd();
  }, [fetchAd]);

  useEffect(() => {
    fetchPhoto();
  }, [fetchPhoto]);

  useEffect(() => {
    fetchLocality();
  }, [fetchLocality]);

  return (
    <DefaultLayout>
      <div className="container">
        <div className={styles.image}>
          <img src={photo} />
        </div>
        <div className={styles.info}>
          <div className={styles.name}>{ad.name}</div>
          <div className={styles.price}>{ad.price} руб.</div>
          <div className={styles.locality}>{locality}</div>
          <div className={styles.date}>{ad.date}</div>
        </div>
        <div className={styles.contacts}>
          <div className={styles.firstName}></div>
          <div className={styles.email}></div>
          <div className={styles.number}></div>
        </div>
        <div className={styles.buttons}>
          <div className={styles.edit}>Редактировать</div>
          <div className={styles.delete}>Удалить</div>
        </div>
      </div>
    </DefaultLayout>
  );
};

export default AdPage;
