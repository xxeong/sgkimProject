import { Button, Input, DatePicker, Switch } from "antd";
import { SwapOutlined } from "@ant-design/icons";

export default function TodoActions({ todoTitle, setTodoTitle, dueDate, setDueDate, addTodo, sortOrder, toggleSort, showCompleted, toggleShowCompleted }) {
    return (
        <div style={{ display: "flex", alignItems: "center", gap: "8px" }}>
            <Input
                placeholder="할 일을 입력하세요."
                value={todoTitle}
                onChange={(e) => setTodoTitle(e.target.value)}
                onKeyDown={(e) => e.key === "Enter" && addTodo()}
            />
            <DatePicker value={dueDate} onChange={setDueDate} format="YYYY-MM-DD" />
            <Button type="primary" onClick={addTodo}>추가</Button>
            <Button type="default" onClick={toggleSort} icon={<SwapOutlined />}>
                {sortOrder === "asc" ? "오름차순" : "내림차순"}
            </Button>
            <Switch checked={showCompleted} onChange={toggleShowCompleted} checkedChildren="완료 보이기" unCheckedChildren="완료 숨기기" />
        </div>
    );
}
