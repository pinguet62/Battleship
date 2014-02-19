package fr.pinguet62.battleship.view;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

/** A non resizable {@link JFrame} centered on screen. */
public class Frame extends JFrame {

    /** Serial version UID. */
    private static final long serialVersionUID = 8378513434863704782L;

    protected static final int BORDER = 5;

    /**
     * {@link JPanel} used to have a {@link Border} around {@link JFrame}.<br />
     * All {@link JComponent} are placed in.
     */
    private final JPanel panel;

    /** If this {@link JFrame} is initialized. */
    private boolean initialized;

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

	panel = new JPanel();
	panel.setBorder(new EmptyBorder(BORDER, BORDER, BORDER, BORDER));
	panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	super.add(panel);

	initialized = true;
    }

    @Override
    public Component add(Component comp) {
	return panel.add(comp);
    }

    @Override
    public Container getContentPane() {
	if (!initialized)
	    return super.getContentPane();
	else
	    return panel;
    }

    @Override
    public void setLayout(LayoutManager manager) {
	if (!initialized)
	    super.setLayout(manager);
	else
	    panel.setLayout(manager);
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
