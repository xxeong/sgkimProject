import React, { useState } from "react";
import { List, Button, Input, Space } from "antd";

const TodoList = () => {
  const [tasks, setTasks] = useState([]);
  const [inputValue, setInputValue] = useState("");

  const addTask = () => {
    if (!inputValue) return;
    setTasks([...tasks, { id: Date.now(), text: inputValue, completed: false }]);
    setInputValue("");
  };

  const toggleComplete = (id) => {
    setTasks(tasks.map(task => task.id === id ? { ...task, completed: !task.completed } : task));
  };

  return (
    <div style={{ padding: "20px", maxWidth: "400px", margin: "auto" }}>
      <h2>할 일 목록 (TODO)</h2>
      <Space>
        <Input value={inputValue} onChange={(e) => setInputValue(e.target.value)} placeholder="할 일 추가" />
        <Button type="primary" onClick={addTask}>추가</Button>
      </Space>
      <List
        bordered
        dataSource={tasks}
        renderItem={(task) => (
          <List.Item
            style={{ textDecoration: task.completed ? "line-through" : "none" }}
            actions={[
              <Button onClick={() => toggleComplete(task.id)}>
                {task.completed ? "취소" : "완료"}
              </Button>
            ]}
          >
            {task.text}
          </List.Item>
        )}
      />
    </div>
  );
};

export default TodoList;
