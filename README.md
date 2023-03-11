# TaskAppAPI

TaskAppAPI is a Java project built with Spring Boot 3.0.4 and Gradle 8.0.2, using the Corretto19 version jdk19.0.2_7. 

It provides an API for managing tasks.

## Prerequisites

    Java 19 on Corretto19 version jdk19.0.2_7 or higher
    Gradle 8.0.2 or higher
    Spring Boot 3.0.4 or higher 

## Getting Started

#### 1. Clone the repository:

    git clone https://github.com/vineralex/NgSoftTestApp.git

#### 2. Build the project:

    cd TaskAppAPI
    gradle build

#### 3. Run the project:

    gradle bootRun

#### 4. Access the API at http://localhost:9000/

## Additions

### Postman
Provided Postman json file in the following project directory:

    .\postman

If the login credentials are already configured in the JSON file, then you can follow these steps to authenticate a user and obtain a bearer token in Postman:

    Import the JSON file into Postman by clicking the "Import" button and selecting the file.
    Locate the login endpoint in the Postman collection "NgSoft Sample App" and open it.
    Click the "Authorization" tab in the request editor and click "Get New Access Token".
    Enter the login credentials in the external browser and complete the authentication process.
    Once you have authenticated successfully, the external browser will redirect to Postman and Postman will automatically save the bearer token in the "Token" field of the request editor.

At this point, you should be able to use the bearer token in subsequent requests by simply clicking the "Send" button without having to enter the token manually.

### Initial users:

#### 1. User
    id: 1
    username: user
    password: user
    roles: USER

#### 2. Admin
    id: 2
    username: admin
    password: admin
    roles: ADMIN