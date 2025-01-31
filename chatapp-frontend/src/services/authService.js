// services/authService.js

const API_URL_LOGIN = "http://localhost:8080/api/auth/login"; // Login API URL
const API_URL_REGISTER = "http://localhost:8080/api/auth/register"; // Register API URL

// Login function
export const loginUser = async (username, password) => {
  try {
    const response = await fetch(API_URL_LOGIN, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ username, password }),
    });

    if (!response.ok) {
      throw new Error("Login failed");
    }

    const data = await response.json();
    localStorage.setItem("authToken", data.token); // Store the token
    return data; // Return data containing the token
  } catch (error) {
    console.error("Error logging in:", error);
      throw error; // Propagate the error to be handled in the Login component
    }
  };

// Register function
export const registerUser = async (username, password) => {
  console.log('Sending registration request...');
  try {
    const response = await fetch("http://localhost:8080/api/auth/register", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ username, password }),
    });

    console.log('Response status:', response.status);

    if (!response.ok) {
      throw new Error("Registration failed");
    }

    return await response.json();
  } catch (error) {
    console.error("Error during registration:", error);
    throw error;
  }
};

