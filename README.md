# Task Management API

This project implements a simple Task Management REST API built using Java and Spring Boot.  


The API allows users to create, update, retrieve, delete, and list tasks.

---

## Tech Stack

- Java 21
- Spring Boot
- Maven
- JUnit + Mockito
- MockMvc (integration testing)
- Swagger / OpenAPI

---

## Project Structure

The project follows a simple layered architecture:

controller : Handles HTTP requests  
service : Business logic  
repository : In-memory data store  
dto : Request / response models  
exception : Custom exceptions and handlers  

---

## Task Model

Each task contains:

- id (auto generated)
- title
- description
- status (PENDING, IN_PROGRESS, DONE)
- dueDate

Default status is **PENDING**.

---

## API Endpoints

Create Task

POST /tasks

Get Task

GET /tasks/{id}

Update Task

PUT /tasks/{id}

Delete Task

DELETE /tasks/{id}

List Tasks

GET /tasks

Optional query params:

GET /tasks?status=PENDING&page=0&size=10

---

## Validation

- title is required
- dueDate must be a future date
- invalid status values return a 400 error
- invalid status transitions are rejected

---

## Running the Application

Clone the repository and run:

mvn spring-boot:run

Swagger UI:

http://localhost:8080/swagger-ui/index.html

---

## Running Tests

Run all tests with:

mvn test

Tests include:

- Unit tests for service layer
- Integration tests for REST APIs

---

## Notes

Data is stored in memory (HashMap) using a repository implementation, so all tasks are lost when the application stops.
