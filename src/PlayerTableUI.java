import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import jade.core.AID;
import javax.swing.JLabel;


public class PlayerTableUI extends JFrame{

	private JPanel panel;
	private JPanel tablePanel;
	
	public PlayerTableUI(Player myAgent, AID gameTable) {
		this.setBounds(100, 100, 1000, 1000);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel = new JPanel();
		panel.setLayout(null);
		
		JLabel lblOla = new JLabel("Olá " + myAgent.playerName);
		lblOla.setBounds(12, 12, 70, 15);
		panel.add(lblOla);
		
		this.getContentPane().add(panel, BorderLayout.CENTER);
		
	}

	public void showGui() {
//		GameTableUI gt = new GameTableUI();
//		tablePanel = gt.includeGui();
		tablePanel.setBounds(100, 100, 500, 500);
		panel.add(tablePanel);
		this.setVisible(true);
	}
}
