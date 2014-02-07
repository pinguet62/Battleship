package fr.pinguet62.battleship.view.positioning;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Arrays;

import javax.swing.JButton;

import fr.pinguet62.battleship.model.grid.Coordinates;

/**
 * Selectable case in grid.<br />
 * Used to place boat in grid.
 */
public final class SelectCase extends JButton {

	/** The state of a {@link SelectCase}. */
	public static enum State {
		BOAT(Color.RED), CHOOSED(Color.GREEN), SELECTABLE(Color.BLUE), UNSELECTABLE(
				Color.BLACK);

		/** The {@link Color}. */
		private final Color color;

		/**
		 * Constructor.
		 * 
		 * @param color
		 *            The {@link Color}.
		 */
		State(Color color) {
			this.color = color;
		}

		/**
		 * Gets the {@link Color}.
		 * 
		 * @return The {@link Color}.
		 */
		public Color getColor() {
			return color;
		}

	}

	/** Serial version UID. */
	private static final long serialVersionUID = -1587352033892307692L;

	/** The {@link Coordinates}. */
	private final Coordinates coordinates;

	/** The {@link State}. */
	private State state = State.SELECTABLE;

	/**
	 * Constructor.
	 * 
	 * @param coordinates
	 *            The {@link Coordinates}.
	 */
	public SelectCase(final Coordinates coordinates) {
		this.coordinates = coordinates;
		this.setPreferredSize(new Dimension(30, 30));
	}

	/**
	 * Gets the {@link Coordinates}.
	 * 
	 * @return The {@link Coordinates}.
	 */
	public Coordinates getCoordinates() {
		return coordinates;
	}

	/**
	 * Gets the {@link State}.
	 * 
	 * @return The {@link State}.
	 */
	public State getState() {
		return state;
	}

	/**
	 * Sets the {@link State}.
	 * 
	 * @param state
	 *            The {@link State}.
	 */
	public void setState(final State state) {
		setEnabled(Arrays.asList(State.CHOOSED, State.SELECTABLE).contains(
				state));
		this.state = state;
		setBackground(state.getColor());
	}

}
