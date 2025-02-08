Overview

This project is a Spring Boot & GraphQL-based application with a Vue.js frontend.
It was designed to provide efficient search, filtering, and auto-suggestion functionalities using Elasticsearch as the backend instead of a traditional relational database like PostgreSQL.


How to use
To start the application, follow these steps:
1.	Navigate to the Project Directory
2.	Build and Start Docker Containers
    Run the following command to build and start all containers:

    docker-compose up --build

    This will start the following services:
    	•	Elasticsearch (on port 9200)
    	•	Kibana (on port 5601)
    	•	Spring Boot Backend (on port 8080)
    	•	Vue.js Frontend (on port 8081)

3. After the command completes, check running containers:
    docker ps
4. 
4. Once the application is up and running:
   	•	Frontend (Vue.js App):
   	        Open http://localhost:8081 in your browser.
   	•	Backend (Spring Boot GraphQL API):
            Access the API at http://localhost:8080/graphql
   	•	Elasticsearch Dashboard (Kibana):
            Open http://localhost:5601



Technology Stack
	•	Backend: Spring Boot, GraphQL, Elasticsearch
	•	Frontend: Vue.js (Vite)
	•	Build & Deployment: Docker, Docker Compose

	Why I Chose GraphQL for This Project?
        Reduces API complexity by allowing frontend to fetch only needed fields.
        Faster performance with optimized data fetching.
        Scalability for handling multiple UI clients without changing the backend.

    Why Elasticsearch was the Best Choice for This Project?
        Designed for search-based applications with near-instant response times.
        Auto-suggestion support using built-in n-gram & edge n-gram analyzers.
        Scalable & distributed for handling large amounts of data.
        Faster query execution for searching across multiple fields.

    Backend Development (Spring Boot + GraphQL + Elasticsearch)
        Developed GraphQL queries & mutations for managing activities and suppliers.
        Integrated Elasticsearch for search, filtering, and auto-suggestion features.
        Implemented exception handling using GlobalExceptionHandler.
        Used DTOs & Mappers for structured data presentation.
        Unit Testing

    Frontend Development (Vue.js + GraphQL Integration)
        Designed UI components for search, filtering, and displaying activity details.
        Integrated GraphQL queries in Vue using Apollo Client.
        Implemented auto-suggest functionality for better user experience.

    New Feature: Auto-Suggest (Enhancing User Experience)
        Why is it useful?
            Helps new users discover relevant searches.
            Provides real-time suggestions based on input keywords.
            Improves usability & navigation for a better experience.

Spring Boot GraphQL Project Documentation

Spring Boot GraphQL application that interacts with Elasticsearch for storing and retrieving activity and supplier data.
The architecture follows a structured package-based approach, ensuring modularity and maintainability.

Package	                Description

controller          	Entry point for GraphQL queries and mutations. Routes requests to services.
dto	                    Data Transfer Objects (DTOs) for presenting data to the client.
entity	                Contains Activity and Supplier entities, representing Elasticsearch documents.
exception	            Contains custom exception handlers for error management.
input	                Defines input models (ActivityInput, SupplierInput) for GraphQL mutations.
mapper	                Maps entities to DTOs for presentation logic.
repository	            Defines ActivityRepository and SupplierRepository for Elasticsearch interactions.
service	                Contains business logic for handling activities, suppliers, and search functionalities.


GraphQL API Documentation
    1. getActivities(title: String, specialOffer: Boolean, minPrice: Int, maxPrice: Int): [Activity]
        Retrieves a list of activities filtered by search parameters such as title, price range, and special offers.

    2. searchAutoSuggest(keyword: String): [AutoSuggestResult]
        Returns auto-suggested activities based on the user’s input keyword.

    3. getAllActivities: [Activity]
        Retrieves all available activities in the system without any filters.

    4.getActivityById(id: Int): Activity
        Fetches a specific activity by its ID.

GraphQL Mutations
    1.insertActivity(activity: ActivityInput): Int
        Inserts a new activity into the system and returns the newly created activity ID.

    2.insertSupplier(supplier: SupplierInput): Int
        Inserts a new supplier into the system and returns the newly created supplier ID.


Project Testing Documentation
    1. GraphQL Query Structure and Data Tests with Real-Time Data
        testGetAllActivities : Ensures the getAllActivities GraphQL query returns all activities stored in Elasticsearch.
        testGetActivitiesByTitle: Ensures filtered search by title works correctly
        testSearchAutoSuggest : verifies the auto-suggestion feature using partial keyword matches
        testGetActivityById: Ensures that fetching a single activity by ID returns the correct result.

    2. Mocked Elasticsearch Tests
        testSearchActivities:Ensures that the search function works correctly without querying Elasticsearch.
        testSearchSupplierNotFoundException:Ensures that if a supplier does not exist, an appropriate error is returned.

    3. Service Layer Tests
        testGetActivity_Success: Ensures that an activity can be successfully fetched when it exists in the database.
        testGetActivity_ActivityNotFound: Ensures that if an activity is not found, the service throws the correct exception.
        testGetActivity_SupplierNotFound: Ensures that if an activity exists, but its supplier is missing, an error is thrown.

Enhancement:

1.	We can implement a login and logout feature, allowing users to sign in and out for a more personalized search experience.
    By creating a user profile, we can track search history and provide recommendations based on previous searches using a machine learning-based algorithm.
2.  Another enhancement could be recommendations based on similar users’ searches. 
    By analyzing what other users with similar interests have searched for, we can suggest relevant activities and suppliers, improving the overall user experience.
3.	I have written unit test cases for major functions, including testing activities retrieval, fetching all activities, and searching activities by title.
4.	Additionally, we can expand test coverage by writing edge test cases, ensuring all scenarios are accounted for. This includes filtering by minimum and maximum price, applying special offers, and handling other possible filtering conditions.




