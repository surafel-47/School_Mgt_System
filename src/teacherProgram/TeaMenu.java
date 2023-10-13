package teacherProgram;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import connectionProgram.Connect;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.TitledBorder;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.border.EtchedBorder;

public class TeaMenu extends JFrame {

	private JPanel contentPane;
	private JTextField currentPassWordF;
	private JTextField newPassWordF;
	private JTextField fnameF;
	private JTextField lnameF;
	private JTextField pnoF;
	private JTextField addressF;
	private JTextField DOBF;
	private JTextField sexF;
	private JTextField cnoF;
	private JTextField TIDF;
	private JTextField titleF;
	private JTextField salaryF;
	private JTable StuMarkTable;

	DefaultTableModel StuMarkTableModel;
	private Statement st=null; //connection Objects
	private ResultSet rs=null;   // Connection Objects
	private String title,fname,lname,TID,sex,DOB,salary,cno,pno,address,currentPassWord;//	To Hold Teacher Personal Info
	private JTextField markF;
	private JTextField SIDF;
	private JButton saveB;
	private JTextField cnoFF;
	
	
	//////////////////////////////////////// To Get And Set Teacher Details To Text Fields
	void getAndSetTeaDetails() {
		
		////////////////////////////////////////////////////////Getting Teacher Details
		try {
			rs=st.executeQuery("SELECT * FROM Instructor WHERE TID='" + TID +" ' ");
			if(rs.next()) {
				title=rs.getString("title"); fname=rs.getString("fname");  lname=rs.getString("lname");
				address=rs.getString("address"); pno=rs.getString("pno"); DOB=rs.getString("DOB");
				salary=rs.getString("salary");  sex=rs.getString("sex");   cno=rs.getString("cno");
				currentPassWord=rs.getString("PassWord");
				rs.close();
			}
		} catch (Exception err) {
			JOptionPane.showMessageDialog(null,err.getMessage());
		}
		//---------------------------------------------------------------------
		
		/////////////////////////////////////////////////////Setting Teacher Details
		titleF.setText(title); fnameF.setText(fname); lnameF.setText(lname);
		addressF.setText(address); pnoF.setText(pno); DOBF.setText(DOB); TIDF.setText(TID);
		salaryF.setText(salary); sexF.setText(sex); cnoF.setText(cno); currentPassWordF.setText(currentPassWord);
		//---------------------------------------------------------------------------
	}
	//----------------------------------------------------------------------------
	
	//////////////////////////////////////////////////////Method to get Teachers Student Data To Table
	void getAndStudnetMarks(){
		StuMarkTableModel.setRowCount(0); // Clearing The table of Previous Data
		String sql= "SELECT student.SID, student.fname, student.lname, takes.cno, takes.finalMark "
					+ "From student,takes,instructor "
					+ "WHERE student.SID=takes.SID AND instructor.TID = '" +TID+ "' AND takes.cno=instructor.cno "
					+ "ORDER BY student.SID ; ";
		String tableRow[]=new String[5];
		try {
			rs=st.executeQuery(sql);
			while(rs.next()) {
				tableRow[0]=rs.getString("SID"); tableRow[1]=rs.getString("fname"); 
				tableRow[2]=rs.getString("lname"); tableRow[3]=rs.getString("cno");
				tableRow[4]=rs.getString("finalMark"); 
				StuMarkTableModel.addRow(tableRow);
			}
			rs.close();
		} catch (Exception err) {
			JOptionPane.showMessageDialog(null,err.getMessage());
		}
	}
	//--------------------------------------------------------
	
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TeaMenu frame = new TeaMenu("");
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
	public TeaMenu(String id) {
		setResizable(false); //passing TID of The Logged in Instructor
		
		TID=id;// Setting TID of Instructor to Access His/Her Profile and Students They Teach
		
		/////////////////////////////////////////////////////////////////////
		st=Connect.getConnection(Connect.UPDATE_ACCESS); // To get Connection As Soon as JFrame in Created
		//------------------------------------------------------------	
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 816, 538);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 813, 479);
		contentPane.add(tabbedPane);
		
		JPanel MarkAssignP = new JPanel();
		tabbedPane.addTab("Manage Your Students", null, MarkAssignP, null);
		tabbedPane.setForegroundAt(0, Color.WHITE);
		tabbedPane.setBackgroundAt(0, Color.DARK_GRAY);
		MarkAssignP.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(34, 37, 560, 326);
		MarkAssignP.add(scrollPane);
		
		StuMarkTable = new JTable();
		StuMarkTable.setForeground(Color.WHITE);
		StuMarkTable.setBackground(Color.DARK_GRAY);
		StuMarkTable.addMouseListener(new MouseAdapter() {
			@Override
			///////////////////////////////////////////////////// if Student is Selected
			public void mouseClicked(MouseEvent e) {
				saveB.setEnabled(true);
				int selectedRow=StuMarkTable.getSelectedRow();
				String SID = (String) StuMarkTableModel.getValueAt(selectedRow, 0);
				String mark = (String) StuMarkTableModel.getValueAt(selectedRow, 4);
				SIDF.setText(SID);
				markF.setText(mark);
				cnoFF.setText(cno);
				markF.setEditable(true);
			}
			//--------------------------------------------------
		});
		StuMarkTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(StuMarkTable);
		StuMarkTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"SID", "First Name", "Last Name", "Cno", "FinalMark"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, String.class, String.class, String.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		StuMarkTable.getColumnModel().getColumn(0).setResizable(false);
		StuMarkTable.getColumnModel().getColumn(0).setPreferredWidth(37);
		StuMarkTable.getColumnModel().getColumn(1).setResizable(false);
		StuMarkTable.getColumnModel().getColumn(2).setResizable(false);
		StuMarkTable.getColumnModel().getColumn(3).setResizable(false);
		StuMarkTable.getColumnModel().getColumn(4).setResizable(false);
		
		saveB = new JButton("Save");
		saveB.setIcon(new ImageIcon(TeaMenu.class.getResource("/img/pngfind.com-save-icon-png-2201567.png")));
		saveB.setBackground(Color.DARK_GRAY);
		saveB.setForeground(Color.WHITE);
		saveB.setFont(new Font("Tahoma", Font.BOLD, 14));
		saveB.setEnabled(false);
		saveB.addActionListener(new ActionListener() {
			
			///////////////////////////////////////////////////// Save button Action To Save Mark
			public void actionPerformed(ActionEvent e) {
				String SID=SIDF.getText(); String cno=cnoFF.getText();
				String sql= "SELECT * FROM takes WHERE SID='"+SID+"' AND Cno='"+ cno + "' ";
				try {
					rs=st.executeQuery(sql);
					if(rs.next()) {
						rs.updateString("FinalMark", markF.getText());
						rs.updateRow();
						rs.close();
					}
				} catch (Exception err) {
					JOptionPane.showMessageDialog(null,err.getMessage());
				}	
				getAndStudnetMarks(); // To Refresh Table
			}
			//-----------------------------------------------
			
		});
		saveB.setBounds(665, 231, 119, 31);
		MarkAssignP.add(saveB);
		
		JButton cancelB2 = new JButton("Cancel");
		cancelB2.setForeground(Color.WHITE);
		cancelB2.setBackground(Color.DARK_GRAY);
		cancelB2.setIcon(new ImageIcon(TeaMenu.class.getResource("/img/11.png")));
		cancelB2.setFont(new Font("Tahoma", Font.BOLD, 14));
		cancelB2.addActionListener(new ActionListener() {
			
			//////////////////////////////////////////////////// Cancel Student Mark Assignment Button Action
			public void actionPerformed(ActionEvent e) {
				markF.setText(""); // clearing mark field
				saveB.setEnabled(false); // disabling save button
			}
			//----------------------------------------------------------------------------------
			
		});
		cancelB2.setBounds(665, 287, 122, 31);
		MarkAssignP.add(cancelB2);
		
		markF = new JTextField();
		markF.setEditable(false);
		markF.addKeyListener(new KeyAdapter() {
			@Override
			
			////////////////////////////////////////// Vaidating Grade Mark Input
			public void keyReleased(KeyEvent e) {
				try {
					if(e.getKeyChar()>='0' && e.getKeyChar()<='9') {
						if(Integer.parseInt(markF.getText()) > 100 || Integer.parseInt(markF.getText())<0)
							markF.setText("");	
					}else 
						markF.setText("");	
				}catch(Exception err) {
						markF.setText("");	
				}			
					
			}
			//----------------------------------------------------------
			
			
		});
		markF.setBounds(693, 189, 86, 20);
		MarkAssignP.add(markF);
		markF.setColumns(10);
		
		SIDF = new JTextField();
		SIDF.setEditable(false);
		SIDF.setBounds(693, 158, 86, 20);
		MarkAssignP.add(SIDF);
		SIDF.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("SID");
		lblNewLabel_2.setForeground(Color.WHITE);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_2.setBounds(640, 159, 43, 14);
		MarkAssignP.add(lblNewLabel_2);
		
		JLabel lblNewLabel_2_1 = new JLabel("Enter Mark");
		lblNewLabel_2_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_2_1.setForeground(Color.WHITE);
		lblNewLabel_2_1.setBounds(604, 191, 82, 14);
		MarkAssignP.add(lblNewLabel_2_1);
		
		cnoFF = new JTextField();
		cnoFF.setEditable(false);
		cnoFF.setBounds(693, 125, 86, 20);
		MarkAssignP.add(cnoFF);
		cnoFF.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Cno");
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1.setBounds(640, 126, 43, 14);
		MarkAssignP.add(lblNewLabel_1);
		
		JLabel img = new JLabel("");
		img.setIcon(new ImageIcon(TeaMenu.class.getResource("/img/abstract-2709402_960_720.png")));
		img.setBounds(0, 0, 808, 451);
		MarkAssignP.add(img);
		
		StuMarkTableModel =(DefaultTableModel) StuMarkTable.getModel();
		
		JPanel changePasswordP = new JPanel();
		changePasswordP.setLayout(null);
		tabbedPane.addTab("Change Your PassWord", null, changePasswordP, null);
		tabbedPane.setBackgroundAt(1, Color.DARK_GRAY);
		tabbedPane.setForegroundAt(1, Color.WHITE);
		
		JButton changeB = new JButton("Change");
		changeB.setIcon(new ImageIcon(TeaMenu.class.getResource("/img/pngfind.com-save-icon-png-2201567.png")));
		changeB.setForeground(Color.WHITE);
		changeB.setFont(new Font("Tahoma", Font.BOLD, 14));
		changeB.addActionListener(new ActionListener() {
			
			//////////////////////////////////////////// change PassWord Button Action Taken
			public void actionPerformed(ActionEvent e) {
				try {
					String sql="SELECT TID, PassWord from Instructor WHERE TID = '"+  TID + "'";
					rs=st.executeQuery(sql);
					if(rs.next()) {
						rs.updateString("PassWord", newPassWordF.getText());
						rs.updateRow();
						rs.close();
						
					}
					getAndSetTeaDetails(); //To Refresh Teacher Details
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
		changeB.setBounds(251, 199, 115, 34);
		changePasswordP.add(changeB);
		
		JButton cancelB = new JButton("Cancel");
		cancelB.setIcon(new ImageIcon(TeaMenu.class.getResource("/img/11.png")));
		cancelB.setFont(new Font("Tahoma", Font.BOLD, 14));
		cancelB.setBackground(Color.DARK_GRAY);
		cancelB.setForeground(Color.WHITE);
		cancelB.addActionListener(new ActionListener() {
			
			///////////////////////////////////////////// Cancel Change Password Button Action Taken
			public void actionPerformed(ActionEvent e) {
				newPassWordF.setText("");		// Clear new PassWord Field
				changeB.setEnabled(false);		// Disable Update Button
			}
			//--------------------------------------------------------
			
		});
		cancelB.setBounds(109, 199, 109, 34);
		changePasswordP.add(cancelB);
		
		JLabel lblCurrentPassWord = new JLabel("Current Pass Word");
		lblCurrentPassWord.setForeground(Color.WHITE);
		lblCurrentPassWord.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCurrentPassWord.setBounds(77, 115, 145, 17);
		changePasswordP.add(lblCurrentPassWord);
		
		currentPassWordF = new JTextField();
		currentPassWordF.setText("<dynamic>");
		currentPassWordF.setFont(new Font("Tahoma", Font.BOLD, 11));
		currentPassWordF.setEditable(false);
		currentPassWordF.setColumns(10);
		currentPassWordF.setBounds(223, 115, 59, 20);
		changePasswordP.add(currentPassWordF);
		
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
		lblEnterNewPass.setBounds(55, 160, 161, 17);
		changePasswordP.add(lblEnterNewPass);
		
		JLabel img_1 = new JLabel("");
		img_1.setIcon(new ImageIcon(TeaMenu.class.getResource("/img/abstract-2709402_960_720.png")));
		img_1.setBounds(0, 0, 808, 451);
		changePasswordP.add(img_1);
		
		JPanel TeaPersonalDetailsP = new JPanel();
		tabbedPane.addTab("Personal Details", null, TeaPersonalDetailsP, null);
		tabbedPane.setForegroundAt(2, Color.WHITE);
		tabbedPane.setBackgroundAt(2, Color.DARK_GRAY);
		TeaPersonalDetailsP.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(240, 240, 240));
		panel.setOpaque(false);
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Personal Details", TitledBorder.LEADING, TitledBorder.TOP, null, Color.WHITE));
		panel.setBounds(12, 7, 407, 240);
		TeaPersonalDetailsP.add(panel);
		panel.setLayout(null);
		
		fnameF = new JTextField();
		fnameF.setBounds(173, 35, 109, 20);
		panel.add(fnameF);
		fnameF.setText("<dynamic>");
		fnameF.setFont(new Font("Tahoma", Font.BOLD, 14));
		fnameF.setEditable(false);
		fnameF.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("First Name");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(173, 16, 93, 17);
		panel.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setForeground(Color.WHITE);
		lblLastName.setBounds(293, 16, 87, 17);
		panel.add(lblLastName);
		lblLastName.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		lnameF = new JTextField();
		lnameF.setBounds(292, 35, 109, 20);
		panel.add(lnameF);
		lnameF.setText("<dynamic>");
		lnameF.setFont(new Font("Tahoma", Font.BOLD, 14));
		lnameF.setEditable(false);
		lnameF.setColumns(10);
		
		JLabel lblGender = new JLabel("Gender");
		lblGender.setForeground(Color.WHITE);
		lblGender.setBounds(53, 96, 62, 17);
		panel.add(lblGender);
		lblGender.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		pnoF = new JTextField();
		pnoF.setBounds(120, 185, 119, 20);
		panel.add(pnoF);
		pnoF.setText("<dynamic>");
		pnoF.setFont(new Font("Tahoma", Font.BOLD, 14));
		pnoF.setEditable(false);
		pnoF.setColumns(10);
		
		addressF = new JTextField();
		addressF.setBounds(120, 214, 190, 20);
		panel.add(addressF);
		addressF.setText("<dynamic>");
		addressF.setFont(new Font("Tahoma", Font.BOLD, 14));
		addressF.setEditable(false);
		addressF.setColumns(10);
		
		JLabel lblAddress = new JLabel("Address");
		lblAddress.setForeground(Color.WHITE);
		lblAddress.setBounds(53, 216, 61, 17);
		panel.add(lblAddress);
		lblAddress.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		DOBF = new JTextField();
		DOBF.setBounds(120, 154, 109, 20);
		panel.add(DOBF);
		DOBF.setText("<dynamic>");
		DOBF.setFont(new Font("Tahoma", Font.BOLD, 14));
		DOBF.setEditable(false);
		DOBF.setColumns(10);
		
		JLabel lblBirthDate = new JLabel("Birth Date");
		lblBirthDate.setForeground(Color.WHITE);
		lblBirthDate.setBounds(37, 157, 78, 17);
		panel.add(lblBirthDate);
		lblBirthDate.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JLabel lblPhoneNumber = new JLabel("Phone Num");
		lblPhoneNumber.setForeground(Color.WHITE);
		lblPhoneNumber.setBounds(17, 187, 93, 17);
		panel.add(lblPhoneNumber);
		lblPhoneNumber.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		sexF = new JTextField();
		sexF.setBounds(121, 94, 31, 20);
		panel.add(sexF);
		sexF.setText("<dynamic>");
		sexF.setFont(new Font("Tahoma", Font.BOLD, 14));
		sexF.setEditable(false);
		sexF.setColumns(10);
		
		cnoF = new JTextField();
		cnoF.setBounds(131, 123, 53, 20);
		panel.add(cnoF);
		cnoF.setText("<dynamic>");
		cnoF.setFont(new Font("Tahoma", Font.BOLD, 14));
		cnoF.setEditable(false);
		cnoF.setColumns(10);
		
		JLabel lblCourse = new JLabel("Subject Assigned");
		lblCourse.setForeground(Color.WHITE);
		lblCourse.setBounds(6, 129, 141, 17);
		panel.add(lblCourse);
		lblCourse.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		TIDF = new JTextField();
		TIDF.setBounds(120, 63, 46, 20);
		panel.add(TIDF);
		TIDF.setText("<dynamic>");
		TIDF.setFont(new Font("Tahoma", Font.BOLD, 14));
		TIDF.setEditable(false);
		TIDF.setColumns(10);
		
		JLabel lblTid = new JLabel("TID");
		lblTid.setForeground(Color.WHITE);
		lblTid.setBounds(62, 68, 38, 17);
		panel.add(lblTid);
		lblTid.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		titleF = new JTextField();
		titleF.setBounds(120, 35, 38, 20);
		panel.add(titleF);
		titleF.setText("<dynamic>");
		titleF.setFont(new Font("Tahoma", Font.BOLD, 14));
		titleF.setEditable(false);
		titleF.setColumns(10);
		
		JLabel lblTitle = new JLabel("Title");
		lblTitle.setForeground(Color.WHITE);
		lblTitle.setBounds(122, 16, 31, 17);
		panel.add(lblTitle);
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		salaryF = new JTextField();
		salaryF.setBounds(259, 123, 71, 20);
		panel.add(salaryF);
		salaryF.setText("<dynamic>");
		salaryF.setFont(new Font("Tahoma", Font.BOLD, 14));
		salaryF.setEditable(false);
		salaryF.setColumns(10);
		
		JLabel lblSalary = new JLabel("Salary");
		lblSalary.setForeground(Color.WHITE);
		lblSalary.setBounds(213, 126, 46, 17);
		panel.add(lblSalary);
		lblSalary.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JLabel img_2 = new JLabel("");
		img_2.setIcon(new ImageIcon(TeaMenu.class.getResource("/img/abstract-2709402_960_720.png")));
		img_2.setBounds(0, 0, 808, 451);
		TeaPersonalDetailsP.add(img_2);
		
		JButton logOutB = new JButton("Log Out");
		logOutB.setBackground(Color.BLACK);
		logOutB.setForeground(Color.WHITE);
		logOutB.setFont(new Font("Tahoma", Font.BOLD, 12));
		logOutB.addActionListener(new ActionListener() {
			
			////////////////////////////////////////  Log out Button Action Preformed
			public void actionPerformed(ActionEvent e) {
				dispose();
				TeaLogin teaLogin=new TeaLogin();
				teaLogin.setVisible(true);			
			}
			//-----------------------------------------------------------
			
		});
		logOutB.setBounds(53, 479, 89, 23);
		contentPane.add(logOutB);
		
		JLabel img_3 = new JLabel("");
		img_3.setIcon(new ImageIcon(TeaMenu.class.getResource("/img/abstract-2709402_960_720.png")));
		img_3.setBounds(0, 0, 813, 513);
		contentPane.add(img_3);
		
	    /////////////////////////////////////////////// Getting and Setting Teacher Detail
		getAndSetTeaDetails();
	    //---------------------------------------------
		
	    /////////////////////////////////////////////////// Getting and Setting Student Marks for that Teacher Subject
		getAndStudnetMarks();
	   //---------------------------------------------------
		
		setLocationRelativeTo(null);  // Center Postion
	}
}
