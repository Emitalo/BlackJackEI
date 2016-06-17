
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PlayingPlayerUI extends JFrame {

	private static final long serialVersionUID = 4505715441913836334L;
	
	private Player player;
	private JPanel cardsPanel;
	private JLabel playerName;
	private JLabel tabCards;
	private JLabel myCards;
	private JLabel tableCards;
	private JLabel message;
	
	public PlayingPlayerUI(Player player){
		this.player = player;
		
		getContentPane().setLayout(null);
		
		cardsPanel = new JPanel();
		cardsPanel.setBounds(93, 65, 287, 138);
		cardsPanel.setLayout(null);

		playerName = new JLabel("Nome");
		playerName.setBounds(121, 50, 100, 15);
		getContentPane().add(playerName);
		this.playerName.setText(player.playerName);
		
		tabCards = new JLabel("New label");
		tabCards.setBounds(167, 5, 110, 15);
		this.cardsPanel.add(tabCards);
		
		myCards = new JLabel("New label");
		myCards.setBounds(12, 5, 110, 15);
		this.cardsPanel.add(myCards);
		
		tableCards = new JLabel("Cartas da mesa");
		tableCards.setBounds(243, 50, 121, 15);
		getContentPane().add(tableCards);
		
		getContentPane().add(cardsPanel);
	}
	
	public void showPlayerCard(String card){
		this.myCards.setText(card);
		this.update(getGraphics());
	}
	
	public void showTableCard(String card){
		
		this.tabCards.setText(card);
		this.update(getGraphics());
	}
	
	public void update(String message){
		this.message.setText(message);
		this.update(getGraphics());
	}

	public void showGui(){
		pack();
		super.setBounds(100, 100, 450, 300);
		super.setVisible(true);
	}
}
