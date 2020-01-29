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
			immeuble.cabine.faireMonterPassager(p);
			immeuble.cabine.changerIntention(p.sens());
			echeancier.ajouter(new EvenementFermeturePorteCabine(date + Global.tempsPourEntrerOuSortirDeLaCabine));
			echeancier.decalerFPC();
		}
    }
}