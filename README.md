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


### choses a faire pour continuer le projet

#### idees : 

creer un graphe qui permet de traquer visuellement les generations  
faire des fonctions pour traquer les ancetres et les liens de parentee  
appliquer du multithreading dans la phase de recuperation des donnees
appliquer du multithreading dans la phase de reproduction
appliquer du multithreading dans la phase d'enregistrement

#### urgent :

#### TODO
ajouter des fonctionnalites pour mettre en pause  

#### trucs a finir si j'en ai le courage

continuer la classe TestAleatoire  
faire un diagramme de classe propre plutot que le plantUML auto genere  
tenir a jour la doc  

#### taches impossibles (ou presque)
enregistrer l'expression lambda dans un fichier et la recreer a partir de ce fichier  
