This Quiz Application allows users to log in, participate in a quiz with random questions based on their selected category and difficulty, submit their answers, view their performance analytics, and see the leaderboard of top players. The application is built using Spring Boot, with H2 database integration for simplicity. The flow of the quiz includes login, question generation, answer submission, analytics, and a leaderboard.

Assumptions
H2 Database is used for simplicity.
A single user is seeded for testing purposes.
Categories: Users can select a category for the quiz, which could be pre-configured in the system (e.g., General Knowledge, Technology, Science).
Difficulty: The quiz can be played at three different levels of difficulty (e.g., Easy, Medium, Hard).
Limit: Users can set the number of questions they want to answer in a quiz.
The leaderboard will display top users based on their quiz results.
Quiz Start Time: The system tracks the start time for each quiz session.

User Login:
/api/quiz/login
  A user logs in using their username and password.
  A simple session is created to maintain the logged-in state.
  After a successful login, the system stores the user's ID in a session map (activeSessions).

Select Quiz Parameters:
/api/quiz/questions/random
  The user will select the category, difficulty, and limit (number of questions).
  Random questions are fetched based on the selected parameters.
  Each question is stored in the session for tracking the quiz session.

Start Quiz:
/api/quiz/quiz/start
  The user clicks "Start Quiz" to begin the quiz. The system records the start time of the quiz.

Answer Submission:
/api/quiz/questions/submit
  The user answers the questions and submits them once they are done.
  The answers are evaluated, and the results are stored.

Analytics:
/api/quiz/analytics
  After submitting answers, the user can view their analytics, including:
  Number of quizzes completed
  Average time taken
  Accuracy rate
  Preferred category

Leaderboard:
/api/quiz/leaderboard
  Users can view the leaderboard, showing the top quiz performers.

Session Management:  Session attributes, such as quizStartTime and question IDs, are stored in HTTP session to keep track of the user's current quiz session and their progress

Sample Data (for Testing)
Users:
username: user1, password: password1
username: user2, password: password2

Future Works:
I will improve user analytics by adding more detailed insights. This includes tracking time spent per question, which will allow me to analyze how long users take to answer each question. I will also track difficulty levels completed, giving insights into user progress through harder questions, and provide category-wise performance to allow users to assess their strengths across various topics. To visualize this data, I will integrate graphical representations like pie charts and bar graphs using libraries such as Chart.js or Thymeleaf's native chart capabilities, enabling users to easily digest their progress over time.

For a more dynamic experience, I will implement a progressive quiz mode, where users begin with easier questions and unlock harder ones as they improve. This will be achieved by maintaining user performance data, dynamically adjusting the difficulty level based on previous quiz results, and providing users with achievements like "Completed 10 Quizzes" or "Accuracy Above 90%" to motivate continued engagement. These achievements will be stored in the database and linked to user profiles, allowing for gamification of the experience.

To foster competition, I will introduce a real-time leaderboard. This will allow users to immediately see their rank and compare their performance with others after completing a quiz. The leaderboard will be updated dynamically using WebSocket for real-time data transmission, ensuring that the ranking reflects the latest quiz results and user scores.

Finally, I will add a multiplayer/challenge mode to enable users to compete against each other. This feature will allow users to invite friends or other players for head-to-head quiz challenges, with the ability to see real-time results and compare answers as they progress. This will be implemented by setting up multiplayer game sessions, allowing real-time communication between players, and providing the ability to challenge others via email or unique invitation links.
