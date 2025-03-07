import { useState, useRef, useEffect, useCallback } from "react";
import { Checkbox, Input, DatePicker, Button, Typography, message, Modal, List } from "antd";
import { EditOutlined, DeleteOutlined, ExclamationCircleOutlined, ClockCircleOutlined } from "@ant-design/icons";
import dayjs from "dayjs";
import api from "../../api";

const { Text } = Typography;

export default function TodoItem({ todo, getTodos }) {
    const [editing, setEditing] = useState(false);
    const [editingText, setEditingText] = useState(todo.title);
    const [editingDate, setEditingDate] = useState(todo.dueDate ? dayjs(todo.dueDate) : null);
    const [isDatePickerOpen, setIsDatePickerOpen] = useState(false);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [descriptions, setDescriptions] = useState([]);
    const [newDescription, setNewDescription] = useState("");
    const [editingDescId, setEditingDescId] = useState(null);
    const [editingDescText, setEditingDescText] = useState("");
    const editRef = useRef(null);
    const inputRef = useRef(null);
    const modalInputRef = useRef(null); // ✅ 모달 입력창용 ref 추가

    const patchStatus = async () => {
        try {
            const updatedStatus = todo.status === "Y" ? "N" : "Y";
            await api.patch(`/todo/${todo.id}`, {
                status: updatedStatus
            });

            getTodos();
        } catch (error) {
            console.error("상태 업데이트 실패:", error);
        }
    };

    const saveEdit = async () => {
        if (!editingText.trim()) {
            message.error("할 일을 입력하세요!");
            return;
        }
        try {
            await api.put(`/todo/${todo.id}`, {
                title: editingText,
                dueDate: editingDate ? editingDate.format("YYYY-MM-DD") : null,
                status: todo.status,
            });
            setEditing(false);
            getTodos();
        } catch (error) {
            console.error("수정 실패:", error);
        }
    };

    const cancelEdit = useCallback(() => {
        setEditing(false);
        setEditingText(todo.title);
        setEditingDate(todo.dueDate ? dayjs(todo.dueDate) : null);
        setIsDatePickerOpen(false);
    }, [todo.title, todo.dueDate]);

    const deleteTodo = async () => {
        try {
            await api.delete(`/todo/${todo.id}`);
            getTodos();
        } catch (error) {
            console.error("삭제 실패:", error);
        }
    };

    const fetchDescriptions = async () => {
        try {
            const { data } = await api.get(`/todo/descriptions/${todo.id}`);
            setDescriptions(data);
        } catch (error) {
            console.error("설명 목록 불러오기 실패:", error);
        }
    };

    const handleAddDescription = async () => {
        if (!newDescription.trim()) return;
        try {
            const { data } = await api.post(`/todo/descriptions/${todo.id}`, { description: newDescription });
            setDescriptions([...descriptions, data]);
            setNewDescription("");
            if (modalInputRef.current) {
                modalInputRef.current.focus();
            }

        } catch (error) {
            console.error("설명 추가 실패:", error);
        }
    };

    const startEditingDescription = (id, content) => {
        setEditingDescId(id);
        setEditingDescText(content);
    };

    const handleUpdateDescription = async () => {
        if (!editingDescText.trim() || editingDescId === null) return;
        try {
            const { data } = await api.put(`/todo/descriptions/${editingDescId}`, { description: editingDescText });
            setDescriptions(descriptions.map(desc => (desc.id === editingDescId ? data : desc)));
            setEditingDescId(null);
            setEditingDescText("");
        } catch (error) {
            console.error("설명 수정 실패:", error);
        }
    };

    const handleDeleteDescription = async (id) => {
        try {
            await api.delete(`/todo/descriptions/${id}`);
            setDescriptions(descriptions.filter(desc => desc.id !== id));
        } catch (error) {
            console.error("설명 삭제 실패:", error);
        }
    };


    const handleKeyDown = (e) => {
        if (e.key === "Enter") {
            saveEdit();
        } else if (e.key === "Escape") {
            cancelEdit();
        }
    };

    useEffect(() => {
        if (editing) {
            inputRef.current?.focus();
        }
    }, [editing]);

    useEffect(() => {
        const handleClickOutside = (event) => {
            if (
                editRef.current && !editRef.current.contains(event.target) &&
                !event.target.closest(".custom-datepicker-popup") &&
                !isDatePickerOpen
            ) {
                cancelEdit();
            }
        };

        if (editing) {
            document.addEventListener("mousedown", handleClickOutside);
        }

        return () => {
            document.removeEventListener("mousedown", handleClickOutside);
        };
    }, [editing, isDatePickerOpen, cancelEdit]);

    const showModal = () => {
        setIsModalOpen(true);
        fetchDescriptions();
    };

    const handleCancel = () => {
        setIsModalOpen(false);
    };

    const isDueToday = todo.dueDate && todo.status === "N" &&
        dayjs(todo.dueDate).startOf("day").diff(dayjs().startOf("day"), "day") === 0;
    const isDueSoon = todo.dueDate && todo.status === "N" &&
        dayjs(todo.dueDate).startOf("day").diff(dayjs().startOf("day"), "day") === 1;

    const isDueAfter = todo.dueDate && todo.status === "N" &&
        dayjs(todo.dueDate).startOf("day").diff(dayjs().startOf("day"), "day") === -1;


    return (
        <div
            style={{
                display: "flex",
                flexDirection: "column",
                padding: "10px",
                borderRadius: "8px",
                border: isDueSoon ? "2px solid #ffc107" : isDueAfter ? "2px solid #dc3545" : "1px solid #f0f0f0",
                backgroundColor: todo.status === "Y" ? "#f6f6f6" : isDueAfter ? "#f8d7da" : isDueSoon ? "#fff3cd" : "#fff",
            }}
            ref={editRef}
        >
            <div style={{ display: "flex", alignItems: "center", justifyContent: "space-between" }}>
                <Checkbox checked={todo.status === "Y"} onChange={patchStatus} />

                <div style={{ flex: 1, marginLeft: "10px" }}>
                    {editing ? (
                        <div>
                            <Input
                                ref={inputRef}
                                value={editingText}
                                onChange={(e) => setEditingText(e.target.value)}
                                onKeyDown={handleKeyDown}
                                style={{ width: "70%" }}
                            />
                            <DatePicker
                                value={editingDate}
                                onChange={setEditingDate}
                                format="YYYY-MM-DD"
                                open={isDatePickerOpen}
                                onOpenChange={(open) => setIsDatePickerOpen(open)}
                                popupClassName="custom-datepicker-popup"
                                style={{ width: "150px", marginLeft: "10px" }}
                            />
                            <Button type="primary" onClick={saveEdit} style={{ marginLeft: "10px" }}>저장</Button>
                        </div>
                    ) : (
                        <div onDoubleClick={showModal}>
                            <Text
                                style={{
                                    fontSize: "16px",
                                    fontWeight: "bold",
                                    textDecoration: todo.status === "Y" ? "line-through" : "none",
                                    color: todo.status === "Y" ? "#aaa" : "#333",
                                    cursor: "pointer"
                                }}
                            >
                                {isDueSoon && <ClockCircleOutlined style={{ color: "#ffc107", marginRight: "5px" }} />}
                                {isDueAfter && <ExclamationCircleOutlined style={{ color: "#dc3545", marginRight: "5px" }} />}
                                {todo.title}
                            </Text>
                            {isDueToday && (
                                <Text style={{ color: "#fd7e14", fontSize: "14px", marginLeft: "10px", fontWeight: "bold" }}>
                                    (마감일 당일 입니다.)
                                </Text>
                            )}
                            {isDueSoon && (
                                <Text style={{ color: "#ffc107", fontSize: "14px", marginLeft: "10px", fontWeight: "bold" }}>
                                    (마감일 하루전 입니다.)
                                </Text>
                            )}
                            {isDueAfter && (
                                <Text style={{ color: "#dc3545", fontSize: "14px", marginLeft: "10px", fontWeight: "bold" }}>
                                    (마감일이 지났습니다)
                                </Text>
                            )}

                        </div>
                    )}
                </div>

                <Button type="text" icon={<EditOutlined />} onClick={() => setEditing(true)} />
                <Button type="text" icon={<DeleteOutlined />} onClick={deleteTodo} danger />
            </div>

            {!editing ? (
                <Text
                    style={{
                        fontSize: "12px",
                        color: "#666",
                        marginTop: "4px",
                        cursor: "pointer"
                    }}
                    onDoubleClick={() => setEditing(true)} // ✅ 날짜도 더블 클릭하면 수정 가능
                >
                    📅 {todo.dueDate || "마감일 없음"}
                </Text>
            ) : null}
            <Modal title="할 일 설명" open={isModalOpen} onCancel={() => {
                handleCancel(); 
                setEditingDescId(null);
                setEditingDescText("");
            }}
                footer={null}>
                <div style={{ minHeight: "400px", maxHeight: "400px", overflowY: "auto", display: "flex", flexDirection: "column" }}>
                    <List
                        dataSource={descriptions}
                        renderItem={desc => (
                            <List.Item
                                actions={[
                                    editingDescId === desc.id ? (
                                        <Button type="primary" onClick={handleUpdateDescription}>저장</Button>
                                    ) : (
                                        <Button type="link" onClick={() => startEditingDescription(desc.id, desc.description)}>수정</Button>
                                    ),
                                    <Button type="link" danger onClick={() => handleDeleteDescription(desc.id)}>삭제</Button>
                                ]}
                            >
                                {editingDescId === desc.id ? (
                                    <Input
                                        value={editingDescText}
                                        onChange={(e) => setEditingDescText(e.target.value)}
                                        autoFocus
                                    />
                                ) : (
                                    <Text>{desc.description || "설명 없음"}</Text>  // 받아온 설명 표시
                                )}
                            </List.Item>
                        )}
                    />
                </div>
                <Input
                    ref={modalInputRef}
                    value={newDescription}
                    onChange={(e) => setNewDescription(e.target.value)}
                    placeholder="새로운 설명 입력"
                    onPressEnter={handleAddDescription} // ✅ 엔터 키로 추가 가능하게 설정
                />

                <Button type="primary" onClick={handleAddDescription} style={{ marginTop: "10px", width: "100%" }}>
                    추가
                </Button>
            </Modal>


        </div>

    );
}
