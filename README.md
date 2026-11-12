# Support Portal & URL Management Platform

A Java Spring Boot backend project designed to grow into a deployable support and URL management platform. The project is being developed incrementally, with an emphasis on backend engineering, authentication, security, database design, containerization, and scalable architecture.

The long-term goal is to build a practical platform that can support real users, employees, and administrators.

## Project Overview

The platform is intended to support three main types of users:

- **Normal users:** Create and manage shortened URLs and their accounts.
- **Employees:** Assist users and manage support requests.
- **Administrators:** Manage users, employees, permissions, and platform activity.

The current development focus is authentication, session management, security, URL shortening, and Docker-based development.

## Current Development

### Authentication & Security

- User registration and request validation.
- Password hashing with BCrypt.
- Login authentication through Spring Security's `AuthenticationManager`.
- Session-based authentication using HTTP sessions.
- Security context persistence through `HttpSessionSecurityContextRepository`.
- CSRF protection.
- Role-based authorization for protected endpoints.
- Secure session cookies configured with `Secure`, `HttpOnly`, and `SameSite=Lax`.
- HTTPS/TLS support for local development.

### URL Shortener

- Create shortened URLs through `POST /url/shorten`.
- Generate unique short codes.
- Store URL mappings in MySQL.
- Associate each URL mapping with its owner.
- Handle duplicate short-code collisions.
- Return a response DTO containing the original URL and generated short link.
- Redirect visitors to the original URL using HTTP `302 Found`.
- Public redirect endpoint: `GET /r/{urlCode}`.
- Configurable short-link base URL through `app.base-url`.

### Docker & Containerization

- Multi-stage Dockerfile using Maven and Eclipse Temurin.
- Separate application and MySQL containers.
- Docker Compose orchestration.
- Dedicated Docker network for application-to-database communication.
- Persistent MySQL storage through a named Docker volume.
- Database initialization through `DB.sql`.
- Environment-based database credentials and keystore password.
- HTTPS keystore mounted into the application container.
- `.dockerignore` and `.gitignore` configurations to exclude build artifacts and sensitive files.

### Current Endpoint Overview

| Endpoint | Access | Purpose |
|---|---|---|
| `POST /user/register` | Public | Register an account |
| `POST /user/login` | Public | Authenticate a user |
| `GET /csrf` | Public | Retrieve CSRF token |
| `GET /user/profile` | Authenticated | Retrieve user profile |
| `POST /url/shorten` | `NORMALUSER` | Create a short URL |
| `GET /r/{urlCode}` | Public | Redirect to original URL |

**Note:** CSRF protection is enabled, including for public POST requests. Clients must obtain and submit a valid CSRF token.

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
| Docker | Application containerization |
| Docker Compose | Multi-container orchestration |
| Postman | API testing |
| TLS / HTTPS | Encrypted application communication |

## Getting Started

### Prerequisites

Install the following:

- [Docker Desktop](https://www.docker.com/products/docker-desktop/)
- [Git](https://git-scm.com/)
- [Postman](https://www.postman.com/) (optional, for API testing)

You do not need to install Java, Maven, or MySQL separately to run the Dockerized application.

### 1. Clone the repository

```bash
git clone https://github.com/HiandBye-1/Support-Portal.git
cd support-portal
```


### 2. Configure environment variables

Create a `.env` file in the project root, alongside `compose.yaml` and `Dockerfile`.

Use the following as a starting point:

```dotenv
DB_USERNAME=appuser
DB_PASSWORD=change_me
DB_ROOT_PASSWORD=change_me
KEYSTORE_PASSWORD=change_me
```

Replace the example values with your own credentials.

These variables are used by Docker Compose:

- `DB_USERNAME`: MySQL application account.
- `DB_PASSWORD`: Password for the application account.
- `DB_ROOT_PASSWORD`: MySQL root account password.
- `KEYSTORE_PASSWORD`: Password protecting the HTTPS keystore.

**Important:** Never commit `.env` or actual credentials to GitHub. The repository should contain an `.env.example` file with placeholder values instead.

### 3. Generate a local HTTPS keystore

The application uses HTTPS on port `8443`.

Create the `certs` directory:

```bash
mkdir certs
```

Generate a local development certificate using Java's `keytool`:

```bash
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

Enter the same password configured in `KEYSTORE_PASSWORD` when prompted.

This creates a self-signed certificate for local development.

**Note:** Browsers and API clients may warn that the certificate is not trusted. This is expected for a self-signed development certificate. Production deployments should use a certificate issued by a trusted certificate authority.

### 4. Start the application

Make sure Docker Desktop is running.

From the project root, execute:

```bash
docker compose up --build
```

Docker Compose will:

1. Build the Spring Boot application using the multi-stage Dockerfile.
2. Download the MySQL image if it is not already available locally.
3. Create the MySQL container and application container.
4. Create the Docker network.
5. Initialize the `Ldata` database.
6. Execute `DB.sql` when MySQL initializes a fresh data directory.
7. Start the Spring Boot application.

The application should become available at:

```text
https://localhost:8443
```

### 5. Verify the application

Use Postman or another API client to test the endpoints.

Start by retrieving a CSRF token:

```http
GET https://localhost:8443/csrf
```

Use the returned token in the `X-CSRF-TOKEN` header for POST requests. Keep the session cookie associated with the request sequence.

Then test the following:

**Register an account**

```http
POST https://localhost:8443/user/register
```

Example JSON body:

```json
{
  "username": "exampleuser",
  "password": "yourpassword"
}
```

**Log in**

```http
POST https://localhost:8443/user/login
```

Use the same account credentials. Retain the session cookie returned by the server.

**Retrieve your profile**

```http
GET https://localhost:8443/user/profile
```

This endpoint requires an authenticated session.

**Create a shortened URL**

```http
POST https://localhost:8443/url/shorten
```

Example JSON body:

```json
{
  "longUrl": "https://example.com"
}
```

This endpoint requires an authenticated user with the `NORMALUSER` role.

**Follow a shortened URL**

```http
GET https://localhost:8443/r/{urlCode}
```

The server redirects the visitor to the original URL.

### 6. Managing Docker

Useful commands for working with the application:

| Command | Purpose |
|---|---|
| `docker compose up` | Start the containers |
| `docker compose up --build` | Build and start the application |
| `docker compose down` | Stop and remove containers and network |
| `docker compose stop` | Stop containers without removing them |
| `docker compose start` | Restart stopped containers |
| `docker compose logs app` | View application logs |
| `docker compose logs database` | View MySQL logs |
| `docker compose ps` | View container status |

**Database persistence:** MySQL data is stored in a named Docker volume. Running `docker compose down` does not delete this data.

To completely reset the development database:

```bash
docker compose down -v
```

This deletes the named database volume and all data stored in it. On the next startup, MySQL initializes a fresh database and executes `DB.sql` again.

Only use this command when you are comfortable losing the existing database data.

## Configuration

The application uses environment variables for sensitive configuration.

Relevant application properties include:

```properties
spring.datasource.url=jdbc:mysql://database:3306/Ldata
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

app.base-url=https://localhost:8443/r/

server.port=8443
server.ssl.enabled=true
server.ssl.key-store=file:/app/certs/localhost.p12
server.ssl.key-store-type=PKCS12
server.ssl.key-store-password=${KEYSTORE_PASSWORD}
server.ssl.key-alias=localhost

server.servlet.session.cookie.same-site=lax
```

The database hostname `database` refers to the MySQL service name inside the Docker Compose network. It is not `localhost` from the application's container.

Change the .Exenv to .env and follow instruction in the new .env files
The `.env` file is read by Docker Compose for variable substitution. Compose then passes the required variables into the application container.

## Architecture

The project currently follows a layered backend structure:

- **Controller:** Handles HTTP requests and responses.
- **Service:** Contains application and business logic.
- **Repository:** Handles database operations.
- **Entity:** Represents persisted database data.
- **DTO:** Defines request and response data structures.
- **Security:** Handles authentication, authorization, and session management.
- **Exception:** Handles application-specific errors.
- **Util / Validation:** Contains reusable utilities and input validation.

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
- Production HTTPS/TLS configuration.
- Improved logging and monitoring.

### Scalability & Deployment

- Redis for caching and shared session storage.
- Multiple Spring Boot instances.
- Load balancing with Nginx or an equivalent solution.
- CI/CD pipeline.
- Cloud deployment, custom domain, and HTTPS.
- Database backup and recovery strategy.

## Development Philosophy

The goal is to build a practical backend system that can eventually support real users.

New technologies will be introduced when they solve actual engineering problems.

For example:

- Redis when shared sessions or caching become necessary.
- Load balancing when multiple backend instances are deployed.
- Rate limiting when protecting public endpoints from abuse.
- Asynchronous processing when background workloads justify it.

The project will prioritize correctness, security, maintainability, and measurable performance over simply accumulating technologies.

---

**Thank you for your interest in the project!** >:)
