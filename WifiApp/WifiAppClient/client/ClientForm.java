package client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import login.Login;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Label;

public class ClientForm extends JFrame{

	private static final long serialVersionUID = 1L;
	private static DateTimeFormatter dtf;
	private static LocalDateTime now;
	private static String userName;
	private static String pass;	
		
	public static void main(String[] args)  throws IOException {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientForm frame = new ClientForm();
					frame.setVisible(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		try {
//			JOptionPane.showMessageDialog(null, "Line 49 called");
						
			Scanner scn = new Scanner(System.in);

			// getting localhost ip
			InetAddress ip = InetAddress.getByName("localhost");
			System.out.println("chk");

			// establish the connection with server port 5056
			Socket s = new Socket(ip, 5056);

			
			// obtaining input and out streams
			DataInputStream dis = new DataInputStream(s.getInputStream());
			DataOutputStream dos = new DataOutputStream(s.getOutputStream());

			// PUBLIC IP SEND
			// Find public IP address
			String systemipaddress = "";
			try {
				URL url_name = new URL("http://checkip.amazonaws.com");

				BufferedReader sc = new BufferedReader(new InputStreamReader(url_name.openStream()));

				// reads system IPAddress
				systemipaddress = sc.readLine().trim();
			} catch (Exception e) {
				systemipaddress = "Cannot Execute Properly";
			}
			System.out.println("Public IP Address: " + systemipaddress + "\n");
			try {
				dos.writeUTF(systemipaddress); // transfer data to server
			} catch (Exception e) {
				System.out.println(e);
			}
			
			//Message testinng. Sending it

			String dateVal = dtf.format(now);
			String userNameVal = userName.toString();   //to be passed
			String passVal = pass.toString();
			String dateIn = dateVal;					//to be passed
			
			System.out.println("U : "+userNameVal + "P : "+ passVal);
			
			dos.writeUTF(userNameVal);
			dos.writeUTF(passVal);
			dos.writeUTF(dateIn);					
			
			String loginMatch = dis.readUTF();
			
			if(loginMatch.equals("true"))
				JOptionPane.showMessageDialog(null, "Attendence entered successfully!");
			else if(loginMatch.equals("false"))
				JOptionPane.showMessageDialog(null, "Incorrect username and password");
			
			// closing resources
			scn.close();
			dis.close();
			dos.close();
		} catch (Exception e) {
			System.out.println("Line 106 occured!!!");
			e.printStackTrace();
		}
	}

	/**
	 * Create the frame.
	 */
	public ClientForm() {
		
		userName = Login.userPublic;
		pass = Login.passPublic;
		
		dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		now = LocalDateTime.now();  
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
