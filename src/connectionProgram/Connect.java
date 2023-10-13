package connectionProgram;
import java.sql.*;

import javax.swing.JOptionPane;

public class Connect {
	
	public static final int UPDATE_ACCESS=1; // To Make an Updatable Connection
	
	public static Statement getConnection() { // Method to return read Only Statment Obj
		Connection con;
		try { 	 
			 con = DriverManager.getConnection( "jdbc:mysql://localhost:3306/school", "root","" );   // establishing connection
			 Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			 return st;
		}catch (SQLException err) {
			JOptionPane.showMessageDialog(null, "Unable to Make Connection With DataBase \nProgram Will Terminate Now");
			System.exit(0);
		}
		return null;
	}
	
	
	public static Statement getConnection(int UPDATE_ACCESS) { // Method to return read and Update Statment Obj
		Connection con;
		try { 	 
			  con = DriverManager.getConnection( "jdbc:mysql://localhost:3306/school", "root","" );   // establishing connection
		      Statement  st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
		      return st;
		}catch (SQLException err) {
			JOptionPane.showMessageDialog(null, "Unable to Make Connection With DataBase \nProgram Will Terminate Now");
			System.exit(0);
		}
		return null;
	}
}
