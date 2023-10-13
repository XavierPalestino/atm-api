## ATM demo

### Project frameworks and tools

#### Make sure you have installed the following software and tools in order to run the project successfully

- Java 17
- MySQL 8
- IDE (Eclipse, Intellij IDEA, Lombok v1.16.18, JPA Static Metamodel)

### 2. Set the database

#### In your DBMS create a database with the next configuration

- Host: `localhost`
- Port: `3306`
- Schema: `atmtest`
- User: `root`
- password: `admin`

### 3. Set configurations to run the project

- In your IDE go to Edit Configurations
- Add a new maven configuration
- Name run-project and put "spring-boot:run --projects api" in the **Run** input

You can also run by using directly the maven commands:

1. `mvn clean install -Pdev -DskipTests=true`
2. `mvn spring-boot:run -Pdev --projects api`
3. `mvn com.spotify.fmt:fmt-maven-plugin:format`


### 3. How to test

For the moment this project only can be tested using curl or postman

- http://localhost:8080/atm 
- Here is a json example:
  - {
    "moneyToWithdraw": "1750.50",
    "atmCode": "K17"
    }

### 4. Notes

- Deposit endpoint will be added soon.
- Withdraw endpoint is finished, but will be updated shortly.
- Balance check endpoint will be added soon.
- The project UI will be worked on in another repository.
