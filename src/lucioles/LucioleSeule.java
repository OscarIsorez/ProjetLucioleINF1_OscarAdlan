package lucioles;


//petit test 
// Étape 1 : Simulation d'une seule luciole

public class LucioleSeule {


    // Seuil au delà duquel une luciole émet un flash.
    public static final double SEUIL = 100.0;

    
    public static String symboliseLuciole(double niveauEnergie)
    {
        if (niveauEnergie < SEUIL)
        {
            return ".";
        }
        else
        {
            return "*";
        }
    }

    public static void afficheLuciole(double niveauEnergie, boolean verbeux){
        System.out.print(symboliseLuciole(niveauEnergie));
        if(verbeux) {
            System.out.print(" (" + niveauEnergie + ")");
        }	
        System.out.println();


    }

    public static double incrementeLuciole(double niveauEnergie, double deltaEnergie)
    {
        niveauEnergie += deltaEnergie;
        return niveauEnergie;
    }




    /* 
    Cr´eer une fonction simuleLucioleNbPas qui initialise al´eatoirement une
luciole, et fait ´evoluer cette luciole un certain nombre de fois (fourni en param`etre), en
affichant la luciole `a chaque pas de temps, en mode verbeux. En particulier, v´erifier que
votre luciole ´emet un flash au bon moment, et que son niveau d’´energie revient bien `a 0
avant d’augmenter `a nouveau. Vous testerez cette fonction dans la fonction main.
 */
    public static void simuleLucioleNbPas(int nbPas)
    {
        double lucioleEnergie =  RandomGen.rGen.nextDouble() * 100;
        for(int i = 0; i < nbPas; i++)
        {
            afficheLuciole(lucioleEnergie, true);
            lucioleEnergie = incrementeLuciole(lucioleEnergie, RandomGen.rGen.nextDouble() * 100 - 50);
        }
    }




    public static void main(String[] args) {
        double lucioleEnergie =  RandomGen.rGen.nextDouble() * 100;
        double lucioleDeltaEnergie = RandomGen.rGen.nextDouble() * 1;


        //changement manuel de lucioleEnergie
        // lucioleEnergie =  100;
        // afficheLuciole(lucioleEnergie, true);
        
        // System.out.println("DeltaEnergie: " + lucioleDeltaEnergie);
        // afficheLuciole(lucioleEnergie, true);
        // afficheLuciole(incrementeLuciole(lucioleEnergie, lucioleDeltaEnergie), true);

        simuleLucioleNbPas(10);
        
    }
    
}
