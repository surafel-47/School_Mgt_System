package studentProgram;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.border.TitledBorder;
import javax.swing.JTextArea;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import connectionProgram.Connect;
import javax.swing.ImageIcon;
import javax.swing.border.EtchedBorder;

public class StuMenu extends JFrame implements AvgGradeCalc {
	
	private JPanel contentPane;
	private JPanel ViewPersonalDetails;
	private JTextField fnameF;
	private JTextField lnameF;
	private JTextField pnoF;
	private JTextField addressF;
	private JTextField DOBF;
	private JTextField sexF;
	private JTextField gradeLvlF;
	private JTextField gFnameF;
	private JTextField gPnoF;
	private JTextField gRshipF;
	private JTable markTable;
	private JTextField extraCurrNameF;
	private JTextField SIDF;
	private JTextArea descF;
	private JButton updateB;
	DefaultTableModel mTableModel;
	private JButton refreshB;
	private JTextField currentPassWordF;
	private JTextField newPassWordF;
	
	
	
	private String fname,lname,SID,sex,gradeLvl,DOB,pno,address,Ename,passWord; //	To Hold Student Info
	private String gFname,gPno,gRship;	// To Hold Guardian name and Description..
	private String extraCurrName, eDesc; // To Hold Extra Curricular name and description.. 	
	
	private Statement st=null; //connection Objects
	private ResultSet rs=null;   // Connection Objects
	private JTextField avgF;
	
	
	///////////////////////////////////////////////Method To Get and Set All Fields For Student Details.
	public void getAndSetStuDetails() {
		String sql;
		try {
			
			////////////////////////////////////////////////// Getting Students Data.
			sql= "SELECT * FROM Student WHERE SID='"+ SID + "' ";
			rs=st.executeQuery(sql);	
			rs.next();
			fname = rs.getString("Fname");
			lname = rs.getString("Lname");
			sex = rs.getString("Sex");
			address = rs.getString("Address");
			pno = rs.getString("Pno");
			DOB=rs.getString("DOB");
			gradeLvl = rs.getString("GradeLvl");
			Ename = rs.getString("EName");
			passWord =rs.getString("PassWord");
			rs.close();
			//---------------------------------------------------------
			
			////////////////////////////////////////////////// Getting Guardian Data if Exists
			sql="SELECT * FROM Gardian WHERE SID= '" + SID + "' "; 
			rs=st.executeQuery(sql);	
			if(rs.next()) {		// Checking if Guardian Exists
				gFname = rs.getString("Fname");
				gPno = rs.getString("Pno");
				gRship = rs.getString("relationship");
				 rs.close();
			}
			//---------------------------------------------------------
			
			
			/////////////////////////////////////////////////////Getting Extra_Curr_Activity Stu Has
			sql="SELECT * FROM extra_curricular WHERE Name= '" + Ename + "' "; 
			rs=st.executeQuery(sql);	
			if(rs.next()) {		// Checking if Student Has Extra Curricular Activities
				extraCurrName =rs.getString("name");
				eDesc= rs.getString("Description");
				 rs.close();
			}
			//------------------------------------------------------

			//////////////////////////////////////////////////////////Setting All TextFields
				fnameF.setText(fname); lnameF.setText(lname);  SIDF.setText(SID);
				sexF.setText(sex); gradeLvlF.setText(gradeLvl); DOBF.setText(DOB);
				pnoF.setText(pno);addressF.setText(address);
				extraCurrNameF.setText(extraCurrName); descF.setText(eDesc);
				gFnameF.setText(gFname); gPnoF.setText(gPno ); gRshipF.setText(gRship);
				currentPassWordF.setText(passWord);
			//-----------------------------------------------------------	
		
		} catch (Exception err) {
			JOptionPane.showMessageDialog(null,err.getMessage());
		}		
	}	
	//------------------------------------------------------------------------------

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
	        rs.close();
		}catch(Exception err){
			System.out.println(err.getMessage());
		}
	     return list;		// Returning List
	}
	//---------------------------------------------------------------------------------------------------
	
	/////////////////////////////////////////////////////////////////////Method To Get and Set Marks to Table
	public void getStuMarks() {
		DefaultTableModel markTableModel= (DefaultTableModel) markTable.getModel();
		String row[]=new String[3];		
		String sql = "SELECT takes.Cno, subject.CName ,takes.FinalMark "
			    	+ "FROM takes,subject WHERE SID=' " + SID + "' AND takes.Cno = subject.Cno";
		try { 
			rs=st.executeQuery(sql);
			markTableModel.setRowCount(0); // Clearing The table of Previous Data if any exists
			while(rs.next()) {
				row [0]=rs.getString("Cno"); 
				row [1]=rs.getString("CName");
				row [2]=rs.getString("FinalMark");
				markTableModel.addRow(row);
			}		
			rs.close();
			 
			avgF.setText( gradeCalc() );   // Calculating Avg Grade 
			 
		}catch(Exception err) {
			System.out.println(err.getMessage());
		}
	}
	//------------------------------------------------------------------		
	
	////////////////////////////////////////// Overiding Method That Implements Grade Calulator
	public String gradeCalc() {  // a method to calc Average Grade and Return it in String
		double no=0,sum=0, avg=0 ;
		try { 
			rs=st.executeQuery("Select FinalMark From Takes WHERE SID= ' " + SID + " ' ");
			while(rs.next()) {
				if(rs.getInt("FinalMark") == 0) {
					return "Subjects Empty";
				}else {					
				  sum=sum+rs.getInt("FinalMark");
				  no++;
				}
			}
			 avg=sum/no;   // Calculating Avergae
			 return Double.toString((int) avg);  // returning Average in String Format
		}catch(Exception err) {
			System.out.println(err.getMessage());
		}
		return ""; 
	}
	//----------------------------------------------------------------------------------------------
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StuMenu frame = new StuMenu("0");
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
	

	public StuMenu(String id) {
		setResizable(false); // parameter ID to set Student SID
		
		SID=id;// Setting SID of Student to Access His/Her Profile and Subjects Taken
		
		/////////////////////////////////////////////////////////////////////
		st=Connect.getConnection(Connect.UPDATE_ACCESS); // To get Connection As Soon as JFrame in Created
		//------------------------------------------------------------	
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 628, 530);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 623, 461);
		contentPane.add(tabbedPane);
		
		JPanel ViewGrade = new JPanel();
		tabbedPane.addTab("View Grades", null, ViewGrade, null);
		tabbedPane.setForegroundAt(0, Color.WHITE);
		tabbedPane.setBackgroundAt(0, Color.DARK_GRAY);
		ViewGrade.setLayout(null);
		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(128, 40, 349, 135);
		ViewGrade.add(scrollPane);
		

		markTable = new JTable();
		markTable.setBackground(Color.GRAY);
		markTable.setFont(new Font("Tahoma", Font.BOLD, 12));
		markTable.setForeground(new Color(0, 0, 0));
		markTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Course No", "Course Name", "Mark"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, String.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		markTable.getColumnModel().getColumn(0).setResizable(false);
		markTable.getColumnModel().getColumn(0).setPreferredWidth(62);
		markTable.getColumnModel().getColumn(1).setResizable(false);
		markTable.getColumnModel().getColumn(1).setPreferredWidth(94);
		markTable.getColumnModel().getColumn(2).setResizable(false);
		markTable.getColumnModel().getColumn(2).setPreferredWidth(41);
		scrollPane.setViewportView(markTable);
		markTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		
		//currentPassWordF
		
		refreshB = new JButton("Refresh");
		refreshB.setForeground(Color.WHITE);
		refreshB.setBackground(Color.DARK_GRAY);
		refreshB.setFont(new Font("Tahoma", Font.BOLD, 12));
		refreshB.addActionListener(new ActionListener() {
			
			////////////////////////////////////// Refresh Button Reloads Marks Data to Table
			public void actionPerformed(ActionEvent e) {
				getStuMarks();	// Method to Refresh Data On Mark Table Again
				getAndSetStuDetails(); // Set Student personal details Again
			}
			//------------------------------------------------------------------
		
		});
		refreshB.setBounds(138, 188, 89, 23);
		ViewGrade.add(refreshB);
		
		avgF = new JTextField();
		avgF.setBounds(391, 186, 86, 20);
		ViewGrade.add(avgF);
		avgF.setColumns(10);
		
		JLabel avgL = new JLabel("Average");
		avgL.setForeground(Color.WHITE);
		avgL.setFont(new Font("Tahoma", Font.BOLD, 14));
		avgL.setBounds(309, 192, 72, 19);
		ViewGrade.add(avgL);
		
		JLabel img = new JLabel("");
		img.setIcon(new ImageIcon(StuMenu.class.getResource("/img/suii.jpg")));
		img.setBounds(0, 0, 618, 444);
		ViewGrade.add(img);
		
		ViewPersonalDetails = new JPanel();
		tabbedPane.addTab("View Personal Details", null, ViewPersonalDetails, null);
		tabbedPane.setBackgroundAt(1, Color.DARK_GRAY);
		tabbedPane.setForegroundAt(1, Color.WHITE);
		ViewPersonalDetails.setLayout(null);
		
		JPanel StuDetailsP = new JPanel();
		StuDetailsP.setOpaque(false);
		StuDetailsP.setFont(new Font("Tahoma", Font.PLAIN, 15));
		StuDetailsP.setBounds(21, 7, 344, 234);
		StuDetailsP.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Your Personal Details", TitledBorder.LEADING, TitledBorder.TOP, null, Color.WHITE));
		ViewPersonalDetails.add(StuDetailsP);
		StuDetailsP.setLayout(null);
		
		fnameF = new JTextField();
		fnameF.setFont(new Font("Tahoma", Font.BOLD, 14));
		fnameF.setEditable(false);
		fnameF.setBounds(113, 18, 109, 20);
		StuDetailsP.add(fnameF);
		fnameF.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("First Name");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(20, 18, 83, 17);
		StuDetailsP.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setForeground(Color.WHITE);
		lblLastName.setBounds(20, 46, 83, 17);
		StuDetailsP.add(lblLastName);
		lblLastName.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		lnameF = new JTextField();
		lnameF.setFont(new Font("Tahoma", Font.BOLD, 14));
		lnameF.setEditable(false);
		lnameF.setBounds(113, 46, 109, 20);
		StuDetailsP.add(lnameF);
		lnameF.setColumns(10);
		
		JLabel lblGender = new JLabel("Gender");
		lblGender.setForeground(Color.WHITE);
		lblGender.setBounds(46, 79, 53, 17);
		StuDetailsP.add(lblGender);
		lblGender.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		pnoF = new JTextField();
		pnoF.setFont(new Font("Tahoma", Font.BOLD, 14));
		pnoF.setEditable(false);
		pnoF.setBounds(113, 174, 119, 20);
		StuDetailsP.add(pnoF);
		pnoF.setColumns(10);
		
		addressF = new JTextField();
		addressF.setFont(new Font("Tahoma", Font.BOLD, 14));
		addressF.setEditable(false);
		addressF.setBounds(113, 203, 190, 20);
		StuDetailsP.add(addressF);
		addressF.setColumns(10);
		
		JLabel lblAddress = new JLabel("Address");
		lblAddress.setForeground(Color.WHITE);
		lblAddress.setBounds(33, 205, 70, 17);
		StuDetailsP.add(lblAddress);
		lblAddress.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		DOBF = new JTextField();
		DOBF.setFont(new Font("Tahoma", Font.BOLD, 14));
		DOBF.setEditable(false);
		DOBF.setBounds(113, 143, 109, 20);
		StuDetailsP.add(DOBF);
		DOBF.setColumns(10);
		
		JLabel lblBirthDate = new JLabel("Birth Date");
		lblBirthDate.setForeground(Color.WHITE);
		lblBirthDate.setBounds(20, 145, 70, 17);
		StuDetailsP.add(lblBirthDate);
		lblBirthDate.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JLabel lblPhoneNumber = new JLabel("Phone Num");
		lblPhoneNumber.setForeground(Color.WHITE);
		lblPhoneNumber.setBounds(10, 176, 93, 17);
		StuDetailsP.add(lblPhoneNumber);
		lblPhoneNumber.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		sexF = new JTextField();
		sexF.setFont(new Font("Tahoma", Font.BOLD, 14));
		sexF.setEditable(false);
		sexF.setBounds(113, 77, 31, 20);
		StuDetailsP.add(sexF);
		sexF.setColumns(10);
		
		gradeLvlF = new JTextField();
		gradeLvlF.setFont(new Font("Tahoma", Font.BOLD, 14));
		gradeLvlF.setEditable(false);
		gradeLvlF.setColumns(10);
		gradeLvlF.setBounds(113, 112, 31, 20);
		StuDetailsP.add(gradeLvlF);
		
		JLabel lblGrade = new JLabel("Grade");
		lblGrade.setForeground(Color.WHITE);
		lblGrade.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblGrade.setBounds(54, 115, 52, 17);
		StuDetailsP.add(lblGrade);
		
		SIDF = new JTextField();
		SIDF.setFont(new Font("Tahoma", Font.BOLD, 14));
		SIDF.setEditable(false);
		SIDF.setColumns(10);
		SIDF.setBounds(288, 18, 46, 20);
		StuDetailsP.add(SIDF);
		
		JLabel lblSid = new JLabel("SID");
		lblSid.setForeground(Color.WHITE);
		lblSid.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblSid.setBounds(247, 20, 31, 17);
		StuDetailsP.add(lblSid);
		
		JPanel GuardianP = new JPanel();
		GuardianP.setOpaque(false);
		GuardianP.setBounds(384, 7, 224, 119);
		GuardianP.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Guardian", TitledBorder.LEADING, TitledBorder.TOP, null, Color.WHITE));
		ViewPersonalDetails.add(GuardianP);
		GuardianP.setLayout(null);
		
		gFnameF = new JTextField();
		gFnameF.setFont(new Font("Tahoma", Font.BOLD, 14));
		gFnameF.setEditable(false);
		gFnameF.setBounds(104, 16, 100, 20);
		GuardianP.add(gFnameF);
		gFnameF.setColumns(10);
		
		gPnoF = new JTextField();
		gPnoF.setFont(new Font("Tahoma", Font.PLAIN, 14));
		gPnoF.setEditable(false);
		gPnoF.setBounds(104, 47, 100, 20);
		GuardianP.add(gPnoF);
		gPnoF.setColumns(10);
		
		JLabel lblFirstName = new JLabel("First Name");
		lblFirstName.setForeground(Color.WHITE);
		lblFirstName.setBounds(10, 16, 88, 17);
		GuardianP.add(lblFirstName);
		lblFirstName.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JLabel lblPhoneNumber_1 = new JLabel("Phone Num");
		lblPhoneNumber_1.setForeground(Color.WHITE);
		lblPhoneNumber_1.setBounds(6, 45, 92, 17);
		GuardianP.add(lblPhoneNumber_1);
		lblPhoneNumber_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JLabel lblRelationship = new JLabel("Relationship");
		lblRelationship.setForeground(Color.WHITE);
		lblRelationship.setBounds(10, 80, 92, 17);
		GuardianP.add(lblRelationship);
		lblRelationship.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		gRshipF = new JTextField();
		gRshipF.setFont(new Font("Tahoma", Font.BOLD, 14));
		gRshipF.setEditable(false);
		gRshipF.setBounds(104, 78, 100, 20);
		GuardianP.add(gRshipF);
		gRshipF.setColumns(10);
		
		JPanel ExtraCurrP = new JPanel();
		ExtraCurrP.setOpaque(false);
		ExtraCurrP.setBounds(21, 252, 552, 192);
		ExtraCurrP.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Extra Curricular Activity", TitledBorder.LEADING, TitledBorder.TOP, null, Color.WHITE));
		ViewPersonalDetails.add(ExtraCurrP);
		ExtraCurrP.setLayout(null);
		
		descF = new JTextArea();
		descF.setBackground(Color.GRAY);
		descF.setBounds(95, 75, 447, 101);
		ExtraCurrP.add(descF);
		descF.setWrapStyleWord(true);
		descF.setText((String) null);
		descF.setLineWrap(true);
		descF.setFont(new Font("Poor Richard", Font.PLAIN, 21));
		descF.setEditable(false);
		
		JLabel lblDescription = new JLabel("Description");
		lblDescription.setForeground(Color.WHITE);
		lblDescription.setBounds(6, 78, 79, 22);
		ExtraCurrP.add(lblDescription);
		lblDescription.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		JComboBox nameC = new JComboBox(getExtraCurList());//////// String Array returned to extraCurCombBOX 
		nameC.setBounds(96, 16, 166, 22);
		ExtraCurrP.add(nameC);
		
		JLabel selectL = new JLabel("Select");
		selectL.setForeground(Color.WHITE);
		selectL.setBounds(30, 14, 43, 22);
		ExtraCurrP.add(selectL);
		selectL.setVisible(false);
		selectL.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JCheckBox changeC = new JCheckBox("Change");
		changeC.setFont(new Font("Tahoma", Font.BOLD, 12));
		changeC.setForeground(Color.WHITE);
		changeC.setOpaque(false);
		changeC.addActionListener(new ActionListener() {
			
			//////////////////////////////////////////////// Change Extra Curricular Activity 
			public void actionPerformed(ActionEvent e) {
				if(changeC.isSelected()) {
					nameC.setVisible(true);
					updateB.setVisible(true);
					selectL.setVisible(true);
				}else {
					nameC.setVisible(false);
					updateB.setVisible(false);
					selectL.setVisible(false);
				}
			}
			//------------------------------------------------------------------------------------
			
		});
		changeC.setBounds(272, 46, 79, 22);
		ExtraCurrP.add(changeC);
		
		updateB = new JButton("Update");
		updateB.setForeground(Color.WHITE);
		updateB.setBackground(Color.DARK_GRAY);
		updateB.setFont(new Font("Tahoma", Font.BOLD, 13));
		updateB.addActionListener(new ActionListener() {
			
			///////////////////////////////////////////////////////// Update Button Action
			public void actionPerformed(ActionEvent e) {
				String sql="SELECT SID, Ename FROM Student WHERE SID='"+SID+" '";
				String selectedExtraCurr = (String) nameC.getSelectedItem();
				try {
					rs=st.executeQuery(sql);
					if(selectedExtraCurr!=""){
						rs.next();
						rs.updateString("Ename",selectedExtraCurr );
						rs.updateRow();
						rs.close();
						getAndSetStuDetails();// calling to refresh the text fields
					}
					
				}catch(Exception err) {
					JOptionPane.showMessageDialog(null,"Error !! : "+ err.getMessage());
				}
			}
			//--------------------------------------------------
			
		});
		updateB.setVisible(false);
		updateB.setBounds(272, 16, 89, 23);
		ExtraCurrP.add(updateB);
		
		JLabel lblName_2 = new JLabel("Name");
		lblName_2.setForeground(Color.WHITE);
		lblName_2.setBounds(30, 47, 43, 22);
		ExtraCurrP.add(lblName_2);
		lblName_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		extraCurrNameF = new JTextField();
		extraCurrNameF.setBounds(95, 49, 170, 20);
		ExtraCurrP.add(extraCurrNameF);
		extraCurrNameF.setFont(new Font("Tahoma", Font.BOLD, 14));
		extraCurrNameF.setEditable(false);
		extraCurrNameF.setColumns(10);
		
		JLabel img_1 = new JLabel("");
		img_1.setIcon(new ImageIcon(StuMenu.class.getResource("/img/suii.jpg")));
		img_1.setBounds(0, 0, 618, 444);
		ViewPersonalDetails.add(img_1);
		nameC.setVisible(false);
		
		JPanel changePasswordP = new JPanel();
		tabbedPane.addTab("Change PassWord", null, changePasswordP, null);
		tabbedPane.setForegroundAt(2, Color.WHITE);
		tabbedPane.setBackgroundAt(2, Color.DARK_GRAY);
		changePasswordP.setLayout(null);
		
		JButton changeB = new JButton("Change");
		changeB.setBackground(Color.DARK_GRAY);
		changeB.setForeground(Color.WHITE);
		changeB.setFont(new Font("Tahoma", Font.BOLD, 14));
		changeB.addActionListener(new ActionListener() {
			
			//////////////////////////////////////////// change PassWord Button Action Taken
			public void actionPerformed(ActionEvent e) {
				try {
					String sql="SELECT SID, PassWord from Student WHERE SID = '"+  SID + "'";
					rs=st.executeQuery(sql);
					if(rs.next()) {
						rs.updateString("PassWord", newPassWordF.getText());
						rs.updateRow();
						rs.close();
						
					}
					getAndSetStuDetails();  // To Refresh Student Personal Details successful 
					newPassWordF.setText("");
					changeB.setEnabled(false);
					JOptionPane.showMessageDialog(null,"Change successful");
				} catch (Exception err) {
					JOptionPane.showMessageDialog(null,err.getMessage());
				}
				
			}
			//-----------------------------------------------------
			
		});
		changeB.setEnabled(false);
		changeB.setBounds(267, 198, 89, 23);
		changePasswordP.add(changeB);
		
		JButton cancelB = new JButton("Cancel");
		cancelB.setForeground(Color.WHITE);
		cancelB.setBackground(Color.DARK_GRAY);
		cancelB.setFont(new Font("Tahoma", Font.BOLD, 14));
		cancelB.addActionListener(new ActionListener() {
			
			///////////////////////////////////////////// Cancel Change Password Button Action Taken
			public void actionPerformed(ActionEvent e) {
				newPassWordF.setText("");		// Clear new PassWord Field
				updateB.setEnabled(false);		// Disable Update Button
			}
			//--------------------------------------------------------
			
		});
		cancelB.setBounds(154, 196, 89, 23);
		changePasswordP.add(cancelB);
		
		JLabel lblCurrentPassWord = new JLabel("Current Pass Word");
		lblCurrentPassWord.setForeground(Color.WHITE);
		lblCurrentPassWord.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCurrentPassWord.setBounds(77, 115, 145, 17);
		changePasswordP.add(lblCurrentPassWord);
		
		currentPassWordF = new JTextField();
		currentPassWordF.setFont(new Font("Tahoma", Font.BOLD, 11));
		currentPassWordF.setEditable(false);
		currentPassWordF.setBounds(223, 115, 59, 20);
		changePasswordP.add(currentPassWordF);
		currentPassWordF.setColumns(10);
		
		newPassWordF = new JTextField();
		newPassWordF.addKeyListener(new KeyAdapter() {
			@Override
			/////////////////////////////////// Making Sure User Enters a Valid PassWord
			public void keyPressed(KeyEvent e) {
				String newPassWord=newPassWordF.getText();
				if(e.getKeyChar()>='a' && e.getKeyChar()<='z' ||
				   e.getKeyChar()>='A' && e.getKeyChar()<='Z' ||
				   e.getKeyChar()>='0' && e.getKeyChar()<='9' ||
				   e.getExtendedKeyCode()==KeyEvent.VK_BACK_SPACE) 
				{
					newPassWordF.setEditable(true);
					if(newPassWord.length()==3)  	changeB.setEnabled(true);
					else                          	changeB.setEnabled(false);
				}
				else 
				{
					changeB.setEnabled(false);
					newPassWordF.setEditable(false);
				}	
			}
			//--------------------------------------------------------------------------
			
		});
		newPassWordF.setColumns(10);
		newPassWordF.setBounds(223, 160, 59, 20);
		changePasswordP.add(newPassWordF);
		
		JLabel lblEnterNewPass = new JLabel("Enter New Pass Word");
		lblEnterNewPass.setForeground(Color.WHITE);
		lblEnterNewPass.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblEnterNewPass.setBounds(57, 160, 159, 17);
		changePasswordP.add(lblEnterNewPass);
		
		JLabel img_2 = new JLabel("");
		img_2.setIcon(new ImageIcon(StuMenu.class.getResource("/img/suii.jpg")));
		img_2.setBounds(0, 0, 618, 444);
		changePasswordP.add(img_2);
		
		JButton logOutB = new JButton("Log Out");
		logOutB.setBackground(Color.BLACK);
		logOutB.setForeground(Color.WHITE);
		logOutB.setFont(new Font("Tahoma", Font.BOLD, 13));
		logOutB.addActionListener(new ActionListener() {
			
			////////////////////////////////////////////////////////// LogOut Button Action Taken
			public void actionPerformed(ActionEvent e) {
				dispose();
				StuLogin stuLogin=new StuLogin();
				stuLogin.setVisible(true);
			}
			//------------------------------------------------------------------------------
			
		});
		logOutB.setBounds(10, 467, 89, 23);
		contentPane.add(logOutB);
		
		JLabel img_3 = new JLabel("");
		img_3.setIcon(new ImageIcon(StuMenu.class.getResource("/img/suii.jpg")));
		img_3.setBounds(0, 0, 623, 506);
		contentPane.add(img_3);
		
		//////////////////////////////////////////Setting Student Personal Detail
		getAndSetStuDetails();
		//--------------------------------------------------------------------------
		
		/////////////////////////////////////////////////////////////////
		getStuMarks();// calling method to load marks data on to the table...
		//--------------------------------------------------------------
		
		setLocationRelativeTo(null);  // Center Postion

	}
}
