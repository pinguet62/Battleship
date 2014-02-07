package fr.pinguet62.battleship.model;

public final class Score {

	private final int actual;

	private final int total;

	public Score(final int actual, final int total) {
		if (actual < 0 || total < 0 || total < actual) {
			throw new IllegalArgumentException("Invalid score.");
		}
		this.actual = actual;
		this.total = total;
	}

	public int getActual() {
		return actual;
	}

	public int getTotal() {
		return total;
	}

}
