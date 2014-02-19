package fr.pinguet62.battleship.view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.JFrame;

/** A non resizable {@link JFrame} centered on screen. */
public class Frame extends JFrame {

    /** Serial version UID. */
    private static final long serialVersionUID = 8378513434863704782L;

    /**
     * Constructor.
     * 
     * @param title
     *            The {@link JFrame} title.
     */
    public Frame(final String title) {
	super(title);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setResizable(false);
    }

    /**
     * Call {@link Window#pack()}, center the {@link Window} and call
     * {@link Window#setVisible(boolean)}.
     */
    @Override
    public void setVisible(final boolean show) {
	if (show) {
	    pack();
	    // Center
	    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (screenSize.width - getWidth()) / 2;
	    int y = (screenSize.height - getHeight()) / 2;
	    setLocation(x, y);
	}

	super.setVisible(show);
    }

}
