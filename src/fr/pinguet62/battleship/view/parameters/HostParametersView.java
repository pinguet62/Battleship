package fr.pinguet62.battleship.view.parameters;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
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
import fr.pinguet62.battleship.view.WaitingView;
import fr.pinguet62.battleship.view.positioning.FleetPositioningView;

/**
 * View where host chose port of the {@link Socket}, the size of grid, and
 * {@link Boat} types with the number.
 */
public final class HostParametersView extends JFrame {

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
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	// Layout
	Container mainContainer = getContentPane();
	mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.Y_AXIS));

	// - Port
	JPanel portPanel = new JPanel();
	portPanel.setBorder(BorderFactory.createTitledBorder("Server"));
	portPanel.setLayout(new GridLayout(1, 2));
	mainContainer.add(portPanel);
	// -- Title
	JLabel portTitle = new JLabel("Port");
	portPanel.add(portTitle);
	// -- Value
	// 49152
	final JSpinner portValue = new JSpinner(new SpinnerNumberModel(49152,
		1, 65535, 1));
	portPanel.add(portValue);
	// - Size
	JPanel sizePanel = new JPanel();
	sizePanel.setBorder(BorderFactory.createTitledBorder("Size"));
	sizePanel.setLayout(new GridLayout(2, 2));
	mainContainer.add(sizePanel);
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
	mainContainer.add(fleetPanel);
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
	mainContainer.add(buttonPanel);
	// -- Ok
	JButton okButton = new JButton("Ok");
	okButton.addActionListener(new ActionListener() {
	    /** Click on "Ok" button. */
	    @Override
	    public void actionPerformed(final ActionEvent e) {
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

		// Game
		final Game game = new Game(PlayerType.HOST);
		game.init(new ParametersDto((int) valueWidthSize.getValue(),
			(int) valueHeightSize.getValue(), boatEntries));
		game.getHostSocketManager().setPort((Integer) portValue.getValue());

		// Next view
		dispose();
		final WaitingView waitConnexionView = new WaitingView(
			"Waiting guest connexion...");
		game.getHostSocketManager().waitClientConnection(new Runnable() {
		    /**
		     * Hide {@link WaitingView}.<br />
		     * Show {@link FleetPositioningView}.
		     */
		    @Override
		    public void run() {
			// Next view
			waitConnexionView.dispose();
			new FleetPositioningView(game);
		    }
		});
	    }
	});
	buttonPanel.add(okButton);

	pack();
	setVisible(true);
    }

}
