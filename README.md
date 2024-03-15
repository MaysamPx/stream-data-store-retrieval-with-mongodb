# stream-data-store-retrieval-with-mongodb
**Problem/Task:** 

Implement a data pipeline to process streaming IoT data. Data is a sort of sensor reading for specific devices. The goal is to provide a simulation of these kinds of data pipelines to handle the incoming data and provide some API to get the average/median/max/min values of the reported metrics. 


**Implementation, in a nutshell:**


# IoT Data Pipeline Project


### Overview
For this project, I've built a lightweight spring boot application that simulates an IoT data stream. It doesn't interact with real devices but instead generates random data to represent sensor readings.

In addition, I've implemented a separate mechanism for storing and retrieving specific metrics reported by different device types.

This project includes two main components: **SensorDataProducer**, and **SensorDataService**. 
The SensorDataProducer sends sensor data to a MongoDB Collection, and the SensorDataService class reads sensor data from the same collection using the MongoDB as a Data Pipeline Storage System. 

### Architecture
![image](https://github.com/MaysamPx/stream-data-store-retrieval-with-springboot-mongodb/assets/13215181/4304755a-0328-4013-ba6e-a8d00e092fd3)

### Dependencies

•	MongoDB

•	Spring Boot

•	Spring Security 

•	Maven (Build and dependency tool)

•	Caffeine (Simple and efficient Spring cache)

•	Lombok (Boilerplate code prevention)

•	JUnit

•	JWT (Used for the scalable security by distributed stateless token verification)

### Building and Running the Project

To build and run this project, follow these steps:

1.	Clone the current Github repo (In a preferred IDE).
2.	Install and run an instance of the MongoDB on a specific port or 17017.
3.	Install OpenJDK 15 on your local machine.
4.	Update the application.properties file to specify the MongoDB and other properties.
5.	Run the SensorDataProducerApplication and SensorDataConsumerApplication classes to start the producer and consumer components, respectively.
Testing the REST APIs
      
### To test the REST APIs, follow these steps:

**Option 1**

1.	Use the Swagger UI to see more details and run each API as well.

2.	Open your browser and go to http://localhost:8080/swagger-ui.

**Option 2**

1.	Get the JWT token.
2.	Pass the JWT token in the Authorization header of each request.

Postman Collection

•	Import the provided Postman collection in the 'PostmanCollectionForRESTAPIs' directory.



### Final tips
####Application Flow:
1. Install and Configure the Kafka, Zookeeper, and KSQL DB instances.
2. Apply Configurations (in application.propertiese and GlobalVariables class).
3. Set Up Run Configuration in the IDE.
4. Make sure that all Maven dependencies are imported correctly.
5. Build and run the application.
6. Call the Authenticate API.
7. Start the Stream Generator by calling the exposed API available in the Swagger doc page.
8. Call the other APIs.
9. For Better performance create a compound index on the fields of SensorName and ReportTimestamp.

db.sensorDataDTO.createIndex( { SensorName: "text", ReportTimestamp: -1 } )

#### Default Username & Password:
Username: datastream

Password: datastream1!
