# Simple Event Driven Microservices Project

This project demonstrates an event-driven microservices architecture using Axon, Eureka, CQRS, and Spring. The repository contains multiple 
microservices that interact with each other through event sourcing and command-query responsibility segregation (CQRS) patterns.

The project contains some mock implementations to focus on demonstrating inter-service communication rather than the microservice logic itself. Please note that this is a work in progress.

## Architecture
The architecture consists of the following components:
- **Discovery Server**: Manages service registration and discovery using Eureka.
- **API Gateway**: Routes requests to appropriate microservices.
- **Products Service**: Manages product data and handles commands and queries using Axon framework.
- **Orders Service**: Manages order data and handles commands and queries using Axon framework.
- **Payments Service**: Mocks a payment processing service.

## Technologies
- **Java**
- **Spring Boot**
- **Axon Framework**
- **Eureka**
- **CQRS**
- **Event Sourcing**

## Setup
1. Clone repository

##TODO: Finish read me.
