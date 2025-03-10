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
(1, 'React 공부하기', DATE_SUB(CURDATE(), INTERVAL 1 DAY), 'N'),
(2, 'DB 설계 검토', CURDATE(), 'N'),
(3, '백엔드 API 구현', DATE_ADD(CURDATE(), INTERVAL 1 DAY), 'N'),
(4, 'JPA 테스트 코드 작성', DATE_ADD(CURDATE(), INTERVAL 2 DAY), 'N'),
(5, 'Ant Design UI 디자인', DATE_SUB(CURDATE(), INTERVAL 3 DAY), 'N');

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

INSERT INTO `event` (title, description, start_date_time, end_date_time, location)
VALUES
('팀 회의', '프로젝트 진행 상황 공유', DATE_SUB(NOW(), INTERVAL 1 DAY), DATE_SUB(NOW(), INTERVAL 1 DAY) + INTERVAL 2 HOUR, '사무실'),
('프로젝트 미팅', 'Spring Boot와 JPA 미팅', NOW(), NOW() + INTERVAL 2 HOUR, '온라인 Zoom'),
('개발 스프린트', '프론트엔드 개발 집중 작업', DATE_ADD(NOW(), INTERVAL 3 DAY), DATE_ADD(NOW(), INTERVAL 3 DAY) + INTERVAL 4 HOUR, '본사 2층 회의실'),
('데이터베이스 점검', '서버 성능 최적화 및 점검', DATE_SUB(NOW(), INTERVAL 2 DAY), DATE_SUB(NOW(), INTERVAL 2 DAY) + INTERVAL 3 HOUR, '본사 1층');
