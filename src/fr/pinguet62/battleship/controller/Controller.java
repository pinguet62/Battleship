package fr.pinguet62.battleship.controller;

import fr.pinguet62.battleship.model.Game;
import fr.pinguet62.battleship.model.grid.Coordinates;
import fr.pinguet62.battleship.view.game.GameView;

public final class Controller {

	private Game model;

	private GameView view;

	public Controller(final Game model, final GameView view) {
		this.model = model;
		this.view = view;
	}

	public void receiveAttack(Coordinates coordinates) {
		// TODO
	}

}
