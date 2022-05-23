import React, { useState, useEffect } from "react";
import { useForm } from "react-hook-form";
import TextInput from "../TextInput";
import Button from "../Button";
import Captcha from "../Captcha";
import { ReactComponent as Mark } from "../../assets/img/mark.svg";
import styles from "./index.module.css";
import {
  validateName,
  validatePassword,
  validateEmail,
  validatePasswordConfirmation,
  validatePhone,
} from "../../utils/validate.js";

const SIGN_IN = "signin";
const SIGN_UP = "signup";

const Modal = ({ isOpen, close }) => {
  const [type, setType] = useState(SIGN_IN);

  const {
    control,
    handleSubmit,
    formState: { errors },
    reset,
  } = useForm({
    defaultValues: {
      email: "",
      password: "",
    },
  });

  const handleChangeType = (type) => {
    type === SIGN_IN ? setType(SIGN_UP) : setType(SIGN_IN);
    reset();
  };

  const onSubmit = (values) => {
    console.log(values, "values");
  };

  console.log("ошибки", errors);

  return (
    <>
      {isOpen && (
        <div className={styles.modal}>
          <div className={styles.icon} onClick={() => close()}>
            <Mark />
          </div>
          {type === SIGN_IN ? (
            <form className={styles.form} onSubmit={handleSubmit(onSubmit)}>
              <h2 className={styles.title}>Вход</h2>
              <TextInput
                type="email"
                name="email"
                rules={{ validate: validateEmail }}
                control={control}
                errors={errors.email}
                placeholder="E-MAIL"
                style={styles.input}
              />
              <TextInput
                name="password"
                rules={{ validate: validatePassword }}
                control={control}
                errors={errors.password}
                placeholder="PASSWORD"
                style={styles.input}
              />
              <Button title="Войти" type="submit" />
              <h6
                className={styles.link}
                onClick={() => handleChangeType(type)}
              >
                Зарегистрироваться
              </h6>
            </form>
          ) : (
            <form className={styles.form} onSubmit={handleSubmit(onSubmit)}>
              <h2 className={styles.title}>Регистрация</h2>

              <TextInput
                type="email"
                name="email"
                rules={{ validate: validateEmail }}
                control={control}
                errors={errors.email}
                placeholder="E-MAIL"
                style={styles.input}
              />
              <TextInput
                type="text"
                name="name"
                rules={{ validate: validateName }}
                control={control}
                errors={errors.name}
                placeholder="NAME"
                style={styles.input}
              />
              <TextInput
                type="tel"
                name="phone"
                rules={{ validate: validatePhone }}
                control={control}
                errors={errors.phone}
                placeholder="PHONE"
                style={styles.input}
              />
              <TextInput
                name="password"
                rules={{ validate: validatePassword }}
                control={control}
                errors={errors.password}
                placeholder="PASSWORD"
                style={styles.input}
              />
              <TextInput
                type="password"
                name="passwordConfirm"
                rules={{ validate: validatePasswordConfirmation }}
                control={control}
                errors={errors.passwordConfirm}
                placeholder="PASSWORD CONFIRM"
                style={styles.input}
              />
              {/* <Captcha /> */}
              <Button title="Регистрация" type="submit" />
              <h6
                className={styles.link}
                onClick={() => handleChangeType(type)}
              >
                Уже есть аккаунт? Войти
              </h6>
            </form>
          )}
        </div>
      )}
    </>
  );
};

export default Modal;
