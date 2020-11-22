# Coinapp 

#### How to run the application
> docker-compose up

#### How to change the environment
Use the `.env` file in the root
- `default` uses in-memory DB
- `mongo` uses the MongoDB

#### How to run the provided tests 
> newman run LND-Web-API.postman-collection.json

#### To run the JUnit tests
> cd domain

> mvn test

#### How to see reports
> cd domain

> mvn site

Open domain/target/site/index.html in a browser and navigate to Project Reports. See Surefire Report