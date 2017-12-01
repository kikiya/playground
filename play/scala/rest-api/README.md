# Simple REST Hello World

## What you’ll build

You’ll build a service that will accept HTTP GET requests at:

`http://localhost:9000/greeting`

and respond with a JSON representation of a greeting:

`{"count":1,"greeting":"Hey, World"}`

You can customize the greeting with an optional **guest** parameter in the query string:

`http://localhost:9000/greeting?guest=Person`

The **guest** parameter value overrides the default value of "World" and is reflected in the response:

`{"count":1,"greeting":"Hey, Person"}`

A global atomic counter is used to keep an interesting stat of the number of times this API has greeted someone.
It's not particularly useful, but nice to verify that each reload of the page is calling the 
greeting code again. This is represented by the "count" field.
