package fr.pinguet62.battleship.view;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;

import fr.pinguet62.battleship.view.parameters.GuestParametersView;
import fr.pinguet62.battleship.view.parameters.HostParametersView;

/** First view: choice between host and guest. */
public final class PlayerTypeView extends JFrame {

    /** Serial version UID. */
    private static final long serialVersionUID = 8779709289758249345L;

    /** Constructor. */
    public PlayerTypeView() {
	super("Player type");
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	// Layout
	Container mainContainer = getContentPane();
	mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.X_AXIS));

	// - Host
	JButton hostButton = new JButton("Host");
	hostButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(final ActionEvent e) {
		System.out.println("HOST");
		new HostParametersView();
		dispose();
	    }
	});
	mainContainer.add(hostButton);
	// - Guest
	JButton guestButton = new JButton("Guest");
	guestButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(final ActionEvent e) {
		System.out.println("GUEST");
		new GuestParametersView();
		dispose();
	    }
	});
	mainContainer.add(guestButton);

	pack();
	setVisible(true);
    }

}
