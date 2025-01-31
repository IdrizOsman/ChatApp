// components/Dashboard.jsx
import React from "react";
import { useHistory } from "react-router-dom";

const Dashboard = () => {
  const history = useHistory();

  const handleLogout = () => {
    localStorage.removeItem("authToken"); // Or any other method you use for storing tokens
    history.push("/"); // Redirect back to login page
  };

  return (
    <div>
      <h1>Welcome to the Dashboard!</h1>
      <button onClick={handleLogout}>Logout</button>
    </div>
  );
};

export default Dashboard;
