package fr.pinguet62.battleship.view.parameters;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.text.MaskFormatter;

import fr.pinguet62.battleship.model.Game;
import fr.pinguet62.battleship.model.PlayerType;
import fr.pinguet62.battleship.socket.dto.ParametersDto;
import fr.pinguet62.battleship.view.Frame;
import fr.pinguet62.battleship.view.WaitingView;
import fr.pinguet62.battleship.view.positioning.FleetPositioningView;
import fr.pinguet62.utils.Consumer;

/** The view parameters for {@link PlayerType#GUEST}. */
public final class GuestParametersView extends Frame {

    /** Serial version UID. */
    private static final long serialVersionUID = 4508008391795747860L;

    /** This for {@link JOptionPane} alerts. */
    private final JFrame parent = this;

    /** Constructor. */
    public GuestParametersView() {
	super("Guest");
	setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

	// - Server
	JPanel serverPanel = new JPanel();
	serverPanel.setBorder(BorderFactory.createTitledBorder("Server"));
	serverPanel.setLayout(new GridLayout(2, 2));
	add(serverPanel);
	// -- IP
	// --- Title
	JLabel ipTitle = new JLabel("IP");
	serverPanel.add(ipTitle);
	// --- Value
	MaskFormatter ipMaskFormatter = null;
	try {
	    ipMaskFormatter = new MaskFormatter("###.###.###.###");
	} catch (ParseException e) {
	}
	final JFormattedTextField ipValue = new JFormattedTextField(
		ipMaskFormatter);
	try {
	    ipValue.setText(InetAddress.getLocalHost().getHostAddress());
	} catch (UnknownHostException e) {
	}
	serverPanel.add(ipValue);
	// -- Port
	// --- Title
	JLabel portTitle = new JLabel("Port");
	serverPanel.add(portTitle);
	// --- Value
	final JSpinner portValue = new JSpinner(new SpinnerNumberModel(49152,
		1, 65535, 1));
	serverPanel.add(portValue);
	// - Button
	JPanel buttonPanel = new JPanel();
	buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
	add(buttonPanel);
	// -- Ok
	JButton okButton = new JButton("Ok");
	okButton.addActionListener(new ActionListener() {
	    /** Click on "Ok" button. */
	    @Override
	    public void actionPerformed(final ActionEvent e) {
		// Validation
		InetAddress inetAddress = null;
		try {
		    inetAddress = InetAddress.getByName(ipValue.getText());
		} catch (UnknownHostException exception) {
		    JOptionPane.showMessageDialog(parent,
			    "Invalid IP address.", "Error",
			    JOptionPane.ERROR_MESSAGE);
		    return;
		}

		// Next view: WaitingView
		dispose();
		final WaitingView waitParametersView = new WaitingView(
			"Waiting host parameters...");

		// Game
		final Game game = new Game(PlayerType.GUEST);
		game.getSocketManager().setInetAddress(inetAddress);
		game.getSocketManager().setPort((int) portValue.getValue());
		game.getSocketManager().connect(new Runnable() {
		    /** Method to execute after connection to host. */
		    @Override
		    public void run() {
			game.getSocketManager()
				.setOnParametersReceivedListener(
					new Consumer<ParametersDto>() {
					    /**
					     * Method to execute after
					     * {@link ParametersDto} reception.
					     */
					    @Override
					    public void accept(
						    final ParametersDto parametersDto) {
						// Game initialization
						game.init(parametersDto);

						// Next view:
						// FleetPositioningView
						waitParametersView.dispose();
						new FleetPositioningView(game);
					    }
					});
		    }
		});
	    }
	});
	buttonPanel.add(okButton);

	setVisible(true);
    }
}
