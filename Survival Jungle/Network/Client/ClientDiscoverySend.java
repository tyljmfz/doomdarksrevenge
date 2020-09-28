package Network.Client;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.TimeUnit;

public class ClientDiscoverySend implements Runnable{
	DatagramSocket DatagramSocket;
	private final int  Port = 8889;
	
	ClientDiscoverySend(DatagramSocket DatagramSocket){
		this.DatagramSocket = DatagramSocket;
	}
	
	public void run() {
		try {
			while(true) {
				byte[] sendData = "SERVER_REQUEST".getBytes();
				
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName("255.255.255.255"), Port);
				DatagramSocket.send(sendPacket);
				System.out.println("Client discovery: Request packet sent to: 255.255.255.255");
				
				TimeUnit.SECONDS.sleep(1);
			}
		}
		catch (Exception ex) {
			System.out.println("Client Discovery Send Error : " +ex.getMessage());
		}
	}
}
