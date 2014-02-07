package fr.pinguet62.battleship.view.parameters;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import fr.pinguet62.battleship.controller.Controller;
import fr.pinguet62.battleship.model.Game;
import fr.pinguet62.battleship.model.boat.AircraftCarrier;
import fr.pinguet62.battleship.model.boat.Cruiser;
import fr.pinguet62.battleship.model.boat.Destroyer;
import fr.pinguet62.battleship.model.boat.Submarine;
import fr.pinguet62.battleship.model.boat.TorpedoBoat;
import fr.pinguet62.battleship.view.View;

public final class ParametersView {

	private final Collection<BoatEntry> boatEntries = Arrays.asList(
			new BoatEntry(AircraftCarrier.class), new BoatEntry(Cruiser.class),
			new BoatEntry(Destroyer.class), new BoatEntry(TorpedoBoat.class),
			new BoatEntry(Submarine.class));

	private JFrame frame = new JFrame("Battleship");

	private final JSpinner valueHeightSize;

	private final JSpinner valueWidthSize;

	public ParametersView() {
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
		valueWidthSize = new JSpinner(new SpinnerNumberModel(10, 1,
				Integer.MAX_VALUE, 1));
		sizePanel.add(valueWidthSize);
		// -- Height
		// --- Title
		JLabel titleHeightSize = new JLabel("Height");
		sizePanel.add(titleHeightSize);
		// --- Value
		valueHeightSize = new JSpinner(new SpinnerNumberModel(10, 1,
				Integer.MAX_VALUE, 1));
		sizePanel.add(valueHeightSize);
		// - Fleet
		JPanel fleetPanel = new JPanel();
		fleetPanel.setBorder(BorderFactory.createTitledBorder("Fleet"));
		fleetPanel.setLayout(new GridLayout(boatEntries.size(), 2));
		mainContainer.add(fleetPanel);
		for (BoatEntry boatEntry : boatEntries) {
			Class<?> boatClass = boatEntry.getBoatClass();
			JLabel nameBoatFleet = new JLabel(boatClass.getSimpleName());
			fleetPanel.add(nameBoatFleet);
			JSpinner numberBoatFleet = new JSpinner(new SpinnerNumberModel(0,
					0, Integer.MAX_VALUE, 1));
			fleetPanel.add(numberBoatFleet);
		}
		// - Buttons
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
		mainContainer.add(buttonsPanel);
		// -- Ok
		JButton okButton = new JButton("Ok");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Game model = new Game((Integer) valueWidthSize.getValue(),
						(Integer) valueHeightSize.getValue(), null);
				View view = new View(model);
				new Controller(model, view);
				frame.dispose();
			}
		});
		buttonsPanel.add(okButton);
		// -- Cancel
		JButton quitButton = new JButton("Quit");
		quitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		buttonsPanel.add(quitButton);

		frame.pack();
		frame.setVisible(true);
	}
}
