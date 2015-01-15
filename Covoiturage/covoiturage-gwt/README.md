# covoiturage-gwt
## Auteurs
Anthony LHOMME et Robin HACAULT
## Informations
Dans ce répertoire se trouve la partie "client" (GWT) du projet de covoiturage, qui offre une vue graphique des évènements programmés, et offre la possibilité d'ajouter ou supprimer des évènements, des conducteurs et des passagers.

## Lancement du client
- Entrer la commande Maven suivante : mvn clean compile gwt:compile package tomcat7:run-war-only
- Accéder à l'application : [http://localhost:8082/covoiturage.html](http://localhost:8082/covoiturage.html)

L'application est lancée sur le port 8082. Ceci est modifiable dans la configuration de tomcat7, dans le fichier [pom.xml](./covoiturage-gwt/pom.xml)
