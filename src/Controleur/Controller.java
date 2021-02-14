package Controleur;

import javax.swing.SwingUtilities;

import Metier.MessageHamming;
import Vue.MainFrame;

/**
 * Controlleur du programme.
 *
 * Permet de faire le lien entre la vue (MainFrame) et les metiers
 * (MessageHamming).
 */

public class Controller {

    /**
     * Message reçu
     */
	private String suiteBinaire;

    /**
     * Vue du programme.
     */
    private MainFrame vue;

    /**
     * MessageHamming lie a la verification.
     */
    private MessageHamming metierVerification;

    /**
     * MessageHamming lie au Calcul.
     */
    private MessageHamming metierCalculCodeH;

    /**
     * Permet de mettre a jours l'ihm.
     */
    private void maj() {
		metierVerification = new MessageHamming(suiteBinaire);
		metierCalculCodeH = MessageHamming.getHammingCodeFor(suiteBinaire);
		vue.maj();
    }
    
    public Controller(){
        this.vue = new MainFrame(this);
        suiteBinaire = "";
        this.metierVerification = new MessageHamming(suiteBinaire);
        this.metierCalculCodeH = MessageHamming.getHammingCodeFor(suiteBinaire);
    }
    
    public String getResultatCalculCodeH(){
    	return (metierCalculCodeH.hasPadding() ? "Après ajout de bits à 0 : ": "") + metierCalculCodeH.toString();
    }
    
    public String getResultatVerification(){
    	if(metierVerification.isHammingCode()) {
    		return metierVerification.isCorrect()? "Aucune erreur détéctée" : "Une erreur à la position " + metierVerification.getPositionErreur();
    	} else {
    		return "La suite saisie n'est pas un code de Hamming";
    	}
    }

    public String getLogVerification(){
       return metierVerification.getLogVerification();
    }

    public String getLogCalculCodeH(){
        return metierCalculCodeH.getLogCalcul();
     }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Controller::new);
    }

	public void setSuiteBinaire(String text) {
		this.suiteBinaire = text;
		maj();
	}

}
