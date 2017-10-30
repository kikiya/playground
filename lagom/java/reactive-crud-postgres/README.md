# Simple Reactive CRUD with Postgres

This simple app shows how to use Lagom to build a simple web service that performs Reactive CRUD using Alpakka Slick with Postgres. 

The project shows how you would register a Slick Template to a Lagom module and interact with Postgres to perform CRUD
operations with reactive programming. 

The Template class uses Alpakka to interact with the Postgres DB. 

There's much you can do with a Postgres + SQL, but this example is just to show a simple case. 

### note
    This assumes that if you are using Postgres, you already know how to design your queries or would look to a 
    different source for that information. This project is showing to submit a query and handle the result,
    both reactively. 

## How to Use
The file CSV file `employee_chicago_salarys` is imported into a local instance of Postgres to start. 

To easily do this, pgfutter here https://github.com/lukasmartinelli/pgfutter or you can use the Postgres COPY feature
directly

The pgfutter command:

    `pgfutter csv employee_chicago_salaries.csv`

To run the application, use `sbt runAll`

You can preview your data using the api call:

    `curl http://localhost:9000/api/employee/all`

You'll notice that only a subset of the list is returned. The implementation has hardcoded the number of docs to return. 

You will find the other available calls in the interface `EmployeeService` located in the folder `/employee-api/src/main/java/com/example/employee/api`

