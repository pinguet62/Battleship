package fr.pinguet62.battleship.view;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import fr.pinguet62.battleship.model.Game;
import fr.pinguet62.battleship.model.grid.Box;
import fr.pinguet62.battleship.model.grid.Coordinates;

public final class View implements ActionListener {

	private BoxView[][] fleet;

	private JFrame frame = new JFrame("Battleship");

	private final Game game;

	public View(final Game model) {
		this.game = model;

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Layout
		Container mainContainer = frame.getContentPane();
		mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.X_AXIS));

		// - Fleet
		JPanel fleetPanel = new JPanel();
		fleetPanel.setLayout(new BoxLayout(fleetPanel, BoxLayout.Y_AXIS));
		mainContainer.add(fleetPanel);
		// -- Title
		JLabel fleetLabel = new JLabel("Adversaire");
		fleetPanel.add(fleetLabel);
		// -- Grid
		JPanel gridFleetPanel = new JPanel();
		gridFleetPanel.setLayout(new GridLayout(this.game.getHeight(),
				this.game.getWidth()));
		fleetPanel.add(gridFleetPanel);
		// --- Buttons
		for (int y = 0; y < this.game.getHeight(); y++) {
			for (int x = 0; x < this.game.getWidth(); x++) {
				BoxView button = new BoxView(new Coordinates(x, y));
				button.setBackground(BoxView.WATER);
				button.addActionListener(this);
				gridFleetPanel.add(button);
			}
		}

		JSeparator seperator = new JSeparator(SwingConstants.VERTICAL);
		mainContainer.add(seperator);

		// - Opponent
		JPanel opponentPanel = new JPanel();
		opponentPanel.setLayout(new BoxLayout(opponentPanel, BoxLayout.Y_AXIS));
		mainContainer.add(opponentPanel);
		// -- Title
		JLabel opponentLabel = new JLabel("Ma flotte");
		opponentPanel.add(opponentLabel);
		// -- Grid
		JPanel gridOpponenPanel = new JPanel();
		gridOpponenPanel.setLayout(new GridLayout(this.game.getHeight(),
				this.game.getWidth()));
		opponentPanel.add(gridOpponenPanel);
		// --- Buttons
		for (int y = 0; y < this.game.getHeight(); y++) {
			for (int x = 0; x < this.game.getWidth(); x++) {
				BoxView button = new BoxView(new Coordinates(x, y));
				button.setBackground(BoxView.WATER);
				button.setEnabled(false);
				gridOpponenPanel.add(button);
			}
		}

		frame.pack();
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		BoxView boxView = (BoxView) e.getSource();
		Coordinates coordinates = boxView.getCoordinates();
		boolean touched = this.game.getFleet().getBox(coordinates).attack();
		boxView.setBackground(touched ? BoxView.TOUCHED : BoxView.FAILED);
		boxView.setEnabled(false);
	}

	public void refreshFleet(final Coordinates coordinates) {
		if (coordinates == null) {
			throw new IllegalArgumentException("Coordinates null.");
		}
		for (BoxView[] boxViews : fleet) {
			for (BoxView boxView : boxViews) {
				if (coordinates.equals(boxView.getCoordinates())) {
					Box target = this.game.getFleet().getBox(coordinates);
					Color color = BoxView.getColor(target);
					boxView.setBackground(color);
					return;
				}
			}
		}
		throw new IllegalArgumentException("Coordinates not found.");
	}

}
