public class EvenementPietonArrivePalier extends Evenement {
    /* PAP: Pieton Arrive Palier
       L'instant précis ou un passager qui à décidé de continuer à pieds arrive sur un palier donné.
       Classe à faire complètement par vos soins.
    */

    private int etageEnCours;
    private Passager p;

    public void afficheDetails(StringBuilder buffer, Immeuble immeuble) {
	    buffer.append("PAP ");
	    buffer.append(etageEnCours);
	    buffer.append(" #");
	    buffer.append(p.numPassager());
    }

    public void traiter(Immeuble immeuble, Echeancier echeancier) {
	    if(p.étageDépart().numéro() == etageEnCours && !immeuble.étage(etageEnCours).getPietons().contains(p)) {
	        p.étageDépart().supprimer(p);
	        Etage e = immeuble.étage(etageEnCours);
	        e.getPietons().add(p);
	        if(p.sens() == '^')
                echeancier.ajouter(new EvenementPietonArrivePalier(tempsPourMonterOuDescendreUnEtageAPieds + date, etageEnCours + 1, p));
	        else if(p.sens() == 'v')
                echeancier.ajouter(new EvenementPietonArrivePalier(tempsPourMonterOuDescendreUnEtageAPieds + date, etageEnCours - 1, p));
        } else if(p.sens() == '^' && p.étageDestination().numéro() != etageEnCours) {
            immeuble.étage(etageEnCours - 1).supprimerPieton(p);
            Etage e = immeuble.étage(etageEnCours);
            e.getPietons().add(p);
            echeancier.ajouter(new EvenementPietonArrivePalier(tempsPourMonterOuDescendreUnEtageAPieds + date, etageEnCours + 1, p));
        } else if(p.sens() == 'v' && p.étageDestination().numéro() != etageEnCours) {
            immeuble.étage(etageEnCours + 1).supprimerPieton(p);
            Etage e = immeuble.étage(etageEnCours);
            e.getPietons().add(p);
            echeancier.ajouter(new EvenementPietonArrivePalier(tempsPourMonterOuDescendreUnEtageAPieds + date, etageEnCours - 1, p));
        } else if(p.sens() == '^' && p.étageDestination().numéro() == etageEnCours) {
            immeuble.étage(etageEnCours - 1).supprimerPieton(p);
        } else if(p.sens() == 'v' && p.étageDestination().numéro() == etageEnCours) {
            immeuble.étage(etageEnCours + 1).supprimerPieton(p);
        }
    }

    public EvenementPietonArrivePalier(long d, int eec, Passager p) {
        // Signature approximative et temporaire... juste pour que cela compile.
        super(d);
        etageEnCours = eec;
        this.p = p;
    }

    public Passager getPassager() {
        return this.p;
    }
    
}
