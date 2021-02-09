package Controleur;

import Metier.HammingVerification;
import Vue.Conteneur;

public class Controller {

    private Conteneur vue;
    private HammingVerification metier;

    public Controller(){
        this.vue = new Conteneur(this);
        this.metier = new HammingVerification(this);
    }

    public String getMessageOut(String messageIn){
       return metier.ConvertToHammingCode(messageIn);
    }

    public static void main(String[] args) {
        new Controller();
    }
}
