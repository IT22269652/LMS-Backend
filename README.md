📚 Library Management System — Backend (Spring Boot)

A comprehensive RESTful API built with Spring Boot for managing a modern library system with features including book management, user authentication, reservations, and administrative controls.

✨ Features
User Management

User registration and authentication with JWT
Role-based access control (USER, LIBRARIAN)
User blacklisting functionality
Email notifications

Book Management

CRUD operations for books
Book categorization
Book status tracking (AVAILABLE, RESERVED)
ISBN validation
Image upload support

Reservation System

Book reservation with customizable duration (7, 14, 21 days)
Automatic book status updates
Reservation history tracking
Overdue book detection
Return book functionality

Category Management

Organize books by categories
CRUD operations for categories
Category-based book filtering

Administrative Features

Complete user management
Reservation tracking and management
Book inventory management
User activity monitoring

🛠️ Tech Stack

Framework: Spring Boot 3.2.0
Language: Java 17
Database: MySQL 8.0
Security: Spring Security with JWT
ORM: Spring Data JPA / Hibernate
Migration: Flyway
Build Tool: Maven
Email: Spring Mail (Gmail SMTP)
Validation: Jakarta Validation
Documentation: Spring REST Docs

📦 Prerequisites
Before you begin, ensure you have the following installed:

Java JDK 17 or higher

bash  java -version

Maven 3.6 or higher

bash  mvn -version

MySQL 8.0 or higher

bash  mysql --version

Git

bash  git --version

🚀 Installation
1. Clone the Repository
bashgit clone https://github.com/nuradha123/lms-backend.git
cd lms-backend
2. Create MySQL Database
Open MySQL Workbench or MySQL CLI and run:
sqlCREATE DATABASE library_db;
3. Configure Database
Edit src/main/resources/application.properties:
properties# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/library_db?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=your_password_here

# Change these to your MySQL credentials
4. Configure Email (Optional)
If you want email notifications to work, update these properties:
propertiesspring.mail.username=your_email@gmail.com
spring.mail.password=your_app_password
Note: For Gmail, you need to create an App Password.
5. Install Dependencies
bashmvn clean install
⚙️ Configuration
Application Properties
Key configuration options in application.properties:
properties# Server Configuration
server.port=8080

# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/library_db
spring.datasource.username=root
spring.datasource.password=your_password

# JPA Configuration
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true

# Flyway Migration
spring.flyway.enabled=true
spring.flyway.baseline-on-migrate=true

# JWT Configuration
jwt.secret=MySecretKeyForJWTTokenGenerationLibraryManagementSystem2024
jwt.expiration=86400000

# File Upload Configuration
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB

# Email Configuration
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your_email@gmail.com
spring.mail.password=your_app_password
JWT Configuration
The JWT token expires after 24 hours (86400000 milliseconds). You can adjust this:
propertiesjwt.expiration=86400000  # 24 hours in milliseconds
File Upload
Book cover images are stored in uploads/book-covers/ directory. Maximum file size is 10MB.

🏃 Running the Application

Development Mode
bash:mvn spring-boot:run
The application will start on http://localhost:8080
Production Build
bash# Build JAR file
mvn clean package

# Run JAR file
java -jar target/lms-backend-0.0.1-SNAPSHOT.jar
Docker (Optional)
bash# Build Docker image
docker build -t lms-backend .

# Run container
docker run -p 8080:8080 lms-backend

📚 API Documentation
Base URL
http://localhost:8080/api
