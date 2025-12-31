#!/bin/bash

# Uber Application - Play with Docker Quick Start Script
# This script automates the deployment process

set -e

echo "=========================================="
echo "Uber Application - Docker Deployment"
echo "=========================================="
echo ""

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Step 1: Check if Docker is installed
echo -e "${YELLOW}[Step 1]${NC} Checking Docker installation..."
if ! command -v docker &> /dev/null; then
    echo -e "${RED}✗ Docker is not installed${NC}"
    exit 1
fi
echo -e "${GREEN}✓ Docker found${NC}"
echo ""

# Step 2: Check if Docker Compose is installed
echo -e "${YELLOW}[Step 2]${NC} Checking Docker Compose installation..."
if ! command -v docker-compose &> /dev/null; then
    echo -e "${RED}✗ Docker Compose is not installed${NC}"
    exit 1
fi
echo -e "${GREEN}✓ Docker Compose found${NC}"
echo ""

# Step 3: Check if we're in the correct directory
echo -e "${YELLOW}[Step 3]${NC} Verifying project structure..."
if [ ! -f "pom.xml" ] || [ ! -f "docker-compose.yml" ] || [ ! -f "Dockerfile" ]; then
    echo -e "${RED}✗ Required files not found (pom.xml, docker-compose.yml, Dockerfile)${NC}"
    exit 1
fi
echo -e "${GREEN}✓ Project structure verified${NC}"
echo ""

# Step 4: Stop any existing containers
echo -e "${YELLOW}[Step 4]${NC} Stopping existing containers..."
docker-compose down 2>/dev/null || true
echo -e "${GREEN}✓ Cleaned up existing containers${NC}"
echo ""

# Step 5: Build and start services
echo -e "${YELLOW}[Step 5]${NC} Building Docker images..."
docker-compose build
echo -e "${GREEN}✓ Build completed${NC}"
echo ""

echo -e "${YELLOW}[Step 6]${NC} Starting services..."
docker-compose up -d
echo -e "${GREEN}✓ Services started${NC}"
echo ""

# Step 7: Wait for services to be ready
echo -e "${YELLOW}[Step 7]${NC} Waiting for services to be ready..."
echo "Waiting for PostgreSQL..."
until docker exec uber-postgres pg_isready -U uber > /dev/null 2>&1; do
  sleep 2
  echo -n "."
done
echo -e "${GREEN}✓ PostgreSQL is ready${NC}"

echo "Waiting for Spring Boot application..."
for i in {1..60}; do
  if curl -f http://localhost:8080/actuator/health > /dev/null 2>&1; then
    echo -e "${GREEN}✓ Application is ready${NC}"
    break
  fi
  sleep 2
  echo -n "."
  if [ $i -eq 60 ]; then
    echo -e "${RED}✗ Application startup timeout${NC}"
    exit 1
  fi
done
echo ""

# Step 8: Display service status
echo -e "${YELLOW}[Step 8]${NC} Verifying service status..."
docker-compose ps
echo ""

# Step 9: Display access information
echo -e "${GREEN}=========================================="
echo "✓ Deployment Successful!"
echo "=========================================${NC}"
echo ""
echo "Access your application:"
echo -e "  ${GREEN}Swagger UI:${NC} http://localhost:8080/swagger-ui.html"
echo -e "  ${GREEN}Health Check:${NC} http://localhost:8080/actuator/health"
echo -e "  ${GREEN}API Info:${NC} http://localhost:8080/actuator/info"
echo ""
echo "Useful commands:"
echo -e "  ${YELLOW}View logs:${NC}           docker-compose logs -f"
echo -e "  ${YELLOW}Stop services:${NC}       docker-compose down"
echo -e "  ${YELLOW}Restart services:${NC}    docker-compose restart"
echo -e "  ${YELLOW}Remove all data:${NC}     docker-compose down -v"
echo ""

