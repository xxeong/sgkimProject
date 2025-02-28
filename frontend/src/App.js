import React from "react";
import TodoList from "./components/TodoList";
import MyCalendar from "./components/Calendar";
import ExButtons from "./components/ExButtons";
import { Layout } from "antd";

const { Header, Content } = Layout;

function App() {
  return (
    <Layout>
      <Header style={{ color: "white", textAlign: "center", fontSize: "20px" }}>
        TODO & Calendar
      </Header>
      <Content style={{ padding: "20px" }}>
        <TodoList />
        <MyCalendar />
        <ExButtons /> {/* ✅ 버튼 추가 */}
      </Content>
    </Layout>
    
  );
}

export default App;
