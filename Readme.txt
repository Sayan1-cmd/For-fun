Run the project with the maven command 

spring-boot:run -Dspring-boot.run.arguments=$fileName

for example:
spring-boot:run -Dspring-boot.run.arguments=InputFile

As soon as application started go to http://localhost:8080/h2-console, 
connect to the H2 with JDBC URL:jdbc:h2:mem:testdb; Driver Class:org.h2.Driver and you will see the DB schema
