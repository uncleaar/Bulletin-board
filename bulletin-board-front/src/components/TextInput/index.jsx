import React from "react";
import { Controller } from "react-hook-form";
import styles from "./index.module.css";

const TextInput = ({
  name,
  control,
  rules,
  type,
  placeholder,
  errors,
  style,
}) => {
  return (
    <>
      <Controller
        name={name}
        control={control}
        rules={rules}
        render={({ field: { value, onChange } }) => (
          <input
            type={type}
            value={value}
            onChange={onChange}
            className={`${styles.input} ${style}`}
            placeholder={placeholder}
          />
        )}
      />
      {errors && <div className={styles.error}>{errors.message}</div>}
    </>
  );
};

export default TextInput;
