import React from "react";
import { Button } from "antd";
import axios from "axios";
import api from "../api";
const ExButtons = ({ setTodos }) => {
  // ✅ Spring Boot API 호출

  const fetchTodos = async () => {
    try {
      const response = await api.get("/todo/list");
      setTodos(response.data); // ✅ axios는 자동으로 JSON 변환을 해줌
    } catch (error) {
      console.error("데이터 가져오기 실패:", error);
    }
  };
  

  return (
    <div style={{ marginTop: "20px" }}>
      <h3 style={{textAlign :"center"}}>Ant Design 버튼 예제</h3>
      <div  style={{textAlign :"center"}}>
      <Button type="primary" onClick={fetchTodos}> {/* ✅ 클릭 시 API 요청 */}
        할 일 불러오기
      </Button>
      </div>
    </div>
  );
};

export default ExButtons;
