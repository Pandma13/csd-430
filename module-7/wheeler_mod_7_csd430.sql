-- SQL Statements for Module 7
-- Megan Wheeler
-- CSD 430 - Module 7
-- 24 June 2025

-- Create Database

CREATE DATABASE IF NOT EXISTS csd430;

-- Use Database

USE csd430;

-- Create User

CREATE USER IF NOT EXISTS 'student1'@'localhost' IDENTIFIED BY 'pass';

-- Grant User Privileges

GRANT ALL PRIVILEGES ON csd430.* TO 'student1'@'localhost';

-- Drop Table

DROP TABLE IF EXISTS wheeler_library_data;

-- Create Table

CREATE TABLE wheeler_library_data (
    bookID INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    genre VARCHAR(255) NOT NULL,
    publicationYear INT NOT NULL,
    pageCount INT NOT NULL,
    ISBN VARCHAR(13) NOT NULL
);

-- Insert Data

INSERT INTO wheeler_library_data (title, author, genre, publicationYear, pageCount, ISBN) VALUES
('The Time Machine', 'H.G. Wells', 'Science Fiction', 1895, 126, '9781542557924'),
('Pride and Prejudice', 'Jane Austen', 'Classic Romance', 1813, 280, '9780140434262'),
('The Color of Magic', 'Terry Pratchett', 'Fantasy', 1983, 205, '9780861400898'),
('Kushiels Dart', 'Jacqueline Carey', 'Fantasy', 2001, 704, '9780312872380'),
('Guilty Pleasures', 'Laurell K. Hamilton', 'Horror', 1993, 272, '9780441304837'),
('Frankenstein', 'Mary Shelley', 'Gothic', 1818, 288, '9780143131847'),
('Twenty Thousand Leagues Under the Sea', 'Jules Verne', 'Adventure', 1870, 448, '9780553212525'),
('Fool Moon', 'Jim Butcher', 'Detective', 2001, 432, '9780451458124'),
('The Girl with the Dragon Tattoo', 'Stieg Larsson', 'Crime', 2005, 576, '9780307949486'),
('Wizards First Rule', 'Terry Goodkind', 'Fantasy', 1994, 640, '9780765348273');

-- Read All

SELECT * FROM wheeler_library_data;

-- Form Get PK

SELECT title FROM wheeler_library_data;

-- Read by Title

SELECT bookID FROM wheeler_library_data WHERE title = ?;

-- Select Title

SELECT DISTINCT title FROM wheeler_library_data ORDER BY title ASC;

-- Select Author

SELECT DISTINCT author FROM wheeler_library_data ORDER BY author ASC;

-- Select Genre

SELECT DISTINCT genre FROM wheeler_library_data ORDER BY genre ASC;

-- Select Publication Year

SELECT DISTINCT publicationYear FROM wheeler_library_data ORDER BY publicationYear ASC;

-- Select Page Count

SELECT DISTINCT pageCount FROM wheeler_library_data ORDER BY pageCount ASC;

-- Select ISBN

SELECT DISTINCT ISBN FROM wheeler_library_data ORDER BY ISBN ASC;