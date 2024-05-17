# Student Management System

## Description
This project is a Student Management System that allows users to manage student registrations, courses, and other related functionalities.

## Installation
To install and run this project run the following commands:

1. npm install
2. npm start

Make sure you have Node.js and npm installed.


## Accessing the Application
Once the application is running, you can access the frontend and backend services at the following URLs:

- Frontend: [http://localhost:3000/](http://localhost:3000/)
- Backend: [http://localhost:8080/](http://localhost:8080/)

If any other services are running on port 8080, you can change the backend port in the /.env file:


Frontend/env
REACT_APP_BACKEND_URL="http://localhost:<YOUR_PORT>"

Backend/Src/Main/Resources/Application.properties
Add Server.port = <your_port>