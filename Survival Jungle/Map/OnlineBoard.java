package Map;

import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.net.InetAddress;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import Main.MenuPanel;
import Network.Network;
import Network.Server.Client;

@SuppressWarnings("serial")
public class OnlineBoard extends JPanel {
	
	private static final int WIDTH = 250;
	private static final int HEIGHT = 200;
	private String playerName;
	private JPanel panel;
	private JTextField text;
	int xx, xy;
	

	public OnlineBoard(MenuPanel bgPanel, JFrame jf){
		setBackground(new Color(0,0,0,0));
		setBounds(280,400 , WIDTH, HEIGHT);	
		//setLayout(Grid)// dimension
		setFocusable(true);
		requestFocus();
		setVisible(true);
		
		JLabel label = new JLabel("Enter username");
		label.setFont(new Font("Tahoma", Font.PLAIN, 20));
		//label.setForeground(Color.red);
		this.add(label);
		text = new JTextField();
		text.setBounds(150, 200,100, 100);
		this.add(text);
		text.setColumns(20);
		
		JButton button= new JButton("OK");
		button.setSize(500, 500);
		button.setBounds(600, 600, 500, 500);
		
		Network Network = new Network();
		ArrayList<Client> Clients = new ArrayList<Client>();
		Network.ClientStartDiscovery();
		
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//get username
				playerName = text.getText();
				if (playerName.contains(":")) {
					errorMessage();
				} else if (playerName.length() > 0) {
					
					Clients.add(new Client(0,playerName));
					
					HostJoinBoard hj = new HostJoinBoard(bgPanel, jf, Clients, Network);
					bgPanel.remove(0);
					bgPanel.add(hj,0);
				} else {
					errorMessage();
				}
			}
		});
		this.add(button);
	
	}
	
	public void errorMessage() {
		JOptionPane.showMessageDialog(this, "Invalid name.", "Error", JOptionPane.ERROR_MESSAGE);
	}


}
