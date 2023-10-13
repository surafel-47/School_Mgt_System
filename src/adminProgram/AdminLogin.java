package adminProgram;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import javax.swing.border.MatteBorder;
import java.awt.Color;
import javax.swing.border.EtchedBorder;
import javax.swing.SwingConstants;
import java.awt.Font;
import connectionProgram.Connect;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.border.TitledBorder;
import javax.swing.ImageIcon;
import java.awt.SystemColor;

public class AdminLogin extends JFrame {

	private JPanel contentPane;
	private JTextField nameF;
	private JPasswordField passWordF;
	
	private Statement st=null; //connection Objects
	private ResultSet rs=null;   // Connection Objects
	
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminLogin frame = new AdminLogin();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AdminLogin() {	
		/////////////////////////////////////////////////////////////////////
		st=Connect.getConnection(); // To get Connection As Soon as JFrame in Created
		//------------------------------------------------------------	
		
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 326, 453);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel LoginPanel = new JPanel();
		LoginPanel.setBounds(0, 0, 326, 453);
		contentPane.add(LoginPanel);
		LoginPanel.setLayout(null);
		
		JPanel loginFourm = new JPanel();
		loginFourm.setOpaque(false);
		loginFourm.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(64, 64, 64), new Color(160, 160, 160)), "Admin Login ", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 255, 255)));
		loginFourm.setBounds(38, 92, 264, 320);
		LoginPanel.add(loginFourm);
		loginFourm.setLayout(null);
		
		nameF = new JTextField();
		nameF.setBorder(null);
		nameF.setBackground(SystemColor.controlDkShadow);
		nameF.setFont(new Font("Tahoma", Font.PLAIN, 17));
		nameF.setForeground(Color.WHITE);
		nameF.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyChar()==KeyEvent.VK_ENTER)
					passWordF.requestFocus();
			}
		});
		nameF.setBounds(41, 111, 192, 27);
		nameF.setColumns(10);
		loginFourm.add(nameF);
		
		JLabel nameL = new JLabel("User Name");
		nameL.setForeground(Color.WHITE);
		nameL.setFont(new Font("Tahoma", Font.PLAIN, 15));
		nameL.setBounds(41, 87, 89, 20);
		loginFourm.add(nameL);
		
		JLabel passWordL = new JLabel("Password");
		passWordL.setFont(new Font("Tahoma", Font.PLAIN, 15));
		passWordL.setForeground(Color.WHITE);
		passWordL.setBounds(41, 163, 89, 20);
		loginFourm.add(passWordL);
		
		JButton logInB = new JButton("Login");
		logInB.setForeground(Color.WHITE);
		logInB.setBorder(null);
		logInB.setBackground(Color.BLUE);
		logInB.setFont(new Font("Times New Roman", Font.BOLD, 15));
		logInB.addActionListener(new ActionListener() {
			
			/////////////////////////////////////////////// Admin Login Button Action
			public void actionPerformed(ActionEvent e) {
				try {
					rs=st.executeQuery("SELECT name,password FROM Admin WHERE name = '" + nameF.getText() + "'  AND password = '" +  passWordF.getText() + "' " );
					if(rs.next()) {
						dispose();
						AdminMainMenu adminMainMenu =new AdminMainMenu();
						adminMainMenu.setVisible(true);	
					}else {
						JOptionPane.showMessageDialog(null, "Invalid Name or PassWord");
						nameF.setText(""); passWordF.setText("");
					}
				}catch(Exception err){
					System.out.println(err.getMessage());
				}

			}
			//------------------------------------------------
			
		});
		logInB.setBounds(89, 245, 106, 27);
		loginFourm.add(logInB);
		
		passWordF = new JPasswordField();
		passWordF.setBorder(null);
		passWordF.setBackground(SystemColor.controlDkShadow);
		passWordF.setFont(new Font("Tahoma", Font.BOLD, 17));
		passWordF.setEchoChar('*');
		passWordF.setBounds(41, 186, 192, 27);
		loginFourm.add(passWordF);
		
		JLabel titleL = new JLabel("Wellcome!");
		titleL.setForeground(Color.WHITE);
		titleL.setBounds(51, 34, 167, 42);
		loginFourm.add(titleL);
		titleL.setFont(new Font("Modern No. 20", Font.PLAIN, 28));
		titleL.setHorizontalAlignment(SwingConstants.CENTER);
		
		JButton exitB = new JButton("");
		exitB.setIcon(new ImageIcon(AdminLogin.class.getResource("/img/Close.jpg")));
		exitB.setForeground(Color.WHITE);
		exitB.setFont(new Font("Tahoma", Font.BOLD, 17));
		exitB.setBorder(null);
		exitB.setBounds(263, 11, 29, 27);
		LoginPanel.add(exitB);
		
		JLabel imgL = new JLabel("");
		imgL.setIcon(new ImageIcon(AdminLogin.class.getResource("/img/wallpaper12.jpg")));
		imgL.setBounds(0, 0, 326, 453);
		LoginPanel.add(imgL);
		exitB.addActionListener(new ActionListener() {
			
			/////////////////////////////////////////////////////////// Exit Button Action
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
			//--------------------------------------------------
		});
		
		setLocationRelativeTo(null);  // Center Postion
	}
}
