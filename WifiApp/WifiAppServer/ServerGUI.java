import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.TitledBorder;

import net.proteanit.sql.DbUtils;

//import client.ClientForm;

import java.awt.Font;
//import java.awt.ScrollPane;

import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.border.EtchedBorder;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;

//import java.sql.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;

public class ServerGUI extends JFrame {

	private JPanel contentPane;
	private JTextField txtUserName;
	private JTextField txtPass;
	private JTable table;
	Connection con;
	Statement st;
	ResultSet rs;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerGUI frame = new ServerGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		try {
			ServerFile.main(null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ServerGUI() {
		initialise();
		connect();
		table_load();
	}
		
	public void initialise()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 685, 434);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);

		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Server Page");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setBounds(314, 11, 136, 29);
		contentPane.add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Insert Login Data", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(10, 51, 258, 130);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Username");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1.setBounds(10, 32, 82, 14);
		panel.add(lblNewLabel_1);
		
		txtUserName = new JTextField();
		txtUserName.setBounds(116, 30, 112, 20);
		panel.add(txtUserName);
		txtUserName.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Password");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_2.setForeground(new Color(0, 0, 0));
		lblNewLabel_2.setBounds(10, 79, 66, 14);
		panel.add(lblNewLabel_2);
		
		txtPass = new JTextField();
		txtPass.setBounds(116, 77, 112, 20);
		panel.add(txtPass);
		txtPass.setColumns(10);
		
		JButton addLoginBtn = new JButton("Add Login");
		addLoginBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {			
				String userName = txtUserName.getText();
				String pass = txtPass.getText();
				try {
					st = con.createStatement();
					String sql = "insert into login(UserName, Password) values('"+userName.toString()+"','"+pass.toString()+"')";
					st.executeUpdate(sql);
					JOptionPane.showMessageDialog(null, "Data inserted");
					txtUserName.setText("");
					txtPass.setText("");
					txtUserName.requestFocus();
				}
				catch(Exception ex)
				{
					System.out.println(ex);
				}
				
			}
		});
		addLoginBtn.setBounds(20, 190, 114, 23);
		contentPane.add(addLoginBtn);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(307, 51, 352, 225);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JButton btnNewButton = new JButton("Load table");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				table_load();
			}
		});
		btnNewButton.setBounds(307, 297, 114, 23);
		contentPane.add(btnNewButton);
	}
	
	public void connect()
	{
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/wifidb","root","");
			st = con.createStatement();
		}catch(Exception e)
		{
			System.out.println(e);
		}
	}
		
	public void table_load()
	{
		try {
			String sql = "select * from attendData";
			rs = st.executeQuery(sql);
			table.setModel(DbUtils.resultSetToTableModel(rs));
		} catch (Exception e) {
			// TODO: handle exception
		}
	}	
}
