package fr.pinguet62.battleship.view;

import javax.swing.JLabel;

/** A waiting view. */
public final class WaitingView extends Frame {

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

	JLabel jLabel = new JLabel(message);
	add(jLabel);

	setVisible(true);
    }

}
