# Clustered Data Warehouse

## Description

Clustered Data Warehouse is a Spring Boot application designed to accept and persist FX deals into a PostgreSQL database. The application provides RESTful endpoints to manage deals, ensuring data validation and handling duplicates appropriately.

## Table of Contents

- [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Installation](#installation)
    - [Running the Application](#running-the-application)
- [API Endpoints](#api-endpoints)
    - [Create a Deal](#create-a-deal)
- [Testing](#testing)
- [Logging and Error Handling](#logging-and-error-handling)
- [Makefile Commands](#makefile-commands)

## Getting Started

### Prerequisites

- Java 17
- Maven
- Docker

### Installation

1. **Clone the repository**:

    ```bash
    git clone https://github.com/OmOthm1/ClusteredDataWarehouse.git
    cd ClusteredDataWarehouse
    ```

2. **Build the Maven project**:

    ```bash
    mvn clean package
    ```

### Running the Application

#### Using Docker

1. **Build and start the services**:

    ```bash
    docker-compose up -d
    ```

2. **View logs**:

    ```bash
    docker-compose logs -f app
    ```

3. **Stop the services**:

    ```bash
    docker-compose down
    ```

#### Without Docker

1. **Start the PostgreSQL database** (ensure it’s running on `localhost:5432`).

2. **Run the application**:

    ```bash
    mvn spring-boot:run
    ```

## API Endpoints

### Create a Deal

- **URL**: `/deals`
- **Method**: `POST`
- **Content-Type**: `application/json`
- **Request Body**:

    ```json
    {
      "dealUniqueId": "123",
      "fromCurrencyIso": "USD",
      "toCurrencyIso": "JOD",
      "dealTimestamp": "2023-05-30T00:00:00.000+00:00",
      "dealAmount": 1000.00
    }
    ```

- **Response**:
    - **Success**: `200 OK`

      ```json
      {
        "id": 1,
        "dealUniqueId": "123",
        "fromCurrencyIso": "USD",
        "toCurrencyIso": "JOD",
        "dealTimestamp": "2023-05-30T00:00:00.000+00:00",
        "dealAmount": 1000.00
      }
      ```

    - **Validation Error**: `400 Bad Request`

      ```json
      {
        "dealUniqueId": "Deal Unique ID is mandatory",
        "fromCurrencyIso": "From Currency ISO Code must be 3 characters long",
        "toCurrencyIso": "To Currency ISO Code must be 3 characters long",
        "dealAmount": "Deal Amount must be greater than zero"
      }
      ```

    - **Duplicate Deal**: `400 Bad Request`

      ```json
      {
        "message": "Deal with this ID already exists"
      }
      ```

## Testing

1. **Run unit tests**:

    ```bash
    mvn test
    ```

2. **Test coverage**:
    - Ensure your tests cover the service and controller layers, including validation and exception handling.

## Logging and Error Handling

- **Logging**: The application uses SLF4J with Logback for logging. Logs can be viewed using Docker Compose or directly in the console when running the application.
- **Error Handling**: Custom exception handlers are implemented to manage validation errors and duplicate deal exceptions, providing meaningful error messages to the client.

## Makefile Commands

- **Build the Docker images**:

    ```bash
    make build
    ```

- **Start the services**:

    ```bash
    make up
    ```

- **Stop the services**:

    ```bash
    make down
    ```

- **View logs**:

    ```bash
    make logs
    ```

- **Clean up**:

    ```bash
    make clean
    ```
