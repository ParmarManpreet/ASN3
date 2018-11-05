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
			inputFromClient = new BufferedReader
					(new InputStreamReader(connectionSocket.getInputStream()));
			String input = inputFromClient.readLine();
			createThread(connectionSocket, input);
			notifyConnection(connectionSocket, "You are Connected" + "\n");
			accounceConnection(input + " is connected"+ "\n", connectionSocket);
			
			new Thread() {
				public void run() {
					while(true) {
						String message;
						try {
							message = inputFromClient.readLine();
							chatWithClients(message + "\n", connectionSocket);
							if(message.equals("Disconnected")) {
								removeClient(connectionSocket);
								break;
							}
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

	public void createThread(Socket connectionSocket, String name){
		System.out.println("inside createThread clientNumb " + clientNumb);
		clients[clientNumb]= connectionSocket;
		clientNames[clientNumb++] = name;
		updateServerUI();
	}

	public void chatWithClients(String message, Socket connectionSocket) {
		System.out.println(clients[clientNumb-1]+ " ");	
		String name = null;
		for(int i=0; i<clientNumb+1; i++) {
			if(clients[i]==connectionSocket) {
				name = clientNames[i];
				break;
			}	
		}
		
		for(int i=0;i<clientNumb+1; i++) {
			if(clients[i] != connectionSocket) {
				System.out.println("value of i " + i);
				Socket socket = clients[i];
				System.out.println("socket value being sent " + socket);
			
				try {
					DataOutputStream outputToClient = new DataOutputStream(socket.getOutputStream());
			        outputToClient.writeBytes(name + ": " + message);
				} catch (Exception ex) {}
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