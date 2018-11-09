# Application déployée 

L'application de démonstration est visible à cette adresse:

http://demo-vaadin.lusis.lu:8080/

# Lancement de l'application

## Prérequis

Ce projet nécessite JAVA 8 et Maven installé.

## Explications

L'application en demo fonctionne par défaut sous base de données H2.

## Configuration Google Map

Pour voir une map Google il faut modifier le fichier application.properties
Sinon la carte googlemap fera une erreur javascript à l'exécution (le reste du projet tourne même si cette étape n'est pas effectuée)

googlemap.apikey=FILLTHISKEY

Mettre une apikey correcte.

## Lancement du projet

Importer le projet dans votre IDE en tant que projet Maven.

Lancer la classe Application dans l'IDE.

OU en ligne de commande: `mvn spring-boot:run`

Ouvrir le navigateur à la page http://localhost:8080/

# Base de données MySQL

Il est possible de connecter l'application sur une base de données MySQL

Il faut créer une base MySQL demo (user demo // demo)
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
docker rm demo


