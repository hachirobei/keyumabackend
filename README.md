Keyuma Backend Management System
================================

This repository contains the backend management system for Keyuma.

Technologies Used
-----------------

*   **Docker**: 3.8
*   **Spring Boot**: 3.3.0
*   **Maven**: 4.0.0
*   **Java**: OpenJDK 22

API Information
---------------

This backend system provides a set of APIs for managing various aspects of the Keyuma system. The APIs are built using Spring Boot and are documented using OpenAPI (OAS 3.0).

How to Run
----------

### Using Docker

1.  Navigate to the `Docker` directory inside the `App` folder:

    cd keyumabackend/App/Docker

3.  Start the Docker containers using `docker-compose`:

    docker-compose up -d

This will set up and run the Keyuma backend management system, making the APIs available for use.

### Using IntelliJ IDEA

1.  **Open the Project**: Open IntelliJ IDEA and select `Open` from the welcome screen. Navigate to the `keyumabackend` directory and open it.
2.  **Set Up SDK**: Ensure that IntelliJ is configured to use OpenJDK 22.
    *   Go to `File > Project Structure`.
    *   Under `Project Settings > Project`, set the `Project SDK` to OpenJDK 22. If OpenJDK 22 is not listed, click `New...` to add it.
3.  **Build the Project**:
    *   Open the `Maven` tool window (View > Tool Windows > Maven).
    *   Click on the `Refresh` icon to load the Maven projects.
    *   Ensure that all dependencies are downloaded and the project builds without errors.
4.  **Run the Application**:
    *   Locate the main class with the `@SpringBootApplication` annotation (typically in `src/main/java`).
    *   Right-click the main class and select `Run 'MainClass'`.

This will start the Spring Boot application and make the APIs available for use.

Accessing the API
-----------------

Once the application is running, you can access the API endpoints at:

    http://localhost:8051/api/auth/**

    http://localhost:8051/api/user/**

You can replace `localhost` with your server's IP address or domain name if you are deploying this in a production environment.

### Example Endpoints

*   `GET /api/user/users`: Retrieve a list of users
*   `POST /api/user/users`: Create a new user
*   `GET /api/user/users/{id}`: Retrieve a specific user by ID
*   `PUT /api/user/users/{id}`: Update a specific user by ID
*   `DELETE /api/user/users/{id}`: Delete a specific user by ID
*   `POST /api/auth/login`: Authenticate a user and retrieve a token
*   `POST /api/auth/register`: Register a new user

OpenAPI Documentation
---------------------

For detailed API documentation, please refer to the OpenAPI definition at:

    http://localhost:8051/v3/api-docs

You can also access the Swagger UI (if available) for a more user-friendly interface:

    http://localhost:8051/swagger-ui.html

### Authentication

The APIs might require authentication. Ensure to include the necessary authentication tokens or credentials when making requests.
