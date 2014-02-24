package fr.pinguet62.battleship.view.parameters;

import java.awt.GridLayout;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import fr.pinguet62.battleship.model.Game;
import fr.pinguet62.battleship.model.PlayerType;
import fr.pinguet62.battleship.model.boat.AircraftCarrier;
import fr.pinguet62.battleship.model.boat.Boat;
import fr.pinguet62.battleship.model.boat.Cruiser;
import fr.pinguet62.battleship.model.boat.Destroyer;
import fr.pinguet62.battleship.model.boat.Submarine;
import fr.pinguet62.battleship.model.boat.TorpedoBoat;
import fr.pinguet62.battleship.socket.dto.ParametersDto;
import fr.pinguet62.battleship.socket.dto.ParametersDto.BoatEntry;
import fr.pinguet62.battleship.view.Frame;
import fr.pinguet62.battleship.view.WaitingView;
import fr.pinguet62.battleship.view.positioning.FleetPositioningView;

/**
 * View where host chose port of the {@link Socket}, the size of grid, and
 * {@link Boat} types with the number.
 */
public final class HostParametersView extends Frame {

    /** Serial version UID. */
    private static final long serialVersionUID = -7022220519762450381L;

    /** The available {@link Boat} types. */
    private final Collection<Class<? extends Boat>> boatClasses = new LinkedHashSet<>(
	    Arrays.asList(AircraftCarrier.class, Cruiser.class,
		    Destroyer.class, Submarine.class, TorpedoBoat.class));

    /** The {@link BoatClassSpinner}s. */
    private final Collection<BoatClassSpinner> boatClassSpinners = new ArrayList<>();

    /** Constructor. */
    public HostParametersView() {
	super("Battleship");
	setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

	// - Server
	JPanel serverPanel = new JPanel();
	serverPanel.setBorder(BorderFactory.createTitledBorder("Server"));
	serverPanel.setLayout(new GridLayout(1, 2));
	add(serverPanel);
	// -- Title
	JLabel portTitle = new JLabel("Port");
	serverPanel.add(portTitle);
	// -- Value
	// 49152
	final JSpinner portValue = new JSpinner(new SpinnerNumberModel(49152,
		1, 65535, 1));
	serverPanel.add(portValue);
	// - Size
	JPanel sizePanel = new JPanel();
	sizePanel.setBorder(BorderFactory.createTitledBorder("Size"));
	sizePanel.setLayout(new GridLayout(2, 2));
	add(sizePanel);
	// -- Width
	// --- Title
	JLabel titleWidthSize = new JLabel("Width");
	sizePanel.add(titleWidthSize);
	// --- Value
	final JSpinner valueWidthSize = new JSpinner(new SpinnerNumberModel(10,
		1, Integer.MAX_VALUE, 1));
	sizePanel.add(valueWidthSize);
	// -- Height
	// --- Title
	JLabel titleHeightSize = new JLabel("Height");
	sizePanel.add(titleHeightSize);
	// --- Value
	final JSpinner valueHeightSize = new JSpinner(new SpinnerNumberModel(
		10, 1, Integer.MAX_VALUE, 1));
	sizePanel.add(valueHeightSize);
	// - Fleet
	JPanel fleetPanel = new JPanel();
	fleetPanel.setBorder(BorderFactory.createTitledBorder("Fleet"));
	fleetPanel.setLayout(new GridLayout(boatClasses.size(), 2));
	add(fleetPanel);
	for (Class<? extends Boat> boatClass : boatClasses) {
	    // Label
	    Boat boat = Boat.getInstance(boatClass);
	    JLabel nameBoatFleet = new JLabel(String.format("%s (x%d)",
		    boat.getName(), boat.getSize()));
	    fleetPanel.add(nameBoatFleet);
	    // Spinner
	    BoatClassSpinner boatClassSpinner = new BoatClassSpinner(boatClass);
	    boatClassSpinners.add(boatClassSpinner);
	    fleetPanel.add(boatClassSpinner);
	}
	// - Button
	JPanel buttonPanel = new JPanel();
	buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
	add(buttonPanel);
	// -- Ok
	JButton okButton = new JButton("Ok");
	okButton.addActionListener((event) -> {
	    /** Click on "Ok" button. */
	    // Validation
	    int nbBoats = 0;
	    for (BoatClassSpinner boatClassSpinner : boatClassSpinners)
		nbBoats += boatClassSpinner.getIntValue();
	    if (nbBoats == 0)
		return;

	    // Boat entries
	    Collection<BoatEntry> boatEntries = new ArrayList<>();
	    for (BoatClassSpinner boatClassSpinner : boatClassSpinners) {
		int number = boatClassSpinner.getIntValue();
		if (0 < number) {
		    BoatEntry boatEntry = new BoatEntry(boatClassSpinner
			    .getBoatClass(), boatClassSpinner.getIntValue());
		    boatEntries.add(boatEntry);
		}
	    }

	    // Game initialization
	    Game game = new Game(PlayerType.HOST);
	    game.init(new ParametersDto((int) valueWidthSize.getValue(),
		    (int) valueHeightSize.getValue(), boatEntries));

	    // Next view: WaitingView
	    dispose();
	    WaitingView waitConnexionView = new WaitingView(
		    "Waiting guest connexion...");

	    // Run host server
	    game.getSocketManager().setPort((Integer) portValue.getValue());
	    game.getSocketManager().connect(() -> {
		/** {@link Runnable} to execute after guest connection. */
		// Send parameters to guest
		    ParametersDto parameters = new ParametersDto(game
			    .getWidth(), game.getHeight(), game
			    .getBoatEntries());
		    game.getSocketManager().send(parameters);

		    // Next view: FleetPositioningView
		    waitConnexionView.dispose();
		    new FleetPositioningView(game);
		});
	});
	buttonPanel.add(okButton);

	setVisible(true);
    }
}
