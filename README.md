# Hotel Hub - Backend

This is the backend of **Hotel Hub**, a hotel booking web application. The backend is built using Spring Boot (Java) and provides RESTful APIs for hotel management, booking, and user authentication. It includes both user and admin roles with JWT-based authentication.

## Tech Stack

- **Java Spring Boot**: Backend framework.
- **Spring Security + JWT**: Authentication and authorization.
- **MySQL**: Database for storing users, hotels, and bookings.
- **Hibernate**: ORM for database interaction.
- **Maven**: For project build and dependency management.

## Features

- **User Authentication**: JWT-based authentication for login and registration.
- **Hotel Management**: API for adding, updating, and listing hotels (Admin only).
- **Booking System**: API for booking rooms and viewing booking history.
- **Admin Functionality**: Admin can manage users, rooms, and bookings.

## API Endpoints

### Authentication

- **POST** `/auth/register` - Register a new user
- **POST** `/auth/login` - Log in and receive a JWT token

### Rooms

- **POST** `/rooms/add` - Add a new room (Admin only)
- **GET** `/rooms/all` - Get a list of all rooms
- **GET** `/rooms/types` - Get all room types
- **GET** `/rooms/all-available-rooms` - Get Available rooms
- **GET** `/rooms/{id}` - Get rooms by id
- **PUT** `/rooms/update/{id}` - Update room by id (Admin only)
- **DELETE** `/rooms/delete/{id}` - Delete a room by id (Admin only)

### Bookings

- **POST** `/bookings/book-room/{roomId}/{userId}` - Book a room
- **GET** `/bookings/all` - Get booking history for a specific user
- **DELETE** `/bookings/cancel/{bookingId}` - Cancel a booking (Admin only)

### Users

- **GET** `/users/all` - Get a list of all users (Admin only)
- **GET** `/users/get-by-id/{id}` - Get details of a specific user
- **DELETE** `/users/delete/{id}` - Delete a user (Admin only)
- **GET** `/users/get-logged-in-profile-info` - Get Loggedin user
- **GET** `/users/get-user-bookings/{userId}` - Get booking of a specific user

## Setup Instructions

### Prerequisites

- Java (JDK 11 or higher)
- MySQL installed and running
- Maven (for building the Spring Boot application)

### Steps to run

1. Clone the repository:
    ```bash
    git clone https://github.com/yourusername/hotel-hub.git
    cd hotel-hub/backend
    ```

2. Configure the MySQL database connection in `application.properties`:
    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/hotel_hub_db
    spring.datasource.username=your_db_username
    spring.datasource.password=your_db_password
    ```

3. Build and run the application:
    ```bash
    mvn clean install
    mvn spring-boot:run
    ```

4. The backend will be running on `http://localhost:8080`.

## Database Schema

### Tables

- **User**: Stores user information (id, username, password, roles).
- **Hotel**: Stores hotel details (id, name, location, rooms).
- **Booking**: Stores booking details (id, user_id, hotel_id, check-in, check-out).

### Sample Structure

```sql
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255),
    password VARCHAR(255),
    roles VARCHAR(255)
);

CREATE TABLE hotels (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    location VARCHAR(255),
    rooms INT
);

CREATE TABLE bookings (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    hotel_id BIGINT,
    check_in DATE,
    check_out DATE,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (hotel_id) REFERENCES hotels(id)
);
