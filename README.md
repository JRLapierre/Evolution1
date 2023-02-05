# evolution

ce projet est un projet personnel de ma part. Personne ne m'a demande de le faire.

### pourquoi ce projet ?
J'ai decide de me lancer dans ce projet pour plusieurs raisons : mon amour pour les reseaux de neurones, mon amour pour l'evolution, mon amour pour le java et ma frustration par rapport aux autres projets d'evolutions que j'ai pu croiser : il est difficile de traquer qui est lie a qui,combien de generations separent deux individus.  
J'ai donc decide de creer un projet avec un tracage facile, en enregistrant tout les individus, leur donnant des parents avec des id uniques a chaque individus. J'ai aussi mis des id aux connexions qui restent les meme si la connexion vient a muter, mais un nouveau id est cree si une connexion est cree a partir de rien.

### ma programation
Dans ce projet, j'essaie de faire un maximum de moi-m�me et de faire le meilleur code possible. J'ai cr�e la classe ListeChaine mod�lisant une liste chain�e et Aleatoire g�n�rant une suite de nombre pseudo-al�atoires. J'ai cr�e ces classes plut�t que d'importer les classe LinkedList et Random afin de savoir ce qu'il se passe en coulisses et d'avoir le controle.  
Pour la communication avec la m�moire et l'utilisateur, ne sachant pas comment faire, j'ai �t� forc� d'importer les classes pr�-existantes en java comme File ou Scanner.  
<br>
Ce projet est long et demande souvent de revenir sur le code produit des mois auparavant, ce qui m'impose de produire un code clair et comment� m�me si je suis le seul � travailler sur le projet. J'essaie de respecter un maximum de conventions de codage.

#### Structure du projet
##### simulation
La simulation consiste � simuler de l'�volution par s�lection naturelle : � chaque g�n�ration, tous les individus sont �valu�s et ceux qui r�ussisent le mieux sont s�lectionn�s pour se reproduire et ainsi former la g�n�ration suivante. A chaque g�n�ration, des mutations al�atoires se produisent.
##### g�n�ration
Les g�n�rations sont constitu�s d'une population d'individus. Elle g�re les interactions entre la simulation et les individus ainsi que les int�ractions avec la m�moire.
##### individus
Les individus poss�dent tous un cerveau qui d�finit leurs d�cisions.  
Il existe plusieurs type d'individus, selon leur cr�ation :  
Original correspond � un individu cr�� de toutes pi�ces par l'utilisateur,  
CloneParfait correspond � des individus ayant un cerveau identitque � leur pr�d�cesseur,  
CloneMut� correspond � des individus ayant un cerveau mut� par rapport � leur pr�d�cesseur,  
EnfantSexe correspond � des individus dont le cerveau est le m�lange des cerveaux de leurs deux parents,  
Sauvegarde correspond � des individus simplifi�s pour les r�cup�rer depuis une sauvegarde et recr�er une nouvelle g�n�ration � partir d'une sauvegarde
##### cerveau
Le cerveau contient des neurones, et ces neurones sont reli�s entre elles par des connexions. Le cerveau est repr�sent� par la classe Cerveau, les neurones par la classe Neurone et les connexions par la classe Connexion.  
##### mutations
La classe Mutation a �t� cr��e pour g�rer les diff�rentes mutations qui arrivent au cerveau des individus lors de la phase de reproduction.  
<br>


### choses a faire pour continuer le projet

#### idees : 

creer un graphe qui permet de traquer visuellement les generations  
faire des fonctions pour traquer les ancetres et les liens de parentee  
appliquer du multithreading dans la phase de recuperation des donnees  
appliquer du multithreading dans la phase de reproduction  
appliquer du multithreading dans la phase d'enregistrement  
se debarasser de la classe Sauvegarde

#### urgent :

#### TODO
ajouter des fonctionnalites pour mettre en pause  
creer un package main et y mettre toutes les methodes main  
renommer les package core en simulation  
enlever le public de Colonne  

#### trucs a finir si j'en ai le courage

continuer la classe TestAleatoire  
faire un diagramme de classe propre plutot que le plantUML auto genere  
tenir a jour la doc  
resoudre le mystere de la reprise des id a 100 a la generation 51014  

#### taches impossibles (ou presque)
enregistrer l'expression lambda dans un fichier et la recreer a partir de ce fichier  
