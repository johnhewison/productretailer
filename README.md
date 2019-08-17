# productretailer
A Java Technical Test

## The Task
The idea of the technical test is to showcase your technical skill with the programming language. 
There is not one ‘right answer’  
We’ll look at the entire solution  
It should be submitted in the form of a public git repo which our developers can clone and review

### Problem description
Implement a system to check the stock levels for the products sold by a retailer, and apply a number of ‘rules’ before returning advice for which products should be ordered

### Acceptance Criteria
* The input and output can be via any medium you’re comfortable with (i.e. filesystem, http request, queuing framework etc).  
* The rules to be applied can be stored however you think is most appropriate.  
  * There should be a rule allowing specification of a minimum stock level for a product. I.e. product specific minimum stock level  
  * There should be a rule allowing products to be marked as blocked – i.e. they should not be reordered  
  * It should be possible to add a rule to order an additional volume of a certain product  
* Optional: in addition to returning the output of the stock-check, the results should be persisted to a database to maintain an audit of the stock-check and recommended purchase history

### Example:
5 products and their stock levels:  
a 5  
b,8  
c,2  
d,0  
e,1  

### Rules
* Products a,b,c & e have a minimum stock level of 4.
* Product d has a minimum stock level of 8
* Product c is blocked and new stock should not be ordered
* Product d has a one-off order of 15



## Solution
This is a simple example of a simple SpringBoot application.  
A few Spock tests have been made to show how to test the different layers, Repository, Service and RestController.
NB. Production code would include much more thorough tests. 

## Build and run
./gradlew build  
./gradlew bootRun

## Database
http://localhost:8080/h2-console

JDBC URL: jdbc:h2:mem:testdb  
User Name: sa

## Default Product
The default products are loaded by the DataLoader class which is only used when the 'dataloader' profile is active. 

## REST Services
http://localhost:8080/product/order/{productId}/{additionalVolume}
Order additional products.
```
$ curl 'http://localhost:8080/product/order/1/4' -i -X PUT \
    -H 'Content-Type: application/json'
```

http://localhost:8080/product/miniumstock/{productId}/{minimumstock}
Set the minimum stock level for a product.
```
$ curl 'http://localhost:8080/product/miniumstock/1/8' -i -X PUT \
    -H 'Content-Type: application/json'
```

http://localhost:8080/product/block/{productId}
Block a product. No more orders can be made for this product.
```
$ curl 'http://localhost:8080/product/block/1' -i -X PUT \
    -H 'Content-Type: application/json'
```

http://localhost:8080/product/unblock/{productId}
Unblock a product.
```
$ curl 'http://localhost:8080/product/unblock/1' -i -X PUT \
    -H 'Content-Type: application/json'
```

http://localhost:8080/product/audit  
This returns a csv file and creates a row in the 'productaudit' table for each 'Product'.
```
$ curl 'http://localhost:8080/product/audit' -i -X GET    
```