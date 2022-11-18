package lucioles;


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
        return niveauEnergie + deltaEnergie;
    }
    public static void main(String[] args) {
        //aleatoire entre 0 et 100
        double lucioleEnergie =  RandomGen.rGen.nextDouble() * 100;
        //changement manuel de lucioleenergie
        // lucioleEnergie =  100;
        // afficheLuciole(lucioleEnergie, true);
        
        double lucioleDeltaEnergie = RandomGen.rGen.nextDouble() * 1;
        System.out.println("DeltaEnergie: " + lucioleDeltaEnergie);
        afficheLuciole(lucioleEnergie, true);
        afficheLuciole(incrementeLuciole(lucioleEnergie, lucioleDeltaEnergie), true);
        
    }
    
}
