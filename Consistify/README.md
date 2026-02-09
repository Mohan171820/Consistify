# Consistify – Practice Tracking API
### Skill Decay & Learning Consistency Tracker (Consistify)

Consistify is a **backend-driven system built with Spring Boot** to help users track their skills, maintain learning streaks, and prevent **skill decay** by monitoring practice consistency and identifying at-risk skills.

The project emphasizes **clean architecture, scalability, and extensibility**, with a strong foundation for future **AI-driven learning insights**.

---

## 🚀 Project Overview

The Consistify system allows users to:

- Register and manage multiple skills
- Log practice sessions for each skill
- Monitor learning consistency and inactivity
- Detect skills that are at risk of decay
- Receive insights into learning behavior

The system is designed to **encourage continuous learning** and **avoid long gaps between practice sessions**.

---

## ✨ Features

- Log daily practice sessions for skills
- Validate practice entries for active skills only
- Prevent duplicate practice entries for the same skill on the same day
- Clean **DTO-based API design**
- Automated **Entity ↔ DTO mapping** using MapStruct
- Track learning time using **YouTube video URLs** to calculate time spent learning a skill

---

## 🛠️ Tech Stack

- Java 17+
- Spring Boot 3.x
- Spring Data JPA
- MapStruct
- Lombok
- H2 / PostgreSQL
- Maven

---

## 🧱 Architecture Overview

The application follows a **standard layered architecture**:


### Key Layers

#### Controller Layer
- Handles REST APIs
- Uses `ResponseEntity` for clean and consistent responses

#### Service Layer
- Contains core business logic
- Calculates learning consistency and skill decay

#### Repository Layer
- JPA repositories for database access

#### DTO Layer
- Clean data transfer between client and server

#### Validation
- Ensures reliable and safe input handling

---

## 📁 Project Structure

├── .idea/
│
├── Consistify/
│ ├── .mvn/
│ │
│ ├── src/
│ │ ├── main/
│ │ │ ├── java/
│ │ │ │ └── com.example.Consistify/
│ │ │ │ ├── Config/
│ │ │ │ ├── Controller/
│ │ │ │ ├── DTO/
│ │ │ │ ├── Entity/
│ │ │ │ ├── ExceptionHandler/
│ │ │ │ ├── GraphQL/
│ │ │ │ ├── Mapper/
│ │ │ │ ├── Repo/
│ │ │ │ ├── Service/
│ │ │ │ ├── util/
│ │ │ │ └── ConsistifyApplication.java
│ │ │
│ │ └── resources/
│ │ ├── graphql/
│ │ │ └── schema.graphqls
│ │ └── application.properties
│ │
│ └── test/
│
├── target/
│
├── .gitattributes
├── .gitignore
├── HELP.md
├── mvnw
├── mvnw.cmd
└── pom.xml

 --- 
## 🔧 Tools & Workflow

- **Backend:** Spring Boot (REST APIs, DTOs, services, repositories)
- **Frontend:** HTML, CSS, JavaScript (AI-assisted UI scaffolding)
- **Database:** H2 / PostgreSQL

### Focus Areas
- Backend architecture & business logic
- API design and data consistency
- Frontend–backend integration
- Learning analytics & streak tracking

---

## ⭐ Core Features

### 🔹 Skill Management
- Add, update, and delete skills
- Track each skill independently

### 🔹 Practice Session Tracking
- Log practice dates and durations
- Link practice sessions to skills

### 🔹 Learning Consistency
- Detect irregular practice patterns
- Identify inactive learning periods

### 🔹 Skill Decay Detection
- Flags skills that haven’t been practiced recently
- Helps users avoid losing proficiency

### 🔹 Clean API Responses
- Uses `ResponseEntity`
- Meaningful HTTP status codes

---

## 🧠 How Skill Decay Is Handled

Currently, the system:

- Checks the **last practice date** for each skill
- Marks skills as **at-risk** after a configurable inactivity threshold
- Helps users refocus on neglected skills

All decay logic is handled in the **service layer**, making it easy to improve or replace later.

---

## 📺 YouTube-Based Learning Tracking

With the latest update:
- Learning streaks and productivity are calculated using **time spent watching YouTube videos**
- Users can attach YouTube URLs to skills to track learning duration automatically

---

## 🤖 Future Integration: Spring AI

The project is designed to support **AI-powered intelligence** using **Spring AI** in future versions.

### Why Spring AI?
Spring AI allows easy integration with:
- Local LLMs (Ollama, LM Studio)
- External AI services

All without tightly coupling AI logic into the core system.

---

## 📌 Status

🚧 Actively under development  
Built with scalability and enterprise-grade architecture in mind.
