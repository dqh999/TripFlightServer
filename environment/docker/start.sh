#!/bin/bash

# Đặt cờ để script dừng lại nếu có lỗi
set -e

echo "Building the app Docker image..."
docker compose build app

echo "Starting MySQL and Redis services..."
docker compose up -d airline-mysql airline-redis

# Chờ vài giây để đảm bảo các dịch vụ phụ (MySQL, Redis) đã sẵn sàng
echo "Waiting for dependencies to be ready..."
sleep 10

echo "Starting the application service..."
docker compose up -d app

echo "All services are up and running!"
docker compose ps
