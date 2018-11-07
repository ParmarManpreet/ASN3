package server;

import java.net.*;
import java.awt.Color;

//import clients.ClientFrame;

import java.io.*;

public class MasterServer{
    public  ServerFrame serverFrame;
    public ServerSocket welcomeSocket;
    public Socket connectionSocket;
    public Socket[] clients = new Socket[50];
    public DataOutputStream outputToClient;
    public BufferedReader inputFromClient;
    public int clientNumb = 0;
    public Thread thread = null;
    public String clientNames[] = new String[50];

	public MasterServer() {
		ServerUI();
		RunServer();
	}
	
	public void ServerUI() {
		//make GUI
		serverFrame = new ServerFrame();
	}
	
	public void RunServer() {
		try {
			welcomeSocket = new ServerSocket(2245);
	    }catch(Exception ex) {}
		
		try {
			while(true) {
				//accept client trying to connect
				connectionSocket = welcomeSocket.accept();
				clientThread(connectionSocket);			    
		    }
		}catch(Exception ex){}
	}
	
	public void notifyConnection(Socket connectionSocket, String message) {
		try {
			DataOutputStream outputToClient = new DataOutputStream(connectionSocket.getOutputStream());
	        outputToClient.writeBytes(message);
		}catch(Exception ex) {}
	}
	
	public void clientThread(Socket connectionSocket){
		try {
			System.out.println("Socket number: "+ connectionSocket);
			inputFromClient = new BufferedReader
					(new InputStreamReader(connectionSocket.getInputStream()));
			String input = inputFromClient.readLine();
			System.out.println("input: "+input);
			createArray(connectionSocket, input);
			//notifies the client connecting that he/she is connected
			notifyConnection(connectionSocket, "You are Connected" + "\n");
			//announce all the other clients that a new client has connected
			accounceConnection(input + " is connected"+ "\n", connectionSocket);
			
			//this thread is constantly listening to client messages
			new Thread() {
				public void run() {
					while(true) {
						String message;
						try {
							message = inputFromClient.readLine();
							if(message.equals("Disconnected")) {
								System.out.println("socket we want to delete " + connectionSocket);
								removeClient(connectionSocket);
								break;
							}
							chatWithClients(message, connectionSocket);
						} catch (IOException e) {
						}
					}
				}
			}.start();
		}catch(Exception ex) {}
	}	
	
	public void removeClient(Socket connectionSocket){
		clientNumb--;
		updateServerUI();
		for(int i=0;i<clientNumb+1; i++){
			if(clients[i]==connectionSocket) {
				clients[i]=null;
				clientNames[i]=null;
			}
		}
	}

	public void accounceConnection(String message, Socket connectionSocket2) {		
		for(int i=0;i<clientNumb+1; i++) {
			if(clients[i] != null && clients[i] != connectionSocket) {
				Socket socket = clients[i];
			
				try {
					DataOutputStream outputToClient = new DataOutputStream(socket.getOutputStream());
			        outputToClient.writeBytes(message);
				} catch (Exception ex) {}
			}
		}
	}

	public void createArray(Socket connectionSocket, String name){
		System.out.println("name into array: " + clientNumb);
		clients[clientNumb]= connectionSocket;
		System.out.println("confirm name name into array: " + name);
		clientNames[clientNumb++] = name;
		updateServerUI();
	}
	
	//send message to all clients
	public void chatWithClients(String message, Socket connectionSocket) {
		String name = null;
		Socket socket = clients[0];
		if(message.startsWith("YES")) {
				String[] data = message.split(",",3);
				name = data[1];
				System.out.println("sending DM to: " + name);
				message = data[2];
				System.out.println("message of DM: " + message);
				for(int i = 0; i<clientNumb; i++) {
					System.out.println("value of i in loop: " + i);
					System.out.println("is it the right receiver of the DM: " + clientNames[i]+ "");
					if(clientNames[i].equals(name)) {
						System.out.println("if statement == name of receiver is: " + clientNames[i]);
						socket = clients[i];
						System.out.println("socket number of the receiver " + clients[i]);
						break;
					}
				}
				for(int i = 0; i<clientNumb; i++) {
					if(clients[i] == connectionSocket) {
						name = clientNames[i];
						System.out.println("sent by: " + name);
						break;
					 }
				}
			try {
				System.out.println(socket);
				String newMessage = name + ": " + message + "\n";
				DataOutputStream outputToClient = new DataOutputStream(socket.getOutputStream());
		        outputToClient.writeBytes(newMessage);
			} catch (Exception ex) {}
		}else {
			for(int i=0; i<clientNumb+1; i++) {
				//System.out.println("Value of i "+ i);
				//System.out.println("Number of clients "+ clientNumb);
				if(clients[i]==connectionSocket) {
					name = clientNames[i];
					break;
				}	
			}
			
			for(int i=0;i<clientNumb+1; i++) {
				if(clients[i] != connectionSocket) {
					Socket socketcon = clients[i];
					try {
						String eMessage = name + ": " + message + "\n"; 
						DataOutputStream outputToClient = new DataOutputStream(socketcon.getOutputStream());
				        outputToClient.writeBytes(eMessage);
					} catch (Exception ex) {}
				}
			}
		}
	}	

	//updates server UI by adding or removing number of clients connected
	public void updateServerUI(){
		if(clientNumb == 1) {
			serverFrame.serverLabel.setText(clientNumb + " Client Connected" );
			serverFrame.serverLabel.setForeground(Color.BLUE);
		}else if (clientNumb == 0) {
			serverFrame.serverLabel.setText("No Clients Connected");
			serverFrame.serverLabel.setForeground(Color.RED);	
		}else {
		serverFrame.serverLabel.setText(clientNumb + "Clients Connected" );
		serverFrame.serverLabel.setForeground(Color.BLUE);
		}
	}
	public static void main(String[] args) {
		MasterServer runServer= new MasterServer();

}



}