# dms-api
REST API using Spring Boot 3 and MS SQL Server DB as a storage

# Installation
Standard Maven project, it uses MS SQL Server for storing the data. SQL database should be called DMS. So application.properties has to be updated - name of the SQL Server plus credentials as below:
spring.datasource.url= jdbc:sqlserver://localhost:1433;encrypt=true;trustServerCertificate=true;databaseName=dms
spring.datasource.username=sa
spring.datasource.password=

Initial SQL scripts are saved in the root folder "SQL Scripts" of the project. They have to be run in following order then: 
1. create_db.sql - in only craete the DMS database
2. schema.sql - creates tables + keys
3. data.sql - insert data to DB tables

The API is secured by Basic Auth plus BCrypt encryption - see src\main\java\com\petrbambas\rest\products\config\SecurityConfig.java. To get access to endpoints use petr as the username and Rolp4/47*-9 as the password (both data is saved in SecurityConfig too)

# Usage
Via endpoints (see src\main\java\com\petrbambas\rest\products\controller) provides processing documents and protocols entities. They support CRUD operations (GET, POST, PUT, DELETE) following the requirements:
  HttpMethod.GET http://localhost:8080/api/protocols
  HttpMethod.PUT, "/api/protocols/**").hasRole("ADMIN")
                        HttpMethod.POST, "/api/protocols").hasRole("ADMIN")
                        HttpMethod.POST, "/api/protocols").hasRole("ASSISTANT")
                        HttpMethod.PUT, "/api/protocols/status/**").hasRole("ADMIN")
                        HttpMethod.GET, "/api/documents").hasRole("ADMIN")
                        HttpMethod.POST, "/api/documents").hasRole("ADMIN")
                        HttpMethod.PUT, "/api/documents/**").hasRole("ADMIN")
                        HttpMethod.DELETE, "/api/documents/**").hasRole("ADMIN"));
