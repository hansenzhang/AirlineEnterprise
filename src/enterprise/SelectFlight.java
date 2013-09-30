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

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;


class SelectFlight extends JFrame implements WindowListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2680022629488916137L;
	private JList list;
	private JScrollPane scrollPane;
	private DefaultListModel listModel = new DefaultListModel();
	final JPanel panel;
	final JFrame parent;
	CreateReservation cRes;
	
	int legInstanceId;
	private JLabel lblSelectAFlight;
	private JButton btnGoBack;
	
	Enterprise en;
	
	/**
	 * SelectFlight takes a flight to select from createreservation
	 * @param createReservationFrame
	 */
	SelectFlight(JFrame createReservationFrame){
		super("Book a Reservation");
		cRes = (CreateReservation) createReservationFrame;
		this.en = cRes.en;
		parent = createReservationFrame;
		
		list = new JList(listModel);
		panel = new JPanel();
		panel.setLayout(new MigLayout("", "[500px]", "[][200px]"));
		
		btnGoBack = new JButton("Back");
		btnGoBack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ee) {
				SelectFlight.this.setVisible(false);
				SelectFlight.this.dispose();
				SelectFlight.this.parent.setVisible(true);
			}
		
		});
		panel.add(btnGoBack, "flowx,cell 0 0");
		
		lblSelectAFlight = new JLabel("Select a flight by clicking on it.");
		panel.add(lblSelectAFlight, "cell 0 0");
		scrollPane = new JScrollPane(list);
		scrollPane.setPreferredSize(new Dimension (500,200));
		panel.add(scrollPane, "cell 0 1,alignx left,aligny top");	
		
		try {
			loadFlights(cRes.srcAirport, cRes.dstAirport, cRes.flightDate);
		} catch (SQLException e) {
			System.err.println("Exception caught: " + e.getMessage());
		}
		
		
		this.getContentPane().add(panel);		
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.pack();
		this.setLocationRelativeTo(null);
		this.addWindowListener(this);
		
		list.addMouseListener(new MouseAdapter (){
			public void mouseClicked(MouseEvent me){
				//There needs to be a new type here.
				ReservationSF temp = (ReservationSF) list.getSelectedValue();
				legInstanceId = temp.instanceId;
				if (temp != null) {
					//format this to look NICE!
					SelectFlight.this.setVisible(false);								
					new SelectSeat((JFrame)SelectFlight.this, temp);
					//JOptionPane.showMessageDialog(ViewReservation.this, temp.src + " - "+temp.dest, "Reservation", JOptionPane.PLAIN_MESSAGE);  
				}
			}
			
		});
		
		this.setVisible(true);
		if (list.getModel().getSize() == 0) {
			JOptionPane.showMessageDialog(this, "No flights found.");
			this.setVisible(false);
			this.dispose();			
			parent.setVisible(true);
		}
	}

	/**
	 * Function load flights finds all flights for the current user
	 * @param src
	 * @param dst
	 * @param date
	 * @throws SQLException
	 */
	void loadFlights(String src, String dst, LocalDate date) throws SQLException{
		//need to format date to specification.
		String day = null;
		if (date.getDayOfMonth() < 10){
			day = "0" + date.getDayOfMonth();
		} else
			day = "" + date.getDayOfMonth();
		String newdate = day + "-" + date.toString("MMM").toUpperCase() + "-" + date.toString("YY");
		CallableStatement cs = null;
		ResultSet rs = null;
		try {
			/* Pulls from the Enterprise class which is instantiated in Customer*/
			//Statement st = cust.en.conn.createStatement(); ///VERY VERY BAD?
			
			String call = "{call view_flights(?,?,?,?)}"; //call stored procedure
			
			cs = en.conn.prepareCall(call);	//WOW....
			
			cs.setString(1, src);
			cs.setString(2, dst);
			cs.setString(3, newdate);
			cs.registerOutParameter(4, OracleTypes.CURSOR);
			
			cs.executeUpdate();
			
			rs = (ResultSet) cs.getObject(4);
			//ResultSetMetaData metaData = rs.getMetaData();
			//int columnSize = metaData.getColumnCount();
			
			while (rs.next()) {
				//create new reservation here
				listModel.addElement(new ReservationSF(rs));
				//attach a new handler to each row to display more information
			}
		} catch (SQLException e) {			
			// This is a safe exception when caught.  
			// There is the expected output when the flight doesn't exist.
			System.err.println("Exception caught: " + e.getMessage()); 
		} finally {
			if (rs != null) {
				rs.close();
			}
 
			if (cs != null) {
				cs.close();
			}			
		};
		
	}
	
	/**
	 * Class reservation holds information needed to retrieve reservation data
	 * @author hansen
	 *
	 */
	class ReservationSF {
		int resId, seatNum, fare, instanceId;		
		String src, dest;
		LocalTime arrTime, depTime;
		LocalDate date;
		
		ReservationSF (ResultSet r) throws SQLException{
			src = r.getString(1);
			dest = r.getString(2);
			date = LocalDate.fromDateFields(r.getDate(3));
			depTime = convertToTime(r.getString(4));
			arrTime = convertToTime(r.getString(5));
			instanceId = r.getInt(6);
		}
		
		LocalTime convertToTime(String src){
			//sepearte string into parts
			String [] parts = src.split(":");
			int hours = Integer.parseInt(parts[0]), minutes = Integer.parseInt(parts[1]), seconds = Integer.parseInt(parts[2]);			
			return new LocalTime (hours, minutes ,seconds);
		}
		
		public String toString(){return "Date: " + date.toString()+ "    Source: " +src +"    Destination: "+ dest +"    Departure Time: " + depTime.toString() + "   Arrival Time: " +  arrTime.toString();}  		
		
	}
	
	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		parent.setVisible(true);
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		// TODO Auto-generated method stub
		this.setVisible(false);
		this.dispose();
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