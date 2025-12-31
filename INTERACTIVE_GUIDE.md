# 🎯 Step-by-Step Interactive Deployment Guide

**Estimated Time**: 10-15 minutes (including 3-5 minute build time)

---

## BEFORE YOU START ✅

Make sure you have:
- [ ] Code saved locally
- [ ] Git installed
- [ ] GitHub account
- [ ] Internet connection
- [ ] This repository will be **PUBLIC** on GitHub

---

## PHASE 1: PREPARE YOUR CODE (2 minutes)

### Step 1A: Initialize Git (If not done)

Open your terminal in the project folder and run:

```bash
git init
```

**✅ What to expect**: `Initialized empty Git repository in ...`

---

### Step 1B: Stage and Commit Your Code

```bash
git add .
```

**✅ What to expect**: No output = success

```bash
git commit -m "Initial commit: Uber application setup"
```

**✅ What to expect**: Shows file count and sizes committed

---

### Step 1C: Add Remote Repository

Replace `YOUR_USERNAME` with your GitHub username:

```bash
git remote add origin https://github.com/YOUR_USERNAME/UberApplication.git
```

**✅ What to expect**: No output = success

---

### Step 1D: Rename Branch to Main

```bash
git branch -M main
```

**✅ What to expect**: No output = success

---

### Step 1E: Push to GitHub

```bash
git push -u origin main
```

**✅ What to expect**: 
```
Enumerating objects...
Counting objects...
Compressing objects...
Writing objects... 100% (X/X)
...
branch 'main' set up to track 'origin/main'
```

---

## PHASE 2: VERIFY GITHUB (1 minute)

### Step 2A: Open Your GitHub Repository

Go to: `https://github.com/YOUR_USERNAME/UberApplication`

**✅ What to expect**: 
- See your code files listed
- "Public" badge visible
- See Dockerfile and docker-compose.yml

---

### Step 2B: Copy Repository URL

Click the **Code** button (green)  
Click the **copy** icon next to the HTTPS URL

**✅ URL should look like**: `https://github.com/YOUR_USERNAME/UberApplication.git`

---

## PHASE 3: ACCESS PLAY WITH DOCKER (2 minutes)

### Step 3A: Go to Play with Docker

Navigate to: https://labs.play-with-docker.com/

**✅ What to expect**: See the Play with Docker interface

---

### Step 3B: Sign In

Click **Sign In**  
Choose **Docker Hub** or **GitHub**  
Complete authentication

**✅ What to expect**: Redirected to main dashboard

---

### Step 3C: Add New Instance

Click **+ ADD NEW INSTANCE**

**✅ What to expect**: 
- New session appears
- Linux terminal ready
- You can type commands

---

## PHASE 4: CLONE YOUR REPOSITORY (1 minute)

### Step 4A: Clone the Repository

In the Play with Docker terminal, paste and run:

```bash
git clone https://github.com/YOUR_USERNAME/UberApplication.git
```

**Replace YOUR_USERNAME** with your actual username

**✅ What to expect**:
```
Cloning into 'UberApplication'...
remote: Enumerating objects...
...
Receiving objects: 100% (XX/XX), YYY KiB | ZZZ KiB/s
```

---

### Step 4B: Navigate to Project

```bash
cd UberApplication
```

**✅ What to expect**: Prompt changes to `UberApplication`

---

### Step 4C: Verify Files

```bash
ls -la
```

**✅ What to expect**: See these key files:
- Dockerfile
- docker-compose.yml
- pom.xml
- src/ (folder)

---

## PHASE 5: START DEPLOYMENT (1 minute)

### Step 5A: Launch Docker Compose

```bash
docker-compose up -d
```

**✅ What to expect**:
```
Creating uber-postgres ... done
Creating uber-app ... done
```

---

### Step 5B: Monitor the Build (3-5 minutes)

This is the longest step. Run:

```bash
docker-compose logs -f springboot-app
```

**✅ What to expect** (Watch for these stages):

**Stage 1 - Pulling Images** (1-2 min):
```
Building uber-app
Step 1/9 : FROM maven:3.9.9-eclipse-temurin-21 AS build
Pulling image...
```

**Stage 2 - Downloading Dependencies** (1 min):
```
[INFO] Downloading from central repository...
[INFO] Downloaded from central: ...
```

**Stage 3 - Building** (1-2 min):
```
[INFO] Compiling...
[INFO] Building jar...
```

**Stage 4 - Starting** (~30 sec):
```
Started UberApplication in X.XXX seconds
```

**🎉 SUCCESS** - You'll see:
```
Started UberApplication in 45.123 seconds (JVM running for 48.456)
```

---

### Step 5C: Exit Logs

Press **CTRL + C** to stop viewing logs

**✅ What to expect**: Prompt returns

---

## PHASE 6: VERIFY SERVICES (2 minutes)

### Step 6A: Check Container Status

```bash
docker-compose ps
```

**✅ What to expect**:
```
NAME              STATUS              PORTS
uber-postgres     Up 5 minutes        0.0.0.0:5432->5432/tcp
                  (healthy)
                  
uber-app          Up 4 minutes        0.0.0.0:8080->8080/tcp
                  (healthy)
```

**🔴 Red flags**:
- Status shows "Exit" → Application crashed
- Status shows "unhealthy" → Service not ready

---

### Step 6B: Test Database Connection

```bash
curl http://localhost:8080/actuator/health
```

**✅ What to expect**:
```json
{
  "status": "UP",
  "components": {
    "db": {
      "status": "UP"
    },
    ...
  }
}
```

---

### Step 6C: Test Database Directly

```bash
docker exec -it uber-postgres psql -U uber -d uberdb -c "\dt"
```

**✅ What to expect**: List of tables (may be empty initially)

---

## PHASE 7: ACCESS YOUR APPLICATION (2 minutes)

### Step 7A: Click Port Link

In the Play with Docker interface, look for:
- A blue **8080** link at the top
- Click it

**✅ What to expect**: New browser tab opens showing:
```
Connection refused or similar
(This is normal - we need to add the path)
```

---

### Step 7B: Access Swagger UI

In the URL bar, append: `/swagger-ui.html`

**Full URL should be**: `http://someurl:8080/swagger-ui.html`

**✅ What to expect**: 
- Swagger interface loads
- See "Uber Application" at the top
- List of API endpoints shown

---

### Step 7C: Explore APIs

Click on different endpoints (like `/api/riders`)

**✅ What to expect**: Can expand and see details

---

### Step 7D: Try a Test Request

1. Click an endpoint to expand it
2. Click "Try it out" button
3. Click "Execute"

**✅ What to expect**: See the response (may be empty data or error if no data exists)

---

## PHASE 8: TROUBLESHOOTING (If Needed)

### ❌ Problem: Build Failed

```bash
docker-compose logs springboot-app | tail -50
```

**Look for**: Error messages in red  
**Action**: Check pom.xml for syntax errors

---

### ❌ Problem: Application Won't Start

```bash
docker-compose logs -f springboot-app
```

**Look for**: Database connection errors  
**Action**: Restart services

```bash
docker-compose restart
```

---

### ❌ Problem: Can't Access Swagger UI

**Action 1**: Wait 60 seconds and try again  
**Action 2**: Check health endpoint

```bash
curl http://localhost:8080/actuator/health
```

**Action 3**: If still not working, restart

```bash
docker-compose down
docker-compose up -d
```

---

### ❌ Problem: Port 8080 Not Working

**Action**: Check port mapping

```bash
docker-compose ps
```

If port shows differently, adjust URL accordingly.

---

## PHASE 9: USEFUL COMMANDS (Reference)

Keep these handy:

```bash
# 📊 View Status
docker-compose ps

# 📝 View Logs
docker-compose logs -f
docker-compose logs -f springboot-app
docker-compose logs --tail=50

# 🔄 Restart
docker-compose restart

# 🛑 Stop
docker-compose stop

# ▶️ Start
docker-compose start

# 🧹 Clean Up
docker-compose down

# 🗑️ Remove Everything
docker-compose down -v

# 🔌 Test Health
curl http://localhost:8080/actuator/health

# 💾 Database Access
docker exec -it uber-postgres psql -U uber -d uberdb
```

---

## PHASE 10: SUCCESS CHECKLIST ✅

Verify your deployment:

- [ ] `docker-compose ps` shows both containers as "healthy"
- [ ] Swagger UI loads without errors
- [ ] Health endpoint returns "UP"
- [ ] Can see API endpoints in Swagger
- [ ] Application started in logs (no ERROR messages)
- [ ] Database tables exist (check with psql)

**If all boxes checked** → 🎉 **Deployment Successful!**

---

## 🎓 What You've Done

1. ✅ Prepared code locally
2. ✅ Pushed to public GitHub
3. ✅ Accessed Play with Docker
4. ✅ Cloned your repository
5. ✅ Built Docker image
6. ✅ Deployed services
7. ✅ Verified everything works
8. ✅ Tested application

---

## 📈 Next Steps

Now that your application is running:

1. **Test API Endpoints**
   - Try different endpoints in Swagger
   - Create test data
   - Verify responses

2. **Explore the Application**
   - Check database structure
   - Review logs
   - Test error handling

3. **Configure OAuth2** (Optional)
   - Add Google credentials
   - Add GitHub credentials
   - Test authentication

4. **Set Up Monitoring**
   - Watch application logs
   - Monitor resource usage
   - Check health periodically

---

## 💡 Pro Tips

- **Session expires**: Play with Docker sessions last 4 hours
- **Save your work**: Document any changes
- **Plan upgrades**: Test new versions locally first
- **Backup data**: Export important data before stopping
- **Monitor resources**: Use `docker stats` to watch usage

---

## 🆘 Need Help?

1. **Check Logs First**
   ```bash
   docker-compose logs -f
   ```

2. **Verify Services**
   ```bash
   docker-compose ps
   ```

3. **Test Connectivity**
   ```bash
   curl http://localhost:8080/actuator/health
   ```

4. **Review Documentation**
   - DEPLOYMENT_GUIDE.md
   - QUICK_REFERENCE.md
   - Troubleshooting sections

---

## 🎉 Congratulations!

Your Uber application is now deployed on Play with Docker!

**You've successfully:**
- ✅ Deployed a Spring Boot application
- ✅ Set up a PostgreSQL database
- ✅ Exposed REST APIs via Swagger
- ✅ Monitored application health
- ✅ Accessed a production-like environment

**Ready for the next adventure?** 🚀

---

**Time Tracking**:
- Phase 1 (Prepare): 2 min
- Phase 2 (Verify): 1 min
- Phase 3 (Access PWD): 2 min
- Phase 4 (Clone): 1 min
- Phase 5 (Deploy): 5 min ⏳ (mostly waiting)
- Phase 6 (Verify): 2 min
- Phase 7 (Access): 2 min
- **Total: ~15 minutes**

---

**Good luck!** 🍀

