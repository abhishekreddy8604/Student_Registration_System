# student-registration-system-api-backend

### Installation

1. *Build the project:*
   Navigate to the project directory and run:
   ```bash
   mvn clean install

2.  *Running the Application*

   mvn spring-boot:run

3.   *Dependencies*

This project uses the following dependencies:

- Spring Boot: The framework used to create the REST API.
- Spring Data JPA: Used for accessing the database using Java Persistence API.
- Oracle JDBC Driver (ojdbc): Used to connect to the Oracle database.

## Configuration

Before running the application, you need to add your database credentials to the application.properties file. 

```properties
spring.application.name=springjpa
spring.datasource.url=jdbc:oracle:thin:@castor.cc.binghamton.edu:1521:ACAD111
spring.datasource.username= your_username
spring.datasource.password= your_password
spring.datasource.driver-class-name=oracle.jdbc.OracleDriver
spring.jpa.hibernate.ddl-auto=update