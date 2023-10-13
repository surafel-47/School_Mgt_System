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
public class RegStudent extends JFrame {

	private JPanel contentPane;
	private JTextField fnameF;
	private JTextField lnameF;
	private JTextField pnoF;
	private JTextField yearF;
	private JTextField monthF;
	private JTextField dateF;
	private JTextField addressF;
	private JTextField gFnameF;
	private JTextField gPnoF;
	private JComboBox gRshipC;
	private JLabel lblGender;
	private JLabel lblBirthDate;
	private JLabel lblMonth;
	private JLabel lblDay;
	private JLabel lblPhoneNumber;
	private JLabel lblAddress;
	private JPanel StuAcademicDetailsP;
	private JPanel StuGardianDetailsP;
	private JButton cancelB;
	private JButton regB;
	private JComboBox sexC;
	private JComboBox gLvlC;
	private JComboBox ecC;

	
	
	private Statement st=null; //connection Objects
	private ResultSet rs=null;   // Connection Objects
	private String gradelvls[]= {"12","11","10","9"}; ///// Grade lvls
	private String rShip[]= {"Father","Mother","Other"};  /// r/ship with student
	private String sex[]= {"M","F"};   // Sex Options
	private JLabel imgL;
	
	///////////////////////////////////////////// to get Extra-Curricular activities to String array	
	public String[] getExtraCurList() { 
		ArrayList <String> extraCurList= new ArrayList<String>();   //ArrayList to Store Data
		String list[]=null;									// String array to transfer from ArrayList 
		try {	
	        rs=st.executeQuery("SELECT name FROM extra_curricular");		// query
	        while(rs.next()) {
	        	extraCurList.add(rs.getString("name"));				// adding to ArrayList
	        }
	        list = new String[extraCurList.size()+1];	//String list obj with ArrayList + 1 size
	        list[0]=null;									// Setting First Element to empty...
	        for(int i = 1; i < list.length; i++) {
	        	list[i] = extraCurList.get(i-1);        // Tranfering form ArrayList to String Array
	        }
		}catch(Exception err){
			System.out.println(err.getMessage());
		}
	     return list;		// Returning List
	}
	//---------------------------------------------------------------------------------------------------
	
	
	//////////////////////////////////////////////////////////////  Get List oF subjects from for specfic grade
	public String [] getSubList(int lvl) {
		ArrayList <String> SubList= new ArrayList<String>();   //ArrayList to Store subjects 
		String list[]=null;									// String array to transfer subject code  from ArrayList 
		
		try {
				 if(lvl==12)
				rs=st.executeQuery("SELECT Cno FROM subject WHERE GradeLvl='12'; "); // query for subject List G12
			else if(lvl==11)
				rs=st.executeQuery("SELECT Cno FROM subject WHERE GradeLvl='11'; ");// query for subject List G11
			else if(lvl==10)
				rs=st.executeQuery("SELECT Cno FROM subject WHERE GradeLvl='10'; ");// query for subject List G10
			else if(lvl==9)
				rs=st.executeQuery("SELECT Cno FROM subject WHERE GradeLvl='9'; ");// query for subject List G9
				
			while(rs.next()) {
				SubList.add(rs.getString("cno"));				// adding to ArrayList
	        }
	        list = new String[SubList.size()];	//String list obj with ArrayList + 1 size
	        for(int i = 0; i < list.length; i++) {
	        	list[i] = SubList.get(i);        // Transfering form ArrayList to String Array
	        }
		}catch(Exception err){
			System.out.println(err.getMessage());
		}
	     return list;		// Returning List of Courses/subjects				
	}
	//---------------------------------------------------------------------------------------------------
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegStudent frame = new RegStudent();
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
	public RegStudent() {
		
		/////////////////////////////////////////////////////////////////////
		st=Connect.getConnection(Connect.UPDATE_ACCESS); // To get Connection As Soon as JFrame in Created
		//------------------------------------------------------------	
		
		Font f=new Font("Tahoma", Font.BOLD, 16);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 892, 539);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel StuRegP = new JPanel();
		StuRegP.setBounds(0, 0, 876, 500);
		contentPane.add(StuRegP);
		StuRegP.setLayout(null);
		
		JPanel StuPersonalDetailsP = new JPanel();
		StuPersonalDetailsP.setForeground(Color.WHITE);
		StuPersonalDetailsP.setOpaque(false);
		StuPersonalDetailsP.setBackground(new Color(240, 240, 240));
		StuPersonalDetailsP.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Student Personal Details", TitledBorder.LEADING, TitledBorder.TOP, f, Color.WHITE));
		StuPersonalDetailsP.setBounds(76, 19, 349, 283);
		StuRegP.add(StuPersonalDetailsP);
		StuPersonalDetailsP.setLayout(null);
		
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
			//--------------------------------------------------------------
			
		});
		fnameF.setBounds(108, 27, 86, 20);
		StuPersonalDetailsP.add(fnameF);
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
			//------------------------------------------------------------
		});
		lnameF.setBounds(108, 66, 86, 20);
		StuPersonalDetailsP.add(lnameF);
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
		pnoF.setBounds(108, 208, 109, 20);
		StuPersonalDetailsP.add(pnoF);
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
		yearF.setBounds(108, 156, 62, 20);
		StuPersonalDetailsP.add(yearF);
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
		monthF.setBounds(231, 156, 31, 20);
		StuPersonalDetailsP.add(monthF);
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
		dateF.setBounds(312, 156, 31, 20);
		StuPersonalDetailsP.add(dateF);
		dateF.setColumns(10);
		
		sexC = new JComboBox(sex);
		sexC.setBounds(108, 111, 52, 22);
		StuPersonalDetailsP.add(sexC);
		
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
		addressF.setBounds(108, 246, 168, 20);
		StuPersonalDetailsP.add(addressF);
		addressF.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("First Name");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBackground(new Color(240, 240, 240));
		lblNewLabel.setBounds(10, 27, 88, 17);
		StuPersonalDetailsP.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setForeground(Color.WHITE);
		lblLastName.setBounds(10, 66, 88, 17);
		StuPersonalDetailsP.add(lblLastName);
		lblLastName.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		lblGender = new JLabel("Gender");
		lblGender.setForeground(Color.WHITE);
		lblGender.setBounds(36, 112, 71, 17);
		StuPersonalDetailsP.add(lblGender);
		lblGender.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		lblBirthDate = new JLabel("Birth Year");
		lblBirthDate.setForeground(Color.WHITE);
		lblBirthDate.setBounds(20, 159, 78, 17);
		StuPersonalDetailsP.add(lblBirthDate);
		lblBirthDate.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		lblMonth = new JLabel("Month");
		lblMonth.setForeground(Color.WHITE);
		lblMonth.setBounds(180, 159, 46, 17);
		StuPersonalDetailsP.add(lblMonth);
		lblMonth.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		lblDay = new JLabel("Date");
		lblDay.setForeground(Color.WHITE);
		lblDay.setBounds(272, 156, 40, 17);
		StuPersonalDetailsP.add(lblDay);
		lblDay.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		lblPhoneNumber = new JLabel("Phone Num");
		lblPhoneNumber.setForeground(Color.WHITE);
		lblPhoneNumber.setBounds(6, 211, 92, 17);
		StuPersonalDetailsP.add(lblPhoneNumber);
		lblPhoneNumber.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		lblAddress = new JLabel("Address");
		lblAddress.setForeground(Color.WHITE);
		lblAddress.setBounds(27, 246, 71, 17);
		StuPersonalDetailsP.add(lblAddress);
		lblAddress.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		StuAcademicDetailsP = new JPanel();
		StuAcademicDetailsP.setOpaque(false);
		StuAcademicDetailsP.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Academic Details", TitledBorder.LEADING, TitledBorder.TOP, f, Color.WHITE));
		StuAcademicDetailsP.setBounds(465, 52, 271, 152);
		StuRegP.add(StuAcademicDetailsP);
		StuAcademicDetailsP.setLayout(null);
		
		
		gLvlC = new JComboBox(gradelvls);	////////////// Grade Levels 12,11,10,9 for CombBox 
		gLvlC.setBounds(6, 47, 57, 22);
		StuAcademicDetailsP.add(gLvlC);
		
	
		ecC = new JComboBox(  getExtraCurList()  );//////// String Array returned to extraCurCombBOX
		ecC.setForeground(new Color(0, 0, 0));
		ecC.setBackground(Color.CYAN);
		ecC.setBounds(6, 124, 170, 22);
		StuAcademicDetailsP.add(ecC);
		
		JLabel lblGradeLevel = new JLabel("Grade Level");
		lblGradeLevel.setForeground(Color.WHITE);
		lblGradeLevel.setBounds(6, 23, 86, 17);
		StuAcademicDetailsP.add(lblGradeLevel);
		lblGradeLevel.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JLabel lblExtraCurricularActivities = new JLabel("Extra Curricular Activities (Optional)");
		lblExtraCurricularActivities.setForeground(Color.WHITE);
		lblExtraCurricularActivities.setBounds(6, 96, 255, 17);
		StuAcademicDetailsP.add(lblExtraCurricularActivities);
		lblExtraCurricularActivities.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		StuGardianDetailsP = new JPanel();
		StuGardianDetailsP.setOpaque(false);
		StuGardianDetailsP.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Guardian Details (optional)", TitledBorder.LEADING, TitledBorder.TOP, f, Color.WHITE));
		StuGardianDetailsP.setBounds(77, 321, 622, 113);
		StuRegP.add(StuGardianDetailsP);
		StuGardianDetailsP.setLayout(null);
		
		gFnameF = new JTextField();
		gFnameF.addKeyListener(new KeyAdapter() {
			@Override
			
			////////////////////////////////////////////////////Validate name input
			public void keyPressed(KeyEvent e) {
				String name=gFnameF.getText();
				if(e.getKeyChar()>='a' && e.getKeyChar()<='z' || e.getKeyChar()>='A' && e.getKeyChar()<='Z') {
					if(name.length()<10)
						gFnameF.setEditable(true);
					else
						gFnameF.setEditable(false);
				}else if(e.getExtendedKeyCode()==KeyEvent.VK_BACK_SPACE)
					gFnameF.setEditable(true);
				else if(e.getKeyChar()==KeyEvent.VK_ENTER)
					gPnoF.requestFocus();
				else 
					gFnameF.setEditable(false);
				
				if(gFnameF.getText()==null || gFnameF.getText()==""){
					gPnoF.setEditable(false); gRshipC.setEditable(false);
				}else {
					gPnoF.setEditable(true); gRshipC.setEditable(true);
				}
			}
			//-----------------------------------------------------------------------------------------
			
		});
		gFnameF.setBounds(108, 40, 100, 20);
		StuGardianDetailsP.add(gFnameF);
		gFnameF.setColumns(10);
		
		gPnoF = new JTextField();
		gPnoF.addKeyListener(new KeyAdapter() {
			@Override
			///////////////////////////////////////////////////// Vaildate Pno Input
			public void keyPressed(KeyEvent e) {
				String pno=gPnoF.getText();
				if(e.getKeyChar()>='0' && e.getKeyChar()<='9') {
					if(pno.length()<10)
						gPnoF.setEditable(true);
					else
						gPnoF.setEditable(false);
				}else if(e.getExtendedKeyCode()==KeyEvent.VK_BACK_SPACE)
					gPnoF.setEditable(true);
				else if(e.getKeyChar()==KeyEvent.VK_ENTER)
						gRshipC.requestFocus();
				else 
					gPnoF.setEditable(false);
			}
			//-------------------------------------------------------------------------------------------
		
		});
		gPnoF.setEditable(false);
		gPnoF.setBounds(108, 87, 100, 20);
		StuGardianDetailsP.add(gPnoF);
		gPnoF.setColumns(10);
		
		gRshipC = new JComboBox(rShip);
		gRshipC.setEditable(true);
		gRshipC.setBounds(339, 39, 143, 22);
		StuGardianDetailsP.add(gRshipC);
		
		JLabel lblFirstName = new JLabel("First Name");
		lblFirstName.setForeground(Color.WHITE);
		lblFirstName.setBounds(10, 40, 86, 17);
		StuGardianDetailsP.add(lblFirstName);
		lblFirstName.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JLabel lblPhoneNumber_1 = new JLabel("Phone Num");
		lblPhoneNumber_1.setForeground(Color.WHITE);
		lblPhoneNumber_1.setBounds(10, 87, 92, 17);
		StuGardianDetailsP.add(lblPhoneNumber_1);
		lblPhoneNumber_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JLabel lblRelationship = new JLabel("Relationship");
		lblRelationship.setForeground(Color.WHITE);
		lblRelationship.setBounds(339, 16, 115, 17);
		StuGardianDetailsP.add(lblRelationship);
		lblRelationship.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		cancelB = new JButton("Return");
		cancelB.setForeground(Color.WHITE);
		cancelB.setFont(new Font("Tahoma", Font.BOLD, 14));
		cancelB.setBackground(Color.BLACK);
		cancelB.addActionListener(new ActionListener() {
			
		////////////////////////////////////////////////////////////// Cancel button to return to menu
		public void actionPerformed(ActionEvent e) {
			dispose();
			AdminMainMenu adminMainMenu=new AdminMainMenu();
			adminMainMenu.setVisible(true);
		}
		///-------------------------------------------------------------------------------------------
		
		});
		cancelB.setBounds(10, 457, 98, 32);
		StuRegP.add(cancelB);
		
		regB = new JButton("Register Student");
		regB.setForeground(Color.WHITE);
		regB.setBackground(Color.BLUE);
		regB.setFont(new Font("Tahoma", Font.BOLD, 14));
		regB.setIcon(new ImageIcon(RegStudent.class.getResource("/img/Add.png")));
		regB.addActionListener(new ActionListener() {
		////////////////////////////////////////////////////////////////////////////Register New Student Button
		public void actionPerformed(ActionEvent e) {
			if((fnameF.getText()).equals("") || (lnameF.getText()).equals("") ||(yearF.getText()).equals("") ||
				(monthF.getText()).equals("") || (dateF.getText()).equals("") ) {
				JOptionPane.showMessageDialog(null, "ERROR!! Some Important Fields are Empty");
			}else {
				
		 		int SID=0;// to Hold New Student SID		 		
		 		try {
		 			//////////////////////// Inserting Student Data		
			 		rs=st.executeQuery("select SID, Fname, Lname, Sex, Address, Pno, DOB, GradeLvl, EName from student;");
			 		rs.moveToInsertRow();
			 		rs.updateString("Fname", fnameF.getText()); // Inserting Student FName
			 		rs.updateString("Lname", lnameF.getText()); // Inserting Student LName
			 		rs.updateString("sex", (String)sexC.getSelectedItem()); // Inserting Student Sex (parsed)
			 		rs.updateString("Address", addressF.getText());    // Inserting Student Address
			 		rs.updateString("Pno", pnoF.getText());    // Inserting Student Phone_no		 		
			 		rs.updateInt("GradeLvl",Integer.parseInt((String)gLvlC.getSelectedItem() ) );// Inserting Student Grade lvl
			 		rs.updateString("Ename", (String)ecC.getSelectedItem());    // Inserting Student Extracurricular
			 		rs.updateString("DOB", yearF.getText()+"-"+ monthF.getText()+"-"+dateF.getText()); // Inserting DOB
			 		rs.insertRow();
			 		rs.close();
			 		///-------------------------------------- Student Data Inserted
			 
			 ///////////////////////////////////////////////////////////// Getting SID of Last Added Student (for Assigning Guardian and Subjects)
			 	   rs=st.executeQuery("select max(SID) from student;");// Query to Get SID of Last Added Student 
		 	       rs.next(); SID=rs.getInt("max(SID)"); rs.close();// Storing SID of last added Student and closing result set		
			 //-------------------------------------------------------
			 	
	
			 ///////////////////////////////////////////////////////////////////////Inserting Guardian Data
			 if(!gFnameF.getText().equals("")) {
				   rs=st.executeQuery("select FName, SID, pno, relationship from gardian "); // Query To access Gardian table
				   rs.moveToInsertRow();
    			   rs.updateInt("SID", SID);						// Inserting Student SID (used as foriegin Key)
			 	   rs.updateString("Fname", gFnameF.getText());	// Inserting Guardian name.
			       rs.updateString("Pno", gPnoF.getSelectedText()); // Inserting Guardian Phone_no
			 	   rs.updateString("RelationShip", (String)gRshipC.getSelectedItem());// Inserting RelationShip Description
			 	   rs.insertRow();
			 	   rs.close();	
			  }
	         //------------------------------------------------------------------------------Guardian Data Inserted																					

			 		///////////////////////////////////////////Assigning Subject to New Registered Student
			 		String subList[]=null;	// Array To Hold Subject List.......
					// Below is to get Subject List based on Input Grade Level
					 	  if((String)gLvlC.getSelectedItem()=="12")  subList=getSubList(12); 
					 else if((String)gLvlC.getSelectedItem()=="11")  subList=getSubList(11); 
					 else if((String)gLvlC.getSelectedItem()=="10")  subList=getSubList(10); 
					 else if((String)gLvlC.getSelectedItem()=="9")   subList=getSubList(9);
					 	  
					 rs=st.executeQuery("SELECT SID, Cno FROM takes");	
					 for(int i=0; i<subList.length;i++) {
						 rs.moveToInsertRow();
						 rs.updateInt("SID", SID);
						 rs.updateString("cno", subList[i]);
						 rs.insertRow();
					 }
					 rs.close();			 
					 //////////////////////////////////////////////////////////////////////////////////
					  
				 	 JOptionPane.showMessageDialog(null, "New Student : "+fnameF.getText()+" "+lnameF.getText()+"\nID: " + SID + "\nRegistered","Registration Successfull",1);
				 	 dispose();
				 	 AdminMainMenu adminMainMenu=new AdminMainMenu();
				 	adminMainMenu.setVisible(true);
		 		}catch(Exception err) {  
					JOptionPane.showMessageDialog(null, "Error Description: " + err.getMessage(),"Error Has Occurred!!",0);
				}	
			}			
		}
		//--------------------------------------------Student Registration Finished
		
		});
		regB.setBounds(354, 440, 213, 49);
		StuRegP.add(regB);
		
		imgL = new JLabel("");
		imgL.setIcon(new ImageIcon(RegStudent.class.getResource("/img/bk.jpg")));
		imgL.setBounds(0, 0, 876, 500);
		StuRegP.add(imgL);
		setLocationRelativeTo(null);  // Center Postion

	}
}
