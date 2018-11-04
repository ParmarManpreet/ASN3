package server;

import java.net.*;
import java.awt.Color;

//import clients.ClientFrame;

import java.io.*;

public class MasterServer {
    public  ServerFrame serverFrame;
    public ServerSocket welcomeSocket;
    public Socket connectionSocket;
    public DataOutputStream outputToServer;
    public BufferedReader inputFromClient;
    public int clientNumb;
    
	public MasterServer() {
		ServerUI();
		Server();
		clientNumb= 0;
	}
	
	public void ServerUI() {
		serverFrame = new ServerFrame();
	}
	
	public void Server() {
		try {
			//port #
			welcomeSocket = new ServerSocket(2245);
			while(true) {
				connectionSocket = welcomeSocket.accept();
				//reads message sent by client
				inputFromClient = new BufferedReader
						(new InputStreamReader(connectionSocket.getInputStream()));
				String recSentence = inputFromClient.readLine();
				System.out.println(recSentence);
				
				confirmConnect(recSentence);
				
//				if(recSentence != null){
//					
//					new Thread() {
//						public void run() {
//							serverFrame.serverLabel.setText(recSentence);
//							System.out.println(recSentence);
//						}	
//					}.start();
//				}
		      	// If connection request, reply to the client 
		      	// Else clear server frame if there are no clients
//		      	if(recSentence.startsWith("Disconnection"))
//		        {
		        	// pushF1.defaultConnection();
	 
//		        }
//		      	else
//		        {
		 
		//            pushF1.writeinBox(recSentence);
		//			pushF1.setConnection("Connected");
		//			System.out.println(recSentence);
				
		            //send message to the client
		           // DataOutputStream outputToClient = new 
		                   // DataOutputStream(connectionSocket.getOutputStream());
		
		            //use \n bc we use readline before and it looks for new line character
		         //   String sentence= "From Server: Connected..." +'\n';
		
		          // outputToClient.writeBytes(sentence);
		            
		        }
				
			//}
		//we can't close loop because we have an infinite loop to keep socket open
		//welcomeSocket.close();
//		
	    }catch(Exception ex) {}
	}
	
	private void confirmConnect(String connect) {
		if(connect.equals("Connected")) {
			try {
				updateServerUI(connect);
	            //send message to the client
	            DataOutputStream outputToClient;
				outputToClient = new 
				        DataOutputStream(connectionSocket.getOutputStream());
	            //use \n bc we use readline before and it looks for new line character
	            String sentence= "You are Connected" +'\n';
	            outputToClient.writeBytes(sentence);
			} catch (IOException e) {}
		}else {
			updateServerUI(connect);	
		}
	}

	//updates server UI by adding or removing number of clients connected
	public void updateServerUI(String connect){
		if(connect.equals("Connected")) {
			clientNumb++;
			if(clientNumb == 1) {
				serverFrame.serverLabel.setText(clientNumb + " Client Connected" );
				serverFrame.serverLabel.setForeground(Color.BLUE);
			}else {
			serverFrame.serverLabel.setText(clientNumb + "Clients Connected" );
			serverFrame.serverLabel.setForeground(Color.BLUE);
			}
			
		}else if (connect.equals("Disconnected")) {
			clientNumb--;
			if(clientNumb == 1) {
				serverFrame.serverLabel.setText(clientNumb + " Client Connected" );
				serverFrame.serverLabel.setForeground(Color.BLUE);
			}else if(clientNumb==0){
				serverFrame.serverLabel.setText("No Clients Connected" );
				serverFrame.serverLabel.setForeground(Color.RED);
			}else {
			serverFrame.serverLabel.setText(clientNumb + "Clients Connected" );
			serverFrame.serverLabel.setForeground(Color.BLUE);
			}
		}
	}
	public static void main(String[] args) {
		MasterServer runServer= new MasterServer();

}

}