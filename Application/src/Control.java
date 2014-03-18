
import java.awt.Dimension;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.event.*;

import net.miginfocom.swing.MigLayout;
import net.sf.nachocalendar.CalendarFactory;
import net.sf.nachocalendar.components.CalendarPanel;


public class Control{

	View GUI;
	Model guiMethods;
	PatientList patList = new PatientList();
	ArrayList<Procedure> procList;
	ArrayList<Patient> addedPatients = new ArrayList<Patient>();
	int choice;

	public Control() {}

	public Control(Model methodsGUIUses, View GUI, PatientList patList, int choice) 
	{
		this.GUI = GUI;
		this.guiMethods = methodsGUIUses;
		this.patList = patList;
		this.choice = choice;
		
		//Sort out choice saving loading etc
		enableDisableBasedOnChoice();		

		//Set up the listModel
		GUI.setMainListModel(guiMethods.namesForList());

		//Set up the JTable
		GUI.setProceduresTable(guiMethods.getProcecdureTableNames(), guiMethods.getProceduresDataForTable());

		//Set up the procedure list
		procList = ProcedureStorage.getProcedureList();//Gets the list to load all data into the list and returns the list to use

		//Set the actionListners up
		GUI.actionPopulate(new ListSelection() );
		GUI.savePatientAction(new SaveNewDetailsOfPatient() );	
		GUI.deletePatientButtonAction(new DeleteSelection() );
		GUI.addPatientButtonAction(new AddPatientSelection() );
		GUI.addPayButtonAction(new PaySelection() );
		GUI.addInvoiceTableListner(new InvoiceTableFocus() );
		GUI.addNewHistoryAction(new AddHistoryFrame() );
		GUI.addNewProcedureAction(new ProcedureAdd() );
		GUI.deleteProcedure(new ProcedureDeleteAction() );
		GUI.deleteHistory(new DeleteHistory() );
		GUI.addInvoiceActionListener(new AddInvoice() );
		GUI.deleteInvoiceButtonAction(new DeleteInvoice() );
		GUI.addProcedureToInvoice(new AddNewProcedureToInvoice() );
		GUI.deleteProccedureFromInvoice(new DeletProcedureFromInvoice() );
		GUI.selectInvoiceProcedudureTableAction(new SelectProcedureInInvoice() );
		GUI.generateReportOne(new GenerateReportOne() );
		GUI.generateReportTwo(new GenerateReportTwo() );
		GUI.generateReportThree(new GenerateReportThree() );
		GUI.generateReportFour(new GenerateReportFour() );
		GUI.procedureTableListen(new ProceudreTableSelection() );
		GUI.historyTableListen(new HistoryTableListener() );
		GUI.saveSerialize(new SaveSerialize() );
		GUI.loadSerialize(new LoadSerialize() );
		GUI.loadDatabase(new LoadDatabase() );
		GUI.saveToDatabase(new SaveToDatabase() );

	}
	
	
	private void enableDisableBasedOnChoice()
	{
		if(choice != 2)
		{
			GUI.setDatabaseOptionToDisabled();
		}
		
	}

	/**
	 * Update the GUI with new information
	 */
	public void updateGUI()
	{
		GUI.setHistoryTable(guiMethods.columnNamesForHistory(), guiMethods.getDataForHistoryJTable(GUI.getListSelectionIndex()));//Updates the historyTable
		GUI.setInvoiceTable(guiMethods.columnNamesForInvoice(), guiMethods.getDataForInvoices(GUI.getListSelectionIndex())); //Updates the invoices
		GUI.setProceduresTable(guiMethods.getProcecdureTableNames(), guiMethods.getProceduresDataForTable());//Updates the procedures
		GUI.setPatientBalance(guiMethods.getBalanceOfPatient(GUI.getListSelectionIndex()) ); //Updates the balance
		GUI.setPatientProceduresTable(null, null); //When the user changes patient, sets the table to empty, stops confusion for the user. 
	}


	/**
	 * Implements the actionListners for the GUI
	 */



	/**
	 * The list actionListner 
	 */
	class ListSelection implements ListSelectionListener{

		public void valueChanged(ListSelectionEvent e) 
		{
			// Get the index of the patient
			int index = GUI.getListSelectionIndex();

			// Get the patient object that was selected
			Patient patient = guiMethods.getPatientViaIndex(index);

			// Set all the details of the patient
			GUI.setPatientDetails(patient.getName(), patient.getAddress(), patient.getPhone() ); // Sets the names in the GUI to there appropriate field
			GUI.setPatientBalance(guiMethods.getBalanceOfPatient(index) );

			//Set up the buttons for use
			GUI.enableButtonsForUse();
			GUI.setHisAddtButtonEnable();
			GUI.setInvoiceButtonsToEnabled();
			GUI.setInvoiceProcedureButtonsToDisable(); //Stops the user being able to add procedures with out a invoice selected
			GUI.setInvoiceDeleteButtonToDisabled(); //Stops the user being able to make and delete a invoice with out having one selected
			GUI.setInvoicePayToDisabled();
			GUI.setProcedureDeleteButtonToDisabled();

			//update the GUI
			updateGUI();
			GUI.setPatientFieldsToEditable();
		}

	}




	class SaveNewDetailsOfPatient implements ActionListener{

		public void actionPerformed(ActionEvent e)	
		{
			Patient patient =  guiMethods.getPatientViaIndex(GUI.getListSelectionIndex()); 

			//Set the patient with the new details from the GUI	
			patient.setName(GUI.getTab1NameField() );
			patient.setAddress(GUI.getTab1AddressField() );
			patient.setPhone(GUI.getTab1PhoneNum() );

			//Reset the list selection
			GUI.setMainListModel(guiMethods.namesForList() ); //Updates the Name list on the GUI. This is essential
			GUI.setSavePatientButtonUnEdit();// Stops the user being able to save nothing
		}

	}

	/**
	 * Deletes the list selection
	 */
	class DeleteSelection implements ActionListener{


		public void actionPerformed(ActionEvent e) 
		{
			int index = GUI.getListSelectionIndex(); //Get the index of the user has selected
			Patient patientToBeRemoved = guiMethods.getPatientViaIndex(index); //Get the patent reference
			guiMethods.patientsToBeDeleted.add(patientToBeRemoved.getPatientNo());
			patList.remove(patientToBeRemoved);
			AdditionalRemoves.patientRemove(patientToBeRemoved, guiMethods.historyDeletes, guiMethods.invoiceDeletes, guiMethods.invoiceProceduresDelete);

			//Reset the list selection
			GUI.setMainListModel(guiMethods.namesForList() );
			GUI.setSavePatientButtonUnEdit();//Stops the user saving nothing 
			GUI.setDeletePatientButtonUnEdit();
			
		}



	}


	/**
	 * Adds the new patient to the list
	 */
	class AddPatientSelection implements ActionListener{

		public void actionPerformed(ActionEvent e) 
		{
			Patient p = new Patient(guiMethods.getNewPatientId(), "", "", "");	//A empty patient

			//Add the patient
			patList.add(p);

			GUI.setMainListModel(guiMethods.namesForList() );//Update the list
			GUI.setListToNewPatient(patList.size()-1);	 //The list goes to the new patient
			GUI.setPatientFieldsToEditable();// Allows the fields to be editable
			
			addedPatients.add(p);
		}

	}


	/**
	 * When the user pays the invoice change it to true or false depending on the data
	 */
	class PaySelection  implements ActionListener{


		public void actionPerformed(ActionEvent e) 
		{
			int patientIndex = GUI.getListSelectionIndex();
			int invoiceIndex = GUI.getinvoiceTableSelection();
			//Check to see if it is paid or not
			if(!guiMethods.checkPaymentType(patientIndex, invoiceIndex) )
			{
				guiMethods.changePaymentToPaid(patientIndex, invoiceIndex); //Changes the payment to paid
			}
			else
			{
				guiMethods.changePaymentToUnpaid(patientIndex, invoiceIndex);
			}

			GUI.setInvoicePayToDisabled();
			GUI.setInvoiceProcedureButtonsToDisable();
			updateGUI();
		}

	}

	/**
	 * When the focus goes onto the invoice table enable the button
	 */ 
	class InvoiceTableFocus implements MouseListener{

		public void mouseClicked(MouseEvent e) 
		{
			GUI.setInvoicePayToEnabled();	
			int invoiceIndex = GUI.getinvoiceTableSelection();
			int patientIndex = GUI.getListSelectionIndex();
			GUI.setInvoicePayButton(guiMethods.checkPaymentType(patientIndex, invoiceIndex)); // set the invoice to paid or unpaid
			GUI.setInvoiceButtonsToEnabled();
			GUI.setInvoiceProcButtonToEnable();
			GUI.setInvoiceProcDeleteToDiabled();

			//Show the procedures
			GUI.setPatientProceduresTable(guiMethods.getProcecdureTableNames(), guiMethods.getPaitentProcedures(GUI.getListSelectionIndex(), GUI.getinvoiceTableSelection()));
		}

		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		


	}

	/**
	 * Open a new frame for adding history and saves it
	 */
	class AddHistoryFrame implements ActionListener{

		public void actionPerformed(ActionEvent e) 
		{
			//Creates a new dialog and gets the history
			JPanel pan 					= new JPanel(new MigLayout());
			JLabel conditionName  		= new JLabel("Condition:");
			JLabel medication    		= new JLabel("medication:");
			JTextField conditionNameTf 	= new JTextField();
			JTextField medicationTf 	= new JTextField();

			conditionNameTf.setPreferredSize(new Dimension(200, 25));
			pan.add(conditionName);
			pan.add(conditionNameTf, "wrap");
			pan.add(medication);
			pan.add(medicationTf, "grow");


			int choice = JOptionPane.showConfirmDialog(null, pan, "Add new Procedure", JOptionPane.OK_CANCEL_OPTION);
			String conditionNameString = "";
			String medicationName = "";

			if(choice == JOptionPane.OK_OPTION)
			{
				conditionNameString = conditionNameTf.getText();
				medicationName = medicationTf.getText();
				guiMethods.saveNewHist(GUI.getListSelectionIndex(), conditionNameString, null, medicationName);
				updateGUI();
			}
		}

	}


	/**
	 * Make a dialog box, get the data and save the procedure
	 */
	class ProcedureAdd implements ActionListener{


		public void actionPerformed(ActionEvent e) 
		{
			//TODO check for double.

			//Create a Dialog box
			JPanel pan = new JPanel(new MigLayout());
			JLabel procName = new JLabel("Procedure name:");
			JLabel procCost = new JLabel("Procedure cost: ");
			JTextField procNameTf = new JTextField();
			JTextField procCostTf = new JTextField();

			procNameTf.setPreferredSize(new Dimension(200, 25));
			pan.add(procName);
			pan.add(procNameTf, "wrap");
			pan.add(procCost);
			pan.add(procCostTf, "grow");


			int choice = JOptionPane.showConfirmDialog(null, pan, "Add new Procedure", JOptionPane.OK_CANCEL_OPTION);
			String procedureName = "";
			Double procedureCost = 0.0;
			
			

			if(choice == JOptionPane.OK_OPTION)
			{
				procedureName = procNameTf.getText();
				Scanner inputChecker = new Scanner(procCostTf.getText()); //Scan the input and check if it is a double.
				if(inputChecker.hasNextDouble())
				{
					procedureCost = Double.valueOf(procCostTf.getText());
					guiMethods.saveProcedure(procedureName, procedureCost);
					updateGUI();
				}
				else
				{
					JFrame frame = new JFrame();
					JOptionPane.showMessageDialog(frame, "You must enter a number into the cost field. Aborting...");
				}
				
			}


		}

	}


	class ProcedureDeleteAction implements ActionListener{

		public void actionPerformed(ActionEvent e)
		{
			guiMethods.deleteProcedure(GUI.getProcedureSelection());	
			updateGUI();
		}

	}


	/**
	 * Deletes the selected history
	 */
	class DeleteHistory implements ActionListener{


		public void actionPerformed(ActionEvent e)
		{
			guiMethods.deleteHistory(GUI.getListSelectionIndex(), GUI.getHistorySelection() );			
			updateGUI();
		}

	}


	/**
	 * Add a invoice
	 */
	class AddInvoice implements ActionListener{

		public void actionPerformed(ActionEvent e) 
		{
			//Create a Dialog box
			JPanel pan = new JPanel(new MigLayout());
			JComboBox<String> procedures = new JComboBox<String>(guiMethods.getProceduresInStringArray());
			CalendarPanel calendarpanel = CalendarFactory.createCalendarPanel(); //Adds the date selecter

			pan.add(procedures);
			pan.add(calendarpanel);


			int choice = JOptionPane.showConfirmDialog(null, pan, "Add invoice", JOptionPane.OK_CANCEL_OPTION);
			Date today = new Date();

			if(choice == JOptionPane.OK_OPTION)
			{
				Date date = (Date) calendarpanel.getValue();
				
				if(date == null)
				{
					date = today;
				}
				
				guiMethods.savePatientInvoice(GUI.getListSelectionIndex(), procedures.getSelectedIndex(), date);
				GUI.setInvoiceButtonsToDisabled(); //Stops the user being able to push buttons when they shouldnt
				GUI.setInvoicePayToDisabled();
				GUI.setInvoiceProcedureButtonsToDisable();
				updateGUI();
			}

		}

	}


	/**
	 * Delete a invoice
	 */
	class DeleteInvoice implements ActionListener{


		public void actionPerformed(ActionEvent e) 
		{
			guiMethods.deleteInvoice(GUI.getListSelectionIndex(), GUI.getinvoiceTableSelection());
			GUI.setInvoiceButtonsToDisabled();
			GUI.setInvoicePayToDisabled();
			GUI.setInvoiceProcedureButtonsToDisable();
			updateGUI();			
		}


	}

	/**
	 * Add a new procedure to a invoice
	 */
	class AddNewProcedureToInvoice implements ActionListener{

		public void actionPerformed(ActionEvent e) 
		{
			//Create a Dialog box
			JPanel pan = new JPanel(new MigLayout());
			JComboBox<String> procedures = new JComboBox<String>(guiMethods.getProceduresInStringArray());

			pan.add(procedures);

			int choice = JOptionPane.showConfirmDialog(null, pan, "Add invoice", JOptionPane.OK_CANCEL_OPTION);

			if(choice == JOptionPane.OK_OPTION)
			{
				guiMethods.saveProcedureToInvoice(GUI.getListSelectionIndex(), GUI.getinvoiceTableSelection(), procedures.getSelectedIndex());
				GUI.setPatientProceduresTable(guiMethods.getProcecdureTableNames(), guiMethods.getPaitentProcedures(GUI.getListSelectionIndex(), GUI.getinvoiceTableSelection()));//Update the procedures each in
				GUI.setInvoiceProcedureButtonsToDisable();
				GUI.setInvoiceButtonsToDisabled();
				GUI.setInvoicePayToDisabled();
				GUI.setInvoiceProcedureButtonsToDisable();
				updateGUI();// used to update the balance
			}


		}

	}


	class DeletProcedureFromInvoice implements ActionListener{

		public void actionPerformed(ActionEvent e) 
		{
			guiMethods.deleteProceudreFromInvoice(GUI.getListSelectionIndex(), GUI.getinvoiceTableSelection(), GUI.getProcedureInInvoiceSelection());
			GUI.setPatientProceduresTable(guiMethods.getProcecdureTableNames(), guiMethods.getPaitentProcedures(GUI.getListSelectionIndex(), GUI.getinvoiceTableSelection()));//Update the procedures each in
			GUI.setInvoiceProcedureButtonsToDisable();//Stops the user deleting nothing
			GUI.setInvoiceButtonsToDisabled();
			GUI.setInvoicePayToDisabled();
			GUI.setInvoiceProcedureButtonsToDisable();
			updateGUI();// used to update the balance
		}

	}

	/**
	 * Allows the deletion of procedures from a the invoice
	 *
	 */
	class SelectProcedureInInvoice implements MouseListener{

		public void mouseClicked(MouseEvent e)
		{
			if(GUI.getinvoiceTableSelection() == -1)
			{
				GUI.setInvoiceProcDeleteToDiabled();
			}
			else
			{
				GUI. setInvoiceProcDeleteToEnabled();
			}
			
		}

		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e){} 
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		

	}

	/**
	 * Do Reports
	 */
	class GenerateReportOne implements ActionListener{


		public void actionPerformed(ActionEvent e) 
		{
			//Make frame for picking two dates
			JFrame frame = new JFrame();
			JPanel panel = new JPanel(new MigLayout());
			JLabel pickDateOne = new JLabel("Pick firstDate, not inclusive of");
			JLabel pickDateTwo = new JLabel("Pick second date, not inclusive of");
			CalendarPanel firstDate = new CalendarPanel();
			CalendarPanel secondDate = new CalendarPanel();

			panel.add(pickDateOne, "wrap");
			panel.add(firstDate);
			panel.add(pickDateTwo, "wrap, cell 1 0");
			panel.add(secondDate, "cell 1 1");

			int choice = JOptionPane.showConfirmDialog(null, panel, "Dates for report one", JOptionPane.OK_CANCEL_OPTION);

			
			Date date = new Date();
			Date lastMonth = new Date();
			if(choice == JOptionPane.OK_OPTION)
			{
				Date dateOne = (Date) firstDate.getValue();
				Date dateTwo = (Date) secondDate.getValue();
				if(dateOne == null)//Ensures the program doesn't crash
				{
					dateOne = date;
					if(dateTwo == null)
					{
						dateTwo = date;
					}
					
				}
				else if( dateTwo == null)
				{
					dateTwo = date;
				}
				
				GUI.setReportOne(guiMethods.report1PatientTreatment(dateOne, dateTwo));	
			}
			else if(choice == JOptionPane.CANCEL_OPTION)
			{
				//Report one
				lastMonth.setMonth(date.getMonth()-1);
				GUI.setReportOne(guiMethods.report1PatientTreatment(date, lastMonth));	
			}

		}

	}

	/**
	 * Generate the fourth report
	 */
	class GenerateReportTwo implements ActionListener{


		public void actionPerformed(ActionEvent e) 
		{
			GUI.setReportTwo(guiMethods.report2paitentBalance());		
		}

	}

	/**
	 * Generate the fourth report
	 */
	class GenerateReportThree implements ActionListener{


		public void actionPerformed(ActionEvent e)
		{
			GUI.setReportThree(guiMethods.report3PatientsNoMoney());		
		}


	}

	/**
	 * Generate the fourth report
	 */
	class GenerateReportFour implements ActionListener{

		public void actionPerformed(ActionEvent e)
		{
			JPanel panel = new JPanel(new MigLayout());
			JLabel pickDateOne = new JLabel("Pick firstDate, not inclusive of");
			JLabel pickDateTwo = new JLabel("Pick second date, not inclusive of");
			CalendarPanel firstDate = new CalendarPanel();
			CalendarPanel secondDate = new CalendarPanel();
			JComboBox<String> proc =  new JComboBox<String>(guiMethods.getProceduresInStringArray());

			panel.add(pickDateOne, "wrap");
			panel.add(firstDate);
			panel.add(pickDateTwo, "wrap, cell 1 0 ");
			panel.add(secondDate, "cell 1 1");
			panel.add(proc);
			
			Date date = new Date();
			Date nextDate = new Date();	
			
			int choice = JOptionPane.showConfirmDialog(null, panel, "Dates for report one", JOptionPane.OK_CANCEL_OPTION);

			if(choice == JOptionPane.OK_OPTION)
			{
				Date dateOne = (Date) firstDate.getValue();
				Date dateTwo = (Date) secondDate.getValue();
				
				if(dateOne == null)//Ensures the program doesn't crash
				{
					dateOne = date;
					if(dateTwo == null)
					{
						dateTwo = nextDate;
					}
				}
				else if( dateTwo == null)
				{
					dateTwo = nextDate;
				}
				GUI.setReportFour(guiMethods.reportFourProcedureTime(dateOne, dateTwo, proc.getSelectedIndex()));	
			}
			else if(choice == JOptionPane.CANCEL_OPTION)
			{
				nextDate.setMonth(date.getMonth()+1);
				GUI.setReportFour(guiMethods.report1PatientTreatment(date, nextDate));	
			}
			
			
		}

	}
	
	/**
	 * The selection for the procedure list, just set the delet button to enabled, it will prevent the  user deleting nothing causing a crash
	 */
	public class ProceudreTableSelection implements MouseListener{

	
		public void mouseClicked(MouseEvent e) 
		{
			GUI.setProcedureDeleteButtonToEnabled();			
		}
	
		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}		
		public void mouseExited(MouseEvent e) {}
		
	}
	
	/**
	 * The history table listener
	 */
	public class HistoryTableListener implements MouseListener{

		
		public void mouseClicked(MouseEvent e) 
		{
			GUI.setHistoryDeleteButtonToEnabled();
			
		}

		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		
	}
	
	/**
	 * Save the serialisation
	 */
	public class SaveSerialize implements ActionListener{

		public void actionPerformed(ActionEvent e) 
		{
			SerializeLoadAndSave save = new SerializeLoadAndSave();
			save.save(patList);		
			
			
		}
		
	}
	
	/**
	 * Load the serialisation
	 */
	public class LoadSerialize implements ActionListener{

		public void actionPerformed(ActionEvent e)
		{
			SerializeLoadAndSave load = new SerializeLoadAndSave();
			patList = (PatientList) load.load();
			guiMethods.patList = patList;
			
			//Stop saving to the database
			GUI.setDatabaseOptionToDisabled();
			
			//Set up the listModel
			GUI.setMainListModel(guiMethods.namesForList());  //Needed to refresh the GUI during run time
						
		}
		
	}
	
	/**
	 * Load from the database
	 */
	public class LoadDatabase implements ActionListener{

		public void actionPerformed(ActionEvent e) 
		{
			Database db = new Database();
			patList = db.loadFromDB();	
			guiMethods.patList = patList;
			
			//Re enable the database save function
			GUI.setDatabaseOptionToEnabled();
			
			//Reset the list model
			GUI.setMainListModel(guiMethods.namesForList());  //Needed to refresh the GUI during run time		
		}	
		
	}
	
	
	/**
	 * Save to the database
	 */
	public class SaveToDatabase implements ActionListener{

		public void actionPerformed(ActionEvent e) 
		{
			//Checks everything that is to be added against everythign that is to be deleted, ensures no duplicates that can attained under certain circumstances, such as directly after loading, if the user adds, then deletes and adds.
			addedPatients = CheckArraysForDuplicates.checkPatientArraysForDuplicates(guiMethods.patientsToBeDeleted, addedPatients);
			guiMethods.historyAdds = CheckArraysForDuplicates.checkHistoryArrayForDuplicates(guiMethods.historyDeletes, guiMethods.historyAdds);
			guiMethods.invoiceAdds = CheckArraysForDuplicates.checkInvoiceArrayForDuplicates( guiMethods.invoiceDeletes, guiMethods.invoiceAdds);
			guiMethods.invoiceProcedureAdd = CheckArraysForDuplicates.checkInvoiceProceduresForDuplicates(guiMethods.invoiceProceduresDelete, guiMethods.invoiceProcedureAdd);
			
			Database db = new Database();
			db.saveDataToDatabase(patList, guiMethods.patientsToBeDeleted, addedPatients, guiMethods.historyAdds, guiMethods.historyDeletes, guiMethods.invoiceAdds, guiMethods.invoiceDeletes, guiMethods.invoiceProceduresDelete, guiMethods.invoiceProcedureAdd);
			guiMethods.resetListCounts();
			addedPatients = new ArrayList(); // resets the list of added patients. Essential
		}
		
	}


}




