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

        assert !cabine.porteOuverte;

        cabine.porteOuverte = true;

        int nbPassagerD = cabine.faireDescendrePassagers(immeuble, date);
        int nbPassagerM = 0;

        assert cabine.porteOuverte;

        if (cabine.étage.aDesPassagers()) {
            if (modeParfait) {
                for (int i = 0; i < passagers.size(); i++) {
                    if (passagers.get(i).sens() == cabine.intention() && cabine.nbPassager() <= nombreDePlacesDansLaCabine) {
                        echeancier.supprimePAP(passagers.get(i));
                        cabine.faireMonterPassager(passagers.get(i));
                        nbPassagerM++;
                    } else if (cabine.nbPassager() == 0) {
                        echeancier.supprimePAP(passagers.get(i));
                        cabine.changerIntention(passagers.get(i).sens());
                        cabine.faireMonterPassager(passagers.get(i));
                        nbPassagerM++;
                    }
                }
            } else {
                boolean memeSens = false;
                int nbPass = passagers.size();
                int nbAvant = cabine.nbPassager();
                for (int i = 0; i < nbPass; i++) {
                    if (cabine.nbPassager() <= nombreDePlacesDansLaCabine) {
                        if (passagers.get(0).sens() == cabine.intention()) {
                            memeSens = true;
                        }
                        echeancier.supprimePAP(passagers.get(0));
                        cabine.faireMonterPassager(passagers.get(0));
                        nbPassagerM++;
                    }
                }
                if (!memeSens && nbAvant == 0) {
                    cabine.changerIntention(cabine.getPassager()[0].sens());
                }
            }
        } else {

            if (cabine.nbPassager() == 0) {
                cabine.changerIntention('-');
            }

            if (immeuble.passagerEnDessous(cabine.étage)) {
                cabine.changerIntention('v');
            }

            if (immeuble.passagerAuDessus(cabine.étage)) {
                cabine.changerIntention('^');
            }
        }
        if (cabine.intention() != '-') {
            echeancier.ajouter(new EvenementFermeturePorteCabine(date + Global.tempsPourEntrerOuSortirDeLaCabine * (nbPassagerM + nbPassagerD)));
            echeancier.decalerFPC();
        }
    }

}
