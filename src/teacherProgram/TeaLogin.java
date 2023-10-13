package teacherProgram;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.border.TitledBorder;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import connectionProgram.Connect;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import javax.swing.ImageIcon;
import java.awt.SystemColor;

public class TeaLogin extends JFrame {

	private JPanel contentPane;
	private JTextField TIDF;
	private JButton exitB;
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
					TeaLogin frame = new TeaLogin();
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
	public TeaLogin() {
		setUndecorated(true);
		
		/////////////////////////////////////////////////////////////////////
		st=Connect.getConnection(); // To get Connection As Soon as JFrame in Created
		//------------------------------------------------------------	
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 326, 453);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 326, 453);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JPanel LoginP = new JPanel();
		LoginP.setOpaque(false);
		LoginP.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Teacher Login", TitledBorder.LEADING, TitledBorder.TOP, null, Color.WHITE));
		LoginP.setBounds(36, 110, 268, 263);
		panel.add(LoginP);
		LoginP.setLayout(null);
		
		TIDF = new JTextField();
		TIDF.setBackground(SystemColor.controlDkShadow);
		TIDF.setForeground(Color.WHITE);
		TIDF.setFont(new Font("Tahoma", Font.PLAIN, 14));
		TIDF.addKeyListener(new KeyAdapter() {
			@Override
			
			/////////////////////////////////////////////////////////////////////////////////////
			public void keyPressed(KeyEvent e) {
			if(e.getKeyChar()==KeyEvent.VK_ENTER) 
					passWordF.requestFocus(); // to shift to last PassWord field
			}
			//-------------------------------------------------------------------------------
			
		});
		TIDF.setBounds(62, 90, 140, 28);
		LoginP.add(TIDF);
		TIDF.setColumns(10);
		
		JButton loginB = new JButton("Login");
		loginB.setBackground(Color.BLUE);
		loginB.setFont(new Font("Tahoma", Font.BOLD, 13));
		loginB.setForeground(Color.WHITE);
		loginB.setBorder(null);
		loginB.addActionListener(new ActionListener() {
			
			////////////////////////////////////////////////////////Login Action Taken
			public void actionPerformed(ActionEvent e) {
				try {
					String TID= TIDF.getText(); String passWord=passWordF.getText();
					
					//Preparing Query to Check if the user entered valid TID and PassWord
					String sql="SELECT TID, PassWord from instructor WHERE TID = '"+  TID + " ' AND PassWord = '" + passWord + "' ";
					
					rs=st.executeQuery(sql); // Executing Query
					if(rs.next()==true) {   // if True, then it has found a user with that TID and PassWord
						
						TeaMenu teaMenu=new TeaMenu(TID);  // Creating New Teacher Menu for that Teacher
						teaMenu.setVisible(true);       // Making Menu Visible
						dispose();   // closing the current Login Frame
					
					}else {   //else the given SID and password is not found and Invalid
						
						JOptionPane.showMessageDialog(null,"Invalid SID or Pass Word");
						TIDF.setText("");          // Clearing the TextFields
						passWordF.setText("");
					
					}
					
				} catch (Exception err) {
					JOptionPane.showMessageDialog(null,err.getMessage());
				}
			}
			//---------------------------------------------------------------------------------------
		});
		loginB.setBounds(91, 209, 89, 23);
		LoginP.add(loginB);
		
		JLabel lblSid = new JLabel("Teacher ID");
		lblSid.setForeground(new Color(255, 255, 255));
		lblSid.setBounds(63, 64, 81, 17);
		LoginP.add(lblSid);
		lblSid.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JLabel lblName = new JLabel("Password");
		lblName.setForeground(new Color(255, 255, 255));
		lblName.setBounds(62, 134, 72, 17);
		LoginP.add(lblName);
		lblName.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		passWordF = new JPasswordField();
		passWordF.setBackground(SystemColor.controlDkShadow);
		passWordF.setForeground(Color.WHITE);
		passWordF.setFont(new Font("Tahoma", Font.PLAIN, 18));
		passWordF.setEchoChar('*');
		passWordF.setBounds(62, 162, 140, 28);
		LoginP.add(passWordF);
		
		JLabel lblNewLabel = new JLabel("Wellcome!");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(62, 11, 140, 54);
		LoginP.add(lblNewLabel);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Modern No. 20", Font.BOLD, 30));
		
		exitB = new JButton("");
		exitB.setIcon(new ImageIcon(TeaLogin.class.getResource("/img/Close.jpg")));
		exitB.setBounds(283, 11, 21, 23);
		panel.add(exitB);
		
		JLabel img = new JLabel("");
		img.setIcon(new ImageIcon(TeaLogin.class.getResource("/img/wallpaper9.jpg")));
		img.setBounds(0, 0, 326, 453);
		panel.add(img);
		exitB.addActionListener(new ActionListener() {
			
			/////////////////////////////////////////////////////// Exit Button Action Taken
			public void actionPerformed(ActionEvent e) {
				System.exit(0);  // Programm Exit
			}
			//-----------------------------------------------------------------
		});
		setLocationRelativeTo(null);  // Center Postion
	}
}
