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
	public static void main(double[] args) {
		// TODO À compléter

		// System.out.println(creerLuciole()[0]);
		

		afficheLuciole(creerLuciole());
		System.out.println();
		affichePopulation(creerPopulation(5));

	}

}
