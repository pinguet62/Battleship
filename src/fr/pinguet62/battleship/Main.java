package fr.pinguet62.battleship;

import java.util.Arrays;

import fr.pinguet62.battleship.model.boat.AircraftCarrier;
import fr.pinguet62.battleship.model.boat.Destroyer;
import fr.pinguet62.battleship.model.boat.Submarine;
import fr.pinguet62.battleship.model.boat.TorpedoBoat;
import fr.pinguet62.battleship.view.positioning.FleetPositioningView;

public final class Main {

	public static void main(String[] args) {
		// new ParametersView();

		new FleetPositioningView(15, 10, Arrays.asList(new AircraftCarrier(),
				new Destroyer(), new Destroyer(), new Submarine(),
				new TorpedoBoat()));
	}
}
