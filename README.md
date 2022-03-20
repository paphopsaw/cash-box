# cash-box
This repository is for a java bootcamp assignment

## Write-ups for the assignment
Please refer to the information on the repository wiki
* [Background](https://github.com/paphopsaw/cash-box/wiki/Background)
* [Design](https://github.com/paphopsaw/cash-box/wiki/Design)

## Setup and run
Java 11
```
./mvnw clean
./mvnw compile
./mvnw test
./mvnw package
./mvnw spring-boot:run
```

Sample requests using curl
```
curl http://localhost:8080/api/center-management/center/1
curl http://localhost:8080/api/delivery-management/cits/2022/03/20
curl http://localhost:8080/api/truck-management/truck/1
curl -X POST http://localhost:8080/api/truck-management/truck/1/updateLocation
   -H 'Content-Type: application/json'
   -d '{
            "latitude": 40.00,
            "longitude": 100.00,
            "dateTimeString": "2022-03-20 01:18:11"
        }'
curl -X POST http://localhost:8080/api/delivery-management/cit/1/confirm
   -H 'Content-Type: application/json'
   -d '{
            "amountTHB": 99,
            "amountUSD": 199
        }'
```
Or via [Postman](https://www.getpostman.com/collections/abe9087e595faa62a578)?
