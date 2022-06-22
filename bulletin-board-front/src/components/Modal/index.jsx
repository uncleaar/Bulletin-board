import React, { useEffect, useState } from "react";
import { useForm } from "react-hook-form";
import { toast } from "react-toastify";
import { signUp, signIn } from "../../requests/auth";
import { useAuth } from "../../hooks/useAuth";
import { useLocalStorage } from "../../hooks/useLocalStorage";
import { useModal } from "../../hooks/useModal";
import Toast from "../Toast";
import TextInput from "../TextInput";
import Button from "../Button";
// import Captcha from "../Captcha";
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

  const { accessToken, user, setToken, setUser } = useAuth();

  const { setValueIntoLocalStorage } = useLocalStorage();

  const {
    control: controlSignin,
    handleSubmit: handleSignin,
    formState: { errors: errorsSignin },
    reset: resetSignin,
  } = useForm({
    defaultValues: {
      email: "",
      password: "",
    },
  });

  const {
    control: controlSignup,
    handleSubmit: handleSignup,
    formState: { errors: errorsSignup },
    reset: resetSignup,
  } = useForm({
    defaultValues: {
      email: "",
      name: "",
      phone: "",
      password: "",
      passwordConfirm: "",
    },
  });

  const handleChangeType = (type) => {
    type === SIGN_IN ? setType(SIGN_UP) : setType(SIGN_IN);
  };

  const signInHandler = async (values) => {
    const userData = {
      login: values.email,
      password: values.password,
    };
    const { data } = await signIn(userData);
    try {
      console.log(data, "data");
      setToken(data?.token);
      setUser(data?.webClient);
      setValueIntoLocalStorage("accessToken", data.token);
      setValueIntoLocalStorage("user", data.webClient.name);
      resetSignin();
      close();
    } catch (err) {
      toast(data.status.description);
    }
  };

  const signUpHandler = async (values) => {
    try {
      const userData = {
        login: values.email,
        password: values.password,
        name: values.name,
        phoneNumber: values.phone,
      };
      const { data } = await signUp(userData);
      setToken(data?.token);
      setUser(data?.webClient);
      setValueIntoLocalStorage("accessToken", data.token);
      setValueIntoLocalStorage("user", data.webClient.name);
      resetSignup();
      close();
    } catch (err) {
      console.log(err);
    }
  };

  useEffect(() => {
    resetSignin();
    resetSignup();
  }, [resetSignin, resetSignup]);

  // useEffect(() => {
  //   const formdata = new FormData();
  //   formdata.append(
  //     "photo",
  //     "https://img.joinfo.com/i/2018/04/800x0/5adddd1b1b5de.jpg"
  //   );
  //   console.log("fromdata", formdata);
  // }, []);

  return (
    <>
      {isOpen && (
        <div className={styles.modal}>
          <div className={styles.icon} onClick={() => close()}>
            <Mark />
          </div>
          {type === SIGN_IN && (
            <>
              <h2 className={styles.title}>Вход</h2>
              <form
                className={styles.form}
                onSubmit={handleSignin(signInHandler)}
              >
                <TextInput
                  type="email"
                  name="email"
                  rules={{ validate: validateEmail }}
                  control={controlSignin}
                  errors={errorsSignin.email}
                  placeholder="E-MAIL"
                  style={styles.input}
                />
                <TextInput
                  type="password"
                  name="password"
                  rules={{ validate: validatePassword }}
                  control={controlSignin}
                  errors={errorsSignin.password}
                  placeholder="PASSWORD"
                  style={styles.input}
                />
                <Button title="Войти" type="submit" />
              </form>
              <h6
                className={styles.link}
                onClick={() => handleChangeType(type)}
              >
                Зарегистрироваться
              </h6>
            </>
          )}
          {type === SIGN_UP && (
            <>
              <h2 className={styles.title}>Регистрация</h2>
              <form
                className={styles.form}
                onSubmit={handleSignup(signUpHandler)}
              >
                <TextInput
                  type="email"
                  name="email"
                  rules={{ validate: validateEmail }}
                  control={controlSignup}
                  errors={errorsSignup.email}
                  placeholder="E-MAIL"
                  style={styles.input}
                />
                <TextInput
                  name="name"
                  rules={{ validate: validateName }}
                  control={controlSignup}
                  errors={errorsSignup.name}
                  placeholder="NAME"
                  style={styles.input}
                />
                <TextInput
                  type="tel"
                  name="phone"
                  rules={{ validate: validatePhone }}
                  control={controlSignup}
                  errors={errorsSignup.phone}
                  placeholder="PHONE"
                  style={styles.input}
                />
                <TextInput
                  type="password"
                  name="password"
                  rules={{ validate: validatePassword }}
                  control={controlSignup}
                  errors={errorsSignup.password}
                  placeholder="PASSWORD"
                  style={styles.input}
                />
                <TextInput
                  type="password"
                  name="passwordConfirm"
                  rules={{ validate: validatePasswordConfirmation }}
                  control={controlSignup}
                  errors={errorsSignup.passwordConfirm}
                  placeholder="PASSWORD CONFIRM"
                  style={styles.input}
                />
                {/* <Captcha /> */}
                <Button title="Регистрация" type="submit" />
              </form>
              <h6
                className={styles.link}
                onClick={() => handleChangeType(type)}
              >
                Уже есть аккаунт? Войти
              </h6>
            </>
          )}
        </div>
      )}
      <Toast />
    </>
  );
};

export default Modal;
