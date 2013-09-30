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

public class ViewReservation extends JFrame implements WindowListener{

	private static final long serialVersionUID = -4362362792904816996L;
	private JList list;
	private JScrollPane scrollPane;
	private DefaultListModel listModel = new DefaultListModel();
	final JPanel panel;
	JFrame parent;
	private JLabel lblClickOnA;
	private JButton btnBack;
	
	Enterprise en;
	int userID;

	/**
	 * View reservation creates a frame with all a current user's views.
	 * @param customerFrame
	 * @param en
	 * @param userID
	 */
	ViewReservation(JFrame customerFrame, Enterprise en, int userID){
		super("Reservations");
		
		list = new JList(listModel);
		panel = new JPanel();
		panel.setLayout(new MigLayout("", "[500px]", "[][500px]"));
		
		parent = customerFrame;
		
		this.en = en;
		this.userID = userID;
		
		btnBack = new JButton("Back");
		panel.add(btnBack, "flowx,cell 0 0");
		btnBack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ee) {
				ViewReservation.this.setVisible(false);
				ViewReservation.this.dispose();
				ViewReservation.this.parent.setVisible(true);
			}
		
		});
		
		lblClickOnA = new JLabel("Click on a flight to view more details.");
		panel.add(lblClickOnA, "cell 0 0");
		scrollPane = new JScrollPane(list);
		scrollPane.setPreferredSize(new Dimension (500,500));
		panel.add(scrollPane, "cell 0 1,alignx left,aligny top");		
		
		//panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
		//this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		this.getContentPane().add(panel);
		
		this.pack();
		this.setLocationRelativeTo(null);
		
		
		//listModel.addElement("");
		this.addWindowListener(this); //LOL this works
		
		try {
			//where number = user id
			loadReservations(userID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		list.addMouseListener(new MouseAdapter (){
			public void mouseClicked(MouseEvent me){
				ReservationVR temp = (ReservationVR) list.getSelectedValue();
				if (temp != null) {
					//format this to look NICE!
					 String pt1 = "<html><body width='300"+
			                    "'><h1>Reservation #" +temp.resId+"</h1>" +
			                    "<p>Route: " + temp.src + " to " + temp.dest + "<br>" +
			                    "Source: " + temp.fullSrc + "<br>" + 
			                    "Destination: " + temp.fullDest + "<br>" + 
			                    "Date: " + temp.date.toString() + "<br>"+
			                    "Departure Time: " + temp.depTime.toString() + "<br>"+
			                    "Arrival Time: " + temp.arrTime.toString()+"<br>";
					JOptionPane.showMessageDialog(ViewReservation.this, pt1);  
				}
			}
			
		});
        //Display the window.
        
        this.setVisible(true);
		
	}
	
	//Throws exceptions only from finally clause which closes result set and statement.
	void loadReservations(int userId) throws SQLException{
		//String query = "select * from ";
		CallableStatement cs = null;
		ResultSet rs = null;
		try {
			/* Pulls from the Enterprise class which is instantiated in Customer*/
			//Statement st = cust.en.conn.createStatement(); ///VERY VERY BAD?
			
			String call = "{call view_res(?,?)}"; //call stored procedure
			cs = en.conn.prepareCall(call);	
			
			cs.setInt(1, userId);
			cs.registerOutParameter(2, OracleTypes.CURSOR);
			
			cs.executeUpdate();
			
			rs = (ResultSet) cs.getObject(2);
			//ResultSetMetaData metaData = rs.getMetaData();
			//int columnSize = metaData.getColumnCount();
			while (rs.next()) {

				//create new reservation here
				listModel.addElement(new ReservationVR(rs));
				//attach a new handler to each row to display more information
			}
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			if (rs != null) {
				rs.close();
			}
 
			if (cs != null) {
				cs.close();
			}			
		}
		
	}
	
	// NO TIME TO FIX OVERRIDES/OVERLOADS for this, so they're all unique classes.
	class ReservationVR {
		int resId, seatNum, fare;		
		String src, dest, fullSrc, fullDest;
		LocalTime arrTime, depTime;
		LocalDate date;
		
		ReservationVR (ResultSet r) throws SQLException{
			resId = r.getInt(1);
			src = r.getString(2);
			dest = r.getString(3);
			fullSrc = r.getString(4);
			fullDest = r.getString(5);
			date = LocalDate.fromDateFields(r.getDate(6));
			depTime = convertToTime(r.getString(7));
			arrTime = convertToTime(r.getString(8));
			seatNum = r.getInt(9);
			fare = r.getInt(10);
		}
		
		LocalTime convertToTime(String src){
			//sepearte string into parts
			String [] parts = src.split(":");
			int hours = Integer.parseInt(parts[0]), minutes = Integer.parseInt(parts[1]), seconds = Integer.parseInt(parts[2]);			
			return new LocalTime (hours, minutes ,seconds);
		}
		
		public String toString(){return "Date: " + date.toString()+ "    Source: " +src +"    Destination: "+ dest +"    seat: " + seatNum ;}  		
		
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		parent.setVisible(true);
		//System.out.println("Set parent to visible here in View Reservation");
	}

	@Override
	public void windowClosing(WindowEvent e) {
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
