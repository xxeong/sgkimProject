import React, { useState } from "react";
import { Calendar, Badge } from "antd";

const MyCalendar = () => {
  const [tasks, setTasks] = useState({
    "2025-03-01": [{ text: "회의", type: "warning" }],
    "2025-03-05": [{ text: "프로젝트 마감", type: "error" }],
  });

  const dateCellRender = (value) => {
    const dateString = value.format("YYYY-MM-DD");
    const listData = tasks[dateString] || [];

    return (
      <ul>
        {listData.map((item, index) => (
          <li key={index}>
            <Badge status={item.type} text={item.text} />
          </li>
        ))}
      </ul>
    );
  };

  return (
    <div style={{ padding: "20px" }}>
      <h2>일정 캘린더</h2>
      <Calendar dateCellRender={dateCellRender} />
    </div>
  );
};

export default MyCalendar;
