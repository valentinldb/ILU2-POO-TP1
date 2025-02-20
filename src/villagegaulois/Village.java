package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	
	public static class Marche {
		
		private Etal[] etals;
		
		public Marche(int nbEtals) {
			etals = new Etal[nbEtals];
			for (int i = 0; i<nbEtals; i++) {
				etals[i] = new Etal();
			}
		}
		
		private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}
		
		private int trouverEtalLibre() {
			int i;
			boolean etalTrouve = false;
			for (i = 0; (i<etals.length && !etalTrouve);i++) {
				if(!etals[i].isEtalOccupe()) {
					etalTrouve = true;
				}
			}
			if(etalTrouve) {
				return i-1;
			}
			else {
				return -1;
			}
		}
		
		private Etal[] trouverEtals(String produit) {
			int nbEtalsProduits = 0;
			for(int i = 0; i<etals.length ; i++) {
				if(etals[i].isEtalOccupe() && etals[i].contientProduit(produit)) {
					nbEtalsProduits++;
				}
			}
			
			Etal[] etalsTrouves = new Etal[nbEtalsProduits];
			int indiceEtalsTrouves = 0;
			
			for (int i= 0; i<etals.length; i++) {
				if(etals[i].isEtalOccupe() && etals[i].contientProduit(produit)) {
					etalsTrouves[indiceEtalsTrouves] = etals[i];
					indiceEtalsTrouves++;
				}
			}
			
			return etalsTrouves;
			
		}
		
		private Etal trouverVendeur(Gaulois gaulois) {
			boolean vendeurTrouve = false;
			Etal etalVendeur = null;
			for (int i = 0; (i<etals.length && !vendeurTrouve);i++) {
				if (etals[i].getVendeur() == gaulois) {
					etalVendeur = etals[i];
					vendeurTrouve = true;
				}
			}
			if(vendeurTrouve) {
				return etalVendeur;
			}
			else {
				return null;
			}
		}
		
		private String afficherMarcher() {
			int nbLibre = 0;
			String ensembleEtals = "";
			for (int i = 0; i<etals.length; i++) {
				if(!etals[i].isEtalOccupe()) {
					nbLibre++;
				}
				else {
					ensembleEtals += etals[i].afficherEtal();
				}
			}
			
			ensembleEtals += "Il reste " + nbLibre + " étals non utilisés dans le marché.\n";
			
			return ensembleEtals;
		}
		
		
		
	}
	
	
	
	
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbEtals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		marche = new Marche(nbEtals);
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
		StringBuilder chaine = new StringBuilder();
		chaine.append(vendeur.getNom() + " cherche un endroit pour vendre " + nbProduit + " " + produit + ".\n");
		int indiceEtal = marche.trouverEtalLibre();
		marche.utiliserEtal(indiceEtal, vendeur, produit, nbProduit);
		chaine.append("Le vendeur " + vendeur.getNom() + " vend des " + produit + " à l'étal n°" + (indiceEtal+1) + ".\n");
		return chaine.toString();
		
	}
	
	public String rechercherVendeursProduit(String produit) {
		StringBuilder chaine = new StringBuilder();
		Etal[] etals = marche.trouverEtals(produit);
		switch(etals.length) {
		case 0: 
			chaine.append("Il n'y a pas de vendeur qui propose des "+ produit +" au marché.\n");
			break;
		case 1:
			chaine.append("Seul le vendeur " + etals[0].getVendeur().getNom() + " propose des " + produit + " au marché\n");
			break;
		default:
			chaine.append("Les vendeurs qui propsent des " + produit + " sont :\n");
			for(int i = 0; i<etals.length ; i++) {
				chaine.append("- " + etals[i].getVendeur().getNom()+ "\n");
			}
			break;
		}	
		return chaine.toString();
	}
	
	public Etal rechercherEtal(Gaulois vendeur) {
		return marche.trouverVendeur(vendeur);		
	}
	
	public String partirVendeur(Gaulois vendeur) {
		StringBuilder chaine = new StringBuilder();
		Etal etal = rechercherEtal(vendeur);
		
		chaine.append(etal.libererEtal());
		return chaine.toString();
	}
	
}