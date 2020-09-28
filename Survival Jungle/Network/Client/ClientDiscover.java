package Network.Client;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ClientDiscover {
	
	DatagramSocket DatagramSocket;
	Thread ThreadDiscoverySend, ThreadDiscoveryReceive;
	private final JPanel errorPanel = new JPanel();
	private static ArrayList<InetAddress> HostAddress;

	public void startDiscover() {
		try {
			DatagramSocket = new DatagramSocket();		
			DatagramSocket.setBroadcast(true);
			HostAddress = new  ArrayList<InetAddress>();
			
			ClientDiscoverySend ClientDiscoverySend = new ClientDiscoverySend(DatagramSocket); 
			ThreadDiscoverySend = new Thread(ClientDiscoverySend);
			ThreadDiscoverySend.start();
            System.out.println("Client discovery send started.");

			ClientDiscoveryReceive ClientDiscoveryReceive = new ClientDiscoveryReceive(DatagramSocket, HostAddress); 
			ThreadDiscoveryReceive = new Thread(ClientDiscoveryReceive);
			ThreadDiscoveryReceive.start();
            System.out.println("Client discovery receive started.");

		} catch (Exception ex) {
		    JOptionPane.showMessageDialog(errorPanel, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void stopDiscover() {
		try {
			DatagramSocket.close();
			ThreadDiscoverySend.interrupt();
			
            System.out.println("Client discovery send stopped.");
			ThreadDiscoveryReceive.interrupt();
            System.out.println("Client discovery receive stopped.");
		}
		catch (Exception ex){
		    JOptionPane.showMessageDialog(errorPanel, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public ArrayList<InetAddress> getDiscoveredServer(){
		Set<InetAddress> HostAddressSet = new HashSet<InetAddress>();
		HostAddressSet.addAll(HostAddress);
		HostAddress.clear();
		HostAddress.addAll(HostAddressSet);
		HostAddressSet.clear();
		return HostAddress;
	}
	
	
	
}
