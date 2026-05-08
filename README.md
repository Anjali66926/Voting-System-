Online Voting System Github Readme
GitHub Description

Online Voting System developed using Java Swing and MySQL with JDBC connectivity. The project provides secure user authentication, MLA & MP election voting, duplicate vote prevention, automatic result generation, and a user-friendly GUI interface.

README.md
🗳️ Online Voting System Using Java Swing and MySQL
📌 Project Overview

The Online Voting System is a desktop-based application developed using Java Swing and MySQL. The project provides a secure and user-friendly platform for conducting digital elections. Users can log in using their credentials, select election categories such as MLA or MP, and cast their votes through an interactive graphical user interface.

The system prevents duplicate voting and automatically generates election results after voting completion.

🚀 Features
User Login Authentication
MLA and MP Election Voting
Dynamic Candidate Selection
Duplicate Vote Prevention
Automatic Vote Counting
Real-Time Result Display
Java Swing GUI Interface
MySQL Database Integration
JDBC Connectivity
🛠️ Technologies Used
Technology	Purpose
Java	Application Development
Java Swing	GUI Design
MySQL	Database Management
JDBC	Database Connectivity
VS Code	Development Environment
MySQL Workbench	Database Handling
📂 Project Structure
OnlineVotingSystem/
│
├── DBConnection.java
├── LoginPage.java
├── VotingPage.java
├── CandidatePage.java
├── ResultPage.java
└── lib/
🗄️ Database Design
Users Table

Stores user login credentials and voting status.

Candidates Table

Stores candidate names, election type, and vote count.

Votes Table

Stores voting records and election type.

⚙️ SQL Queries
Create Database
CREATE DATABASE voting_system;
USE voting_system;
Create Users Table
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE,
    password VARCHAR(50),
    voted_mla BOOLEAN DEFAULT FALSE,
    voted_mp BOOLEAN DEFAULT FALSE
);
Create Candidates Table
CREATE TABLE candidates (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    votes INT DEFAULT 0,
    type VARCHAR(10)
);
Create Votes Table
CREATE TABLE votes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    candidate_id INT,
    type VARCHAR(10)
);
👤 Sample Users
admin / 1234
user1 / 1111
user2 / 1111
user3 / 1111
▶️ How to Run the Project
Step 1: Compile Java Files
javac *.java
Step 2: Run the Application
java LoginPage
🔄 Project Flow
Login → Select Election Type → Select Candidate → Vote Stored → Results Generated
📸 Output Screens
Login Page
Election Selection Page
Candidate Voting Page
Vote Success Message
Result Page
🔒 Security Features
User Authentication
Duplicate Vote Prevention
Separate MLA and MP Voting
Secure Database Storage
🎯 Future Enhancements
OTP Authentication
Aadhaar Verification
Admin Dashboard
Cloud Database Integration
Graphical Result Analysis
Online Voting Support
📚 Learning Outcomes

This project demonstrates:

Java Swing GUI Development
JDBC Database Connectivity
MySQL Database Management
Event Handling in Java
Real-Time Data Processing
Desktop Application Development
👨‍💻 Author

Developed as a Java Mini Project using Java Swing and MySQ