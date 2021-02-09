package Metier;

import Controleur.Controller;
import Vue.Conteneur;

public class HammingVerification {
    private String baseCode;
    private String hammingCode;

    private Controller ctrl;


    public HammingVerification(Controller ctrl){
        this.ctrl = ctrl;
        baseCode = null;
        hammingCode = null;
    }

    private boolean isPowerOfTwo(int nb){
        return (nb != 0) && ((nb & (nb - 1)) == 0 );
    }


    /*-------------------*/
    /* Getters & Setters */
    /*-------------------*/

    public String getBaseCode() {
        return baseCode;
    }

    public void setBaseCode(String baseCode) {
        this.baseCode = baseCode;
    }

    public String getHammingCode() {
        return hammingCode;
    }

    // Traitement

    public boolean[] VerificationMessage(MessageHamming message){
        int motif = 1;
        boolean[] bitsDeControles = new boolean[message.getNbBitsControles()];
        String messageFinal = "";

        for(int i = message.getNbBitsControles()-1; i >= 0; i--){
            //bitsDeControles[i] = false;

            int parcoursCase = 0;
            while (parcoursCase< message.getTaille()){
                for (int j = 0; j<motif; j++){
                    bitsDeControles[i] = bitsDeControles[i] ^ message.getBit(parcoursCase);
                    parcoursCase++;
                }

                parcoursCase += motif;
            }
            motif *= 2;
        }

       return bitsDeControles;

    }

    public String ConversionBitsDeControle(boolean[] bitsDeControle){
        StringBuilder messageTmp = new StringBuilder();

        for (boolean b : bitsDeControle) {
            messageTmp.append(b ? "1" : "0");
        }

        return messageTmp.toString();
    }


    public String ConvertToHammingCode(String code){
        int nbBitsControle = getBitDeControle(code);
        String[] tabTmp = new String[code.length()+nbBitsControle];

        int cptMsg = 0;

        for (int cpt=tabTmp.length-1; cpt >= 0; cpt--){
            if (isPowerOfTwo(cpt+1)){
                tabTmp[cpt] = "0";
            } else {
                tabTmp[cpt] = String.valueOf(code.charAt(cptMsg));
                cptMsg++;
            }
        }

        int motif = 1;
        int iC = 0;
        for(int cpt=0; cpt<nbBitsControle; cpt++){
            int Ccpt = 0;
            for(int cpt2 = tabTmp.length-1; cpt2 >= 0; cpt2-=motif){
                for (int cpt3 = 0; cpt3 < motif; cpt3++){
                    Ccpt = Integer.parseInt(tabTmp[cpt2--]) ^ Ccpt;
                }
            }

            tabTmp[iC] = String.valueOf(Ccpt);
            motif *= 2;

            iC = motif-1;
        }

        String messageHamming = "";

        for(int cpt2 = tabTmp.length-1; cpt2>=0; cpt2--){
            messageHamming += tabTmp[cpt2];
        }

        return messageHamming;
    }

    private int getBitDeControle(String code){
        return 3;
    }
}
