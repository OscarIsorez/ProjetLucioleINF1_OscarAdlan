package lucioles;


//import de la fonction creerPopulation de Prairie.java
import static lucioles.Prairie.creerPopulation;
import static lucioles.Prairie.prairieLucioles;
// import static lucioles.Prairie.affichePopulation;
// import static lucioles.Prairie.incrementeLuciole;
// import static lucioles.Prairie.creerLuciole;
// import static lucioles.Prairie.afficheLuciole;
// import static lucioles.Prairie.creerLuciole;
// import static lucioles.Prairie.affichePrairie;
// import static lucioles.Prairie.simulationPrairie;
// import static lucioles.Prairie.simulationPrairieGIF;

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
	public static final double APPORT = 15.0;
	public static final int RAYON = 2;



	//affichematrice
	public static void afficheMatrice(int[][] matrice) {
		for(int i = 0; i < matrice.length; i++) {
			for(int j = 0; j < matrice[i].length; j++) {
				System.out.print(matrice[i][j] + " ");
			}
			System.out.println();
		}
	}
	//affichetab
	public static void afficheTab(int[] tab) {
		for(int i = 0; i < tab.length; i++) {
			System.out.print(tab[i] + " ");
		}
		System.out.println();
	}
	/* 
	* r calculer le
	nombre de lucioles pr´esentes dans la prairie
	*/
	public static int nbLucioles(int[][] prairie) {
		int nbLucioles = 0;
		for(int i = 0; i < prairie.length; i++) {
			for(int j = 0; j < prairie[i].length; j++) {
				if(prairie[i][j] != 0) {
					nbLucioles++;
				}
			}
		}
		return nbLucioles;
	}

	/* 
	 * e, le voisinage d’une case donn´ee de la prairie en fonction du RAYON
	 */
	public static int[] voisinageCase(int[][] prairie, int i, int nbvoisins) {
		//renvoie une liste de tous le voisins de i dans la matrice prairie
		int[] voisins = new int[nbvoisins];
		int k = 0;
		for(int j = 0; j < prairie[i].length; j++) {
			if(prairie[i][j] != 0) {
				voisins[k] = prairie[i][j];
				k++;
			}
		}
		return voisins;
	}

	/* r d´eterminer combien de lucioles sont dans le voisinage d’une autre */
	public static int nbVoisins(int[][] prairie, int ligne, int colonne) {
		//retourne le nombre d'element different de -1 dans un rayon RAYON autour de prairie[i][j] dans la matrice prairie		

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
				if(prairie[m][n] != 0) {
					nbVoisins++;
				}
			}
		}
		return nbVoisins;
	}


	public static int[][] voisinage(int[][] prairie){
		//renvoie le tableau des voisins de chaque luciole dans prairie
		//donc s'ily a 4 lucioles dans prairie, le tableau renvoyé aura 4 lignes 
		int nbLucioles = nbLucioles(prairie);
		int[][] voisins = new int[nbLucioles][];
		int k = 0;
		for(int i = 0; i < prairie.length; i++) {
			for(int j = 0; j < prairie[i].length; j++) {
				if(prairie[i][j] != 0) {
					// voisins[k] = voisinageCase(prairie, i, nbVoisins(prairie, i));
					k++;
				}
			}
		}
		return voisins;
	}

	public static void afficheVoisinage(int[][] prairie) {
		int[][] voisinage = voisinage(prairie);
		for(int i = 0; i < voisinage.length; i++) {
			for(int j = 0; j < voisinage[i].length; j++) {
				System.out.print(voisinage[i][j] + " ");
			}
			System.out.println();
		}
	}


		/* Cr´eer une fonction incrementeLuciole qui prend en param`etre une
	population de lucioles, un voisinage, ainsi qu’une luciole et son num´ero, et qui modifie
	la luciole en fonction de son voisinage.
	Pour ne pas bouleverser de mani`ere impr´evisible le m´ecanisme d’interaction entre lucioles, vous aurez besoin d’utiliser une copie de la population de lucioles pour d´eterminer
	les niveaux d’´energie au pas suivant.
	*/
	public static void incrementeLuciole(int[][] prairie, int[][] voisinage, int ligne, int colonne) {
		int[][] copiePrairie = new int[prairie.length][prairie[0].length];
		for(int i = 0; i < prairie.length; i++) {
			for(int j = 0; j < prairie[i].length; j++) {
				copiePrairie[i][j] = prairie[i][j];
			}
		}
		int[][] copieVoisinage = new int[voisinage.length][voisinage[0].length];
		for(int i = 0; i < voisinage.length; i++) {
			for(int j = 0; j < voisinage[i].length; j++) {
				copieVoisinage[i][j] = voisinage[i][j];
			}
		}
		int nbVoisins = copieVoisinage[ligne][colonne];
		if(nbVoisins > 0) {
			int energie = copiePrairie[ligne][colonne];
			int delta = copiePrairie[ligne][colonne + 1];
			if(energie > SEUIL) {
				prairie[ligne][colonne] = (int) (energie - nbVoisins * APPORT);
				prairie[ligne][colonne + 1] = (int) (delta + nbVoisins * APPORT);
			} else {
				prairie[ligne][colonne] = energie + delta;
				prairie[ligne][colonne + 1] = 0;
			}
		}
	}

	public static void affichePrairie(int[][] prairie, double[][] population){
		for(int i = 0; i < prairie.length; i++) {
			for(int j = 0; j < prairie[i].length; j++) {
				System.out.print(prairie[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}



	
	public static void simulationPrairieGIF(int nbpas, double[][] population, int[][] prairie){


		String[] fichierGIF = new String[nbpas];
		
		
		for(int i = 0; i<nbpas; i++){
			fichierGIF[i] = "img/prairieInteraction" + i + ".bmp";
			BitMap.bmpEcritureFichier("img/prairieInteraction"+i+".bmp", prairie, population, SEUIL);
			for(int j = 0; j < population.length; j++){
				// System.out.println(population[j][ENERGIE]);
				if (population[j][ENERGIE] >= SEUIL) {
					population[j][ENERGIE] = 0;
					
				}
				incrementeLuciole( prairie, voisinage(prairie), prairie.length, prairie[0].length);
			}
		}


		GifCreator.construitGIF("simu/prairie_lucioles_interaction.gif", fichierGIF);;
		
		
	}

	public static void main(String[] args) {
		//on utilise les fonctions du fichier Prairie.java 

		//on crée une prairie de 10 lignes et 10 colonnes
		
		double[][] population = creerPopulation(4);
		int[][] prairie = prairieLucioles(4, 4, population);

		//on affiche la prairie
		affichePrairie(prairie, population);
		System.out.println();
		// afficheVoisinage(prairie);
		System.out.println(nbVoisins(prairie, 0,0));
		// afficheMatrice(voisinageCase(prairie, ENERGIE, DELTA));



		// simulationPrairieGIF(10, population, prairie);
		

	}

}
