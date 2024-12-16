CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50),
    password VARCHAR(50)
);

CREATE TABLE questions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    question_text VARCHAR(255),
    option_a VARCHAR(100),
    option_b VARCHAR(100),
    option_c VARCHAR(100),
    option_d VARCHAR(100),
    correct_option CHAR(1),
    category VARCHAR(50),
    difficulty VARCHAR(50)
);

CREATE TABLE quiz_sessions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    total_questions INT,
    correct_answers INT,
    incorrect_answers INT,
    quiz_date TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);
