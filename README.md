# Quiz Application

This Quiz Application allows users to log in, participate in a quiz with random questions based on their selected category and difficulty, submit their answers, view their performance analytics, and see the leaderboard of top players. The application is built using Spring Boot, with H2 database integration for simplicity. The flow of the quiz includes login, question generation, answer submission, analytics, and a leaderboard.

## Assumptions
- **H2 Database** is used for simplicity.
- A single user is seeded for testing purposes.
- **Categories:** Users can select a category for the quiz, which could be pre-configured in the system (e.g., General Knowledge, Technology, Science).
- **Difficulty:** The quiz can be played at three different levels of difficulty (e.g., Easy, Medium, Hard).
- **Limit:** Users can set the number of questions they want to answer in a quiz.
- The **leaderboard** will display top users based on their quiz results.
- **Quiz Start Time:** The system tracks the start time for each quiz session.

## Features and API Endpoints

### User Login
**Endpoint:** `/api/quiz/login`
- A user logs in using their username and password.
- A simple session is created to maintain the logged-in state.
- After a successful login, the system stores the user's ID in a session map (`activeSessions`).

### Select Quiz Parameters
**Endpoint:** `/api/quiz/questions/random`
- The user selects the category, difficulty, and limit (number of questions).
- Random questions are fetched based on the selected parameters.
- Each question is stored in the session for tracking the quiz session.

### Start Quiz
**Endpoint:** `/api/quiz/quiz/start`
- The user clicks "Start Quiz" to begin the quiz.
- The system records the start time of the quiz.

### Answer Submission
**Endpoint:** `/api/quiz/questions/submit`
- The user answers the questions and submits them once they are done.
- The answers are evaluated, and the results are stored.

### Analytics
**Endpoint:** `/api/quiz/analytics`
- After submitting answers, the user can view their analytics, including:
  - Number of quizzes completed
  - Average time taken
  - Accuracy rate
  - Preferred category

### Leaderboard
**Endpoint:** `/api/quiz/leaderboard`
- Users can view the leaderboard, showing the top quiz performers.

### Session Management
Session attributes, such as `quizStartTime` and question IDs, are stored in HTTP session to keep track of the user's current quiz session and their progress.

## Sample Data (for Testing)
**Users:**
- `username: user1, password: password1`
- `username: user2, password: password2`

## Future Works

### Improved User Analytics
I will improve user analytics by adding more detailed insights. This includes:
- Tracking **time spent per question**, allowing analysis of how long users take to answer each question.
- Tracking **difficulty levels completed**, giving insights into user progress through harder questions.
- Providing **category-wise performance**, enabling users to assess their strengths across various topics.

To visualize this data, I will integrate **graphical representations** like pie charts and bar graphs using libraries such as Chart.js or Thymeleaf's native chart capabilities, making progress easier to digest.

### Progressive Quiz Mode
For a more dynamic experience, I will implement a **progressive quiz mode**, where users begin with easier questions and unlock harder ones as they improve. This will involve:
- Maintaining user performance data.
- Dynamically adjusting difficulty levels based on quiz results.
- Providing achievements such as "Completed 10 Quizzes" or "Accuracy Above 90%" to motivate users. These achievements will be stored in the database and linked to user profiles for gamification.

### Real-Time Leaderboard
To foster competition, I will introduce a **real-time leaderboard**, allowing users to:
- Immediately see their rank and compare performance with others after completing a quiz.
- Use WebSocket for dynamic updates to reflect the latest quiz results and scores in real time.

### Multiplayer/Challenge Mode
I will add a **multiplayer/challenge mode**, enabling users to:
- Invite friends or other players for head-to-head quiz challenges.
- See real-time results and compare answers as they progress.
- Set up multiplayer game sessions with real-time communication and invitation links via email or unique codes.

---
By implementing these features, this application will provide a more interactive, rewarding, and competitive experience for users.
