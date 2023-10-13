package adminProgram;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextPane;
import javax.swing.JTabbedPane;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.border.TitledBorder;

import connectionProgram.Connect;
import connectionProgram.Connect;
import java.awt.Color;
import javax.swing.border.EtchedBorder;
import java.awt.SystemColor;
import javax.swing.UIManager;
public class ExtraCurrMgt extends JFrame {

	private JPanel contentPane;
	private JTextArea descF;
	private JLabel lblName;
	private JComboBox nameC;
	private JPanel DeleteP;
	private JCheckBox editC;
	private JButton cancelB;
	private JButton updateB;
	private JTextField newNameF;
	private JLabel newNameL;
	private JTextArea newAddDescF;
	private JLabel lblDescription_1;
	private JLabel lblName_1;
	private JComboBox delNameC;
	private JButton deleteB;
	private JPanel addP;
	private JPanel deleteP;
	private JLabel lblName_2;
	private JTextField newAddNameF;
	private JButton addB;
	private JButton clearB;

	private Statement st=null; //connection Objects
	private ResultSet rs=null;   // Connection Objects
	
	
	///////////////////////////////////////////// to get Extra-Curricular activities to String array	
	public String[] getExtraCurList() { 
		ArrayList <String> extraCurList= new ArrayList<String>();   //ArrayList to Store Data
		String list[]=null;									// String array to transfer from ArrayList 
		try {	
	        rs=st.executeQuery("SELECT name FROM extra_curricular");		// query
	        while(rs.next()) {
	        	extraCurList.add(rs.getString("name"));				// adding to ArrayList
	        }
	        list = new String[extraCurList.size()];	//String list obj with ArrayList + 1 size
	        for(int i = 0; i < list.length; i++) {
	        	list[i] = extraCurList.get(i);        // Tranfering form ArrayList to String Array
	        }
	        rs.close();
		}catch(Exception err){
			System.out.println(err.getMessage());
		}
	     return list;		// Returning List
	}
	//---------------------------------------------------------------------------------------------------
		
	/////////////////////////////////////////////////////////////////////////// To get Description of a certain Extra Currculilar activity
	public String getSelectedExtraCurDescription(String name) {
 		String desc=null;  // String To Hold Extra Curr Description
 		String sql="SELECT * FROM extra_curricular WHERE name='" + name + "' ;" ; 
 		try{
 			rs=st.executeQuery(sql);
 			rs.next();
 			desc=rs.getString("Description"); 
 			rs.close();
 		}catch(Exception err) {
 			JOptionPane.showMessageDialog(null, err.getMessage());
 		}
 		return desc;
	}
	//---------------------------------------------------------------------------------------------------
	
	///////////////////////////////////////////////////////////// Function to Clear/Cancel Edited Data.
	public void clear() {
		newNameF.setText(""); newNameF.setVisible(false);
		newNameL.setVisible(false); descF.setEditable(false);
		updateB.setEnabled(false); cancelB.setEnabled(false);
		editC.setSelected(false);   nameC.setEnabled(true);
		nameC.setModel( new DefaultComboBoxModel( getExtraCurList() ) );
		descF.setText(getSelectedExtraCurDescription( (String) nameC.getSelectedItem() )    ); // restoring default text written
	}
	//-----------------------------------------------------------------
	
	///////////////////////////////////////////////////////////// To Refresh the tables, fields, combBoxs when DataBase is Altered
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void refresh() {
		delNameC.setModel( new DefaultComboBoxModel( getExtraCurList() ) ); //reloading array list for CombBoX
		nameC.setModel( new DefaultComboBoxModel( getExtraCurList() ) );    //reloading array list for CombBoX
	}
	//*************************************************************************************** 

	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ExtraCurrMgt frame = new ExtraCurrMgt();
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
	public ExtraCurrMgt() {
		
		/////////////////////////////////////////////////////////////////////
		st=Connect.getConnection(Connect.UPDATE_ACCESS); // To get Connection As Soon as JFrame in Created
		//------------------------------------------------------------	
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 846, 460);
		contentPane = new JPanel();
		contentPane.setBackground(Color.GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 830, 385);
		contentPane.add(tabbedPane);
		
		JPanel View = new JPanel();
		View.setBackground(Color.GRAY);
		tabbedPane.addTab("View ", null, View, null);
		tabbedPane.setForegroundAt(0, Color.WHITE);
		tabbedPane.setBackgroundAt(0, Color.DARK_GRAY);
		View.setLayout(null);
		
		nameC = new JComboBox();
		nameC.setBackground(Color.LIGHT_GRAY);
		nameC.addActionListener(new ActionListener() {
			
			/////////////////////////////////////////////////// Action When Selecting form CombBox Name..
			public void actionPerformed(ActionEvent e) {
				descF.setText(getSelectedExtraCurDescription( (String) nameC.getSelectedItem())); //Casting String Description from JCombBox Names for Activity 
			}
			//----------------------------------------------------------------------------------
			
		});
		nameC.setModel( new DefaultComboBoxModel( getExtraCurList() ) ); //setting combBox Model with Array List of Names
		nameC.setBounds(109, 50, 166, 22);
		View.add(nameC);
		
		lblName = new JLabel("Name");
		lblName.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblName.setBounds(47, 51, 52, 17);
		View.add(lblName);
		
		////////////////////////////////////////////////////////////////// Setting Up Descripion CombBox
		descF=new JTextArea();
		descF.setBackground(Color.LIGHT_GRAY);
		descF.setText(getSelectedExtraCurDescription( (String) nameC.getSelectedItem())); //Casting String Description from JCombBox Names for Activity 
		descF.setFont(new Font("Tempus Sans ITC", Font.BOLD, 18));
		descF.setEditable(false);
		descF.setBounds(109, 114, 451, 163);
		descF.setLineWrap(true);      // To Make Sure it Goes to The Next Line
		descF.setWrapStyleWord(true);//
		View.add(descF);
		//****************************************************************************
		
		JLabel lblDescription = new JLabel("Description");
		lblDescription.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblDescription.setBounds(28, 116, 71, 17);
		View.add(lblDescription);
		
		editC = new JCheckBox("Edit");
		editC.setFont(new Font("Tahoma", Font.PLAIN, 14));
		editC.setOpaque(false);
		editC.addActionListener(new ActionListener() {
			
			////////////////////////////////////////////////   Editable Set for Activity....
			public void actionPerformed(ActionEvent e) {
				if(editC.isSelected()) {
					newNameF.setVisible(true);	newNameL.setVisible(true);
					descF.setEditable(true);    updateB.setEnabled(true);
					cancelB.setEnabled(true);	nameC.setEnabled(false);
					
				}else {
					clear();  // calling function to clear edited data
				}
			}
			//******************************************************************
		});
		editC.setBounds(281, 50, 97, 23);
		View.add(editC);
		
		cancelB = new JButton("Cancel");
		cancelB.setBackground(Color.DARK_GRAY);
		cancelB.setForeground(Color.WHITE);
		cancelB.setFont(new Font("Tahoma", Font.BOLD, 14));
		cancelB.addActionListener(new ActionListener() {
			
			/////////////////////////////////////////////////////////////// Cancel Button Action
			public void actionPerformed(ActionEvent e) {
				clear(); // calling Method to clear edited Fields
			}
			//*******************************************************
			
		});
		cancelB.setEnabled(false);
		cancelB.setBounds(361, 306, 89, 22);
		View.add(cancelB);
		
		updateB = new JButton("Update");
		updateB.setForeground(Color.WHITE);
		updateB.setBackground(Color.DARK_GRAY);
		updateB.setFont(new Font("Tahoma", Font.BOLD, 14));
		updateB.addActionListener(new ActionListener() {
			
			////////////////////////////////////////////////////////// Update Activity Action
			public void actionPerformed(ActionEvent e) {
				if(newNameF.getText().equals("")) {
					JOptionPane.showMessageDialog(null," New Name is Empty!");
				}else {
					try {
						String selectedName= (String) nameC.getSelectedItem();
						String sql="select * from extra_curricular Where name = '" + selectedName + "'  ";
						rs=st.executeQuery(sql);
						rs.next();
						rs.updateString("Name", newNameF.getText());
						rs.updateString("Description", descF.getText());
						rs.updateRow();
						rs.close();
						JOptionPane.showMessageDialog(null, "Updated ");
						clear();		// To clear text Fields
						refresh(); // to reload data onto the JCombBox()
					} catch (Exception err) {
						JOptionPane.showMessageDialog(null, "Name Might Already Exist : "+ err.getMessage());
					}
				}
			}
			//--------------------------------------------------------------------
			
		});
		updateB.setEnabled(false);
		updateB.setBounds(471, 305, 89, 23);
		View.add(updateB);
		
		newNameF = new JTextField();
		newNameF.setBackground(Color.LIGHT_GRAY);
		newNameF.addKeyListener(new KeyAdapter() {
			@Override
			
			///////////////////////////////////////////////////// To Vallidate Extra Curricular Activity Name
			public void keyPressed(KeyEvent e) {
				String name=newNameF.getText();
				if(e.getKeyChar()>='a' && e.getKeyChar()<='z' || e.getKeyChar()>='A' && e.getKeyChar()<='Z') {
					if(name.length()<20)
						newNameF.setEditable(true);
					else
						newNameF.setEditable(false);
				}else if(e.getExtendedKeyCode()==KeyEvent.VK_BACK_SPACE)
					newNameF.setEditable(true);
				else 
					 newNameF.setEditable(false);
			}
			//----------------------------------------------------------
			
		});
		newNameF.setBounds(109, 83, 166, 20);
		newNameF.setVisible(false);
		View.add(newNameF);
		newNameF.setColumns(10);
		
		newNameL = new JLabel("New Name");
		newNameL.setFont(new Font("Tahoma", Font.BOLD, 14));
		newNameL.setBounds(10, 79, 89, 17);
		newNameL.setVisible(false);
		View.add(newNameL);
		
		DeleteP = new JPanel();
		DeleteP.setBackground(Color.GRAY);
		tabbedPane.addTab("Add/Delete", null, DeleteP, null);
		tabbedPane.setForegroundAt(1, Color.WHITE);
		tabbedPane.setBackgroundAt(1, Color.DARK_GRAY);
		DeleteP.setLayout(null);
		
		addP = new JPanel();
		addP.setBackground(UIManager.getColor("Button.shadow"));
		addP.setOpaque(false);
		addP.setBorder(new TitledBorder(null, "Add ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		addP.setBounds(6, 173, 809, 173);
		DeleteP.add(addP);
		addP.setLayout(null);
		
		newAddDescF = new JTextArea();
		newAddDescF.setBackground(Color.LIGHT_GRAY);
		newAddDescF.addKeyListener(new KeyAdapter() {
			@Override
			///////////////////////////////////////////////////////////// Description Validation, Making Sure Description Doesn't Pass 100 Chars
			public void keyPressed(KeyEvent e) {
				String name=newAddDescF.getText();
				if(name.length()<100)
					newAddDescF.setEditable(true);
				else
					newAddDescF.setEditable(false);
				if(e.getExtendedKeyCode()==KeyEvent.VK_BACK_SPACE){
					newAddDescF.setEditable(true);
				}
			}
			//*-----------------------------------------------------------------------------------------------------------------
		});
		newAddDescF.setBounds(134, 49, 489, 113);
		addP.add(newAddDescF);
		newAddDescF.setText((String) null);
		newAddDescF.setLineWrap(true);      // To Make Sure it Goes to The Next Line
		newAddDescF.setWrapStyleWord(true);//
		newAddDescF.setFont(new Font("MS Reference Sans Serif", Font.ITALIC, 17));
		
		lblDescription_1 = new JLabel("Description");
		lblDescription_1.setBounds(53, 49, 71, 17);
		addP.add(lblDescription_1);
		lblDescription_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		lblName_2 = new JLabel("Name");
		lblName_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblName_2.setBounds(81, 21, 43, 17);
		addP.add(lblName_2);
		
		newAddNameF = new JTextField();
		newAddNameF.setBackground(Color.LIGHT_GRAY);
		newAddNameF.addKeyListener(new KeyAdapter() {
			@Override
			/////////////////////////////////////////////////////////// Add New Extra Curr Name Validation....
			public void keyPressed(KeyEvent e) {
				String name=newAddNameF.getText();
				if(e.getKeyChar()>='a' && e.getKeyChar()<='z' || e.getKeyChar()>='A' && e.getKeyChar()<='Z') {
					if(name.length()<20)
						newAddNameF.setEditable(true);
					else
						newAddNameF.setEditable(false);
				}else if(e.getExtendedKeyCode()==KeyEvent.VK_BACK_SPACE)
					newAddNameF.setEditable(true);
				else if(e.getExtendedKeyCode()==KeyEvent.VK_ENTER)
					  newAddDescF.requestFocus();
				else 
					newAddNameF.setEditable(false);
			}
			//-----------------------------------------------------------------------------------------------
		});
		newAddNameF.setBounds(134, 21, 136, 20);
		addP.add(newAddNameF);
		newAddNameF.setColumns(10);
		
		addB = new JButton("Add");
		addB.setForeground(Color.WHITE);
		addB.setBackground(Color.DARK_GRAY);
		addB.setFont(new Font("Tahoma", Font.BOLD, 14));
		addB.addActionListener(new ActionListener() {
			
			//////////////////////////////////////////////////////////// Add Extra Cur Activity Action
			public void actionPerformed(ActionEvent e) {
				if(newAddNameF.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Name is Empty");
				}else {
					try {
						rs=st.executeQuery("select * from extra_curricular");
						rs.moveToInsertRow();
						rs.updateString("Name", newAddNameF.getText());
						rs.updateString("Description", newAddDescF.getText());
						rs.insertRow();
						rs.close();
						JOptionPane.showMessageDialog(null,"Added");
						newAddNameF.setText(""); newAddDescF.setText("");
						
						refresh(); // to reload data onto the JCombBox
					}catch(Exception err) {
						JOptionPane.showMessageDialog(null, "Unable To Delete : "+ err.getMessage());
					}
				}
			}
			//--------------------------------------------------------------------------------------
			
		});
		addB.setBounds(645, 68, 89, 23);
		addP.add(addB);
		
		clearB = new JButton("Cancel");
		clearB.setForeground(Color.WHITE);
		clearB.setBackground(Color.DARK_GRAY);
		clearB.setFont(new Font("Tahoma", Font.BOLD, 14));
		clearB.addActionListener(new ActionListener() {
			
			////////////////////////////////////////////////////////////  Clear Action...
			public void actionPerformed(ActionEvent e) {
				newAddNameF.setText("");
				newAddDescF.setText("");
			}
			//---------------------------------------------------------------------------
		
		});
		clearB.setBounds(645, 102, 89, 23);
		addP.add(clearB);
		
		deleteP = new JPanel();
		deleteP.setBackground(Color.GRAY);
		deleteP.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Delete", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		deleteP.setBounds(6, 11, 809, 157);
		DeleteP.add(deleteP);
		deleteP.setLayout(null);
		
		lblName_1 = new JLabel("Name");
		lblName_1.setBounds(75, 46, 43, 17);
		deleteP.add(lblName_1);
		lblName_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		delNameC = new JComboBox();
		delNameC.setBackground(Color.LIGHT_GRAY);
		delNameC.setModel( new DefaultComboBoxModel( getExtraCurList() ) ); //setting combBox Model with Array List of Names
		delNameC.setBounds(128, 45, 166, 22);
		deleteP.add(delNameC);
		
		deleteB = new JButton("Delete");
		deleteB.setForeground(Color.WHITE);
		deleteB.setBackground(Color.DARK_GRAY);
		deleteB.setFont(new Font("Tahoma", Font.BOLD, 14));
		deleteB.addActionListener(new ActionListener() {
			
			///////////////////////////////////////////////////////////////// Action to delete an Extra Curricular Activity
			public void actionPerformed(ActionEvent e) {
				String selectedName= (String) delNameC.getSelectedItem();
				String sql="Select * FROM extra_curricular WHERE Name = '" + selectedName+"' ";
				try {
					rs=st.executeQuery(sql);
					rs.next();
					rs.deleteRow();
					rs.close();
					JOptionPane.showMessageDialog(null,"Deleted");
					refresh();  // to reload JCombBox data...
				}catch(Exception err) {
					JOptionPane.showMessageDialog(null, "Unable To Delete : "+ err.getMessage());
				}
				delNameC.setModel( new DefaultComboBoxModel( getExtraCurList() ) ); //setting combBox Model with Array List of Names
			}
			//-------------------------------------------------------------------------------------------------------------
			
		});
		deleteB.setBounds(325, 45, 89, 23);
		deleteP.add(deleteB);
		
		JButton Return = new JButton("Return");
		Return.setForeground(Color.WHITE);
		Return.setBackground(Color.DARK_GRAY);
		Return.setFont(new Font("Tahoma", Font.BOLD, 14));
		Return.addActionListener(new ActionListener() {
			
			////////////////////////////////////////////////////////////// Return button to return to menu
			public void actionPerformed(ActionEvent e) {
				dispose();
				AdminMainMenu adminMainMenu=new AdminMainMenu();
				adminMainMenu.setVisible(true);
			}
			///-------------------------------------------------------------------------------------------
			
		});
		Return.setBounds(51, 387, 89, 23);
		contentPane.add(Return);
		setLocationRelativeTo(null);  // Center Postion

	}
}
