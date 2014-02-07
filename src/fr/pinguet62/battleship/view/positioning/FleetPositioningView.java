package fr.pinguet62.battleship.view.positioning;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import fr.pinguet62.battleship.model.boat.Boat;
import fr.pinguet62.battleship.model.grid.Coordinates;
import fr.pinguet62.battleship.model.grid.Fleet;
import fr.pinguet62.battleship.view.positioning.SelectCase.State;

/** View used to place {@link Boat}s in grid. */
public final class FleetPositioningView implements ActionListener {

	/** The {@link JPanel} of {@link BoatView}s. */
	final JPanel boatsPanel;

	/** The {@link Boat}s to place in grid. */
	private Collection<BoatView> boatViews = new ArrayList<BoatView>();

	/** The grid of {@link SelectCase}s. */
	private SelectCase[][] casess;

	/** The {@link JFrame}. */
	private JFrame frame = new JFrame("Fleet Positioning");

	/** The {@link JPanel}. */
	private JPanel gridFleetPanel;

	/** The height. */
	private final int height;

	/** The selected {@link Boat}. */
	private Boat selectedBoat;

	/** The selected {@link BoatView}. */
	private BoatView selectedBoatView;

	/** The width. */
	private final int width;

	/**
	 * Constructor.
	 * 
	 * @param width
	 *            The width of {@link Fleet}.
	 * @param height
	 *            The height of {@link Fleet}.
	 * @param boats
	 *            The {@link Boat}s.
	 */
	public FleetPositioningView(final int width, final int height,
			final Collection<Boat> boats) {
		this.width = width;
		this.height = height;

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Layout
		Container mainContainer = frame.getContentPane();
		mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.X_AXIS));
		// - Boats
		boatsPanel = new JPanel();
		boatsPanel.setLayout(new BoxLayout(boatsPanel, BoxLayout.Y_AXIS));
		mainContainer.add(boatsPanel);
		for (Boat boat : boats) {
			final BoatView boatView = new BoatView(boat);
			boatView.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// Save selection
					selectedBoat = boatView.getBoat();
					selectedBoatView = boatView;
					// Disable buttons
					for (BoatView boatView : boatViews) {
						boatView.setEnabled(false);
					}
				}
			});
			boatViews.add(boatView);
			boatsPanel.add(boatView);
		}
		// - Grid
		gridFleetPanel = new JPanel();
		gridFleetPanel.setLayout(new GridLayout(height, width));
		mainContainer.add(gridFleetPanel);
		// -- Buttons
		casess = new SelectCase[height][width];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				SelectCase button = new SelectCase(new Coordinates(x, y));
				button.addActionListener(this);
				gridFleetPanel.add(button);
				casess[y][x] = button;
			}
		}

		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * Click on a {@link SelectCase}.
	 * 
	 * @param e
	 *            The {@link ActionEvent}.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (selectedBoat == null) {
			return;
		}

		SelectCase clickCase = (SelectCase) e.getSource();

		clickCase
				.setState(clickCase.getState().equals(State.CHOOSED) ? State.SELECTABLE
						: State.CHOOSED);

		refresh();

		// Count ok
		List<SelectCase> choosedCases = getSelectedCases(State.CHOOSED);
		if (choosedCases.size() == selectedBoat.getSize()) {
			// TODO insert boat
			for (SelectCase choosedCase : choosedCases) {
				choosedCase.setState(State.BOAT);
			}
			for (SelectCase unselectableCase : getSelectedCases(State.UNSELECTABLE)) {
				unselectableCase.setState(State.SELECTABLE);
			}
			selectedBoat = null;

			selectedBoatView.setPlaced(true);
			// Refresh buttons
			for (BoatView boatView : boatViews) {
				if (!boatView.isPlaced()) {
					boatView.setEnabled(true);
				}
			}
		}
	}

	/**
	 * Get the index of first (for {@link Direction#TOP} or
	 * {@link Direction#LEFT}) or last (for {@link Direction#BOTTOM} and
	 * {@link Direction#RIGHT}) {@link SelectCase} available (without
	 * {@link Boat}).
	 * 
	 * @param coordinates
	 *            The start {@link Coordinates}.
	 * @param dir
	 *            The {@link Direction}.
	 * @param size
	 *            The number of {@link SelectCase} between coordinates and
	 *            first/last.
	 * @return The index.
	 */
	private int getLastAvailable(final Coordinates coordinates,
			final Direction dir, final int size) {
		int x = coordinates.getX();
		int y = coordinates.getY();
		while ((0 <= x && x < width)
				&& (0 <= y && y < height)
				&& !casess[y][x].getState().equals(State.BOAT)
				&& (Math.abs(x - coordinates.getX()) + Math.abs(y
						- coordinates.getY())) < size) {
			x += dir.getSensX();
			y += dir.getSensY();
		}
		if (dir.isHorizontal()) {
			return y;
		} else {
			return x;
		}
	}

	/**
	 * Gets {@link SelectCase}s by {@link State}.
	 * 
	 * @param state
	 *            The {@link State}.
	 * @return The {@link SelectCase}s.
	 */
	private List<SelectCase> getSelectedCases(final State state) {
		List<SelectCase> selectedCases = new ArrayList<SelectCase>();
		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width; i++) {
				if (casess[j][i].getState().equals(state)) {
					selectedCases.add(casess[j][i]);
				}
			}
		}
		return selectedCases;
	}

	/** Refresh the grid. */
	private void refresh() {
		// Reset selection
		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width; i++) {
				if (!Arrays.asList(State.BOAT, State.CHOOSED).contains(
						casess[j][i].getState())) {
					casess[j][i].setState(State.SELECTABLE);
				}
			}
		}

		// Choosed cases
		List<SelectCase> selectedCases = new ArrayList<SelectCase>();
		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width; i++) {
				if (casess[j][i].getState().equals(State.CHOOSED)) {
					selectedCases.add(casess[j][i]);
				}
			}
		}

		// 0 selection
		if (selectedCases.size() == 0) {
			for (int j = 0; j < height; j++) {
				for (int i = 0; i < width; i++) {
					if (!casess[j][i].getState().equals(State.BOAT)) {
						casess[j][i].setState(State.SELECTABLE);
					}
				}
			}
		} else {
			// 1 selection : cross
			if (selectedCases.size() == 1) {
				Coordinates selected = selectedCases.get(0).getCoordinates();
				final int x = selected.getX();
				final int y = selected.getY();
				// Complementary to the cross
				for (int j = 0; j < height; j++) {
					for (int i = 0; i < width; i++) {
						if (i != x && j != y) {
							if (casess[j][i].getState()
									.equals(State.SELECTABLE)) {
								casess[j][i].setState(State.UNSELECTABLE);
							}
						}
					}
				}
				// Cross
				// - horizontal
				int left = getLastAvailable(selected, Direction.LEFT,
						selectedBoat.getSize() - 1);
				int right = getLastAvailable(selected, Direction.RIGHT,
						selectedBoat.getSize() - 1);
				for (int i = 0; i < left; i++) {
					if (!casess[selected.getY()][i].getState().equals(
							State.BOAT)) {
						casess[selected.getY()][i].setState(State.UNSELECTABLE);
					}
				}
				for (int i = left; i <= right; i++) {
				}
				for (int i = right + 1; i < width; i++) {
					if (!casess[selected.getY()][i].getState().equals(
							State.BOAT)) {
						casess[selected.getY()][i].setState(State.UNSELECTABLE);
					}
				}
				// - vertical
				int top = getLastAvailable(selected, Direction.TOP,
						selectedBoat.getSize() - 1);
				int bottom = getLastAvailable(selected, Direction.BOTTOM,
						selectedBoat.getSize() - 1);
				for (int j = 0; j < top; j++) {
					if (!casess[j][selected.getX()].getState().equals(
							State.BOAT)) {
						casess[j][selected.getX()].setState(State.UNSELECTABLE);
					}
				}
				for (int j = top; j <= bottom; j++) {
				}
				for (int j = bottom + 1; j < height; j++) {
					if (!casess[j][selected.getX()].getState().equals(
							State.BOAT)) {
						casess[j][selected.getX()].setState(State.UNSELECTABLE);
					}
				}
			}
			// Many selection : horizontal or vertical
			else {
				Coordinates first = selectedCases.get(0).getCoordinates();
				Coordinates last = selectedCases.get(selectedCases.size() - 1)
						.getCoordinates();
				// Horizontal
				if (first.getY() == last.getY()) {
					final int y = first.getY();
					// Out of line
					for (int j = 0; j < height; j++) {
						if (j != y) {
							for (int i = 0; i < width; i++) {
								if (!casess[j][i].getState().equals(State.BOAT)) {
									casess[j][i].setState(State.UNSELECTABLE);
								}
							}
						}
					}
					// Join
					for (int i = first.getX(); i <= last.getX(); i++) {
						casess[y][i].setState(State.CHOOSED);
					}
					// Available space
					int size = selectedBoat.getSize()
							- (last.getX() - first.getX() + 1);
					int left = getLastAvailable(first, Direction.LEFT, size);
					int right = getLastAvailable(last, Direction.RIGHT, size);
					for (int i = 0; i < left; i++) {
						if (!casess[y][i].getState().equals(State.BOAT)) {
							casess[y][i].setState(State.UNSELECTABLE);
						}
					}
					for (int i = left; i <= right; i++) {
					}
					for (int i = right + 1; i < width; i++) {
						if (!casess[y][i].getState().equals(State.BOAT)) {
							casess[y][i].setState(State.UNSELECTABLE);
						}
					}
				}
				// Vertical
				else {
					final int x = first.getX();
					// Out of column
					for (int i = 0; i < width; i++) {
						if (i != x) {
							for (int j = 0; j < height; j++) {
								if (!casess[j][i].getState().equals(State.BOAT)) {
									casess[j][i].setState(State.UNSELECTABLE);
								}
							}
						}
					}
					// Join
					for (int j = first.getY(); j <= last.getY(); j++) {
						casess[j][x].setState(State.CHOOSED);
					}
					// Available space
					int size = selectedBoat.getSize()
							- (last.getY() - first.getY() + 1);
					int top = getLastAvailable(first, Direction.TOP, size);
					int bottom = getLastAvailable(last, Direction.BOTTOM, size);
					for (int j = 0; j < top; j++) {
						if (!casess[j][x].getState().equals(State.BOAT)) {
							casess[j][x].setState(State.UNSELECTABLE);
						}
					}
					for (int j = top; j <= bottom; j++) {
					}
					for (int j = bottom + 1; j < height; j++) {
						if (!casess[j][x].getState().equals(State.BOAT)) {
							casess[j][x].setState(State.UNSELECTABLE);
						}
					}
				}
			}
		}
	}

	/**
	 * Tests if all {@link Boat} are placed.
	 * 
	 * @return Result.
	 */
	private boolean isFinish() {
		for (BoatView boatView : boatViews) {
			if (!boatView.isPlaced()) {
				return false;
			}
		}
		return true;
	}

}
