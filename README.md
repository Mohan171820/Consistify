# Consistify

> A productivity tracking platform designed to help users maintain consistent learning and skill development through honest, distraction-free time tracking.

##  Overview

Consistify is a unique productivity application that goes beyond traditional time tracking by focusing on authentic learning progress and skill maintenance. The platform prevents fake logging, tracks skill decay, and provides a distraction-free environment for focused learning.

##  Key Features

### 1. **Distraction-Free YouTube Learning**
- Embed YouTube educational videos directly within the platform
- Automatic time tracking for video-based learning
- Eliminates distractions from recommendations, comments, and unrelated content
- Builds a comprehensive learning history based on actual watch time

### 2. **Multi-Platform Practice Timer**
- Integrated timer for popular coding and learning platforms:
  - LeetCode
  - HackerRank
  - CodeChef
  - Codeforces
  - And more...
- Track time spent practicing across different platforms in one centralized location
- Start/stop/pause functionality with accurate time logging

### 3. **Manual Skill Logging**
- Log daily practice sessions with detailed metadata:
  - **Skill Name**: What you're learning/practicing
  - **Skill Level**: Current proficiency
  - **Effort Intensity**: High, Medium, or Low
  - **Decay Days**: Track time since last practice
- One entry per skill per day limit to ensure data integrity

### 4. **One Entry Per Day Rule**
- Users can only log once per day for each skill
- Prevents fake logging and gaming the system
- Encourages honest self-assessment
- No editing of past entries to maintain accountability

### 5. **Streak System**
- Visual streak tracking to maintain motivation
- Multiple streak types:
  - Learning streaks (consecutive days of practice)
  - Logging streaks (daily entry consistency)
  - Skill maintenance streaks
- Milestone celebrations and progress visualization

### 6. **Skill Decay Tracking**
- Automatically tracks days since last practice
- Visual indicators for skills requiring attention
- Helps prioritize which skills need maintenance
- Prevents skill degradation through timely reminders

### 7. **History & Progress Tracking**
- Comprehensive history of all logged sessions
- View progress over time for each skill
- Analyze learning patterns and productivity trends
- Immutable history ensures data reliability

##  Upcoming Features

### AI-Powered Insights (Spring AI Integration)
- Personalized skill recommendations based on decay patterns
- Optimal learning schedule suggestions
- Pattern analysis: "You learn best on Tuesday mornings"
- Intelligent practice reminders
- Skill progression predictions

## 🛠️ Tech Stack

- **Backend**: Java, Spring Boot
- **Database**: [Your database choice]
- **API**: GraphQL
- **Build Tool**: Maven
- **Future Integration**: Spring AI

##  Project Structure

```
Consistify/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com.example.Consistify/
│   │   │       ├── Config/           # Application configuration
│   │   │       ├── Controller/       # REST/GraphQL controllers
│   │   │       ├── DTO/             # Data Transfer Objects
│   │   │       ├── Entity/          # JPA entities
│   │   │       ├── ExceptionHandler/ # Global exception handling
│   │   │       ├── GraphQL/         # GraphQL resolvers and schemas
│   │   │       ├── Mapper/          # Entity-DTO mappers
│   │   │       ├── Repo/            # Repository interfaces
│   │   │       ├── Service/         # Business logic
│   │   │       ├── util/            # Utility classes
│   │   │       └── ConsistifyApplication.java
│   │   └── resources/
│   └── test/                        # Unit and integration tests
├── docs/                            # Documentation
│   └── images/                      # Documentation images
├── target/                          # Build output
├── .gitattributes
├── .gitignore
├── HELP.md
├── mvnw                             # Maven wrapper (Unix)
├── mvnw.cmd                         # Maven wrapper (Windows)
├── pom.xml                          # Maven dependencies
└── README.md
```

## How It Works

### For YouTube Learning:
1. Paste a YouTube educational video URL
2. Video embeds directly in the platform
3. Start watching in a distraction-free environment
4. Time is automatically tracked
5. Session is logged against the relevant skill

### For Platform Practice:
1. Select your practice platform (e.g., LeetCode)
2. Choose the skill you're practicing
3. Start the timer
4. Practice on the actual platform
5. Stop the timer when done
6. Session is automatically logged

### For Manual Logging:
1. Select the skill you practiced
2. Indicate your current level
3. Choose effort intensity (High/Medium/Low)
4. Submit your daily log
5. Entry is locked and cannot be edited

##  Philosophy

Consistify is built on the principle of **authentic productivity tracking**. We believe that:

- **Honesty matters**: The one-entry-per-day rule and immutable history prevent self-deception
- **Skills decay**: Regular practice is essential, and tracking decay helps maintain proficiency
- **Distractions kill productivity**: Embedded learning environments keep you focused
- **Streaks motivate**: Visual progress tracking encourages consistency
- **Data-driven improvement**: Comprehensive history enables pattern recognition and optimization

## 🚦 Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- [Your database]

### Installation

1. Clone the repository:
```bash
git clone https://github.com/yourusername/Consistify.git
cd Consistify
```

2. Install dependencies:
```bash
./mvnw clean install
```

3. Configure your database in `application.properties`

4. Run the application:
```bash
./mvnw spring-boot:run
```

5. Access the application at `http://localhost:8080`

##  Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

##  Authors

M.Mohan Murali

##  Acknowledgments

Built with the goal of helping developers and learners maintain consistent skill development and honest productivity tracking.

---

**Consistify** - *Track honestly. Learn consistently. Grow continuously.*
