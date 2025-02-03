# E-Commerce Microservices Project

A microservices-based e-commerce application built with Spring Boot, Kafka, and MongoDB.

## System Architecture

```
┌──────────────┐
│    Client    │
└──────┬───────┘
       │
       ▼
┌──────────────┐         ┌───────────────┐
│Order Service │────────▶│    MongoDB    │
│   :8080      │         │               │
└──────┬───────┘         └───────────────┘
       │                         ▲
       ▼                         │
┌──────────────┐                │
│    Kafka     │                │
│  Message Bus │                │
└──────┬───────┘                │
       │                        │
       ├─────────┐             │
       │         │             │
       ▼         ▼             │
┌──────────┐  ┌──────────────┐ │
│ Notify   │  │  Inventory   │ │
│ Service  │  │   Service    │─┘
│  :8081   │  │    :8082     │
└──────────┘  └──────────────┘

┌─────────── Monitoring ───────────┐
│  ┌──────────────┐ ┌──────────┐   │
│  │  Prometheus  │ │ Grafana  │   │
│  │    :9090     │▶│  :3000   │   │
│  └──────────────┘ └──────────┘   │
└──────────────────────────────────┘
```

## Components

### 1. Microservices

#### Order Service (Port: 8080)

- Manages order processing
- Stores order records in MongoDB
- Publishes order events to Kafka
- Exposes REST API for order creation

#### Inventory Service (Port: 8082)

- Handles inventory management
- Consumes order events from Kafka
- Updates stock levels in MongoDB
- Provides inventory status API

#### Notification Service (Port: 8081)

- Manages notification delivery
- Consumes order events from Kafka
- Logs notification activities

### 2. Infrastructure Components

#### Apache Kafka

- Message broker for event-driven architecture
- Enables asynchronous communication between services
- Topics:
  - order-events
  - order-status-events

#### MongoDB

- NoSQL database
- Separate databases for each service
- Persistent data storage

#### Prometheus & Grafana

- Metrics collection (Prometheus)
- Metrics visualization (Grafana)
- Monitors JVM, Kafka, and custom metrics

## Data Flow

1. Client creates an order (Order Service)
2. Order Service:
   - Saves order to MongoDB
   - Publishes event to Kafka
3. Inventory Service:
   - Consumes event
   - Checks stock
   - Updates inventory
4. Notification Service:
   - Consumes event
   - Processes notification

## Monitoring

- Prometheus: http://localhost:9090
- Grafana: http://localhost:3000
  - Username: admin
  - Password: admin123

## Getting Started

### Prerequisites

- Java 17
- Docker & Docker Compose
- Maven

### Running the Application

```bash
# Build and start
mvn clean package -DskipTests
docker-compose up --build

# Test endpoints
# Add inventory
curl -X POST http://localhost:8082/api/inventory \
-H "Content-Type: application/json" \
-d '{
    "productId": "PROD-001",
    "quantity": 100
}'

# Create order
curl -X POST http://localhost:8080/api/orders \
-H "Content-Type: application/json" \
-d '{
    "customerId": "CUST-001",
    "productId": "PROD-001",
    "quantity": 5,
    "totalAmount": 500.00
}'
```

## Technologies

- Java 17
- Spring Boot 3.2.3
- Apache Kafka
- MongoDB
- Docker & Docker Compose
- Prometheus & Grafana
- Spring Actuator & Micrometer

## Project Structure

```
e-commerce-microservices/
├── order-service/
│   ├── src/main/java/
│   │   └── com/eeibra/ecommerce/order/
│   │   └── src/main/resources/
│   │       └── application.yml
├── inventory-service/
│   ├── src/main/java/
│   │   └── com/eeibra/ecommerce/inventory/
│   └── src/main/resources/
│       └── application.yml
├── notification-service/
│   ├── src/main/java/
│   │   └── com/eeibra/ecommerce/notification/
│   └── src/main/resources/
│       └── application.yml
├── prometheus/
│   └── prometheus.yml
├── grafana/
│   └── provisioning/
├── docker-compose.yml
└── README.md
```
