import { useContext } from "react";
import { AuthContext } from "../context/AuthContext";

export const useAuth = () => {
  const { accessToken, user, setToken, setUser, setAuth, isAuth } =
    useContext(AuthContext);

  return { accessToken, user, setToken, setUser, setAuth, isAuth };
};
