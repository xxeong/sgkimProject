import { useState } from "react";
import { List, Button, Input, DatePicker, Switch } from "antd";
import { SendOutlined, SwapOutlined } from "@ant-design/icons";
import useTodos from "../../hooks/useTodos";
import TodoItem from "./TodoItem";
import api from "../../api";
import TodoShareModal from "./TodoShareModal";

export default function TodoList({ setDueSoonTodos }) {
    const { getTodos, sortOrder, setSortOrder, showCompleted, setShowCompleted, filteredTodos } = useTodos(setDueSoonTodos);
    const [todoTitle, setTodoTitle] = useState("");
    const [dueDate, setDueDate] = useState(null);
    const [isShareModalVisible, setIsShareModalVisible] = useState(false);

    const addTodo = async () => {
        if (!todoTitle.trim()) return;
        await api.post("/todo", { title: todoTitle, dueDate: dueDate ? dueDate.format("YYYY-MM-DD") : null, status: "N" });
        getTodos();
        setTodoTitle("");
        setDueDate(null);
    };

    return (
        <div>
            <div style={{ display: "flex", alignItems: "center", gap: "8px", marginBottom: "10px" }}>
                <Input
                    placeholder="할 일을 입력하세요."
                    value={todoTitle}
                    onChange={(e) => setTodoTitle(e.target.value)}
                    onKeyDown={(e) => e.key === "Enter" && addTodo()}
                    style={{ width: "300px" }}
                />
                <DatePicker value={dueDate} onChange={setDueDate} format="YYYY-MM-DD" style={{ width: "150px" }} />
                <Button type="primary" onClick={addTodo}>추가</Button>

                {/* ✅ 정렬 토글 버튼 */}
                <Button type="default" onClick={() => setSortOrder(prev => prev === "asc" ? "desc" : "asc")} icon={<SwapOutlined />}>
                    {sortOrder === "asc" ? "오름차순" : "내림차순"}
                </Button>

                {/* ✅ 완료된 항목 보이기/숨기기 */}
                <Switch
                    checked={showCompleted}
                    onChange={() => setShowCompleted(prev => !prev)}
                    checkedChildren="완료 보이기"
                    unCheckedChildren="완료 숨기기"
                />
                {/* ✅ 공유 버튼 추가 */}
                <Button
                    type="primary"
                    onClick={() => setIsShareModalVisible(true)}
                    style={{ marginBottom: "10px", marginLeft: "auto" }} // ✅ 오른쪽으로 정렬
                    icon={<SendOutlined />}
                >
                    공유
                </Button>

            </div>

            <div>


                {/* ✅ 공유 모달 */}
                <TodoShareModal
                    todos={filteredTodos}
                    visible={isShareModalVisible}
                    onClose={() => setIsShareModalVisible(false)}
                />
            </div>
            {/* ✅ 필터링된 리스트 출력 */}
            <List
                dataSource={filteredTodos}
                renderItem={(todo) => <TodoItem key={todo.id} todo={todo} getTodos={getTodos} />}
            />
        </div>
    );
}
