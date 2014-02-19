package fr.pinguet62.battleship.view.game;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import fr.pinguet62.battleship.model.Game;
import fr.pinguet62.battleship.model.Score;
import fr.pinguet62.battleship.model.grid.Box.AttackResult;
import fr.pinguet62.battleship.model.grid.Coordinates;
import fr.pinguet62.battleship.socket.dto.AttackDto;
import fr.pinguet62.battleship.view.Frame;
import fr.pinguet62.battleship.view.game.BoxView.State;
import fr.pinguet62.utils.Consumer;

/** Duel view. */
public final class GameView extends Frame implements ActionListener {

    /** Serial version UID. */
    private static final long serialVersionUID = -2190498449403789762L;

    private final JLabel boatScoreHeaderMyfleet;

    private final JLabel boatScoreHeaderOpponentfleet;

    private final JLabel boxScoreHeaderMyfleet;

    private final JLabel boxScoreHeaderOpponentfleet;

    /** The {@link Game}. */
    private final Game game;

    /** {@link BoxView} of my fleet. */
    private final BoxView[][] myBoxViewss;

    /** If it my turn to play. */
    private boolean myTurn;

    /** {@link BoxView} of he opponent. */
    private final BoxView[][] opponentBoxViewss;

    /**
     * Constructor.
     * 
     * @param game
     *            The {@link Game}.
     */
    public GameView(final Game game) {
	super("Battleship");

	this.game = game;
	myTurn = game.getPlayerType().isHost();
	game.getSocketManager().setOnAttackReceivedListener(
		new Consumer<AttackDto>() {
		    /** {@link AttackDto} received. */
		    @Override
		    public void accept(final AttackDto attackDto) {
			Coordinates coordinates = attackDto.getCoordinates();

			// Update my fleet
			AttackResult attackResult = game.getMyFleet()
				.getBox(coordinates).attack();
			State newState = attackResult
				.equals(AttackResult.FAILED) ? State.FAILED
				: State.TOUCHED;
			myBoxViewss[coordinates.getY()][coordinates.getX()]
				.setState(newState);

			updateScores();

			myTurn = true;
		    }
		});

	// Layout
	setLayout(new GridLayout(1, 2, 5, 0));

	// - Opponent fleet
	JPanel panelOpponentfleet = new JPanel();
	panelOpponentfleet.setLayout(new BoxLayout(panelOpponentfleet,
		BoxLayout.Y_AXIS));
	add(panelOpponentfleet);
	// -- Header
	JPanel panelHeaderOpponentfleet = new JPanel();
	panelHeaderOpponentfleet.setLayout(new BoxLayout(
		panelHeaderOpponentfleet, BoxLayout.Y_AXIS));
	panelOpponentfleet.add(panelHeaderOpponentfleet);
	// --- Title
	JLabel titleHeaderOpponentfleet = new JLabel("Adversaire");
	panelHeaderOpponentfleet.add(titleHeaderOpponentfleet);
	// --- Score
	JPanel panelScoreHeaderOpponentFleet = new JPanel();
	panelScoreHeaderOpponentFleet.setLayout(new GridLayout(1, 2));
	panelHeaderOpponentfleet.add(panelScoreHeaderOpponentFleet);
	// ---- Box
	boxScoreHeaderOpponentfleet = new JLabel();
	panelScoreHeaderOpponentFleet.add(boxScoreHeaderOpponentfleet);
	// ---- Boat
	boatScoreHeaderOpponentfleet = new JLabel();
	panelScoreHeaderOpponentFleet.add(boatScoreHeaderOpponentfleet);
	// -- Grid
	JPanel gridFleetPanel = new JPanel();
	gridFleetPanel.setLayout(new GridLayout(game.getHeight(), game
		.getWidth()));
	panelOpponentfleet.add(gridFleetPanel);
	// --- Buttons
	opponentBoxViewss = new BoxView[game.getHeight()][game.getWidth()];
	for (int y = 0; y < game.getHeight(); y++)
	    for (int x = 0; x < game.getWidth(); x++) {
		BoxView button = new BoxView(new Coordinates(x, y));
		button.addActionListener(this);
		gridFleetPanel.add(button);
		opponentBoxViewss[y][x] = button;
	    }

	// - My fleet
	JPanel panelMyfleet = new JPanel();
	panelMyfleet.setLayout(new BoxLayout(panelMyfleet, BoxLayout.Y_AXIS));
	add(panelMyfleet);
	// -- Header
	JPanel panelHeaderMyfleet = new JPanel();
	panelHeaderMyfleet.setLayout(new BoxLayout(panelHeaderMyfleet,
		BoxLayout.Y_AXIS));
	panelMyfleet.add(panelHeaderMyfleet);
	// --- Title
	JLabel titleHeaderMyfleet = new JLabel("Moi");
	panelHeaderMyfleet.add(titleHeaderMyfleet);
	// --- Score
	JPanel panelScoreHeaderMyFleet = new JPanel();
	panelScoreHeaderMyFleet.setLayout(new GridLayout(1, 2));
	panelHeaderMyfleet.add(panelScoreHeaderMyFleet);
	// ---- Box
	boxScoreHeaderMyfleet = new JLabel();
	panelScoreHeaderMyFleet.add(boxScoreHeaderMyfleet);
	// ---- Boat
	boatScoreHeaderMyfleet = new JLabel();
	panelScoreHeaderMyFleet.add(boatScoreHeaderMyfleet);
	// -- Grid
	JPanel gridOpponenPanel = new JPanel();
	gridOpponenPanel.setLayout(new GridLayout(game.getHeight(), game
		.getWidth()));
	panelMyfleet.add(gridOpponenPanel);
	// --- Buttons
	myBoxViewss = new BoxView[game.getHeight()][game.getWidth()];
	for (int y = 0; y < game.getHeight(); y++)
	    for (int x = 0; x < game.getWidth(); x++) {
		BoxView button = new BoxView(new Coordinates(x, y));
		button.setEnabled(false);
		gridOpponenPanel.add(button);
		myBoxViewss[y][x] = button;
	    }

	updateScores();
	setVisible(true);
    }

    /**
     * Click on a {@link BoxView}.
     * 
     * @param event
     *            The {@link ActionEvent} with the {@link BoxView} source.
     */
    @Override
    public void actionPerformed(final ActionEvent event) {
	if (!myTurn)
	    return;

	// Update opponent fleet
	BoxView boxView = (BoxView) event.getSource();
	Coordinates coordinates = boxView.getCoordinates();
	AttackResult attackResult = game.getOpponentFleet().getBox(coordinates)
		.attack();
	boxView.setState(attackResult.equals(AttackResult.FAILED) ? State.FAILED
		: State.TOUCHED);

	updateScores();

	game.getSocketManager().send(new AttackDto(coordinates));

	myTurn = false;
    }

    /** Update {@link Score} of 2 players. */
    private void updateScores() {
	Score opponentScore = game.getOpponentFleet().getScore();
	boxScoreHeaderOpponentfleet.setText(String.format("Box : %d/%d",
		opponentScore.getActual(), opponentScore.getTotal()));
	boatScoreHeaderOpponentfleet.setText("");
	if (opponentScore.isWin()) {
	    end("Winner!");
	    return;
	}

	Score myScore = game.getMyFleet().getScore();
	boxScoreHeaderMyfleet.setText(String.format("Box : %d/%d",
		myScore.getActual(), myScore.getTotal()));
	boatScoreHeaderMyfleet.setText("");
	if (myScore.isWin()) {
	    end("Looser!");
	    return;
	}
    }

    /**
     * Show result and quit game.
     * 
     * @param message
     *            The message to show.
     */
    private void end(final String message) {
	dispose();
	JOptionPane.showMessageDialog(this, message, "End",
		JOptionPane.INFORMATION_MESSAGE);
	game.getSocketManager().stop();
    }

}
