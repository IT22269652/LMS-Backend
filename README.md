📚 Library Management System — Backend (Spring Boot)

This is the backend service of the Library Management System.
It is built using Spring Boot, Spring Security, JWT, and MySQL.

The backend exposes a secure REST API for:

User & Librarian authentication

Book & Category management

Reservation management

Image upload

Email sending

Role-based authorization (RBAC)

Backend requests are routed through the API Gateway.

🚀 Tech Stack
Layer	Technology
Language	Java 17+
Framework	Spring Boot
Security	Spring Security + JWT
Database	MySQL
ORM	Spring Data JPA
Gateway	Spring Cloud Gateway
Build Tool	Maven
📌 Features
🔐 Authentication & Authorization

User/Librarian signup & login

JWT token generation

JWT validation filter

Password hashing using BCrypt

Role-based access control

Librarian (Admin)

User (Member)

📘 Book & Category Management (Librarian Only)

Add new books

Update book status

Upload book cover image

Add/Edit/Delete categories

Blacklist users

📖 User Features

View all books

Filter by category, author, genre, language

Reserve a book (7/14/21 days)

View personal reservations

🗄️ Database Schema
users

id

email

password

role

is_blacklisted

created_at

categories

id

name

books

id

title

author

genre

language

isbn

status

image_url

category_id

reservations

id

user_id

book_id

reservation_date

due_date

status
