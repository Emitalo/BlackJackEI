

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

	public PlayerUI(Player player2) {
		super();
		myAgent = player2;
		
		p = new JPanel();
		p.setBounds(0, 250, 450, 50);
		p.setLayout(new GridLayout(2, 2));
		getContentPane().setLayout(null);
		getContentPane().add(p);
		
		name = new JTextField();
		name.setBounds(120, 78, 225, 25);
		getContentPane().add(name);
		findTableBtn = new JButton("Procurar mesa");
		findTableBtn.setBounds(0, 194, 450, 25);
		getContentPane().add(findTableBtn);
		
		message = new JLabel("");
		message.setBounds(12, 128, 450, 25);
		getContentPane().add(message);
		
		playerName = new JLabel("Nome");
		playerName.setBounds(42, 83, 70, 15);
		getContentPane().add(playerName);
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
		this.update(message);
		this.remove(findTableBtn);
		this.remove(name);
		this.playerName.setText("Olá " + name.getText());
		this.update(getGraphics());
	}
	
	public void showCard(String card){
		this.message.setText(card);
		this.update(getGraphics());
	}
	
	public void update(String message){
		this.message.setText(message);
		this.update(getGraphics());
	}
	
}
