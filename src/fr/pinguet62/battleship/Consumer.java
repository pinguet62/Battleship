package fr.pinguet62.battleship;

/**
 * Operation that accepts a single input argument and returns no result.
 * 
 * @param <T>
 *            The argument type.
 */
public interface Consumer<T> {

    /** Performs this operation on the given argument. */
    void accept(T t);

}
