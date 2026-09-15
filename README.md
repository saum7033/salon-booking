# 💇 Salon Booking Platform

A **microservices-based salon booking platform** built with **Java, Spring Boot, Spring Cloud, PostgreSQL, Docker, and Spring AI**.

The platform is designed to manage salon operations through independently deployable services for **salons, users, categories, service offerings, bookings, payments, reviews, notifications, and AI-powered assistance**.

The project demonstrates practical backend engineering concepts including **microservice architecture, service discovery, API Gateway, REST APIs, inter-service communication, database integration, authentication/authorization, asynchronous communication, containerization, and AI integration**.

---

## 📌 Table of Contents

* [Overview](#-overview)
* [Key Features](#-key-features)
* [Architecture](#-architecture)
* [Microservices](#-microservices)
* [Technology Stack](#-technology-stack)
* [Project Structure](#-project-structure)
* [Service Communication](#-service-communication)
* [Request Flow](#-request-flow)
* [Database Design](#-database-design)
* [AI Assistant](#-ai-assistant)
* [Getting Started](#-getting-started)
* [Configuration](#-configuration)
* [Running the Application](#-running-the-application)
* [API Testing](#-api-testing)
* [Docker](#-docker)
* [CI/CD](#-cicd)
* [Future Improvements](#-future-improvements)
* [Learning Outcomes](#-learning-outcomes)
* [Author](#-author)

---

# 🚀 Overview

The **Salon Booking Platform** follows a microservices architecture where different business capabilities are separated into independent services.

Instead of implementing the entire application as one large backend, the system separates responsibilities into services such as:

* User management
* Salon management
* Service categories
* Service offerings
* Appointment booking
* Payments
* Reviews
* Notifications
* AI assistance

This separation makes the system easier to develop, maintain, test, and extend.

The platform also includes infrastructure services such as:

* **Eureka Server** for service discovery
* **API Gateway** for centralized request routing
* **Docker** for containerized execution
* **GitHub Actions** for CI/CD automation

---

# ✨ Key Features

### 🏗️ Microservices Architecture

The application is divided into independently developed services based on business responsibilities.

### 🔎 Service Discovery

The project uses **Netflix Eureka** so that microservices can register themselves and discover other services dynamically.

### 🌐 API Gateway

A centralized API Gateway provides a single entry point for clients and routes requests to the appropriate backend services.

### 👤 User Management

The user service handles user-related functionality and provides the foundation for authenticated access to the platform.

### 💇 Salon Management

The salon service manages salon-related information and acts as one of the core business services.

### 🗂️ Categories & Services

Salon services are organized through:

* Categories
* Service offerings
* Service-specific information

This allows salons to manage the services that customers can book.

### 📅 Appointment Booking

The booking service manages appointment-related operations and booking states.

A booking can move through different states depending on the booking workflow.

### 💳 Payment Service

The payment service is separated from the booking functionality so payment-related operations can evolve independently.

### ⭐ Review Service

A dedicated review service allows customer feedback functionality to remain independent from the booking and salon services.

### 🔔 Notification Service

Notification functionality is isolated into its own service, allowing notifications to be processed without tightly coupling them to the core booking logic.

### 🤖 AI Assistant

The project includes a dedicated `ai-assistant` service that provides an AI-powered conversational interface.

The AI service is designed as a separate Spring Boot service rather than embedding AI logic directly into the booking service.

This keeps the AI functionality modular and allows it to evolve independently.

---

# 🏛️ Architecture

```text
                         ┌─────────────────────┐
                         │       Client        │
                         │  Web / Postman / UI │
                         └──────────┬──────────┘
                                    │
                                    ▼
                         ┌─────────────────────┐
                         │    API Gateway      │
                         │   Spring Cloud      │
                         └──────────┬──────────┘
                                    │
                ┌───────────────────┼───────────────────┐
                │                   │                   │
                ▼                   ▼                   ▼
        ┌──────────────┐    ┌──────────────┐    ┌──────────────┐
        │ User Service │    │ Salon Service│    │Booking Service│
        └──────────────┘    └──────────────┘    └──────────────┘
                │                   │                   │
                │                   ▼                   │
                │           ┌──────────────┐            │
                │           │   Category   │            │
                │           │    Service   │            │
                │           └──────────────┘            │
                │                   │                   │
                │                   ▼                   │
                │           ┌──────────────┐            │
                │           │   Service    │            │
                │           │   Offering   │            │
                │           └──────────────┘            │
                │                                       │
                ├──────────────┐          ┌─────────────┤
                ▼              ▼          ▼             ▼
         ┌────────────┐ ┌────────────┐ ┌──────────┐ ┌──────────┐
         │  Payment   │ │   Review   │ │Notification│ │AI Assistant│
         │  Service   │ │  Service   │ │  Service   │ │  Service  │
         └────────────┘ └────────────┘ └──────────┘ └──────────┘


                         ┌─────────────────────┐
                         │    Eureka Server    │
                         │  Service Discovery  │
                         └─────────────────────┘


                         ┌─────────────────────┐
                         │     PostgreSQL      │
                         │      Databases      │
                         └─────────────────────┘
```

---

# 🧩 Microservices

| Service            | Responsibility                            |
| ------------------ | ----------------------------------------- |
| `eureka-server`    | Service discovery and registration        |
| `gateway-server`   | Centralized API routing and gateway layer |
| `user-service`     | User management                           |
| `salon-service`    | Salon management                          |
| `category-service` | Service category management               |
| `service-offering` | Salon service offerings                   |
| `booking-service`  | Appointment and booking management        |
| `payment`          | Payment-related operations                |
| `review`           | Customer reviews                          |
| `notification`     | Notification processing                   |
| `ai-assistant`     | AI-powered conversational assistance      |

---

# 🛠️ Technology Stack

## Backend

* Java 17
* Spring Boot
* Spring MVC
* Spring Data JPA
* Spring Security
* REST APIs

## Microservices

* Spring Cloud
* Netflix Eureka
* Spring Cloud Gateway
* Inter-service communication
* API Gateway architecture

## Database

* PostgreSQL

## Messaging

* RabbitMQ

## AI

* Spring AI
* Google Gemini / Generative AI integration

## DevOps

* Docker
* Docker Compose
* GitHub Actions
* Google Cloud Run

## Development Tools

* IntelliJ IDEA
* Maven
* Git
* GitHub
* Postman
* Swagger / OpenAPI

---

# 📁 Project Structure

```text
salon-booking/
│
├── .github/
│   └── workflows/
│
├── ai-assistant/
│
├── booking-service/
│
├── category-service/
│
├── docker-compose/
│
├── eureka-server/
│
├── gateway-server/
│
├── notification/
│
├── payment/
│
├── review/
│
├── salon-service/
│
├── service-offering/
│
├── user-service/
│
└── README.md
```

---

# 🔄 Service Communication

The platform uses a combination of synchronous REST-based communication and asynchronous messaging depending on the business requirement.

### Synchronous Communication

REST APIs are used when a service needs an immediate response from another service.

For example:

```text
Client
  │
  ▼
API Gateway
  │
  ▼
Booking Service
  │
  ├──► User Service
  │
  ├──► Salon Service
  │
  └──► Service Offering
```

### Asynchronous Communication

RabbitMQ can be used for event-driven communication where the producer does not need to wait for the consumer to complete its processing.

A simplified flow:

```text
Booking Service
      │
      │ Booking Event
      ▼
   RabbitMQ
      │
      ├──────────────► Notification Service
      │
      └──────────────► Other Consumers
```

This helps reduce direct coupling between services.

---

# 🔀 Request Flow

A typical client request follows this architecture:

```text
Client
  │
  ▼
API Gateway
  │
  ▼
Service Discovery
  │
  ▼
Target Microservice
  │
  ├──► Business Logic
  │
  ├──► Database
  │
  └──► Other Microservices
  │
  ▼
Response
  │
  ▼
API Gateway
  │
  ▼
Client
```

The API Gateway provides a centralized entry point while Eureka allows services to locate one another without hardcoding service instances.

---

# 🗄️ Database Design

The platform uses **PostgreSQL** for persistent storage.

Instead of keeping all business logic inside a single database module, the application separates data ownership according to service boundaries.

Major business domains include:

```text
User
 │
 └── User Service

Salon
 │
 ├── Category
 │     └── Service Offering
 │
 └── Booking

Booking
 │
 ├── Payment
 │
 ├── Notification
 │
 └── Review
```

This separation follows the microservice principle of keeping business responsibilities isolated.

---

# 🤖 AI Assistant

The repository includes a dedicated:

```text
ai-assistant
```

service.

The AI assistant is implemented as a separate Spring Boot application using **Spring AI**.

### Example Endpoint

```http
POST /api/ai/chat
```

Example request:

```json
{
  "message": "What services are available?"
}
```

The AI assistant is intended to act as a conversational interface for the Salon Booking platform.

A key design principle is that the assistant should **not invent salon-specific information** that is not available from the application's data sources.

This service can later be extended with **RAG (Retrieval-Augmented Generation)** so that responses are grounded in actual salon data such as:

* Services
* Pricing
* Salon information
* Availability
* Booking policies
* Frequently asked questions

---

# ⚙️ Getting Started

## Prerequisites

Make sure the following are installed:

* Java 17+
* Maven
* PostgreSQL
* Docker
* Docker Compose
* Git
* Postman

Optional:

* IntelliJ IDEA

---

# 📥 Clone the Repository

```bash
git clone https://github.com/saum7033/salon-booking.git
```

Navigate into the project:

```bash
cd salon-booking
```

---

# 🔧 Configuration

Each microservice contains its own configuration.

Before running the application, configure:

* PostgreSQL connection
* Database credentials
* Eureka server URL
* Gateway configuration
* RabbitMQ configuration
* AI provider credentials where required

### Example PostgreSQL configuration

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/<database-name>
spring.datasource.username=<username>
spring.datasource.password=<password>
```

Do **not** commit real passwords, API keys, tokens, or other secrets to GitHub.

Use environment variables or local configuration files for sensitive values.

---

# ▶️ Running the Application

The recommended startup order is:

### 1. Start PostgreSQL

Make sure PostgreSQL is running and the required databases are available.

### 2. Start Eureka Server

Start:

```text
eureka-server
```

The Eureka dashboard should become available at the configured Eureka port.

### 3. Start Backend Services

Start the required microservices:

```text
user-service
salon-service
category-service
service-offering
booking-service
payment
review
notification
```

### 4. Start API Gateway

Start:

```text
gateway-server
```

The gateway becomes the main entry point for client requests.

### 5. Start AI Assistant

Start:

```text
ai-assistant
```

The AI service exposes its conversational endpoint independently.

---

# 🐳 Running with Docker

The repository contains Docker Compose configuration under:

```text
docker-compose/
```

Docker can be used to run infrastructure and services in a consistent environment.

Example:

```bash
docker compose up -d
```

To stop the containers:

```bash
docker compose down
```

> The exact Docker Compose command and environment variables depend on the compose configuration being used.

---

# 🧪 API Testing

The APIs can be tested using **Postman** or any REST client.

A typical booking workflow can be tested as:

```text
1. Create User
       ↓
2. Create Salon
       ↓
3. Create Category
       ↓
4. Create Service Offering
       ↓
5. Create Booking
       ↓
6. Process Payment
       ↓
7. Send Notification
       ↓
8. Submit Review
```

Example AI request:

```http
POST /api/ai/chat
Content-Type: application/json
```

```json
{
  "message": "Explain the available salon services."
}
```

---

# 🔐 Security

Security is handled at the application/service level using Spring Security and authentication/authorization mechanisms.

The architecture is designed so that security can be enforced at the gateway and individual service boundaries where required.

Sensitive configuration such as:

```text
Database passwords
API keys
JWT secrets
AI credentials
```

should be provided through environment variables rather than committed to source control.

---

# 🔄 CI/CD

The project includes GitHub Actions workflows under:

```text
.github/workflows/
```

The CI/CD setup is intended to automate application build and deployment-related workflows.

The project also includes configuration/workflows related to containerized deployment and cloud execution.

---

# 📊 Design Principles

The project follows several important backend engineering principles:

### Separation of Concerns

Each service owns a specific business responsibility.

### Loose Coupling

Services communicate through defined APIs and messaging rather than sharing internal implementation details.

### Independent Scalability

Individual services can be scaled independently according to their workload.

### Service Discovery

Eureka removes the need for services to rely entirely on hardcoded service locations.

### Centralized Routing

The API Gateway provides a single entry point for external clients.

### Event-Driven Communication

RabbitMQ can be used for asynchronous communication between services.

### Independent Data Ownership

Business domains are separated according to service responsibilities.

---

# 🔮 Future Improvements

Potential improvements include:

* [ ] Implement complete RAG pipeline for the AI assistant
* [ ] Connect AI assistant with live salon/service data
* [ ] Add semantic search using vector embeddings
* [ ] Add AI-powered service recommendations
* [ ] Implement real-time appointment availability
* [ ] Add distributed tracing
* [ ] Add centralized logging
* [ ] Add circuit breakers and resilience patterns
* [ ] Add Redis caching
* [ ] Improve API documentation with OpenAPI
* [ ] Add comprehensive integration testing
* [ ] Add monitoring with Prometheus and Grafana
* [ ] Improve deployment automation
* [ ] Add frontend application

---

# 🎯 Learning Outcomes

This project was built to gain practical experience with:

* Microservices architecture
* Spring Boot
* Spring Cloud
* REST API development
* API Gateway
* Service discovery
* PostgreSQL
* Spring Security
* RabbitMQ
* Docker
* CI/CD
* Cloud deployment
* Distributed system design
* AI application development
* Spring AI

The project also provides a practical foundation for experimenting with **RAG, LLM-powered applications, asynchronous processing, and distributed backend systems**.

---

# 👨‍💻 Author

**Saumya Raj**

B.Tech — Computer Science & Engineering
NIT Durgapur
---

# ⭐ If You Find This Project Useful

If this project helped you understand microservices, Spring Boot, or backend architecture, consider giving the repository a ⭐.

Contributions, suggestions, and feedback are welcome.
