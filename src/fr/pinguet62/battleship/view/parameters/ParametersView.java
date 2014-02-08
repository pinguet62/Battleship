package fr.pinguet62.battleship.view.parameters;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import fr.pinguet62.battleship.model.boat.AircraftCarrier;
import fr.pinguet62.battleship.model.boat.Boat;
import fr.pinguet62.battleship.model.boat.Cruiser;
import fr.pinguet62.battleship.model.boat.Destroyer;
import fr.pinguet62.battleship.model.boat.Submarine;
import fr.pinguet62.battleship.model.boat.TorpedoBoat;
import fr.pinguet62.battleship.view.positioning.FleetPositioningView;

/** View where user chose {@link Boat} types and the number. */
public final class ParametersView {

    /** The available {@link Boat} types. */
    private final Collection<Class<? extends Boat>> boatClasses = Arrays
	    .asList(AircraftCarrier.class, Cruiser.class, Destroyer.class,
		    TorpedoBoat.class, Submarine.class);

    private final Collection<BoatClassSpinner> boatClassSpinners = new ArrayList<>();

    /** Constructor. */
    public ParametersView() {
	final JFrame frame = new JFrame("Battleship");
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	// Layout
	Container mainContainer = frame.getContentPane();
	mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.Y_AXIS));

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
	    JLabel nameBoatFleet = new JLabel(boatClass.getSimpleName());
	    fleetPanel.add(nameBoatFleet);
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
		    for (int i = 0; i < boatClassSpinner.getIntValue(); i++)
			try {
			    Class<? extends Boat> boatClass = boatClassSpinner
				    .getBoatClass();
			    Constructor<? extends Boat> defaultConstructor = boatClass
				    .getConstructor();
			    Boat boat = defaultConstructor.newInstance();
			    boats.add(boat);
			} catch (NoSuchMethodException | SecurityException
				| InstantiationException
				| IllegalAccessException
				| IllegalArgumentException
				| InvocationTargetException exception) {
			    exception.printStackTrace();
			}

		new FleetPositioningView((Integer) valueWidthSize.getValue(),
			(Integer) valueHeightSize.getValue(), boats);
		// Game model = new Game((Integer) valueWidthSize.getValue(),
		// (Integer) valueHeightSize.getValue(), null);
		// View view = new View(model);
		// new Controller(model, view);
		frame.dispose();
	    }
	});
	buttonsPanel.add(okButton);
	// -- Cancel
	JButton quitButton = new JButton("Quit");
	quitButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(final ActionEvent e) {
		frame.dispose();
	    }
	});
	buttonsPanel.add(quitButton);

	frame.pack();
	frame.setVisible(true);
    }

}
