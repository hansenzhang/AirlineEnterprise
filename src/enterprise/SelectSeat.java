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

public class SelectSeat extends JFrame implements WindowListener {

	private static final long serialVersionUID = -6976814425210428319L;
	final SelectFlight sFlight;
	final JFrame parent;
	private JList list;
	private JScrollPane scrollPane;
	private DefaultListModel listModel = new DefaultListModel();
	final JPanel panel;
	protected int seatId;
	private JLabel lblSelectASeat;
	private JButton btnBack;
	
	Enterprise en;
	enterprise.SelectFlight.ReservationSF flight;
	
	/**
	 * Constructor SelectSeat creates a new select set instance to
	 * create a seat for the createreservation leg.
	 * @param selectFlightFrame
	 * @param temp
	 */
	SelectSeat(JFrame selectFlightFrame, enterprise.SelectFlight.ReservationSF temp) {
		super("Select a Seat");
		parent = selectFlightFrame;
		sFlight = (SelectFlight) selectFlightFrame;
		en = sFlight.en;
		flight = temp;
		list = new JList(listModel);
		panel = new JPanel();
		panel.setLayout(new MigLayout("", "[300px]", "[][400px]"));
		
		btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ee) {
				SelectSeat.this.setVisible(false);
				SelectSeat.this.dispose();
				SelectSeat.this.parent.setVisible(true);
			}
		
		});
		
		panel.add(btnBack, "flowx,cell 0 0");
		
		lblSelectASeat = new JLabel("Select a seat by clicking on it.");
		panel.add(lblSelectASeat, "cell 0 0");
		
		scrollPane = new JScrollPane(list);
		scrollPane.setPreferredSize(new Dimension (300,400));
		panel.add(scrollPane, "cell 0 1,alignx left,aligny top");	
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(this);
		try {
			loadSeats(sFlight.legInstanceId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		this.getContentPane().add(panel);
		//this.setAlwaysOnTop(true);
		this.pack();
		list.addMouseListener(new MouseAdapter (){
			public void mouseClicked(MouseEvent me){
				//There needs to be a new type here.
				ReservationSS temp = (ReservationSS) list.getSelectedValue();
				seatId = temp.seatId;
				if (temp != null) {
					//format this to look NICE!
					//SelectFlight.this.dispose();
					//new SelectSeat(SelectFlight.this);
					//insert values?
					String s = "<html><body width='400"+
		                    "'><h2> Is this information correct?</h2>" +
		                    "<p>Route: " + SelectSeat.this.flight.src + " to " + SelectSeat.this.flight.dest + "<br>" +		                   
		                    "Date: " + SelectSeat.this.flight.date.toString() + "<br>"+
		                    "Departure Time: " + SelectSeat.this.flight.depTime.toString() + "<br>"+
		                    "Arrival Time: " + SelectSeat.this.flight.arrTime.toString()+"<br>"+
		                    "Seat number: " + seatId;
					int dialogResult = JOptionPane.showConfirmDialog(SelectSeat.this, 
							s,
							"Warning",JOptionPane.YES_NO_OPTION);
					if (dialogResult == 0) {
						JOptionPane.showMessageDialog(SelectSeat.this, "Created reservation."); 
						updateTables();
						SelectSeat.this.setVisible(false);
						SelectSeat.this.dispose();
						SelectSeat.this.parent.dispose();
						SelectSeat.this.sFlight.cRes.parent.setVisible(true);
					}
						
					//JOptionPane.showMessageDialog(ViewReservation.this,
					//temp.src + " - "+temp.dest, "Reservation", JOptionPane.PLAIN_MESSAGE);  
				}
			}
			
		});
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		if (list.getModel().getSize()==0) {
			JOptionPane.showMessageDialog(this, "No seats available.");
			this.dispose();			
			parent.setVisible(true);
		}
	}
	
	protected void updateTables() {
		CallableStatement cs = null;
		try{
			String call = "{call insert_reservation(?,?,?)}";
			cs = en.conn.prepareCall(call);
			
			cs.setInt(1, sFlight.legInstanceId); //Leg_instance_id
			cs.setInt(2, seatId); // Seat_id
			cs.setInt(3, sFlight.cRes.userID); // CustomerId
			
			cs.executeUpdate();
			
			//Commit transaction
			en.conn.commit();
		} catch (SQLException e) {
			System.err.println("Exception caught in SelectSeat - updateTables(): " + e.getMessage()); 
		}
	}

	private void loadSeats(int legInstanceId) throws SQLException {
		CallableStatement cs = null;
		ResultSet rs = null;
		try {
			String call = "{call check_seat(?,?)}";
			cs = en.conn.prepareCall(call); 
			
			cs.setInt(1, legInstanceId);
			cs.registerOutParameter(2, OracleTypes.CURSOR);
			
			cs.executeUpdate();
			rs = (ResultSet) cs.getObject(2);
			while (rs.next()) {
				listModel.addElement(new ReservationSS(rs));
			}
			
		} catch (SQLException e) {
			System.err.println("SelectSeat:loadSeat - " + e.getMessage());
		} finally {
			if (rs != null) {
				rs.close();
			}
 
			if (cs != null) {
				cs.close();
			}
		}
		
	}

	class ReservationSS {
		int seatId;
		ReservationSS(ResultSet r) throws SQLException {
			seatId = r.getInt(1);
		}
		public String toString(){ return "" + seatId;}
	}
	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		this.setVisible(false);
		this.dispose();
		parent.setVisible(true);
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
