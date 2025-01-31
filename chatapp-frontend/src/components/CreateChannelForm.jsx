import { useState, useEffect } from "react";

const CreateChannelForm = ({ onChannelCreated }) => {
  const [channelName, setChannelName] = useState("");
  const [users, setUsers] = useState([]);
  const [selectedUsers, setSelectedUsers] = useState([]);

  useEffect(() => {
    fetch("http://localhost:8080/api/users")
      .then((response) => response.json())
      .then((data) => setUsers(data))
      .catch((error) => console.error("Error loading users:", error));
  }, []);

  const handleCreateChannel = async () => {
    if (!channelName || selectedUsers.length === 0) {
      alert("Enter a channel name and select at least one user.");
      return;
    }

    const userIds = selectedUsers.join(",");

    try {
      const response = await fetch(`http://localhost:8080/api/channels/create?name=${channelName}&userIds=${userIds}`, {
        method: "POST",
      });

      if (!response.ok) throw new Error("Failed to create channel");

      const newChannel = await response.json();
      onChannelCreated(newChannel); // Pass the new channel to the parent component
      setChannelName(""); // Clear the inputs
      setSelectedUsers([]);
    } catch (error) {
      console.error("Error creating channel:", error);
    }
  };

  return (
    <div className="bg-black p-4 rounded-lg shadow-md">
      <h2 className="text-lg font-bold mb-2">Create New Channel</h2>
      <input
        type="text"
        placeholder="Channel Name"
        value={channelName}
        onChange={(e) => setChannelName(e.target.value)}
        className="w-full p-2 border rounded mb-2"
      />

      <h3 className="text-md font-semibold">Select Users:</h3>
      <div className="flex flex-wrap">
        {users.map((user) => (
          <label key={user.id} className="mr-2">
            <input
              type="checkbox"
              value={user.id}
              onChange={(e) => {
                const id = Number(e.target.value);
                setSelectedUsers((prev) =>
                  prev.includes(id) ? prev.filter((uid) => uid !== id) : [...prev, id]
                );
              }}
            />
            {user.username}
          </label>
        ))}
      </div>

      <button onClick={handleCreateChannel} className="mt-3 p-2 bg-blue-500 text-white rounded">
        Create
      </button>
    </div>
  );
};

export default CreateChannelForm;
