# Stream data store retrieval with mongodb
### **Problem/Task:** 

Implement a data pipeline to process streaming IoT data. Data is a sort of sensor reading for specific devices. The goal is to provide a simulation of these kinds of data pipelines to handle the incoming data and provide some APIs to get the average/median/max/min values of the reported metrics. 


### Implementation Overview
For this project, I've built a lightweight spring boot application that simulates an IoT data stream. It doesn't interact with real devices but instead generates random data to represent sensor readings.

In addition, I've implemented a separate mechanism for storing and retrieving specific metrics reported by different device types.

This project includes two main components: **SensorDataProducer**, and **SensorDataService**. 
The SensorDataProducer sends sensor data to a MongoDB Collection, and the SensorDataService class reads sensor data from the same collection using the MongoDB as a Data Pipeline Storage System. 

### Architecture
![image](https://github.com/MaysamPx/stream-data-store-retrieval-with-springboot-mongodb/assets/13215181/e8af092b-f7bf-498c-8477-968ce0af2369)

### Dependencies

•	MongoDB

•	Spring Boot

•	Spring Security 

•	Maven (Build and dependency tool)

•	Caffeine (Simple and efficient Spring cache)

•	Lombok (Boilerplate code prevention)

•	JUnit

•	JWT (Used for the scalable security by distributed stateless token verification)

### Installing MongoDB on a Docker Container Locally

1. Install Docker Desktop on your local machine
2. Ensure Docker Desktop is installed and running.
3. Open a terminal and run:
```
docker pull mongo
```
4. Start a MongoDB container:
```
docker run -d -p 27017:27017 --name mongodb mongo
```
5.  MongoDB is now running on `localhost:27017`.

To stop the container run the following command:
```
docker stop mongodb
```

To restart:
```
docker start mongodb
```

### Building and Running the Project

To build and run this project, follow these steps:

1.	Clone the current Github repo (In a preferred IDE).
2.	Install and run an instance of the MongoDB on a specific port or 27017.
3.	Install OpenJDK 21 on your local machine.
4.	Update the application.properties file to specify the MongoDB and other properties.
5.	Run the SensorDataProducerApplication and SensorDataConsumerApplication classes to start the producer and consumer components, respectively.
Testing the REST APIs
      
### To test the REST APIs, follow these steps:

**Option 1**

1.	Use the OpenAPI to see more details and run each API as well.

2.	Open your browser and go to http://localhost:8080/v3/api-docs.

**Option 2**

1.	Get the JWT token.
2.	Pass the JWT token in the Authorization header of each request.

Postman Collection (Not ready yet)

•	Import the provided Postman collection in the 'PostmanCollectionForRESTAPIs' directory.

#### Get Token by this call:
```
curl --location 'localhost:8080/authenticate' \
--header 'Content-Type: application/json' \
--data '{
    "username": "datastream",
    "password": "Datastream1!"
}'
```

#### Start the Sample Data Generator
```
curl --location 'localhost:8080/generator/start' \
--header 'Authorization: Bearer PASTE_TOKEN_HERE'
```

#### Wait for a few seconds and call metric APIs. e.g: it fetches average of reported metrics for "Sensor_1"
```
curl --location 'http://localhost:8080/api/v1/Sensor_1/average?start=2025-01-22T13%3A27%3A48.994&end=2025-01-29T14%3A27%3A48.994' \
--header 'Authorization: Bearer PASTE_TOKEN_HERE'
```

### Final tips
#### Application Flow:
1. Install and Configure the MongoDB.
2. Apply Configurations (in application.properties and GlobalVariables class).
3. Set Up Run Configuration in the IDE.
4. Make sure that all Maven dependencies are imported correctly.
5. Build and run the application.
6. Call the Authenticate API.
7. Start the Stream Generator by calling the exposed API available on the OpenAPI doc page.
8. Call the other APIs.
9. For Better performance create a compound index on the fields of SensorName and ReportTimestamp.

```
db.sensorDataDTO.createIndex( { SensorName: "text", ReportTimestamp: -1 } )
```

#### Default Username & Password:
Username: datastream

Password: Datastream1!
