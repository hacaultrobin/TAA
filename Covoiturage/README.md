# TAA et GLI - Projets Covoiturage
## Auteurs
Anthony LHOMME et Robin HACAULT
## Informations
Dans ce dépôt se trouvent les projets de covoiturage des enseignements TAA et GLI. Il a 3 projets Maven :
- [covoiturage-server](./covoiturage-server)
    - Projet contenant la partie serveur de l'application de Covoiturage, avec le modèle métier (entités JPA) et les services REST d'accès et de modification du modèle.
- [covoiturage-play](./covoiturage-play)
    - Projet Play de réalisation d'un client de l'application de covoiturage
- [covoiturage-gwt](./covoiturage-gwt)
    - Projet GWT de réalisation d'un client de l'application de covoiturage. 
- [covoiturage-angular](./covoiturage-angular)
    - Projet Angular.js de réalisation d'un client de l'application de covoiturage

## Lancement de l'application de covoiturage
- Démarrer le serveur - voir projet [covoiturage-server](./covoiturage-server)
- Démarrer le client souhaité - voir projet au choix :
    - [covoiturage-play](./covoiturage-play)
    - [covoiturage-gwt](./covoiturage-gwt)
    - [covoiturage-angular](./covoiturage-angular)


## Architecture de l'application
L'application se décompose en 3 couches, à savoir :
- Le modèle métier, rendu persistant en base grâce aux entités JPA
- La partie services, interface HTTP/REST permettant d'interagir avec le modèle métier (lecture et modification)
- Le client (versions au choix : GWT, AngularJS, Play) qui communique avec la couche de services via des requêtes HTTP
