package DistributedSystemsAssignment;
import java.net.*;
import java.io.*;
import java.util.*;

public class Node{

    private Random ra;
    private Socket	s;
    private PrintWriter pout = null;
    private ServerSocket n_ss;
    private Socket	n_token;
    String	c_host = "127.0.0.1";
    int 	c_request_port = 7000;
    int 	c_return_port = 7001;
    String	n_host = "127.0.0.1";
    String 	n_host_name;
    int     n_port;
    private int sleepTime;
    
    public Node(String nam, int por, int sec){	
		ra = new Random();
		n_host_name = nam;
		n_port = por;
	
    	System.out.println("Node " +n_host_name+ ":" +n_port+ " of DME is active ....");

    	// NODE sends n_host and n_port  through a socket s to the coordinator
    	// c_host:c_req_port
    	// and immediately opens a server socket through which will receive 
    	// a TOKEN (actually just a synchronization).
    
    	while(true){
    
	    // >>>  sleep a random number of seconds linked to the initialisation sec value
		int randomNumber = ra.nextInt(sleepTime);
    		System.out.println("a random sleep time:"+ randomNumber);
    		try { Thread.sleep(randomNumber);}
    		catch (InterruptedException e) {
    			e.printStackTrace(); }
    		
    		try {

		    // **** Send to the coordinator a token request.
		    // send your ip address and port number
    			n_ss = new ServerSocket(n_port);
				s = new Socket(c_host, c_request_port);
				OutputStream outstream = s.getOutputStream();
				pout = new PrintWriter(outstream);
				String toSend = n_host_name + " " + n_port;
				pout.write(toSend);
				pout.flush();
				pout.close();
				System.out.println("request for token has been made to the port: " + s.getPort() + ". \n");
    		
		    // **** Then Wait for the token
		    // Print suitable messages
				try {
					System.out.println("Waiting for the token...");
					n_token = n_ss.accept();
					System.out.println("Token received!" + "\n");
					n_ss.close();
					n_token.close();

				} catch (Exception e) {
					e.printStackTrace();
				}

		    // **** Sleep for a while
		    // This is the critical session
				try {
					System.out.println("Enter the critical session.");
					Thread.sleep(1500);
					System.out.println("Exit the critical session" + ". \n");
				} catch (InterruptedException e) {
					e.printStackTrace(); }

		    // **** Return the token
		    // Print suitable messages - also considering communication failures 
				try {
			// Makes new connection to indicate return of token (Port 7000 and 7001 is used)
					n_token = new Socket(c_host, 7001);
					System.out.println("Token returned to coordinator.");
					Thread.sleep(3000);
					n_token.close();
				} catch (InterruptedException e) {
						e.printStackTrace(); }

    		}
    		catch (java.io.IOException e) {
		    System.out.println(e);
		    System.exit(1);	
    		}
    	}
    }
    
    public static void main (String args[]){
		String n_host_name = ""; 
		int n_port;
		
		// port and millisec (average waiting time) are specific of a node
		if ((args.length < 1) || (args.length > 2)){
		    System.out.print("Usage: Node [port number] [millisecs]");
		    System.exit(1);
		}
		
		// get the IP address and the port number of the node
	 	try{ 
		    InetAddress n_inet_address =  InetAddress.getLocalHost() ;
		    n_host_name = n_inet_address.getHostName();
		    System.out.println ("node hostname is " +n_host_name+":"+n_inet_address);
	    	}
	    	catch (java.net.UnknownHostException e){
		    System.out.println(e);
		    System.exit(1);
	    	} 
		
		n_port = Integer.parseInt(args[0]);
		System.out.println ("node port is "+n_port);
	    Node n = new Node(n_host_name, n_port, Integer.parseInt(args[1]));
    }
    
    
}
