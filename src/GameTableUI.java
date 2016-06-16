import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JLayeredPane;


public class GameTableUI extends JFrame{

	private JPanel panel;
	private JLabel tableLabel;
	private JLabel tableCard;
	private GameTable gameTable;
	private JLabel playerLabel;
	private JLabel tableCards;
	private JPanel tableCardsPanel;
	private JLabel playerCards;
	private JLabel turnMessage;

	public GameTableUI(GameTable gameTable){
		super();
		
		this.gameTable = gameTable;
		this.initialize();
		this.setVisible(true);

	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setBounds(100, 100, 300, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		tableLabel= new JLabel("Mesa");
		tableLabel.setBounds(122, 22, 69, 17);
		panel.add(tableLabel);

		tableCardsPanel = new JPanel();
		tableCardsPanel.setBounds(71, 169, 175, 89);
		tableCardsPanel.setLayout(null);

		tableCards = new JLabel("Cartas");
		tableCards.setBounds(42, 12, 120, 17);

		playerLabel = new JLabel("");
		playerLabel.setBounds(0, 0, 195, 17);
		tableCardsPanel.add(playerLabel);
		
		playerCards = new JLabel("");
		playerCards.setBounds(53, 29, 70, 15);
		
		tableCard = new JLabel("");
		tableCard.setBounds(91, 52, 115, 31);
		panel.add(tableCard);
		
		turnMessage = new JLabel("");
		turnMessage.setBounds(54, 12, 213, 15);
		panel.add(turnMessage);
	}
	
	public void showCard(String card){
		tableCardsPanel.add(tableCards);
		this.tableCard.setText(card);
		this.update(getGraphics());
	}

	public void showPlayer(String player) {
		panel.add(tableCardsPanel);
		this.playerLabel.setText(player);
		this.update(getGraphics());
	}
	
	public void showPlayerCards(String card) {
		tableCardsPanel.add(playerCards);
		this.playerCards.setText(card);
		this.update(getGraphics());
	}
}
