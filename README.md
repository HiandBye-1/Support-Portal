# LinkFlow — Link Management & Analytics Platform

LinkFlow is a Java Spring Boot application for creating, managing, and tracking shortened URLs. It is being developed as a secure, deployable platform with user, employee, administrator, and developer-facing functionality.

## Current Features

- **Authentication:** User registration, BCrypt password hashing, Spring Security authentication, session management, and CSRF protection.
- **Authorization:** Role-based access control for protected endpoints.
- **URL Shortening:** Generate unique short codes, associate links with their owners, store mappings in MySQL, and redirect visitors.
- **HTTPS:** TLS support for local development.
- **Docker:** Multi-stage application image, Docker Compose, MySQL container, and persistent database storage.

Email verification and MFA are currently under development.

## Technology Stack

| Technology | Purpose |
|---|---|
| Java 23 | Backend |
| Spring Boot 4 | Application framework |
| Spring Security 7 | Authentication and authorization |
| Spring Data JPA / Hibernate | Database persistence |
| MySQL 8 | Database |
| Maven | Build and dependencies |
| Docker / Compose | Containerization |
| Postman | API testing |
| JavaScript | Planned frontend integration |

## Getting Started

### Requirements

- [Docker Desktop](https://www.docker.com/products/docker-desktop/)
- [Git](https://git-scm.com/)
- [Postman](https://www.postman.com/) (optional)

Java, Maven, and MySQL do not need to be installed separately to run the Dockerized application.

### 1. Clone the repository

```bash
git clone https://github.com/HiandBye-1/LinkFlow.git
cd LinkFlow
```

### 2. Configure environment variables

Create a `.env` file in the project root, alongside `compose.yaml` and `Dockerfile`:

```dotenv
DB_USERNAME=appuser
DB_PASSWORD=change_me
DB_ROOT_PASSWORD=change_me
KEYSTORE_PASSWORD=change_me
```

Replace the example values with your own credentials. Never commit `.env` or real credentials to Git.

### 3. Create the HTTPS certificate

Create a `certs` directory:

```bash
mkdir certs
```

Generate a local development keystore using Java's `keytool`:

```cmd
keytool -genkeypair ^
  -alias localhost ^
  -keyalg RSA ^
  -keysize 2048 ^
  -storetype PKCS12 ^
  -keystore certs/localhost.p12 ^
  -validity 365 ^
  -dname "CN=localhost" ^
  -ext "SAN=dns:localhost,ip:127.0.0.1"
```

Use the same password configured in `KEYSTORE_PASSWORD`.

This creates a self-signed certificate. Browsers and API clients may warn that it is not trusted; this is expected for local development.

### 4. Start LinkFlow

Make sure Docker Desktop is running, then execute:

```bash
docker compose up --build
```

The application will be available at:

```text
https://localhost:8443
```

Docker Compose builds the application, starts MySQL, initializes a fresh database, and starts Spring Boot.

**Important:** `DB.sql` is executed when MySQL initializes an empty data directory. Existing database volumes are preserved between restarts.

## API Usage

All examples use:

```text
https://localhost:8443
```

CSRF protection is enabled, including for public POST requests. First retrieve a CSRF token:

```http
GET /csrf
```

Keep the session cookie and send the returned token in the `X-CSRF-TOKEN` header for POST requests.

| Method | Endpoint | Access | Purpose |
|---|---|---|---|
| POST | `/user/register` | Public | Register an account |
| POST | `/user/login` | Public | Log in |
| GET | `/csrf` | Public | Retrieve CSRF token |
| GET | `/user/profile` | Authenticated | View profile |
| POST | `/url/shorten` | NORMALUSER | Create a short link |
| GET | `/r/{urlCode}` | Public | Redirect to original URL |

### Register

```http
POST /user/register
```

Example JSON body:

```json
{
  "username": "exampleuser",
  "password": "yourpassword",
  "reenteredPassword": "yourpassword"
}
```

### Log in

```http
POST /user/login
```

Submit the account credentials and retain the returned session cookie.

### View profile

```http
GET /user/profile
```

Requires an authenticated session.

### Create a short link

```http
POST /url/shorten
```

Example JSON body:

```json
{
  "longUrl": "https://example.com"
}
```

Requires an authenticated user with the `NORMALUSER` role.

### Follow a short link

```http
GET /r/{urlCode}
```

Redirects the visitor to the original URL.

## Useful Docker Commands

| Command | Purpose |
|---|---|
| `docker compose up` | Start containers |
| `docker compose up --build` | Build and start |
| `docker compose down` | Stop and remove containers |
| `docker compose stop` | Stop containers |
| `docker compose start` | Restart stopped containers |
| `docker compose logs app` | View application logs |
| `docker compose logs database` | View database logs |
| `docker compose ps` | Check container status |

MySQL data is stored in a named Docker volume. `docker compose down` preserves it.

To completely reset the development database:

```bash
docker compose down -v
```

**Warning:** This deletes the database volume and all stored data. Use it only when you intend to reset the database.

## Project Architecture

LinkFlow follows a layered architecture:

- **Controller:** Handles HTTP requests and responses.
- **Service:** Contains business logic.
- **Repository:** Handles database operations.
- **Entity:** Represents persisted database records.
- **DTO:** Defines API request and response structures.
- **Security:** Handles authentication and authorization.
- **Exception:** Centralizes application error handling.

The intended architecture is a **modular monolith**: one deployable application organized into separate functional areas.

## Roadmap

Planned development includes:

- Email verification and multi-factor authentication.
- Link management, campaigns, and click analytics.
- User, employee/support, and administrator portals.
- Developer APIs, API keys, and rate limiting.
- Asynchronous analytics processing.
- JavaScript frontend integration.
- Automated testing, monitoring, and cloud deployment.

## Development Approach

The project prioritizes backend functionality, security, correctness, maintainability, and testing. New technologies will be introduced when they solve a practical engineering problem rather than simply to expand the technology stack.

JavaScript interfaces will be developed incrementally alongside stable backend APIs.

---

**LinkFlow — Build, Manage, and Understand Your Links.**
Thank you for your interest in the project! >:)