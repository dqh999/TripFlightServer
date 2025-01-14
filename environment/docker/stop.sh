#!/bin/bash

# Đặt cờ để script dừng lại nếu có lỗi
set -e

echo "Stopping all running services..."
docker compose down

echo "Cleaning up unused Docker resources..."
docker system prune -f

echo "All services have been stopped and cleaned up!"
