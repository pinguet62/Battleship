package fr.pinguet62.battleship.view;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;

import fr.pinguet62.battleship.view.parameters.ParametersView;

public final class PlayerTypeChoiceView {

    public PlayerTypeChoiceView() {
	final JFrame frame = new JFrame("Player type");
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	// Layout
	Container mainContainer = frame.getContentPane();
	mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.X_AXIS));
	// Host
	JButton hostButton = new JButton("Host");
	hostButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(final ActionEvent event) {
		new ParametersView();
		frame.dispose();
	    }
	});
	mainContainer.add(hostButton);
	// Guest
	JButton guestButton = new JButton("Guest");
	guestButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(final ActionEvent event) {
		// TODO
	    }
	});
	mainContainer.add(guestButton);

	frame.pack();
	frame.setVisible(true);
    }

}
