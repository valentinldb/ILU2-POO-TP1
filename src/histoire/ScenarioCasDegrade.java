package histoire;

import personnages.Gaulois;
import villagegaulois.Etal;
import villagegaulois.Village;
import villagegaulois.VillageSansChefException;

public class ScenarioCasDegrade {

	public static void main(String[] args) {
		Etal etal = new Etal();
		Gaulois asterix = new Gaulois("nom", 6);
		etal.occuperEtal(asterix, "chaussettes", 1);
		try {
			etal.acheterProduit(0, asterix);
		} catch(IllegalArgumentException e) {
			System.err.println(true);
		} catch(Exception e) {
			System.err.println(false);
		}
		System.out.println("Fin du test");
		
		
		Village village = new Village("village-test", 1, 1);
		
		village.ajouterHabitant(asterix);
		try {
			village.afficherVillageois();
		} catch (VillageSansChefException e) {
			System.err.println(true);
		}
	}
	
}
