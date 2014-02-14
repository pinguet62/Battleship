package fr.pinguet62.utils;

/**
 * Wrapper for Hexavigesimal representation and conversions. <br />
 * 0 <-> "A"<br/>
 * 25 <-> "Z"<br/>
 * 26 <-> "AA"<br/>
 * 27 <-> "AB"<br/>
 * 730 <-> "ABC"
 */
public final class Hexavigesimal {

    /**
     * Parses the {@link String} representation of Hexavigesimal value.
     * 
     * @param representation
     *            The {@link String} representation.
     * @return The long value.
     * @throws IllegalArgumentException
     *             Invalid hexavigesimal representation.
     */
    public static long parse(final String representation) {
	if ((representation == null) || !representation.matches("[A-Z]+"))
	    throw new IllegalArgumentException(
		    "Invalid hexavigesimal representation.");

	int decimal = 0;
	for (int index = 0; index < representation.length(); index++) {
	    int m = representation.charAt(index) - 'A';
	    if ((representation.length() != 1)
		    && (index != (representation.length() - 1)))
		m++;
	    int p = representation.length() - index - 1;
	    decimal += m * Math.pow(26, p);
	}
	return decimal;
    }

    /** The long value. */
    private final long value;

    /** Default constructor. */
    public Hexavigesimal() {
	value = 0;
    }

    /**
     * Constructor this initial value.
     * 
     * @param value
     *            The initial value.
     * @throws IllegalArgumentException
     *             Unauthorized negative value.
     */
    public Hexavigesimal(final long value) {
	if (value < 0)
	    throw new IllegalArgumentException("Unauthorized negative value.");

	this.value = value;
    }

    /**
     * Constructor this initial value.
     * 
     * @param representation
     *            The initial representation.
     * @throws IllegalArgumentException
     *             Invalid hexavigesimal representation.
     */
    public Hexavigesimal(final String representation) {
	value = Hexavigesimal.parse(representation);
    }

    /**
     * Gets the long value.
     * 
     * @return The long value.
     */
    public long longValue() {
	return value;
    }

    /**
     * Gets the Hexavigesimal representation.
     * 
     * @return The Hexavigesimal representation.
     */
    @Override
    public String toString() {
	long decimal = value;
	StringBuilder converted = new StringBuilder();
	decimal++;
	while (0 < decimal) {
	    --decimal;
	    converted.append((char) ('A' + (decimal % 26)));
	    decimal /= 26;
	}
	return converted.reverse().toString();
    }

}
