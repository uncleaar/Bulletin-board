import React, { useState, createContext } from "react";

export const AuthContext = createContext({
  accessToken: undefined,
  user: undefined,
  setToken: () => {},
  setUser: () => {},
  isAuth: false,
  setAuth: () => {},
});

export const AuthProvider = ({ children }) => {
  const [accessToken, setToken] = useState();

  const [user, setUser] = useState();

  const [isAuth, setAuth] = useState(false);

  return (
    <AuthContext.Provider
      value={{ accessToken, user, setToken, setUser, isAuth, setAuth }}
    >
      {children}
    </AuthContext.Provider>
  );
};
