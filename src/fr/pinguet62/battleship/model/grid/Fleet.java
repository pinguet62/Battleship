package fr.pinguet62.battleship.model.grid;

import fr.pinguet62.battleship.model.Game;
import fr.pinguet62.battleship.model.Score;
import fr.pinguet62.battleship.model.boat.Boat;

public final class Fleet {

    private final Box[][] boxss;

    private final Game model;

    public Fleet(final Game model) {
	boxss = new Box[model.getHeight()][model.getWidth()];
	this.model = model;
	for (int y = 0; y < boxss.length; y++)
	    for (int x = 0; x < boxss[y].length; x++)
		boxss[y][x] = new Box(new Coordinates(x, y));
    }

    public void addBoat(final Boat boat) {
	// Tests
	for (Box boatBox : boat.getBoxs()) {
	    Coordinates boatCoordinates = boatBox.getCoordinates();
	    // - out of grid
	    if ((model.getWidth() <= boatCoordinates.getX())
		    || (model.getHeight() <= boatCoordinates.getY()))
		throw new IllegalArgumentException("Boat out of grid.");
	    // - superposition
	    if (getBox(boatCoordinates).getBoat() != null)
		throw new IllegalArgumentException(
			"Boat already exists in this zone.");
	}

	// Insert
	Box[] boxs = boat.getBoxs();
	for (Box boatBox : boxs) {
	    Coordinates coordinatesBoatBox = boatBox.getCoordinates();
	    boxss[coordinatesBoatBox.getY()][coordinatesBoatBox.getX()] = boatBox;
	}
	// model.getBoats().add(boat);
    }

    public Box getBox(final Coordinates coordinates) {
	return boxss[coordinates.getY()][coordinates.getX()];
    }

    public Score getScore() {
	int actual = 0;
	int total = 0;
	for (Box[] boxs : boxss)
	    for (Box box : boxs) {
		total++;
		if (box.isAttacked())
		    actual++;
	    }
	return new Score(actual, total);
    }

    public void insertBoat(final Boat boat, final Coordinates first,
	    final Coordinates last) {
	// Vertical
	if (first.getX() == last.getX()) {
	    final int x = first.getX();
	    for (int y = first.getY(), i = 0; y <= last.getY(); y++, i++) {
		Box box = new Box(new Coordinates(x, y), boat);
		boxss[y][x] = box;
		boat.setBox(i, box);
	    }
	}
	// Horizontal
	else if (first.getY() == last.getY()) {
	    final int y = first.getY();
	    for (int x = first.getX(), i = 0; x <= last.getX(); x++, i++) {
		Box box = new Box(new Coordinates(x, y), boat);
		boxss[y][x] = box;
		boat.setBox(i, box);
	    }
	}
	// Error
	else
	    throw new IllegalArgumentException("Invalid coordinates.");
    }

}
