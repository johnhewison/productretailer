## Description
This project is an example of a simple SpringBoot application.  
A few Spock tests have been made to show how to test the different layers, Repository, Service and RestController. 

## Build and run
./gradlew build  
./gradlew bootRun

## Database
http://localhost:8080/h2-console

JDBC URL: jdbc:h2:mem:testdb  
User Name: sa

## Audit
http://localhost:8080/product/audit
This returns a csv file and creates a row in the 'productaudit' table for each 'Product'.

## Default Product
The default products are loaded by the DataLoader class which is only used when the 'dataloader' profile is active. 
