# Library_Management

This is a RESTful API built using Spring Boot to manage a library system. The API allows users to perform CRUD operations
on books while adhering to best practices, validation rules, and design patterns. 
Security is implemented using Spring Security for basic authentication.

# Prerequisites
1. Java Development Kit (JDK): Ensure Java 17 is installed.
2. Build Tool: Use either Maven or Gradle to build the application.
3. Database:
   By default, the application uses an in-memory H2 database.
   To use MySQL, ensure you have MySQL installed and running.

# How to Run the Application
  # Clone the Repository
    git clone https://github.com/your-repo/library-management-api.git

  # Configure the Application

    1. By default, the application uses an H2 in-memory database.
    2. To switch to MySQL:
        Open src/main/resources/application.properties.
# Update the database configurations:
    spring.datasource.url=jdbc:mysql://localhost:3306/library
    spring.datasource.username=<your_username>
    spring.datasource.password=<your_password>
    spring.jpa.hibernate.ddl-auto=update

# Ensure the database library exists or create it using:
    CREATE DATABASE library;

# Build and Run the Application
1. Using Maven:
   ./mvnw spring-boot:run
2. Using Gradle:
   ./gradlew bootRun

# Access the application at:
    Swagger/OpenAPI Documentation: http://localhost:8080/swagger-ui.html

# How to Test the Endpoints
1. Authentication : The application uses Basic Authentication. Use the default credentials specified in from the endpoint of user

2. Create User: create new user with cred. 
   - login with user
   - copy token and put into Authorize button
3. Book:
    - Create Book with details
    - Update Book details with providing id and details
    - Retrieve book details by id or by all book list
    - Delete Book Details by id.