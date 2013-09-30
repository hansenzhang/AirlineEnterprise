package enterprise;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Customer extends JFrame implements ActionListener, WindowListener {

	private static final long serialVersionUID = 1L;
	//ArrayList <JFrame> booking;
	final JButton viewReservation, createReservation, editProfile,
		dropReservation, logout; 
	String username, password;
	int userID;
	JFrame parent;
	Enterprise en;
	Login login;
	
	/**
	 * Customer frame creates a new customer frame
	 * @param master
	 * @param loginFrame
	 */
	Customer (JFrame master, JFrame loginFrame){
		super("Welcome " + ((Login)loginFrame).userName); 
		parent = master;
		en = (Enterprise) master;
		en.setVisible(false);
		this.login = (Login) loginFrame; 
		
		userID = login.userId;
		
		JPanel panel = new JPanel(new GridLayout(5,2));
		
		viewReservation = new JButton("View Reservations");
		viewReservation.addActionListener(this);
		
		createReservation = new JButton("Book a Reservation");
		createReservation.addActionListener(this);
		
		editProfile = new JButton("Edit Profile");
		editProfile.addActionListener(this);
		
		dropReservation = new JButton("Delete a Reservation");
		dropReservation.addActionListener(this);
		
		logout = new JButton("Logout");
		logout.addActionListener(new ExitListener());
		
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		//add a quit button as well
		panel.add(viewReservation);
		panel.add(createReservation);
		panel.add(dropReservation);
		panel.add(editProfile);		
		panel.add(logout);
		
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		add(panel, BorderLayout.CENTER);
		
		setSize(400, 300);
		this.setLocationRelativeTo(null);
		this.addWindowListener(this);
		this.setVisible(true);			
	}
	
	/**
	 * Create a new Frame based on the button clicked by user.
	 */
	@Override
	public void actionPerformed(ActionEvent ee) {
		if (ee.getSource() == viewReservation) {
			this.setVisible(false);
			new ViewReservation(this, en, userID);
		} else if (ee.getSource() == createReservation){
			this.setVisible(false);
			new CreateReservation(this, en, userID);
		} else if (ee.getSource() == dropReservation) {
			this.setVisible(false);
			new DropReservation(this, en, userID);
		} else if (ee.getSource() == editProfile){
			this.setVisible(false);
			new EditCustomer(this, en.conn, userID);
		} else {
			//logout handler
		}

	}
	
	private class ExitListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
			int confirm = JOptionPane.showConfirmDialog (null, 
					"Are you sure you want to logout?",
					"Logout",JOptionPane.YES_NO_OPTION);           
            if (confirm == JOptionPane.YES_OPTION) {
                Customer.this.setVisible(false);
                Customer.this.dispose();
                Customer.this.parent.setVisible(true);
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
		en.setVisible(true);
	}
	@Override
	public void windowClosing(WindowEvent arg0) {
		// TODO Auto-generated method stub
		int confirm = JOptionPane.showConfirmDialog(null,
                "Are you sure you want to log out?",
                "Logout", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            this.dispose();         
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
