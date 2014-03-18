import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import net.miginfocom.swing.MigLayout;


public class View {

	//Create the frame for use
	private JFrame frame;

	//Set up the tabs
	private JTabbedPane tabbedPane;
	private JPanel panelPatient; 
	private JPanel panelHistory;
	private JPanel panelInvoice;
	private JPanel panelProcedures;
	private JPanel panelReports;

	//Set up the JList
	private JList<String> nameList;

	//Set up the textFields
	private JTextField patientNameTf;
	private JTextField patientAddressTf;
	private JTextField paitentPhoneNumTf;	
	private JTextField historyPatientNameTf;
	private JTextField searchField;
	private JTextField invoicePatientNameTf;
	private JTextField invoicePatientBalanceTf;
		
	//Set up the labels
	private JLabel patientNameLabel;
	private JLabel patientAddressLabel;
	private JLabel phoneNumLabel;
	private JLabel historyPatientNameLabel;
	private JLabel invoicePatientNameLabel;
	private JLabel invoiceBalanceLabel;
	private JLabel mainName;
	private JLabel searchLabel;
			
	//Set up JButtons
	private JButton patientSave;
	private JButton patientDelete;
	private JButton addPatient;
	private JButton addInvoice;
	private JButton addHistory;
	private JButton invoicePay;
	private JButton procedureAdd;
	private JButton procedureDelete;
	private JButton invoiceDelete;
	private JButton historyDelete;
	private JButton addPatientProcedure;
	private JButton deletePatientProcedure; 
	//Set up the right panel
	private JPanel panelRight;

	//Create a JTable
	private JTable historyTable;
	private JTable invoiceTable;
	private JTable proceduresTable;
	private JTable patientProcedures;
	
	//Reports panel stuff
	private JLabel reportOne;
	private JLabel reportTwo;
	private JLabel reportThree;
	private JLabel reportFour;
	private JTextArea reportOneTa;
	private JTextArea reportTwoTa;
	private JTextArea reportThreeTa;
	private JTextArea reportFourTa;
	private JButton doReportOne;
	private JButton doReportTwo;
	private JButton doReportThree;
	private JButton doReportFour;
	private JLabel reportOneDec;
	private JLabel reportTwoDec;
	private JLabel reportThreeDec;
	private JLabel reportFourDec;
	
	//Adding a menu bar
	private JMenuBar menu;
	private JMenu serialised;
	private JMenuItem save;
	private JMenuItem load;
	private JMenu database;
	private JMenuItem dbSave;
	private JMenuItem dbLoad;
	
	public View()
	{
		try 
		{
	        // Set System L&F
			UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
	    } 
		catch (Exception e) {
			
		}
		
		
		
		//Create everything
		frame       = new JFrame();
		tabbedPane  = new JTabbedPane();
		panelRight  = new JPanel(new MigLayout());
		nameList    = new JList();
		mainName    = new JLabel("Names:");
		searchField = new JTextField();
		searchLabel = new JLabel("Search:");
		menu        = new JMenuBar();
		serialised  = new JMenu("Serialised");
		database	= new JMenu("Database");
		
		//Set the menu
		frame.setJMenuBar(menu);
		menu.add(serialised);
		menu.add(database);
		save 	= new JMenuItem("Save");
		load 	= new JMenuItem("Load");
		dbSave 	= new JMenuItem("Save");
		dbLoad 	= new JMenuItem("Load");
		serialised.add(save);
		serialised.add(load);
		database.add(dbSave);
		database.add(dbLoad);
		
		//Set up the tool tips
		searchField.setToolTipText("Search"); //If the user mouses over the search field they will get this message

		//Create the tabbed panels
		createPatientTabPanel();
		createHistoryTabPanel();
		createInvoiceTabPanel();
		createProceduresTabPanel();
		createReportsPanel();

		//Add the tabs to the tabbed Pane
		tabbedPane.addTab( "Patient", panelPatient );
		tabbedPane.addTab( "History", panelHistory );
		tabbedPane.addTab( "Invoices", panelInvoice );
		tabbedPane.addTab( "Procedures", panelProcedures );
		tabbedPane.addTab( "Reports", panelReports );

		//Set up the search field
		searchField.setPreferredSize(new Dimension(153, 20) ); //Makes the search bar the same size as the JList

		//Make a scrollPane so I can add a column header as well as scroll the names as well as set up the right panel
		JScrollPane scrollPane = new JScrollPane(nameList);
		panelRight.add(scrollPane, "wrap");
		scrollPane.setColumnHeaderView(mainName);
		scrollPane.setPreferredSize(new Dimension(200,200) ); //Set the size of the name List

		//Set up the right panel
		panelRight.add(searchLabel);
		panelRight.add(searchField, "cell 0 1");

		//Add the tabs to the frame
		frame.getContentPane().add(tabbedPane);
		frame.getContentPane().add(BorderLayout.EAST, panelRight);
		
		//Set the frame size and for it to be visible
		frame.setTitle("Office application");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.pack();
	}

	
	
	/**
	 * Set up the patient panel tab
	 */
	public void createPatientTabPanel()
	{
		//Create the panel stuff for the patient tab
		patientDelete            = new JButton("Delete");
		patientSave              = new JButton("Apply Changes");
		addPatient               = new JButton("Add");
		panelPatient             = new JPanel(new MigLayout() );
		patientNameLabel         = new JLabel("Name: ");
		patientAddressLabel      = new JLabel("Address: ");
		phoneNumLabel            = new JLabel("Phone number: ");
		patientNameTf            = new JTextField();
		patientAddressTf         = new JTextField();
		paitentPhoneNumTf        = new JTextField();
		
		//Make the buttons as unusable until something has been selected
		patientDelete.setEnabled(false);

		//Set the size of the textField
		patientNameTf.setPreferredSize(new Dimension(200, 20));
		
		//Disable the fields at the beginning
		patientNameTf.setEditable(false);
		patientAddressTf.setEditable(false);
		paitentPhoneNumTf.setEditable(false);

		//Add the components to the panels 
		panelPatient.add(patientNameLabel);
		panelPatient.add(patientNameTf, "wrap");
		panelPatient.add(patientAddressLabel);
		panelPatient.add(patientAddressTf, "grow, wrap");
		panelPatient.add(phoneNumLabel);
		panelPatient.add(paitentPhoneNumTf, "grow, wrap");


		//Set up the south Panel
		panelPatient.add(patientDelete);
		panelPatient.add(addPatient, "align right, cell 1 3");
		panelPatient.add(patientSave, "cell 1 3");

	}


	/**
	 * Create the history tab
	 */
	public void createHistoryTabPanel()
	{
		//Create the panel stuff for the history tab
		panelHistory               = new JPanel(new MigLayout());
		historyPatientNameLabel    = new JLabel("Name: ");
		historyPatientNameTf       = new JTextField();
		addHistory                 = new JButton("Add history");
		historyTable               = new JTable();
		historyDelete              = new JButton("Delete");
		
		//Give the history Table a scroll area
		JScrollPane histScroll = new JScrollPane(historyTable);
						
		//Set the size of the textField
		historyPatientNameTf.setPreferredSize(new Dimension(200, 25) );
		historyPatientNameTf.setEditable(false);
		addHistory.setEnabled(false);
		historyDelete.setEnabled(false);
		
		//Add the labels and field to the panel
		panelHistory.add(historyPatientNameLabel, "cell 0 0");
		panelHistory.add(historyPatientNameTf, "cell 0 0, wrap");
		panelHistory.add(histScroll, "cell 0 1, wrap");
		panelHistory.add(addHistory, "cell 0 2, split 2");
		panelHistory.add(historyDelete,"gap left 285, cell 0 2");
		
	}

	/**
	 * Create the invoice tab
	 */
	public void createInvoiceTabPanel()
	{
		//Create the panel stuff for the invoice tab
		panelInvoice                 = new JPanel(new MigLayout());
		invoiceBalanceLabel          = new JLabel("Balance: ");
		invoicePatientNameLabel      = new JLabel("Name: ");
		invoicePatientNameTf         = new JTextField();
		invoicePatientBalanceTf      = new JTextField();
		addInvoice                   = new JButton("Add invoice");
		invoiceTable                 = new JTable();
		invoicePay                   = new JButton("Mark as paid");
		invoiceDelete                = new JButton("Delete invoice");
		patientProcedures            = new JTable();
		addPatientProcedure          = new JButton("Add procedure");
		deletePatientProcedure       = new JButton("Delete procedure");

		//Set up the JTable
		JScrollPane scroll    = new JScrollPane(invoiceTable);
		JScrollPane proScroll = new JScrollPane(patientProcedures);

		// Set the nameField and balance as un-editable
		invoicePatientNameTf.setEditable(false);
		invoicePatientBalanceTf.setEditable(false);
				
		//Set the buttons as un-enabled
		invoicePay.setEnabled(false);
		addPatientProcedure.setEnabled(false);  
		deletePatientProcedure.setEnabled(false);
		invoiceDelete.setEnabled(false);
		addInvoice.setEnabled(false);
		addPatientProcedure.setEnabled(false);
		deletePatientProcedure.setEnabled(false);

		//Set up the size of the text fields
		invoicePatientNameTf.setPreferredSize(new Dimension(200, 20) );

		//Set up the main panel
		panelInvoice.add(invoicePatientNameLabel);
		panelInvoice.add(invoicePatientNameTf, "cell 0 0, grow, wrap");
		panelInvoice.add(invoiceBalanceLabel);
		panelInvoice.add(invoicePatientBalanceTf, "cell 0 1, grow, wrap");
		panelInvoice.add(scroll);
		panelInvoice.add(proScroll, "wrap");
		panelInvoice.add(invoiceDelete, "split 3");
		panelInvoice.add(addInvoice, "gap 110");
		panelInvoice.add(invoicePay, "alignx 100%");
		panelInvoice.add(addPatientProcedure, "split 2, gapright 200");
		panelInvoice.add(deletePatientProcedure);
		
	}

	/**
	 * 
	 */
	public void createProceduresTabPanel()
	{
		//Create the panel stuff for the procedures tab
		panelProcedures       = new JPanel(new MigLayout());
		proceduresTable       = new JTable();
		procedureAdd          = new JButton("Add procedure");
		procedureDelete       = new JButton("Delete");
		
		procedureDelete.setEnabled(false);
		
		//Create the JTable scroll
		JScrollPane scroll = new JScrollPane(proceduresTable);
				
		//Add the labels and field to the panel
		panelProcedures.add(scroll, "cell 2 1, wrap");
		panelProcedures.add(procedureAdd, "cell 2 2, split 2, gapright 265");
		panelProcedures.add(procedureDelete);

	}
	
	/**
	 * Create the reports panel
	 */
	public void createReportsPanel()
	{
		panelReports   = new JPanel(new MigLayout());
		reportOne      = new JLabel("Report one");
		reportTwo      = new JLabel("Report two");
		reportThree    = new JLabel("Report three");
		reportFour     = new JLabel("Report four");
		reportOneTa    = new JTextArea();
		reportTwoTa    = new JTextArea();
		reportThreeTa  = new JTextArea();
		reportFourTa   = new JTextArea();
		doReportOne    = new JButton("Generate report one");
		reportOneDec   = new JLabel("All treaments in a certain time");
		doReportTwo    = new JButton("Generate report two");
		doReportThree  = new JButton("Generate report three");
		doReportFour   = new JButton("Generate report four");
		reportTwoDec   = new JLabel("Out standing invoices");
		reportThreeDec = new JLabel("No out standing invoices");
		reportFourDec  = new JLabel("Specific procedure in time frame");
		
		//Add scroll panes
		JScrollPane reportOneScroll    = new JScrollPane(reportOneTa);
		JScrollPane reportTwoScroll    = new JScrollPane(reportTwoTa);
		JScrollPane reportThreeScroll  = new JScrollPane(reportThreeTa);
		JScrollPane reportFourScroll   = new JScrollPane(reportFourTa);
		
		//Set the size
		reportOneScroll.setPreferredSize(new Dimension(200,400));
		reportTwoScroll.setPreferredSize(new Dimension(200,400));
		reportThreeScroll.setPreferredSize(new Dimension(200,400));
		reportFourScroll.setPreferredSize(new Dimension(200,400));
		
		//Set column headers
		reportOneScroll.setColumnHeaderView(reportOneDec);
		reportTwoScroll.setColumnHeaderView(reportTwoDec);
		reportThreeScroll.setColumnHeaderView(reportThreeDec);
		reportFourScroll.setColumnHeaderView(reportFourDec);
		
		//Stop the user editing them
		reportOneTa.setEditable(false);
		reportTwoTa.setEditable(false);
		reportThreeTa.setEditable(false);
		reportFourTa.setEditable(false);
		
		//Add to the panel
		panelReports.add(reportOne);
		panelReports.add(reportTwo);
		panelReports.add(reportThree);
		panelReports.add(reportFour, "wrap");
		panelReports.add(reportOneScroll);
		panelReports.add(reportTwoScroll);
		panelReports.add(reportThreeScroll);
		panelReports.add(reportFourScroll, "wrap");
		panelReports.add(doReportOne);
		panelReports.add(doReportTwo);
		panelReports.add(doReportThree);
		panelReports.add(doReportFour);
		
	}

	//Name List model set up

	/**
	 * Gives access to the list model
	 * @return
	 */
	public ListModel<String> getListModel()
	{
		return nameList.getModel();
	}
	
	
	/**
	 * Set up the history JTable
	 * The column names and the data is passed in from the controller
	 */
	public void setHistoryTable(String[] columnNames, Object[][] data)
	{
		DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);		
		historyTable.setModel(tableModel);
	}
	
	/**
	 * Set up the invoice JTable
	 */
	public void setInvoiceTable(String[] columnNames, Object[][] data)
	{
		DefaultTableModel invoiceMod = new DefaultTableModel(data, columnNames);
		invoiceTable.setModel(invoiceMod);
	}
	
	/**
	 * Set up the JTable for the procedures
	 */
	public void setProceduresTable(String[] columnNames, Object[][] data)
	{
		DefaultTableModel tabMod = new DefaultTableModel(data, columnNames);
		proceduresTable.setModel(tabMod);
	}
	
	/**
	 * Sets up the name list Model so things can be changed in real time
	 * @param nameListModel
	 */
	public void setMainListModel(String[] nameListModel)
	{
		nameList.setListData(nameListModel);
	}
	
	/**
	 * Set up the patientProcedures
	 */
	public void setPatientProceduresTable(String[] columnNames, Object[][] data)
	{
		DefaultTableModel table = new DefaultTableModel(data, columnNames);
		patientProcedures.setModel(table);
	}
	

	
	//ActionListners

	/**
	 * The actionListener for the list that populates the patent names
	 * @param ev
	 */
	public void actionPopulate(ListSelectionListener ev)
	{
		nameList.addListSelectionListener(ev);
	}

	public void savePatientAction(ActionListener e)
	{
		patientSave.addActionListener(e);
	}

	public void deletePatientButtonAction(ActionListener e)
	{
		patientDelete.addActionListener(e);
	}

	public void addPatientButtonAction(ActionListener e)
	{
		addPatient.addActionListener(e);
	}
		
	public void addPayButtonAction(ActionListener e)
	{
		invoicePay.addActionListener(e);
	}
	
	public void addInvoiceTableListner(MouseListener e)
	{
		invoiceTable.addMouseListener(e);
	}
	
	public void addNewHistoryAction(ActionListener e)
	{
		addHistory.addActionListener(e);
	}
		
	public void addNewProcedureAction(ActionListener e)
	{
		procedureAdd.addActionListener(e);
	}
		
	public void deleteProcedure(ActionListener e)
	{
		procedureDelete.addActionListener(e);
	}
	
	public void deleteHistory(ActionListener e)
	{
		historyDelete.addActionListener(e);
	}
	
	public void addInvoiceActionListener(ActionListener e)
	{
		addInvoice.addActionListener(e);
	}
	
	public void deleteInvoiceButtonAction(ActionListener e)
	{
		invoiceDelete.addActionListener(e);
	}
	
	public void addProcedureToInvoice(ActionListener e)
	{
		addPatientProcedure.addActionListener(e);
	}
	
	
	public void deleteProccedureFromInvoice(ActionListener e)
	{
		deletePatientProcedure.addActionListener(e);
	}
	
	public void selectInvoiceProcedudureTableAction(MouseListener e)
	{
		patientProcedures.addMouseListener(e);
	}
	
	public void generateReportOne(ActionListener e)
	{
		doReportOne.addActionListener(e);
	}
	
	public void generateReportTwo(ActionListener e)
	{
		doReportTwo.addActionListener(e);
	}
	
	public void generateReportThree(ActionListener e)
	{
		doReportThree.addActionListener(e);
	}
	
	public void generateReportFour(ActionListener e)
	{
		doReportFour.addActionListener(e);
	}
	
	public void procedureTableListen(MouseListener e)
	{
		proceduresTable.addMouseListener(e);
	}
	
	public void historyTableListen(MouseListener e)
	{
		historyTable.addMouseListener(e);
	}
	
	public void saveSerialize(ActionListener e)
	{
		save.addActionListener(e);
	}
	
	public void loadSerialize(ActionListener e)
	{
		load.addActionListener(e);
	}
	
	public void saveToDatabase(ActionListener e)
	{
		dbSave.addActionListener(e);
	}
	
	public void loadDatabase(ActionListener e)
	{
		dbLoad.addActionListener(e);
	}
	
	


	//GUI reading methods

	/**
	 * Returns the Lists selected value
	 * @return
	 */
	public String getListSelection()
	{
		return nameList.getSelectedValue();
	}

	public int getListSelectionIndex()
	{
		if(nameList.getSelectedIndex() == -1)
		{
			return 0;
		}
		return nameList.getSelectedIndex();
	}

	/**
	 * @return The content in the text field
	 */
	public String getTab1NameField()
	{
		return patientNameTf.getText();
	}

	public String getTab1AddressField()
	{
		return patientAddressTf.getText();
	}

	/**
	 * @return The content in the phone Num field
	 */
	public String getTab1PhoneNum()
	{
		return paitentPhoneNumTf.getText();
	}
	
	/**
	 * Get the table invoice selection
	 */
	public int getinvoiceTableSelection()
	{
		return invoiceTable.getSelectedRow();
	}
		
	/**
	 * Returns the selected procedure
	 */
	public int getProcedureSelection()
	{
		return proceduresTable.getSelectedRow();
	}
	
	/**
	 * Returns the selected history
	 */
	public int getHistorySelection()
	{
		return historyTable.getSelectedRow();
	}
	
	/**
	 * Get the selected procedure in the invoice
	 * @return 
	 */
	public int getProcedureInInvoiceSelection()
	{
		return patientProcedures.getSelectedRow();
	}
	

	//GUI affecting(setting etc) methods

	/**
	 * inputs the patient details into the relevant fields
	 * @param name
	 */
	public void setPatientDetails(String name, String address, String phoneNum)
	{
		//Tab 1 set patient details
		patientNameTf.setText(name);
		patientAddressTf.setText(address);
		paitentPhoneNumTf.setText(phoneNum);

		//Tab 2 set patient details		
		historyPatientNameTf.setText(name);

		//Tab 3 set patient details
		invoicePatientNameTf.setText(name);
	}


	/**
	 * Set the text fields to editable
	 */
	public void enableButtonsForUse()
	{
		patientDelete.setEnabled(true); //enables the delete button here as it enables everything else to be edited
		patientSave.setEnabled(true);
	}

	public void setSavePatientButtonUnEdit()
	{
		patientSave.setEnabled(false);
	}

	
	public void setPatientBalance(double amount)
	{
		invoicePatientBalanceTf.setText(Double.toString(amount) );
	}

	public void setClearTab1TextFields()
	{
		patientNameTf.setText("");
		patientAddressTf.setText("");
		paitentPhoneNumTf.setText("");
	}
	
	public void setDeletePatientButtonUnEdit()
	{
		patientDelete.setEnabled(false);
	}
	
	//When the user adds a patient this gets calls causing the GUI to go the new patient and focus as well as highlight the appropriate textField
	public void setListToNewPatient(int index)
	{
		nameList.setSelectedIndex(index); 
		patientNameTf.requestFocus();    
		patientNameTf.selectAll();
		patientAddressTf.setText("");
		paitentPhoneNumTf.setText("");
	}
	
	public void setInvoicePayToEnabled()
	{
		invoicePay.setEnabled(true);
	}
	
	public void setInvoicePayToDisabled()
	{
		invoicePay.setEnabled(false);
	}
	
	public void setInvoicePayButton(boolean isPaid)
	{
		if(isPaid)
		{
			invoicePay.setText("Mark as unpaid");
		}
		else
		{
			invoicePay.setText("Mark as paid");
		}
	}
	
	public void setHisAddtButtonEnable()
	{
		addHistory.setEnabled(true);
	}
	
	public void setInvoiceProcButtonToEnable()
	{
		addPatientProcedure.setEnabled(true);  
		deletePatientProcedure.setEnabled(true);
	}
	
	public void setInvoiceButtonsToEnabled()
	{
		invoiceDelete.setEnabled(true);
		addInvoice.setEnabled(true);
	}
	
	public void setInvoiceProcedureButtonsToDisable()
	{
		addPatientProcedure.setEnabled(false);
		deletePatientProcedure.setEnabled(false);
	}
	
	public void setInvoiceButtonsToDisabled()
	{
		invoiceDelete.setEnabled(false);
		addInvoice.setEnabled(false);
	}
	
	public void setInvoiceDeleteButtonToDisabled()
	{
		invoiceDelete.setEnabled(false);
	}
	
	public void setInvoiceProcDeleteToDiabled()
	{
		deletePatientProcedure.setEnabled(false);
	}
	
	public void setInvoiceProcDeleteToEnabled()
	{
		deletePatientProcedure.setEnabled(true);
	}
	
	//Reports section
	public void setReportOne(String names)
	{
		reportOneTa.setText(names);
	}
	
	public void setReportTwo(String patBal)
	{
		reportTwoTa.setText(patBal);
	}
	
	public void setReportThree(String NoOutStandingCharge)
	{
		reportThreeTa.setText(NoOutStandingCharge);
	}
	
	public void setReportFour(String patientsWithProcAndInTime)
	{
		reportFourTa.setText(patientsWithProcAndInTime);
	}
	
	public void setProcedureDeleteButtonToEnabled()
	{
		procedureDelete.setEnabled(true);
	}
	
	public void setProcedureDeleteButtonToDisabled()
	{
		procedureDelete.setEnabled(false);
	}
	
	public void setHistoryDeleteButtonToEnabled()
	{
		historyDelete.setEnabled(true);
	}
	
	public void setPatientFieldsToEditable()
	{
		patientNameTf.setEditable(true);
		patientAddressTf.setEditable(true);
		paitentPhoneNumTf.setEditable(true);

	}
	
	public void setDatabaseOptionToDisabled()
	{
		dbSave.setEnabled(false);
	}
	
	public void setDatabaseOptionToEnabled()
	{
		dbSave.setEnabled(true);
	}
	
//	public void setSerialisationOptionToDisabled()
//	{
//		.setEnabled(false);
//	}
//	
//	public void setSerialisationOptionToEnabled()
//	{
//		serialised.setEnabled(true);
//	}
	
	
		
}
