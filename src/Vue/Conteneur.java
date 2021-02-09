package Vue;

import Controleur.Controller;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AbstractDocument;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Conteneur extends JFrame {
	
	private Controller ctrl;
	private JPanel zoneSaisie;
	private JTextField saisie;
	private JLabel labelSaisie;
	
	private JTabbedPane fonctionnalites;
	private JPanel foncVerification; 
	private JPanel foncCalculCodeH;
	
	private JLabel logVerification;
	private JLabel resultatVerification;
	
	private JLabel logCalculCodeH;
	private JLabel resultatCalculCodeH;

    public Conteneur(Controller ctrl){
        super("TP3 : Le code de Hamming");
        this.ctrl = ctrl;
        init();
        this.pack();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private void init(){
    	zoneSaisie = new JPanel();
    	labelSaisie = new JLabel("Entrez une suite binaire :");
    	zoneSaisie.add(labelSaisie);
    	saisie = new JTextField(20);
    	saisie.setToolTipText("Entrez uniquement des '0' et des '1'");
    	saisie.getDocument().addDocumentListener(new DocumentListener() {
        	
        	private void maj() {
        	       ctrl.setSuiteBinaire(saisie.getText());
        	}
        	
			@Override
			public void insertUpdate(DocumentEvent e) {
				maj();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				maj();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				maj();
			}
        	
        });
    	((AbstractDocument) saisie.getDocument()).setDocumentFilter(new BinaryDocumentFilter());
    	zoneSaisie.add(saisie);
    	add(zoneSaisie,BorderLayout.NORTH);
    	
    	fonctionnalites = new JTabbedPane();
    	
    	foncVerification = new JPanel();
    	foncVerification.setLayout(new BoxLayout(foncVerification, BoxLayout.Y_AXIS));
    	logVerification = new JLabel("logs");
    	foncVerification.add(logVerification);
    	resultatVerification = new JLabel("rien");
    	foncVerification.add(resultatVerification);
    	fonctionnalites.add("vérification",foncVerification);
    	
    	foncCalculCodeH = new JPanel();
    	foncCalculCodeH.setLayout(new BoxLayout(foncCalculCodeH, BoxLayout.Y_AXIS));
    	logCalculCodeH = new JLabel("logs");
    	foncCalculCodeH.add(logCalculCodeH);
    	resultatCalculCodeH = new JLabel("rien");
    	foncCalculCodeH.add(resultatCalculCodeH);
    	fonctionnalites.add("Calcul du code d'Hamming",foncCalculCodeH);	
    	
    	add(fonctionnalites, BorderLayout.CENTER);
    }

	public void maj() {
		resultatVerification.setText(ctrl.getResultatVerification());
		logVerification.setText(ctrl.getLogVerification());
		resultatCalculCodeH.setText(ctrl.getResultatCalculCodeH());
		logCalculCodeH.setText(ctrl.getLogCalculCodeH());
	}

}
