# URL Shortener
The "URL Shortener" project is a web service developed to convert long URLs into short ones. The user enters a long URL, and our service provides them with a unique shortened URL, which automatically redirects to the original long URL when accessed.
## Content
- [Technologies](#technologies)
- [Environmental Variables](#environmental-variables)
- [Start of work](#start-of-work)
- [Testing](#testing)
- [Deploy and CI/CD](#deploy-and-cicd)
- [To do](#to-do)
- [Improvements](#improvements)
- [Project Team](#project-team)
# Technologies
 + Spring Boot
 + Spring Data
 + PostgreSQL
 + JWT
 + FlyWay
 + Swagger
 + jUnit5
 + Mockito
 + CI pipeline (GitHub actions)
 + Docker
 + docker-compose
## Environmental Variables
### Sensitive variables which must be in `Edit Configuration` menu
- ```
- DEFAULT_H2_DB_HOST=jdbc:h2:mem:testdb;DEFAULT_H2_DB_USERNAME=sa;
- DEFAULT_H2_DB_PASSWORD=;DEFAULT_APP_CACHING=true;DEFAULT_APP_URL=http://localhost:8080;
- DOCKER_POSTGRES_DB_USERNAME=postgres;DOCKER_POSTGRES_DB_PASSWORD=1234;DOCKER_POSTGRES_DB_NAME=url_shortener;
- DOCKER_POSTGRES_DB_HOST=localhost;DOCKER_POSTGRES_DB_PORT=5432;DOCKER_APP_URL=http://localhost:9999;DOCKER_APP_CACHING=true
- ;PROD_POSTGRES_DB_USERNAME=postgres;PROD_POSTGRES_DB_PASSWORD=1234;PROD_POSTGRES_DB_NAME=url_shortener;PROD_POSTGRES_DB_HOST=localhost;
- PROD_POSTGRES_DB_PORT=5432;PROD_APP_URL=http://localhost:8080;PROD_APP_CACHING=true;LOCAL_POSTGRES_DB_USERNAME=postgres;LOCAL_POSTGRES_DB_PASSWORD=1234;
- LOCAL_POSTGRES_DB_NAME=url_shortener;LOCAL_POSTGRES_DB_HOST=localhost;LOCAL_POSTGRES_DB_PORT=5432;MAIN_APP_URL=http://localhost:8080;MAIN_APP_CACHING=false;
- TEST_APP_URL=http://localhost:8080;TEST_APP_CACHING=false;JWT_SECRET_PROPS=as542d54fkja656s7h6ndfjas1241234kdnsadiojfkjasdk1123rjkj34;JWT_LIMIT_PROPS=7d
- ```
### PostgreSQL Database Configuration
- ```POSTGRES_DB_USERNAME:``` The username for accessing the PostgreSQL database. Default value: ```postgres```.
- ```POSTGRES_DB_PASSWORD:``` The password for the root user of the PostgreSQL database. Default value: ```1234```.
- ```POSTGRES_DB_NAME:``` The name of the PostgreSQL database to be used. Default value: ```url_shortener```.
- ```POSTGRES_DB_LOCAL_PORT:``` The local port number to access the PostgreSQL database. Default value: ```5433```.
- ```POSTGRES_DB_DOCKER_PORT:``` The port number used by the PostgreSQL Docker container. Default value: ```5432```.
### Spring Application Configuration
- ```SPRING_LOCAL_PORT:``` The local port number to access the Spring application. Default value: ```8080```.
- ```SPRING_DOCKER_PORT:``` The port number used by the Spring application Docker container. Default value: ```9999```.
## Start of Work
### Creating Production Application Image
```java, -jar, opt/UrlShortenerApplication.jar```
### Deploying application & Database into containers
```docker build -t goit-proj .```
```docker-compose up -d```
### Stop & remove container
 - ```docker stop```
 - ```docker rm```
### Removing all containers
```docker-compose down```
## Testing
## Deploy and CI/CD
## To do
### Main Requirements:
 - [x] The project must be implemented as a REST API.
 - [x] The project must be presented in the form of API documentation (Swagger).
 - [x] The API must have versioning: [https://www.baeldung.com/rest-versioning](https://www.baeldung.com/rest-versioning).
 - [x] Code coverage with tests: at least 80%.
### Requirements for Launch:
 - [x] There must be two launch profiles, default and prod [https://www.baeldung.com/spring-profiles](https://www.baeldung.com/spring-profiles).
 - [x] The default profile should have settings for local launch.
 - [x] Prod for a remote server.
 - [x] The application must have a Dockerfile, and the local launch of the application (application + database) must be described in docker-compose.yml.
 - [x] The database for tests must be launched using [https://java.testcontainers.org/](https://java.testcontainers.org/).
 - [x] All private information, such as login and password to the DB, must be in the form of environment variables: ```${DB_USERNAME}```.
 - [x] Environment variables must be described in the README.md file.
#### 1. Registration and Authentication of Users
##### Registration:
 - [x] Check for the uniqueness of the username.
 - [x] The password must contain at least 8 characters, including numbers, uppercase and lowercase letters.
 - [x] Storage of passwords in encrypted form.
##### Authentication:
 - [x] Verification of entered data for correspondence with the data in the DB.
 - [x] Upon successful authorization, return a JWT (JSON Web Token) that will be used for further authentication.
### 2. Short Link
 - [x] Must contain a short link, original, creation date, the number of transitions made on it, and the user who created it.
 - [x] The link must have an expiration date.
### 3. Creation of a Short Link
 - [x] Only a registered user can create a short link.
 - [x] The original link must be valid.
 - [x] The system must generate a new short link (6-8 random letters/numbers).
 - [x] The short and original links must be saved in the DB according to the user.
### 4. User Capabilities
 - [x] The user must be able to view all created active links and statistics on them.
 - [x] The user must be able to view all created links and statistics on them.
 - [x] The user can create a new/delete an existing link.

### 5. Following the Link
 - [x] Even an unregistered user can follow the shortened link.
 - [x] The transition statistics must be updated.
 - [x] The response must be cached.
## Improvements
 - [x] Implement a global exception handler using ```@ControllerAdvice``` and ```@ExceptionHandle``` annotations to manage exceptions across the entire application.
 - [ ] Thymeleaf Integration: Thymeleaf should be used as the server-side template engine for rendering HTML content.
## Project Team
 - Team Lead [Antonii Viazovskyi](https://github.com/AntoniiViazovskyi)
 - Scrum Master [Vitalii Bahrynovskyi](https://github.com/bagrik571)
 - Developer [Dmytro Ilin](https://github.com/Dmitriy2028)
 - Developer [Viktor Kudryavtsev](https://github.com/vikadmin88)
 - Developer [Oleksii Bielov](https://github.com/Belimbb)
 - Developer [Katerina Zharchynska](https://github.com/Zharch1541)
 - Developer [Maryna Yeretska](https://github.com/MNN1107)
 - Developer [Oleg Gordienko](https://github.com/Holmc555)