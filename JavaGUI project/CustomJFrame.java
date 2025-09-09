import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.text.NumberFormatter;



/**
 * Creates a GUI survey and adds the appropriate buttons and labels to the GUI
 */
public class CustomJFrame extends JFrame{
	
	/** The heading label. */
	private JLabel headingLabel = new JLabel("Personal Information");
	
	/** The first name label. */
	private JLabel  firstNameLabel= new JLabel("First Name:");
	
	/** The last name label. */
	private JLabel lastNameLabel= new JLabel("Last Name:");
	
	/** The phone number label. */
	private JLabel phoneNumberLabel= new JLabel("Phone Number:");
	
	/** The email label. */
	private JLabel emailLabel=new JLabel("Email:");
	
	/** The gender label. */
	private JLabel genderLabel= new JLabel("Sex:");
	
	/** The dietary label. */
	private JLabel dietaryLabel= new JLabel("Dietary Questions");
	
	/** The water label. */
	private JLabel waterLabel = new JLabel("How many cups of water on average do you drink a day?");
	
	/** The meals label. */
	private JLabel mealsLabel= new JLabel("How many meals on average do you eat a day?");
	
	/** The check box label. */
	private JLabel checkBoxLabel= new JLabel("Do any of these meals regularly conatain:");
	
	/** The walk label. */
	private JLabel walkLabel= new JLabel("On average how many miles do you walk in a day");
	
	/** The weight label. */
	private JLabel weightLabel= new  JLabel("How much do you weigh?");
	
	/** The first name box. */
	private JTextField firstNameBox= new JTextField();
	
	/** The last name box. */
	private JTextField lastNameBox= new JTextField();
	
	/** The phone number box. */
	private JTextField phoneNumberBox= new JTextField();
	
	/** The email box. */
	private JTextField emailBox= new JTextField();
	
	/** The male radio button. */
	private JRadioButton maleRadioButton= new JRadioButton("Male");
	
	/** The female radio button. */
	private JRadioButton femaleRadioButton= new JRadioButton("Female");
	
	/** The prefer radio button. */
	private JRadioButton preferRadioButton= new JRadioButton("Prefer not to say");
	
	/** The button group. */
	private ButtonGroup buttonGroup= new ButtonGroup();
	
	/** The wheat check box. */
	private JCheckBox wheatCheckBox= new JCheckBox("Wheat");
	
	/** The sugar check box. */
	private JCheckBox sugarCheckBox= new JCheckBox("Sugar");
	
	/** The dairy check box. */
	private JCheckBox dairyCheckBox= new JCheckBox("Dairy");
	
	/** The walk options. */
	private String[] walkOptions= {"Less than 1 Mile","More than 1 mile but less than 2 miles","More than 2 miles but less than 3 miles","More than 3 Miles"};
	
	/** The walk combo box. */
	private JComboBox<String> walkComboBox= new JComboBox<String>(walkOptions);
	
	/** The water intake spinner. */
	private JSpinner waterIntakeSpinner= new JSpinner(new SpinnerNumberModel(15, 0, 50, 1));
	
	/** The meal slider. */
	private JSlider mealSlider = new JSlider(JSlider.HORIZONTAL, 1, 10, 3);
	
	/** The weight formatted text field. */
	private JFormattedTextField weightFormattedTextField= new JFormattedTextField(new NumberFormatter(new DecimalFormat("#0")));
	
	/** The submit button. */
	private JButton submitButton= new JButton("Submit");
	
	/** The clear button. */
	private JButton clearButton= new JButton("Clear");
	
	/** The file handler. */
	private FileHandler fileHandler;
	
	/**
	 * Instantiates a new custom J frame.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public CustomJFrame() throws IOException {
		
		//declares the filehandler,panel, and frame
		try {
            fileHandler = new FileHandler();
        } catch (IOException e1) {
            System.out.println("Error creating file handler");
        }
		   JFrame frame= new JFrame("Dietary Survey");
		   JPanel panel= new JPanel();
			panel.setBorder(BorderFactory.createEmptyBorder(30,30,10,30));
			panel.setLayout(new GridLayout(0,1));
		
	//adds the labels and buttons to the panel
		panel.add(headingLabel);
		panel.add(firstNameLabel);
		panel.add(firstNameBox);
		panel.add(lastNameLabel);
		panel.add(lastNameBox);
		panel.add(phoneNumberLabel);
		panel.add(phoneNumberBox);
		panel.add(emailLabel);
		panel.add(emailBox);
		panel.add(genderLabel);
		buttonGroup.add(femaleRadioButton);
		buttonGroup.add(maleRadioButton);
		buttonGroup.add(preferRadioButton);
		panel.add(femaleRadioButton);
		panel.add(maleRadioButton);
		panel.add(preferRadioButton);
		panel.add(dietaryLabel);
		panel.add(checkBoxLabel);
		panel.add(wheatCheckBox);
		panel.add(sugarCheckBox);
		panel.add(dairyCheckBox);
		panel.add(waterLabel);
		panel.add(waterIntakeSpinner);
		panel.add(mealsLabel);
		panel.add(mealSlider);
		mealSlider.setPaintTicks(true);
		mealSlider.setPaintLabels(true);
		mealSlider.setMajorTickSpacing(1);
		panel.add(walkLabel);
		panel.add(walkComboBox);
		panel.add(weightLabel);
		panel.add(weightFormattedTextField);
		panel.add(submitButton);
		submitButton.setBackground(Color.GREEN);
		panel.add(clearButton);
		clearButton.setBackground(Color.yellow);
		
		//adds actionListener to the submit and clear button
		submitButton.addActionListener(new InnerActionListener());
		clearButton.addActionListener(new InnerActionListener());
		
		//adds panel to the frame, sets size of frame, makes the GUI visible, and exits on close
			frame.add(panel, BorderLayout.CENTER);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setSize(400,1200);
			frame.setVisible(true);
	}
	
	/**
	 * The listener interface for receiving innerAction events.
	 * The class that is interested in processing a innerAction
	 * event implements this interface, and the object created
	 * with that class is registered with a component using the
	 * component's <code>addInnerActionListener<code> method. When
	 * the innerAction event occurs, that object's appropriate
	 * method is invoked.
	 *
	 * @see InnerActionEvent
	 */
	public class InnerActionListener implements ActionListener{
	
	/**
	 * Action performed assesses which button is pressed and either clears data in the GUI or records the
	 * data to a file and then clears the survey GUI
	 *
	 * @param e the actionEvent 
	 */
	public void actionPerformed(ActionEvent e) {
		   
		//gets the button pressed
		JButton button= (JButton)e.getSource();
		if(button.getText().equals("Submit")) {
			//gets the data from the survey
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
			LocalDateTime dateTime = LocalDateTime.now();
			String formatted = dateTime.format(formatter);
			String firstName =firstNameBox.getText();
			String lastName= lastNameBox.getText();
			String phoneNumber= phoneNumberBox.getText();
			String email= emailBox.getText();
			String gender="";
			int water= (int)waterIntakeSpinner.getValue();
			int meals= (int)mealSlider.getValue();
			boolean wheat=wheatCheckBox.isSelected();
			boolean sugar=sugarCheckBox.isSelected();
			boolean dairy=dairyCheckBox.isSelected();
			String miles= (String) walkComboBox.getSelectedItem();
			String weight= weightFormattedTextField.getText();
			if(maleRadioButton.isSelected()) {
				gender="Male";
			}
			else if(femaleRadioButton.isSelected()) {
				gender="Female";
			}
			else if(preferRadioButton.isSelected()) {
				gender="Prefer not to answer";
			}
			//puts information into a String and writes it to file
			String surveyData= formatted+","+firstName+","+lastName+","+phoneNumber+","+email+","+gender+","+water+","+meals+","+wheat+
			","+sugar+","+dairy+","+miles+","+weight;
			try {
				fileHandler.writeResults(surveyData);
			} catch (IOException e1) {
				System.out.println("Input Output exception");
			}
			//clears survey GUI
			clearForm();
		}
		//sees if clear button is pressed and clears GUI if true
		if(button.getText().equals("Clear")) {
			clearForm();
		}
		
	}
	
	/**
	 * Clears the data from the Dietary Survey GUI.
	 */
	public void clearForm() {
		 firstNameBox.setText("");
		    lastNameBox.setText("");
		    phoneNumberBox.setText("");
		    emailBox.setText("");
		    maleRadioButton.setSelected(false);
		    femaleRadioButton.setSelected(false);
		    preferRadioButton.setSelected(false);
		    buttonGroup.clearSelection();
		    wheatCheckBox.setSelected(false);
		    sugarCheckBox.setSelected(false);
		    dairyCheckBox.setSelected(false);
		    walkComboBox.setSelectedIndex(0);
		    waterIntakeSpinner.setValue(15);
		    mealSlider.setValue(3);
		    weightFormattedTextField.setText("");
	}

}
}
