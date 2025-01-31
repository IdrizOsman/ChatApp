import React from "react";

const ChannelList = ({ channels, onSelectChannel, onDeleteChannel }) => {
  return (
    <div className="p-4 bg-black rounded-lg shadow-md">
      <h2 className="text-lg font-bold mb-3">Списък с Канали</h2>
      <ul>
        {channels.map((channel) => (
          <li key={channel.id} className="border-b p-2 flex justify-between items-center">
            <span onClick={() => onSelectChannel(channel)} className="cursor-pointer text-blue-600">
              {channel.name}
            </span>
            <button
              onClick={() => onDeleteChannel(channel.id)} // Изтриване на канал
              className="bg-red-500 text-white px-2 py-1 rounded"
            >
              Изтрий
            </button>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default ChannelList;
