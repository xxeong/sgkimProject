import React, { createContext, useContext, useState, useEffect } from "react";

const TodoContext = createContext(null);

// ✅ 전역에서 사용할 수 있도록 Provider 설정
export function TodoProvider({ children }) {
    const [dueSoonTodos, setDueSoonTodos] = useState([]);

    useEffect(() => {
        const updateDueSoonTodos = () => {
            const savedNotifications = JSON.parse(localStorage.getItem("dueSoonTodos")) || [];
            setDueSoonTodos(savedNotifications);
        };

        window.addEventListener("storage", updateDueSoonTodos);
        updateDueSoonTodos();

        return () => {
            window.removeEventListener("storage", updateDueSoonTodos);
        };
    }, []);

    return (
        <TodoContext.Provider value={{ dueSoonTodos, setDueSoonTodos }}>
            {children}
        </TodoContext.Provider>
    );
}

export function useTodoContext() {
    const context = useContext(TodoContext);
    if (!context) {
        throw new Error("useTodoContext must be used within a TodoProvider");
    }
    return context;
}
