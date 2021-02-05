public class MessageHamming {

    private final String MESSAGE;
    private final int NB_BITS_CONTROLE;

    private final boolean IS_VALIDE_TAILLE;
    //private final boolean IS_VALIDE_MESSAGE;


    public MessageHamming(String message){
        this.MESSAGE = message;

        int taille = message.length()+1;

        int n = 0;
        boolean verif = true;

        while (taille != 1 && verif) {
            if (taille % 2 != 0) {
                verif = false;
            }
            else{
                n++;
                taille = taille/2;
            }
        }

        IS_VALIDE_TAILLE = verif;
        NB_BITS_CONTROLE = n;
    }

    public String getMessage() {
        return MESSAGE;
    }


    public int getNbBitsControles() {
        return NB_BITS_CONTROLE;
    }

    public int getTaille(){
        return getMessage().length();
    }

    public boolean getBit(int i){
        char msgTmp = getMessage().charAt(i);

        return msgTmp != '0';
    }

    @Override
    public String toString() {
        return MESSAGE;

    }
}
