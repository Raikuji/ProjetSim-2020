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

		if (cabine.étage.numéro() == étage.numéro()) {
			echeancier.ajouter(new EvenementOuverturePorteCabine(this.date + Global.tempsPourOuvrirOuFermerLesPortes));
		} else {
			if (immeuble.cabine.intention() == '^') {
				Etage e = immeuble.étage(étage.numéro() + 1);
				immeuble.cabine.étage = this.étage;
				this.étage = e;
			} if (immeuble.cabine.intention() == 'v') {
				Etage e = immeuble.étage(étage.numéro() - 1);
				immeuble.cabine.étage = this.étage;
				this.étage = e;
			}
			if(immeuble.cabine.passagersVeulentDescendre()) {
				echeancier.ajouter(new EvenementOuverturePorteCabine(this.date + tempsPourOuvrirOuFermerLesPortes));
			} else {
				this.date = this.date + Global.tempsPourBougerLaCabineDUnEtage;
				echeancier.ajouter(this);
			}
		}


	}
}