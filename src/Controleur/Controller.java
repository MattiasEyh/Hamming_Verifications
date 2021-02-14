package Controleur;

import javax.swing.SwingUtilities;

import Metier.MessageHamming;
import Vue.MainFrame;

public class Controller {
	
	private String suiteBinaire;
    private MainFrame vue;
    private MessageHamming metierVerification;
    private MessageHamming metierCalculCodeH;

    
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
