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
        if (niveauEnergie < SEUIL)
        {
            niveauEnergie +=deltaEnergie;
            return niveauEnergie;
        }
        else
        {
            return niveauEnergie;
        }
    }



    public static void simuleLucioleNbPas(int nbPas)
    {
        double lucioleEnergie =  RandomGen.rGen.nextDouble() * 100;
        for(int i = 0; i < nbPas; i++)
        {
            lucioleEnergie = incrementeLuciole(lucioleEnergie, RandomGen.rGen.nextDouble() * 1);
            afficheLuciole(lucioleEnergie, true);
            //on remet à O quand ça atteint 100
            if(lucioleEnergie > 100) {
                lucioleEnergie = 0;
            } 
        }
    }

    public static void simuleLucioleNbFlashs(){
        double lucioleEnergie =  RandomGen.rGen.nextDouble() * 100;
        int nbFlashs = 0;
        while(nbFlashs < 3) {
            lucioleEnergie = incrementeLuciole(lucioleEnergie, RandomGen.rGen.nextDouble() * 1);
            afficheLuciole(lucioleEnergie, true);
            if(lucioleEnergie > 100) {
                lucioleEnergie = 0;
                nbFlashs++;
            } 
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
        System.out.println("---------------afficheLuciole()-------------");
        afficheLuciole(incrementeLuciole(lucioleEnergie, lucioleDeltaEnergie), true);
        System.out.println("---------------simuleLucioleNbPas()-------------");
        simuleLucioleNbPas(10);
        System.out.println("--------------simuleLucioleNbFlashs()--------------");
        simuleLucioleNbFlashs();
        
    }
    
}
