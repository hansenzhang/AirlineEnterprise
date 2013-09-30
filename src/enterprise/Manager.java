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
import javax.swing.JPanel;

public class Manager extends JFrame implements ActionListener, WindowListener{
	
	private static final long serialVersionUID = -8788942332388536247L;
	Enterprise en;
	JFrame parent;
	final JButton modifyRes, modifyFare, makeUser, logout;
	
	/**
	 * Constructor Manager creates a manager from 
	 * the enterprise frame.
	 * @param master
	 */
	Manager (JFrame master){
		super("Welcome Manager");
		parent = master;
		en = (Enterprise) master;
		
		JPanel panel = new JPanel(new GridLayout(5,2));		
		
		modifyRes = new JButton("Modify Reservation");
		modifyRes.addActionListener(this);
		
		modifyFare = new JButton("Modify Fares");
		modifyFare.addActionListener(this);
		
		makeUser = new JButton("Add New Customer");
		makeUser.addActionListener(this);
		
		logout = new JButton("Logout");
		logout.addActionListener(this);
		
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		//add a quit button as well
		
		panel.add(modifyRes);
		panel.add(modifyFare);
		panel.add(makeUser);
		panel.add(logout);
		
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		add(panel, BorderLayout.CENTER);
		setSize(400, 300);
		this.setLocationRelativeTo(null);		
		setVisible(true);
		
		this.addWindowListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent ee) {
		this.setVisible(false);
		if (ee.getSource() == modifyRes) {
			new ModifyReservation (this);
		} else if (ee.getSource() == modifyFare) {
			new ModifyFare(this);
		} else if (ee.getSource() == makeUser) {
			new CreateCustomer(this, en.conn);
		} else {
			this.dispose();
			parent.setVisible(true);
		}
		
	}
	
	@Override
	public void windowActivated(WindowEvent arg0) {		
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		parent.setVisible(true);
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		this.setVisible(false);
		this.dispose();		
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		
	}

}
