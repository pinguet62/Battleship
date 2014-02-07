package fr.pinguet62.battleship.view.positioning;

public enum Direction {

	BOTTOM(0, +1), LEFT(-1, 0), RIGHT(+1, 0), TOP(0, -1);

	final int sensX;

	final int sensY;

	Direction(final int sensX, final int sensY) {
		this.sensX = sensX;
		this.sensY = sensY;
	}

	public int getSensX() {
		return sensX;
	}

	public int getSensY() {
		return sensY;
	}

	public boolean isVertical() {
		return sensY == 0 && sensX != 0;
	}

	public boolean isHorizontal() {
		return sensX == 0 && sensY != 0;
	}

}
