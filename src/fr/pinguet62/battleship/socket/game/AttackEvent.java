package fr.pinguet62.battleship.socket.game;

import fr.pinguet62.battleship.model.grid.Coordinates;

/** Attack received. */
public interface AttackEvent {

    /**
     * Action to execute when user receive an attack.
     * 
     * @param coordinates
     *            The {@link Coordinates} of the attack.
     */
    void onAttack(final Coordinates coordinates);

}
