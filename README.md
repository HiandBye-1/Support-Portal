# Support Portal API

A Java-based backend project evolving from a basic authentication system into a support management portal. The project focuses on secure authentication, database-backed account management, and role-based access control, with plans to expand into user-facing services and support-related functionality.

## Overview

The goal is to build a backend system that manages different account types and provides functionality based on each user's role.

- **Users:** Register, log in, and access user-facing services.
- **Employees:** Authenticate and access employee-facing functionality for handling support-related tasks.
- **Administrators:** Manage accounts and access administrative functionality.

Development is incremental, beginning with authentication and database integration before expanding into authorization, support management, and additional services.

## Current Development

The project is transitioning from a basic Java and JDBC authentication implementation toward a Spring Boot architecture using Spring Data JPA and Spring Security.

### Implemented

- User registration endpoint
- Password hashing and verification using BCrypt
- MySQL database integration
- Spring Security authentication through `AuthenticationManager`
- Session-based authentication
- Security context persistence across requests
- Protected user profile endpoint
- CSRF protection
- Logout functionality

### Planned

- Role-based authorization for users, employees, and administrators
- Employee and administrator account management
- Improved request validation and error handling
- Automated unit and integration testing
- Docker-based application and database deployment
- JavaScript integration for future frontend functionality
- Additional user-facing services, beginning with a URL shortener
- A personal dashboard for managing shortened URLs

## Technologies

### Languages and Database

- **Java** — Backend application logic
- **SQL** — Relational database queries and data management
- **MySQL** — Persistent storage for accounts and application data
- **JavaScript** — Planned frontend integration and API interaction

### Backend Frameworks and Libraries

- **Spring Boot** — Backend application development and REST API creation
- **Spring Data JPA** — Entity mapping and database access through repositories
- **Spring Security** — Authentication, session management, CSRF protection, and authorization
- **JDBC** — Database connectivity in the original authentication implementation
- **Hibernate** — JPA implementation and ORM functionality
- **BCrypt** — Password hashing and verification

### Development and Deployment

- **Maven** — Dependency management and application builds
- **Git and GitHub** — Version control and source code management
- **Docker** — Planned containerization of the application and database
- **Docker Compose** — Planned orchestration of the application and database containers
- **Environment Variables** — External configuration for database connections and other settings

### Testing and Quality

- **JUnit** — Unit testing
- **Spring Boot Testing** — Testing Spring application components and integration behavior

## Running and Testing the API

The following endpoints assume the application is running locally.

**Base URL:** `http://localhost:8080`

### Available Endpoints

| Method | Endpoint | Purpose |
|---|---|---|
| `GET` | `/csrf` | Retrieve the CSRF token |
| `POST` | `/user/register` | Register a new user |
| `POST` | `/user/login` | Authenticate a user |
| `GET` | `/user/profile` | Retrieve the authenticated user's profile |
| `POST` | `/logout` | Log out and invalidate the authenticated session |

### Testing with Postman

1. Start the Spring Boot application and confirm that it is listening on port `8080`.
2. Send a `GET` request to `http://localhost:8080/csrf` to retrieve the CSRF token.
3. For requests protected by CSRF, send the token using the header name provided by the `/csrf` response. Include the associated CSRF cookie when required by the configured token repository.
4. Send a `POST` request to `/user/register` with the required registration data.
5. Send a `POST` request to `/user/login` with the user's credentials.
6. Preserve the session cookie returned during authentication when testing protected endpoints.
7. Send a `GET` request to `/user/profile` to verify the authenticated session.
8. Send a `POST` request to `/logout` to log out.

**Notes:**

- Registration and login use `POST`, not `GET`, because they submit data or perform an authentication action.
- Retrieve or refresh the CSRF token as required by the application's configuration. Do not assume every request requires a new token.
- Postman must preserve the relevant cookies and send the CSRF token in the expected header for protected state-changing requests.
- Exact request bodies, CSRF header names, and response formats depend on the current controller and security configuration.

## Project Goals

This project is intended to strengthen practical backend development skills, including:

- Designing REST APIs
- Implementing secure authentication and authorization
- Working with relational databases and JPA
- Structuring applications with controllers, services, and repositories
- Handling sessions, CSRF protection, and access control
- Writing unit and integration tests
- Deploying and configuring backend applications
- Extending an API with additional user-facing services
