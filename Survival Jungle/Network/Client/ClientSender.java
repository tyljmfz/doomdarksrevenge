package Network.Client;

import java.io.DataOutputStream;
import java.util.concurrent.BlockingQueue;

public class ClientSender implements Runnable{
	
	private BlockingQueue<String> queue;
	private DataOutputStream output;
	
	ClientSender(BlockingQueue<String> queue, DataOutputStream output){
		this.queue = queue;
		this.output = output;
	}
	
	public void run() {
		while (true) {
			try {
				String message = queue.take();
				output.writeBytes(message);
				System.out.print("Client sender message: " + message);
			}
			catch(Exception ex) {
				System.out.println("Client Sender error: " + ex.getMessage());
				ex.printStackTrace();
			}

		}

		
	}
}
