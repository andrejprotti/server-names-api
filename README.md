# Server Name API

![Image](https://i.imgur.com/fBwCn06.jpg)

**server-names-api** takes a REST API approach to the basic CRUD functionality of the problem that was presented. Using Maven, Spring Boot, Hibernate and MySQL.

## Requirements

- Java 8
- Maven 3.5.3
- A running instance of MySQL with **server_names_api** and **server_names_api_test** databases created. **Don't forget** to add your database credentials to **/src/main/resources/application.properties** and **/src/test/resources/application.properties** !

## Usage

After cloning the project and switching to the project root, install the application and run the tests with:

    mvn clean install

To start the application:

    mvn spring-boot:run

## API examples

After the application is up and running feel free to copy these curl commands to test it out!

Create a new server:

```
curl -X POST \
  http://localhost:8080/servers/ \
  -H 'Cache-Control: no-cache' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: cf8a32a0-c829-45e2-9c2d-ce746e0170e0' \
  -d '{
  "name":"server1",
  "description":"test"
}'
```

Get all servers:

```
curl -X GET \
  http://localhost:8080/servers/ \
  -H 'Cache-Control: no-cache' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: 142987c6-a8ae-41da-b25a-f60cf1de551a'
```

Get one specific server (use the id on the database):

```
curl -X GET \
  http://localhost:8080/servers/1 \
  -H 'Cache-Control: no-cache' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: 3093be91-9f64-49d7-8069-609375a16837'
```

Update a server (remember to change de id!):

```
curl -X PUT \
  http://localhost:8080/servers/1 \
  -H 'Cache-Control: no-cache' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: 3dc586b0-ea65-4b55-a4ea-f7ea2798b7dc' \
  -d '{
  "name":"updatedName",
  "description":"updatedDescription"
}'
```

Delete a server by id:

```
curl -X DELETE \
  http://localhost:8080/servers/1 \
  -H 'Cache-Control: no-cache' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: 0f2b724a-3f90-449f-a074-dc7fc7e8f755'
```

Count total amount of servers:

```
curl -X GET \
  http://localhost:8080/servers/count \
  -H 'Cache-Control: no-cache' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: 49321647-967c-4ba7-ac95-247220800147'
```

Get help (redirects here):

```
curl -X GET \
  http://localhost:8080/help \
  -H 'Cache-Control: no-cache' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: ccdeb06b-a8b3-4b5a-88a3-645b8feecddf'
```