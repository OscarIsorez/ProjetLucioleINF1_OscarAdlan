package lucioles;


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
	public static final double APPORT = 1.0;
	public static final int RAYON = 2;




	public static void afficheMatrice(int[][] matrice) {

		System.out.println("Matrice de " + matrice.length + " lignes et "
				+ matrice[0].length + " colonnes");
		for(int i = 0; i < matrice.length; i++) {
			for(int j = 0; j < matrice[i].length; j++) {
				System.out.print(i+  "-"+ j + " = " + matrice[i][j] + " ,");
				//si ce n'est pas -1 on affiche le nbr de voisins
				// if(matrice[i][j] != -1) {
					// System.out.println("nbVoisins de "+ i +" et " + j +" = " + nbVoisins(matrice, j, i));
				// }


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
		if(nbvoisins == 0) {
			//remplir de -1
			for(int i = 0; i < voisins.length; i++) {
				voisins[i] = -1;
			}
		}
		else{
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
					nbVoisins++;
				}
			}
		}
		return nbVoisins ;

	}

	public static int[][] voisinage(int[][] prairie){
		//renvoie le tableau des voisins de chaque luciole dans prairie
		//donc s'ily a 4 lucioles dans prairie, le tableau renvoyé aura 4 lignes 
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

	public static void incrementeLuciole(double[][] population, int[][] voisinage, double[] luciole, int numLuciole) {
		double[][] copiePopulation = new double[population.length][population[0].length];
		for(int i = 0; i < population.length; i++) {
			for(int j = 0; j < population[i].length; j++) {
				copiePopulation[i][j] = population[i][j];

			}
		}

		//peut etre la le probleme
		for(int i = 0; i < voisinage[numLuciole].length; i++) {
			copiePopulation[voisinage[numLuciole][i]][0] += APPORT;
		}
		//peut etre la le probleme
		//on remet a jour le tableau population
		for(int i = 0; i < population.length; i++) {
			for(int j = 0; j < population[i].length; j++) {
				population[i][j] = copiePopulation[i][j];
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

				//peut etre la le probleme 
				incrementeLuciole( population, voisinage(prairie), population[j], j);
			}
		}
		GifCreator.construitGIF("simu/prairie_lucioles_interaction.gif", fichierGIF);;
	}

	public static void main(String[] args) {
		//on utilise les fonctions du fichier Prairie.java 

		//on crée une prairie de 10 lignes et 10 colonnes
		
		double[][] population = creerPopulation(100);
		int[][] prairie = prairieLucioles(50, 50, population);

		//on affiche la prairie
		affichePrairie(prairie, population);
		System.out.println();

		
		//affiche le nombre de voisins et le voisinage de la luciole en position (0,0)
		// System.out.println(nbVoisins(prairie, 0, 0));
		// afficheTab(voisinageCase(prairie, 0,0,nbVoisins(prairie, 0,0)));
		System.out.println();

		//affiche le tableau des voisins de chaque luciole	
		afficheMatrice(voisinage(prairie));
		System.out.println();

		simulationPrairieGIF(80, population, prairie);
		

	}

}
