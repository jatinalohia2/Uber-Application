# 🚀 Uber Application - Deploy to Play with Docker

Complete step-by-step guide to deploy the Uber Spring Boot application on Play with Docker.

## 📋 Table of Contents

1. [Quick Start](#quick-start)
2. [Detailed Instructions](#detailed-instructions)
3. [Verification](#verification)
4. [Troubleshooting](#troubleshooting)
5. [Advanced Configuration](#advanced-configuration)

---

## ⚡ Quick Start

### For Linux/Mac:
```bash
# Clone the repository
git clone https://github.com/YOUR_USERNAME/UberApplication.git
cd UberApplication

# Run deployment
chmod +x deploy.sh
./deploy.sh
```

### For Windows:
```cmd
# Clone the repository
git clone https://github.com/YOUR_USERNAME/UberApplication.git
cd UberApplication

# Run deployment
deploy.bat
```

---

## 📖 Detailed Instructions

### Step 1: Prepare GitHub Repository

```bash
# Initialize and push to GitHub
git init
git add .
git commit -m "Initial commit: Uber application"
git remote add origin https://github.com/YOUR_USERNAME/UberApplication.git
git branch -M main
git push -u origin main
```

**⚠️ Important**: Make your repository PUBLIC so Play with Docker can access it.

---

### Step 2: Access Play with Docker

1. Navigate to: https://labs.play-with-docker.com/
2. Sign in with Docker Hub or GitHub
3. Click **"+ ADD NEW INSTANCE"**
4. You'll get a Linux terminal environment

---

### Step 3: Clone and Deploy in Play with Docker Terminal

```bash
# Clone your repository
git clone https://github.com/YOUR_USERNAME/UberApplication.git
cd UberApplication

# Deploy using Docker Compose
docker-compose up -d

# Monitor the build and startup (takes ~3-5 minutes)
docker-compose logs -f springboot-app
```

**Expected output** (when ready):
```
springboot-app  | Started UberApplication in X.XXX seconds
```

---

### Step 4: Verify Services Are Running

```bash
# Check container status
docker-compose ps

# Should show:
# NAME              STATUS
# uber-postgres     Up X minutes (healthy)
# uber-app          Up X minutes (healthy)
```

---

## ✅ Verification

### Test Connectivity

```bash
# Check PostgreSQL
docker exec uber-postgres psql -U uber -d uberdb -c "\dt"

# Check Spring Boot health
curl http://localhost:8080/actuator/health

# Expected response:
# {"status":"UP","components":{"db":{"status":"UP"},...}}
```

### Access via Browser

Play with Docker provides clickable port links. After `docker-compose up -d`:

- **Click the 8080 port** link at the top
- Append paths:
  - `/swagger-ui.html` - Interactive API documentation
  - `/actuator/health` - Application health
  - `/actuator/info` - Application info

### Sample API Calls (via Swagger UI)

```bash
# Health check
curl http://localhost:8080/actuator/health

# Application info
curl http://localhost:8080/actuator/info
```

---

## 🔧 Troubleshooting

### Issue: "Build fails with compilation error"

**Solution:**
```bash
# Check Java version compatibility
docker exec uber-app java -version

# View build logs
docker-compose logs springboot-app

# Clean build
docker-compose build --no-cache
docker-compose up -d
```

### Issue: "Application crashes after starting"

**Solution:**
```bash
# View detailed logs
docker-compose logs -f springboot-app

# Check database connectivity
docker exec uber-app curl -v http://postgres:5432/

# Restart services
docker-compose restart
```

### Issue: "Database connection refused"

**Solution:**
```bash
# Verify PostgreSQL is running
docker-compose ps

# Check database logs
docker-compose logs postgres

# Restart database service
docker-compose restart postgres
```

### Issue: "Port 8080 already in use"

**Solution:**

Edit `docker-compose.yml`:
```yaml
services:
  springboot-app:
    ports:
      - "9090:8080"  # Map to different port
```

Then restart:
```bash
docker-compose down
docker-compose up -d
```

### Issue: "Swagger UI returns 404"

**Solution:**
```bash
# Verify the application is fully loaded
curl http://localhost:8080/actuator/health

# Wait for startup_period (40 seconds)
# Check logs
docker-compose logs springboot-app
```

---

## 🔐 Advanced Configuration

### Using Environment Variables

Create `.env` file in project root:

```env
POSTGRES_DB=uberdb
POSTGRES_USER=uber
POSTGRES_PASSWORD=secure_password_here

SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/uberdb
SPRING_DATASOURCE_USERNAME=uber
SPRING_DATASOURCE_PASSWORD=secure_password_here

GOOGLE_CLIENT_ID=your_google_id
GOOGLE_CLIENT_SECRET=your_google_secret

GITHUB_CLIENT_ID=your_github_id
GITHUB_CLIENT_SECRET=your_github_secret

FACEBOOK_CLIENT_ID=your_facebook_id
FACEBOOK_CLIENT_SECRET=your_facebook_secret
```

Then Docker Compose will automatically load these values.

### Custom Port Mapping

Edit `docker-compose.yml`:

```yaml
services:
  springboot-app:
    ports:
      - "9080:8080"  # Access on port 9080

  postgres:
    ports:
      - "5433:5432"  # PostgreSQL on port 5433
```

### Enable Additional Endpoints

Edit `application.yml`:

```yaml
management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics,prometheus  # Add more endpoints
```

### Database Persistence

Data is automatically persisted via volumes defined in `docker-compose.yml`:

```yaml
volumes:
  postgres_data:  # PostgreSQL data persists
```

To clear all data:
```bash
docker-compose down -v  # -v removes volumes
```

---

## 📊 Useful Commands

```bash
# Build and start
docker-compose up -d

# View logs
docker-compose logs -f                    # All services
docker-compose logs -f springboot-app     # Specific service
docker-compose logs --tail=50             # Last 50 lines

# Manage services
docker-compose ps                         # List containers
docker-compose restart                    # Restart all
docker-compose restart springboot-app     # Restart specific
docker-compose stop                       # Stop all
docker-compose start                      # Start all
docker-compose down                       # Stop and remove

# Database access
docker exec -it uber-postgres psql -U uber -d uberdb

# Application shell
docker exec -it uber-app bash

# View resource usage
docker stats

# Rebuild without cache
docker-compose build --no-cache

# Remove everything
docker-compose down -v --remove-orphans
```

---

## 📈 Monitoring & Performance

### Real-time Monitoring

```bash
# CPU, Memory, Network usage
docker stats
```

### Application Metrics

Access via Actuator:
```bash
curl http://localhost:8080/actuator/metrics
curl http://localhost:8080/actuator/metrics/jvm.memory.usage
```

### Database Queries

View executed SQL:
```bash
# From logs (SPRING_JPA_SHOW_SQL=true already set)
docker-compose logs springboot-app | grep "Hibernate:"
```

---

## 🚀 Production Deployment Checklist

- [ ] Use strong database passwords
- [ ] Add valid OAuth2 credentials
- [ ] Configure CORS for your domain
- [ ] Set `SPRING_JPA_HIBERNATE_DDL_AUTO=validate` (not `update`)
- [ ] Enable HTTPS/SSL
- [ ] Configure logging levels
- [ ] Set up database backups
- [ ] Monitor resource usage
- [ ] Configure auto-restart policies
- [ ] Set up health check notifications

---

## 📞 Getting Help

### Common Resources

- [Docker Documentation](https://docs.docker.com/)
- [Docker Compose Reference](https://docs.docker.com/compose/compose-file/)
- [Spring Boot Docker Guide](https://spring.io/guides/gs/spring-boot-docker/)
- [Play with Docker Documentation](https://docs.docker.com/desktop/)

### Debugging Steps

1. Check logs: `docker-compose logs -f`
2. Verify images: `docker images`
3. Inspect containers: `docker inspect uber-app`
4. Check network: `docker network ls`
5. Validate compose file: `docker-compose config`

---

## 🎉 Next Steps

After successful deployment:

1. **Test API endpoints** via Swagger UI
2. **Create sample data** using API calls
3. **Configure OAuth2** providers (Google, GitHub, Facebook)
4. **Set up monitoring** and alerting
5. **Configure CI/CD** for automated deployments

---

## 📝 Notes

- Play with Docker sessions expire after 4 hours
- Build takes ~3-5 minutes on first run
- Subsequent builds are faster due to Docker caching
- All data is stored in Docker volumes
- Use `docker-compose down -v` to delete everything

---

**Happy Deploying! 🚀**

