# stream-data-store-retrieval-with-mongodb
**Problem/Task:** 

Implement a data pipeline to process streaming IoT data. Data is a sort of sensor reading for specific devices. The goal is providing a simulation of this kinds of data pipeline to handle the incoming data and provide some API to get average/median/max/min values of the reported metrics. 


**Implementation, in a nutshell:**

For this project, I've built a lightweight spring boot application that simulates an IoT data stream. It doesn't interact with real devices but instead generates random data to represent sensor readings.

In addition, I've implemented a separate mechanism for storing and retrieving specific metrics reported by different device types.

