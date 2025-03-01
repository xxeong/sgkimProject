import React, { useState } from "react";
import TodoList from "./components/TodoList";
import MyCalendar from "./components/Calendar";
import ExButtons from "./components/ExButtons";
import { Layout } from "antd";

const { Header, Content } = Layout;

const App = () => {
  const [todos, setTodos] = useState([]); // ✅ 할 일 목록을 상태로 관리

  return (
    <div>
      <h1 style={{textAlign :"center"}}>할 일 관리 앱</h1>
      <ExButtons setTodos={setTodos} /> {/* ✅ 데이터를 설정하는 함수 전달 */}
      <TodoList tasks={todos} /> {/* ✅ API 데이터를 TodoList에 전달 */}
    </div>
  );
};

export default App;
