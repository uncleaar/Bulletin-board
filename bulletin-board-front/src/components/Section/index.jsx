import React from "react";
import { ReactComponent as Logo } from "../../assets/img/logo.svg";
import styles from "./index.module.css";

const Section = () => {
  return (
    <section>
      <div class="container">
        <div class={styles.section__wrap__info}>
          <div class={styles.section__wrap__about__logo}>
            <Logo />
            <h3 class={styles.section__wrap__about__title}>Bulletin Board</h3>
          </div>
          <div class={styles.section__wrap__about__text}>
            <p class={styles.about__text}>Bulletin Board - сайт объявлений.</p>
          </div>
        </div>
      </div>
    </section>
  );
};

export default Section;
