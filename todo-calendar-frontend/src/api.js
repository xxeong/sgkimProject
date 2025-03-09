import axios from "axios";

const API_BASE_URL = "http://localhost:8080/api";
const api = axios.create({
  baseURL: API_BASE_URL,
  timeout: 5000, // ⏳ 요청 타임아웃 (5초)
  headers: {
    "Content-Type": "application/json",
  },
});

export default api;