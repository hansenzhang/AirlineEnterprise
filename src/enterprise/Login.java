package enterprise;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import oracle.jdbc.OracleTypes;

class Login extends JFrame implements ActionListener, WindowListener {

		private static final long serialVersionUID = 1L;
		final JButton submitBtn, submitBtnDefault;
		final JPanel panel;
		final JLabel usernameLabel, passwordLabel;
		final JTextField  usernameField;
		final JPasswordField passwordField;
		
		Enterprise en;
		int userId;
		String userName;
		
		final JFrame parent;
		
		/**
		 * Constructor Login takes a login and creates a new customer instance.
		 * @param master
		 */
		Login(JFrame master) {
			super("Login");
			en = (Enterprise) master; 
			parent = master;
			usernameLabel = new JLabel("Username:");
			usernameField = new JTextField(15);
			usernameField.setText("Reyer1964"); // default user
			
			passwordLabel = new JLabel("Password:");
			passwordField = new JPasswordField(15);
			passwordField.setText("aN9iemeesh");//default password

			submitBtn = new JButton("Submit");
			submitBtn.addActionListener(this);
			
			submitBtnDefault = new JButton("Create New User");
			submitBtnDefault.addActionListener(this);

			panel = new JPanel();
			panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

			//panel.add(new JLabel(""));
			getContentPane().add(panel,BorderLayout.CENTER);
			GroupLayout gl_panel = new GroupLayout(panel);
			gl_panel.setHorizontalGroup(
				gl_panel.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_panel.createSequentialGroup()
						.addComponent(usernameLabel, GroupLayout.PREFERRED_SIZE, 190, GroupLayout.PREFERRED_SIZE)
						.addComponent(usernameField, GroupLayout.PREFERRED_SIZE, 190, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_panel.createSequentialGroup()
						.addComponent(passwordLabel, GroupLayout.PREFERRED_SIZE, 190, GroupLayout.PREFERRED_SIZE)
						.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, 190, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_panel.createSequentialGroup()
						.addComponent(submitBtnDefault, GroupLayout.PREFERRED_SIZE, 190, GroupLayout.PREFERRED_SIZE)
						.addComponent(submitBtn, GroupLayout.PREFERRED_SIZE, 190, GroupLayout.PREFERRED_SIZE))
			);
			gl_panel.setVerticalGroup(
				gl_panel.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_panel.createSequentialGroup()
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
							.addComponent(usernameLabel, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
							.addComponent(usernameField, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
							.addComponent(passwordLabel, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
							.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
							.addComponent(submitBtnDefault, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
							.addComponent(submitBtn, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)))
			);
			panel.setLayout(gl_panel);
			
			setTitle("Login");
			setSize(400,150);
			this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			this.setLocationRelativeTo(null);
			this.addWindowListener(this);
			this.setVisible(true);
		}
		
		/**
		 * function userlogin checks to see if the login is valid for 
		 * customers in the database
		 * @param username
		 * @param password
		 * @return
		 */
		boolean userLogin (String username, String password){
			CallableStatement cs = null;
			ResultSet rs = null;
			try {
				String call = "{call customer_login(?,?,?)}";
				cs = en.conn.prepareCall(call);
				
				cs.setString(1, username);
				cs.setString(2, password);
				cs.registerOutParameter(3, OracleTypes.CURSOR);
				
				cs.executeUpdate();
				rs = (ResultSet) cs.getObject(3);
				rs.next();
				userId = rs.getInt(1);
				userName = rs.getString(2);
				return true;
			} catch (SQLException e) {
				System.err.println("Exception caught: " + e.getMessage()); 
			}
			return false;
		}
		
		@Override
		public void actionPerformed(ActionEvent ee)
		{
			if (ee.getSource() == submitBtnDefault){				
				this.setVisible(false);				
				
				this.dispose();				
				new CreateCustomer(this, en.conn);
				return;
			}
			//if a connection can be opened, do everything! else, just print an incorrect dialogue
			else if(userLogin(usernameField.getText(), new String(passwordField.getPassword() ))) {				
					this.setVisible(false);			
					
					this.dispose();
					new Customer(en, this);
					return;
				
			} 
			// send an option pane that does not match
			JOptionPane.showMessageDialog(this, "Sorry wrong Username/Password\n Try again");

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
			this.setVisible(false);
			this.dispose();
			parent.setVisible(true);
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