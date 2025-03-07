import React, { useState, useRef, useEffect } from "react";
import { Button, List } from "antd";
import { BellOutlined, BellTwoTone } from "@ant-design/icons";
import { useTodoContext } from "../../context/TodoContext";

function NotificationBell() {
    const { dueSoonTodos } = useTodoContext(); // ✅ 전역 상태 사용
    const [showNotifications, setShowNotifications] = useState(false);
    const notificationRef = useRef(null);

    // ✅ 알림창 외부 클릭 시 닫기
    useEffect(() => {
        function handleClickOutside(event) {
            if (notificationRef.current && !notificationRef.current.contains(event.target)) {
                setShowNotifications(false);
            }
        }

        if (showNotifications) {
            document.addEventListener("mousedown", handleClickOutside);
        } else {
            document.removeEventListener("mousedown", handleClickOutside);
        }

        return () => {
            document.removeEventListener("mousedown", handleClickOutside);
        };
    }, [showNotifications]);

    return (
        <div style={{ position: "relative" }}>
            <Button
                type="text"
                icon=
                {dueSoonTodos.length > 0 ? <BellTwoTone twoToneColor="#ff4d4f" style={{ fontSize: "30px" }} /> : <BellOutlined style={{ fontSize: "30px" }} />}
                
                onClick={() => setShowNotifications(prev => !prev)}
                style={{ display: "inline-flex", // 버튼 크기를 아이콘 크기에 맞춤
                    alignItems: "center",
                    justifyContent: "center",
                    padding: 0, // 버튼 내부 여백 제거
                    minWidth: "auto", // 버튼 크기를 최소화
                    width: "auto", // 버튼 크기를 아이콘 크기에 맞춤
                    height: "auto", // 높이도 자동 조정
                    border: "none", // 불필요한 테두리 제거
                    background: "transparent", // 배경 투명 처리
                    marginRight:"30px",
                    marginTop:"20px"
                    }}
            />

            {/* ✅ 기존 디자인 유지 */}
            {showNotifications && (
                <div
                    ref={notificationRef}
                    style={{
                        position: "absolute",
                        top: "40px",
                        right: "0px",
                        width: "300px",
                        background: "white",
                        border: "1px solid #ddd",
                        borderRadius: "8px",
                        padding: "10px",
                        boxShadow: "0 2px 5px rgba(0,0,0,0.2)",
                        zIndex: 1000, // ✅ 가장 앞에 표시
                    }}
                >
                    <List
                        dataSource={dueSoonTodos}
                        renderItem={todo => (
                            <List.Item>
                                📅 {todo.dueDate} - <b>{todo.title}</b>
                            </List.Item>
                        )}
                    />
                </div>
            )}
        </div>
    );
}

export default NotificationBell;
