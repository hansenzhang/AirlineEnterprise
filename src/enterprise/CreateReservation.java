package enterprise;

import java.awt.Button;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Date;
import java.util.SortedSet;

import javax.swing.Box;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import org.jdesktop.swingx.JXMonthView;
import org.jdesktop.swingx.calendar.DateSelectionModel.SelectionMode;
import org.joda.time.LocalDate;

public class CreateReservation extends JFrame implements WindowListener {
	
	private static final long serialVersionUID = -7079334833544502357L;
	final JComboBox srcAirportList, dstAirportList;
	final JXMonthView datePicker;
	
	LocalDate flightDate;

	final JPanel panel;
	final JFrame parent;
	String  srcAirport, dstAirport;
	String [] airports = {
			"-Select an Airport-",
			"EWR",
			"IAH",
			"JFK",
			"LAX",
			"LGA",
			"LHR",
			"MIA",
			"ORD",
			"PEK",
			"PHL"
	};
	private Component verticalStrut;
	private Component horizontalStrut;
	private JLabel lblDepartingAirport;
	private JLabel lblArrivingAirport;
	private Button button;
	private Component verticalStrut_1;
	
	Enterprise en;
	int userID;
	
	/**
	 * Constructor CreateReservation takes in a parent frame and enterprise object to create a new 
	 * reservation in the database.
	 * @param customerFrame
	 * @param en
	 * @param userID
	 */
	CreateReservation (JFrame customerFrame, Enterprise en, int userID){
		super("Book a Reservation");
		parent = customerFrame;
		//JPanel controller = new JPanel(new GridLayout(2,1));
		panel = new JPanel();
		this.en = en;
		this.userID = userID;
		
		srcAirportList = new JComboBox(airports);
		srcAirportList.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				srcAirport = ((JComboBox)e.getSource()).getSelectedItem().toString();				
			}
			
		});
		
		dstAirportList = new JComboBox(airports);
		dstAirportList.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				dstAirport = ((JComboBox)e.getSource()).getSelectedItem().toString();
			}
			
		});
		
		datePicker = new JXMonthView();
		datePicker.setTraversable(true);
		datePicker.setSelectionMode(SelectionMode.SINGLE_SELECTION);
		datePicker.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent ee) {				
				loadFlights(((JXMonthView) ee.getSource()).getSelection());
				//System.out.println(((JXMonthView) ee.getSource()).getSelection());
			}			
		});
		panel.setSize(400, 400);
		panel.setLayout(new MigLayout("", "[176px][5px][176px]", "[][27px][][182px][][][]"));
		
		horizontalStrut = Box.createHorizontalStrut(20);
		panel.add(horizontalStrut, "flowx,cell 0 0");
		
		lblArrivingAirport = new JLabel("Arriving Airport");
		panel.add(lblArrivingAirport, "cell 2 0,alignx center");
		
		panel.add(srcAirportList, "cell 0 1,alignx left,aligny top");
		panel.add(dstAirportList, "cell 2 1,alignx left,aligny top");
		
		verticalStrut = Box.createVerticalStrut(20);
		panel.add(verticalStrut, "cell 0 2");
		panel.add(datePicker, "cell 0 3 3 1,alignx center,aligny top");
		getContentPane().add(panel);
		
		lblDepartingAirport = new JLabel("Departing Airport");
		panel.add(lblDepartingAirport, "cell 0 0,alignx center");
		
		button = new Button("Back");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CreateReservation.this.setVisible(false);
				CreateReservation.this.dispose();
				CreateReservation.this.parent.setVisible(true);
			}
		});
		
		verticalStrut_1 = Box.createVerticalStrut(20);
		panel.add(verticalStrut_1, "cell 0 5");
		panel.add(button, "cell 0 6");
		
		this.addWindowListener(this);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);		
	}
	
	/**
	 * Create flights in the current list
	 * @param sortedSet
	 * @return
	 */
	protected JXMonthView loadFlights(SortedSet<Date> sortedSet) {
		// TODO Auto-generated method stub
		//extract date and search database for values using values from above
		
		//Convert from SortedSet<Date> to LocalDate (joda date)
		Object[] temp = new Object [1]; //this only has to be 1?
		temp = sortedSet.toArray();
		flightDate = new LocalDate(temp[0]);
		//System.out.println(flightDate.toString());
		this.setVisible(false);
		new SelectFlight(this);
		//add all entries to entry list.
		return null;
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		parent.setVisible(true);
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
