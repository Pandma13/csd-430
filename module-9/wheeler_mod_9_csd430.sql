-- SQL Statements for Module 8
-- Megan Wheeler
-- CSD 430 - Module 9
-- 9 July 2025

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
    ISBN VARCHAR(13) NOT NULL
);

-- Insert Data

INSERT INTO wheeler_library_data (title, author, genre, publicationYear, ISBN) VALUES
('The Time Machine', 'H.G. Wells', 'Science Fiction', 1895, '9781542557924'),
('Pride and Prejudice', 'Jane Austen', 'Classic Romance', 1813, '9780140434262'),
('The Color of Magic', 'Terry Pratchett', 'Fantasy', 1983, '9780861400898'),
('Kushiels Dart', 'Jacqueline Carey', 'Fantasy', 2001, '9780312872380'),
('Guilty Pleasures', 'Laurell K. Hamilton', 'Horror', 1993, '9780441304837'),
('Frankenstein', 'Mary Shelley', 'Gothic', 1818, '9780143131847'),
('Twenty Thousand Leagues Under the Sea', 'Jules Verne', 'Adventure', 1870, '9780553212525'),
('Fool Moon', 'Jim Butcher', 'Detective', 2001, '9780451458124'),
('The Girl with the Dragon Tattoo', 'Stieg Larsson', 'Crime', 2005, '9780307949486'),
('Wizards First Rule', 'Terry Goodkind', 'Fantasy', 1994, '9780765348273');

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

-- Select ISBN

SELECT DISTINCT ISBN FROM wheeler_library_data ORDER BY ISBN ASC;

-- Create Record

INSERT INTO wheeler_library_data(title, author, genre, pubYear, ISBN) VALUES(?, ?, ?, ?, ?);

-- Update Record

UPDATE wheeler_library_data SET title = ?, author = ?, genre = ?, pubYear = ?, ISBN = ? WHERE bookID = ?;

-- Delete Record

DELETE FROM wheeler_library_data WHERE bookID = ?;