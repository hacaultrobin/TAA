# covoiturage-server
## Auteurs
Anthony LHOMME et Robin HACAULT
## Informations
Dans ce répertoire se trouve la partie "serveur" du projet de covoiturage, qui comprend :
- Le modèle de données de l'application, rendu persistant en base grâce aux entités JPA.
- Un service WEB de type REST pour l'accès au modèle et sa modification.

## Lancement du serveur de l'application
- Lancer le serveur de base de données : script [run-hsqldb-server.sh](./covoiturage-server/run-hsqldb-server.sh).
- Si premier démarrage : Lancer le programme [InitDatabase.java](./covoiturage-server/src/main/java/fr/istic/m2gl/covoiturage/db/InitDataBase.java) du package db, qui va initialiser les tables et insérer quelques objets persistants en base.
- Lancer le service REST avec la commande Maven : mvn tomcat7:run (Lancé sur le port 8080)
