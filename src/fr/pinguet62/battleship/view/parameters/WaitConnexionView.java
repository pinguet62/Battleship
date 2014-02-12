package fr.pinguet62.battleship.view.parameters;

import java.util.Collection;

import javax.swing.JFrame;

import fr.pinguet62.battleship.model.Game;
import fr.pinguet62.battleship.model.boat.Boat;
import fr.pinguet62.battleship.view.positioning.FleetPositioningView;

/** A wait view. */
public final class WaitConnexionView extends JFrame {

    /** Serial version UID. */
    private static final long serialVersionUID = 8730244299577968556L;

    /**
     * Constructor.
     * 
     * @param game
     *            The {@link Game}.
     */
    public WaitConnexionView(final Game game) {
	super("Waiting connexion.");
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	pack();
	setVisible(true);

	game.getSocketManager().waitClientConnection(new Runnable() {
	    @Override
	    public void run() {
		dispose();
		new FleetPositioningView(game);
	    }
	});
    }

}
