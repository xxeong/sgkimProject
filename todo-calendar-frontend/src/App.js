import { Tabs, Card } from "antd";
import { useRef, useState, useEffect } from "react";
import TodoList from "./components/todo/TodoList";
// import TodoCalendar from "./components/Calendar";
import NotificationBell from "./components/ui/NotificationBell";
import { TodoProvider, useTodoContext } from "./context/TodoContext"; // ✅ TodoProvider 추가
import { CheckSquareOutlined, ProfileOutlined } from "@ant-design/icons";

export default function App() {
  const [showNotifications, setShowNotifications] = useState(false);
  const { setDueSoonTodos } = useTodoContext();
  const notificationRef = useRef(null); // ✅ 알림창 감지용 ref

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

// ✅ 페이지 로드 시 `localStorage`에서 저장된 알림 데이터를 가져오기
useEffect(() => {
  const updateDueSoonTodos = () => {
    const savedNotifications = JSON.parse(localStorage.getItem("dueSoonTodos")) || [];
    setDueSoonTodos(savedNotifications);
  };

  // ✅ `storage` 이벤트 리스너 추가 (다른 컴포넌트에서 변경되면 자동 반영)
  window.addEventListener("storage", updateDueSoonTodos);
  updateDueSoonTodos();

  return () => {
    window.removeEventListener("storage", updateDueSoonTodos);
  };
}, [setDueSoonTodos]);

  return (
    <TodoProvider> {/* ✅ TodoProvider로 감싸줌 */}
    <div className="p-4">
      {/* 할일 알람 아이콘 및 알림창 */}
      <div style={{ display: "flex", justifyContent: "space-between", alignItems: "center", position: "relative" }}>
        {/* 🏷️ 탭 메뉴 */}
        <h1 style={{ marginLeft: "10px" }}> <ProfileOutlined /> 할 일</h1>
        <NotificationBell /> {/* ✅ 별도의 알림 컴포넌트 사용 */}
      </div>
      <Tabs
        defaultActiveKey="todo"
        items={[
          {
            key: "todo",
            label: (
              <span style={{ paddingLeft: "10px", display: "flex", alignItems: "center" }}>
                <CheckSquareOutlined style={{ marginRight: "5px" }} />
                To-Do 목록
              </span>
            ),
            children: (
              <Card>
                <TodoList setDueSoonTodos={setDueSoonTodos} /> {/* ✅ 직접 넘김 */}
              </Card>
            ),
          },
          // {
          //   key: "calendar",
          //   label: "📅 캘린더",
          //   children: (
          //     <Card title="📅 일정 캘린더">
          //       <TodoCalendar />
          //     </Card>
          //   ),
          // },
        ]}
      />

    </div>
    </TodoProvider>
  );
}
