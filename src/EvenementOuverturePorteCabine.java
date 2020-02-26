import java.util.ArrayList;

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
        ArrayList<Passager> passagers = cabine.étage.getPassagers();

        cabine.porteOuverte = true;

        int nbPassagerD = cabine.faireDescendrePassagers(immeuble, date);
        int nbPassagerM = 0;

        if(cabine.nbPassager() == 0) {
            cabine.changerIntention('-');
        }
        assert cabine.porteOuverte;

        if(cabine.étage.aDesPassagers()) {
            Passager p = cabine.étage.getPassagers().get(0);
            nbPassagerM ++;
            cabine.faireMonterPassager(p);
            cabine.changerIntention(p.sens());
            if(modeParfait) {
                for (Passager v: cabine.étage.getPassagers()) {
                    if(v.sens() == p.sens() && cabine.nbPassager() <= nombreDePlacesDansLaCabine) {
                        cabine.faireMonterPassager(v);
                        nbPassagerM ++;
                    }
                }
            } else {
                for(int i = 0; i <passagers.size(); i++) {
                    if(cabine.nbPassager() <= nombreDePlacesDansLaCabine) {
                        echeancier.supprimePAP(passagers.get(i));
                        cabine.faireMonterPassager(passagers.get(i));
                        nbPassagerM ++;
                    }
                }
            }
            echeancier.supprimePAP(p);
        }

        if(immeuble.passagerEnDessous(cabine.étage)) {
            cabine.changerIntention('v');
        }
        if(cabine.intention() != '-') {
            echeancier.ajouter(new EvenementFermeturePorteCabine(date + Global.tempsPourEntrerOuSortirDeLaCabine * (nbPassagerM + nbPassagerD)));
            echeancier.decalerFPC();
        }
    }

}
