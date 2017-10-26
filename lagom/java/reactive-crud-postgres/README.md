# Simple Reactive CRUD with MongoDB

This simple app shows how to use Lagom to build a simple web service that performs Reactive CRUD using MongoDB. 

The project shows how you would register a MongoDB client to a Lagom module and interact with MongoDB to perform CRUD operations reactively. 

The underlying MongoDB client is the Mongo Reactive Streams client which is an impmentatino of Reactive Streams and therefore directly useable by Akka Streams. 

The Template class uses Akka Streams to interact with the Mongo collection. 

There's much you can do with a Mongo Collection, but this example is just to show a simple case. 

### note
    This assumes that if you are using Mongo, you already know how to design your queries or would look to a different source for that information. This project is showing to  submit a query and handle the result, both reactively. 

## How to Use
The file CSV file `employee_chicago_salarys` is imported into a local instance of Mongo to start. 

Use the command:

    `mongoimport --db salaries --type csv --headerline --file employee_chicago_salaries.csv`

To run the application, use `sbt runAll`

You can preview your data using the api call:

    `curl http://localhost:9000/api/employee/all`

You'll notice that only a subset of the list is returned. The implementation has hardcoded the number of docs to return. 

You will find the other available calls in the interface `EmployeeService` located in the folder `/employee-api/src/main/java/com/example/employee/api`

