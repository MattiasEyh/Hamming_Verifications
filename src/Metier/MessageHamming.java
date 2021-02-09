package Metier;

import java.util.stream.Stream;

public class MessageHamming {
	
	// Message reçu complet
    private final boolean[] bits;
    // isHammingCode : indique si this.bits est un mot de hamming (taille = 2^n - 1)
    private final boolean isHammingCode;
    // nbBitsControl : nombre de bits de controle si this.isHammingCode
    private final int nbControlBits;
    // controlBits : sous-mot de this.bits correspondant aux bits de controle si this.isHammingCode
    private final boolean[] controlBits;
    // receivedControlBits : mot constitué des bits de controle de reception
    private final boolean[] receivedControlBits;
 	// isCorrect : indique si this.bits est un mot de hamming sans erreurs si this.isHammingCode
    private final boolean isCorrect;
    // hasPadding : indique si le message initial a ete complete d'une suite de bits à 0 pour avoir une taille valide
    private boolean hasPadding;
    
    // Utilisé pour la construction d'un code de hamming
    private MessageHamming(boolean[] bits, boolean isHammingCode, int nbControlBits, boolean[] controlBits, 
    		boolean[] receivedControlBits, boolean isCorrect, boolean hasPadding) {
    	this.bits = bits;
    	this.isHammingCode = isHammingCode;
    	this.nbControlBits = nbControlBits;
    	this.controlBits = controlBits;
    	this.receivedControlBits = receivedControlBits;
    	this.isCorrect = isCorrect;
    	this.hasPadding = hasPadding;
    }
    
    // Utilisé pour la vérification d'un message
    public MessageHamming(String message){
        this.bits = fromStringToBits(message);
        // Initialisation de isHammingCode et nbControlBits
        int taille = bits.length; // taille = 2^n - 1 ?
    	taille++; // taille = 2^n ?
    	int n = 0;
        boolean verif = true;
        // taille = 2^n <=> taille / 2 / 2 / .... = 1
        // Invariant : taille / 2 / ...(n fois) est un entier
        // Quantite de controle : taille / 2 / ...(n fois)
        while (taille != 1 && verif) {
            if (taille % 2 != 0) {
                verif = false;
            }
            else{
                n++;
                taille = taille/2;
            }
        }
        isHammingCode = verif;
        nbControlBits = n;
        // Fin initialisation de isHammingCode et nbControlBits
        if(isHammingCode) {
        	controlBits = getControlBits(this);
        	receivedControlBits = getReceivedControlBits(this);
        	isCorrect = isCorrect(this);
        } else {
        	isCorrect = false;
        	receivedControlBits = new boolean[0];
        	controlBits = new boolean[0];
        }
        hasPadding = false;
    }
    
    public boolean[] getMessage() {
        return bits;
    }
    
    public boolean[] getReceivedControlBits() {
    	return receivedControlBits;
    }

    public int getNbControlBits() {
        return nbControlBits;
    }

    public int getTaille(){
        return getMessage().length;
    }

    public boolean getBit(int i){
    	 return getMessage()[i];
    }

    @Override
    public String toString() {
        String s = "";
    	for(int i = 0; i < bits.length; i++) {
        	s += bits[i] ? "1" : "0";
        }
        return s;
    }

	// Renvoie le mot qui correspond aux bits de controle de reception de message
	// A. E. : message.isHammingCode
	private static boolean[] getReceivedControlBits(MessageHamming message){
	    int motif = 1;
	    boolean[] bitsDeControles = new boolean[message.getNbControlBits()];
	    String messageFinal = "";
	    for(int i = message.getNbControlBits()-1; i >= 0; i--){
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
	
	// Renvoie le sous-mot de message.bits qui correspond aux bits de controle
	// A. E. : message.isHammingCode
	private static boolean[] getControlBits(MessageHamming message) {
		int nbControlBits = message.getNbControlBits();
		boolean[] controlBits = new boolean[nbControlBits];
		int PowerOfTwo = 1;
		for(int i = nbControlBits - 1; i >= 0; i--) {
			controlBits[i] = message.getBit(message.getBits().length - PowerOfTwo);
			PowerOfTwo *= 2;
		}
		return controlBits;
	}
	
	private boolean[] getBits() {
		return bits;
	}

	// Vérifie qu'un message de Hamming est valide (les bits de controle de reception sont tous à 0)
	// A. E. : message.isHammingCode
	private static boolean isCorrect(MessageHamming message) {
		boolean[] ReceivedControlBits = message.getReceivedControlBits();
		for(int i = 0; i < ReceivedControlBits.length; i++) {
			if(ReceivedControlBits[i])
				return false;
		}
		return true;
	}

	// Converti un message binaire sous forme de chaine de carateres en message sous forme de tableau de booleens
	// A. E. : message ne contient que des '0' et des '1'
	private static boolean[] fromStringToBits(String message) {
		boolean[] bits = new boolean[message.length()];
		for(int i = 0; i < message.length(); i++) {
			bits[i] = message.charAt(i) == '1' ? true : false;
		}
		return bits;
	}
	
	// Renvoie un MessageHamming correspondant au code de hamming pour sentMessage
	// Si sentMessage n'a pas pas une taille en (2^n - n - 1), une suite de 0 lui est adjoint (padding)
	public static MessageHamming getHammingCodeFor(String sentMessage) {
		// Verification de la necessite d'un padding
		int taille = sentMessage.length();
		int n = 0;
		int Un = 1 - 0 - 1;
		// Invariant : Un = 2^n - n - 1
		while(Un < taille) {
			Un += n + 1; // Un = 2^n
			Un *= 2; 	 // Un = 2^(n + 1)
			n++; 		 // Un = 2^n
			Un -= n + 1; // Un = 2^n - n - 1
		}
		boolean needPadding;
		int padding;
		if(Un == taille) {
			needPadding = false;
			padding = 0;
		} else {
			needPadding = true;
			padding = Un - taille;
		}
		// Fin verification padding
		
		// Ajout d'un padding si besoin
		if(needPadding) {
			sentMessage = Stream.generate(()->"0").limit(padding).reduce((a,b) -> a + b).get() + sentMessage;
		}
		// Fin ajout padding

		// Construction du message;
		boolean[] sentMessageBits = fromStringToBits(sentMessage);
        boolean[] HCodeBits = new boolean[sentMessageBits.length + n];
        // Construction d'un code de Hamming avec tout les bits de controle à 0
        int iSM = sentMessageBits.length - 1; // index for sent message
        for (int cpt = HCodeBits.length - 1; cpt >= 0; cpt--){
            if (isPowerOfTwo(HCodeBits.length - cpt)){
            	HCodeBits[cpt] = false;
            } else {
            	HCodeBits[cpt] = sentMessageBits[iSM];
            	iSM--;
            }
        }
        //Fin construction d'un code de Hamming avec tout les bits de controle à 0
        
        // Correction des bits de controle et construction du mot des bits de controle
        int pattern = 1;
        int indexCi = HCodeBits.length - 1; 
        boolean[] controlBits = new boolean[n];
        // On parcours les n bits de controle Ci
        for(int i = n - 1; i >= 0; i--){
            boolean parity = false;
            // On parcours les bits de contrôle de parité Ci en partant du dernier
            int indexParityBit = 0;
            while(indexParityBit < HCodeBits.length){
            	// on prend en compte 'pattern' bits 
                for (int cpt3 = 0; cpt3 < pattern; cpt3++){
                	parity ^= HCodeBits[indexParityBit++];
                }
                // on saute 'pattern' bits
                indexParityBit += pattern;
            }
            controlBits[i] = parity;
            HCodeBits[indexCi] = parity;
            pattern *= 2;
            indexCi = HCodeBits.length - 1 - (pattern - 1);
        }
        // Fin Correction des bits de controle 	
        boolean[] receivedControlBits = new boolean[n]; // par construction le code est correct
        												// donc bits de controle de reception à 0;
		return new MessageHamming(HCodeBits, true, n, controlBits, receivedControlBits, true, needPadding);
	}
	
    private static boolean isPowerOfTwo(int nb){
        return (nb != 0) && ((nb & (nb - 1)) == 0 );
    }

	public boolean isCorrect() {
		return isCorrect;
	}
	
	public boolean isHammingCode() {
		return isHammingCode;
	}
	
	// A. E.: !this.isCorrect
	public int getPositionErreur() {
		int twoPowI = 1;
		int errorPosition = 0;
		for(int i = receivedControlBits.length - 1; i >= 0; i--) {
			errorPosition += twoPowI * (receivedControlBits[i]? 1 : 0);
			twoPowI *= 2;
		}
		return errorPosition;
	}

	public boolean hasPadding() {
		return hasPadding;
	}
}
