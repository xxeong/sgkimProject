import React from "react";
import { List, Button } from "antd";

const TodoList = ({ tasks }) => {
  return (
    <div style={{ padding: "20px", maxWidth: "400px", margin: "auto" }}>
      <h2>할 일 목록 (TODO)</h2>
      <List
        bordered
        dataSource={tasks}
        renderItem={(task) => (
          <List.Item
            style={{ textDecoration: task.status === "completed" ? "line-through" : "none" }}
            actions={[<Button>{task.status === "PENDING" ? "취소" : "완료"}</Button>]}
          >
            <h4>{task.title}</h4>
            
            {task.description}
          </List.Item>
        )}
      />
    </div>
  );
};

export default TodoList;
