# Support Portal & URL Management Platform

A Java Spring Boot backend project designed to grow into a deployable support and URL management platform(and more to come). The project is being developed incrementally, with an emphasis on backend engineering, authentication, security, database design, and scalable architecture.

## Project Overview

The platform is intended to support three main types of users:

- **Normal users:** Create and manage shortened URLs and their accounts.(More feature in plans)
- **Employees:** Assist users and manage support requests.
- **Administrators:** Manage users, employees, permissions, and platform activity.

The current development focus is the user authentication system and URL-shortening functionality.

## Current Development

### Authentication & Security

- User registration and request validation.
- Password hashing with BCrypt.
- Login authentication through Spring Security's `AuthenticationManager`.
- Session-based authentication using HTTP sessions.
- Security context persistence through `HttpSessionSecurityContextRepository`.
- CSRF protection.
- Role-based authorization for protected endpoints.
- Public registration, login, and redirect routes.

### URL Shortener

- Create shortened URLs through `POST /url/shorten`.
- Generate unique short codes.
- Store URL mappings in MySQL.
- Associate each URL mapping with its owner.
- Handle duplicate short-code collisions with retry logic.
- Return a response DTO containing the original URL and generated short link.
- Redirect visitors to the original URL using HTTP `302 Found`.
- Public redirect endpoint: `GET /r/{urlCode}`.
- Configurable short-link base URL through `app.base-url`.

### Current Endpoint Overview

| Endpoint | Access | Purpose |
|---|---|---|
| `POST /user/register` | Public | Register an account |
| `POST /user/login` | Public | Authenticate a user |
| `GET /csrf` | Public | Retrieve CSRF token |
| `GET /user/profile` | Authenticated | Retrieve user profile |
| `POST /url/shorten` | `NORMALUSER` | Create a short URL |
| `GET /r/{urlCode}` | Public | Redirect to original URL |

Other endpoints may be added as development progresses.

## Technology Stack

| Technology | Purpose |
|---|---|
| Java 23 | Backend programming language |
| Spring Boot 4 | Application framework |
| Spring Security 7 | Authentication and authorization |
| Spring Data JPA | Database persistence |
| Hibernate | ORM implementation |
| MySQL 8 | Relational database |
| Maven | Build and dependency management |
| Postman | API testing |
| Docker | Containerization and deployment preparation |

## Configuration

The short-link base URL is configurable:

```properties
app.base-url=http://localhost:8080/r/
```

Database connection settings should be supplied through the application's configuration or environment variables. Avoid committing database credentials or other secrets.

## Architecture

The project currently follows a layered backend structure:

- **Controller:** Handles HTTP requests and responses.
- **Service:** Contains application and business logic.
- **Repository:** Handles database operations.
- **Entity:** Represents persisted database data.
- **DTO:** Defines request and response data structures.
- **Security:** Handles authentication, authorization, and session management.
- **Exception:** Handles application-specific errors.

The intended direction is a **modular monolith**: one deployable application organized into clearly separated functional areas, rather than prematurely splitting the system into microservices.

## Planned Features

### User Portal
- URL management dashboard.
- Custom short codes.
- URL expiration and deactivation.
- Click analytics.
- QR code generation.
- Password reset and account recovery.
- Multi-factor authentication.
- Account settings.

### Employee / Support Portal
- Employee account management.
- Support ticket creation and assignment.
- Ticket responses and history.
- User assistance workflows.

### Admin Portal
- User and employee management.
- Role and permission management.
- Account suspension and recovery oversight.
- Audit logs.
- Platform activity and abuse monitoring.

### Security & Reliability
- Centralized exception handling.
- Automated unit and integration testing.
- Rate limiting.
- Secure account recovery and MFA.
- HTTPS/TLS deployment.
- Improved logging and monitoring.

### Scalability & Deployment
- Docker and Docker Compose.
- Redis for caching and shared session storage.
- Multiple Spring Boot instances.
- Load balancing with Nginx or an equivalent solution.
- CI/CD pipeline.
- Cloud deployment, custom domain, and HTTPS.
- Database backup and recovery strategy.

## Development Philosophy

The goal is to build a practical backend system that can eventually support real users.

New technologies will be introduced when they solve actual engineering problems. For example:

- Redis when shared sessions or caching become necessary.
- Load balancing when multiple backend instances are deployed.
- Rate limiting when protecting public endpoints from abuse.
- Asynchronous processing when background workloads justify it.

The project will prioritize correctness, security, maintainability, and measurable performance over simply accumulating technologies.


Thank you for your interest in the project >:)