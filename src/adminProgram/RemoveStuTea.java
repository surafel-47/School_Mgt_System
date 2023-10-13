package adminProgram;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import connectionProgram.Connect;

import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

public class RemoveStuTea extends JFrame {

	private JPanel contentPane;
	private JTextField teaSearchF;
	private JTextField stuSearchF;
	private JTable teaTable;
	private JTable stuTable;
	
	DefaultTableModel stuModel,teaModel; // Table models for Student and Teachers
	private Statement st=null; //connection Objects
	private ResultSet rs=null;   // Connection Objects
	
	
	////////////////////////////////////////////////////////// Method To Load Students To Table
	void setStuTabel(String sql) {
		stuModel.setRowCount(0); // Clearing The table of Previous Data
		String stu[]=new String[3];	// array to hold a single Students SID, fname,lname
		try {
			rs=st.executeQuery(sql); 
			while(rs.next()) {			// Loading Studentss data to table
				stu[0]=rs.getString("SID");
				stu[1]=rs.getString("fname");
				stu[2]=rs.getString("lname");
				stuModel.addRow(stu);
			}
		}catch(Exception err) {
			JOptionPane.showMessageDialog(null, err.getMessage());
		}
		
	}
	//----------------------------------------------------------------------------
	
	////////////////////////////////////////////////////////// Method To Load Students To Table
	void setTeaTabel(String sql) {
		teaModel.setRowCount(0); // Clearing The table of Previous Data
		String tea[]=new String[4];	// array to hold a single Teachers TID, title, fname,lname
		try {
			rs=st.executeQuery(sql); 
			while(rs.next()) {			// Loading Teacher data to table
				tea[0]=rs.getString("TID");
				tea[1]=rs.getString("title");
				tea[2]=rs.getString("fname");
				tea[3]=rs.getString("lname");
				teaModel.addRow(tea);
			}
		}catch(Exception err) {
			JOptionPane.showMessageDialog(null, err.getMessage());
		}
		
	}
	//----------------------------------------------------------------------------
		
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RemoveStuTea frame = new RemoveStuTea();
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
	public RemoveStuTea() {
		setResizable(false);
		
		/////////////////////////////////////////////////////////////////////
		st=Connect.getConnection(Connect.UPDATE_ACCESS); // To get Connection As Soon as JFrame in Created
		//------------------------------------------------------------	
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 711, 502);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 711, 431);
		contentPane.add(tabbedPane);
		
		JPanel teaRemoveP = new JPanel();
		teaRemoveP.setBackground(Color.GRAY);
		tabbedPane.addTab("Remove Teacher", null, teaRemoveP, null);
		teaRemoveP.setLayout(null);
		
		teaSearchF = new JTextField();
		teaSearchF.setBounds(152, 63, 143, 26);
		teaRemoveP.add(teaSearchF);
		teaSearchF.setColumns(10);
		
		JLabel lblEnterSidOr = new JLabel("Enter TID or Name");
		lblEnterSidOr.setForeground(Color.WHITE);
		lblEnterSidOr.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblEnterSidOr.setBounds(0, 66, 136, 17);
		teaRemoveP.add(lblEnterSidOr);
		
		JButton teaRemoveB = new JButton("Remove");
		teaRemoveB.setForeground(Color.WHITE);
		teaRemoveB.setFont(new Font("Tahoma", Font.BOLD, 14));
		teaRemoveB.setBackground(Color.DARK_GRAY);
		teaRemoveB.setIcon(new ImageIcon(RemoveStuTea.class.getResource("/img/11.png")));
		teaRemoveB.addActionListener(new ActionListener() {
			
			//////////////////////////////////////////////// Remove Teacher action taken
			public void actionPerformed(ActionEvent e) {
				int selectedRow=teaTable.getSelectedRow();	
				if(selectedRow!=-1) {  	// if it returns -1, No row is selected
					String selectedTeaTID= (String) teaModel.getValueAt(selectedRow,0); // Get TID, Type Casting from Object to String
					String sql=" DELETE FROM instructor WHERE TID='" + selectedTeaTID + "'  ";
					try {
						st.execute(sql);
						JOptionPane.showMessageDialog(null, "Teacher  Deleted ");
					}catch(Exception err) {
						JOptionPane.showMessageDialog(null, "Unable to Delete"+ err.getMessage());
					}
					setTeaTabel("SELECT * FROM instructor");	
				}else {
					JOptionPane.showMessageDialog(null, "Please Select a Teacher ");
				}

			}	
			//------------------------------------------------------------------
		
		});
		teaRemoveB.setBounds(298, 359, 121, 33);
		teaRemoveP.add(teaRemoveB);
		
		JButton teaSearchB = new JButton("Search");
		teaSearchB.setIcon(new ImageIcon(RemoveStuTea.class.getResource("/img/Search.png")));
		teaSearchB.setFont(new Font("Tahoma", Font.BOLD, 13));
		teaSearchB.addActionListener(new ActionListener() {
			////////////////////////////////////////////////////////////////// Search Teacher action
			public void actionPerformed(ActionEvent e) {
				String str=teaSearchF.getText(); // getting the searched String
				String sql;
				if(str.equals("")) {
					setTeaTabel("SELECT * FROM instructor");
				}else {
					sql= "SELECT * FROM instructor WHERE TID like '%" + str +"%' OR fname like '%" + str + "%' OR lname like '%" +str+"%'" ;
					setTeaTabel(sql);
				}
				
			}
			//------------------------------------------------------
		});
		teaSearchB.setBounds(316, 63, 121, 26);
		teaRemoveP.add(teaSearchB);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(152, 108, 482, 240);
		teaRemoveP.add(scrollPane);
		
		teaTable = new JTable();
		teaTable.setBackground(Color.LIGHT_GRAY);
		teaTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		teaTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"TID", "Title", "First Name", "Last Name"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, String.class, String.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		teaTable.getColumnModel().getColumn(0).setResizable(false);
		teaTable.getColumnModel().getColumn(1).setResizable(false);
		teaTable.getColumnModel().getColumn(2).setResizable(false);
		teaTable.getColumnModel().getColumn(3).setResizable(false);
		scrollPane.setViewportView(teaTable);
		
		JLabel img = new JLabel("");
		img.setIcon(new ImageIcon(RemoveStuTea.class.getResource("/img/bk.jpg")));
		img.setBounds(0, 0, 706, 400);
		teaRemoveP.add(img);
		
		///////////////////////////////////////////////// Getting Teachers Table Model
		teaModel =(DefaultTableModel) teaTable.getModel();
		//-------------------------------------------
		
		
		
		JPanel stuRemoveP = new JPanel();
		stuRemoveP.setBackground(Color.GRAY);
		tabbedPane.addTab("Remove Student", null, stuRemoveP, null);
		stuRemoveP.setLayout(null);
		
		JLabel lblEnterTidOr = new JLabel("Enter SID or Name");
		lblEnterTidOr.setForeground(Color.WHITE);
		lblEnterTidOr.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblEnterTidOr.setBounds(10, 61, 143, 20);
		stuRemoveP.add(lblEnterTidOr);
		
		stuSearchF = new JTextField();
		stuSearchF.setColumns(10);
		stuSearchF.setBounds(152, 63, 143, 20);
		stuRemoveP.add(stuSearchF);
		
		JButton stuSearchB = new JButton("Search");
		stuSearchB.setHorizontalAlignment(SwingConstants.LEFT);
		stuSearchB.setFont(new Font("Tahoma", Font.BOLD, 14));
		stuSearchB.setIcon(new ImageIcon(RemoveStuTea.class.getResource("/img/Search.png")));
		stuSearchB.addActionListener(new ActionListener() {
			
			////////////////////////////////////////////////////////////////// Search Student action
			public void actionPerformed(ActionEvent e) {
				String str=stuSearchF.getText(); // getting the searched String
				String sql;
				if(str.equals("")) {
					setStuTabel("SELECT * FROM Student");
				}else {
					sql= "SELECT * FROM student WHERE SID like '%" + str +"%' OR Fname like '%" + str + "%' OR Lname like '%" +str+"%'" ;
					setStuTabel(sql);
				}
				
			}
			//------------------------------------------------------
		
		});
		stuSearchB.setBounds(329, 60, 115, 23);
		stuRemoveP.add(stuSearchB);
		
		JButton stuRemoveB = new JButton("Remove");
		stuRemoveB.setForeground(Color.WHITE);
		stuRemoveB.setBackground(Color.DARK_GRAY);
		stuRemoveB.setIcon(new ImageIcon(RemoveStuTea.class.getResource("/img/11.png")));
		stuRemoveB.setFont(new Font("Tahoma", Font.BOLD, 14));
		stuRemoveB.addActionListener(new ActionListener() {
			
			//////////////////////////////////////////////// Remove Student action taken
			public void actionPerformed(ActionEvent e) {
				int selectedRow=stuTable.getSelectedRow();	
				if(selectedRow!=-1) {  	// if it returns -1, No row is selected		
					String selectedStuSID= (String) stuModel.getValueAt(selectedRow,0); // Get SID, Type Casting from Object to String
					String sql=" DELETE FROM student WHERE SID='" + selectedStuSID + "'  ";
					try {
						st.execute(sql);
						JOptionPane.showMessageDialog(null, "Student Deleted ");
					}catch(Exception err) {
						JOptionPane.showMessageDialog(null, "Unable to Delete"+ err.getMessage());
					}
					setStuTabel("SELECT * FROM student");
				}else {
					JOptionPane.showMessageDialog(null, "Please Select Student");
				}
			}	
			//------------------------------------------------------------------
			
		});
		stuRemoveB.setBounds(298, 359, 121, 33);
		stuRemoveP.add(stuRemoveB);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(152, 108, 482, 240);
		stuRemoveP.add(scrollPane_1);
		
		stuTable = new JTable();
		stuTable.setBackground(Color.GRAY);
		stuTable.addMouseListener(new MouseAdapter() {
			@Override
			/////////////////////////////////////////////////////////////////////////
			public void mouseClicked(MouseEvent e) {
				 stuTable.getSelectionModel().isSelectionEmpty();
			}
			//--------------------------------------------------------------
		});
		stuTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		stuTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"SID", "First Name", "Last Name"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, String.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		stuTable.getColumnModel().getColumn(0).setResizable(false);
		stuTable.getColumnModel().getColumn(1).setResizable(false);
		stuTable.getColumnModel().getColumn(2).setResizable(false);
		scrollPane_1.setViewportView(stuTable);
		
		JLabel img_1 = new JLabel("");
		img_1.setIcon(new ImageIcon(RemoveStuTea.class.getResource("/img/bk.jpg")));
		img_1.setBounds(0, 0, 706, 400);
		stuRemoveP.add(img_1);
		
		///////////////////////////////////////////////// Getting Students Table Model
		stuModel = (DefaultTableModel) stuTable.getModel();
		//-------------------------------------------
		
		
		JButton returnB = new JButton("Return");
		returnB.setBackground(Color.BLACK);
		returnB.setFont(new Font("Tahoma", Font.BOLD, 13));
		returnB.setForeground(Color.WHITE);
		returnB.addActionListener(new ActionListener() {
			
			///////////////////////////////////////////////// Return Button to return to adminMainMenu
			public void actionPerformed(ActionEvent e) {
				dispose();
				AdminMainMenu adminMainMenu =new AdminMainMenu();
				adminMainMenu.setVisible(true);
			}
			//---------------------------------------------------------------------------
		});
		returnB.setBounds(32, 439, 89, 23);
		contentPane.add(returnB);
		
		JLabel img2 = new JLabel("");
		img2.setIcon(new ImageIcon(RemoveStuTea.class.getResource("/img/bk.jpg")));
		img2.setBounds(0, 0, 705, 473);
		contentPane.add(img2);
		
		///////////////////////////////////////////////////////////// Setting Stu and Tea Table all Data
		setStuTabel("SELECT * FROM Student");
		setTeaTabel("SELECT * FROM Instructor");
		//--------------------------------------------------------
		
		setLocationRelativeTo(null);  // Center Postion

	}
}
