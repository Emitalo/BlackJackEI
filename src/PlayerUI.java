

import jade.core.behaviours.OneShotBehaviour;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class PlayerUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4213113937500262395L;

	private Player myAgent;
	private JPanel p;

	public PlayerUI(Player player2) {
		super();
		
		myAgent = player2;
		
		p = new JPanel();
		p.setLayout(new GridLayout(2, 2));
		
		JButton addButton = new JButton("Procurar mesa");
		addButton.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				myAgent.joinTable();
			    
			}
		} );
		p.add(addButton);
		getContentPane().add(p, BorderLayout.SOUTH);
			
		setResizable(false);
	}
	
	public void showGui() {
		pack();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int centerX = (int)screenSize.getWidth() / 2;
		int centerY = (int)screenSize.getHeight() / 2;
		setLocation(centerX - getWidth() / 2, centerY - getHeight() / 2);
		super.setVisible(true);
	}

}
