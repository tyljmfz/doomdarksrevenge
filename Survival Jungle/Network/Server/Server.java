package Network.Server;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Multiplayer.MultiplayerGameState;

public class Server {
	static ArrayList<Client> Clients;

	private static Thread HostThread, DiscoveryThread;
	private static ServerSocket ServerSocket;
	private static DatagramSocket DatagramSocket;
	private final int ServerPort = 8888;
	private final int DiscoveryPort = 8889;
	private final JPanel errorPanel = new JPanel();
	private MultiplayerGameState MultiplayerGameState;

	public Server (ArrayList<Client> Clients, MultiplayerGameState MultiplayerGameState){
		this.MultiplayerGameState = MultiplayerGameState;
		this.Clients = Clients;
	}
	
	
	public void startServer() {
		try {		
			ServerSocket = new ServerSocket(ServerPort);
			ServerHost ServerHost = new ServerHost(ServerSocket, MultiplayerGameState);
			HostThread = new Thread(ServerHost);
			HostThread.start();
			System.out.println("Server started.");
			
			DatagramSocket = new DatagramSocket(DiscoveryPort, InetAddress.getByName("0.0.0.0"));
			ServerDiscovery ServerDiscovery = new ServerDiscovery(DatagramSocket);
			DiscoveryThread = new Thread(ServerDiscovery);
			DiscoveryThread.start();
			System.out.println("Server discovery started.");
		} catch (Exception ex){
		    JOptionPane.showMessageDialog(errorPanel, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		} 
	}
	
	public void stopServer() {
		try {
            ServerSocket.close();
            DatagramSocket.close();
            HostThread.interrupt();
            DiscoveryThread.interrupt();
            System.out.println("Server stopped.");
		} catch (Exception ex){
		    JOptionPane.showMessageDialog(errorPanel, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void sendAsServer(String message) {
		for (Client Client : Clients) {
			Client.getQueue().add(message+"\n");
		}
	}
	
}
