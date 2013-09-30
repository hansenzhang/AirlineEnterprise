package enterprise;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;

import oracle.jdbc.OracleTypes;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class EditCustomer extends JFrame implements WindowListener{

	private static final long serialVersionUID = -8191664974949039879L;
	JFrame parent;
	private JTextField fieldFirstName;
	private JTextField fieldLastName;
	private JTextField fieldMidName;
	private JTextField fieldAddress;
	private JTextField fieldCity;
	private JTextField fieldZipcode;
	private JTextField fieldEmail;
	private JTextField fieldTelephone;
	private JTextField fieldMaidenName;
	private JTextField fieldBirthday;
	private JTextField fieldSocialSecurity;
	private JTextField fieldCCnumber;
	private JTextField fieldCCV;
	private JTextField fieldExpirDate;
	private JTextField fieldUsername;
	private JTextField fieldPassword;
	
	JComboBox titleBox, genderBox, cctypeBox;
	
	Connection conn;
	private String [] genders = {"-", "male", "female"}; // If these aren't lowercase, they don't line up
	private String [] titles ={"-", "Mr.", "Mrs.", "Ms.", "Dr."};
	private String [] cctypes = {"-", "MasterCard", "Visa"};
	private JTextField fieldState;
	
	int custId;
	
	/**
	 * Constructor for Edit customer takes in a parent frame
	 * and creates a new customer form based on existing values
	 */
	EditCustomer(JFrame master, Connection conn, int custId) {
		super("Create New Customer");
		this.conn = conn;
		this.custId = custId;
		getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		ExistingReservation er = new ExistingReservation(custId);
		JLabel lblGender = new JLabel("Gender");
		getContentPane().add(lblGender, "2, 2");		
		
		JLabel lblTitle = new JLabel("Title");
		getContentPane().add(lblTitle, "4, 2");
		
		genderBox = new JComboBox(genders);
		genderBox.setAutoscrolls(true);
		getContentPane().add(genderBox, "2, 4, fill, default");
		genderBox.setSelectedItem(er.gender);
		
		titleBox = new JComboBox(titles);
		getContentPane().add(titleBox, "4, 4");
		titleBox.setSelectedItem(er.title);
		
		JLabel lblMi = new JLabel("M.I.");
		getContentPane().add(lblMi, "6, 4, right, default");
		
		fieldMidName = new JTextField();
		getContentPane().add(fieldMidName, "8, 4, left, default");
		fieldMidName.setColumns(10);
		fieldMidName.setText(er.midname);
		
		JLabel lblFirstName = new JLabel("First Name*");
		getContentPane().add(lblFirstName, "2, 6, right, default");
		
		fieldFirstName = new JTextField();
		getContentPane().add(fieldFirstName, "4, 6, fill, default");
		fieldFirstName.setColumns(10);
		fieldFirstName.setText(er.firstname);
		new HighlightListener(fieldFirstName);
		
		JLabel lblLastName = new JLabel("Last Name*");
		getContentPane().add(lblLastName, "6, 6, right, default");
		
		fieldLastName = new JTextField();
		getContentPane().add(fieldLastName, "8, 6, fill, default");
		fieldLastName.setColumns(10);
		fieldLastName.setText(er.lastname);
		new HighlightListener(fieldLastName);
		
		JLabel lblUserName = new JLabel("User Name*");
		getContentPane().add(lblUserName, "2, 8, right, default");
		
		
		fieldUsername = new JTextField();
		getContentPane().add(fieldUsername, "4, 8, fill, default");
		fieldUsername.setColumns(10);
		fieldUsername.setText(er.username);
		new HighlightListener(fieldUsername);
		
		JLabel lblPassword = new JLabel("Password*");
		getContentPane().add(lblPassword, "6, 8, right, default");
		
		fieldPassword = new JTextField();
		getContentPane().add(fieldPassword, "8, 8, fill, default");
		fieldPassword.setColumns(10);
		fieldPassword.setText(er.password);
		new HighlightListener(fieldPassword);
		
		JLabel lblAddress = new JLabel("Address");
		getContentPane().add(lblAddress, "2, 10, right, default");
		
		fieldAddress = new JTextField();
		getContentPane().add(fieldAddress, "4, 10, fill, default");
		fieldAddress.setColumns(10);
		fieldAddress.setText(er.address);
		
		JLabel lblCity = new JLabel("City");
		getContentPane().add(lblCity, "6, 10, right, default");
		
		fieldCity = new JTextField();
		getContentPane().add(fieldCity, "8, 10, fill, default");
		fieldCity.setColumns(10);
		fieldCity.setText(er.city);
		
		JLabel lblState = new JLabel("State");
		getContentPane().add(lblState, "2, 12, right, default");
		
		fieldState = new JTextField();
		getContentPane().add(fieldState, "4, 12, fill, default");
		fieldState.setColumns(10);
		fieldState.setText(er.state);
		
		JLabel lblZipCode = new JLabel("Zip Code");
		getContentPane().add(lblZipCode, "6, 12, right, default");
		
		fieldZipcode = new JTextField();
		getContentPane().add(fieldZipcode, "8, 12, fill, default");
		fieldZipcode.setColumns(10);
		fieldZipcode.setText(er.zipcode);
		
		JLabel lblEmail = new JLabel("Email");
		getContentPane().add(lblEmail, "2, 14, right, default");
		
		fieldEmail = new JTextField();
		getContentPane().add(fieldEmail, "4, 14, fill, default");
		fieldEmail.setColumns(10);
		fieldEmail.setText(er.email);
		
		JLabel lblTelephone = new JLabel("Telephone");
		getContentPane().add(lblTelephone, "6, 14, right, default");
		
		fieldTelephone = new JTextField();
		getContentPane().add(fieldTelephone, "8, 14, fill, default");
		fieldTelephone.setColumns(10);
		fieldTelephone.setText(er.telephone);
		
		JLabel lblBirthday = new JLabel("Birthday");
		getContentPane().add(lblBirthday, "2, 16, right, default");
		
		fieldBirthday = new JTextField();
		getContentPane().add(fieldBirthday, "4, 16, fill, default");
		fieldBirthday.setColumns(10);
		fieldBirthday.setText(er.birthday);
		
		JLabel lblMaidenName = new JLabel("Maiden Name");
		getContentPane().add(lblMaidenName, "2, 20, right, default");
		
		fieldMaidenName = new JTextField();
		getContentPane().add(fieldMaidenName, "4, 20, fill, default");
		fieldMaidenName.setColumns(10);
		fieldMaidenName.setText(er.maidenname);
		
		JLabel lblSocialSecurity = new JLabel("Social Security");
		getContentPane().add(lblSocialSecurity, "6, 20, right, default");
		
		fieldSocialSecurity = new JTextField();
		getContentPane().add(fieldSocialSecurity, "8, 20, fill, default");
		fieldSocialSecurity.setColumns(10);
		fieldSocialSecurity.setText(er.socialsecurity);
		
		JLabel lblCreditCardType = new JLabel("Credit Card Type");
		getContentPane().add(lblCreditCardType, "2, 24, right, default");
		
		cctypeBox = new JComboBox(cctypes);
		getContentPane().add(cctypeBox, "4, 24, fill, default");
		cctypeBox.setSelectedItem(er.cctype);
		
		JLabel lblCreditCard = new JLabel("Credit Card #");
		getContentPane().add(lblCreditCard, "6, 24, right, default");
		
		fieldCCnumber = new JTextField();
		getContentPane().add(fieldCCnumber, "8, 24, fill, default");
		fieldCCnumber.setColumns(10);
		fieldCCnumber.setText(er.ccnumber);
		
		JLabel lblCcv = new JLabel("CCV");
		getContentPane().add(lblCcv, "2, 26, right, default");
		
		fieldCCV = new JTextField();
		getContentPane().add(fieldCCV, "4, 26, fill, default");
		fieldCCV.setColumns(10);
		fieldCCV.setText(er.ccv);
		
		JLabel lblExpirDate = new JLabel("Expir. Date");
		getContentPane().add(lblExpirDate, "6, 26, right, default");
		
		fieldExpirDate = new JTextField();
		getContentPane().add(fieldExpirDate, "8, 26, fill, default");
		fieldExpirDate.setColumns(10);
		fieldExpirDate.setText(er.expirdate);		
		
		JButton btnSubmit = new JButton("Submit");
		getContentPane().add(btnSubmit, "4, 32");
		
		
		btnSubmit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ee) {
				if (EditCustomer.this.fieldFirstName.getText().trim().length() == 0
					|| EditCustomer.this.fieldLastName.getText().trim().length() == 0
					|| EditCustomer.this.fieldUsername.getText().trim().length() == 0
					|| EditCustomer.this.fieldPassword.getText().trim().length() == 0
						) {
					JOptionPane.showMessageDialog(EditCustomer.this, "Please fill highlighted fields.");
				}
				else {
					updateCustomer();
					JOptionPane.showMessageDialog(EditCustomer.this, "Updated user '" + 
								EditCustomer.this.fieldUsername.getText() + "'");
					EditCustomer.this.setVisible(false);
					
					EditCustomer.this.dispose();
					EditCustomer.this.parent.setVisible(true);
					//
				}
			}
			
		});
		parent = master;
		JButton btnCancel = new JButton("Cancel");
		getContentPane().add(btnCancel, "6, 32");
		btnCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ee) {
				EditCustomer.this.setVisible(false);
				EditCustomer.this.dispose();
				EditCustomer.this.parent.setVisible(true);
			}
			
		});
				 
		this.setSize(new Dimension (500, 500));
		
		this.addWindowListener(this);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	/**
	 * Update customer takes the values from the text fields
	 * and pushes it into the database.
	 */
	void updateCustomer() {
		CallableStatement cs = null;
		try {
			String call = "call update_customer (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			cs = conn.prepareCall(call);			
			ArrayList <String> param = new ArrayList<String>(); 			
			param.add(genderBox.getSelectedItem().toString());
			param.add(titleBox.getSelectedItem().toString());
			param.add(fieldFirstName.getText());			
			param.add(fieldMidName.getText());
			param.add(fieldLastName.getText());
			param.add(fieldAddress.getText());
			param.add(fieldCity.getText());
			param.add(fieldState.getText());
			param.add(fieldZipcode.getText());
			param.add(fieldEmail.getText());			
			param.add(fieldUsername.getText());
			param.add(fieldPassword.getText());
			param.add(fieldTelephone.getText());
			param.add(fieldMaidenName.getText());
			param.add(fieldBirthday.getText());
			param.add(cctypeBox.getSelectedItem().toString());
			param.add(fieldCCnumber.getText());
			param.add(fieldCCV.getText());
			param.add(fieldExpirDate.getText());
			param.add(fieldSocialSecurity.getText() + " ");
			
			cs.setInt(1, custId);
			for (int i = 0; i < 20; i++) {
				cs.setString(i+2, param.get(i));
			}
			cs.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			System.err.println("Exception caught in editcustomer: " + e.getMessage());
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "User, " + fieldUsername.getText() +" already exists.");
		}  finally {
			
		}
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
	
	class HighlightListener implements DocumentListener {  
		JTextComponent comp = null; 
		Border defaultBorder = null; 
		Border highlightBorder = BorderFactory.createLineBorder(java.awt.Color.ORANGE);   
		public HighlightListener(JTextComponent jtc) { 
			comp = jtc; defaultBorder = comp.getBorder(); 
			// Adding this listener to a specified component: 
			comp.getDocument().addDocumentListener(this); 
			// Highlight if empty: 
			this.maybeHighlight(); 
		}   
		public void insertUpdate(DocumentEvent e) {
			maybeHighlight(); 
		}   
		public void removeUpdate(DocumentEvent e) {
			maybeHighlight(); 
		}   
		public void changedUpdate(DocumentEvent e) {
			maybeHighlight(); 
		}  
		private void maybeHighlight() { 
			if (comp.getText().trim().length() > 0)
				comp.setBorder(defaultBorder); 
			else 
				comp.setBorder(highlightBorder); // ... more actions
		}
	}
	
	/**
	 * Class Existing Reservation holds the basis for holding
	 * reservation fields. 
	 * @author hansen
	 *
	 */
	class ExistingReservation {
		String gender, title, firstname, midname, lastname, address, city, state, zipcode,
			email, username, password, telephone, maidenname, birthday, cctype, ccnumber,
			ccv, expirdate, socialsecurity;
		int userID;
		ExistingReservation (int userID) {
			this.userID = userID;
			findUserFields();
		}
		void findUserFields(){
			CallableStatement cs = null;
			ResultSet rs = null;
			try {
				String call = "{call getUserInfo(?,?)}";
				cs = conn.prepareCall(call);
				cs.setInt(1, userID);
				cs.registerOutParameter(2, OracleTypes.CURSOR);
				cs.executeUpdate();
				rs = (ResultSet) cs.getObject(2);
				while (rs.next()) {
					gender = rs.getString(1);
					title = rs.getString(2);
					firstname = rs.getString(3);
					midname = rs.getString(4);		
					lastname = rs.getString(5);
					address = rs.getString(6);
					city = rs.getString(7);
					state = rs.getString(8);
					zipcode = rs.getString(9);
					email = rs.getString(10);
					username = rs.getString(11);
					password = rs.getString(12);
					telephone = rs.getString(13);
					maidenname = rs.getString(14);
					birthday = rs.getString(15);
					cctype = rs.getString(16);
					ccnumber = rs.getString(17);
					ccv = rs.getString(18);
					expirdate = rs.getString(19);
					socialsecurity = rs.getString(20);
				}
				
			} catch (SQLException e) {
				System.err.println("Caught exception in: " + e.getMessage());
			} 
		}
	}
}
