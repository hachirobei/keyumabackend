# Keyuma Backend Management System

![Docker](https://img.shields.io/badge/Docker-3.8-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.0-brightgreen)
![Maven](https://img.shields.io/badge/Maven-4.0.0-orange)
![Java](https://img.shields.io/badge/Java-OpenJDK%2022-red)

Welcome to the Keyuma Backend Management System repository. This guide will help you set up, run, and use our backend system efficiently using Docker.

## Technologies Used

- **Docker**: 3.8
- **Spring Boot**: 3.3.0
- **Maven**: 4.0.0
- **Java**: OpenJDK 22

## API Information

Our backend system provides a set of APIs for managing various aspects of the Keyuma system. Built with Spring Boot and documented using OpenAPI (OAS 3.0), it ensures robust and scalable management capabilities.

## How to Run

### Using Docker

1. **Navigate to the Docker Directory**:
    ```bash
    cd keyumabackend/App/Docker
    ```
2. **Start the Docker Containers**:
    ```bash
    docker-compose up -d
    ```
   This will set up and run the Keyuma backend management system, making the APIs available for use.

## Accessing the API

Once the application is running, you can access the API endpoints at:

- `http://localhost:8051/api/auth/**`
- `http://localhost:8051/api/user/**`

Replace `localhost` with your server's IP address or domain name if deploying in a production environment.

## OpenAPI Documentation

For detailed API documentation, refer to the OpenAPI definition at:

- `http://localhost:8051/v3/api-docs`

You can also access the Swagger UI for a more user-friendly interface:

- `http://localhost:8051/swagger-ui.html`

### Authentication

Some APIs might require authentication. Ensure to include the necessary authentication tokens or credentials when making requests.

---

## Contribution Guidelines

### Branching Strategy

- All new features and bug fixes should be developed in feature branches.
- Feature branches should be created from the `develop` branch.
- The `master` branch is protected and should only contain stable code.

### Workflow

1. Clone the repository:
   ```bash
   git clone https://github.com/hachirobei/keyumabackend.git
   cd keyumabackend

Enjoy using the Keyuma Backend Management System! If you have any questions or need further assistance, feel free to reach out.
