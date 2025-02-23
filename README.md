# it-support-ticket-backend

Simple ticket management application designed to allow employees to report and track IT issues.

### Features
- Authentification with the following roles:
  Employees: Can create and view their own tickets.
  IT Support: Can view all tickets, change statuses, and add comments.
- Ticket Creation:
  Employees can create tickets (title, description, Priority, category)
- Search & Filter:
  Search by ticket ID and status.
- Audit Log:
  Track changes to ticket status and added comments.

### Technology Stack
- Backend: Java 17, Spring Boot 3.4.3, RESTful API with Swagger/OpenAPI
- Database**: Oracle 21c Express Edition
- UI: Java Swing (MigLayout)
- Testing: JUnit, Mockito
- Deployment: Docker (backend and database), executable JAR (Swing client)

## Prerequisites
- Java 17 JDK
- Maven 3.8+
- Docker and Docker Compose
- Oracle SQL*Plus or SQL Developer (for manual DB setup)
- Git

## Setup Instructions

docker run -d -p 1521:1521 container-registry.oracle.com/database/express:latest

docker exec -it <container_id> bash

sqlplus / as sysdba

ALTER SESSION SET CONTAINER = XEPDB1;
CREATE USER ticketuser IDENTIFIED BY TicketPass123;
GRANT CONNECT, RESOURCE TO ticketuser;

Exit

sqlplus ticketuser/TicketPass123@//localhost:1521/XEPDB1

## Create Tables and Init database with the sql files in the resources

mvn clean install

docker-compose up --build

mvn package -pl swing-client

java -jar swing-client/target/itsupportticket-client.jar