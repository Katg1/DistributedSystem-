package DistributedSystemsAssignment;
import java.net.*;

public class C_mutex extends Thread{
    C_buffer buffer;
    Socket   s;
    int      port;

    // ip address and port number of the node requesting the token.
    // They will be fetched from the buffer    
    String n_host;
    int    n_port;
	
    public C_mutex (C_buffer b, int p){
		buffer = b;
		port = p;
    }

    public void run(){
	try (ServerSocket ss_back = new ServerSocket(7001)) { 
	    //  >>>  Listening from the server socket on port 7001
	    // from where the TOKEN will be returned later.
		
	    // edit- i moved "serverSocket..."because i got a message =Resource leak: 'ss_back' is never closed
		
	    while (true){
		// >>> Print some info on the current buffer content for debugging purposes.
		// >>> please look at the available methods in C_buffer

		System.out.println("C:mutex   Buffer size is "+ buffer.size());
		
		// if the buffer is not empty
		if (buffer.size( ) >0 ) {		    

		    // >>>   Getting the first (FIFO) node that is waiting for a TOKEN form the buffer
		    //       Type conversions may be needed.

			Object obj = buffer.get();
			if(obj instanceof String[]);
			String[] nodeInfo = (String[]) obj;

		    n_host = nodeInfo[0];
		    n_port = Integer.parseInt(nodeInfo[1]) ;
		    
		    
		    // >>>  **** Granting the token
		    //
		    try{
		//Kate add more  =// Connect to the node's indicated port to grant the token
                s = new Socket(n_host, n_port);
                // Assuming you need to send some message to grant the token
                // You can customize this part based on your implementation
                // Example: PrintWriter out = new PrintWriter(s.getOutputStream(), true);
                // out.println("TOKEN_GRANT_MESSAGE");
                // out.close();
                s.close();
		    }
		    catch (java.io.IOException e) {
				System.out.println(e);
				System.out.println("CRASH Mutex connecting to the node for granting the TOKEN" + e);
		    }
			    
			    
		    //  >>>  **** Getting the token back
		    try{
			// THIS IS BLOCKING !
		    	Socket tokenBackSocket = ss_back.accept();
		    	//Kate- add more here ? 
		    	tokenBackSocket.close();
		    }
		    catch (java.io.IOException e) {
				System.out.println(e);
				System.out.println("CRASH Mutex waiting for the TOKEN back" + e);
		    }
		}// endif	
	    }// endwhile
	}catch (Exception e) {System.out.print(e);}
	
   }
}
