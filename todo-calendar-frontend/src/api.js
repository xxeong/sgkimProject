import axios from "axios";

const API_BASE_URL = process.env.REACT_APP_API_BASE_URL; // 환경 변수 불러오기

const api = axios.create({
  baseURL: API_BASE_URL,
  timeout: 5000, // ⏳ 요청 타임아웃 (5초)
  headers: {
    "Content-Type": "application/json",
  },
});

export default api;