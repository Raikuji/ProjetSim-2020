public class EvenementPassageCabinePalier extends Evenement {
    /* PCP: Passage Cabine Palier
       L'instant précis où la cabine passe juste en face d'un étage précis.
       Vous pouvez modifier cette classe comme vous voulez (ajouter/modifier des méthodes etc.).
    */
    
    private Etage étage;
    
    public EvenementPassageCabinePalier(long d, Etage e) {
		super(d);
		étage = e;
    }
    
    public void afficheDetails(StringBuilder buffer, Immeuble immeuble) {
		buffer.append("PCP ");
		buffer.append(étage.numéro());
    }

    public void traiter(Immeuble immeuble, Echeancier echeancier) {
		Cabine cabine = immeuble.cabine;
		assert ! cabine.porteOuverte;
		assert étage.numéro() != cabine.étage.numéro();
		assert cabine.intention() != '-';
		boolean ouvrePorte = false;

		if (immeuble.cabine.intention() == '^') {
			if (immeuble.étage(cabine.étage.numéro() + 1).aDesPassagers()) {
				echeancier.ajouter(new EvenementOuverturePorteCabine(this.date + Global.tempsPourOuvrirOuFermerLesPortes));
				//Etage e = immeuble.étage(étage.numéro() + 1);
				immeuble.cabine.étage = this.étage;
				ouvrePorte = true;
				//this.étage = e;
			} else {
				Etage e;
				if(étage.numéro() + 1 <= 7) {
					e = immeuble.étage(étage.numéro() + 1);
				} else {
					e = immeuble.étage(étage.numéro());
				}
				immeuble.cabine.étage = this.étage;
				this.étage = e;
			}
		} else if (immeuble.cabine.intention() == 'v') {
			if (immeuble.étage(cabine.étage.numéro() - 1).aDesPassagers()) {
				echeancier.ajouter(new EvenementOuverturePorteCabine(this.date + Global.tempsPourOuvrirOuFermerLesPortes));
				//Etage e = immeuble.étage(étage.numéro() - 1);
				immeuble.cabine.étage = this.étage;
				ouvrePorte = true;
				//this.étage = e;
			} else {
				Etage e;
				 if(étage.numéro() - 1 >= -1) {
					e = immeuble.étage(étage.numéro() - 1);
				} else {
					e = immeuble.étage(étage.numéro());
				}
				immeuble.cabine.étage = this.étage;
				this.étage = e;
			}
		}
		if(immeuble.cabine.passagersVeulentDescendre() && !ouvrePorte) {
			echeancier.ajouter(new EvenementOuverturePorteCabine(this.date + tempsPourOuvrirOuFermerLesPortes));
		} else if(!ouvrePorte) {
			this.date = this.date + Global.tempsPourBougerLaCabineDUnEtage;
			echeancier.ajouter(this);
		}
	}
}