public class EvenementOuverturePorteCabine extends Evenement {
    /* OPC: Ouverture Porte Cabine
       L'instant précis ou la porte termine de s'ouvrir.
    */

    public EvenementOuverturePorteCabine(long d) {
	    super(d);
    }

    public void afficheDetails(StringBuilder buffer, Immeuble immeuble) {
	    buffer.append("OPC");
    }

    public void traiter(Immeuble immeuble, Echeancier echeancier) {
        Cabine cabine = immeuble.cabine;
        Etage étage = cabine.étage;

        cabine.porteOuverte = true;

        cabine.faireDescendrePassagers(immeuble, date);

        if(cabine.nbPassager() == 0) {
            cabine.changerIntention('-');
        }
        assert cabine.porteOuverte;

        if(cabine.étage.aDesPassagers()) {
            Passager p = cabine.étage.getPassager().get(0);
            immeuble.cabine.faireMonterPassager(p);
            immeuble.cabine.changerIntention(p.sens());
            echeancier.ajouter(new EvenementFermeturePorteCabine(date + Global.tempsPourEntrerOuSortirDeLaCabine));
            echeancier.decalerFPC();
            echeancier.supprimePAP(p);
        }
    }

}
