-- UTF-8로 강제 설정
SET NAMES utf8mb4;
SET CHARACTER SET utf8mb4;

--  데이터베이스 생성 (없으면 생성)
CREATE DATABASE IF NOT EXISTS planner_db 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

USE planner_db;

-- MySQL 사용자 생성 및 권한 부여
-- 사용자: admin / 비밀번호: qhdkscjfwj1!
CREATE USER IF NOT EXISTS 'admin'@'%' IDENTIFIED BY 'qhdkscjfwj1!';

-- planner_db에 대한 모든 권한 부여 (INSERT, UPDATE, DELETE, SELECT, CREATE, DROP 등)
GRANT ALL PRIVILEGES ON planner_db.* TO 'admin'@'%';

-- 변경 사항 적용
FLUSH PRIVILEGES;

-- 기존 테이블 삭제 (외래 키 문제 해결)
SET FOREIGN_KEY_CHECKS = 0; -- 외래 키 검사 비활성화
DROP TABLE IF EXISTS todo_descriptions;
DROP TABLE IF EXISTS todo;
DROP TABLE IF EXISTS `event`;

SET FOREIGN_KEY_CHECKS = 1; -- 외래 키 검사 다시 활성화

-- ToDo 테이블 생성
CREATE TABLE todo (
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    due_date DATE,  
    status VARCHAR(10) NOT NULL DEFAULT 'N',
    create_date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ToDo 테스트 데이터 삽입
INSERT INTO todo (id, title, due_date, status) VALUES
(1, 'React 공부하기', '2025-03-20', 'Y'),
(2, 'DB 설계 검토', '2025-03-21', 'N'),
(3, '백엔드 API 구현', '2025-03-22', 'Y'),
(4, 'JPA 테스트 코드 작성', '2025-03-23', 'Y'),
(5, 'Ant Design UI 디자인', '2025-03-24', 'N');

-- ToDo Descriptions 테이블 생성
CREATE TABLE todo_descriptions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    todo_id BIGINT NOT NULL,
    description VARCHAR(255),
    FOREIGN KEY (todo_id) REFERENCES todo(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ToDo Descriptions 테스트 데이터 삽입
INSERT INTO todo_descriptions (todo_id, description) VALUES
(1, 'React 프로젝트 기본 구조 설계'),
(1, '컴포넌트 분리 및 스타일링'),
(2, 'Spring Boot API 개발 - CRUD 구현'),
(2, 'Swagger 문서화 작업'),
(3, 'MySQL 데이터베이스 스키마 정의'),
(3, '기본 기초 데이터 INSERT문 작성'),
(4, 'JPA Repository 테스트 코드 작성'),
(5, 'Ant Design을 이용한 UI 디자인'),
(5, '테마 커스터마이징 적용');


CREATE TABLE `event` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `description` text,
  `start_date_time` timestamp NOT NULL,
  `end_date_time` timestamp NOT NULL,
  `location` varchar(255) DEFAULT NULL,
  `create_date_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;