package fr.pinguet62.battleship;

import java.util.Arrays;

import fr.pinguet62.battleship.model.Game;
import fr.pinguet62.battleship.model.PlayerType;
import fr.pinguet62.battleship.model.boat.Submarine;
import fr.pinguet62.battleship.socket.dto.ParametersDto;
import fr.pinguet62.battleship.socket.dto.ParametersDto.BoatEntry;
import fr.pinguet62.battleship.view.game.GameView;

public final class Main {

    public static void main(final String[] args) {
	// new PlayerTypeView();

	Game game = new Game(PlayerType.HOST);
	game.init(new ParametersDto(8, 12, Arrays.asList(new BoatEntry(
		Submarine.class, 1), new BoatEntry(Submarine.class, 1))));
	new GameView(game);
    }
}
