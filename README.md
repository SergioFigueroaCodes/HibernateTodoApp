# Hibernate Todo App

A command-line task manager built with Java, Hibernate ORM, and MySQL. The project demonstrates CRUD operations, Jakarta Persistence entity mapping, transaction management, input validation, and Gradle-based builds.

## Features

- Add tasks with validated titles
- Display saved tasks in ID order
- Remove tasks by ID
- Persist data in MySQL through Hibernate ORM
- Roll back failed database transactions
- Configure database credentials with environment variables

## Technology

- Java 17
- Hibernate ORM 7
- Jakarta Persistence 3.2
- MySQL Connector/J
- Gradle
- JUnit 5

## Project structure

```text
src/
├── main/
│   ├── java/org/example/
│   │   ├── HibernateUtil.java
│   │   ├── Main.java
│   │   └── Task.java
│   └── resources/hibernate.cfg.xml
└── test/java/org/example/TaskTest.java
```

## Run locally

### Prerequisites

- JDK 17 or newer
- MySQL 8 or newer

### 1. Create the database

```sql
CREATE DATABASE todo_db;
```

Hibernate creates and updates the `tasks` table when the application starts.

### 2. Configure the connection

Set these environment variables before running the application:

```bash
export TODO_DB_URL="jdbc:mysql://localhost:3306/todo_db"
export TODO_DB_USER="root"
export TODO_DB_PASSWORD="your-password"
```

The URL and username default to the local values shown above. The password has no default value and is never stored in the repository.

### 3. Build, test, and run

```bash
./gradlew clean test
./gradlew run
```

On Windows, use `gradlew.bat` instead of `./gradlew`.

## What I practiced

- Mapping a Java entity to a relational table
- Managing Hibernate sessions and transactions safely
- Keeping database credentials outside source control
- Validating command-line input and domain data
- Writing focused unit tests with JUnit 5

## Future improvements

- Add task completion and due dates
- Separate persistence logic into a repository layer
- Add integration tests with a temporary test database
- Build a REST API or JavaFX interface
