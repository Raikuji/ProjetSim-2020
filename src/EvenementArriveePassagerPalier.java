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
		if((étage == immeuble.cabine.étage) && immeuble.cabine.porteOuverte && (immeuble.cabine.nbPassager() <= nombreDePlacesDansLaCabine)) {
			immeuble.cabine.faireMonterPassager(p);
			echeancier.ajouter(new EvenementFermeturePorteCabine(date + 4));
			echeancier.decalerFPC();
		}
    }
}