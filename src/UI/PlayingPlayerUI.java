package UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import agent.Player;

import javax.swing.JButton;

public class PlayingPlayerUI extends JFrame {

	private static final long serialVersionUID = 4505715441913836334L;
	
	private Player player;
	private JPanel cardsPanel;
	private JLabel playerName;
	private JLabel tabCards;
	private JLabel myCards;
	private JLabel tableCards;
	private JLabel message;

	private JButton newCardBtn;
	private JButton standBtn;
	private JButton newRoundBtn;
	
	public PlayingPlayerUI(final Player player){
		this.player = player;
		
		getContentPane().setLayout(null);
		
		cardsPanel = new JPanel();
		cardsPanel.setBounds(93, 54, 287, 138);
		cardsPanel.setLayout(null);

		playerName = new JLabel("Nome");
		playerName.setBounds(117, 27, 100, 15);
		getContentPane().add(playerName);
		this.playerName.setText(player.playerName);
		
		tabCards = new JLabel("");
		tabCards.setBounds(167, 5, 110, 15);
		this.cardsPanel.add(tabCards);
		
		myCards = new JLabel("");
		myCards.setBounds(12, 5, 110, 15);
		this.cardsPanel.add(myCards);
		
		tableCards = new JLabel("Cartas da mesa");
		tableCards.setBounds(244, 27, 121, 15);
		getContentPane().add(tableCards);
		
		getContentPane().add(cardsPanel);
		
		newCardBtn = new JButton("Pegar Carta");
		newCardBtn.setBounds(51, 204, 148, 25);
		getContentPane().add(newCardBtn);
		
		standBtn = new JButton("Não pegar mais");
		standBtn.setBounds(232, 204, 148, 25);
		getContentPane().add(standBtn);
		
		newRoundBtn = new JButton("Nova Rodada");
		newRoundBtn.setBounds(152, 241, 148, 25);
		getContentPane().add(newRoundBtn);
		
		newCardBtn.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				player.getNewCard();
			}
		} );
	}
	
	public void showPlayerCard(String card){
		String cards = this.myCards.getText();
		this.myCards.setText(cards + "\n" + card);
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
