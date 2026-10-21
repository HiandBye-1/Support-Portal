# Support Portal

A Java-based backend project evolving from a basic authentication system into a support management portal. The project focuses on secure user authentication, database-backed account management, and role-based access for users, employees, and administrators.

## Overview

The goal of this project is to develop a backend system that manages different types of accounts and provides access to features based on each user's role.

- **Users:** Register, log in, and access features available to their accounts(to be decide).
- **Employees:** Authenticate and access employee-facing functionality for handling support-related tasks.
- **Administrators:** Manage accounts, oversee system operations, and access administrative functionality.

The project is being developed incrementally, starting with authentication and database integration before expanding into role-based authorization and support management features.

## Current Development

The current focus is on transitioning the existing Java and JDBC authentication implementation toward Spring Boot, Spring Data JPA, and Spring Security.

Planned development includes:

- User registration and login
- Secure password storage and verification using BCrypt
- MySQL-backed account management
- Spring Boot and JPA integration
- Role-based authentication and authorization for users, employees, and administrators
- Access control for protected endpoints and resources
- Validation, error handling, and automated testing
- Docker-based application and database deployment

## Technologies

### Languages and Database
- **Java** — Application logic and backend development
- **SQL** — Database queries and relational data management
- **MySQL** — Persistent storage for user accounts and application data

### Backend Frameworks and Libraries
- **Spring Boot** *(in progress)* — Backend application development and REST API creation
- **Spring Data JPA** *(in progress)* — Entity mapping and database access through repositories
- **Spring Security** *(in progress)* — Authentication and role-based authorization
- **JDBC** — Database connectivity in the original authentication implementation
- **BCrypt** — Password hashing and verification

### Development and Deployment
- **Maven** — Dependency management and project builds for the Spring Boot application
- **Git and GitHub** — Version control and source code management
- **Docker** — Containerization of the application and database environment
- **Docker Compose** — Managing the application and MySQL containers together
- **Environment Variables** — External configuration for database connection settings

### Testing and Quality
- **JUnit and Spring Boot Testing** *(for testing as development progresses)* — Testing application behavior and backend components


### Currently working
prefix = http://localhost:8080

GET  /csrf   to get your csrf key, DO THIS BEFORE ANY REQUEST and save the name + key into the postman header if you use postman
POST /user/login  = to login
GET  /user/register = to register your stuff to databse
