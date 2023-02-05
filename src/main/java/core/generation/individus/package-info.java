/**
 * Ce package permet de gerer les individus.
 * 
 * Ce package contient les sous-packages mutations, 
 * qui gere les mutations, et le package cerveau, 
 * qui gere le cerveau des individus.
 * 
 * Ce package contient la classe abstraite Individu, 
 * qui represente un individu. Les autres classes sont 
 * des sous classes de Individu : 
 *  - La classe Original permet de generer un individu 
 *  selon notre volonte. utile en debut de simulation.
 *  - La classe Sauvegarde regenere des individus 
 *  simplifies a partir de sauvegardes json.
 *  - la classe CloneParfait represente un clone sans 
 *  mutations par rapport a son predecesseur.
 *  - la classe CloneMute represente un clone dont le 
 *  cerveau a change par rapport a son predecesseur.
 *  - la classe EnfantSexe represente un individu issu 
 *  de deux parents et melange leurs carracteristiques.
 */
package core.generation.individus;