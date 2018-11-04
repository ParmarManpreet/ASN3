package server;

import java.awt.Color;

import java.awt.FlowLayout;//imports basic layout so we don't have to create one

import javax.swing.JButton;
import javax.swing.JFrame;// gives you basic features of windows (title bar, maximize, minimize...)
import javax.swing.JLabel;//lets you output text and images
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.event.ActionListener;//ways for user to do something ex press enter
import java.awt.event.ActionEvent;



public class ServerFrame extends JFrame{// Extends all windows features
	public JLabel serverLabel;

	public ServerFrame() {
		super("Chatting Server"); //gives title
		//types of layout you can use: FlowLayout, GridLayout ...
		setLayout(null); //gives default layout
		defaultServerUI();
		
	}
	

	public void writeinBox(String input) {
	
			//box1.append(input + '\n');	
	
		}
		
	
	
	
	public void defaultServerUI() {
		
		setSize(400,400);
		serverLabel = new JLabel("No Clients Connected");
		serverLabel.setBounds(130, 50, 150 ,30);
		serverLabel.setForeground(Color.RED);
		add(serverLabel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}
	

	//Method checks what connection status it is and outputs it with its designated color
	public void setConnection(String connect) {
		
//		if(connect.equals("Not Connected")) {
//			connectLabel.setText("Connection Status: "+ connect );
//			//connectLabel= new JLabel("Connection Status: "+ connect );
//			connectLabel.setForeground(Color.RED);
//			
//		}else {
//			connectLabel.setText("Connection Status: "+ connect );
//			//connectLabel= new JLabel("Connection Status: "+ connect );
//			connectLabel.setForeground(Color.BLUE);
			
		}
		

	
	//Method that adds the different features and makes the frame visible


	public static void main(String[] args) {
	
	
}

}