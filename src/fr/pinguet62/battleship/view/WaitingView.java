package fr.pinguet62.battleship.view;

import javax.swing.JFrame;
import javax.swing.JLabel;

/** A waiting view. */
public final class WaitingView extends JFrame {

    /** Serial version UID. */
    private static final long serialVersionUID = 8730244299577968556L;

    /**
     * Constructor.
     * 
     * @param message
     *            The message to show to user.
     */
    public WaitingView(final String message) {
	super("Waiting...");
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	JLabel jLabel = new JLabel(message);
	add(jLabel);
	pack();
	setVisible(true);
    }

}
