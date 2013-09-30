package enterprise;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import net.miginfocom.swing.MigLayout;
import oracle.jdbc.OracleTypes;

public class ModifyFare extends JFrame implements WindowListener {
	
	private static final long serialVersionUID = 5745108813561443471L;
	final Manager man;
	final JFrame parent;
	private JList list;
	private JScrollPane scrollPane;
	private DefaultListModel listModel = new DefaultListModel();
	final JPanel panel;
	protected int fare, fareCode;
	private JLabel lblChangeFare;
	private JButton btnBack;
	
	/**
	 * Constructor ModifyFare creates a new frame that can modify
	 * fare prices.
	 * @param managerFrame
	 */
	ModifyFare (JFrame managerFrame) {
		super("Change Fares");
		man = (Manager) managerFrame;
		parent = managerFrame;
		list = new JList(listModel);
		panel = new JPanel();
		panel.setLayout(new MigLayout("", "[300px]", "[][400px]"));
		
		btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ee) {
				ModifyFare.this.setVisible(false);
				ModifyFare.this.dispose();
				ModifyFare.this.parent.setVisible(true);
			}
		
		});
		
		panel.add(btnBack, "flowx,cell 0 0");
		
		lblChangeFare = new JLabel("Change a fare by clicking on it.");
		panel.add(lblChangeFare, "cell 0 0");
		
		scrollPane = new JScrollPane(list);
		scrollPane.setPreferredSize(new Dimension (300,400));
		panel.add(scrollPane, "cell 0 1,alignx left,aligny top");	
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(this);
		
		try {
			loadFares();
		} catch (SQLException e) {
			System.err.println("Error in loadfares" + e.getMessage());
		}
		
		this.getContentPane().add(panel);
		//this.setAlwaysOnTop(true);
		this.pack();
		
		list.addMouseListener(new MouseAdapter (){
			public void mouseClicked(MouseEvent me){
				//There needs to be a new type here.
				Fare temp = (Fare) list.getSelectedValue();
				//fare = temp.fare;
				fareCode = temp.fareCode;
				if (temp != null) {
					//format this to look NICE!
					//SelectFlight.this.dispose();
					//new SelectSeat(SelectFlight.this);
					//insert values?
					boolean isValid = false;
					String message = "";
					do {
						try{
							fare = Integer.valueOf(JOptionPane.showInputDialog ("Enter new fare"));
							if (fare != 0)
								isValid = true;
						} catch (NumberFormatException e) {
							message = e.getMessage();
							if (message == "null"){
								isValid = true;
							} else
								JOptionPane.showMessageDialog(ModifyFare.this, "Please enter a number.");
						}
					} while (!isValid);
					if (message != "null"){ 
						JOptionPane.showMessageDialog(ModifyFare.this, "Fare'" + fareCode + "' updated.");
						updateTables(fare, fareCode);
						ModifyFare.this.listModel.clear();
						try {
							ModifyFare.this.loadFares();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					/*
					ModifyFare.this.setVisible(false);
					ModifyFare.this.dispose();
					ModifyFare.this.parent.dispose();
					ModifyFare.this.parent.setVisible(true);*/
					
						
					//JOptionPane.showMessageDialog(ViewReservation.this,
					//temp.src + " - "+temp.dest, "Reservation", JOptionPane.PLAIN_MESSAGE);  
				}
			}
			
		});
		
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
		// Emergency case if there aren't any values.
		if (list.getModel().getSize() == 0) {
			JOptionPane.showMessageDialog(this, "No fares available.");
			this.dispose();			
			parent.setVisible(true);
		}
	}
	
	/**
	 * Function load fares finds the current fares
	 * @throws SQLException
	 */
	void loadFares () throws SQLException {
		CallableStatement cs = null;
		ResultSet rs = null;
		try {
			String call = "{call load_fare(?)}";
			cs = man.en.conn.prepareCall(call);
			
			cs.registerOutParameter(1, OracleTypes.CURSOR);
			
			cs.executeUpdate();
			
			rs = (ResultSet) cs.getObject(1);
			while (rs.next()) {
				listModel.addElement(new Fare(rs));
			}
		} catch (SQLException e) {
			System.err.println("ModifyFare:loadFares - " + e.getMessage());
		} finally {
			if (rs != null) {
				rs.close();
			}
 
			if (cs != null) {
				cs.close();
			}
		}
	}
	
	/**
	 * update tables updates the fares table with a new fare.
	 * @param fare
	 * @param fareCode
	 */
	protected void updateTables(int fare, int fareCode) {
		CallableStatement cs = null;
		try {
			String call = "{call update_fare(?,?)}";
			cs = man.en.conn.prepareCall(call);
			cs.setInt(1, fareCode);
			cs.setInt(2, fare);
			
			cs.executeUpdate();
			
			man.en.conn.commit();			
		} catch (SQLException e) {
			System.err.println("Exception caught in ModifyFare - updateTables(): " + e.getMessage());
		}
	}
	
	class Fare {
		int fareCode, fare;
		String description;
		Fare (ResultSet rs) throws SQLException {
			fareCode = rs.getInt(1);
			fare = rs.getInt(2);
			description = rs.getString(3);
		}
		
		public String toString() {return "Fare Code: " + fareCode + "   $" + fare;}
	}
	
	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
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
