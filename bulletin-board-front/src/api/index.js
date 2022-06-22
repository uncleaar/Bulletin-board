import axios from "axios";
import { API_URL } from "../constants/global";

const SLEEP_TIME = 7200;

const sleep = () => new Promise((res) => setTimeout(res, SLEEP_TIME));

const axiosInstance = axios.create({
  baseURL: API_URL,
});

axiosInstance.interceptors.response.use(async (response) => {
  await sleep();
  return response;
});

export { axiosInstance };
