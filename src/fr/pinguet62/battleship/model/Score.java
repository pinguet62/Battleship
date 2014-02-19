package fr.pinguet62.battleship.model;

/** The score of the player. */
public final class Score {

    /** The actual score. */
    private final int actual;

    /** The total score. */
    private final int total;

    /**
     * Constructor.
     * 
     * @param actual
     *            The actual score.
     * @param total
     *            The total score.
     */
    public Score(final int actual, final int total) {
	if ((actual < 0) || (total < 0) || (total < actual))
	    throw new IllegalArgumentException("Invalid score.");
	this.actual = actual;
	this.total = total;
    }

    /**
     * Gets the actual score.
     * 
     * @return The actual score.
     */
    public int getActual() {
	return actual;
    }

    /**
     * Gets the total score.
     * 
     * @return The total score.
     */
    public int getTotal() {
	return total;
    }

    /**
     * Gets if score is maximal.
     * 
     * @return Result.
     */
    public boolean isWin() {
	return actual == total;
    }

}
