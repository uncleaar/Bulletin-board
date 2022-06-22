import { axiosInstance } from "../api/index";

export const signUp = (userData) =>
  axiosInstance.post("auth/registration", userData);

export const signIn = (userData) => axiosInstance.post("auth/login", userData);
