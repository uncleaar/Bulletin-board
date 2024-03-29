import React from "react";
import { useModal } from "../../hooks/useModal";
import { ReactComponent as Logo } from "../../assets/img/logo.svg";
import { useAuth } from "../../hooks/useAuth";
import styles from "./index.module.css";

const Header = () => {
  const { open } = useModal();

  const { isAuth, user } = useAuth();

  const handleCreateAd = () => {
    if (isAuth) console.log("валим ребята)");
    else open();
  };

  return (
    <header>
      <div className="container">
        <div className={styles.header__wrap}>
          <div className={styles.header__wrap__left}>
            <Logo />
            <a href="index.html" className={styles.header__wrap__title}>
              Bulletin Board
            </a>
            <nav className={styles.header__wrap__nav}>
              <a href="" className={styles.header__wrap__left__category}>
                Авто
              </a>
              <a href="" className={styles.header__wrap__left__category}>
                Недвижимость
              </a>
              <a href="" className={styles.header__wrap__left__category}>
                Работа
              </a>
            </nav>
          </div>
          <div className={styles.header__wrap__right}>
            <div className={styles.header__wrap__right__buttons}>
              {!isAuth ? (
                <div
                  className={styles.header__wrap__right__buttons__registration}
                  onClick={() => open()}
                >
                  Вход и регистрация
                </div>
              ) : (
                <div
                  className={styles.header__wrap__right__buttons__registration}
                >
                  {user?.name}
                </div>
              )}
              <div
                className={styles.header__wrap__right__buttons__ad}
                onClick={handleCreateAd}
              >
                Подать объявление
              </div>
            </div>
          </div>
        </div>
        <div className={styles.header__search}>
          <select
            name=""
            id=""
            className={styles.header__search__select__category}
          >
            <option selected value>
              Любая категория
            </option>
            <option value="1">Авто</option>
            <option value="2">Недвижимость</option>
            <option value="3">Личные вещи</option>
          </select>
          <input
            type="text"
            className={styles.header__search__input}
            placeholder="Поиск по объявлениям"
          />
          <select name="" id="" className={styles.header__search__select__city}>
            <option selected value>
              Любой город
            </option>
            <option value="1">Барнаул</option>
            <option value="2">Москва</option>
            <option value="3">Новосибирск</option>
          </select>
          <button className={styles.header__search__button}>Найти</button>
        </div>
      </div>
    </header>
  );
};

export default Header;
