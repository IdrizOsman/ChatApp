import React, { useState, useEffect } from "react";
import { Routes, Route, Navigate, useNavigate } from "react-router-dom"; // No need for BrowserRouter here
import CreateChannelForm from "./components/CreateChannelForm";
import ChannelList from "./components/ChannelList";
import Login from "./components/Login";
import Register from "./components/Register";
import { loginUser, registerUser } from "./services/authService";

function App() {
  const [messages, setMessages] = useState([]);
  const [message, setMessage] = useState("");
  const [channels, setChannels] = useState([]);
  const [selectedChannel, setSelectedChannel] = useState(null);
  const [selectedUser, setSelectedUser] = useState(null); // New state for selected user
  const [users, setUsers] = useState([]); // New state for users
  const [isAuthenticated, setIsAuthenticated] = useState(false); // Authentication state
  const navigate = useNavigate();

  // Check authentication status on load
  useEffect(() => {
    const token = localStorage.getItem("authToken");
    if (token) {
      setIsAuthenticated(true);
    }
  }, []);

  // Fetch channels and users
  useEffect(() => {
    if (isAuthenticated) {
      const fetchChannels = async () => {
        try {
          const response = await fetch("http://localhost:8080/api/channels");
          const data = await response.json();
          setChannels(data);
        } catch (error) {
          console.error("Error loading channels:", error);
        }
      };

      const fetchUsers = async () => {
        try {
          const response = await fetch("http://localhost:8080/api/users");
          const data = await response.json();
          setUsers(data);
        } catch (error) {
          console.error("Error loading users:", error);
        }
      };

      fetchChannels();
      fetchUsers();
    }
  }, [isAuthenticated]);

  // Handle deleting a channel
  const deleteChannel = async (channelId) => {
    try {
      const response = await fetch(`http://localhost:8080/api/channels/${channelId}`, {
        method: "DELETE",
      });

      if (!response.ok) {
        throw new Error("Failed to delete channel");
      }

      setChannels((prevChannels) =>
        prevChannels.filter((channel) => channel.id !== channelId)
      );
    } catch (error) {
      console.error("Error deleting channel:", error);
    }
  };

  // Handle sending a message
  const sendMessage = async () => {
    if (!selectedUser || !message.trim()) return;

    const currentUserId = 1; // Replace with dynamic user ID

    try {
      const response = await fetch(
        `http://localhost:8080/api/messages/user/${currentUserId}/${selectedUser.id}`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            content: message,
          }),
          credentials: "include", // Include credentials for authentication
        }
      );

      if (!response.ok) {
        throw new Error("Failed to send message");
      }

      const data = await response.json();
      setMessages((prevMessages) => [...prevMessages, { sender: "You", text: message }]);
      setMessage(""); // Clear message input
    } catch (error) {
      console.error("Error sending message:", error);
    }
  };

  // Select a channel
  const handleSelectChannel = (channel) => {
    setSelectedChannel(channel);
    setSelectedUser(null); // Reset selected user when selecting a channel
  };

  // Select a user
  const handleSelectUser = (user) => {
    setSelectedUser(user);
    setSelectedChannel(null); // Reset selected channel when selecting a user
  };

  // Create a new channel
  const createChannel = (newChannel) => {
    setChannels((prevChannels) => [...prevChannels, newChannel]);
  };

  // Handle login
  const handleLogin = async (username, password) => {
    const response = await loginUser(username, password);
    if (response && response.token) {
      localStorage.setItem("authToken", response.token);
      setIsAuthenticated(true);
    }
  };

  // Handle logout
  const handleLogout = () => {
    localStorage.removeItem("authToken"); // Remove token from storage
    setIsAuthenticated(false); // Update authentication state
  };

  // Handle user registration
  const handleRegister = async (username, password) => {
    const response = await registerUser(username, password);
    if (response) {
      navigate("/login"); // Redirect to login after registration
    }
  };

  return (
    <Routes>
      <Route
        path="/"
        element={isAuthenticated ? <Navigate to="/chat" /> : <Navigate to="/login" />}
      />
      <Route
        path="/login"
        element={isAuthenticated ? <Navigate to="/chat" /> : <Login onLogin={handleLogin} />}
      />
      <Route path="/register" element={<Register onRegister={handleRegister} />} />
      <Route
        path="/chat"
        element={
          isAuthenticated ? (
            <div className="flex flex-col h-screen bg-gray-900 p-4">
              <h1 className="text-2xl font-bold text-center mb-4">Chat App</h1>

              {/* Logout Button */}
              <button
                onClick={handleLogout}
                className="bg-red-500 text-white px-4 py-2 rounded-lg mb-4"
              >
                Logout
              </button>

              {/* Create Channel Form */}
              <CreateChannelForm onChannelCreated={createChannel} />

              {/* Channel List */}
              <ChannelList
                channels={channels}
                onSelectChannel={handleSelectChannel}
                onDeleteChannel={deleteChannel}
              />

              {/* Select User */}
              <div className="mt-4">
                <h3 className="text-xl font-semibold">Send Message To:</h3>
                <select
                  className="w-full p-2 mt-2 mb-4"
                  value={selectedUser ? selectedUser.id : ""}
                  onChange={(e) => {
                    const user = users.find((u) => u.id === parseInt(e.target.value));
                    handleSelectUser(user);
                  }}
                >
                  <option value="">Select User</option>
                  {users.map((user) => (
                    <option key={user.id} value={user.id}>
                      {user.username}
                    </option>
                  ))}
                </select>
              </div>

              {/* Chatbox and Messages */}
              <div className="flex-1 overflow-y-auto bg-black p-4 rounded-lg shadow">
                {selectedChannel && (
                  <h2 className="text-xl font-bold mb-4">{selectedChannel.name}</h2>
                )}
                {selectedUser && (
                  <h2 className="text-xl font-bold mb-4">
                    Chatting with {selectedUser.username}
                  </h2>
                )}
                {messages.map((msg, index) => (
                  <div
                    key={index}
                    className={`mb-2 ${msg.sender === "You" ? "text-right" : "text-left"}`}
                  >
                    <span className="bg-blue-500 text-white px-3 py-1 rounded-lg">{msg.text}</span>
                  </div>
                ))}
              </div>

              {/* Message Input */}
              <div className="flex mt-4">
                <input
                  type="text"
                  value={message}
                  onChange={(e) => setMessage(e.target.value)}
                  className="flex-1 p-2 border border-gray-300 rounded-l-lg"
                  placeholder="Write a message..."
                />
                <button
                  onClick={sendMessage}
                  className="bg-blue-500 text-white px-4 py-2 rounded-r-lg hover:bg-blue-600"
                >
                  Send
                </button>
              </div>
            </div>
          ) : (
            <Navigate to="/login" />
          )
        }
      />
    </Routes>
  );
}

export default App;
