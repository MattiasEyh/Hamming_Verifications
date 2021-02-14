package Vue;

import Controleur.Controller;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AbstractDocument;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
	
	private Controller ctrl;
	private JPanel zoneSaisie;
	private JTextField saisie;
	private JLabel labelSaisie;
	
	private JTabbedPane fonctionnalites;
	private JPanel foncVerification; 
	private JPanel foncCalculCodeH;
	
	private JTextArea logVerification;
	private JLabel resultatVerification;
	
	private JTextArea logCalculCodeH;
	private JLabel resultatCalculCodeH;

    public MainFrame(Controller ctrl){
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
    	
    	Dimension  logD = new Dimension(300,190);
    	foncVerification = new JPanel(new BorderLayout());
    	foncVerification.setBorder(BorderFactory.createLineBorder(Color.black));
    	logVerification = new JTextArea();
    	logVerification.setFont(new Font("monospaced", Font.PLAIN, 12));
    	logVerification.setEditable(false);
    	logVerification.setWrapStyleWord(true);
    	logVerification.setSize(logD);
    	logVerification.setMinimumSize(logD);
    	logVerification.setPreferredSize(logD);
    	logVerification.setBorder(BorderFactory.createLineBorder(Color.black));
    	foncVerification.add(logVerification, BorderLayout.CENTER);
    	resultatVerification = new JLabel(" ");
    	resultatVerification.setHorizontalAlignment(SwingConstants.CENTER);
    	foncVerification.add(resultatVerification, BorderLayout.SOUTH);
    	fonctionnalites.add("v?rification",foncVerification);
    	
    	foncCalculCodeH = new JPanel(new BorderLayout());
    	foncCalculCodeH.setBorder(BorderFactory.createLineBorder(Color.black));
    	logCalculCodeH = new JTextArea();
    	logCalculCodeH.setFont(new Font("monospaced", Font.PLAIN, 12));
    	logCalculCodeH.setEditable(false);
    	logCalculCodeH.setWrapStyleWord(true);
    	logCalculCodeH.setSize(logD);
    	logCalculCodeH.setMinimumSize(logD);
    	logCalculCodeH.setPreferredSize(logD);
    	logCalculCodeH.setBorder(BorderFactory.createLineBorder(Color.black));
    	foncCalculCodeH.add(logCalculCodeH, BorderLayout.CENTER);
    	resultatCalculCodeH = new JLabel(" ");
    	resultatCalculCodeH.setHorizontalAlignment(SwingConstants.CENTER);
    	foncCalculCodeH.add(resultatCalculCodeH, BorderLayout.SOUTH);
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
