package fr.pinguet62.battleship.controller;

import fr.pinguet62.battleship.model.Game;
import fr.pinguet62.battleship.model.grid.Coordinates;
import fr.pinguet62.battleship.view.View;

public final class Controller {

	private Game model;

	private View view;

	public Controller(final Game model, final View view) {
		this.model = model;
		this.view = view;
	}

	public void receiveAttack(Coordinates coordinates) {
		// TODO
	}

}
