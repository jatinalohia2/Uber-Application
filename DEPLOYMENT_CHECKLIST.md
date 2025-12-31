# Uber Application - Play with Docker Deployment Checklist

## Pre-Deployment Checklist

### Repository Setup
- [ ] Project code committed to GitHub
- [ ] Repository is PUBLIC
- [ ] `.gitignore` configured properly
- [ ] No secrets in repository (credentials removed)
- [ ] README.md present in repository

### Project Files
- [ ] `pom.xml` exists and valid
- [ ] `Dockerfile` present
- [ ] `docker-compose.yml` present
- [ ] `application.yml` configured
- [ ] Source code compiles without errors

### Dependencies
- [ ] Java 21 compatible
- [ ] All Maven dependencies available
- [ ] PostgreSQL driver configured
- [ ] Spring Security dependencies added
- [ ] Spring Data JPA configured

### Configuration
- [ ] Database credentials set
- [ ] OAuth2 credentials available (if using)
- [ ] Application properties configured
- [ ] Port 8080 available
- [ ] No hardcoded secrets in code

---

## Deployment Day Checklist

### Step 1: Prepare GitHub
- [ ] Latest code pushed to main branch
- [ ] Verify repository is public
- [ ] Copy GitHub repository URL
- [ ] Note your GitHub username

### Step 2: Access Play with Docker
- [ ] Navigate to https://labs.play-with-docker.com/
- [ ] Sign in with Docker Hub or GitHub account
- [ ] Click "ADD NEW INSTANCE"
- [ ] Wait for Linux terminal to load
- [ ] Verify you can type commands

### Step 3: Clone and Navigate
- [ ] Run: `git clone https://github.com/YOUR_USERNAME/UberApplication.git`
- [ ] Run: `cd UberApplication`
- [ ] Verify files present: `ls -la`
- [ ] Check docker-compose.yml: `cat docker-compose.yml`

### Step 4: Start Deployment
- [ ] Run: `docker-compose up -d`
- [ ] Wait for containers to build (3-5 minutes)
- [ ] Observe build output for errors

### Step 5: Monitor Startup
- [ ] Run: `docker-compose logs -f springboot-app`
- [ ] Look for "Started UberApplication" message
- [ ] Verify no ERROR lines
- [ ] Press Ctrl+C to exit logs

### Step 6: Verify Services
- [ ] Run: `docker-compose ps`
- [ ] Verify both services show "Up"
- [ ] Verify both show "healthy"
- [ ] No services in "Exit" status

### Step 7: Test Connectivity
- [ ] Run: `curl http://localhost:8080/actuator/health`
- [ ] Verify response contains `"status":"UP"`
- [ ] Check database: `docker exec -it uber-postgres psql -U uber -d uberdb -c "\dt"`

### Step 8: Access Application
- [ ] Click the 8080 port link (in Play with Docker UI)
- [ ] Append `/swagger-ui.html` to the URL
- [ ] Verify Swagger UI loads
- [ ] Try a test endpoint

---

## Post-Deployment Verification

### Application Health
- [ ] Health endpoint returns UP: `curl http://localhost:8080/actuator/health`
- [ ] Swagger UI accessible: `http://localhost:8080/swagger-ui.html`
- [ ] Actuator info available: `http://localhost:8080/actuator/info`
- [ ] Metrics endpoint working: `http://localhost:8080/actuator/metrics`

### Database Connectivity
- [ ] PostgreSQL responding: `docker exec -it uber-postgres pg_isready -U uber`
- [ ] Can connect via Docker: `docker exec -it uber-postgres psql -U uber -d uberdb`
- [ ] Tables created (check via psql)
- [ ] No connection errors in logs

### API Functionality
- [ ] Swagger UI loads without 404 errors
- [ ] Can see API endpoints in Swagger
- [ ] Try a GET endpoint
- [ ] Check authentication endpoints

### Logs Analysis
- [ ] No ERROR level logs in springboot-app
- [ ] No WARN logs about missing configurations
- [ ] Database migrations completed successfully
- [ ] Application startup completed within 60 seconds

---

## Troubleshooting Checklist

### If Build Fails
- [ ] Check error message in terminal
- [ ] Verify pom.xml is valid XML
- [ ] Ensure Java 21 is targeted
- [ ] Check Maven dependencies are available
- [ ] Try rebuild: `docker-compose build --no-cache`

### If Application Won't Start
- [ ] Check logs: `docker-compose logs -f springboot-app`
- [ ] Verify database is running: `docker-compose ps`
- [ ] Check database connection string in logs
- [ ] Verify no port conflicts
- [ ] Try restart: `docker-compose restart`

### If Can't Connect to Database
- [ ] Verify postgres container is running: `docker-compose ps`
- [ ] Check postgres logs: `docker-compose logs postgres`
- [ ] Verify database credentials match application.yml
- [ ] Ensure service name is correct: `postgres`
- [ ] Try restart database: `docker-compose restart postgres`

### If API Endpoints Return 404
- [ ] Wait 40+ seconds for full startup (health check start_period)
- [ ] Check if application is ready: `curl http://localhost:8080/actuator/health`
- [ ] Verify controller mapping is correct
- [ ] Check for any initialization errors in logs
- [ ] Try refreshing page after 30 seconds

### If Port 8080 is Already in Use
- [ ] Edit docker-compose.yml
- [ ] Change port mapping: `- "9080:8080"`
- [ ] Restart services: `docker-compose down && docker-compose up -d`
- [ ] Access on new port: `http://localhost:9080`

---

## Performance Verification

### Container Health
- [ ] All containers show "healthy" in `docker-compose ps`
- [ ] No container restarts: `docker-compose ps` (check RESTARTS column)
- [ ] Resource usage acceptable: `docker stats`

### Response Times
- [ ] Health endpoint responds in < 500ms
- [ ] API endpoints respond in < 2 seconds
- [ ] Database queries complete within reasonable time

### Resource Usage
- [ ] CPU usage < 80%
- [ ] Memory usage reasonable (< 1GB for demo)
- [ ] Disk space adequate for volumes

---

## Security Verification

### Before Production
- [ ] Default database password changed
- [ ] OAuth2 credentials configured
- [ ] CORS settings appropriate
- [ ] SSL/HTTPS enabled (if accessible externally)
- [ ] No debug mode enabled
- [ ] Actuator endpoints restricted (in production)

### Sensitive Data
- [ ] No credentials in docker-compose.yml
- [ ] No secrets in application.yml
- [ ] Environment variables used for secrets
- [ ] .env file not committed to git

---

## Documentation Checklist

- [ ] DEPLOYMENT_GUIDE.md reviewed
- [ ] PWD_DEPLOYMENT_README.md reviewed
- [ ] QUICK_REFERENCE.md bookmarked
- [ ] Important endpoints documented
- [ ] Troubleshooting section saved

---

## Final Confirmation

- [ ] ✅ Application is running
- [ ] ✅ Database is connected
- [ ] ✅ API endpoints are accessible
- [ ] ✅ Swagger UI is working
- [ ] ✅ Health check returns UP
- [ ] ✅ No critical errors in logs
- [ ] ✅ Can access at least one API endpoint

---

## Post-Deployment Tasks

- [ ] Document any custom configurations
- [ ] Note session expiration time (4 hours for Play with Docker)
- [ ] Set up monitoring/alerts (if available)
- [ ] Configure backups (if needed)
- [ ] Document any issues encountered
- [ ] Create runbooks for common operations

---

## Useful Commands Reference

```bash
# Start/Stop
docker-compose up -d           # Start
docker-compose down            # Stop
docker-compose restart         # Restart

# Monitoring
docker-compose logs -f         # View logs
docker-compose ps              # List containers
docker stats                   # Resource usage

# Database
docker exec -it uber-postgres psql -U uber -d uberdb

# Cleanup
docker-compose down -v         # Remove everything
docker system prune -a         # Clean up unused resources
```

---

## Support Contacts & Resources

- Docker Documentation: https://docs.docker.com/
- Spring Boot Guide: https://spring.io/guides/gs/spring-boot-docker/
- Play with Docker: https://labs.play-with-docker.com/
- PostgreSQL Docs: https://www.postgresql.org/docs/

---

**Last Updated**: December 31, 2025
**Deployment Guide Version**: 1.0
**Recommended Java Version**: 21
**Recommended Spring Boot Version**: 3.3.4

