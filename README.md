# ChatterBox

Real-time chat application built with React and Spring Boot. Talk with online friends, either in private or public ways.

# Project overview
## Features
- Chatting using Spring WebSocket, using Stomp protocol over SockJS
- Allowing public channels and private channels
- Stomp Topic & Queue Subscription
- Online & Offline User Detection
- Closing Browser Detection
## Demo
One-on-One Conversation
![One-on-one Conversation](assets/private-chat.gif?raw=true)

Public Conversation
![Public Conversation](assets/public-chat.png?raw=true)


# Project Requirements

* [JDK](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) 17
* [Spring Boot](https://www.oracle.com/technologies/) 3.x.x
* [PostgreSQL](https://www.postgresql.org/)
* [NodeJS](https://nodejs.org/en/download/) v20.12.2
* [ReactJS](https://reactjs.org/) v18
* [TailwindCSS](https://tailwindcss.com/) v3.4.3

# Project development set up
## Using docker-compose
- Create your `.env` file, then set up your database configuration (database name, username, password). For example:
    ```
    POSTGRES_DB=chat
    POSTGRES_USER=postgres
    POSTGRES_PASSWORD=password
    DATASOURCE_URL=jdbc:postgresql://db:5432/    
    ```
    Note that `DATASOURCE_URL` is setup based on the postgres container **name** and **port** as you setup in the `docker-compose.yaml` file.

- Running the following command:
    ````
    docker-compose up --build -d
    ````
**Warning**: *Be sure that no other app is running on port 3000, 8080 or 5432*