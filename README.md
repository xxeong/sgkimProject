# sgkimProject

# 📅 할 일 관리 앱

이 프로젝트는 **할 일(To-Do) 관리 웹 애플리케이션**입니다.  
React.js(프론트엔드)와 Spring Boot(백엔드)를 사용하여 구현되었습니다.

---

## 🚀 프로젝트 개요
### 📌 주요 기능
- ✅ **할 일 관리**: To-Do 항목 추가, 수정, 삭제, 완료 처리
    - **할 일 메일 공유 기능**
      - Microsoft Graph API 이용
      - 백엔드의 ".env 및 application.properties"에서 메일 발송 정보 관리
      - 현재 메일 샌더는 설정 테넌트에 존재하는 사용자만 가능<br>(application.properties에 본인 계정으로 설정해놓았음).
- 🔔 **알림 기능**: 마감 임박 및 지연된 항목 표시
- 📌 **정렬 및 필터링**: 완료 여부, 마감 기한별 정렬 가능
- 📄 **API 명세**: Swagger UI 지원

---

## 🛠️ 기술 스택
### 🔹 **프론트엔드**
```
React.js	        18.3.1  -	SPA (Single Page Application)
Ant Design	        5.24.2  -	UI 라이브러리 (디자인 컴포넌트)
Axios	                1.8.1   -	API 요청 처리 (RESTful API 통신)
Day.js	                1.11.13 -	날짜 및 시간 처리
```
### 🔹 **백엔드**
```
Spring Boot	        3.4.3 -	Java 기반 백엔드 프레임워크
Spring Web	              -	REST API 개발 지원
Spring Data JPA	              -	ORM (객체-관계 매핑)
MySQL	                8.0.33- 관계형 데이터베이스
Lombok	                      -	코드 간소화 (Getter, Setter 자동 생성)
ModelMapper	        3.1.1 -	DTO ↔ Entity 변환 라이브러리
SpringDoc OpenAPI	2.7.0 -	Swagger API 문서화
Spring Boot DevTools	      -	개발 편의성 제공 (자동 리로드)
Java-Dotenv             5.2.2 - `.env` 파일을 이용한 환경 변수 관리
```

---
## 실행 가이드

### 실행 전 필수 설치
**- Java 17 (Spring Boot 실행을 위해 필요)**<br>
**- Gradle (Spring Boot 프로젝트 빌드를 위해 필요)**<br>
**- MySQL 8.0 (데이터베이스 사용을 위해 필요)**<br>
**- Node.js (React 프로젝트 실행을 위해 필요)**<br>
**- git (git clone을 위해 필요)**

### 1. git clone
**1. 깃허브에서 프로젝트 파일 다운로드**
**프로젝트를 받을 폴더로 이동**
```
git clone https://github.com/xxeong/sgkimProject.git
```

### 2. 데이터베이스 (MySQL) 설정<br>

**(명령 프롬프트 실행)**

**1. MySQL이 설치된 경로로 이동**

```
cd c:Program Files\MySQL Server 8.0
or
cd c:Program Files\MySQL Server 8.0\bin
```
**(MySQL이 설치된 경로에 따라 다를 수 있음)**<br>

**2. DB.sql 파일 실행(초기 데이터포함)**<br>
**docs/DB.sql 파일 실행** 
```
mysql -u root -p < "/파일이_저장된_경로/docs/DB.sql"
```
<br>

**3. SpringBoot의 application.properties의 # MySQL 연결 정보 확인.**
```
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/planner_db?serverTimezone=Asia/Seoul&characterEncoding=UTF-8
spring.datasource.username=admin
spring.datasource.password=qhdkscjfwj1!
```
### 3. 백엔드 
**📌 [⚠️ 필수] .env 파일 설정**

**todo-calendar-backend/ 폴더 내 .env 파일을 생성해야 함.**

**.env 파일에 메일 전송 관련 필수값 존재(AZURE_CLIENT_SECRET)**
<br>



**(명령프롬프트 실행)**<br>
**1. backend폴더로 이동**
```
cd ./todo-calendar-backend
```
**2. Gradle 빌드**

```
gradlew clean build
```
**3. jar 실행**
```
cd .\build\libs
java -jar todo-calendar-backend-0.0.1-SNAPSHOT.jar
```
### 4. 프론트엔드
**1. 프론트엔드 폴더로 이동**<br>
<br>

**명령프롬프트 실행**
```
cd ./todo-calendar-frontend
```
**2. 패키지 설치**
```
npm install
```
**3. 서버 실행**
```
npm start
```
---

# API명세
**Swagger API 문서**: http://localhost:8080/swagger-ui.html

**테스트케이스는 rest파일로 작성**<br>
**sgkimProject/todo-calendar-backend 경로의 api.rest**


---
# 주요 컴포넌트 설명

## TodoItem.js
- **역할**: 개별 할 일(Todo) 항목을 표시 및 관리
- **사용 기능**:
  - 완료 여부 체크 (`Checkbox`)
  - 제목 및 마감일 수정 (`Input`, `DatePicker`)
  - 삭제 기능 (`DeleteOutlined`)
  - 설명 추가/수정/삭제 (`Modal`, `List`)
  - 마감일 강조 (`dayjs` 활용)


## TodoActions.js
- **역할**: 할 일 추가 및 정렬, 완료 표시 컨트롤
- **사용 기능**:
  - 새 할 일 추가 (`Input`, `Button`)
  - 마감일 선택 (`DatePicker`)
  - 오름/내림차순 정렬 (`SwapOutlined`)
  - 완료된 항목 표시/숨김 (`Switch`)


## TodoList.js
- **역할**: 할 일 목록 렌더링 및 관리
- **사용 기능**:
  - `useTodos` 훅을 이용해 목록 가져오기
  - 필터링 및 정렬 (`sortOrder`, `showCompleted`)
  - 할 일 공유 (`TodoShareModal` 연결)


## TodoShareModal.js
- **역할**: 할 일을 이메일로 공유하는 모달
- **사용 기능**:
  - 공유할 할 일 선택 (`Checkbox`, `List`)
  - 이메일 입력 (`Input`)
  - 선택한 할 일 공유 (`API 요청`)


## NotificationBell.js
- **역할**: 마감이 임박한 할 일 알림 기능
- **사용 기능**:
  - `dueSoonTodos` 상태를 기반으로 알림 표시
  - 클릭 시 알림 목록 표시 (`List`)
  - 알림 아이콘 (`BellOutlined`, `BellTwoTone`) 상태 변경
  - 외부 클릭 시 알림 닫기 (`useEffect`, `useRef` 활용)


## TodoContext.js
- **역할**: 전역 상태 관리 (마감 임박 할 일 목록)
- **사용 기능**:
  - `dueSoonTodos` 상태를 관리 (`useState`)
  - `localStorage`와 `storage` 이벤트를 활용한 상태 동기화
  - `useTodoContext` 훅을 제공하여 전역 상태 접근 가능


## useTodos.js
- **역할**: 할 일 데이터를 관리하는 커스텀 훅
- **사용 기능**:
  - 할 일 목록 가져오기 (`getTodos`)
  - 정렬 및 완료된 항목 필터링 (`sortOrder`, `showCompleted`)
  - 마감 하루 전 할 일 감지 및 알림 (`message.warning`)
  - `localStorage`를 이용한 알림 중복 방지


## api.js
- **역할**: 백엔드 API 요청을 처리하는 모듈
- **사용 기능**:
  - `axios.create()`를 이용해 API 기본 설정 (`baseURL`, `timeout`, `headers`)
  - 모든 API 요청 (`GET`, `POST`, `PUT`, `PATCH`, `DELETE`)을 처리


## App.js
- **역할**: 애플리케이션의 주요 구조 및 상태 관리
- **사용 기능**:
  - `TodoProvider`를 사용하여 전역 상태 관리
  - `Tabs`를 사용한 할 일 목록 UI 구성
  - `NotificationBell`을 활용한 알림 기능
  - `useEffect`를 활용하여 `localStorage`에서 알림 데이터 불러오기

---

## 주요 라이브러리 사용
- `React` → 상태 및 UI 관리 (`useState`, `useEffect`, `useContext`, `useRef` 등)
- `Ant Design` → UI 컴포넌트 (`Button`, `Input`, `Modal`, `List`, `Tabs` 등)
- `axios` → 백엔드 API 요청 처리 (`GET`, `POST`, `PUT`, `PATCH`, `DELETE`)
- `dayjs` → 날짜 계산 및 마감일 강조 (`startOf("day")`, `diff()`)

---
<details>
  <summary>📂 프로젝트 구조 보기</summary>

```
📦 sgkimProject
├── 📂 todo-calendar-frontend   # React.js (프론트엔드)
│   ├── 📂 public/              # 정적 파일 (HTML, Favicon 등)
│   ├── 📂 src/                 # 소스 코드
│   │   ├── 📂 components/      # 재사용 가능한 UI 컴포넌트
│   │   ├── 📂 context/         # 유틸리티 함수
│   │   ├── 📂 hooks/           # 유틸리티 함수
│   │   ├── 📜 App.js           # 메인 컴포넌트
│   │   ├── 📜 api.js           # axios baseURL 설정
│   │   ├── 📜 index.js         # 엔트리 포인트
│   ├── 📜 package.json         # 프로젝트 의존성 목록
│   ├── 📜 package-lock.json    # 프로젝트 패키지 의존성 고정 목록(버전)
│   ├── 📜 README.md            # 프론트엔드 설명 문서
│   ├── 📜 .env                 # 환경변수 관리
│
├── 📂 todo-calendar-backend  # Spring Boot (백엔드)
│   ├── 📂 src/main/java/com/sgkim/todocalendar/todo_calendar-backend
│   │   ├── 📂 common/          # REST API 컨트롤러
│   │   │   ├── 📂 config/      # 설정 관련 클래스
│   │   ├── 📂 controller/      # REST API 컨트롤러
│   │   ├── 📂 exception/       # 예외 처리 
│   │   ├── 📂 service/         # 비즈니스 로직
│   │   ├── 📂 model/           # 엔티티 및 DTO
│   │   │   ├── 📂 dto/         # API 요청/응답을 위한 객체 (데이터 전송 전용)
│   │   │   ├── 📂 entity/      # 데이터베이스에 저장되는 객체 (JPA 매핑)
│   │   ├── 📂 repository/      # 데이터 접근 계층 (JPA)
│   ├── 📂 src/main/resources/
│   │   ├── 📜 application.properties  # 데이터베이스 및 서버 설정 등
│   ├── 📂 src/test/java/       # 테스트 코드
│   ├── 📜 .env                 # 환경변수 관리
│   ├── 📜 build.gradle         # 백엔드 프로젝트 의존성 목록
│   ├── 📜 README.md            # 백엔드 설명 문서
│   ├── 📜 api.rest             # api테스트용 rest파일
│
├── 📂 docs  # 문서화
│   ├── 📜 DB.sql               # 백엔드 필수 생성 sql
│
└── 📜 .gitignore # Git 버전 관리에서 제외할 파일 목록
└── 📜 README.md  # 최상위 프로젝트 설명 문서
```
</details>
