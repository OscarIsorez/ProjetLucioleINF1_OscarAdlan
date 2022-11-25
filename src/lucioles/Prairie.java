package lucioles;

import outils.*;

// Étapes 2 et 3 : Définition de prairies, et simulation sans interaction

public class Prairie {

	// Seuil au delà duquel une luciole émet un flash.
	public static final double SEUIL = 100.0;

	// Indices nommés pour accéder aux données d'une luciole
	public static final int ENERGIE = 0;
	public static final int DELTA = 1;


	// Affiche une luciole
	public static void afficheLuciole(double[] luciole){

		for(int i = 0; i < luciole.length; i++) {
			System.out.print(luciole[i] + " ");
		}
	
	}
	//affiche population
	public static void affichePopulation(double[][] population) {
		for(int i = 0; i < population.length; i++) {
			afficheLuciole(population[i]);

			//test pour savoir si elles ont bien des adresses differentes
			// System.out.print(" " + population[i]);
			System.out.println();
		}
	}
	public static double[] creerLuciole(){
		double[] luciole = new double[2];
		luciole[ENERGIE] =  RandomGen.rGen.nextDouble() * 100;
		luciole[DELTA] =  RandomGen.rGen.nextDouble() * 1;
		return luciole;
	}

	public static double[] incrementeLuciole(double[] luciole){
		luciole[ENERGIE] += luciole[DELTA];

		return luciole;
	}

	public static double[][] creerPopulation(int nbLucioles){
		double[][] population = new double[nbLucioles][2];
		for(int i = 0; i < nbLucioles; i++) {
			population[i] = creerLuciole();
			
		}
		return population;
	} 

	public static int[][]prairieVide(int nbLigne, int nbColonne){
		int[][] prairie = new int[nbLigne][nbColonne];
		for(int i = 0; i < nbLigne; i++) {
			for(int j = 0; j < nbColonne; j++) {
				prairie[i][j] = -1;
			}
		}
		return prairie;

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

	public static void simulationPrairie(int nbLigne, int nbColonne, int nbLucioles, int nbPas) {
		double[][] population = creerPopulation(nbLucioles);
		int[][] prairie = prairieLucioles(nbLigne, nbColonne, population);
		for(int i = 0; i < nbPas; i++) {
			affichePrairie(prairie, population);
			System.out.println();
			for(int j = 0; j < population.length; j++) {
				population[j] = incrementeLuciole(population[j]);
				if (population[j][ENERGIE] > SEUIL) {
					population[j][ENERGIE] = 0;

				}
			}
		}
	}

	public static void simulationPrairieGIF(int nbpas, double[][] population, int[][] prairie){


		String[] fichierGIF = new String[nbpas];
		
		
		for(int i = 0; i<nbpas; i++){
			fichierGIF[i] = "img/prairie" + i + ".bmp";
			BitMap.bmpEcritureFichier("img/prairie"+i+".bmp", prairie, population, SEUIL);
			for(int j = 0; j < population.length; j++){
				// System.out.println(population[j][ENERGIE]);
				if (population[j][ENERGIE] >= SEUIL) {
					population[j][ENERGIE] = 0;
					
				}
				population[j] = incrementeLuciole(population[j]);
			}
		}


		GifCreator.construitGIF("simu/prairie_lucioles.gif", fichierGIF);;
		
		
	}


	public static void main(String[] args) {
		// System.out.println(creerLuciole()[0]);
		// afficheLuciole(creerLuciole());
		// System.out.println();
		// affichePopulation(creerPopulation(5));


		double[][] population = creerPopulation(100);
		int[][] prairie = prairieLucioles(50, 50, population);
		// affichePrairie(prairie, population);
		// System.out.println();
		// affichePrairie(prairieLucioles(5, 5, population), population);
		// simulationPrairie(30, 30, 100, 10);

		simulationPrairieGIF(30, population, prairie);



	}

}
