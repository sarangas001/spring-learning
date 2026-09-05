# Spring Boot Learning 🚀

A hands-on repository for learning and practicing **Spring Boot** and related backend development concepts.

This repository contains examples, exercises, and small implementations created while learning Spring Boot step by step.

---

## 📚 Topics Covered

### 1. Spring Boot Fundamentals

* Introduction to Spring Boot
* Spring Boot project structure
* `@SpringBootApplication`
* Application configuration
* `application.properties`
* `application.yml`
* Maven and dependencies
* Spring Boot starters
* Running a Spring Boot application

### 2. Spring Core

* Dependency Injection
* Inversion of Control (IoC)
* Beans
* `@Component`
* `@Service`
* `@Repository`
* `@Controller`
* `@Autowired`
* Constructor Injection
* Component Scanning

### 3. REST API Development

* REST API fundamentals
* Controllers
* HTTP methods

  * GET
  * POST
  * PUT
  * PATCH
  * DELETE
* Request parameters
* Path variables
* Request bodies
* Response entities
* HTTP status codes
* JSON responses

### 4. Layered Architecture

* Controller Layer
* Service Layer
* Repository Layer
* Entity/Model Layer
* DTOs
* Separation of responsibilities

Typical flow:

```text
Client
   ↓
Controller
   ↓
Service
   ↓
Repository
   ↓
Database
```

### 5. Database Integration

* Connecting Spring Boot with databases
* MySQL
* PostgreSQL
* Database configuration
* JDBC basics
* Connection management

### 6. Spring Data JPA

* JPA fundamentals
* Hibernate
* Entities
* `@Entity`
* `@Id`
* `@GeneratedValue`
* Repository interfaces
* `JpaRepository`
* CRUD operations
* Query methods
* Custom queries

### 7. Entity Relationships

* One-to-One
* One-to-Many
* Many-to-One
* Many-to-Many
* `@OneToOne`
* `@OneToMany`
* `@ManyToOne`
* `@ManyToMany`
* Foreign keys
* Cascade operations
* Fetch types

### 8. DTOs

* What are DTOs?
* Entity vs DTO
* Request DTOs
* Response DTOs
* Mapping Entity ↔ DTO

### 9. Validation

* Request validation
* `@Valid`
* `@NotNull`
* `@NotBlank`
* `@Size`
* `@Email`
* `@Min`
* `@Max`
* Validation error handling

### 10. Exception Handling

* Custom exceptions
* Global exception handling
* `@ExceptionHandler`
* `@ControllerAdvice`
* Standardized error responses

### 11. Spring Security

* Authentication
* Authorization
* Password encoding
* Roles and permissions
* Spring Security configuration
* JWT authentication
* Protected endpoints

### 12. Testing

* Unit testing
* Integration testing
* JUnit
* Mockito
* Testing controllers
* Testing services
* Testing repositories

### 13. API Documentation

* Swagger / OpenAPI
* API documentation
* Testing APIs through Swagger UI

### 14. Advanced Topics

* Profiles
* Environment variables
* Logging
* Actuator
* Pagination
* Sorting
* Transactions
* Caching
* Scheduling
* File uploads
* Email services

---

## 🛠️ Technologies

* Java
* Spring Boot
* Spring Framework
* Spring MVC
* Spring Data JPA
* Hibernate
* Spring Security
* Maven
* MySQL / PostgreSQL
* REST API
* JUnit
* Mockito
* Swagger / OpenAPI

---

## 📁 Project Structure

The repository is organized by learning topics.

```text
spring-boot-learning/
│
├── 01-spring-boot-basics/
│
├── 02-spring-core/
│
├── 03-rest-api/
│
├── 04-layered-architecture/
│
├── 05-database/
│
├── 06-spring-data-jpa/
│
├── 07-entity-relationships/
│
├── 08-dto/
│
├── 09-validation/
│
├── 10-exception-handling/
│
├── 11-spring-security/
│
├── 12-testing/
│
├── 13-swagger/
│
└── 14-advanced-topics/
```

---

## 🎯 Learning Goals

The main goals of this repository are to:

* Understand Spring Boot fundamentals
* Build RESTful APIs
* Understand dependency injection
* Work with relational databases
* Learn JPA and Hibernate
* Implement clean layered architecture
* Handle validation and exceptions
* Implement authentication and authorization
* Write unit and integration tests
* Understand common Spring Boot backend patterns

---

## ▶️ Running the Project

### Prerequisites

Make sure you have installed:

* Java JDK
* Maven
* MySQL or PostgreSQL (depending on the example)
* Git

Check Java:

```bash
java -version
```

Check Maven:

```bash
mvn -version
```

### Clone the Repository

```bash
git clone https://github.com/your-username/spring-boot-learning.git
```

```bash
cd spring-boot-learning
```

### Run a Project

Navigate to the required topic:

```bash
cd 01-spring-boot-basics
```

Then run:

```bash
mvn spring-boot:run
```

Or run the application from your IDE.

---

## 🧪 API Testing

For REST API examples, you can use:

* Postman
* Swagger UI
* IntelliJ IDEA HTTP Client
* cURL

Example:

```http
GET /api/users
```

---

## 📝 Learning Approach

Each topic is implemented with practical examples.

The learning process generally follows:

```text
Concept
   ↓
Simple Example
   ↓
Implementation
   ↓
API Testing
   ↓
Database Integration
   ↓
Advanced Example
```

---

## 📌 Notes

This repository is primarily for **learning and experimentation**.

The code may change as I learn new Spring Boot concepts and improve previous implementations.

Some examples may intentionally demonstrate different approaches to understand how Spring Boot works internally.

---

## 🚀 Future Topics

Topics that may be added later:

* Microservices
* Spring Cloud
* API Gateway
* Service Discovery
* Kafka
* Redis
* Docker
* Kubernetes
* CI/CD
* AWS deployment
* Distributed systems

---

## 👨‍💻 Author

**Saranga Samarakoon**

Bachelor of Computing (Hons) in Software Engineering

---

⭐ If you find this repository useful for learning Spring Boot, feel free to star the repository.
