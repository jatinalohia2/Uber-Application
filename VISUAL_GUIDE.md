# 🎨 Uber Application - Visual Deployment Guide

## 1️⃣ Preparation Phase

```
┌─────────────────────────────────────────────────────┐
│          YOUR LOCAL MACHINE                         │
├─────────────────────────────────────────────────────┤
│                                                       │
│  1. Code Ready?  ✓                                  │
│  2. Push to GitHub (PUBLIC)  ✓                      │
│  3. Verify Dockerfile  ✓                            │
│  4. Verify docker-compose.yml  ✓                    │
│                                                       │
│                    DOWN                              │
│                      │                               │
│                      ▼                               │
│                                                       │
└─────────────────────────────────────────────────────┘

  🔗 GitHub Repository
      │
      │ PUBLIC
      │
      ▼
```

## 2️⃣ Play with Docker Setup

```
┌──────────────────────────────────────────────────┐
│  1. Visit: labs.play-with-docker.com             │
│  2. Sign In (Docker Hub / GitHub)                │
│  3. Click "+ ADD NEW INSTANCE"                   │
│  4. Get Linux Terminal Environment               │
└──────────────────────────────────────────────────┘
         ▼
     ✅ Ready!
```

## 3️⃣ Deployment Flow

```
┌─────────────────────────────────────────────────────────────┐
│                  PLAY WITH DOCKER TERMINAL                  │
└─────────────────────────────────────────────────────────────┘

git clone https://github.com/YOUR_USERNAME/UberApplication.git
                              ▼
cd UberApplication
                              ▼
docker-compose up -d
        │
        ├─→ Pulling images
        │       └─→ maven:3.9.9
        │       └─→ postgis/postgis:15-3.4
        │       └─→ eclipse-temurin:21-jdk
        │
        ├─→ Building Spring Boot Image
        │       └─→ Running Maven build
        │       └─→ Compiling Java source
        │       └─→ Creating JAR
        │
        └─→ Starting Services (2-5 min)
                ├─→ PostgreSQL (Port 5432)
                │   └─→ Health Check: pg_isready
                │
                └─→ Spring Boot App (Port 8080)
                    └─→ Health Check: /actuator/health
                    
                    ▼
                DEPLOYMENT COMPLETE ✅
```

## 4️⃣ Container Architecture

```
┌─────────────────────────────────────────────────────────────┐
│         DOCKER COMPOSE NETWORK: uber_default                │
├─────────────────────────────────────────────────────────────┤
│                                                               │
│  ┌────────────────────────┐         ┌────────────────────┐  │
│  │   POSTGRES CONTAINER   │         │  SPRING BOOT CONT. │  │
│  ├────────────────────────┤         ├────────────────────┤  │
│  │ Image: postgis/postgis │         │ Image: Custom JAR  │  │
│  │ Name: uber-postgres    │         │ Name: uber-app     │  │
│  │ Port: 5432 (internal)  │◄───────►│ Port: 8080         │  │
│  │                        │         │                    │  │
│  │ Database: uberdb       │         │ Health: Active     │  │
│  │ User: uber             │         │                    │  │
│  │ Password: uber123      │         │ Ready: ✅          │  │
│  │                        │         │                    │  │
│  │ Volume:                │         │                    │  │
│  │ postgres_data/ ────────┼─────┐   │                    │  │
│  │                        │     │   │                    │  │
│  └────────────────────────┘     │   └────────────────────┘  │
│                                  │                            │
│                          Data Persistence                    │
│                          (Preserved after                    │
│                           container stop)                    │
│                                                               │
└─────────────────────────────────────────────────────────────┘
```

## 5️⃣ Access Flow

```
┌─────────────────────────────────────────────────────────────┐
│              BROWSER / CLIENT                                │
├─────────────────────────────────────────────────────────────┤
│                                                               │
│  http://localhost:8080                                      │
│          │                                                   │
│          ├─→ /swagger-ui.html (Swagger UI)                 │
│          │   • Interactive API Documentation               │
│          │   • Test Endpoints                              │
│          │   • View Request/Response                       │
│          │                                                   │
│          ├─→ /actuator/health (Health Check)              │
│          │   • Status: UP/DOWN                             │
│          │   • Database Status                             │
│          │                                                   │
│          ├─→ /actuator/info (Application Info)            │
│          │   • Version, Name                               │
│          │   • Custom Properties                           │
│          │                                                   │
│          ├─→ /api/riders (REST Endpoints)                 │
│          │   • CRUD Operations                             │
│          │   • Business Logic                              │
│          │                                                   │
│          └─→ /api/drivers (REST Endpoints)                │
│              • Driver Management                           │
│              • Ride Operations                             │
│                                                               │
└─────────────────────────────────────────────────────────────┘
```

## 6️⃣ Timeline

```
      MINUTE 0
         │
         ├─→ docker-compose up -d
         │   (Command sent)
         │
      MINUTE 0-1
         │
         ├─→ Pulling images
         │   • 300-500 MB download
         │   (Depending on internet)
         │
      MINUTE 1-3
         │
         ├─→ Building Docker image
         │   • Maven downloading dependencies
         │   • Compiling Java source
         │   • Creating JAR file
         │   (Depends on CPU)
         │
      MINUTE 3-4
         │
         ├─→ Starting containers
         │   • PostgreSQL starts
         │   • Spring Boot initializes
         │   • Database migrations run
         │
      MINUTE 4-5
         │
         ├─→ ✅ READY!
         │
         └─→ Access: http://localhost:8080/swagger-ui.html
```

## 7️⃣ Status Indicators

### ✅ Successful Deployment

```
docker-compose ps

NAME              STATUS              PORTS
uber-postgres     Up 2 minutes        0.0.0.0:5432->5432/tcp
                  (healthy)
                  
uber-app          Up 1 minute         0.0.0.0:8080->8080/tcp
                  (healthy)
```

### Health Check Responses

```
✅ POSTGRES:
pg_isready -U uber
→ Response: "accepting connections"

✅ SPRING BOOT:
curl http://localhost:8080/actuator/health
→ Response: {"status":"UP", "components":{"db":{"status":"UP"}}}
```

## 8️⃣ Command Reference Map

```
                    DEPLOYMENT LIFECYCLE
                            │
        ┌───────────────────┼───────────────────┐
        │                   │                   │
      START               RUNNING             STOP
        │                   │                   │
        ▼                   ▼                   ▼
        
docker-compose    docker-compose logs    docker-compose
  up -d            docker-compose ps       down
  build             docker stats
  down              curl health...
```

## 9️⃣ Troubleshooting Decision Tree

```
                 ❌ ISSUE?
                     │
        ┌────────────┼────────────┐
        │            │            │
        ▼            ▼            ▼
    BUILD     START-UP         ACCESS
    FAILS     FAILS           FAILS
        │        │               │
        ├─→      │               │
    Check    Check logs       Check
    pom.xml  for errors       port
        │        │
        └────┬───┘
             │
             ▼
      docker-compose restart
             │
             ▼
          FIXED? ✅
```

## 🔟 Data Flow

```
┌─────────────────────────────────────────────────────────┐
│  CLIENT REQUEST (Browser/API Client)                   │
└─────────────────────────────────────────────────────────┘
           │
           ▼
┌─────────────────────────────────────────────────────────┐
│  SPRING BOOT APPLICATION (Port 8080)                   │
│  ├─ Request Processing                                 │
│  ├─ Business Logic                                     │
│  ├─ Authentication/Authorization                       │
│  └─ Response Formatting                                │
└─────────────────────────────────────────────────────────┘
           │
           ▼
┌─────────────────────────────────────────────────────────┐
│  POSTGRESQL DATABASE (Port 5432)                       │
│  ├─ Query Execution                                    │
│  ├─ Data Storage                                       │
│  ├─ Transaction Management                             │
│  └─ Result Return                                      │
└─────────────────────────────────────────────────────────┘
           │
           ▼
┌─────────────────────────────────────────────────────────┐
│  RESPONSE TO CLIENT                                    │
│  ├─ Status Code                                        │
│  ├─ Response Body                                      │
│  └─ Headers                                            │
└─────────────────────────────────────────────────────────┘
```

## Key Concepts

### 🐳 Docker
- **Container**: Lightweight, isolated environment
- **Image**: Blueprint for containers
- **Compose**: Tool to manage multiple containers

### 🌐 Networking
- **Internal Network**: Containers communicate via service names
- **Port Mapping**: `host:container` (e.g., 8080:8080)
- **Health Checks**: Verify service readiness

### 📊 Volumes
- **Persistence**: Data survives container restart
- **Sharing**: Share files between host and container
- **Backup**: Easy data backup and restore

### 🚀 Deployment
- **Orchestration**: Docker Compose manages services
- **Dependencies**: Ensure correct startup order
- **Auto-restart**: Recover from failures

---

**Ready to deploy? Follow this visual guide step by step!** 🎉

