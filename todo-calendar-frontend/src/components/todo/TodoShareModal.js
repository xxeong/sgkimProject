import React, { useState, useEffect } from "react";
import { Modal, Button, Input, List, Checkbox, message } from "antd";
import api from "../../api";

export default function TodoShareModal({ todos, visible, onClose }) {
    const [selectedTodoIds, setSelectedTodoIds] = useState([]);
    const [email, setEmail] = useState("");

    // ✅ 체크박스 선택 핸들러
    const handleSelectTodo = (todoId, checked) => {
        setSelectedTodoIds((prev) =>
            checked ? [...prev, todoId] : prev.filter((id) => id !== todoId)
        );
    };

    // ✅ 공유 API 호출
    const handleShare = async () => {
        if (!email.trim()) {
            message.error("이메일을 입력하세요!");
            return;
        }
        if (selectedTodoIds.length === 0) {
            message.error("공유할 할 일을 선택하세요!");
            return;
        }

        try {
            await api.post("/todo/share/mail", {
                email: email,
                todoIds: selectedTodoIds,
            });

            message.success("이메일로 선택한 할 일이 공유되었습니다!");
            onClose(); // ✅ 모달 닫기
            setSelectedTodoIds([]); // ✅ 선택 목록 초기화
            setEmail(""); // ✅ 이메일 입력 초기화
        } catch (error) {
            console.error("이메일 공유 실패:", error);
            message.error("이메일 공유에 실패했습니다.");
        }
    };

    // ✅ 모달이 닫힐 때 선택한 항목과 이메일을 초기화
    useEffect(() => {
        if (!visible) {
            setSelectedTodoIds([]); // ✅ 선택 목록 초기화
            setEmail(""); // ✅ 이메일 입력 초기화
        }
    }, [visible]); // visible 값이 변경될 때 실행

    return (
        <Modal
            title="할 일 공유하기"
            open={visible}
            onCancel={onClose}
            footer={[
                <Button key="cancel" onClick={onClose}>
                    닫기
                </Button>,
                <Button key="submit" type="primary" onClick={handleShare}>
                    공유
                </Button>,
            ]}
        >
            <Input
                placeholder="이메일을 입력하세요."
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                style={{ marginBottom: "10px" }}
            />

            {/* 완료되지 않은 할 일 목록 표시 */}
            <div style={{ maxHeight: "400px", overflowY: "auto" }}>
                <List
                    bordered
                    dataSource={todos.filter((todo) => todo.status !== "Y")} // ✅ 완료되지 않은 항목만 필터링
                    renderItem={(todo) => (
                        <List.Item>
                            <Checkbox
                                checked={selectedTodoIds.includes(todo.id)}
                                onChange={(e) => handleSelectTodo(todo.id, e.target.checked)}
                            />
                            {todo.title} 📅 {todo.dueDate || "마감일 없음"}
                        </List.Item>
                    )}
                />
            </div>
        </Modal>
    );
}
