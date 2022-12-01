package lucioles;



/* 2 QUESTIONS
 * on doit importer ou recopier ces fonctions
 * comment faire une synchronisation
 */


import static lucioles.Prairie.creerPopulation;
import static lucioles.Prairie.prairieLucioles;

//tout immporter de la classe Prarie de  Prairie.java
// import lucioles.Prairie.*;

import outils.*;

// Étape 4: Simulation d'une prairie avec interaction entre les lucioles

public class PrairieInteraction {

	// Seuil au delà duquel une luciole émet un flash.
	public static final double SEUIL = 100.0;

	// Indices nommés pour accéder aux données d'une luciole
	public static final int ENERGIE = 0;
	public static final int DELTA = 1;

	// Définition de l'apport d'énergie par flash, et du rayon de voisinage
	/* 
	 * après de multiples essais et recherches, j'en suis venu à la conclusion qu'il était plus efficace 
	 * pour une synchronisation d'avoir un rayon très large et un apport léger.
	 * En effet, si l'apport est trop grand, dans le cas ou le rayon est trop grand aussi,
	 * alors toutes les lucioles deviennent vertes et le restent tout le temps.
	 * Dans les autres cas, on obtient des résultats plus ou moins satisfaisants. 
	 */
	public static final double APPORT = 0.4;
	public static final int RAYON = 10;


//------------------- AFFICHAGE ------------------

	public static void afficheMatrice(int[][] matrice) {
		System.out.println("Matrice de " + matrice.length + " lignes et "
				+ matrice[0].length + " colonnes");
		for(int i = 0; i < matrice.length; i++) {
			for(int j = 0; j < matrice[i].length; j++) {
				System.out.print(i+  "-"+ j + " = " + matrice[i][j] + " ,");
			}
			System.out.println();
		}
		System.out.println("Fin de la matrice");
	}

	public static void afficheTab(int[] tab) {
		for(int i = 0; i < tab.length; i++) {
			System.out.print(tab[i] + " ");
		}
		System.out.println();
	}
	
//-------------------------------------------------

	/*
	 * @param prairie : tableau de tableau contenant les indices des lucioles et -1 si la case est vide
	 * @return entier correspondant au nombre de lucioles dans la prairie
	  */
	public static int nbLucioles(int[][] prairie) {
	
		int nbLucioles = 0;
		for(int i = 0; i < prairie.length; i++) {
			for(int j = 0; j < prairie[i].length; j++) {
				if(prairie[i][j] != -1) {
					nbLucioles++;
				}
			}
		}
		return nbLucioles;
	}


	/* 

		voisines d'une luciole sont trouvées en fonction du RAYON autour de la luciole 
		Cette fonction trouve donc toutes les voisines dans la matrice dans le rayon de la luciole


	 * @param prairie : tableau de tableau contenant les indices des lucioles et -1 si la case est vide
	 * @param ligne : entier correspondant à la ligne de la luciole
	 * @param colonne : entier correspondant à la colonne de la luciole
	 * @param nbvoisins : entier correspondant au nombre de voisins de la luciole
	 * @return tableau d'entiers contenant les indices des voisins de la luciole
	 */
	public static int[] voisinageCase(int[][] prairie, int ligne, int colonne, int nbvoisins) {
		int[] voisins = new int[nbvoisins];
			int indice_voisins = 0;
			int i = ligne - RAYON;
			int j = colonne - RAYON;
			int k = ligne + RAYON;
			int l = colonne + RAYON;
			if(i < 0) {
				i = 0;
			}
			if(j < 0) {
				j = 0;
			}
			if(k > prairie.length - 1) {
				k = prairie.length - 1;
			}
			if(l > prairie[i].length - 1) {
				l = prairie[i].length - 1;
			}
			for(int m = i; m <= k; m++) {
				for(int n = j; n <= l; n++) {
					if(prairie[m][n] != -1) {
						//si ce n'est pas lui meme 
						if(prairie[ligne][colonne] != prairie[m][n]) {
							voisins[indice_voisins] = prairie[m][n];
							indice_voisins++;
						}
					}
				}
			}
		return voisins;
	}

	/* 
		C'est la même fonction que VoisinCase mais elle renvoie le nombre de voisins qui est utile plus tard.

	 * @param prairie : tableau de tableau contenant les indices des lucioles et -1 si la case est vide
	 * @param ligne : entier correspondant à la ligne de la luciole
	 * @param colonne : entier correspondant à la colonne de la luciole
	 * @return entier correspondant au nombre de voisins de la luciole
	 */
	public static int nbVoisins(int[][] prairie, int ligne, int colonne) {
		int nbVoisins = 0;
		int i = ligne - RAYON;
		int j = colonne - RAYON;
		int k = ligne + RAYON;
		int l = colonne + RAYON;
		if(i < 0) {
			i = 0;
		}
		if(j < 0) {
			j = 0;
		}
		if(k > prairie.length - 1) {
			k = prairie.length - 1;
		}
		if(l > prairie[i].length - 1) {
			l = prairie[i].length - 1;
		}
		for(int m = i; m <= k; m++) {
			for(int n = j; n <= l; n++) {
				if(prairie[m][n] != -1) {
					//si ce n'est pas lui meme
					if(m!= ligne || n != colonne) {
						nbVoisins++;
					}
				}
			}
		}
		return nbVoisins ;

	}


	/* 
	 * @param prairie : tableau de tableau contenant les indices des lucioles et -1 si la case est vide
	 * 
	 * @return tableau de tableaux d'entiers contenant les indices des voisins de la luciole i
	 * ex: à l'indice 0 de voisins, on a la liste des voisins de la luciole 0 (dans la population)
	*/
	public static int[][] voisinage(int[][] prairie){
		int[][] voisins = new int[nbLucioles(prairie)][];
		for(int i = 0; i < prairie.length; i++) {
			for(int j = 0; j < prairie[i].length; j++) {
				if(prairie[i][j] != -1) {
					voisins[prairie[i][j]] = voisinageCase(prairie, i, j, nbVoisins(prairie, i, j));
				}
			}
		}
		return voisins;
	}

	/* 

		Cette fonction increment la luciole spécifiquement en fonction de ses voisines et leur niveau d'énergie.
		On joue avec copiePopulation pour ne pas modifier le pas observé directement.

		C'est le coeur du programme.

	 * @param copiePopulation : tableau de tableaux contenant toutes les lucioles
	 * @param voisinage : le tableau de voisins pour chaque luciole
	 * 
	 * @param luciole : une liste correspondant à la luciole elle même, 
	 * le premier élément étant son energie, et le deuxieme son delta,
	 * c'est à dire son increment à chaque pas de simulation
	 * 
	 * @param numeroLuciole : entier correspondant à l'indice de la luciole dans la population
	 * 
	 * @return rien.
	 */
	public static void  incrementeLuciole(double[][] copiePopulation, int[][] voisinage, double[] luciole, int numLuciole) {
		double increment = 0;
		// l'ordre des actions suivantes comptent, il y a un disfonctionnement si on fait tout dans le mauvais ordre.
		for(int i = 0; i < voisinage[numLuciole].length; i++) {
			if(copiePopulation[voisinage[numLuciole][i]][ENERGIE] >= SEUIL) {
				increment += APPORT;
			}	
		}
		luciole[ENERGIE] += increment;
		if(copiePopulation[numLuciole][ENERGIE] < SEUIL){
			luciole[ENERGIE] += luciole[DELTA];
		}
	}
	

	/* 

		AFFICHAGE GIF DE LA SIMULATION

		C'est cette fonction qui gère les pas t et t+1 avec la copiePopulation.
		C'est la notion de modification au pas différent qui m'a comlexifie la tâche.
		
	 * @param nbpas : entier correspondant au nombre de pas de simulation
	 * @param population : tableau de tableaux contenant toutes les lucioles (chaque luciole est un tableau de double)
	 * @param prairie : tableau de tableaux contenant les indices des lucioles et -1 si la case est vide
	 * 
	 * @return rien.
	 */
	public static void simulationPrairieGIF(int nbpas, double[][] population, int[][] prairie){
		String[] fichierGIF = new String[nbpas];
		int[][] voisines = voisinage(prairie);
		//première copie hors du for car sinon copiePopulation n'existe pas au premier tour de boucle
		double[][] copiePopulation = new double[population.length][population[0].length];
		for(int l = 0; l < population.length; l++) {
			for(int k = 0; k < population[l].length; k++) {
				copiePopulation[l][k] = population[l][k];
			}
		}
		for(int i = 0; i<nbpas; i++){
			fichierGIF[i] = "img/prairieInteraction" + i + ".bmp";
			BitMap.bmpEcritureFichier("img/prairieInteraction"+i+".bmp", prairie, population, SEUIL);
			for(int j = 0; j < population.length; j++){				
				if(population[j][ENERGIE] >= 100) {
					population[j][ENERGIE] =0;
				}
				incrementeLuciole(copiePopulation, voisines, population[j], j);
			}

			//AFFICHAGE DEBUG
			// tableau de la figure 2, la clé pour réussir le projet

			// for(int p = 0; p < population.length; p++) {
			// 	System.out.print("Ene " + p + " : " + population[p][ENERGIE] + "  ---  ");
			// }
			// System.out.println();
			
			for(int l = 0; l < population.length; l++) {
				for(int k = 0; k < population[l].length; k++) {
					copiePopulation[l][k] = population[l][k];
				}
			}
		}
		GifCreator.construitGIF("simu/prairie_lucioles_interaction.gif", fichierGIF);
	}



	/* 
	 * @param prairie : tableau de tableaux contenant les indices des lucioles et -1 si la case est vide
	 * @param population : tableau de tableaux contenant toutes les lucioles (chaque luciole est un tableau de double)
	 * 
	 * @return rien.
	 */
	public static void affichePrairie(int[][] prairie, double[][] population) {
		//affiche les bordures
		for(int i = 0; i < prairie[0].length + 2; i++) {
			System.out.print("#");
		}
		System.out.println();
		//affiche les lignes
		for(int i = 0; i < prairie.length; i++) {
			System.out.print("#");
			//affiche les colonnes
			for(int j = 0; j < prairie[i].length; j++) {
				//si la case est vide
				if(prairie[i][j] == -1) {
					System.out.print(" ");
				}
				//si la case est occupée
				else {
					//si la luciole n'émet pas de flash
					if(population[(int)prairie[i][j]][ENERGIE] < SEUIL) {
						System.out.print(".");
					}
					//si la luciole émet un flash
					else {
						System.out.print("*");
						//on remet l'energie a 0
						population[(int)prairie[i][j]][ENERGIE] = 0;
					}
				}
			}
			System.out.println("#");
		}
		//affiche les bordures
		for(int i = 0; i < prairie[0].length + 2; i++) {
			System.out.print("#");
		}
		System.out.println();
	}


	/* 

	AFFICHAGE CONSOLE DE LA SIMULATION

	appelle affichePrairie() pour afficher la prairie à chaque pas de simulation
	appelle incrementeLuciole() pour incrémenter l'énergie des lucioles
	* @param population : tableau de tableaux contenant toutes les lucioles (chaque luciole est un tableau de double)
	 * @param prairie : tableau de tableaux contenant les indices des lucioles et -1 si la case est vide
	 * @param nbpas : entier correspondant au nombre de pas de simulation
	 * 
	 * @return rien.
	 */
	public static void simulationPrairie(double[][] population, int[][] prairie, int nbpas) {
		int[][] voisines = voisinage(prairie);
		double[][] copiePopulation = new double[population.length][population[0].length];
		for(int l = 0; l < population.length; l++) {
			for(int k = 0; k < population[l].length; k++) {
				copiePopulation[l][k] = population[l][k];
			}
		}
		for(int i = 0; i<nbpas; i++){
			for(int j = 0; j < population.length; j++){				
				if(population[j][ENERGIE] >= SEUIL) {
					population[j][ENERGIE] =0;
				}
				incrementeLuciole(copiePopulation, voisines, population[j], j);
			}
			
			for(int l = 0; l < population.length; l++) {
				for(int k = 0; k < population[l].length; k++) {
					copiePopulation[l][k] = population[l][k];
				}
			}
			affichePrairie(prairie, population);
		}
	}
	public static void main(String[] args) {
		

		/* 
		recréer la figure 2 en dur a été la clé pour comprendre le fonctionnement de la simulation
		et incrementeLuciole(). J'ai pu recréer le tableau de la figure 2 et comprendre les soucis de ma fonction
		//FIGURE 2 POUR DEBUG

		
		double[][] populationExemple = {{96, 1},{98, 1},{98, 1},{97, 1},{97, 1},{96, 1},{96, 1},{12, 1},{12, 1},{5, 1}};
		int[][] prairieExemple = {{-1,-1,-1,-1,-1,-1,-1,-1},
								{-1,-1,-1,-1,-1,-1,-1,0},	
								{-1,-1,-1,1,-1,2,-1,-1},
								{-1,-1,-1,3,-1,-1,-1,-1},
								{-1,-1,-1,-1,4,-1,-1,-1},
								{-1,-1,-1,-1,5,-1,6,-1},
								{-1,-1,7,8,-1,-1,-1,-1},
								{-1,-1,-1,-1,-1,9,-1,-1},};

		 */

		/* pour permettre de visualiser les résulats, j'ai crée un petite simulation,
		 * Les paramètres initiaux pour faire une synchronisation étaient :
		 * nbLucioles = 6000
		 * nbLigne = 100 nbColonne = 100
		 */
		double[][] population =creerPopulation(20);
		int[][] prairie = prairieLucioles(10,10, population);
		

		/* 
		création de copies pour avoir le même résulat  en console et en GIF
		*/
		double[][] copiePopulation = new double[population.length][population[0].length];
		for(int l = 0; l < population.length; l++) {
			for(int k = 0; k < population[l].length; k++) {
				copiePopulation[l][k] = population[l][k];
			}
		}
		int[][] copiePrairie = new int[prairie.length][prairie[0].length];
		for(int l = 0; l < prairie.length; l++) {
			for(int k = 0; k < prairie[l].length; k++) {
				copiePrairie[l][k] = prairie[l][k];
			}
		}

		//paramètres initiaux pour le gif : environ 300 pas 
		simulationPrairie(copiePopulation, copiePrairie,30);
		simulationPrairieGIF(30, population, prairie);
	}
}

/* 

	Toutes mes simulations présentes dans SimuLucioles ont été crées dans le but d'avoir une synchronisation le plus tot possible.
	Ce n'est qu'apres de nombreux essais que j'ai pu obtenir un résultat satisfaisant (simulation 5).

 * Exemple de prairie_luciole_interaction8 :
 * 
 * j'ai choisi de modifier la fonction creerLuciole das Prairie.java pour avoir une synchoronisation des lucioles
 * directement au lancement de la simulation.
 * Cela a fonctionné, mais ne se stabilise pas.
 * 
 * J'ai choisi comme paramêtre de creerPopulation un grand nombre de lucioles pour que chacune ait plusieurs voisines.
 * dans ce cas la, la synchronisation est plus simple à atteindre. 
 * 
 * Pour obtenir ce résultat la, j'ai regardé plusieurs fois la vidéo teaser et analysé son code source, surtout les paramètres de rayon,
 * le nombre de lucioles à la base et la taille de sa simulation.
 * 
 */
