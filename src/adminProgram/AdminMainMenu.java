package adminProgram;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import connectionProgram.Connect;
import javax.swing.JTabbedPane;
import java.awt.Window.Type;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import java.awt.Color;
public class AdminMainMenu extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminMainMenu frame = new AdminMainMenu();
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
	public AdminMainMenu() {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(AdminMainMenu.class.getResource("/img/nathan-anderson-iYO-EGosrCo-unsplash.jpg")));
		setVisible(true);
		setTitle("Main Menu\r\n");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 440);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 746, 401);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JButton logOutB = new JButton("Log Out");
		logOutB.setFocusable(false);
		logOutB.setBackground(Color.BLACK);
		logOutB.setForeground(Color.WHITE);
		logOutB.setIcon(new ImageIcon(AdminMainMenu.class.getResource("/img/logout.png")));
		logOutB.setBounds(20, 351, 122, 28);
		panel.add(logOutB);
		logOutB.addActionListener(new ActionListener() {
			
			////////////////////////////////////////////////////////////// Log Out button to return to menu
			public void actionPerformed(ActionEvent e) {
				dispose();
				AdminLogin adminLogin=new AdminLogin();
				adminLogin.setVisible(true);
			}
			///-------------------------------------------------------------------------------------------
			
		});
		logOutB.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JButton extraCurB = new JButton(" Extra Curricular ");
		extraCurB.setForeground(Color.WHITE);
		extraCurB.setBackground(Color.DARK_GRAY);
		extraCurB.setIcon(new ImageIcon(AdminMainMenu.class.getResource("/img/pngfind.com-sports-png-472802.png")));
		extraCurB.setFocusable(false);
		extraCurB.setBounds(74, 258, 248, 68);
		panel.add(extraCurB);
		extraCurB.addActionListener(new ActionListener() {
			
			////////////////////////////////////////////////////////////// Go to Extra Curricular Activity Managment
			public void actionPerformed(ActionEvent e) {
				dispose();
				ExtraCurrMgt ExtraCurrMgt=new ExtraCurrMgt();
				ExtraCurrMgt.setVisible(true);
			}
			///-------------------------------------------------------------------------------------------
			
		});
		extraCurB.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JButton stuDetailsB = new JButton("      Student/Teacher");
		stuDetailsB.setFocusable(false);
		stuDetailsB.setBackground(Color.DARK_GRAY);
		stuDetailsB.setForeground(Color.WHITE);
		stuDetailsB.setHorizontalAlignment(SwingConstants.LEFT);
		stuDetailsB.setIcon(new ImageIcon(AdminMainMenu.class.getResource("/img/1.png")));
		stuDetailsB.setBounds(74, 165, 248, 68);
		panel.add(stuDetailsB);
		stuDetailsB.addActionListener(new ActionListener() {
			
			////////////////////////////////////////////////////////////// Go to remove Student or Teacher
			public void actionPerformed(ActionEvent e) {
				dispose();
				RemoveStuTea removeStuTea = new RemoveStuTea();
				removeStuTea.setVisible(true);
			}
			//----------------------------------------------------
			
		});
		stuDetailsB.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JButton stuMgtB = new JButton("Register New Student");
		stuMgtB.setFocusable(false);
		stuMgtB.setForeground(Color.WHITE);
		stuMgtB.setBackground(Color.DARK_GRAY);
		stuMgtB.setIcon(new ImageIcon(AdminMainMenu.class.getResource("/img/Add.png")));
		stuMgtB.setBounds(74, 79, 248, 68);
		panel.add(stuMgtB);
		stuMgtB.addActionListener(new ActionListener() {
			
			//////////////////////////////////////////////////////// Go To Register Students		
			public void actionPerformed(ActionEvent e) {
				dispose();
				RegStudent regStudent=new RegStudent();
				regStudent.setVisible(true);
			}
			//-------------------------------------------------
		
		});
		stuMgtB.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JButton teachMgtB = new JButton("Register New Teacher");
		teachMgtB.setFocusable(false);
		teachMgtB.setForeground(Color.WHITE);
		teachMgtB.setBackground(Color.DARK_GRAY);
		teachMgtB.setIcon(new ImageIcon(AdminMainMenu.class.getResource("/img/pngfind.com-teacher-clip-art-png-5456157.png")));
		teachMgtB.setBounds(404, 79, 238, 68);
		panel.add(teachMgtB);
		teachMgtB.addActionListener(new ActionListener() {
			
			////////////////////////////////////////////////////// Go to Register Teacher
			public void actionPerformed(ActionEvent e) {
				dispose();
				TeaRegister teaRegister=new TeaRegister();
				teaRegister.setVisible(true);
			}
			//------------------------------------------
			
		});
		teachMgtB.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JButton bookMgtB = new JButton("Books Managment");
		bookMgtB.setForeground(Color.WHITE);
		bookMgtB.setBackground(Color.DARK_GRAY);
		bookMgtB.setIcon(new ImageIcon(AdminMainMenu.class.getResource("/img/pngfind.com-classroom-icon-png-3441162.png")));
		bookMgtB.setFocusable(false);
		bookMgtB.setBounds(404, 165, 238, 68);
		panel.add(bookMgtB);
		bookMgtB.addActionListener(new ActionListener() {
			
			///////////////////////////////////////////////////////////// To Go to Book Managment Menu
			public void actionPerformed(ActionEvent e) {
				dispose();
				BookMgt bookMgt=new BookMgt();
				bookMgt.setVisible(true);
			}
			//--------------------------------------------------------------
			
		});
		bookMgtB.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JButton btnCustomQuery = new JButton("Custom Query");
		btnCustomQuery.setForeground(Color.WHITE);
		btnCustomQuery.setBackground(Color.DARK_GRAY);
		btnCustomQuery.setIcon(new ImageIcon(AdminMainMenu.class.getResource("/img/pngfind.com-settings-icon-png-858960.png")));
		btnCustomQuery.setFocusable(false);
		btnCustomQuery.setBounds(404, 258, 238, 68);
		panel.add(btnCustomQuery);
		btnCustomQuery.addActionListener(new ActionListener() {
			
			///////////////////////////////////////////////// return to adminMainMenu Button
			public void actionPerformed(ActionEvent e) {
				dispose();
				CustomQuery customQuery =new CustomQuery();
				customQuery.setVisible(true);
			}
			//---------------------------------------------------------------------------
			
		});
		btnCustomQuery.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JLabel imgL = new JLabel("");
		imgL.setForeground(Color.WHITE);
		imgL.setFont(new Font("Vivaldi", Font.PLAIN, 17));
		imgL.setVerticalAlignment(SwingConstants.TOP);
		imgL.setHorizontalAlignment(SwingConstants.LEFT);
		imgL.setIcon(new ImageIcon(AdminMainMenu.class.getResource("/img/nathan-anderson-iYO-EGosrCo-unsplash.jpg")));
		imgL.setBounds(0, 0, 746, 401);
		panel.add(imgL);
		setLocationRelativeTo(null);  // Center Postion

	}
	
	
}
