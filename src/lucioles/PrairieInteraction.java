package lucioles;


//import round de math
import java.lang.Math;
//import de la fonction creerPopulation de Prairie.java
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
	public static final double APPORT = 0.1;
	public static final int RAYON = 6;




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

	public static void  incrementeLuciole(double[][] copiePopulation, int[][] voisinage, double[] luciole, int numLuciole) {
		double increment = 0;
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
	
	public static void simulationPrairieGIF(int nbpas, double[][] population, int[][] prairie){
		String[] fichierGIF = new String[nbpas];
		int[][] voisines = voisinage(prairie);
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
				if(population[j][ENERGIE] >= 100) {
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
		/* ATTENTION 
			il faut appeler une seuel fonction à la fois 
			cad, soit simulationPrairieGIF soit simulationPrairie 
			mais pas les deux en même temps
		*/

		/* 
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


		double[][] population =creerPopulation(2000);
		int[][] prairie = prairieLucioles(90, 90, population);
		// simulationPrairieGIF(300, population, prairie);

		// double[][] population =creerPopulation(4000);
		// int[][] prairie = prairieLucioles(100, 100, population);
		// simulationPrairie(population, prairie,10);
		simulationPrairieGIF(200, population, prairie);

	}

}
