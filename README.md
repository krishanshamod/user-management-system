# Project learnSpire

Project learnSpire is a fully functional learning management system including microservices architectured API backend, Android application and single-page React website.

This is the one of backend microservice called user management system. This service can use to handle users, registrations and login.

Handle courses, content, enrollments, marks and announcement we have another microservice called learning management system. You can check it [here.](https://github.com/krishanshamod/learning-management-system)

## Architecture and Database Diagrams

- Context Diagram: [https://bit.ly/3a3prOO](https://bit.ly/3a3prOO)

- Container Diagram: [https://bit.ly/3lu76wP](https://bit.ly/3lu76wP)

- Component Diagram: [https://bit.ly/3sNY3eo](https://bit.ly/3sNY3eo)

- Database ER Diagram: [https://bit.ly/3yR6iu3](https://bit.ly/3yR6iu3)

## Features

- We added spring in-memory caching and increased the performance by 40%.

- We added multi-threading to the email service and increased the performance by 70%.

- All of the backend services are unit tested to ensure that nothing goes wrong in any situation.

- All the endpoints are integration tested to ensure that all functionalities work as intended.

## Getting Started

We pushed this application's Docker image to the Docker hub. To run the application you need to setup Docker in your pc properly.
Then you can follow the steps.

1. Pull the Docker image from the Docker hub.

```jsx
docker pull krishanshamod/user-management-system
```


2. Create a file called docker-compose.yml and add following content to it.

```yaml
services:
    ums:
      image: "krishanshamod/user-management-system"
      ports:
        - 8090:8090
      environment:
        - ums_db_url= [database url]
        - ums_db_username= [database username]
        - ums_db_password= [database password]
        - token_secret= [jwt token secret]
```
Note: You need you replace the environment variables with your own values.

3. Locate to the docker-compose.yml file directory using terminal and execute this command.

```jsx
docker-compose up -d
```

Now you are good to go. User management system is running on port 8090.

You can also run this application as a normal Java application. You can clone the repo, build using maven, put the environment variables and then you can run the jar file as normal java application.
