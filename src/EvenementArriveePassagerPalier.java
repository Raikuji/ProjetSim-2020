import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class EvenementArriveePassagerPalier extends Evenement {
    /* APP: Arrivée Passager Palier
       L'instant précis ou un nouveau passager arrive sur un palier donné.
    */
    
    private Etage étage;

    public EvenementArriveePassagerPalier(long d, Etage edd) {
		super(d);
		étage = edd;
    }

    public void afficheDetails(StringBuilder buffer, Immeuble immeuble) {
		buffer.append("APP ");
		buffer.append(étage.numéro());
    }

    public void traiter(Immeuble immeuble, Echeancier echeancier) {
		assert étage != null;
		assert immeuble.étage(étage.numéro()) == étage;
		Passager p = new Passager(date, étage, immeuble);
		étage.ajouter(p);
		echeancier.ajouter(new EvenementArriveePassagerPalier(date + étage.arrivéeSuivante(), étage));
		if((étage == immeuble.cabine.étage) && immeuble.cabine.porteOuverte && (immeuble.cabine.nbPassager() <= nombreDePlacesDansLaCabine)) {
			if(p.sens() == immeuble.cabine.intention() || immeuble.cabine.intention() == '-') {
				immeuble.cabine.faireMonterPassager(p);
				immeuble.cabine.changerIntention(p.sens());
				if (!echeancier.hasFPC()) {
					echeancier.ajouter(new EvenementFermeturePorteCabine(date + Global.tempsPourEntrerOuSortirDeLaCabine));
				}
				echeancier.decalerFPC();
			}
		} else if (!immeuble.cabine.porteOuverte) {
			echeancier.ajouter((new EvenementPietonArrivePalier(this.date + Global.délaiDePatienceAvantSportif, p.étageDépart().numéro(), p)));
		} else if(immeuble.cabine.intention() == '-') {
			echeancier.ajouter((new EvenementPietonArrivePalier(this.date + Global.délaiDePatienceAvantSportif, p.étageDépart().numéro(), p)));
			if(étage.numéro() < immeuble.cabine.étage.numéro()) {
				immeuble.cabine.changerIntention('v');
			} else {
				immeuble.cabine.changerIntention('^');
			}
			if(!echeancier.hasFPC())
				echeancier.ajouter(new EvenementFermeturePorteCabine(date + Global.tempsPourOuvrirOuFermerLesPortes));
		} else {
			echeancier.ajouter((new EvenementPietonArrivePalier(this.date + Global.délaiDePatienceAvantSportif, p.étageDépart().numéro(), p)));
		}
    }
}