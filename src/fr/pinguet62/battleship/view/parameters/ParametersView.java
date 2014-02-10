package fr.pinguet62.battleship.view.parameters;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import fr.pinguet62.battleship.model.boat.AircraftCarrier;
import fr.pinguet62.battleship.model.boat.Boat;
import fr.pinguet62.battleship.model.boat.Cruiser;
import fr.pinguet62.battleship.model.boat.Destroyer;
import fr.pinguet62.battleship.model.boat.Submarine;
import fr.pinguet62.battleship.model.boat.TorpedoBoat;
import fr.pinguet62.battleship.view.positioning.FleetPositioningView;

/** View where user chose {@link Boat} types and the number. */
public final class ParametersView extends JFrame {

    /** Serial version UID. */
    private static final long serialVersionUID = -7022220519762450381L;

    /** The available {@link Boat} types. */
    private final Collection<Class<? extends Boat>> boatClasses = new LinkedHashSet<>(
	    Arrays.asList(AircraftCarrier.class, Cruiser.class,
		    Destroyer.class, Submarine.class, TorpedoBoat.class));

    /** The {@link BoatClassSpinner}s. */
    private final Collection<BoatClassSpinner> boatClassSpinners = new ArrayList<>();

    /** Constructor. */
    public ParametersView() {
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
	// - Buttons
	JPanel buttonsPanel = new JPanel();
	buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
	mainContainer.add(buttonsPanel);
	// -- Ok
	JButton okButton = new JButton("Ok");
	okButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(final ActionEvent event) {
		// Validation
		int nbBoats = 0;
		for (BoatClassSpinner boatClassSpinner : boatClassSpinners)
		    nbBoats += boatClassSpinner.getIntValue();
		if (nbBoats == 0)
		    return;

		Collection<Boat> boats = new ArrayList<>();
		for (BoatClassSpinner boatClassSpinner : boatClassSpinners)
		    for (int i = 0; i < boatClassSpinner.getIntValue(); i++) {
			Class<? extends Boat> boatClass = boatClassSpinner
				.getBoatClass();
			Boat boat = Boat.getInstance(boatClass);
			boats.add(boat);
		    }

		// Next view
		Game game = new Game((Integer) valueWidthSize.getValue(),
			(Integer) valueHeightSize.getValue());
		game.getThreadSocket().setPort((Integer) portValue.getValue());
		new FleetPositioningView(game, boats);
		dispose();
	    }
	});
	buttonsPanel.add(okButton);
	// -- Cancel
	JButton quitButton = new JButton("Quit");
	quitButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(final ActionEvent e) {
		dispose();
	    }
	});
	buttonsPanel.add(quitButton);

	pack();
	setVisible(true);
    }

}
