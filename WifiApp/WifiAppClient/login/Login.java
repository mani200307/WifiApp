package login;

import client.ClientForm;

import java.awt.EventQueue;

import java.sql.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ButtonGroup;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;

public class Login extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField user;
	private JPasswordField pass;
	public static String userPublic = "";
	public static String passPublic = "";
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public Login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 340, 376);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Login page");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 26));
		lblNewLabel.setBounds(10, 11, 304, 35);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Username");
		lblNewLabel_1.setBounds(20, 57, 102, 35);
		contentPane.add(lblNewLabel_1);
		
		user = new JTextField();
		user.setBounds(10, 90, 304, 35);
		contentPane.add(user);
		user.setColumns(10);
		
		JLabel lblNewLabel_1_1 = new JLabel("Password");
		lblNewLabel_1_1.setBounds(20, 141, 102, 35);
		contentPane.add(lblNewLabel_1_1);
		
		pass = new JPasswordField();
		pass.setBounds(10, 172, 304, 35);
		contentPane.add(pass);
		
		JButton btnNewButton = new JButton("Enter");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int flag = 0;
				try {
					userPublic = user.getText();
					passPublic = pass.getText().toString();
						flag = 1;
				}catch(Exception ex) {
					
				}
				//initiates connection with Server only when User Credentials matches
				if(flag == 1) {
					System.out.println("Flag true");
					ClientForm client = new ClientForm();
					try {
						ClientForm.main(null);
					} catch (IOException e1) {
						e1.printStackTrace();
					}	
				}
				else
				{
//					JOptionPane.showMessageDialog(null, "False");
				}
				System.out.println(user.getText());
			}
		});
		btnNewButton.setBounds(103, 242, 110, 35);
		contentPane.add(btnNewButton);  
	}
}
