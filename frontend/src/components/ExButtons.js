import React, { useState } from "react";
import { Button } from "antd";

const ExButtons = () => {
  const [todos, setTodos] = useState([]);

  // ✅ Spring Boot API 호출 함수
  const fetchTodos = async () => {
    try {
      const response = await fetch("http://localhost:8080/api/todo/List");
      const data = await response.json();
      setTodos(data);
    } catch (error) {
      console.error("데이터 가져오기 실패:", error);
    }
  };

  return (
    <div style={{ marginTop: "20px" }}>
      <h3>Ant Design 버튼 예제</h3>
      <Button type="primary">Primary 버튼</Button>
      <Button>기본 버튼</Button>
      <Button type="dashed">Dashed 버튼</Button>
      <Button type="text" onClick={fetchTodos}> {/* ✅ 클릭 시 API 요청 */}
        Text 버튼
      </Button>
      <Button type="link">Link 버튼</Button>

      {/* ✅ 리스트 데이터 출력 */}
      {todos.length > 0 && (
        <ul>
          {todos.map((todo) => (
            <li key={todo.id}>
              {todo.title} - {todo.status}
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default ExButtons;