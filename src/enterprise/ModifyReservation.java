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

public class ModifyReservation extends JFrame implements ActionListener, WindowListener{
	
	private static final long serialVersionUID = 7281844753995701994L;
	final JButton viewReservation, dropReservation, createReservation, logout;
	final JPanel panel;
	JFrame parent;
	Manager manager;
	int userID;
	
	/**
	 * ModifyReservation brings an new pane and 
	 * to change Reseravtions.
	 * @param managerFrame
	 */
	ModifyReservation (JFrame managerFrame){
		super("Modify Reservation");
		manager = (Manager) managerFrame;
		parent = managerFrame;
		
		
		//if null, then go back.
		
		panel = new JPanel(new GridLayout(5,2));
		
		viewReservation = new JButton("View Reservation");
		viewReservation.addActionListener(this);
		
		createReservation = new JButton("Book Reservation");
		createReservation.addActionListener(this);
		
		dropReservation = new JButton("Drop Reservation");
		dropReservation.addActionListener(this);
		
		logout = new JButton("Logout");
		logout.addActionListener(this);
		
		
		panel.add(createReservation);
		panel.add(viewReservation);
		panel.add(dropReservation);
		panel.add(logout);
		
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		add(panel, BorderLayout.CENTER);
		this.setSize(400, 300);
		this.setLocationRelativeTo(null);	
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setVisible(true);
		this.addWindowListener(this);
		
		boolean isValid = false;
		String message = "";
		do {
			try{
				//We assume that managers know user id numbers for simplicity
				//We could use a lookup table, but the size and complexity wouldn't be 
				//worth it.
				userID = Integer.valueOf(JOptionPane.showInputDialog ("Enter user id number (between 101 and 2000)"));
				if (userID != 0)
					isValid = true;
			} catch (NumberFormatException e) {
				message = e.getMessage();
				if (message == "null"){
					isValid = true;
				} else
					JOptionPane.showMessageDialog(this, "Please enter a valid user id number.");
			}
		} while (!isValid);
		
		if (message == "null"){
			this.setVisible(false);
			parent.setVisible(true);
			this.dispose();		
		}
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == viewReservation ) {
			new ViewReservation(this, manager.en, userID);
		} else if (e.getSource() == createReservation) {			
			new CreateReservation(this, manager.en, userID);
		} else if (e.getSource() == dropReservation){
			new DropReservation(this, manager.en, userID);
		} else {
			this.setVisible(false);
			parent.setVisible(true);
			this.dispose();
		}
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		this.parent.setVisible(true);
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		this.setVisible(false);
		this.dispose();
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
}
