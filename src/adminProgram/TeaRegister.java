package adminProgram;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.border.TitledBorder;
import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.*;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import connectionProgram.Connect;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.border.EtchedBorder;
public class TeaRegister extends JFrame {

	private JPanel contentPane;
	private JTextField fnameF;
	private JTextField lnameF;
	private JTextField pnoF;
	private JTextField yearF;
	private JTextField monthF;
	private JTextField dateF;
	private JTextField addressF;
	private JLabel lblGender;
	private JLabel lblBirthDate;
	private JLabel lblMonth;
	private JLabel lblDay;
	private JLabel lblPhoneNumber;
	private JLabel lblAddress;
	private JPanel TeaJobAndSalaryP;
	private JButton cancelB;
	private JButton regB;
	private JComboBox sexC;
	private String sex[]= {"M","F"};
	private JTextField titleF;
	private JTextField salaryF;
	private JLabel lblSalary;
	

	private Statement st=null; //connection Objects
	private ResultSet rs=null;   // Connection Objects
	private JLabel subjectL;
	private JLabel imgL;

	
	//////////////////////////////////////////////////////////////  Get List oF unoccupied subjects
	public String [] getSubList() {
		ArrayList <String> SubList= new ArrayList<String>();   //ArrayList to Store subjects 
		String list[]=null;									// String array to transfer subject code  from ArrayList 
		
		//////////////////////////////////// a query to find unoccupied subjects (that have no teachers)
		String sql="SELECT subject.Cno FROM subject WHERE cno NOT IN (SELECT instructor.cno FROM instructor) ";
		try {
			rs=st.executeQuery(sql);	
			while(rs.next()) {
				SubList.add(rs.getString("cno"));				// adding to ArrayList
	        }
	        list = new String[SubList.size()];	//String list obj with ArrayList
	        for(int i = 0; i < list.length; i++) {
	        	list[i] = SubList.get(i);        // Transfering form ArrayList to String Array
	        }
	        rs.close();
		}catch(Exception err){
			System.out.println(err.getMessage());
		}
	     return list;		// Returning List of unoccupied Courses/subjects				
	}
	//--------------------------------------------------------------------------
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TeaRegister frame = new TeaRegister();
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
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public TeaRegister() {
		setResizable(false);
		
		/////////////////////////////////////////////////////////////////////
		st=Connect.getConnection(Connect.UPDATE_ACCESS); // To get Connection As Soon as JFrame in Created
		//------------------------------------------------------------	
		
		Font f=new Font("Tahoma", Font.BOLD, 16);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 788, 432);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel TeaRegP = new JPanel();
		TeaRegP.setBounds(0, 0, 783, 405);
		contentPane.add(TeaRegP);
		TeaRegP.setLayout(null);
		
		JPanel TeaPersonalDetailsP = new JPanel();
		TeaPersonalDetailsP.setOpaque(false);
		TeaPersonalDetailsP.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Teacher Personal Details", TitledBorder.LEADING, TitledBorder.TOP, f, Color.WHITE));
		TeaPersonalDetailsP.setBounds(10, 11, 532, 283);
		TeaRegP.add(TeaPersonalDetailsP);
		TeaPersonalDetailsP.setLayout(null);
		
		fnameF = new JTextField();
		fnameF.addKeyListener(new KeyAdapter() {
			@Override
			
			////////////////////////////////////////////////////////Validate Name Input
			public void keyPressed(KeyEvent e) {
				String name=fnameF.getText();
				if(e.getKeyChar()>='a' && e.getKeyChar()<='z' || e.getKeyChar()>='A' && e.getKeyChar()<='Z') {
				if(name.length()<10)
					fnameF.setEditable(true);
				else
					fnameF.setEditable(false);
				}else if(e.getKeyChar()==KeyEvent.VK_ENTER) 
					lnameF.requestFocus(); // to shift to last name field
				else if(e.getExtendedKeyCode()==KeyEvent.VK_BACK_SPACE)
						fnameF.setEditable(true);
				else 
						fnameF.setEditable(false);
			}
			//--------------------------------------------------------------------------------
			
		});
		fnameF.setBounds(252, 27, 86, 20);
		TeaPersonalDetailsP.add(fnameF);
		fnameF.setColumns(10);
		
		lnameF = new JTextField();
		lnameF.addKeyListener(new KeyAdapter() {
			@Override
			
			////////////////////////////////////////////////////////Validate Name Input	
			public void keyPressed(KeyEvent e) {			
				String name=lnameF.getText();
				if(e.getKeyChar()>='a' && e.getKeyChar()<='z' || e.getKeyChar()>='A' && e.getKeyChar()<='Z') {
					if(name.length()<10)
						lnameF.setEditable(true);
					else
						lnameF.setEditable(false);
				}else if(e.getExtendedKeyCode()==KeyEvent.VK_BACK_SPACE)
						lnameF.setEditable(true);
				else if(e.getKeyChar()==KeyEvent.VK_ENTER)
					yearF.requestFocus();
				else 
						lnameF.setEditable(false);
			}
			//-----------------------------------------------------------------------
		});
		lnameF.setBounds(436, 27, 86, 20);
		TeaPersonalDetailsP.add(lnameF);
		lnameF.setColumns(10);
		
		pnoF = new JTextField();
		pnoF.addKeyListener(new KeyAdapter() {
			@Override
			
			////////////////////////////////////////////////////////  Validate PhoneNo Input	
			public void keyPressed(KeyEvent e) {
				String pno=pnoF.getText();
				if(e.getKeyChar()>='0' && e.getKeyChar()<='9') {
					if(pno.length()<10)
						pnoF.setEditable(true);
					else
						pnoF.setEditable(false);
				}else if(e.getExtendedKeyCode()==KeyEvent.VK_BACK_SPACE)
						pnoF.setEditable(true);
				else if(e.getKeyChar()==KeyEvent.VK_ENTER)
						addressF.requestFocus();
				else 
						pnoF.setEditable(false);
			}
			//------------------------------------------------------------------------------------
			
		});
		pnoF.setBounds(112, 145, 109, 20);
		TeaPersonalDetailsP.add(pnoF);
		pnoF.setColumns(10);
		
		yearF = new JTextField();
		yearF.addKeyListener(new KeyAdapter() {
			@Override
			
			////////////////////////////////////////////////////////Validate Year Input	
			public void keyPressed(KeyEvent e) {
				String year=yearF.getText();
				if(e.getKeyChar()>='0' && e.getKeyChar()<='9') {
					if(year.length()<4)
						yearF.setEditable(true);
					else
						yearF.setEditable(false);
				}else if(e.getKeyChar()==KeyEvent.VK_ENTER) 
					monthF.requestFocus();
				 else if(e.getExtendedKeyCode()==KeyEvent.VK_BACK_SPACE)
						yearF.setEditable(true);
				 else 
						yearF.setEditable(false);
			}
			//------------------------------------------------------------------------------------
			
		});
		yearF.setBounds(112, 103, 62, 20);
		TeaPersonalDetailsP.add(yearF);
		yearF.setColumns(10);
		
		monthF = new JTextField();
		monthF.addKeyListener(new KeyAdapter() {
			@Override
			////////////////////////////////////////////////////////Validate Month Input
			public void keyReleased(KeyEvent e) {
				try {
				if(e.getKeyChar()>='0' && e.getKeyChar()<='9') {
					if(Integer.parseInt(monthF.getText())>12 || Integer.parseInt(monthF.getText())==0)
							monthF.setText("");
				}else if(e.getExtendedKeyCode()==KeyEvent.VK_BACK_SPACE)
					monthF.setEditable(true);
				else 
					monthF.setText("");
				}catch(Exception err) {
					///// Typed to fast Exception....
				}
				
			}
			//---------------------------------------------------------------------------------------
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyChar()==KeyEvent.VK_ENTER) 
					dateF.requestFocus();		// to Shift to date Field
			}
		});
		monthF.setBounds(235, 103, 31, 20);
		TeaPersonalDetailsP.add(monthF);
		monthF.setColumns(10);
		
		dateF = new JTextField();
		dateF.addKeyListener(new KeyAdapter() {
			@Override
			/////////////////////////////////////////// Validate Date Input
			public void keyReleased(KeyEvent e) {

				try {
					if(e.getKeyChar()>='0' && e.getKeyChar()<='9') {
						if(Integer.parseInt(dateF.getText())>31)
							dateF.setText("");
					}else if(e.getExtendedKeyCode()==KeyEvent.VK_BACK_SPACE)
					dateF.setEditable(true);
					else 
					dateF.setText("");
				}catch(Exception err) {
					///// Typed to fast Exception....
				}
			}
			//---------------------------------------------------------------------------------------
			@Override
			public void keyPressed(KeyEvent e) {
				 if(e.getKeyChar()==KeyEvent.VK_ENTER) 
					pnoF.requestFocus();			// To shift to Phone Number Field
			}
		});
		dateF.setBounds(316, 103, 31, 20);
		TeaPersonalDetailsP.add(dateF);
		dateF.setColumns(10);
		
		sexC = new JComboBox(sex);
		sexC.setBounds(112, 58, 52, 22);
		TeaPersonalDetailsP.add(sexC);
		
		addressF = new JTextField();
		addressF.addKeyListener(new KeyAdapter() {
			@Override
			////////////////////////////////////////////////////////Validate Address  Input	
			public void keyPressed(KeyEvent e) {
				String address=addressF.getText();
				if( (e.getKeyChar()>='a' && e.getKeyChar()<='z') || 
					(e.getKeyChar()>='A' && e.getKeyChar()<='Z') || 
					(e.getExtendedKeyCode()==KeyEvent.VK_SPACE)  ||
					(e.getKeyChar()>='0' && e.getKeyChar()<='9')  ) {
					if(address.length()<20)
						addressF.setEditable(true);
					else
						addressF.setEditable(false);
				}else if(e.getExtendedKeyCode()==KeyEvent.VK_BACK_SPACE )
					addressF.setEditable(true);
				else 
					addressF.setEditable(false);
			}
			//---------------------------------------------------------------------------------------
		});
		addressF.setBounds(112, 183, 168, 20);
		TeaPersonalDetailsP.add(addressF);
		addressF.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("First Name");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(171, 27, 86, 17);
		TeaPersonalDetailsP.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setForeground(Color.WHITE);
		lblLastName.setBounds(348, 27, 88, 17);
		TeaPersonalDetailsP.add(lblLastName);
		lblLastName.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		lblGender = new JLabel("Gender");
		lblGender.setForeground(Color.WHITE);
		lblGender.setBounds(40, 59, 62, 17);
		TeaPersonalDetailsP.add(lblGender);
		lblGender.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		lblBirthDate = new JLabel("Birth Year");
		lblBirthDate.setForeground(Color.WHITE);
		lblBirthDate.setBounds(22, 106, 80, 17);
		TeaPersonalDetailsP.add(lblBirthDate);
		lblBirthDate.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		lblMonth = new JLabel("Month");
		lblMonth.setForeground(Color.WHITE);
		lblMonth.setBounds(184, 106, 46, 17);
		TeaPersonalDetailsP.add(lblMonth);
		lblMonth.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		lblDay = new JLabel("Date");
		lblDay.setForeground(Color.WHITE);
		lblDay.setBounds(276, 103, 40, 17);
		TeaPersonalDetailsP.add(lblDay);
		lblDay.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		lblPhoneNumber = new JLabel("Phone Num");
		lblPhoneNumber.setForeground(Color.WHITE);
		lblPhoneNumber.setBounds(10, 145, 92, 17);
		TeaPersonalDetailsP.add(lblPhoneNumber);
		lblPhoneNumber.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		lblAddress = new JLabel("Address");
		lblAddress.setForeground(Color.WHITE);
		lblAddress.setBounds(40, 183, 62, 17);
		TeaPersonalDetailsP.add(lblAddress);
		lblAddress.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JLabel lblTitle = new JLabel("Title");
		lblTitle.setForeground(Color.WHITE);
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTitle.setBounds(59, 27, 40, 17);
		TeaPersonalDetailsP.add(lblTitle);
		
		titleF = new JTextField();
		titleF.addKeyListener(new KeyAdapter() {
			@Override
			
			////////////////////////////////////////////////////////Validate Name Input
			public void keyPressed(KeyEvent e) {
				String title=titleF.getText();
				if(e.getKeyChar()>='a' && e.getKeyChar()<='z' || e.getKeyChar()>='A' && e.getKeyChar()<='Z') {
				if(title.length()<5)
					titleF.setEditable(true);
				else
					titleF.setEditable(false);
				}else if(e.getKeyChar()==KeyEvent.VK_ENTER) 
					fnameF.requestFocus(); // to shift to last name field
				else if(e.getExtendedKeyCode()==KeyEvent.VK_BACK_SPACE)
					titleF.setEditable(true);
				else 
					titleF.setEditable(false);
			}
			//--------------------------------------------------------------------------------
		
		});
		titleF.setColumns(10);
		titleF.setBounds(111, 27, 53, 20);
		TeaPersonalDetailsP.add(titleF);
		
		TeaJobAndSalaryP = new JPanel();
		TeaJobAndSalaryP.setOpaque(false);
		TeaJobAndSalaryP.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Job Details", TitledBorder.LEADING, TitledBorder.TOP, f, Color.WHITE));
		TeaJobAndSalaryP.setBounds(552, 26, 222, 152);
		TeaRegP.add(TeaJobAndSalaryP);
		TeaJobAndSalaryP.setLayout(null);
		
		//////////////////////////////////////////////////////////
		JComboBox cnoC = new JComboBox(getSubList()); // getting and loading unoccupied sublist to combo box
		//----------------------------------------------------------
		
		cnoC.setBounds(84, 39, 114, 22);
		TeaJobAndSalaryP.add(cnoC);
		
		salaryF = new JTextField();
		salaryF.addKeyListener(new KeyAdapter() {
			@Override
			
			////////////////////////////////////////////////////////Validate Salary Input	
			public void keyPressed(KeyEvent e) {
				String salary=salaryF.getText();
				if(e.getKeyChar()>='0' && e.getKeyChar()<='9') {
					if(salary.length()<7)
						salaryF.setEditable(true);
					else
						salaryF.setEditable(false);
				}else if(e.getExtendedKeyCode()==KeyEvent.VK_BACK_SPACE)
					salaryF.setEditable(true);
				 else 
					 salaryF.setEditable(false);
			}
			//------------------------------------------------------------------------------------
			
		});
		salaryF.setBounds(84, 72, 62, 20);
		TeaJobAndSalaryP.add(salaryF);
		salaryF.setColumns(10);
		
		lblSalary = new JLabel("Salary");
		lblSalary.setForeground(Color.WHITE);
		lblSalary.setBounds(20, 72, 46, 17);
		TeaJobAndSalaryP.add(lblSalary);
		lblSalary.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		subjectL = new JLabel("Subject");
		subjectL.setForeground(Color.WHITE);
		subjectL.setFont(new Font("Tahoma", Font.BOLD, 14));
		subjectL.setBounds(10, 42, 64, 17);
		TeaJobAndSalaryP.add(subjectL);
		
		cancelB = new JButton("Return");
		cancelB.setBackground(Color.BLACK);
		cancelB.setFont(new Font("Tahoma", Font.PLAIN, 15));
		cancelB.setForeground(Color.WHITE);
		cancelB.addActionListener(new ActionListener() {
			
		////////////////////////////////////////////////////////////// Cancel button to return to menu
		public void actionPerformed(ActionEvent e) {
			dispose();
			AdminMainMenu adminMainMenu=new AdminMainMenu();
			adminMainMenu.setVisible(true);
		}
		///-------------------------------------------------------------------------------------------
		
		});
		cancelB.setBounds(21, 366, 89, 23);
		TeaRegP.add(cancelB);
		
		regB = new JButton("Register Teacher");
		regB.setBackground(Color.BLUE);
		regB.setForeground(Color.WHITE);
		regB.setIcon(new ImageIcon(TeaRegister.class.getResource("/img/Add.png")));
		regB.setFont(new Font("Tahoma", Font.BOLD, 14));
		regB.addActionListener(new ActionListener() {
			
			////////////////////////////////////////////////////////// Register Teacher Button Action taken
			public void actionPerformed(ActionEvent e) {
				if((fnameF.getText()).equals("") || (lnameF.getText()).equals("") ||(yearF.getText()).equals("") ||
				  (monthF.getText()).equals("") || (dateF.getText()).equals("")  || (salaryF.getText().equals(""))) {
					
					JOptionPane.showMessageDialog(null, "ERROR!! Some Important Fields are Empty");
					return;
				}
				
				try {
					cnoC.getSelectedItem().equals("");
				}catch(Exception err){
					JOptionPane.showMessageDialog(null, "All Postions are Occupied!");
					return;
				}
						
					
				
				{					 		
				 		////////////////////////Inserting Instructor Data		
				 		try {		
					 		rs=st.executeQuery("SELECT * FROM Instructor;");
					 		rs.moveToInsertRow();
					 		rs.updateString("title", titleF.getText()); // Inserting Instructor FName
					 		rs.updateString("fname", fnameF.getText()); // Inserting Instructor FName
					 		rs.updateString("lname", lnameF.getText()); // Inserting Instructor LName
					 		rs.updateString("sex", (String)sexC.getSelectedItem()); // Inserting Instructor Sex (parsed)
					 		rs.updateString("Address", addressF.getText());    // Inserting Instructor Address
					 		rs.updateString("Pno", pnoF.getText());    // Inserting Instructor Phone_no		 		
					 		rs.updateInt("salary",Integer.parseInt(salaryF.getText()));// Inserting Instructor Salart
					 		rs.updateString("cno", (String)cnoC.getSelectedItem());    // Inserting Instructor Course taught
					 		rs.updateString("DOB", yearF.getText()+"-"+ monthF.getText()+"-"+dateF.getText()); // Inserting DOB
					 		rs.insertRow();
					 		rs.close();
					 		
					 		///////////////////////////////////////////////////////////// Getting TID of Last Added Instructor
					 		rs=st.executeQuery("select max(TID) from instructor;");// Query to Get TID of Last Added Student 
					 		rs.next(); 
					 		int TID=rs.getInt("max(TID)"); 
					 		rs.close();	
					 		//-------------------------------------------------------
					 		
					 	   //------------------------------------ Instructor Data Inserted																							  
						 	 JOptionPane.showMessageDialog(null, "New Instructor : "+fnameF.getText()+" "+lnameF.getText()+"\nTID: "+TID +"\n Added To DataBase","Registration Successfull",1);
						 	 dispose();
						 	 AdminMainMenu adminMainMenu=new AdminMainMenu();
						 	adminMainMenu.setVisible(true);
				 		}catch(Exception err) {  
							JOptionPane.showMessageDialog(null, "Error Description: " + err.getMessage(),"Error Has Occurred!!",0);
						}	
					}	
			}
			//------------------------------------------------------------------------
			
		});
		regB.setBounds(299, 316, 226, 46);
		TeaRegP.add(regB);
		
		imgL = new JLabel("");
		imgL.setIcon(new ImageIcon(TeaRegister.class.getResource("/img/gradient-liquid-abstract-background_52683-60469.jpg")));
		imgL.setBounds(0, 0, 783, 405);
		TeaRegP.add(imgL);
		setLocationRelativeTo(null);  // Center Postion
	}
}
