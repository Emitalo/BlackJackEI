import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class GameTableUI extends JFrame{

	private JPanel panel;
	private JLabel tableLabel;
	private JLabel tableCard;
	private GameTable gameTable;
	private JLabel playerLabel;

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
		tableLabel.setBounds(120, 12, 69, 17);
		panel.add(tableLabel);

		JPanel tableCardsPanel = new JPanel();
		tableCardsPanel.setBounds(51, 51, 195, 141);
		panel.add(tableCardsPanel);
		tableCardsPanel.setLayout(null);
		
		tableCard = new JLabel("");
		tableCard.setBounds(42, 12, 120, 17);
		tableCardsPanel.add(tableCard);
		
		playerLabel = new JLabel("");
		playerLabel.setBounds(51, 34, 195, 17);
		panel.add(playerLabel);
	}
	
	public void showCard(String player, String card){
		this.playerLabel.setText(player);
		this.showCard(card);
	}
	
	public void showCard(String card){
		this.tableCard.setText(card);
		this.update(getGraphics());
	}
}
