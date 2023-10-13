package adminProgram;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import connectionProgram.Connect;
import net.proteanit.sql.DbUtils;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.SystemColor;

public class CustomQuery extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField queryF;
	private JTable resultT;
	
	
	
	private Statement st=null; //connection Objects
	private ResultSet rs=null;   // Connection Objects
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CustomQuery frame = new CustomQuery();
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
	public CustomQuery() {
		
		/////////////////////////////////////////////////////////////////////
		st=Connect.getConnection(Connect.UPDATE_ACCESS); // To get Connection As Soon as JFrame in Created
		//------------------------------------------------------------	
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 866, 522);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.controlDkShadow);
		panel.setBounds(0, 0, 845, 483);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Enter Query");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(40, 63, 111, 23);
		panel.add(lblNewLabel);
		
		queryF = new JTextField();
		queryF.setBounds(161, 66, 542, 20);
		panel.add(queryF);
		queryF.setColumns(10);
		
		JButton goB = new JButton("Go");
		goB.setFocusable(false);
		goB.setBackground(Color.DARK_GRAY);
		goB.setForeground(Color.WHITE);
		goB.setFont(new Font("Tahoma", Font.BOLD, 14));
		goB.addActionListener(new ActionListener() {
			
			///////////////////////////////////////////////// Go Query Action
			public void actionPerformed(ActionEvent e) {
				String sql=queryF.getText();
				try {
					rs=st.executeQuery(sql); 
					resultT.setModel(DbUtils.resultSetToTableModel(rs));
				}catch(Exception err) {
					JOptionPane.showMessageDialog(null, err.getMessage());
				}
			}
			//-----------------------------------------
			
		});
		goB.setBounds(725, 65, 89, 23);
		panel.add(goB);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBackground(Color.DARK_GRAY);
		scrollPane.setBounds(67, 137, 748, 306);
		panel.add(scrollPane);
		
		resultT = new JTable();
		resultT.setFont(new Font("Tahoma", Font.PLAIN, 12));
		resultT.setForeground(Color.WHITE);
		resultT.setBackground(Color.DARK_GRAY);
		resultT.setEnabled(false);
		scrollPane.setViewportView(resultT);
		
		JButton returnB = new JButton("Return");
		returnB.setFocusable(false);
		returnB.setForeground(Color.WHITE);
		returnB.setBackground(Color.DARK_GRAY);
		returnB.setFont(new Font("Tahoma", Font.PLAIN, 14));
		returnB.addActionListener(new ActionListener() {
			
			///////////////////////////////////////////////// Return Button to return to adminMainMenu
			public void actionPerformed(ActionEvent e) {
				dispose();
				AdminMainMenu adminMainMenu =new AdminMainMenu();
				adminMainMenu.setVisible(true);
			}
			//---------------------------------------------------------------------------
			
		});
		returnB.setBounds(10, 11, 101, 34);
		panel.add(returnB);
		setLocationRelativeTo(null);  // Center Postion

	}

}
