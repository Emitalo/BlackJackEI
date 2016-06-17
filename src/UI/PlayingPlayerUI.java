package UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

import agent.GameTable;
import agent.Player;

import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import exception.OverTwentOneException;
import exception.TwentOneException;

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

	private DefaultTableModel tableTableModel;

	private JTable tableOfTableCards;
	private JTable tableOfPlayerCards;

	private DefaultTableModel playerTableModel;
	
	public PlayingPlayerUI(final Player player){
		this.player = player;
		
		getContentPane().setLayout(null);
		
		playerName = new JLabel("Nome");
		playerName.setBounds(117, 27, 100, 15);
		getContentPane().add(playerName);
		this.playerName.setText(player.playerName);
	
		message = new JLabel();
		message.setBounds(117, 0, 248, 22);
		getContentPane().add(message);
		
		String [] columnsTableCards = {"Mesa"};
		
		tableTableModel = new DefaultTableModel(null, columnsTableCards);			
		tableOfTableCards = new JTable(tableTableModel);
		tableOfTableCards.setBounds(245, 54, 160, 138);
		getContentPane().add(tableOfTableCards);
		
		String [] columnsPlayerCards = {player.playerName};
		
		playerTableModel = new DefaultTableModel(null, columnsPlayerCards);	
		tableOfPlayerCards = new JTable(playerTableModel);
		tableOfPlayerCards.setBounds(51, 54, 166, 138);
		getContentPane().add(tableOfPlayerCards);

		newCardBtn = new JButton("Pegar Carta");
		newCardBtn.setBounds(51, 204, 148, 25);
		getContentPane().add(newCardBtn);
		
		standBtn = new JButton("Não pegar mais");
		standBtn.setBounds(232, 204, 148, 25);
		getContentPane().add(standBtn);
		
		standBtn.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				player.stand();
				standBtn.setEnabled(false);
				newCardBtn.setEnabled(false);
			}
		} );
		
		newRoundBtn = new JButton("Nova Rodada");
		newRoundBtn.setBounds(152, 241, 148, 25);
		getContentPane().add(newRoundBtn);
		
		JLabel lblNewLabel = new JLabel("Mesa");
		lblNewLabel.setBounds(259, 26, 106, 17);
		getContentPane().add(lblNewLabel);
		
		newCardBtn.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				try{
					player.getNewCard();
				}catch (OverTwentOneException e){
					PlayingPlayerUI.this.update(PlayingPlayerUI.this.player.playerName + " passou de 21!");
					PlayingPlayerUI.this.enableOnlyNewRound();
					PlayingPlayerUI.this.player.stand();
				}catch(TwentOneException e){
					PlayingPlayerUI.this.update(PlayingPlayerUI.this.player.playerName + " fez 21! Ganhou!");
					PlayingPlayerUI.this.enableOnlyNewRound();
				}
			}
		} );
		
		newRoundBtn.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				this.removePlayerRows();
				this.removeTableRows();
				PlayingPlayerUI.this.update("Vez de " + PlayingPlayerUI.this.player.playerName);
				PlayingPlayerUI.this.update(getGraphics());
				PlayingPlayerUI.this.player.stand();
				player.startNewRound();
				newCardBtn.setEnabled(true);
				standBtn.setEnabled(true);
			}
			
			private void removePlayerRows() {
				int rows = playerTableModel.getRowCount();
				while(rows != 0){
					playerTableModel.removeRow(0);
					rows = playerTableModel.getRowCount();
				}
			}
			
			private void removeTableRows() {
				int rows = tableTableModel.getRowCount();
				while(rows != 0){
					tableTableModel.removeRow(0);
					rows = tableTableModel.getRowCount();
				}
			}

		} );
	}
	
	public void showPlayerCard(String card){
		String [] cards = new String[1];
		cards[0] = card;
		
		playerTableModel.addRow(cards);
	}
	
	public void update(String message){
		System.out.println("\nUpdating screen messagewith: " + message + "\n");
		this.message.setText(message);
		this.update(getGraphics());
	}
	
	public void addTableCard(String card){

		String [] cards = new String[1];
		cards[0] = card;
		
		tableTableModel.addRow(cards);
	}

	public void showGui(){
		pack();
		super.setBounds(100, 100, 450, 300);
		super.setVisible(true);
	}

	public void enableOnlyNewRound(){
		this.newCardBtn.setEnabled(false);
		this.standBtn.setEnabled(false);
	}
}
