package Map;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import Main.MenuPanel;
import Multiplayer.MultiplayerBoard;
import Network.Network;
import Network.Server.Client;

/**
 * 
 * @author Bitnarae Kim , Khang Sheong Foong
 * The HostJoinBoard class gives a JComboBox for the user to select an ip address.
 *
 */
@SuppressWarnings("serial")
public class HostJoinBoard extends JPanel {
	
	private static final int WIDTH = 250;
	private static final int HEIGHT = 200;
	
	JButton button = new JButton("Join");
	
	private JComboBox<String> cb;
	Network Network;
	private Timer timer;
	private final int DELAY = 1000;	// milliseconds delay

	public HostJoinBoard(MenuPanel bgPanel, JFrame jf, ArrayList<Client> Clients, Network Network){
		this.Network = Network;
		
		ArrayList<String> ip = new ArrayList<String>();
		ArrayList<InetAddress> server = Network.getDiscoveredServer();
		for (InetAddress serverIP : server) {
			ip.add(serverIP.toString().substring(1));
		}
		
		List<String> ipList = ip;
		
		//add some stuff
		ipList.add(0, "Select IP");
		String[] ipString = ipList.toArray(new String[0]);
		cb = new JComboBox<String>(ipString);
		
		setBackground(new Color(0,0,0,0));
		setBounds(280,400 , WIDTH, HEIGHT);	
		//setLayout(Grid)// dimension
		setFocusable(true);
		requestFocus();
		setVisible(true);
		
		JLabel label = new JLabel("Server IP:");
		label.setFont(new Font("Tahoma", Font.PLAIN, 20));
		this.add(label);

		JButton button= new JButton("HOST");
		button.setSize(500, 500);
		button.setBounds(600, 600, 500, 500);		
		

		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Network.clientStopDiscover();

				
				JPanel game = new JPanel();
				game.add(new MultiplayerBoard(Clients, true, Network, "127.0.0.1"));
				jf.setContentPane(game);
				jf.setVisible(true);

			}
		});
		
		cb.setEditable(true);
		this.add(cb);
		this.add(button);
		
		JButton button1 = new JButton("JOIN");
		button1.setSize(500, 500);
		button1.setBounds(600, 600, 500, 500);		
		

		button1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Network.clientStopDiscover();
				
				JPanel game = new JPanel();
				game.add(new MultiplayerBoard(Clients, false, Network, cb.getEditor().getItem().toString()));
				jf.setContentPane(game);
				jf.setVisible(true);
			}
		});
		this.add(button1);
			
		
	}
	
}
