package fr.pinguet62.battleship.model.grid;

import java.util.ArrayList;
import java.util.Collection;

import fr.pinguet62.battleship.model.Game;
import fr.pinguet62.battleship.model.Score;
import fr.pinguet62.battleship.model.boat.Boat;

/** The fleet of player. */
public final class Fleet {

    /** The {@link Boat}s. */
    private final Collection<Boat> boats = new ArrayList<>();

    /** The grid of {@link Box}. */
    private final Box[][] boxss;

    /**
     * Constructor.
     * 
     * @param game
     *            The {@link Game}.
     */
    public Fleet(final Game game) {
	boxss = new Box[game.getHeight()][game.getWidth()];
	for (int y = 0; y < boxss.length; y++)
	    for (int x = 0; x < boxss[y].length; x++)
		boxss[y][x] = new Box(new Coordinates(x, y));
    }

    /**
     * Gets the {@link Box} at {@link Coordinates}.
     * 
     * @param coordinates
     *            The {@link Coordinates}.
     * @return The {@link Box}.
     */
    public Box getBox(final Coordinates coordinates) {
	return boxss[coordinates.getY()][coordinates.getX()];
    }

    /**
     * Gets the {@link Score}.
     * 
     * @return The {@link Score}.
     */
    public Score getScore() {
	int actual = 0;
	int total = 0;
	for (Box[] boxs : boxss)
	    for (Box box : boxs)
		if (box.getBoat() != null) {
		    total++;
		    if (box.isAttacked())
			actual++;
		}
	return new Score(actual, total);
    }

    /**
     * Insert {@link Boat}. <br />
     * Update {@link Box}ss of {@link Fleet} and {@link Box}s of {@link Boat}. <br />
     * The first and last {@link Coordinates} must be aligned (vertical or
     * horizontal).
     * 
     * @param boatClass
     *            The {@link Boat} class.
     * @param first
     *            The first {@link Coordinates} (top/left).
     * @param last
     *            The last {@link Coordinates} (bottom/right).
     * @throws IllegalArgumentException
     *             Invalid {@link Coordinates} or a {@link Boat} already exists
     *             in they positions.
     */
    public void insertBoat(final Class<? extends Boat> boatClass,
	    final Coordinates first, final Coordinates last) {
	if ((first.getX() != last.getX()) && (first.getY() != last.getY()))
	    throw new IllegalArgumentException("Invalid coordinates.");

	Boat boat = Boat.getInstance(boatClass);

	// Vertical
	if (first.getX() == last.getX()) {
	    final int x = first.getX();
	    for (int y = first.getY(), i = 0; y <= last.getY(); y++, i++) {
		Box box = new Box(new Coordinates(x, y), boat);
		boxss[y][x] = box;
		boat.getBoxs()[i] = box;
	    }
	}
	// Horizontal
	else {
	    final int y = first.getY();
	    for (int x = first.getX(), i = 0; x <= last.getX(); x++, i++) {
		Box box = new Box(new Coordinates(x, y), boat);
		boxss[y][x] = box;
		boat.getBoxs()[i] = box;
	    }
	}
	boats.add(boat);
    }

}
