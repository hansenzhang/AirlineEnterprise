package enterprise;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class Enterprise extends JFrame implements ActionListener, WindowListener{
	
	private static final long serialVersionUID = 1L;
	ResultSet result;
	ResultSetMetaData metaData;
	int rowCount, colCount;
	Connection conn; //This is the global connection.  Each command will use this.
	String query;
	boolean running;

	final JButton customer, manager;
	private JButton btnNewButton;
	
	/**
	 * Default Constructor for the program.  Opens up connection and starts listener
	 * for an action event.
	 */
	public Enterprise () {		
		super("Enterprise");
		this.openConnection("", "");// Need to edit to talke input
		JPanel panel = new JPanel();
		customer = new JButton("Customer");
		customer.addActionListener(this);
		
		manager = new JButton("Manager");
		manager.addActionListener(this);
		panel.setLayout(new MigLayout("", "[120.00px][111.00:111.00][180px]", "[138px][][]"));
		
		panel.add(customer, "cell 0 0,grow");
		panel.add(manager, "cell 2 0,grow");
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		getContentPane().add(panel, BorderLayout.CENTER);
		
		btnNewButton = new JButton("Quit");
		btnNewButton.addActionListener(new ExitListener());
		panel.add(btnNewButton, "cell 1 2,alignx center");
		setSize(400, 200);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);		
		this.setVisible(true);
		this.addWindowListener(this);
	}
	
	/**
	 * Extends current class for actionPerforemd on action event
	 * Splitting between Customer login and manager login.
	 */
	public void actionPerformed(ActionEvent ee){
		if (ee.getSource() == customer) {
			this.setVisible(false);
			new Login(this);
			//create child and deactivate current screen
		} else if (ee.getSource() == manager) {
			this.setVisible(false);
			new Manager(this);
		} else {
			//crash program? this shouldn't happen.
		}
		
	}
	
	/**
	 * Open connection with username and string.
	 * @param username
	 * @param password
	 * @return
	 */
	boolean openConnection(String username, String password) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// TODO: update for sqlite
            conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@edgar2.cse.lehigh.edu:1521:cse241",
					username, password);
			conn.setAutoCommit(false);
			System.out.println("Connected.");
			return true;
		} catch (SQLException e) {
			System.out.println("SQL Exception: " + e.toString());
		} catch (NoClassDefFoundError e) {
			System.out.println("SQL Exception: " + e.toString());
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException: " + e.toString());
		}
		return false;
	}

	/**
	 * Closes connection to database
	 */
	protected void closeConnection() {
		try {
			conn.close();
			System.out.println("Disconnected.");
		} catch (SQLException e) {
			System.out.println("SQL Exception: " + e.toString());
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Enterprise();
		
	}

	/**
	 * Class ExitListener check to see if the main program is being closed and 
	 * brings up a prompt for closing.
	 * @author hansen
	 *
	 */
	private class ExitListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
			int confirm = JOptionPane.showConfirmDialog (null, 
					"Are you sure you want to exit?",
					"Exit Confirmation",JOptionPane.YES_NO_OPTION);           
            if (confirm == JOptionPane.YES_OPTION) {
                Enterprise.this.closeConnection();
            	System.exit(0);
            }
        }
    }
	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		System.exit(0);
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		// TODO Auto-generated method stub
		//maybe a warning message
		
			int confirm = JOptionPane.showConfirmDialog (null, 
					"Are You Sure to Close this Application?",
					"Exit Confirmation",JOptionPane.YES_NO_OPTION);           
            if (confirm == JOptionPane.YES_OPTION) {
            	this.closeConnection();
            	System.exit(0);
            }
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
