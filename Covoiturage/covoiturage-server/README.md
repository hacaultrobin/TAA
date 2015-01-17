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

## Accès au service web
Lancer un client ou effectuer les requêtes HTTP suivantes à l'URL de base [http://localhost:8080/covoiturage/rest](http://localhost:8080/covoiturage/rest)

 Type  |              URL             |                                        Action
-------|------------------------------|------------------------------------------------------------------------
  GET  |           /events            | Retourne la liste de tous les events
  GET  |         /events/{id}         | Retourne l'event d'identifiant {id}
  GET  |     /events/{id}/users       | Retourne les participants de l'event d'identifiant {id}
  GET  |       /events/{id}/cars      | Retourne les voitures de l'event d'identifiant {id}
 POST  |           /events            | Ajoute un nouvel event (paramètres du POST : date, place, description)
 POST  |       /events/{id}/join      | Crée un passager et l'ajoute à l'event {id} (params du POST : username)
 POST  |   /events/{id}/joindriver    | Crée un conducteur, sa voiture et les ajoutent à l'event {id} (params du POST : username, modelCar, nbSeatsCar)
DELETE |          /events/{id}        | Supprime l'event d'identifiant {id}
DELETE |/events/{eid}/removeuser/{uid}| Supprime l'utilisateur d'identifiant {uid}, et le désinscrit de l'event {eid}. Si conducteur et pas de passagers dans sa voiture : Suppression de la voiture et du conducteur. Sans effet si le conducteur transporte des passagers.
