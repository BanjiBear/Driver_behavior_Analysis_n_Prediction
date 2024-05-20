# Driver Behavior Analysis and Prediction

![img](https://img.shields.io/badge/Build%20on%20Centos%20Linux%207.9-Pass-brightgreen) ![](https://img.shields.io/badge/Maven%20Build-Pass-brightgreen) ![](https://img.shields.io/badge/Coverage-93%25-brightgreen) ![](https://img.shields.io/badge/Release%20Version-V1.0-orange) 

## Introduction
Driver Behavior Analysis and Prediction is a web service that utilized the Java Spark framework for real-time big data analysis and visualziation, and Java SpringBoot to provide RESTful web services. The requirements are to analyze the given driving behavior dataset of 10 drivers over 10 consecutive days. Detailed requirements include:
- [x] A Website for Driving Behavior Analysis
- [x] Real Time
- [x] Generate a summary to show the driving behavior of each driver.
- [x] Monitor the driving speed of each driver in real time.
- [x] Use Amazon Web Services (AWS) to develop the website.
- [x] Analyze the driving behavior with **Spark** (Spark-SQL)
- [x] The cumulative number of times for each driver
- [x] Use a diagram to visualize the driving speed
- [x] When the driver is speeding, a warning will be issued
- [x] Automatically Updated every 30 seconds
- [x] Running of the website: no run-time error
- [x] Effective and user-friendly user interface

## Web service deployment on AWS
- https://aws.hoptraf.hirsun.tech
- http://dev.hoptraf.hirsun.tech
<img width="1440" alt="Screenshot 2024-05-20 at 6 55 19 PM" src="https://github.com/BanjiBear/Driver_behavior_Analysis_n_Prediction/assets/70761188/f837a273-4b69-4128-99bf-a18b356ee60f">
<img width="1440" alt="Screenshot 2024-05-20 at 6 55 31 PM" src="https://github.com/BanjiBear/Driver_behavior_Analysis_n_Prediction/assets/70761188/dde48688-744c-45c2-a246-e72792fcc60d">
<img width="1440" alt="Screenshot 2024-05-20 at 6 55 42 PM" src="https://github.com/BanjiBear/Driver_behavior_Analysis_n_Prediction/assets/70761188/714388d8-881e-4d55-97d4-20c31763c18d">
<img width="1440" alt="Screenshot 2024-05-20 at 6 56 00 PM" src="https://github.com/BanjiBear/Driver_behavior_Analysis_n_Prediction/assets/70761188/a50e2bda-a483-4b13-97ed-9fb2f3378b89">

## Environment
- Pruduction Env - Operation system: Centos 7.9 (Linux)
- Development Env - Operation system: Macos 14.4.1 (23E224)
- Programming language: Java 17.0.10
- Application Framework: StringBoot 3
- Dependencies/Required packages in Java: 
    ```
    lombok
    spring-boot-configuration-processor
    jedis
    spark-core_2.13
    spark-sql_2.13
    janino
    fastjson2
    mysql-connector-j
    mybatis-spring-boot-starter
    druid-spring-boot-starter
    ```
- Software for Building: Maven
- Other Components  in used
  ```
  Mysql
  Redis
  Nginx
  ```

## Data
The data of this project was provided in advance containing 10 files of 10 drivers’ driving behavior over 10 consecutive days. These data are stored in CSV files without headers with a total of 413,450 records. Each record consists of 19 features (columns) vary from driver information to specific driving behavior indications.
<img width="1067" alt="1" src="https://github.com/BanjiBear/Driver_behavior_Analysis_n_Prediction/assets/70761188/dfab0dea-657f-4e60-864b-13f67153ac79">

## Features and Functionalities
For detailed description of Features and Functionalities with figures illustration, please refer to: [Section 2 of the application report](https://github.com/BanjiBear/Driver_behavior_Analysis_n_Prediction/tree/a31fdd1ea4e288afe6a4186e591adbe58f1b8d74/report)
- Data Time Simulation
- Real-time Monitoring
- Data Visualization
- Data Query
- Adjustable Display

## System Design and Architecture
The program is written in Java with the SpringBoot framework for building RESTful APIs and using Java Spark for data related operations.
This project adopts a multi-layer architecture. The multi-layer architecture completely separates the front end and backend, while the backend can further be divided into three different layers. As illustrated in the figure below, the front end model, also known as the view model, is responsible for listening to request data and rendering response data. The model accepts input request then pass it to the backend embedded in a HTTP body and return response data in JSON format in a web page. Since SpringBoot web services are RESTful, the response data are automatically configured into JSON format. As for the backend, it consists of three layers, the Controller layer, Service layer, and Data persistence layer. The Controller layer manages the input and output data but only interacts with the service layer. According to different input paths, Controller pass the data to different service interfaces in the service layer. The service layer holds the interface and implements the core application logic and functionalities. This layer interacts with the Controller, the data, Spring Beans, and different entities (Plain-Old-Java-Objects, POJOs). Lastly, the Data Persistence Layer fetches data via SparkSQL and data persistence via MySQL. With the multi-layer design, this project is able to achieve parallel development, improved maintainability, and seamless upgrade in CI/CD.
<img width="1173" alt="Screenshot 2024-05-20 at 7 03 32 PM" src="https://github.com/BanjiBear/Driver_behavior_Analysis_n_Prediction/assets/70761188/51c96a35-9a3f-4a2d-976d-8dc418370121">
<img width="948" alt="Screenshot 2024-05-20 at 7 03 17 PM" src="https://github.com/BanjiBear/Driver_behavior_Analysis_n_Prediction/assets/70761188/31da1826-844c-4bc2-89a3-53b70d9e3dce">

## AWS Deployment Architecture
<img width="1024" alt="Screenshot 2024-05-20 at 7 04 01 PM" src="https://github.com/BanjiBear/Driver_behavior_Analysis_n_Prediction/assets/70761188/cd0435f0-dd4d-4923-80ce-ed4aef378fb3">


