PROJET S5
GROUPE : Gabriel Soulie, Mohammed Benallal, Valentin Tahon

-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*
PRE-REQUIS POUR FAIRE FONCTIONNER LE PROJET :

1) MySQL (root admin SANS MOT DE PASSE ou mdp vide) pour lancer Server.jar
2) Java 16 (minimum)

La base de donnees est AUTOMATIQUEMENT CREE lors du lancement de Server.jar avec un petit jeu de donnees prefait

Le Client.jar ouvre une fenetre de connexion, si le compte renseigne est celui d'un admin, l'interface serveur se lancera, sinon, l'interface utilisateur se lancera

Il est possible de lancer jusqu'a 100 utilisateurs (hardcode), mais je ne vous recommande pas d'essayer :)

Voici les differents utilisateurs implementes de base dans la DB :

username	-	password
-----------------------------------------------
root		  -	root		(ADMIN)
Jean31		-	123
Pierre31	-	123
Vincent31	-	123
Dylan31	  - 123


Nom de la DB : projetS5VGM

-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*
Fonctionnalitees implementees :

* Fenetre de connexion :
- Refus d'un utilisateur non authentifie
- Lance l'interface serveur si utilisateur admin, sinon interface utilisateur

* Interface utilisateur :
- Ajout d'un thread
- Ajout d'un message
- Reception des messages a la connexion
- Update en direct des threads, messages et status des messages
- 4 statuts de messages : Pas envoye (gris), pas recu par tous (rouge), pas lus par tous (orange), lus par tous (vert)
- Lors de l'ouvertue d'un thread, envoie d'update du statut des messages au serveur
- Reconnexion automatique en cas de deconnexion du serveur
- Abandon de la reconnexion automatique si l'utilisateur a ete supprime de la base de donnees
- L'utilisateur voit les threads de son groupe ainsi que les threads dont il est l'auteur
- L'utilisateur, lorsqu'il est ajoute a un nouveau groupe, recoit l'update de ses threads
- L'utilisateur, lorsqu'il est retire d'un groupe, il perd la possibilite de voir les threads de ce groupe (et donc d'y participer)
- Si un utilisateur est supprime, tous les threads dont il est l'auteur et tout ses messages seront supprimes pour tout le monde
- Si un utilisateur n'ayant pas vu ou pas recu un message se fait retirer du groupe concerne, la couleur du message pourra etre impactee en consequence (si il etait le seul a ne pas avoir lu, le message passera de orange a vert)

* Interface administrateur :
- Affichage des utilisateurs avec mention de leur etat de connexion
- Ajout d'un utilisateur
- Ajout d'un groupe
- Ajout d'un utilisateur a un groupe
- Suppression d'un utilisateur
- Suppression d'un groupe
- Suppression d'un utilisateur a un groupe
- Reconnexion automatique en cas de deconnexion du serveur
