import React, { useEffect } from "react";
import { Routes, Route } from "react-router-dom";
import HomePage from "./HomePage";
import { useLocalStorage } from "../hooks/useLocalStorage";
import { useAuth } from "../hooks/useAuth";

const RouteComponent = () => {
  const { getValueFromLocalStorage } = useLocalStorage();

  const { isAuth, setAuth } = useAuth();

  useEffect(() => {
    const accessToken = getValueFromLocalStorage("accessToken");
    if (accessToken && accessToken !== "null") setAuth(true);
  }, [setAuth, getValueFromLocalStorage]);

  console.log(isAuth, "isAuth");

  return (
    <Routes>
      <Route path="/" element={<HomePage />} />
    </Routes>
  );
};

export default RouteComponent;
