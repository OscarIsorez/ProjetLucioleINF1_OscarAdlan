package lucioles;

import outils.*;

// Étapes 2 et 3 : Définition de prairies, et simulation sans interaction

public class Prairie {

	// Seuil au delà duquel une luciole émet un flash.
	public static final double SEUIL = 85.0;

	// Indices nommés pour accéder aux données d'une luciole
	public static final int ENERGIE = 0;
	public static final int DELTA = 1;


	//----------------- AFFICHAGE -----------------


	public static void printLigne(){
		System.out.println("--------------------------------------------------");
	}

	//affiche matrice
	public static void affichePop(double[][] matrice){
		for(int i = 0; i < matrice.length; i++){
			for(int j = 0; j < matrice[i].length; j++){
				System.out.print( matrice[i][j] + " ");
			}
			System.out.println();

		}
	}
	public static void affichePrairie(int[][] matrice){
		for(int i = 0; i < matrice.length; i++){
			for(int j = 0; j < matrice[i].length; j++){
				System.out.print( matrice[i][j] + " ");
			}
			System.out.println();

		}

	}
	public static void afficheLuciole(double[] luciole){
		for(int i = 0; i < luciole.length; i++) {
			System.out.print(luciole[i] + " ");
		}
	
	}
	
	public static void affichePopulation(double[][] population) {
		for(int i = 0; i < population.length; i++) {
			afficheLuciole(population[i]);
			System.out.println();
		}
	}

	/* 
	 * @return un tableau de double , premier element : energie, deuxieme element : delta (incrmement pour energie à chaque pas de la simulation)
	 *
	 */
	public static double[] creerLuciole(){
		double[] luciole = new double[2];

		//à changer pour avoir une synchronisation
		/* 
			J'ai rajouté 80 pour avoir un résultat plus rapide car lancer la simulation prend déjà au moins 2min 
		 * Cela permet aussi d'avvoir une sychronisation forcée car toutes les lucioles ont à peut près le même
		 * niveau d'énergie donc quand la première luciole emet un flash, il y a une grosse réaction en chaine.
		*/
		luciole[ENERGIE] =  80+ RandomGen.rGen.nextDouble() * 8;
		luciole[DELTA] =  RandomGen.rGen.nextDouble() * 1;
		return luciole;
	}

	/* 
	 * @param luciole : une luciole, cad un tableau de double avec energie et deltaEnergie en premier et deuxieme element
	 * 
	 * @return la luciole, dont l'energie a été incrmentée de son delta 
	 */
	public static double[] incrementeLuciole(double[] luciole){
		if(luciole[ENERGIE] < SEUIL){
			luciole[ENERGIE] += luciole[DELTA];
		}

		return luciole;
	}

	/* 
	 * @param nbluciole : le nombre de lucioles voulu pour notre simulation
	 * 
	 * @return une population de lucioles, un tableau contenant "nblucioles" lucioles,elles mêmes des tableaux
	 */
	public static double[][] creerPopulation(int nbLucioles){
		double[][] population = new double[nbLucioles][2];
		for(int i = 0; i < nbLucioles; i++) {
			population[i] = creerLuciole();
		}
		return population;
	} 

	/* 
	 * @param nbLigne : le nombre de lignes de la prairie
	 * @param nbColonne : le nombre de colonnes de la prairie
	 * 
	 * @return un tableau de tableau d'entier de longueur nbLigne et de largeur nbColonne remplit de -1 en attente de lucioles
	 */
	public static int[][]prairieVide(int nbLigne, int nbColonne){
		int[][] prairie = new int[nbLigne][nbColonne];
		for(int i = 0; i < nbLigne; i++) {
			for(int j = 0; j < nbColonne; j++) {
				prairie[i][j] = -1;
			}
		}
		return prairie;
	}

	/* 
	 * @param prairie : la prairie, un tableau de tableau d'entier
	 * @param population : la population de lucioles, un tableau de tableau de double
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
	 * @param nbLigne : le nombre de lignes de la prairie
	 * @param nbColonne : le nombre de colonnes de la prairie
	 * @param population : la population de lucioles, un tableau de tableau de double
	 * 
	 * @return la prairie remplie de lucioles, placées aléatoirement
	 */
	public static int[][] prairieLucioles(int nbLigne, int nbColonne, double[][] population) {
		int[][] prairie = prairieVide(nbLigne, nbColonne);
		int nbLucioles = population.length;
		int nbLuciolesPlacees = 0;
		while(nbLuciolesPlacees < nbLucioles) {
			int ligne = RandomGen.rGen.nextInt(nbLigne);
			int colonne = RandomGen.rGen.nextInt(nbColonne);
			if(prairie[ligne][colonne] == -1) {
				prairie[ligne][colonne] = nbLuciolesPlacees;
				nbLuciolesPlacees++;
			}
		}
		return prairie;
	}

	/* 
	 * @param nbPas : le nombre de pas de la simulation
	 * @param prairie : la prairie, un tableau de tableau d'entier
	 * @param population : la population de lucioles, un tableau de tableau de double
	 * 
	 * @return rien.
	 */
	public static void simulationPrairie( int nbPas,double[][] population, int[][] prairie) {
		for(int i = 0; i < nbPas; i++) {
			affichePrairie(prairie, population);
			System.out.println();
			for(int j = 0; j < population.length; j++) {
				if (population[j][ENERGIE] > 100) {
					population[j][ENERGIE] = 0;
				}
				population[j] = incrementeLuciole(population[j]);
			}
		}
	}


	/* 
	 * @param nbpas : le nombre de pas de la simulation
	 * @param population : la population de lucioles, contenant les lucioles placées dans la prairie
	 * @param prairie : la prairie, un tableau de tableau d'entier, contenant les indices des lucioles ou -1 si la case est vide
	 * 
	 * @return rien. 
	 */
	public static void simulationPrairieGIF(int nbpas, double[][] population, int[][] prairie){

		String[] fichierGIF = new String[nbpas];
		for(int i = 0; i<nbpas; i++){
			fichierGIF[i] = "img/prairie" + i + ".bmp";
			BitMap.bmpEcritureFichier("img/prairie"+i+".bmp", prairie, population, SEUIL);
			for(int j = 0; j < population.length; j++){
				if (population[j][ENERGIE] >= SEUIL) {
					population[j][ENERGIE] = 0;
				}
				population[j] = incrementeLuciole(population[j]);
			}
		}
		GifCreator.construitGIF("simu/prairie_lucioles.gif", fichierGIF);
	}


	public static void main(String[] args) {
	
		double[][] population = creerPopulation(100);
		int[][] prairie = prairieLucioles(50, 50, population);
	
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

		printLigne();
		simulationPrairie(10,population, prairie);
		printLigne();
		affichePop(population);
		printLigne();
		affichePrairie(prairie);

		//verifier le fonctionnement du gif en comparant avec la simulation dans le terminal
		//ou en verifiant image par image !
		simulationPrairieGIF(10, copiePopulation, copiePrairie);



	}

}
