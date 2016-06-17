

import jade.core.AID;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.management.MalformedObjectNameException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JList;

public class PlayerUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4213113937500262395L;

	private Player myAgent;
	private JPanel p;
	private JLabel message;
	private JButton findTableBtn;
	private JLabel playerName;

	private JTextField name;
	private JPanel cardsPanel;
	private JPanel panel;
	private JLabel tableCards;
	private JList list;

	private JLabel tabCards;

	private JLabel myCards;

	private JLabel turnMessage;

	public PlayerUI(Player player2) {
		super();
		myAgent = player2;
		
		p = new JPanel();
		p.setBounds(0, 250, 450, 50);
		p.setLayout(new GridLayout(2, 2));
		getContentPane().setLayout(null);
		getContentPane().add(p);
		
		name = new JTextField();
		name.setBounds(169, 45, 225, 25);
		getContentPane().add(name);
		findTableBtn = new JButton("Procurar mesa");
		findTableBtn.setBounds(0, 194, 450, 25);
		getContentPane().add(findTableBtn);
		
		message = new JLabel("");
		message.setBounds(12, 128, 450, 25);
		getContentPane().add(message);
		
//		cardsPanel = new JPanel();
//		cardsPanel.setBounds(93, 65, 287, 138);
//		cardsPanel.setLayout(null);
//
		playerName = new JLabel("Nome");
		playerName.setBounds(121, 50, 100, 15);
		getContentPane().add(playerName);
//		
//		tabCards = new JLabel("New label");
//		tabCards.setBounds(167, 5, 110, 15);
//		
//		myCards = new JLabel("New label");
//		myCards.setBounds(12, 5, 110, 15);
//		
//		tableCards = new JLabel("Cartas da mesa");
//		tableCards.setBounds(243, 50, 121, 15);

//		turnMessage = new JLabel("");
//		turnMessage.setBounds(54, 12, 213, 15);
//		panel.add(turnMessage);
		
		findTableBtn.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				myAgent.joinTable(name.getText());
			}
		} );
		
		setResizable(false);
	}
	
	public void showGui() {
		pack();
		super.setBounds(100, 100, 450, 300);
		super.setVisible(true);
	}

	public void update(String message, boolean showCards){
		this.remove(findTableBtn);
		this.remove(name);
		this.remove(this.message);
		this.playerName.setText(name.getText());
		getContentPane().add(cardsPanel);
		this.update(getGraphics());
	}
	
	public void update(String message){
		this.message.setText(message);
		this.update(getGraphics());
	}
}
