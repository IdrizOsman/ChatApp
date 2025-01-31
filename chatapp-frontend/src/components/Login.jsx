import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom'; // useNavigate hook for navigation

const Login = ({ onLogin }) => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate(); // Initialize navigate for routing

  const handleLoginSubmit = (e) => {
    e.preventDefault();
    onLogin(username, password);
  };

  return (
    <div className="login-container">
      <h2>Login</h2>
      <form onSubmit={handleLoginSubmit}>
        <input
          type="text"
          placeholder="Username"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
        />
        <input
          type="password"
          placeholder="Password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />
        <button type="submit">Login</button>
      </form>

      {/* Add the Register button here */}
      <div className="register-link">
        <button
          onClick={() => navigate('/register')}
          className="bg-blue-500 text-white px-4 py-2 rounded-lg mt-4"
        >
          Register
        </button>
      </div>
    </div>
  );
};

export default Login;
