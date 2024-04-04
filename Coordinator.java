package DistributedSystemsAssignment;
import java.net.*;

public class Coordinator {
	
    public static void main (String args[]){
		int port = 7000;
		
		Coordinator c = new Coordinator ();
		
		try {
			// (newly April 1st added) 
			// initialize a ServerSocket on port 7000 to listen for incoming connection requests from nodes. 
			//This server socket will handle all communication between the coordinator and the nodes.
			ServerSocket serverSocket = new ServerSocket(port);
			System.out.println("Coordinator is running and listening for node requests...");
			
		    InetAddress c_addr = InetAddress.getLocalHost();
		    String c_name = c_addr.getHostName();
		    System.out.println ("Coordinator address is "+c_addr);
		    System.out.println ("Coordinator host name is "+c_name+"\n\n");    
		}
		catch (Exception e) {
		    System.err.println(e);
		    System.err.println("Error in corrdinator");
		}
				
		// allows defining port at launch time
		if (args.length == 1) port = Integer.parseInt(args[0]);
	
		// Create and run a C_receiver and a C_mutex object sharing a C_buffer object
		C_receiver receiver_c = new C_receiver(buffer, port);
		Thread receiverThread = new Thread(receiver_c); // instantiate receiver thread

		C_mutex mutex_c = new C_mutex(buffer, 7001);
		Thread mutex_thread = new Thread(mutex_c);
		// The Mutex receives threads, takes requests, and put them in line to done./
		
		receiverThread.start();
		mutex_thread.start();
    }
    
}


