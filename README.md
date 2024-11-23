# **Gameloft Test Application**

## **Description**

This is an application developed as a solution for a technical test provided by Gameloft. The application manages users and campaigns, including functionalities to retrieve users and update their data based on active campaigns.

---

## **Technologies Used**

### **Backend**
- **Java 21**: Main language for implementation.
- **Spring Boot 3.3.5**: Framework used to develop the application.
    - **Spring WebFlux**: For building reactive APIs.
    - **Spring Data MongoDB**: For NoSQL database support.
- **Gradle**: Build tool and dependency management.

### **Testing**
- **JUnit 5**: Main framework for unit and integration testing.
- **Mockito**: Mocking library for unit tests.
- **Spring Boot Test**: For integration testing with WebTestClient.
- **WebTestClient**: Reactive client for testing REST controllers.

### **Databases**
- **MongoDB**: Default database embedded using 'de.flapdoodle.embed' library.

---

## **Project Structure**

The application follows a hexagonal architecture to decouple dependencies from business logic.

### **Main Modules**
1. **User**: Module to handle user profile information.
    - **Application**: Implements the business logic use cases.
    - **Domain**: Contains models and interfaces for use cases and repositories.
    - **Infrastructure**:
        - **Configuration**: Defines beans and dependency configuration.
        - **Input**: Contains REST adapters to handle incoming requests.
        - **Output**: Contains adapters for database persistence (MongoDB) and a REST client adapter to connect to the CampaignService.
        

---

## **Features**

- **Find User**: Retrieve a user by their UUID ID.
- **Update User**: Add an active campaign to the user if conditions are met.

---

## **Configuration**

### **Profiles**
- **`mongo`**: Uses MongoDB as the main database.

---

## **How to Run the Application**

### **Prerequisites**
- Java 21
- Gradle (use `./gradlew` located inside the project)

### **Run**
```bash
./gradlew bootRun
```

## **Tests**

### **Run**
```bash
./gradlew test
```

---

## **API Endpoints**

### **Get User Profile**
- **URL**: `/get_client_config/{playerId}`
- **Method**: `GET`
- **Description**: Retrieves a user's details and updates their campaigns if they match the current active campaign.
- **Path Parameters**:
    - `playerId` (UUID): The unique identifier of the player.
- **Response**:
    - **Status**: `200 OK`
    - **Body**: `UserResponseDto` containing the user's details.
- **Error Responses**:
    - `404 Not Found`: If the user with the specified `playerId` does not exist.

---

### **Examples**

Initial data for the examples can be found in `src/main/resources/db/mongodb/data.json`.

1. **Case: Active Campaign Included**
    - **Input**:
        - `playerId`: `97983be2-98b7-11e7-90cf-082e5f28d836`
    - **GET**: `http://localhost:8080/get_client_config/97983be2-98b7-11e7-90cf-082e5f28d836`
    - **Expected Behavior**:
        - The endpoint will return the user with the `active_campaigns` field updated to include the currently active campaign since the conditions are met.

2. **Case: Active Campaign Not Included**
    - **Input**:
        - `playerId`: `97983be2-98b7-11e7-90cf-082e5f28d837`
   - **GET**: `http://localhost:8080/get_client_config/97983be2-98b7-11e7-90cf-082e5f28d837`
    - **Expected Behavior**:
        - The endpoint will return the user with their existing `active_campaigns` field unchanged, as the user does not meet the conditions to include the active campaign.

---

## **Additional Notes**

- **MongoDB Data Initialization**:
    - The application loads initial test data for integration tests from the file:
        - `src/test/resources/db/mongodb/data-test.json`
    - This file contains mock user data for testing the `mongo` profile.
