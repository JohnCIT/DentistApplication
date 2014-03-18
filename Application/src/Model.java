import java.util.ArrayList;
import java.util.Date;






public class Model {
	//All the patients and there data is stored here
	PatientList patList = new PatientList();
	
	//All the patients to be deleted
	ArrayList<Integer> patientsToBeDeleted = new ArrayList<Integer>();
	
	//Stores the history's that was deleted
	ArrayList<Integer> historyDeletes = new ArrayList<Integer>();
	ArrayList<History> historyAdds	 = new ArrayList <History> ();
	
	//Stores the invoices to be deleted
	ArrayList<Integer> invoiceDeletes = new ArrayList<Integer>();
	ArrayList<Invoice> invoiceAdds = new ArrayList <Invoice> ();
	
	//Stores the procedures of the invoices to be deleted
	ArrayList<Integer> invoiceProceduresDelete = new ArrayList <Integer>();
	ArrayList <Procedure> invoiceProcedureAdd	  = new ArrayList<Procedure>();
	
	

	public Model() {}

	public Model(PatientList patList) 
	{
		this.patList = patList;
	}
	
	
	/**
	 * Get the new ID of the patient
	 */
	public int getNewPatientId()
	{
		int newPatientId = 0;
		
		for(int i=0; i<patList.size(); i++)
		{
			Patient patient = patList.get(i);
			
			if(patient.getPatientNo() > newPatientId)
			{
				newPatientId = patient.getPatientNo();
			}
		}
		
		return newPatientId + (1 + patientsToBeDeleted.size());
	}
	


	/**
	 * Uses data in the list to print out data in string form
	 * @return
	 */
	public String[] namesForList()
	{
		String[] namesForList = new String[patList.size()];

		for(int i=0; i<patList.size(); i++)
		{
			Patient patient = patList.get(i);
			String name = patient.getName().toString(); //Gets the name of the patient
			namesForList[i] = name;
		}

		return namesForList;
	}


	/**
	 * Gets all the procedure names
	 * @return the procedure names in the form of a string array
	 */
	public String[] procedureNames()
	{
		String[] procedureNames = new String[ProcedureStorage.procedureList.size() ];

		for(int i=0; i<ProcedureStorage.procedureList.size(); i++)
		{
			Procedure procedure = new Procedure();
			procedure = ProcedureStorage.procedureList.get(i);
			procedureNames[i] = procedure.getProcName();
		}

		return procedureNames;
	}


	/**
	 * Get the cost of a particular procedure
	 */
	public double getProcedureCostOfSelectedIndex(int index)
	{
		ArrayList<Procedure> procList = ProcedureStorage.getProcedureList();
		Procedure procedure = procList.get(index);
		return procedure.getProcCost();
	}

	/**
	 * Get the patient through the index passed in
	 */
	public Patient getPatientViaIndex(int index)
	{
		return patList.get(index);
	}

	/**
	 * Get the balance of the patient
	 */
	public double getBalanceOfPatient(int indexOfPatient)
	{
		double totalBalance = 0.0;

		Patient patient = getPatientViaIndex(indexOfPatient);

		for(int i=0; i<patient.p_Invoice.size(); i++)
		{
			Invoice invoice = new Invoice();
			invoice = patient.p_Invoice.get(i);

			if(!invoice.getInvoiceIsPaid() )
			{
				totalBalance -= invoice.getInvoiceAmt();
			}
		}



		return totalBalance;
	}

	

	/**
	 * Gets the history for the selected patient
	 */
	public String getPatientHistoryString(int index)
	{
		Patient patient = getPatientViaIndex(index);
		return patient.p_History.toString();
	}


	/**
	 * Gets the object details for the JTable
	 */
	public Object[][] getDataForHistoryJTable(int index)
	{

		Patient patient = getPatientViaIndex(index);
		int size        = patient.p_History.size();
		Object[][] data = new Object[size][4];


		for(int row=0; row<size; row++)
		{
			History hist = patient.p_History.get(row);
			data[row][0] = hist.getHistID(); 
			data[row][1] = hist.getConditionName();
			data[row][2] = hist.getDateOccured();
			data[row][3] = hist.getMedication();
		}

		return data;
	}


	/**
	 * Returns a string array of the column headers for the History JTable
	 */
	public String[] columnNamesForHistory()
	{
		String [] names = {"History ID", "Condition name", "date occured", "Medication"};
		return names;
	}

	/**
	 * Get the Objects data for the invoices
	 */
	public Object[][] getDataForInvoices(int index)
	{
		Patient patient = getPatientViaIndex(index);
		int size = patient.p_Invoice.size();
		Object[][] data = new Object[size][4];

		for(int row=0; row<size; row++)
		{
			Invoice invoice = patient.p_Invoice.get(row);
			data[row][0] = invoice.getInvoiceNum();
			data[row][1] = invoice.getInvoiceAmt();
			data[row][2] = invoice.getInvoiceDate();
			String paid;
			//if the bill is true say paid and false is unpaid
			if(invoice.getInvoiceIsPaid())
			{
				paid = "Paid";
			}
			else
			{
				paid = "Un-paid";
			}

			data[row][3] = paid;
		}

		return data;

	}

	/**
	 * Set the column headers for the Invoice table
	 */
	public String[] columnNamesForInvoice()
	{
		String [] headings = {"Invoice number", "Amount", "Date", "Is paid"};
		return headings;
	}

	/**
	 * Get the data for the procedures
	 */
	public Object[][] getProceduresDataForTable()
	{
		ArrayList <Procedure> procList = ProcedureStorage.procedureList;
		Object[][] data = new Object[procList.size()][2];

		for(int row=0; row<procList.size(); row++)
		{
			Procedure proc = procList.get(row);
			data[row][0] = proc.getProcName();
			data[row][1] = proc.getProcCost();
		}

		return data;
	}

	/**
	 * Get the column headers for the procedure table
	 */
	public String[] getProcecdureTableNames()
	{
		String[] headers = {"Procedure name", "Procedure cost"};
		return headers;
	}

	/**
	 * Get the data for the patient procedures, It consists of all the procedures the invoice has
	 */
	public Object[][] getPaitentProcedures(int patientIndex, int invoiceIndex)
	{
		Patient patient = getPatientViaIndex(patientIndex);
		Invoice invoice = (Invoice) patient.getPatientInvoiceList().get(invoiceIndex);

		Object[][] data = new Object[invoice.getProcList().size()][2];

		for(int row=0; row<invoice.getProcList().size(); row++)
		{
			Procedure proc = invoice.getProcList().get(row);
			data[row][0] = proc.getProcName();
			data[row][1] = proc.getProcCost();
		}

		return data;
	}

	/**
	 * Receives a index and changes the index from false to true. payments for invoices
	 */
	public void changePaymentToPaid(int patientIndex, int invoiceIndex)
	{
		Patient patient = patList.get(patientIndex);
		ArrayList <Invoice> invoiceList = patient.getPatientInvoiceList();
		Invoice invoice = invoiceList.get(invoiceIndex);
		invoice.setInvoiceIsPaid(true);		
	}

	/**
	 * Changes the payment in the invoices to unpaid
	 */
	public void changePaymentToUnpaid(int patientIndex, int invoiceIndex)
	{
		Patient patient = patList.get(patientIndex);
		ArrayList <Invoice> invoiceList = patient.getPatientInvoiceList();
		Invoice invoice = invoiceList.get(invoiceIndex);
		invoice.setInvoiceIsPaid(false);		
	}

	/**
	 * Checks to see if the selection is paid or unpaid
	 */
	public boolean checkPaymentType(int patientIndex, int invoiceIndex)
	{
		Patient patient = patList.get(patientIndex);
		ArrayList <Invoice> invoiceList = patient.getPatientInvoiceList();
		Invoice invoice = invoiceList.get(invoiceIndex);
		if(invoice.getInvoiceIsPaid())
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * Saves new history
	 */
	public void saveNewHist(int patientIndex, String conditionName, Date dateOccured, String medication)
	{
		Date todaysDate = new Date();


		Patient patient = getPatientViaIndex(patientIndex);

		int histId = 0;
		
		for(int i=0; i<patList.size(); i++)
		{
			Patient pat = patList.get(i);
			ArrayList historys = pat.p_History;
			
			for(int histIndex=0; histIndex<historys.size(); histIndex++)
			{
				History hist = (History) historys.get(histIndex);
				
				//If the history id is higher set it to that, This ensure no duplicates
				if(hist.getHistID() > histId)
				{
					histId = hist.getHistID();
				}
			}
			
		}
		// Gets all amount of all the history's and adds one for a unique historyNum, if the user deletes anything this should be taken into account
		histId += 1;
		histId += historyDeletes.size();

		History hist = new History(histId, conditionName, todaysDate, medication);

		patient.p_History.add(hist);//Add the history to the list
		historyAdds.add(hist);
	}

	/**
	 * Save a new procedure
	 */
	public void saveProcedure(String name, Double cost)
	{
		Procedure proc = new Procedure(ProcedureStorage.procedureList.size()+1, name, cost);
		ProcedureStorage.procedureList.add(proc);
		ProcedureStorage.saveProcedure();		
	}

	/**
	 * Delete a procedure
	 */
	public void deleteProcedure(int index)
	{
		ProcedureStorage.procedureList.remove(index);
		ProcedureStorage.saveProcedure();
	}

	/**
	 * Delete history
	 */
	public void deleteHistory(int patientIndex, int historyIndex)
	{
		Patient patient = getPatientViaIndex(patientIndex);
		History hist = patient.p_History.get(historyIndex);
		historyDeletes.add(hist.getHistID());//Gets the Id so it can be deleted later from the database.
		patient.p_History.remove(historyIndex);	
	}
	

	/**
	 * Delete invoice
	 */
	public void deleteInvoice(int patientIndex, int invoiceIndex)
	{
		Patient patient = getPatientViaIndex(patientIndex);		
		Invoice in = (Invoice) patient.getPatientInvoiceList().get(invoiceIndex);		
		invoiceDeletes.add(in.getInvoiceNum());	
		AdditionalRemoves.invoiceRemove(in, invoiceProceduresDelete);
		patient.getPatientInvoiceList().remove(invoiceIndex);
	}

	/**
	 * Get the procedure names into a string array
	 */
	public String[] getProceduresInStringArray()
	{
		//Initilize the string
		String[] procNames = new String [ProcedureStorage.procedureList.size()];

		//Get all the procedure names
		for(int i=0; i<ProcedureStorage.procedureList.size(); i++)
		{
			Procedure proc = ProcedureStorage.procedureList.get(i);
			procNames[i] = proc.getProcName();
		}

		return procNames;
	}

	/**
	 * Save the invoice
	 */
	public void savePatientInvoice(int patientIndex, int procListIndex, Date date)
	{
		Patient patient = getPatientViaIndex(patientIndex);

		String procName = ProcedureStorage.procedureList.get(procListIndex).getProcName(); //Get the procedure name from the list using the index
		
		int invoiceId = 0;
		
		for(int i=0; i<patList.size(); i++)
		{
			Patient pat = patList.get(i);
			
			ArrayList patIn = pat.p_Invoice;
			
			for(int invoiceIndex=0; invoiceIndex<patIn.size(); invoiceIndex++)
			{
				Invoice inv = (Invoice) patIn.get(invoiceIndex);
				if(inv.getInvoiceNum() > invoiceId)
				{
					invoiceId = inv.getInvoiceNum();
				}
			}
		}
		
		invoiceId += invoiceDeletes.size();

		Invoice invoice = new Invoice(invoiceId+1, procName, date, false);

		patient.p_Invoice.add(invoice);
		invoiceAdds.add(invoice);
		
		Procedure proUp = new Procedure();
		Procedure proc = getProcedureViaName(procName);
		
		proUp.setProcCost(proc.getProcCost());
		proUp.setProcName(proc.getProcName());
		proUp.setProcNum(getNewProcedureId());
		
		invoice.procList.add(proUp);		
		invoiceProcedureAdd.add(proUp);
	}
	
	
	
	/**
	 * Counts all the procedures all invoices have + 1, used to get the Id of the next procedure Id, 
	 */
	public int getNewProcedureId()
	{
		int newProcedureId = 0;
		
		
		for(int i=0; i<patList.size(); i++)
		{
			Patient patient = patList.get(i);
			ArrayList invoices = patient.p_Invoice;
			
			for(int invoiceIndex=0; invoiceIndex<invoices.size(); invoiceIndex++)
			{
				Invoice invoice = (Invoice) invoices.get(invoiceIndex);
				ArrayList procedures = invoice.procList;
				
				for(int procedureIndex=0; procedureIndex<procedures.size(); procedureIndex++)
				{
					Procedure proc = (Procedure) procedures.get(procedureIndex);
					
					if(proc.getProcNum() > newProcedureId)
					{
						newProcedureId = proc.getProcNum();
					}
					
				}
			}
			
		}
		
		newProcedureId += invoiceProceduresDelete.size();		
		return newProcedureId + 1;
	}

	
	
	/**
	 * Add a procedure to a invoice
	 */
	public void saveProcedureToInvoice(int patientIndex, int invoiceIndex, int procedureIndex)
	{
		Patient patient = getPatientViaIndex(patientIndex);
		Invoice invoice = patient.p_Invoice.get(invoiceIndex);
		Procedure proc = ProcedureStorage.procedureList.get(procedureIndex);
	
		Procedure procUp = new Procedure();
		procUp.setProcCost(proc.getProcCost());
		procUp.setProcName(proc.getProcName());
		procUp.setProcNum(getNewProcedureId());
		
		
		patient.p_Invoice.get(invoiceIndex).procList.add(procUp);
		invoiceProcedureAdd.add(procUp);
	}

	/**
	 * Remove a procedure form a invoice
	 */
	public void deleteProceudreFromInvoice(int patientIndex, int invoiceIndex, int procedureIndex)
	{
		Patient patient = getPatientViaIndex(patientIndex);
		Invoice invoice = patient.p_Invoice.get(invoiceIndex);
		Procedure proc = invoice.procList.get(procedureIndex);
		
		invoiceProceduresDelete.add(proc.getProcNum());
		invoice.procList.remove(procedureIndex);
	}

	/**
	 * Check if a patient has outstanding charges
	 */
	public boolean isPatientClearOfDebt(int patientIndex)
	{
		double amount = getBalanceOfPatient(patientIndex);
		if(amount != 0)
		{
			return false;
		}
		else
		{
			return true;
		}

	}

	/**
	 * return a String array of patients that received treatments in a certain time frame
	 */
	public String report1PatientTreatment(Date firstDate, Date secondDate)
	{
		String patients = "";

		for(int i=0; i<patList.size(); i++)
		{
			Patient patient = getPatientViaIndex(i);

			for(int invoiceIndex=0; invoiceIndex<patient.p_Invoice.size(); invoiceIndex++)
			{
				Invoice invoice = patient.p_Invoice.get(invoiceIndex);

				if(invoice.getInvoiceDate().after(firstDate) && invoice.getInvoiceDate().before(secondDate) && !patients.contains(patient.getName()) )
				{
					patients +=  patient.getName() + "\n";
				}
				else if(invoice.getInvoiceDate().equals(firstDate) || invoice.getInvoiceDate().equals(secondDate))
				{
					patients +=  patient.getName() + "\n";
				}
			}

		}

		return patients;
	}

	/**
	 * Show all patients who owe money, display how much they owe and combined total at the bottom
	 */
	public String report2paitentBalance()
	{
		String patientBal = "";
		double total = 0.0;

		for(int i=0; i<patList.size(); i++)
		{
			Patient patient = getPatientViaIndex(i);

			if(getBalanceOfPatient(i) < 0)
			{
				patientBal += patient.getName() + ": " +Double.valueOf(getBalanceOfPatient(i))  + "\n";
				total += getBalanceOfPatient(i);	
			}

		}


		return patientBal + "\nTotal balance: " + Double.valueOf(total);
	}

	/**
	 * Display all patients who owe no money
	 */
	public String report3PatientsNoMoney()
	{
		String names = "";

		for(int i=0; i<patList.size(); i++)
		{
			Patient patient = getPatientViaIndex(i);
			boolean clearOfDebt = isPatientClearOfDebt(i);

			if(clearOfDebt)
			{
				names += patient.getName() + "\n";
			}
		}

		return names;
	}

	/**
	 * Find patients in a specific time frame and who have had a specific procedure
	 */
	public String reportFourProcedureTime(Date firstDate, Date secondDate, int procIndex)
	{
		Procedure procedure = ProcedureStorage.procedureList.get(procIndex);
		String procName = procedure.getProcName();
		String patients = "";

		for(int i=0; i<patList.size(); i++)
		{
			Patient patient = getPatientViaIndex(i);

			for(int invoiceIndex=0; invoiceIndex<patient.p_Invoice.size(); invoiceIndex++) //Loops the patients
			{
				Invoice invoice = patient.p_Invoice.get(invoiceIndex);

				if(invoice.getInvoiceDate().after(firstDate) && invoice.getInvoiceDate().before(secondDate) && !patients.contains(patient.getName()) ) //Loops the invoice and checks the date
				{
					for(int procIn=0; procIn<ProcedureStorage.procedureList.size(); procIn++)//Loops the procedures and checks the the procedure name against what was selected
					{
						if(ProcedureStorage.procedureList.get(procIn).getProcName().equals(procName))
						{
							patients +=  patient.getName() + "\n"; //Finally all the conditions were checked and the patient name can be added
						}
					}
					
				}
				else if(invoice.getInvoiceDate().equals(firstDate) || invoice.getInvoiceDate().equals(secondDate) && !patients.contains(patient.getName()) )
				{
					for(int procIn=0; procIn<ProcedureStorage.procedureList.size(); procIn++)//Loops the procedures and checks the the procedure name against what was selected
					{
						if(ProcedureStorage.procedureList.get(procIn).getProcName().equals(procName))
						{
							patients +=  patient.getName() + "\n"; //Finally all the conditions were checked and the patient name can be added
						}
					}
				}
			}

		}

		return patients;
	}
	
	/**
	 * Resets all the lists
	 */
	public void resetListCounts()
	{
		historyDeletes 			= new ArrayList();
		invoiceDeletes 			= new ArrayList();
		patientsToBeDeleted 	= new ArrayList();
		invoiceProceduresDelete = new ArrayList();
		historyAdds 			= new ArrayList<History>();	
		invoiceAdds 			= new ArrayList<Invoice>();
		invoiceDeletes 			= new ArrayList();
		invoiceProcedureAdd		= new ArrayList<Procedure>();
		invoiceDeletes     		= new ArrayList();
	}
	
	
	
	/**
	 * Gives a procedure based on the name put in 
	 */
	public Procedure getProcedureViaName(String proName)
	{
		Procedure proc = new Procedure();
		
		for(int i=0; i<ProcedureStorage.procedureList.size(); i++)
		{
			Procedure procedure = ProcedureStorage.procedureList.get(i);
		
			if(procedure.getProcName().equals(proName))
			{
				proc = procedure;
			}
		}
		
		return proc;
	}
	
}
