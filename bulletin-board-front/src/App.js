import React from "react";
import { BrowserRouter } from "react-router-dom";
import { AuthProvider } from "./context/AuthContext";
import RouteComponent from "./pages";

const App = () => {
  return (
    <BrowserRouter>
      <AuthProvider>
        <RouteComponent />
      </AuthProvider>
    </BrowserRouter>
  );
};

export default App;
