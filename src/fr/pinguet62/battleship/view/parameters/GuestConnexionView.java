package fr.pinguet62.battleship.view.parameters;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import fr.pinguet62.battleship.view.WaitingView;

public final class GuestConnexionView extends JFrame {

    /** Serial version UID. */
    private static final long serialVersionUID = 4508008391795747860L;

    /** Constructor. */
    public GuestConnexionView() {
	super("Guest");
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	// Layout
	Container mainContainer = getContentPane();
	mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.Y_AXIS));

	// - Port
	JPanel portPanel = new JPanel();
	portPanel.setBorder(BorderFactory.createTitledBorder("Server"));
	portPanel.setLayout(new GridLayout(1, 2));
	mainContainer.add(portPanel);
	// -- Title
	JLabel portTitle = new JLabel("Port");
	portPanel.add(portTitle);
	// -- Value
	// 49152
	final JSpinner portValue = new JSpinner(new SpinnerNumberModel(49152,
		1, 65535, 1));
	portPanel.add(portValue);

	// - Button
	JPanel buttonPanel = new JPanel();
	buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
	mainContainer.add(buttonPanel);
	// -- Ok
	JButton okButton = new JButton("Ok");
	okButton.addActionListener(new ActionListener() {
	    /** Click on "Ok" button. */
	    @Override
	    public void actionPerformed(ActionEvent e) {
		dispose();
		final WaitingView waitConnexionView = new WaitingView(
			"Connexion to host...");
		// TODO connection avec Runnable en paramètre
	    }
	});
	buttonPanel.add(okButton);

	pack();
	setVisible(true);
    }
}
