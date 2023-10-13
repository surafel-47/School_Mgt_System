package studentProgram;
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
import javax.swing.border.LineBorder;

public class StuLogin extends JFrame {

	private JPanel contentPane;
	private JTextField SIDF;
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
					StuLogin frame = new StuLogin();
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
	public StuLogin() {
		setUndecorated(true);
		
		/////////////////////////////////////////////////////////////////////
		st=Connect.getConnection(); // To get Connection As Soon as JFrame in Created
		//------------------------------------------------------------	
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 320, 453);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 320, 453);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JPanel LoginP = new JPanel();
		LoginP.setForeground(new Color(255, 255, 255));
		LoginP.setOpaque(false);
		LoginP.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 255), 2, true), "Student Login", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 255, 255)));
		LoginP.setBounds(22, 101, 273, 281);
		panel.add(LoginP);
		LoginP.setLayout(null);
		
		SIDF = new JTextField();
		SIDF.setBackground(SystemColor.controlDkShadow);
		SIDF.setForeground(Color.WHITE);
		SIDF.setFont(new Font("Tahoma", Font.PLAIN, 16));
		SIDF.addKeyListener(new KeyAdapter() {
			@Override
			
			/////////////////////////////////////////////////////////////////////////////////////
			public void keyPressed(KeyEvent e) {
				String SID=SIDF.getText();
				if(e.getKeyChar()>='0' && e.getKeyChar()<='9') {
					if(SID.length()<5)
						SIDF.setEditable(true);
					else
						SIDF.setEditable(false);
				}else if(e.getKeyChar()==KeyEvent.VK_ENTER) 
					passWordF.requestFocus(); // to shift to last name field
				else if(e.getExtendedKeyCode()==KeyEvent.VK_BACK_SPACE)
					SIDF.setEditable(true);
				else 
					SIDF.setEditable(false);
			}
			//-------------------------------------------------------------------------------
			
		});
		SIDF.setBounds(59, 96, 149, 28);
		LoginP.add(SIDF);
		SIDF.setColumns(10);
		
		JButton loginB = new JButton("Login");
		loginB.setForeground(Color.WHITE);
		loginB.setBorder(null);
		loginB.setFont(new Font("Tahoma", Font.BOLD, 13));
		loginB.setBackground(Color.BLUE);
		loginB.addActionListener(new ActionListener() {
			
			////////////////////////////////////////////////////////Login Action Taken
			public void actionPerformed(ActionEvent e) {
				try {
					String SID= SIDF.getText(); String passWord=passWordF.getText();
					
					//Preparing Query to Check if the user entered valid TID and PassWord
					String sql="SELECT SID, PassWord from Student WHERE SID = '"+  SID + " ' AND PassWord = '" + passWord + "' ";
					
					rs=st.executeQuery(sql); // Executing Query
					if(rs.next()==true) {  // if True, then it has found a user with that SID and PassWord
					
						StuMenu stuMenu=new StuMenu(SID); // Creating New Student Menu for that Student
						stuMenu.setVisible(true);       // Making Menu Visible
						dispose();        // closing the current Login Frame
					
					}else {			//else the given SID and password is not found and Invalid
						
						JOptionPane.showMessageDialog(null,"Invalid SID or Pass Word");
						SIDF.setText("");				//clearing textFields
						passWordF.setText("");
					}
					
				} catch (Exception err) {
					JOptionPane.showMessageDialog(null,err.getMessage());
				}
			}
			//---------------------------------------------------------------------------------------
			
		});
		loginB.setBounds(89, 211, 89, 28);
		LoginP.add(loginB);
		
		JLabel lblSid = new JLabel("Student ID");
		lblSid.setForeground(Color.WHITE);
		lblSid.setBounds(59, 72, 89, 17);
		LoginP.add(lblSid);
		lblSid.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JLabel lblName = new JLabel("Password");
		lblName.setForeground(Color.WHITE);
		lblName.setBounds(59, 135, 72, 17);
		LoginP.add(lblName);
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		passWordF = new JPasswordField();
		passWordF.setBackground(SystemColor.controlDkShadow);
		passWordF.setForeground(Color.WHITE);
		passWordF.setFont(new Font("Tahoma", Font.BOLD, 16));
		passWordF.setEchoChar('*');
		passWordF.addKeyListener(new KeyAdapter() {
			@Override
			/////////////////////////////////////////////////////////
			public void keyPressed(KeyEvent e) {
				@SuppressWarnings("deprecation")
				String passWord = passWordF.getText();
					if(passWord.length()>4) {
						if(e.getExtendedKeyCode()==e.VK_BACK_SPACE)
						   passWordF.setEditable(true);
						else
							   passWordF.setEditable(false);
					}if(e.getExtendedKeyCode()==e.VK_ENTER) {
						loginB.requestFocus();
					}else {
						passWordF.setEditable(true);						
					}
			}
			//-------------------------------------------------
		});
		passWordF.setBounds(60, 159, 148, 30);
		LoginP.add(passWordF);
		
		JLabel lblNewLabel = new JLabel("Wellcome!");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(54, 11, 168, 54);
		LoginP.add(lblNewLabel);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Modern No. 20", Font.PLAIN, 30));
		
		exitB = new JButton("");
		exitB.setBorder(null);
		exitB.setIcon(new ImageIcon(StuLogin.class.getResource("/img/Close.jpg")));
		exitB.setBounds(266, 11, 29, 27);
		panel.add(exitB);
		
		JLabel img1 = new JLabel("");
		img1.setIcon(new ImageIcon(StuLogin.class.getResource("/img/wallpaper3.jpg")));
		img1.setBounds(0, 0, 320, 453);
		panel.add(img1);
		exitB.addActionListener(new ActionListener() {
			
			/////////////////////////////////////////////////////// Exit Button Action Taken
			public void actionPerformed(ActionEvent e) {
				System.exit(0);  // Programm Exit
			}
			//------------------------------------------------------------------
			
		});
		
		setLocationRelativeTo(null);  // Center Postion
	}
}
