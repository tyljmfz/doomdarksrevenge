package Network.Server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

import Entity.foodonMap;
import Local.Particle;
import Multiplayer.MultiplayerGameState;
import Multiplayer.MultiplayerCell;
import Multiplayer.MultiplayerForest;
import Multiplayer.MultiplayerMud;
import Multiplayer.MultiplayerParticle;
import Multiplayer.MultiplayerPool;

public class ServerHost implements Runnable {
	private ServerSocket ServerSocket;
	private MultiplayerGameState MultiplayerGameState;

	ServerHost(ServerSocket ServerSocket, MultiplayerGameState MultiplayerGameState){
		this.ServerSocket = ServerSocket;
		this.MultiplayerGameState = MultiplayerGameState;
	}
	
	public void run() {
		int i = 1;
		while (true) {
			Socket Socket;
			try {
				Socket = ServerSocket.accept();
				BufferedReader input = new BufferedReader(new InputStreamReader(Socket.getInputStream()));
				DataOutputStream output = new DataOutputStream(Socket.getOutputStream());
				
				Client Client = new Client(i, Socket.getInetAddress());
				Server.Clients.add(Client);
				System.out.println("Client : " + Client.getUserID() + " connected from IP " + Client.getIP() + " .");
				
				ServerSender ServerSender = new ServerSender(output,i);
				Thread SenderThread = new Thread(ServerSender);
				SenderThread.start();
				System.out.println("Output stream for client " + Client.getUserID() + " established.");

				ServerReceiver ServerReceiver = new ServerReceiver(input,i,MultiplayerGameState);
				Thread ReceiverThread = new Thread(ServerReceiver);
				ReceiverThread.start();
				System.out.println("Input stream for client " + Client.getUserID() + " established.");
				
				// Send Client own ID
				Client.sendMessage("CLIENTID:" + i);
				// Send the Clients list
				String message = "CLIENTLIST:";
				for (Client c : Server.Clients) {
					if (c.getUserID() != i) {
						message = message + c.getUserID() + ":" + c.getUsername() + ":";
					}
				}
				Client.sendMessage(message);
				
				// if game already running
				if (MultiplayerGameState.getRunning()) {
					message = "CELLADD:";
					for (MultiplayerCell c : MultiplayerCell.cells) {
						// CELLADD:ID:Name:X:Y:HP:SCORE
						message = message + c.id + ":" + c.name + ":" + c.x +":" + c.y + ":" + c.currentHp + ":" + c.currentExp + ":";
					}
					Client.sendMessage(message);
					String imgSteak=foodonMap.getSteak();
					String imgCheese=foodonMap.getCheese();
					String imgBread=foodonMap.getBread();
					message = "FOODADD:";
					for (MultiplayerParticle p : MultiplayerParticle.particles) {
						message  = message + p.id + ":" + p.x + ":" + p.y + ":";
						
						if (p.img == imgSteak) {
							message = message + "S:";
						} else if (p.img == imgCheese) {
							message = message + "C:";
						} else if (p.img == imgBread) {
							message = message + "B:";
						}
						
					}
					Client.sendMessage(message);
					
					message = "FORESTADD:";
					for (MultiplayerForest f : MultiplayerForest.forests) {
						message = message + f.getX() + ":" + f.getY() + ":";
					}
					Client.sendMessage(message);
					
					message = "POOLADD:";
					for (MultiplayerPool p : MultiplayerPool.pools) {
						message = message + p.getX() + ":" + p.getY() + ":";
					}
					Client.sendMessage(message);
					
					message = "MUDADD:";
					for (MultiplayerMud m : MultiplayerMud.muds) {
						message = message + m.getX() + ":" + m.getY() + ":";
					}
					Client.sendMessage(message);
				}

				i++;
			} catch (Exception ex) {
				System.out.println("Server host error : " +ex.getMessage());
				break;
			}
		}
	}
}
