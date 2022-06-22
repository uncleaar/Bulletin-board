import { axiosInstance } from "../api/index";

export const getAds = async () => {
  const response = await axiosInstance.get("advertisements");
  return response;
};

export const getPhotos = async () => {
  const response = await axiosInstance.get("photos");
  return response;
};

export const getLocalities = async () => {
  const response = await axiosInstance.get("localities");
  return response;
};

export const getAd = (id) => axiosInstance.get(`advertisements/${id}`);

export const getPhoto = (id) => axiosInstance.get(`photos/${id}`);

export const getLocality = (id) => axiosInstance.get(`localitites/${id}`);
