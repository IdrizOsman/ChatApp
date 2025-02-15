import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import { BrowserRouter } from "react-router-dom";  // Make sure to import BrowserRouter
import "./index.css";
import App from "./App.jsx";

createRoot(document.getElementById("root")).render(
  <StrictMode>
    <BrowserRouter>  {/* Only one Router here */}
      <App />
    </BrowserRouter>
  </StrictMode>
);
