package Vue;

import Controleur.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Conteneur extends JFrame implements ActionListener {

    private JPanel pnlHaut;
    private JPanel pnlCentre;
    private JPanel pnlBas;

    private ButtonGroup btGroup;
    private JRadioButton rbValidation;
    private JRadioButton rbConstruction;

    private JLabel lblMessageIn;
    private JLabel lblMessageOut;

    private JTextField txtMessageIn;
    private JTextField txtMessageout;

    private JTextArea txtLogs;

    private JButton valider;

    private Controller ctrl;

    public Conteneur(Controller ctrl){
        super("Hamming - Traitements de bits");

        this.ctrl = ctrl;

        init();

        this.pack();
        this.setVisible(true);
    }

    private void init(){
        this.pnlHaut   = new JPanel();
        this.pnlCentre = new JPanel(new FlowLayout(FlowLayout.LEFT));
        this.pnlBas    = new JPanel(new FlowLayout(FlowLayout.LEFT));


        this.btGroup = new ButtonGroup();
        this.rbValidation   = new JRadioButton("Validation");
        this.rbConstruction = new JRadioButton("Construction");

        this.lblMessageIn  = new JLabel ("Message en entrée : ");
        this.lblMessageOut = new JLabel ( "Message en sortie : ");

        this.txtMessageIn   = new JTextField(10);
        this.txtMessageout  = new JTextField(10);

        this.txtMessageout.setEditable(false);

        this.txtLogs = new JTextArea("LOGS :",3, 25);

        this.valider = new JButton("Valider");
        this.valider.addActionListener(this);

        this.btGroup.add(rbValidation);
        this.btGroup.add(rbConstruction);

        this.pnlHaut.add(rbValidation);
        this.pnlHaut.add(rbConstruction);

        this.pnlCentre.add(lblMessageIn);
        this.pnlCentre.add(txtMessageIn);
        this.pnlCentre.add(valider);

        this.pnlBas.add(lblMessageOut);
        this.pnlBas.add(txtMessageout);

        this.add(pnlHaut, BorderLayout.NORTH);
        this.add(pnlCentre, BorderLayout.CENTER);
        this.add(pnlBas, BorderLayout.SOUTH);

        this.add(txtLogs, BorderLayout.EAST);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (this.txtMessageIn.getText() != "") {
            if (e.getSource() == this.valider && this.rbConstruction.isSelected()) {
                this.txtMessageout.setText(this.ctrl.getMessageOut(this.txtMessageIn.getText()));
            }


        }
    }
}
