# Flight Ticket Booking System

## Introduction

This project is a **Flight Ticket Booking System** designed to provide a platform for users to search for available flights, book tickets, and manage their bookings. It is developed using modern technologies and follows best practices for security, testing, and deployment.

## Project Overview

The system allows users to:
- Search for available flights based on origin, destination, and date
- View detailed flight information (e.g., departure time, duration, airline)
- Book tickets with different passenger details
- Track booking history and manage current reservations

## Tech Stack

- **Programming Language**: Java 23
- **Framework**: Spring Boot 3.3.0
- **Database**: Mysql 8
- **Cache**: Redis 7
- **Unit Testing**: JUnit 5
- **Security**: JWT, Spring Security
- **Payment Gateway**: VNPay
- **Message Broker**: Kafka
- **Containerization**: Docker
- **CICD**: GitHub Actions
- **Deployment**: VPS

## Features

### 1. Flight Search
Users can search flights by providing their **origin**, **destination**, and **travel date**. The system will return all available flights for the requested dates.

### 2. Booking a Flight
Users can book a flight by providing their **passenger details**, **payment information**, and **flight selection**.

### 3. Booking Management
Users can view and manage their bookings, including **check-in date**, **checkout date**, and **number of passengers**.

## API cURL Sample

### 1. Search Flights by Parameters

```bash
curl --location 'http://localhost:8080/api/v1/flights/search?departureAirportCode=SGN&arrivalAirportCode=HAN&departureTime=2024-07-10&childSeats=0&adultSeats=1&page=1&pageSize=5&sortBy=price'
````
**Endpoint**: `/api/v1/flights/search`  
**Method**: `GET`  
**Parameters**:
- `departureAirportCode`: IATA code of the departure airport (e.g., `SGN`).
- `arrivalAirportCode`: IATA code of the arrival airport (e.g., `HAN`).
- `departureTime`: Date of departure in `YYYY-MM-DD` format.
- `childSeats`: Number of child seats.
- `adultSeats`: Number of adult seats.
- `page`: Page number for paginated results (default is `1`).
- `pageSize`: Number of results per page (default is `5`).
- `sortBy`: Sorting criteria (default is `price`).

### 2. Booking Flights