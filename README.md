# evolution

ce projet est un projet personnel de ma part. Personne ne m'a demande de le faire.

### pourquoi ce projet ?
J'ai decide de me lancer dans ce projet pour plusieurs raisons : mon amour pour les reseaux de neurones, mon amour pour l'evolution, mon amour pour le java et ma frustration par rapport aux autres projets d'evolutions que j'ai pu croiser : il est difficile de traquer qui est lie a qui,combien de generations separent deux individus.  
J'ai donc decide de creer un projet avec un tracage facile, en enregistrant tout les individus, leur donnant des parents avec des id uniques a chaque individus. J'ai aussi mis des id aux connexions qui restent les meme si la connexion vient a muter, mais un nouveau id est cree si une connexion est cree a partir de rien.

### ma programation
Dans ce projet, j'essaie de faire un maximum de moi-même et de faire le meilleur code possible. J'ai crée la classe ListeChaine modélisant une liste chainée et Aleatoire générant une suite de nombre pseudo-aléatoires. J'ai crée ces classes plutôt que d'importer les classe LinkedList et Random afin de savoir ce qu'il se passe en coulisses et d'avoir le controle.  
Pour la communication avec la mémoire et l'utilisateur, ne sachant pas comment faire, j'ai été forcé d'importer les classes pré-existantes en java comme File ou Scanner.  
<br>
Ce projet est long et demande souvent de revenir sur le code produit des mois auparavant, ce qui m'impose de produire un code clair et commenté même si je suis le seul à travailler sur le projet. J'essaie de respecter un maximum de conventions de codage.

#### Structure du projet
##### simulation
La simulation consiste à simuler de l'évolution par sélection naturelle : à chaque génération, tous les individus sont évalués et ceux qui réussisent le mieux sont sélectionnés pour se reproduire et ainsi former la génération suivante. A chaque génération, des mutations aléatoires se produisent.
##### génération
Les générations sont constitués d'une population d'individus. Elle gère les interactions entre la simulation et les individus ainsi que les intéractions avec la mémoire.
##### individus
Les individus possèdent tous un cerveau qui définit leurs décisions.  
Il existe plusieurs type d'individus, selon leur création :  
Original correspond à un individu créé de toutes pièces par l'utilisateur,  
CloneParfait correspond à des individus ayant un cerveau identitque à leur prédécesseur,  
CloneMuté correspond à des individus ayant un cerveau muté par rapport à leur prédécesseur,  
EnfantSexe correspond à des individus dont le cerveau est le mélange des cerveaux de leurs deux parents,  
Sauvegarde correspond à des individus simplifiés pour les récupérer depuis une sauvegarde et recréer une nouvelle génération à partir d'une sauvegarde
##### cerveau
Le cerveau contient des neurones, et ces neurones sont reliés entre elles par des connexions. Le cerveau est représenté par la classe Cerveau, les neurones par la classe Neurone et les connexions par la classe Connexion.  
##### mutations
La classe Mutation a été créée pour gérer les différentes mutations qui arrivent au cerveau des individus lors de la phase de reproduction.  
<br>


### choses a faire pour continuer le projet

#### idees : 

creer un graphe qui permet de traquer visuellement les generations  
faire des fonctions pour traquer les ancetres et les liens de parentee  
appliquer du multithreading dans la phase de recuperation des donnees  
appliquer du multithreading dans la phase de reproduction  
appliquer du multithreading dans la phase d'enregistrement  
revoir la generation du cerveau pour les nouveaux individus  
ecrire la derniere generation enregistree dans un fichier  
ajouter des fonctionnalites pour mettre en pause  
faire un fichier java contenant tous les parametres  


#### urgent :

#### TODO
faire un convertisseur binaire-json pour visualiser les individus enregistres en binaire  
mettre dans le package main toutes les methodes main  
revoir la classe Mutation (n'agis que sur une liste de connexions)  

#### trucs a finir si j'en ai le courage

continuer la classe TestAleatoire  
faire un diagramme de classe propre plutot que le plantUML auto genere  
tenir a jour la doc  

#### taches impossibles (ou presque)
enregistrer l'expression lambda dans un fichier et la recreer a partir de ce fichier  
