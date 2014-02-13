package fr.pinguet62.battleship.view.parameters;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import fr.pinguet62.battleship.Consumer;
import fr.pinguet62.battleship.model.Game;
import fr.pinguet62.battleship.model.PlayerType;
import fr.pinguet62.battleship.socket.dto.ParametersDto;
import fr.pinguet62.battleship.view.positioning.FleetPositioningView;

/** The view parameters for {@link PlayerType#GUEST}. */
public final class GuestParametersView extends JFrame {

    /** Serial version UID. */
    private static final long serialVersionUID = 4508008391795747860L;

    /** Constructor. */
    public GuestParametersView() {
	super("Guest");
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

	// - Button
	JPanel buttonPanel = new JPanel();
	buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
	mainContainer.add(buttonPanel);
	// -- Ok
	JButton okButton = new JButton("Ok");
	okButton.addActionListener(new ActionListener() {
	    /** Click on "Ok" button. */
	    @Override
	    public void actionPerformed(ActionEvent e) {
		final Game game = new Game(PlayerType.GUEST);
		game.getGuestSocketManager()
			.setPort((int) portValue.getValue());
		game.getGuestSocketManager().connectToHost(
		/** {@link ParametersDto} received. */
		new Consumer<ParametersDto>() {
		    /**
		     * Initialize {@link Game}.<br />
		     * Show {@link FleetPositioningView}.
		     */
		    @Override
		    public void accept(final ParametersDto parametersDto) {
			game.init(parametersDto);
			dispose();
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
