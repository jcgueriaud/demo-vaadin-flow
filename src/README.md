### Base de données MySQL

Pour test il faut avoir une base de données MySQL fyvm (user fyvm // fyvm)
Ceci peut être configuré dans application.properties

Pour installer facilement la base de données sous docker:
## Créer le container
docker run --name demo -p 3306:3306 -e MYSQL_ROOT_PASSWORD=demo -d mariadb
## Créer la base de données vide
docker exec -it demo bash
mysql -u root -p
create database demo;

## Changer la connexion dans application.properties (ou créer un user fyvm)

spring.jpa.database-platform=org.hibernate.dialect.MySQL5Dialect

spring.jpa.hibernate.ddl-auto=create
spring.datasource.url=jdbc:mysql://localhost:3306/demo?useSSL=false
spring.datasource.username=root
spring.datasource.password=demo

##Couper/lancer la db 
docker stop demo // docker start demo

## Détruire le container
docker rm fyvmdb


# Project Base for Vaadin Flow and Spring Boot

This project can be used as a starting point to create your own Vaadin Flow application with Spring Boot.
It contains all the necessary configuration and some placeholder files to get you started.

The best way to use it by via [vaadin.com/start](https://vaadin.com/start) - you can get only the necessary parts and choose the package naming you want to use.

Import the project to the IDE of your choosing as a Maven project. 

Run application using `mvn spring-boot:run` or directly running Application class from your IDE.

Open http://localhost:8080/ in browser


For documentation on using Vaadin Flow and Spring, visit [vaadin.com/docs](https://vaadin.com/docs/v10/flow/spring/tutorial-spring-basic.html)

For more information on Vaadin Flow, visit https://vaadin.com/flow.

Branching information:
* `master` the latest version of the starter, using the latest platform snapshot
* `V10` the version for Vaadin 10
* `V11` the version for Vaadin 11
