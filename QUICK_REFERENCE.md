# 🚀 Play with Docker - Quick Reference Card

## 30-Second Deploy

```bash
git clone https://github.com/YOUR_USERNAME/UberApplication.git
cd UberApplication
docker-compose up -d
```

**Wait 2-3 minutes for build and startup...**

Then access: `http://localhost:8080/swagger-ui.html`

---

## One-Line Commands

| Task | Command |
|------|---------|
| **Deploy** | `docker-compose up -d` |
| **Stop** | `docker-compose down` |
| **Logs** | `docker-compose logs -f` |
| **Status** | `docker-compose ps` |
| **Rebuild** | `docker-compose build --no-cache && docker-compose up -d` |
| **Clean** | `docker-compose down -v` |
| **Database shell** | `docker exec -it uber-postgres psql -U uber -d uberdb` |
| **App shell** | `docker exec -it uber-app bash` |

---

## Important Endpoints

| Endpoint | URL |
|----------|-----|
| **Swagger UI** | `http://localhost:8080/swagger-ui.html` |
| **Health Check** | `http://localhost:8080/actuator/health` |
| **Metrics** | `http://localhost:8080/actuator/metrics` |
| **App Info** | `http://localhost:8080/actuator/info` |

---

## Environment Variables

```bash
# Database
SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/uberdb
SPRING_DATASOURCE_USERNAME=uber
SPRING_DATASOURCE_PASSWORD=uber123

# OAuth2 (Update with real credentials)
GOOGLE_CLIENT_ID=your_id
GOOGLE_CLIENT_SECRET=your_secret
GITHUB_CLIENT_ID=your_id
GITHUB_CLIENT_SECRET=your_secret
```

---

## Troubleshooting

| Problem | Solution |
|---------|----------|
| Build fails | `docker-compose build --no-cache` |
| Port in use | Edit `docker-compose.yml` ports section |
| DB won't connect | `docker-compose logs postgres` |
| App crashes | `docker-compose logs -f springboot-app` |
| Can't reach API | Wait 40 seconds for startup |

---

## File Structure

```
UberApplication/
├── Dockerfile              # Build instructions
├── docker-compose.yml      # Services orchestration
├── deploy.sh               # Linux/Mac deploy script
├── deploy.bat              # Windows deploy script
├── .env.example            # Environment template
├── pom.xml                 # Maven dependencies
├── src/
│   └── main/
│       ├── java/           # Source code
│       └── resources/
│           └── application.yml
└── README.md
```

---

## Pre-requisites

- ✅ Docker installed (or use Play with Docker web interface)
- ✅ Code pushed to public GitHub repository
- ✅ Java 21 compatible code
- ✅ PostgreSQL database configured

---

## Deployment Flow

```
1. Clone Repository
   ↓
2. Navigate to project
   ↓
3. Run: docker-compose up -d
   ↓
4. Wait for build (3-5 min)
   ↓
5. Access application
   ↓
6. Test endpoints
```

---

## Health Check Test

```bash
# Check if application is ready
curl http://localhost:8080/actuator/health

# Expected response (UP means ready)
# {"status":"UP"}
```

---

## Database Quick Access

```bash
# Connect to PostgreSQL
docker exec -it uber-postgres psql -U uber -d uberdb

# Common commands:
\dt              # List tables
\d table_name    # Describe table
SELECT * FROM users;  # Query data
```

---

## View Application Logs

```bash
# All services
docker-compose logs -f

# Spring Boot app only
docker-compose logs -f springboot-app

# PostgreSQL only
docker-compose logs -f postgres

# Last 50 lines
docker-compose logs --tail=50
```

---

## Resource Monitoring

```bash
# Real-time stats
docker stats

# Shows: CPU%, Memory%, Network I/O, etc.
```

---

## Port Mapping

| Service | Internal | External | URL |
|---------|----------|----------|-----|
| Spring Boot | 8080 | 8080 | http://localhost:8080 |
| PostgreSQL | 5432 | 5432 | localhost:5432 |

---

## Common Issues & Quick Fixes

### ❌ "Connection refused"
```bash
docker-compose restart
```

### ❌ "Port 8080 already in use"
```bash
# Edit docker-compose.yml:
# ports:
#   - "9080:8080"
docker-compose up -d
```

### ❌ "Out of memory"
```bash
# Remove unused images/containers
docker system prune -a
```

### ❌ "Build timeout"
```bash
# Rebuild without cache
docker-compose build --no-cache
```

---

## Before Production

- [ ] Change default passwords
- [ ] Add OAuth2 credentials
- [ ] Set proper environment variables
- [ ] Configure CORS
- [ ] Enable HTTPS
- [ ] Set JPA mode to `validate`
- [ ] Increase log retention
- [ ] Setup monitoring

---

## Useful Links

- 🐳 [Docker Docs](https://docs.docker.com/)
- 🚀 [Spring Boot Docker](https://spring.io/guides/gs/spring-boot-docker/)
- 📦 [Docker Compose](https://docs.docker.com/compose/)
- 🎮 [Play with Docker](https://labs.play-with-docker.com/)

---

**Remember**: Play with Docker sessions expire after 4 hours!

