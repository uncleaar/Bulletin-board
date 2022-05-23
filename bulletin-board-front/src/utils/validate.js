export const validateName = (name) => {
  if (!name) {
    return "Поле обязательно";
  }
};

export const validatePassword = (password) => {
  if (!password) {
    return "Поле обязательно";
  }

  if (password.length < 6) {
    return "Длина пароля должна быть больше 6";
  }
};

export const validateEmail = (email) => {
  if (!email) {
    return "Поле обязательно";
  }

  if (
    !email
      .toLowerCase()
      .match(
        /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
      )
  ) {
    return "Некорректный ввод";
  }
};

export const validatePasswordConfirmation = (passwordConfirmation) => {
  if (!passwordConfirmation) {
    return "Поле обязательно";
  }
};

export const validatePhone = (phone) => {
  if (!phone.toLowerCase().match(/^\+?[1-9][0-9]{7,14}$/)) {
    return "Некорректный ввод";
  }
};
