public class EvenementPietonArrivePalier extends Evenement {
    /* PAP: Pieton Arrive Palier
       L'instant précis ou un passager qui à décidé de continuer à pieds arrive sur un palier donné.
       Classe à faire complètement par vos soins.
    */

    private int etageDepart;
    private Passager p;

    public void afficheDetails(StringBuilder buffer, Immeuble immeuble) {
	    buffer.append("PAP ");
	    buffer.append(etageDepart);
	    buffer.append(" #");
	    buffer.append(p.numPassager());
    }

    public void traiter(Immeuble immeuble, Echeancier echeancier) {
	    notYetImplemented();
    }

    public EvenementPietonArrivePalier(long d, int ed, Passager p) {
        // Signature approximative et temporaire... juste pour que cela compile.
        super(d);
        etageDepart = ed;
        this.p = p;
    }

    public Passager getPassager() {
        return this.p;
    }
    
}
