@echo off
REM Uber Application - Docker Deployment Script for Windows
REM This script automates the deployment process

setlocal enabledelayedexpansion

cls
echo ==========================================
echo Uber Application - Docker Deployment
echo ==========================================
echo.

REM Step 1: Check if Docker is installed
echo [Step 1] Checking Docker installation...
docker --version >nul 2>&1
if errorlevel 1 (
    echo X Docker is not installed
    pause
    exit /b 1
)
echo OK Docker found
echo.

REM Step 2: Check if Docker Compose is installed
echo [Step 2] Checking Docker Compose installation...
docker-compose --version >nul 2>&1
if errorlevel 1 (
    echo X Docker Compose is not installed
    pause
    exit /b 1
)
echo OK Docker Compose found
echo.

REM Step 3: Check if we're in the correct directory
echo [Step 3] Verifying project structure...
if not exist "pom.xml" (
    echo X pom.xml not found
    pause
    exit /b 1
)
if not exist "docker-compose.yml" (
    echo X docker-compose.yml not found
    pause
    exit /b 1
)
if not exist "Dockerfile" (
    echo X Dockerfile not found
    pause
    exit /b 1
)
echo OK Project structure verified
echo.

REM Step 4: Stop any existing containers
echo [Step 4] Stopping existing containers...
docker-compose down 2>nul
echo OK Cleaned up existing containers
echo.

REM Step 5: Build Docker images
echo [Step 5] Building Docker images...
docker-compose build
if errorlevel 1 (
    echo X Build failed
    pause
    exit /b 1
)
echo OK Build completed
echo.

REM Step 6: Start services
echo [Step 6] Starting services...
docker-compose up -d
if errorlevel 1 (
    echo X Failed to start services
    pause
    exit /b 1
)
echo OK Services started
echo.

REM Step 7: Display service status
echo [Step 7] Service Status:
docker-compose ps
echo.

REM Step 8: Display access information
echo.
echo ==========================================
echo OK Deployment Successful!
echo ==========================================
echo.
echo Access your application:
echo   Swagger UI: http://localhost:8080/swagger-ui.html
echo   Health Check: http://localhost:8080/actuator/health
echo   API Info: http://localhost:8080/actuator/info
echo.
echo Useful commands:
echo   View logs:           docker-compose logs -f
echo   Stop services:       docker-compose down
echo   Restart services:    docker-compose restart
echo   Remove all data:     docker-compose down -v
echo.
pause

