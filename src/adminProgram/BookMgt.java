package adminProgram;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import connectionProgram.Connect;

import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ListSelectionModel;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JCheckBox;
import connectionProgram.Connect;
import java.awt.Color;
import javax.swing.ImageIcon;
import java.awt.SystemColor;
public class BookMgt extends JFrame {

	private JPanel contentPane;
	private JTextField searchF;
	private JButton searchB;
	private JTable bkTable;
	private JTextField ISBNF;
	private JTextField titleF;
	private JButton updateB;
	private JButton cancelB;
	private JCheckBox editC;
	private JButton deleteB;
	private JButton returnB;
	private JTextField bISBNF;
	private JTextField bTitleF;
	private JLabel lblBookIsbn;
	private JLabel lblBookTitle;
	private JButton addB;
	private JButton cancelB2;
	private JButton returnB2;
	
	
	
	DefaultTableModel bkModel;	// Table Model to add Data to books JTable
	private String selectedBkISBN;// To Store Selected Book ISBN
	private String selectedBkTitle;// To Store Selected Book Title
	
	private Statement st=null; //connection Objects
	private ResultSet rs=null;   // Connection Objects
	
   
	
	///////////////////////////////////////////////////////////////// Method to Set ISBN and Title To The  Book Table
	public void setBooksToTable(String sql) {	
		bkModel.setRowCount(0); // Clearing The table of Previous Data
		String Book_ISBN_Title[]=new String[2];	// array to hold a single books Title and ISBN
		try {
			rs=st.executeQuery(sql); 
			while(rs.next()) {			// Loading Book data to table
				Book_ISBN_Title[0]=rs.getString("ISBN");
				Book_ISBN_Title[1]=rs.getString("Title");
				bkModel.addRow(Book_ISBN_Title);
			}
			rs.close();
		}catch(Exception err) {
			System.out.println(err.getMessage());
		}
	}
   //-----------------------------------------------------------------------------------
	
	
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BookMgt frame = new BookMgt();
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
	public BookMgt() {
		
		/////////////////////////////////////////////////////////////////////
			st=Connect.getConnection(Connect.UPDATE_ACCESS); // To get Connection As Soon as JFrame in Created
		//------------------------------------------------------------	
			
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 651, 563);
		contentPane = new JPanel();
		contentPane.setBackground(Color.GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBackground(Color.GRAY);
		tabbedPane.setBorder(null);
		tabbedPane.setBounds(0, 0, 638, 524);
		contentPane.add(tabbedPane);
		
		JPanel ViewEditBooksP = new JPanel();
		ViewEditBooksP.setFocusable(false);
		ViewEditBooksP.setForeground(Color.WHITE);
		ViewEditBooksP.setBorder(null);
		ViewEditBooksP.setBackground(Color.GRAY);
		tabbedPane.addTab("View/Edit/Remove Books", null, ViewEditBooksP, null);
		tabbedPane.setForegroundAt(0, Color.WHITE);
		tabbedPane.setBackgroundAt(0, Color.DARK_GRAY);
		ViewEditBooksP.setLayout(null);
		
		searchF = new JTextField();
		searchF.setFont(new Font("Tahoma", Font.BOLD, 13));
		searchF.setBackground(Color.WHITE);
		searchF.addKeyListener(new KeyAdapter() {
			@Override
			////////////////////////////////////////////////////////////////////////Get Valid Book Name or ISBN
			public void keyPressed(KeyEvent e) {

				String name=searchF.getText();
				if(name.length()<15)					/// Checking if Input Exceeds 15 Characters...
					searchF.setEditable(true);	
				else
					searchF.setEditable(false);
				
				if(e.getExtendedKeyCode()==KeyEvent.VK_BACK_SPACE)    // Setting Action Done on BackSpace Press
					searchF.setEditable(true);	
			}
			//-----------------------------------------------------------------------------------------------
		});
		searchF.setBounds(198, 33, 131, 28);
		ViewEditBooksP.add(searchF);
		searchF.setColumns(10);
		
		
		searchB = new JButton("Search");
		searchB.setForeground(Color.WHITE);
		searchB.setBackground(Color.DARK_GRAY);
		searchB.setIcon(new ImageIcon(BookMgt.class.getResource("/img/Search.png")));
		searchB.addActionListener(new ActionListener() {
			///////////////////////////////////////////////////////////////// To Search Books Matching a Sepcfic Pattern
			public void actionPerformed(ActionEvent e) {
				String searchString= searchF.getText();  // Getting String to be searched....
				String searchSql="SELECT ISBN, Title FROM book WHERE Title LIKE '%"+searchString+"%' OR ISBN like '%"+searchString+"%' ";
				setBooksToTable(searchSql);
			}
			//----------------------------------------------------------------------------------------------------------

		});
		searchB.setBounds(341, 32, 112, 29);
		searchB.setFont(new Font("Tahoma", Font.PLAIN, 14));
		ViewEditBooksP.add(searchB);
		
		JLabel lblEnterTitleOr = new JLabel("Enter Title or ISBN");
		lblEnterTitleOr.setForeground(Color.WHITE);
		lblEnterTitleOr.setBounds(73, 36, 120, 17);
		lblEnterTitleOr.setFont(new Font("Tahoma", Font.PLAIN, 14));
		ViewEditBooksP.add(lblEnterTitleOr);
		
		JScrollPane bkScrollPane = new JScrollPane();
		bkScrollPane.setBounds(73, 75, 507, 240);
		ViewEditBooksP.add(bkScrollPane);

		

////////////////////////////////////////////////////////////////////////////////// Setting the bkTable a model......		
		bkTable = new JTable();
		bkTable.setGridColor(Color.WHITE);
		bkTable.setForeground(Color.WHITE);
		bkTable.setBackground(Color.DARK_GRAY);
		bkTable.addMouseListener(new MouseAdapter() {
			@Override
			
			//////////////////////////////////////////////////////////////////////// Action When a Book is Selected
			public void mouseClicked(MouseEvent e) {
				deleteB.setEnabled(true);
				editC.setEnabled(true);
				
				int selectedRow=bkTable.getSelectedRow();
				selectedBkISBN= (String) bkModel.getValueAt(selectedRow,0); // Get ISBN, Type Casting from Object to String
				selectedBkTitle= (String) bkModel.getValueAt(selectedRow,1);// Get Title, Type Casting from object to String
				
				ISBNF.setText(selectedBkISBN);
				titleF.setText(selectedBkTitle);
			}
			
			//----------------------------------------------------------------------------------------------------------
		});
		bkTable.setFont(new Font("Tahoma", Font.PLAIN, 12));
		bkTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		bkScrollPane.setViewportView(bkTable);	
		bkTable.setModel(new DefaultTableModel( 
				new Object[][] { }, 
				new String[] {"ISBN", "Name" }) 
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			Class[] columnTypes = new Class[] {
				String.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];		
			}
		    public boolean isCellEditable(int row, int column) { 
		        return false;	
		     }
		});
		
		////////////////////////////////////// Getting book table model and assigning to tableModel Variable
		bkModel=(DefaultTableModel) bkTable.getModel();
		//---------------------------------------------------------------------------------------------------------------------------------------		
		
		ISBNF = new JTextField();
		ISBNF.addKeyListener(new KeyAdapter() {
			@Override
			////////////////////////////////////////////////////////////////////// Validate ISBN Field Input....
			public void keyPressed(KeyEvent e) {
				String ISBN=ISBNF.getText();
				if(e.getKeyChar()>='0' && e.getKeyChar()<='9') {
					if(ISBN.length()<13)
						ISBNF.setEditable(true);
					else
						ISBNF.setEditable(false);
				}else if(e.getExtendedKeyCode()==KeyEvent.VK_BACK_SPACE)
					ISBNF.setEditable(true);
				else 
					ISBNF.setEditable(false);		
			}
			//-----------------------------------------------------------------------------------------
			
		});
		ISBNF.setEditable(false);
		ISBNF.setBounds(107, 413, 103, 20);
		ViewEditBooksP.add(ISBNF);
		ISBNF.setColumns(10);
		
		titleF = new JTextField();
		titleF.setEditable(false);
		titleF.setBounds(260, 413, 170, 20);
		ViewEditBooksP.add(titleF);
		titleF.setColumns(10);
		
		JLabel lblIsbn = new JLabel("ISBN");
		lblIsbn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblIsbn.setBounds(73, 413, 37, 17);
		ViewEditBooksP.add(lblIsbn);
		
		JLabel lblTitle = new JLabel("Title");
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTitle.setBounds(229, 413, 46, 17);
		ViewEditBooksP.add(lblTitle);
		
		editC = new JCheckBox("Edit");
		editC.setFont(new Font("Tahoma", Font.PLAIN, 14));
		editC.setOpaque(false);
		editC.setEnabled(false);
		
		editC.addActionListener(new ActionListener() {
			/////////////////////////////////////////////////////////////////////////Action Taken for Editable Check Box		
			public void actionPerformed(ActionEvent e) {
				if(editC.isSelected()) {						// if CheckBox Selected, Make Fields Editable
					ISBNF.setEditable(true); titleF.setEditable(true);	
					updateB.setEnabled(true); cancelB.setEnabled(true);
				}else {												// else Clear Fields, Make Fields not Editable
					ISBNF.setEditable(false); titleF.setEditable(false);
					updateB.setEnabled(false); cancelB.setEnabled(false);
				}
			}
			//----------------------------------------------------------------------------------------------------------------------
		
		});
		editC.setBounds(73, 371, 103, 35);
		ViewEditBooksP.add(editC);
		
		updateB = new JButton("Update");
		updateB.setForeground(Color.WHITE);
		updateB.setBackground(Color.DARK_GRAY);
		updateB.addActionListener(new ActionListener() {
			///////////////////////////////////////////////////////////////////// Update Book Button...
			public void actionPerformed(ActionEvent e) {
				String ISBN,title; 
				ISBN=ISBNF.getText();
				title=titleF.getText();
				editC.setSelected(false);   // Edit Button is unchecked
				ISBNF.setEditable(false); titleF.setEditable(false);
				updateB.setEnabled(false); cancelB.setEnabled(false);
				deleteB.setEnabled(false);
				if(ISBN.equals("") || title.equals("")) {
					JOptionPane.showMessageDialog(null,"Error: Important Fields Are Missing");
				}else {
					try {
						rs=st.executeQuery("SELECT * FROM Book WHERE ISBN= '" + selectedBkISBN +"' ;");
						rs.next();
						rs.updateString("ISBN", ISBN);
						rs.updateString("Title", title);
						rs.updateRow(); //Updating
						rs.close();
					}catch(Exception err) {
						JOptionPane.showMessageDialog(null, "ISBN Might Already Exist in DataBase "+ err.getMessage());
					}
					ISBNF.setText(""); titleF.setText("");
					setBooksToTable("select * from Book");  // Refreshing Books Table
				}
			}
			//-------------------------------------------------------------------------------------
		});
		updateB.setEnabled(false);
		updateB.setFont(new Font("Tahoma", Font.PLAIN, 14));
		updateB.setBounds(459, 405, 103, 28);
		ViewEditBooksP.add(updateB);
		
		cancelB = new JButton("Cancel");
		cancelB.setBackground(Color.DARK_GRAY);
		cancelB.setForeground(Color.WHITE);
		cancelB.addActionListener(new ActionListener() {
			//////////////////////////////////////////////////////////////////////// Book Editing Cancel Action
			public void actionPerformed(ActionEvent e) {
				setBooksToTable("SELECT ISBN, Title FROM Book");
				editC.setSelected(false);
				ISBNF.setText(""); titleF.setText("");
				ISBNF.setEditable(false); titleF.setEditable(false);
				updateB.setEnabled(false); cancelB.setEnabled(false);
			}
			//-------------------------------------------------------------------------------------------------
		});
		cancelB.setEnabled(false);
		cancelB.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cancelB.setBounds(459, 444, 103, 28);
		ViewEditBooksP.add(cancelB);
		
		deleteB = new JButton("Delete Selected");
		deleteB.setFont(new Font("Tahoma", Font.PLAIN, 14));
		deleteB.setForeground(Color.WHITE);
		deleteB.setBackground(Color.DARK_GRAY);
		deleteB.addActionListener(new ActionListener() {
			
			////////////////////////////////////////////////////Delete Book Action Listener...
			public void actionPerformed(ActionEvent e) {
				String sql="Select * FROM Book WHERE ISBN =" + selectedBkISBN;
				try {
					rs=st.executeQuery(sql);
					rs.next();
					rs.deleteRow();
					rs.close();
					editC.setSelected(false); ISBNF.setText("");  titleF.setText("");
				}catch(Exception err) {
					JOptionPane.showMessageDialog(null, "Book Might Be In Use By Subjects : "+ err.getMessage());
				}
				setBooksToTable("select * from Book");
			}
			//-----------------------------------------------------------------------------------
			
		});
		deleteB.setEnabled(false);
		deleteB.setBounds(316, 326, 137, 35);
		ViewEditBooksP.add(deleteB);
		
		returnB = new JButton("Return");
		returnB.setForeground(Color.WHITE);
		returnB.setFont(new Font("Tahoma", Font.PLAIN, 14));
		returnB.setBackground(Color.DARK_GRAY);
		returnB.addActionListener(new ActionListener() {
			
			/////////////////////////////////////////////////////////////////// To return To Admin Main Menu
			public void actionPerformed(ActionEvent e) {
				dispose();
				AdminMainMenu adminMainMenu=new AdminMainMenu();
				adminMainMenu.setVisible(true);
			}
			//-----------------------------------------------------------------------------------
			
		});
		returnB.setBounds(21, 457, 89, 28);
		ViewEditBooksP.add(returnB);
		setBooksToTable("select * from book"); // Loading Book Data
		
		JPanel AddBookP = new JPanel();
		AddBookP.setBackground(Color.GRAY);
		tabbedPane.addTab("Add New Book", null, AddBookP, null);
		tabbedPane.setBackgroundAt(1, Color.DARK_GRAY);
		tabbedPane.setForegroundAt(1, Color.WHITE);
		AddBookP.setLayout(null);
		
		bISBNF = new JTextField();
		bISBNF.addKeyListener(new KeyAdapter() {
			@Override
			////////////////////////////////////////////////////////  Validate PhoneNo Input	
			public void keyPressed(KeyEvent e) {
				String ISBN=bISBNF.getText();
				if(e.getKeyChar()>='0' && e.getKeyChar()<='9') {
					if(ISBN.length()<13)
						bISBNF.setEditable(true);
					else
						bISBNF.setEditable(false);
				}else if(e.getExtendedKeyCode()==KeyEvent.VK_BACK_SPACE)
					bISBNF.setEditable(true);
				else 
					bISBNF.setEditable(false);
			}
			//------------------------------------------------------------------------------------
		});
		bISBNF.setBounds(167, 69, 197, 20);
		AddBookP.add(bISBNF);
		bISBNF.setColumns(10);
		
		bTitleF = new JTextField();
		bTitleF.addKeyListener(new KeyAdapter() {
			@Override
			////////////////////////////////////////////////////////////////////////Get Valid Book Name
			public void keyPressed(KeyEvent e) {
				String title=bTitleF.getText();
				if(title.length()<35)					/// Checking if Input Exceeds 15 Characters...
					bTitleF.setEditable(true);	
				else
					bTitleF.setEditable(false);
				
				if(e.getExtendedKeyCode()==KeyEvent.VK_BACK_SPACE)    // Setting Action Done on BackSpace Press
					bTitleF.setEditable(true);
				else if(e.getKeyChar()==KeyEvent.VK_ENTER) 
					bTitleF.requestFocus(); 		
			}
			//-----------------------------------------------------------------------------------------------
		});
		bTitleF.setBounds(167, 129, 197, 20);
		AddBookP.add(bTitleF);
		bTitleF.setColumns(10);
		
		lblBookIsbn = new JLabel("Book ISBN");
		lblBookIsbn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblBookIsbn.setBounds(96, 69, 72, 17);
		AddBookP.add(lblBookIsbn);
		
		lblBookTitle = new JLabel("Book Title");
		lblBookTitle.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblBookTitle.setBounds(96, 132, 72, 17);
		AddBookP.add(lblBookTitle);
		
		addB = new JButton("Add");
		addB.setForeground(Color.WHITE);
		addB.setBackground(Color.DARK_GRAY);
		addB.setFont(new Font("Tahoma", Font.PLAIN, 16));
		addB.addActionListener(new ActionListener() {
			
			////////////////////////////////////////////////////////////////////////// Action taken to add Books
			public void actionPerformed(ActionEvent e) {
				String ISBN,title; 
				ISBN=bISBNF.getText();
				title=bTitleF.getText();
				if(ISBN.equals("") || title.equals("")) {
					JOptionPane.showMessageDialog(null,"Error: Important Fields Are Missing");
				}else {
					try {
						rs=st.executeQuery("Select ISBN, Title FROM book ");
						rs.moveToInsertRow();
						rs.updateString( "ISBN",  ISBN);
						rs.updateString("Title", title);
						rs.insertRow();
						rs.close();
						setBooksToTable("SELECT * FROM book"); // to refresh book table
						bISBNF.setText(""); bTitleF.setText("");
					}catch(Exception err) {
						JOptionPane.showMessageDialog(null, "Book Might Exist "+ err.getMessage());
					}
					ISBNF.setText(""); titleF.setText("");
					setBooksToTable("select * from Book");
				}	
			}
			//----------------------------------------------------------------------------------------------------
			
		});
		addB.setBounds(411, 186, 98, 47);
		AddBookP.add(addB);
		
		cancelB2 = new JButton("Cancel");
		cancelB2.setForeground(Color.WHITE);
		cancelB2.setBackground(Color.DARK_GRAY);
		cancelB2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		cancelB2.addActionListener(new ActionListener() {
			
			//////////////////////////////////////////////////////// Cancel New Book Adding....
			public void actionPerformed(ActionEvent e) {
				bISBNF.setText("");
				bTitleF.setText("");
			}
			//*********************************************
			
		});
		cancelB2.setBounds(249, 186, 98, 47);
		AddBookP.add(cancelB2);
		
		returnB2 = new JButton("Return");
		returnB2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		returnB2.setForeground(Color.WHITE);
		returnB2.setBackground(Color.DARK_GRAY);
		returnB2.addActionListener(new ActionListener() {
			/////////////////////////////////////////////////////////////////// To return To Admin Main Menu
			public void actionPerformed(ActionEvent e) {
				dispose();
				AdminMainMenu adminMainMenu=new AdminMainMenu();
				adminMainMenu.setVisible(true);
			}
			//-----------------------------------------------------------------------------------
		});
		returnB2.setBounds(79, 431, 98, 32);
		AddBookP.add(returnB2);
	
		setLocationRelativeTo(null);  // Center Postion

	}
}
