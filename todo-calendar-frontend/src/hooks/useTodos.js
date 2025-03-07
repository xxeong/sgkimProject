import { useState, useEffect, useCallback } from "react";
import { message } from "antd"; // ✅ 알림 추가
import api from "../api";
import dayjs from "dayjs";

export default function useTodos(setDueSoonTodos) {
    const [todos, setTodos] = useState([]);
    const [sortOrder, setSortOrder] = useState("asc");
    const [showCompleted, setShowCompleted] = useState(true);

    const filteredTodos = todos.filter(todo => showCompleted || todo.status !== "Y");

    // ✅ 새로고침 시 알람을 다시 띄우도록 `localStorage.hasNotified` 초기화
    useEffect(() => {
        console.log("🔄 새로고침 감지 - 알림 초기화");
        localStorage.removeItem("hasNotified");
    }, []);

    const updateDueSoonTodos = useCallback((updatedTodos) => {
        console.log("🚀 updateDueSoonTodos 실행됨");

        const today = dayjs().startOf("day");
        const dueSoon = updatedTodos.filter(todo =>
            todo.dueDate &&
            todo.status === "N" &&
            dayjs(todo.dueDate).startOf("day").diff(today, "day") === 1
        );

        console.log("⚠️ 마감 하루 전 할 일:", dueSoon);

        setDueSoonTodos(dueSoon);
        localStorage.setItem("dueSoonTodos", JSON.stringify(dueSoon));
        window.dispatchEvent(new Event("storage"));

        // ✅ 기존 알림 여부 확인 (중복 방지)
        const hasNotifiedBefore = localStorage.getItem("hasNotified") === "true";
        console.log("📢 기존에 알림 표시됨?:", hasNotifiedBefore);

        if (dueSoon.length > 0 && !hasNotifiedBefore) {

            console.log("🔔 알림 표시 실행");
            message.warning("⚠️ 마감 하루 전인 할 일이 있습니다! 🔔");
            localStorage.setItem("hasNotified", "true"); // ✅ 알림 중복 방지

        } else if (dueSoon.length === 0) {
            console.log("🗑️ 알림 초기화");
            localStorage.removeItem("hasNotified"); // ✅ 마감 하루 전 일정이 없으면 알림 초기화
        }
    }, [setDueSoonTodos]);

    const getTodos = useCallback(async () => {
        try {
            const response = await api.get(`/todo?order=${sortOrder}`);
            setTodos(response.data);
            updateDueSoonTodos(response.data);
        } catch (error) {
            console.error("할 일 가져오기 실패:", error);
        }
    }, [sortOrder, updateDueSoonTodos]);

    useEffect(() => {
        getTodos();
    }, [getTodos]);

    return { todos, setTodos, getTodos, sortOrder, setSortOrder, showCompleted, setShowCompleted, filteredTodos };
}
