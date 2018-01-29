# Infojobs Technical test

## Short description
Web app without any framework or external library, except for testing purpose, only with Java 8 and common desing patterns.
## How to run
* For test and coverage reports ``mvn test`` reports will be created in target/jacoco-ut/index.html
* For create fat jar execute  ``mvn package`` 
* In target directory execute ``java -jar pruebaInfojobs-1.0-jar-with-dependencies.jar``
* App run in 8000 port

For rest api in folder postman exists CRUD requests for test.

## Project Structure

* Controllers -> Implements C in MVC pattern, responsible of bind request values to the model and redirect to proper view. For the implementation I choosed template pattern, with this pattern I gained flexibility in order to make inversion of control in ``RoutesDefinition``
* Dao -> Implements DAO pattern with generics. Every model has a concrete dao in order to wrap the infrastructure.
* Exception -> Models status code into runtime exceptions in order to interrupt current execution and response with proper http status code.
* Filter -> Security filter executes before all request. If the path is secured the filter use a concrete authenticator, for the web paths check session cookie and for rest paths check basic authentication headers and in both check the users rights for the requested path.
* Handler -> Route mapping of the app, factory pattern with enums for construct concrete controller for path, strategy pattern with enums for decision of http method execution.
* Helper -> Utility classes, help in data transformation for easy use in the system.
* Infrastructure -> Class that simulates the database, implements singleton pattern with synchronized map in order to share the same information for different clients.
* Model -> This object represents the domain of the applications, with rich model (builders, self gestionated state...)
* ViewResolver -> Concrete view resolve for Accept header, can render:
	*  Html with simple temlate pattern, only render web app request.
	* JSON with simple custom parser, for rest request with header Accept : aplication/json 
 	* XML with simple custom parser, for rest request with header Accept : aplication/xml