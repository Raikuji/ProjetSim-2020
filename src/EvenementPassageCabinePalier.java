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

		long dureeChangement = this.date + this.tempsPourBougerLaCabineDUnEtage;

		if (immeuble.cabine.intention() == '^') {
			Etage e = immeuble.étage(étage.numéro() + 1);
			immeuble.cabine.étage = this.étage;
			this.étage = e;
		} if (immeuble.cabine.intention() == 'v') {
			Etage e = immeuble.étage(étage.numéro() - 1);
			this.étage = e;
			immeuble.cabine.étage = e;
		}
		this.date = dureeChangement;
		echeancier.ajouter(this);
	}
}