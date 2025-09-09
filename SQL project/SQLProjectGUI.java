//in this Java file, the only functions you need to complete are the following:
//RegisterNewUser()
//LoginWithCreds()
//GetAllProducts()
//GetSalesTotal()
//SubmitOrder()
//SubmitNewProduct()

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

import javax.swing.*;

@SuppressWarnings({ "rawtypes", "unchecked", "serial" })
public class FinalProjectGUI extends JFrame {

	// global variables used by many functions
	GridBagConstraints gbc;
	CustomActionListener cal;
	ComboBoxActionListener cbal;
	String connection = "jdbc:mysql://localhost:3306/finalguiproject?";
	String user = "guest";
	String pass = "guest";

	// constructor
	public FinalProjectGUI() {

		setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);
		cal = new CustomActionListener();
		cbal = new ComboBoxActionListener();
		DisplaySplashScreen();
	}

	// this function removes all of the controls from the content pane;
	// this should not be called except by the pre-defined functions!!
	private void ClearScreen() {
		this.getContentPane().removeAll();
	}

	// this screen is the first screen the user sees!
	JLabel welcomeMessage;
	JButton registerButton;
	JButton loginButton;

	private void DisplaySplashScreen() {
		ClearScreen();

		welcomeMessage = new JLabel("Welcome to the Shoe Store application!  What function do you want to do?");
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		this.add(welcomeMessage, gbc);

		registerButton = new JButton("Register New User");
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 1;
		registerButton.addActionListener(cal);
		registerButton.setName("register");
		this.add(registerButton, gbc);

		loginButton = new JButton("Login with Existing Credentials");
		gbc.gridx = 1;
		loginButton.addActionListener(cal);
		loginButton.setName("login");
		this.add(loginButton, gbc);

		this.validate();
		this.pack();
		this.repaint();
	}

	// this screen lets a user sign up for an account!
	JLabel registerMessage;
	JLabel usernameLabel;
	JLabel passwordLabel;
	JTextField usernameField;
	JTextField passwordField;
	JButton submitButton;
	JTextArea errorMessageArea;

	private void DisplayRegisterScreen() {
		ClearScreen();

		registerMessage = new JLabel("Please enter a new username and password below.");
		gbc.gridx = 0;
		gbc.gridy = 0;
		add(registerMessage, gbc);

		usernameLabel = new JLabel("User Name:");
		gbc.gridy++;
		add(usernameLabel, gbc);

		usernameField = new JTextField(20);
		gbc.gridy++;
		add(usernameField, gbc);

		passwordLabel = new JLabel("Password:");
		gbc.gridy++;
		add(passwordLabel, gbc);

		passwordField = new JTextField(20);
		gbc.gridy++;
		add(passwordField, gbc);

		submitButton = new JButton("Submit");
		submitButton.setName("newUserRegister");
		submitButton.addActionListener(cal);
		gbc.gridy++;
		add(submitButton, gbc);

		errorMessageArea = new JTextArea(10, 50);
		gbc.gridy++;
		add(errorMessageArea, gbc);

		// all 3 of these are necessary to display the new form!
		this.validate();
		this.pack();
		this.repaint();
	}

	// this function re-uses some of the controls from the Register User screen.
	// this lets any user signin to their account
	/*
	 * JLabel usernameLabel; JLabel passwordLabel; JTextField usernameField;
	 * JTextField passwordField; JButton submitButton; JTextArea errorMessageArea;
	 */
	JLabel loginLabel;

	private void DisplayLoginScreen() {
		ClearScreen();

		loginLabel = new JLabel("Please enter your credentials below.");
		gbc.gridx = 0;
		gbc.gridy = 0;
		add(loginLabel, gbc);

		usernameLabel = new JLabel("User Name:");
		gbc.gridy++;
		add(usernameLabel, gbc);

		usernameField = new JTextField(20);
		gbc.gridy++;
		add(usernameField, gbc);

		passwordLabel = new JLabel("Password:");
		gbc.gridy++;
		add(passwordLabel, gbc);

		passwordField = new JTextField(20);
		gbc.gridy++;
		add(passwordField, gbc);

		submitButton = new JButton("Submit");
		submitButton.setName("loginWithCreds");
		submitButton.addActionListener(cal);
		gbc.gridy++;
		add(submitButton, gbc);

		errorMessageArea = new JTextArea(10, 50);
		gbc.gridy++;
		add(errorMessageArea, gbc);

		this.validate();
		this.pack();
		this.repaint();
	}

	// this screen is shown once a user successfully logs in.
	// if the user has the Role of admin (denoted by the value 1 in the table
	// "Users"),
	// then they can see buttons to manage inventory and view a sales report.
	// otherwise, they can see a button to place an order.
	// finally, all users will be able to see a Logout button.
	JLabel selectFormLabel;
	JButton orderFormButton;
	JButton inventoryFormButton;
	JButton salesReportButton;
	JButton logoutButton;

	private void DisplayFormSelectScreen() {
		ClearScreen();

		selectFormLabel = new JLabel("Welcome, " + loggedInUserName + ", please select a form below.");
		gbc.gridx = 0;
		gbc.gridy = 0;
		add(selectFormLabel, gbc);

		if (loggedInUserRole == 2) {
			orderFormButton = new JButton("Place an Order");
			orderFormButton.setName("placeOrder");
			orderFormButton.addActionListener(cal);
			gbc.gridy++;
			add(orderFormButton, gbc);
		} else if (loggedInUserRole == 1) {
			inventoryFormButton = new JButton("Manage Inventory");
			inventoryFormButton.setName("inventory");
			inventoryFormButton.addActionListener(cal);
			gbc.gridy++;
			add(inventoryFormButton, gbc);

			salesReportButton = new JButton("See Sales Report");
			salesReportButton.setName("salesReport");
			salesReportButton.addActionListener(cal);
			gbc.gridy++;
			add(salesReportButton, gbc);
		}

		logoutButton = new JButton("Log Out");
		logoutButton.setName("logout");
		logoutButton.addActionListener(cal);
		gbc.gridy++;
		add(logoutButton, gbc);

		this.validate();
		this.pack();
		this.repaint();
	}

	// this screen allows a non-admin user (i.e. has a Role of "2" in the Users
	// table)
	// to order a new item.
	// This screen's comboBox will be populated with data from the database!
	JLabel orderLabel;
	JLabel priceLabel;
	JComboBox productListComboBox;
	JTextField productQtyField;
	JButton placeOrderButton;
	JButton cancelButton;

	private void DisplayOrderScreen() {
		productNames = new ArrayList<String>();
		productPrices = new ArrayList<Double>();
		productIDs = new ArrayList<Integer>();

		GetAllProducts();

		ClearScreen();

		orderLabel = new JLabel("Please select the item you want to order and specify the quantity.");
		gbc.gridx = 0;
		gbc.gridy = 0;
		add(orderLabel, gbc);

		productListComboBox = new JComboBox(productNames.toArray());
		gbc.gridy++;
		productListComboBox.addActionListener(cbal);
		add(productListComboBox, gbc);

		// this might throw an error if there are no items in the arraylist.
		// that means you need to complete the "GetAllProducts()" function first :)
		priceLabel = new JLabel("Price: " + productPrices.get(0).toString());
		gbc.gridy++;
		add(priceLabel, gbc);

		productQtyField = new JTextField(5);
		gbc.gridy++;
		add(productQtyField, gbc);

		placeOrderButton = new JButton("Place Order");
		placeOrderButton.setName("submitOrder");
		placeOrderButton.addActionListener(cal);
		gbc.gridy++;
		add(placeOrderButton, gbc);

		cancelButton = new JButton("Go Back");
		cancelButton.setName("return");
		cancelButton.addActionListener(cal);
		gbc.gridy++;
		add(cancelButton, gbc);

		this.validate();
		this.pack();
		this.repaint();
	}

	// this screen allows an admin user (i.e. with role "1" in the Users table)
	// to add new products to the Product table
	JLabel productNameLabel;
	JTextField productNameField;
	JLabel productPriceLabel;
	JTextField productPriceField;
	// JButton submitButton;
	JButton returnButton;

	private void DisplayInventoryScreen() {
		ClearScreen();

		productNameLabel = new JLabel("Product Name:");
		gbc.gridx = 0;
		gbc.gridy = 0;
		add(productNameLabel, gbc);

		productNameField = new JTextField(25);
		gbc.gridy++;
		add(productNameField, gbc);

		productPriceLabel = new JLabel("Price:");
		gbc.gridy++;
		add(productPriceLabel, gbc);

		productPriceField = new JTextField(10);
		gbc.gridy++;
		add(productPriceField, gbc);

		submitButton = new JButton("Add New Product");
		submitButton.setName("submitNewProduct");
		submitButton.addActionListener(cal);
		gbc.gridy++;
		add(submitButton, gbc);

		returnButton = new JButton("Go Back");
		returnButton.setName("return");
		returnButton.addActionListener(cal);
		gbc.gridy++;
		add(returnButton, gbc);

		this.validate();
		this.pack();
		this.repaint();
	}

	// this screen allows an admin user (i.e. with role "1" in the Users table)
	// to see a simple report of all sales logged in the database
	JLabel totalSalesLabel;

	// JButton returnButton;
	private void DisplaySalesReportScreen() {
		ClearScreen();

		GetSalesTotal();

		gbc.gridx = 0;
		gbc.gridy = 0;
		totalSalesLabel = new JLabel("Total Sales: " + salesTotal);
		add(totalSalesLabel, gbc);

		returnButton = new JButton("Return");
		returnButton.setName("return");
		gbc.gridy++;
		returnButton.addActionListener(cal);
		add(returnButton, gbc);

		this.validate();
		this.pack();
		this.repaint();
	}

	// custom action listener for when a combobox changes values.
	// useful for displaying the price of the selected item.
	class ComboBoxActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JComboBox theBox = (JComboBox) e.getSource();
			int selectedIndex = theBox.getSelectedIndex();

			priceLabel.setText("Price: " + productPrices.get(selectedIndex).toString());
		}

	}

	// custom action listener for all buttons in the program. dang!
	class CustomActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			JButton button = (JButton) e.getSource();
			String name = button.getName();

			// this button is in the splash screen
			if (name.equals("login"))
				DisplayLoginScreen();

			// this button is in the splash screen
			else if (name.equals("register"))
				DisplayRegisterScreen();

			// this button is in the Register New User screen
			else if (name.equals("newUserRegister"))
				RegisterNewUser();

			// this button is in the Login screen
			else if (name.equals("loginWithCreds"))
				LoginWithCreds();

			// this button is in the Form Select screen
			else if (name.equals("placeOrder"))
				DisplayOrderScreen();

			// this button is in the Form Select screen
			else if (name.equals("inventory"))
				DisplayInventoryScreen();

			// this button is in the Form Select screen
			else if (name.equals("salesReport"))
				DisplaySalesReportScreen();

			// this button is found on the Form Select screen
			else if (name.equals("logout"))
				DisplaySplashScreen();

			// this button is in the Order form for customers
			else if (name.equals("submitOrder"))
				SubmitOrder();

			// this button is found on both the Order form and the Inventory form
			else if (name.equals("return"))
				DisplayFormSelectScreen();

			// this button is found on the Inventory screen
			else if (name.equals("submitNewProduct"))
				SubmitNewProduct();
		}

	}

	/////////////////////////////////////////
	/////////////////////////////////////////
	// Begin completing the functions below!//
	// Everything else, above, do not touch!//
	/////////////////////////////////////////
	/////////////////////////////////////////

	// this function calls a stored procedure to insert a new user into the "User"
	// table
	// Any error from the database (e.g. duplicate username) should appear in the
	// "errorMessageArea" JTextArea.
	// On a success, the function "DisplaySplashScreen()" should be called.
	private void RegisterNewUser() {
		try (Connection con = DriverManager.getConnection(connection, user, pass)) {
			String createUser = "{call RegisterNewUser(?, ?, ?)}";
			try (CallableStatement statement = con.prepareCall(createUser)) {
				statement.setString(1, usernameField.getText());
				statement.setString(2, passwordField.getText());
				statement.setInt(3, 2);

				statement.execute();
				DisplaySplashScreen();
			}
		} catch (SQLException e) {
			errorMessageArea.setText("Error: " + e.getMessage());
		}
	}

	// this function calls a stored procedure to determine if there exists
	// a username / value pair in the database that matches the user's input.
	// If there is, the below variables should be populated, then call the
	// DisplayFormSelectScreen() function.
	// If there is not, then the "errorMessageArea" (JTextArea) should display an
	// 'error' message
	int loggedInUserID;
	int loggedInUserRole;
	String loggedInUserName;

	private void LoginWithCreds() {
		try (Connection con = DriverManager.getConnection(connection, user, pass)) {
			String loginWithCreds = "{call LoginWithCreds(?, ?)}";
			try (CallableStatement statement = con.prepareCall(loginWithCreds)) {
				statement.setString(1, usernameField.getText());
				statement.setString(2, passwordField.getText());

				try (ResultSet resultSet = statement.executeQuery()) {
					if (resultSet.next()) {
						loggedInUserID = resultSet.getInt("id");
						loggedInUserName = usernameField.getText();
						loggedInUserRole = resultSet.getInt("userRole");

						DisplayFormSelectScreen();
					} else {
						errorMessageArea.setText("Invalid username or password");
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			errorMessageArea.setText("Error: " + e.getMessage());
		}
	}

	// this function calls a stored procedure to get a recordset of all products
	ArrayList<String> productNames;
	ArrayList<Double> productPrices;
	ArrayList<Integer> productIDs;

	private void GetAllProducts() {
		productNames = new ArrayList<>();
		productPrices = new ArrayList<>();
		productIDs = new ArrayList<>();

		try (Connection con = DriverManager.getConnection(connection, user, pass)) {
			String getAllProducts = "{call GetAllProducts()}";
			try (CallableStatement statement = con.prepareCall(getAllProducts)) {
				ResultSet resultSet = statement.executeQuery();

				while (resultSet.next()) {
					productIDs.add(resultSet.getInt("id"));
					productNames.add(resultSet.getString("prodName"));
					productPrices.add(resultSet.getDouble("price"));
				}
			}
		} catch (SQLException e) {
			errorMessageArea.setText("Error: " + e.getMessage());
		}
	}

	// this function calls a stored procedure to determine the Sum of all sales
	double salesTotal = -1;

	private void GetSalesTotal() {
		salesTotal = -1;

		try (Connection con = DriverManager.getConnection(connection, user, pass)) {
			String getSalesTotal = "{call GetSalesTotal()}";
			try (CallableStatement statement = con.prepareCall(getSalesTotal)) {
				ResultSet resultSet = statement.executeQuery();

				while (resultSet.next()) {
					salesTotal = resultSet.getDouble(1);

				}
			}
		} catch (SQLException e) {
			errorMessageArea.setText("Error: " + e.getMessage());
		}
	}

	// this function calls a stored procedure to add a new Sale to the database
	private void SubmitOrder() {
		try (Connection con = DriverManager.getConnection(connection, user, pass)) {
			String submitOrder = "{call SubmitOrder(?, ?, ?, ?)}";
			try (CallableStatement statement = con.prepareCall(submitOrder)) {
				statement.setInt(1, productIDs.get(productListComboBox.getSelectedIndex()));
				statement.setInt(2, loggedInUserID);
				statement.setInt(3, Integer.parseInt(productQtyField.getText()));
				statement.setDouble(4, productPrices.get(productListComboBox.getSelectedIndex())*Integer.parseInt(productQtyField.getText()));

				statement.execute();

				DisplayFormSelectScreen();
			}
		} catch (SQLException e) {
			errorMessageArea.setText("Error: " + e.getMessage());
		}
	}

	// this function calls a stored procedure to add a new product to the database
	private void SubmitNewProduct() {
		try (Connection con = DriverManager.getConnection(connection, user, pass)) {
			String submitNewProduct = "{call SubmitNewProduct(?, ?)}";
			try (CallableStatement statement = con.prepareCall(submitNewProduct)) {
				statement.setString(1, productNameField.getText());
				statement.setDouble(2, Double.parseDouble(productPriceField.getText()));

				statement.execute();
				DisplayFormSelectScreen();
			}
		} catch (SQLException e) {
			errorMessageArea.setText("Error: " + e.getMessage());
		}
	}

}