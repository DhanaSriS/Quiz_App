INSERT INTO users (username, password) VALUES ('user', 'password');

INSERT INTO questions (question_text, option_a, option_b, option_c, option_d, correct_option, category, difficulty)
VALUES
('What is the capital of France?', 'Paris', 'Rome', 'Berlin', 'Madrid', 'A', 'Geography', 'Easy'),
('What is 2 + 2?', '3', '4', '5', '6', 'B', 'Math', 'Easy'),
('Who wrote Hamlet?', 'Shakespeare', 'Dickens', 'Homer', 'Tolstoy', 'A', 'Literature', 'Medium'),
('What is the square root of 16?', '2', '3', '4', '5', 'C', 'Math', 'Easy'),
('What is the chemical symbol for Water?', 'O2', 'H2O', 'HO', 'HHO', 'B', 'Science', 'Medium'),
('Who painted the Mona Lisa?', 'Van Gogh', 'Da Vinci', 'Picasso', 'Monet', 'B', 'Art', 'Medium');
