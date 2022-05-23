import React from "react";
import ReCAPTCHA from "react-google-recaptcha";
import { GOOGLE_API_PUBLIC_KEY } from "../../constants/global.js";

const Captcha = () => {
  const onChange = (value) => {
    console.log("Captcha value:", value);
  };

  return <ReCAPTCHA sitekey={GOOGLE_API_PUBLIC_KEY} onChange={onChange} />;
};

export default Captcha;
