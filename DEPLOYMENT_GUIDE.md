# Uber Application - Play with Docker Deployment Guide

## Overview
This guide will help you deploy the Uber Spring Boot application on **Play with Docker** (https://labs.play-with-docker.com/).

## Prerequisites
- A Play with Docker account (free)
- Your project pushed to GitHub
- Docker and Docker Compose basics knowledge

---

## Step-by-Step Deployment Instructions

### Step 1: Prepare Your GitHub Repository

1. **Initialize Git in your project (if not already done)**:
   ```bash
   cd your-project-folder
   git init
   git add .
   git commit -m "Initial commit: Uber application setup"
   ```

2. **Create a GitHub repository and push your code**:
   ```bash
   git remote add origin https://github.com/YOUR_USERNAME/UberApplication.git
   git branch -M main
   git push -u origin main
   ```

3. **Make sure your repository is PUBLIC** (or Play with Docker can access it)

---

### Step 2: Log in to Play with Docker

1. Go to https://labs.play-with-docker.com/
2. Click **"Sign In"** and authenticate with your Docker Hub or GitHub account
3. Click **"+ ADD NEW INSTANCE"** to create a new instance
4. You'll get a terminal with a Docker environment ready

---

### Step 3: Clone Your Repository

In the Play with Docker terminal, run:

```bash
git clone https://github.com/YOUR_USERNAME/UberApplication.git
cd UberApplication
```

Replace `YOUR_USERNAME` with your actual GitHub username.

---

### Step 4: Update application.yml for Docker Environment

**Important**: Configure database connection for Docker container communication.

Edit `src/main/resources/application.yml`:

```yaml
server:
  port: 8080

spring:
  application:
    name: uber-application

  datasource:
    driver-class-name: org.postgresql.Driver
    url: jdbc:postgresql://postgres:5432/uberdb
    username: uber
    password: uber123

  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    open-in-view: false

  security:
    oauth2:
      client:
        registration:
          google:
            client-id: YOUR_GOOGLE_CLIENT_ID
            client-secret: YOUR_GOOGLE_CLIENT_SECRET
          github:
            client-id: YOUR_GITHUB_CLIENT_ID
            client-secret: YOUR_GITHUB_CLIENT_SECRET
          facebook:
            client-id: YOUR_FACEBOOK_CLIENT_ID
            client-secret: YOUR_FACEBOOK_CLIENT_SECRET

        provider:
          facebook:
            authorization-uri: https://www.facebook.com/v18.0/dialog/oauth
            token-uri: https://graph.facebook.com/v18.0/oauth/access_token
            user-info-uri: https://graph.facebook.com/me?fields=id,name,email,picture
            user-name-attribute: id

springdoc:
  swagger-ui:
    path: /swagger-ui.html

management:
  endpoints:
    web:
      exposure:
        include: health,info
```

---

### Step 5: Verify Docker Files

Ensure these files exist in your project root:

**Dockerfile** - Multi-stage build for optimal image size:
```dockerfile
# ---------- BUILD STAGE ----------
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests

# ---------- RUN STAGE ----------
FROM eclipse-temurin:21-jdk
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
```

**docker-compose.yml** - Orchestrates PostgreSQL and Spring Boot:
```yaml
version: "3.9"

services:
  postgres:
    image: postgis/postgis:15-3.4
    container_name: uber-postgres
    environment:
      POSTGRES_DB: uberdb
      POSTGRES_USER: uber
      POSTGRES_PASSWORD: uber123
    ports:
      - "5432:5432"
    healthcheck:
      test: ["CMD-SHELL", "pg_isready -U uber"]
      interval: 10s
      timeout: 5s
      retries: 5

  springboot-app:
    build: .
    container_name: uber-app
    ports:
      - "8080:8080"
    depends_on:
      postgres:
        condition: service_healthy
    environment:
      SPRING_DATASOURCE_URL: jdbc:postgresql://postgres:5432/uberdb
      SPRING_DATASOURCE_USERNAME: uber
      SPRING_DATASOURCE_PASSWORD: uber123
      SPRING_JPA_HIBERNATE_DDL_AUTO: update
      SPRING_JPA_SHOW_SQL: true
    restart: unless-stopped
```

---

### Step 6: Deploy on Play with Docker

**In the Play with Docker terminal**, run:

```bash
docker-compose up -d
```

**Wait for services to start** (2-3 minutes):
```bash
docker-compose logs -f springboot-app
```

You should see logs like:
```
springboot-app  | Started UberApplication in X seconds
```

---

### Step 7: Verify Deployment

1. **Check running containers**:
   ```bash
   docker-compose ps
   ```

2. **Test the application**:
   - Swagger UI: Click the port **8080** link in Play with Docker, then visit `/swagger-ui.html`
   - Health Check: `/actuator/health`
   - API Info: `/actuator/info`

3. **View logs**:
   ```bash
   docker-compose logs springboot-app
   ```

4. **Access PostgreSQL** (if needed):
   ```bash
   docker exec -it uber-postgres psql -U uber -d uberdb
   ```

---

### Step 8: Common Issues & Troubleshooting

| Issue | Solution |
|-------|----------|
| **Build fails** | Check `pom.xml` dependencies and Java version (21) |
| **Database connection fails** | Ensure `postgres` service is healthy; check service name in URL |
| **Port already in use** | Modify docker-compose.yml ports section |
| **Out of memory** | Increase Docker resource limits in Play with Docker settings |
| **OAuth not working** | Add valid credentials in application.yml |

---

### Step 9: Access Your Application

After successful deployment:

1. **Swagger UI**: `http://<PWD_IP>:8080/swagger-ui.html`
2. **Health endpoint**: `http://<PWD_IP>:8080/actuator/health`
3. **API endpoints**: Refer to your controller mappings

---

### Step 10: Useful Docker Commands

```bash
# View logs
docker-compose logs -f

# Stop services
docker-compose down

# Remove containers and volumes
docker-compose down -v

# Rebuild without cache
docker-compose build --no-cache

# Execute command in container
docker exec -it uber-app bash

# View resource usage
docker stats
```

---

## Configuration for Production

Before deploying to production, update:

1. **Database credentials** - Use strong passwords
2. **OAuth2 secrets** - Add real Google/GitHub/Facebook credentials
3. **CORS settings** - Configure allowed origins
4. **SSL/TLS** - Add HTTPS configuration
5. **Environment variables** - Use `.env` file for sensitive data

---

## Additional Resources

- [Docker Documentation](https://docs.docker.com/)
- [Docker Compose Docs](https://docs.docker.com/compose/)
- [Spring Boot Docker Guide](https://spring.io/guides/gs/spring-boot-docker/)
- [Play with Docker Docs](https://docs.docker.com/docker-for-mac/docker-toolbox/)

---

## Support

If you encounter issues:
1. Check logs: `docker-compose logs`
2. Verify images built: `docker images`
3. Check network: `docker network ls`
4. Restart services: `docker-compose restart`

Happy deploying! 🚀

