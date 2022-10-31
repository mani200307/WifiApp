import java.awt.EventQueue;

import java.io.*;
import java.sql.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

//ClientHandler class

class ClientHandler extends Thread {
	final DataInputStream dis;
	final DataOutputStream dos;
	final Socket s;
	static String userNameVal;
	static String passVal;
	static String dateInVal;
	String res;
	String userArray;
	String passArray;
	
	// Constructor
	public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos, String res) {
		this.s = s;
		this.dis = dis;
		this.dos = dos;
		this.res = res;
	}
	
	@Override
	public void run() {

		// PUBLIC IP VERIFICATION
		// Find public IP address
		String systemipaddress = "";
		try {
			URL url_name = new URL("http://checkip.amazonaws.com");

			BufferedReader sc = new BufferedReader(new InputStreamReader(url_name.openStream()));

			// reads system IPAddress
			systemipaddress = sc.readLine().trim();
		}
		catch (Exception e) {
			systemipaddress = "Cannot Execute Properly";
		}

		System.out.println("Server Public IP Address: " + systemipaddress + "\n");
		String iprcv = "";
		try {
			iprcv = dis.readUTF(); // recieve data from client
		} catch (Exception e) {
			System.out.println(e);
		}

		if (systemipaddress.equals(iprcv))
			System.out.println("matched");
		else
			System.out.println("unmatched");

		
		//Message testing. Getting it
		
		userArray = "";
		passArray = "";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/wifidb","root","");
			Statement st = con.createStatement();
			String sql = "Select UserName from login";
			ResultSet rs = st.executeQuery(sql);
			while(rs.next())
			{
				userArray += rs.getString(1) + " ";
				System.out.println(userArray);
			}
			sql = "Select Password from login";
			rs = st.executeQuery(sql);
			while(rs.next())
			{
				passArray += rs.getString(1) + " ";
				System.out.println(passArray);
			}
			con.close();
		} catch (Exception e)
		{
			System.out.println(e);
		}

		String strUser[] = userArray.split(" ");
		String strPass[] = passArray.split(" ");		
		
		userNameVal = "";
		passVal = "";
		dateInVal = "";
		try {
			userNameVal = dis.readUTF();
			passVal = dis.readUTF();
			dateInVal = dis.readUTF();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String flagMatch = "false";
		
		for(int i=0;i<strUser.length;i++)
		{
			if(strUser[i].equals(userNameVal))
			{
				if(strPass[i].equals(passVal))
				{
					flagMatch = "true";
					break;
				}
			}
		}
		
		try {
			dos.writeUTF(flagMatch);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		System.out.println("FlagMatch : "+flagMatch);
		
		if(flagMatch.equals("true"))
		{
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/wifidb","root","");
				Statement st = con.createStatement();
				String sql = "INSERT INTO `attendData`(`UserName`, `EntryTime`) VALUES ('"+userNameVal.toString()+"','"+dateInVal.toString()+"')";
				st.executeUpdate(sql);
				res += "User Name : "+ userNameVal + "Time : "+ dateInVal + "\n";
//				JOptionPane.showMessageDialog(null, res);
				con.close();
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
		}
	
		System.out.println(res);
				
		System.out.println("User name : " + userNameVal);		
		System.out.println("Time : " + dateInVal);		
		
		try {
			// closing resources
			this.dis.close();
			this.dos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}


public class ServerFile extends JFrame {

	private static String resStr;
	private static JLabel resLabel;
		
	public ServerFile() {
		System.out.println("Constructor called!!");
	}		

	
	public static void main(String[] args) throws IOException {
		
		System.out.println("ServerFile!!");
		
		// server is listening on port 5056
		ServerSocket ss = new ServerSocket(5056);
		
		System.out.println("ServerFile!!");
		
		// running infinite loop for getting
		// client request
		while (true) {
			Socket s = null;

			try {
				// socket object to receive incoming client requests
				s = ss.accept();
//				JOptionPane.showMessageDialog(null, "Accepted");
				System.out.println("A new client is connected : " + s);
				
				// obtaining input and out streams
				DataInputStream dis = new DataInputStream(s.getInputStream());
				DataOutputStream dos = new DataOutputStream(s.getOutputStream());

				System.out.println("Assigning new thread for this client");				
				
				// create a new thread object
				
				ClientHandler t = new ClientHandler(s, dis, dos, "");
				
				// Invoking the start() method
				t.start();
				t.join();
				s.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}