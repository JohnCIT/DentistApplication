import java.util.ArrayList;


public class CheckArraysForDuplicates {


	/**
	 * Returns the array of patients to be added. removes any duplicates that can be got under certain circumstances.
	 */
	public static ArrayList checkPatientArraysForDuplicates(ArrayList deletedPatients, ArrayList addedPatients)
	{

		ArrayList addedPatientsUpdated = addedPatients;

		for(int deletePatIndex=0; deletePatIndex < deletedPatients.size(); deletePatIndex++)
		{

			for(int addPatientIndex=0; addPatientIndex < addedPatients.size(); addPatientIndex++)
			{

				Patient patient1 = (Patient) addedPatients.get(addPatientIndex);
				int patient2Num = (Integer) deletedPatients.get(deletePatIndex);
				if(patient1.getPatientNo() == patient2Num)
				{
					addedPatientsUpdated.remove(patient1);
				}
			}
		}

		return addedPatientsUpdated;
	}
	
	
	/**
	 * Returns the array with no duplicates
	 */
	public static ArrayList  checkHistoryArrayForDuplicates(ArrayList deletedHist, ArrayList addedHists)
	{
		ArrayList addHistUpdate = addedHists;
		
		for(int deleteHisIndex=0; deleteHisIndex < deletedHist.size(); deleteHisIndex++)
		{

			for(int addHistIndex=0; addHistIndex < addedHists.size(); addHistIndex++)
			{

				History hist1 = (History) addedHists.get(addHistIndex);
				int hist2Num = (Integer) deletedHist.get(deleteHisIndex);
				if(hist1.getHistID() == hist2Num)
				{
					addHistUpdate.remove(hist1);
				}
			}
		}
				
		return addHistUpdate;
	}
	
	
	
	/**
	 * Returns the invoices to be added with no duplicates
	 */
	public static ArrayList checkInvoiceArrayForDuplicates(ArrayList deletedInvoice, ArrayList addedInvoice)
	{
		ArrayList addInvoicesUpdate = addedInvoice;
		
		for(int deleteInvocieIndex=0; deleteInvocieIndex < deletedInvoice.size(); deleteInvocieIndex++)
		{

			for(int addInvoiceIndex=0; addInvoiceIndex < addedInvoice.size(); addInvoiceIndex++)
			{

				Invoice invocie = (Invoice) addedInvoice.get(addInvoiceIndex);
				int invoiceNum = (Integer) deletedInvoice.get(deleteInvocieIndex);
				if(invocie.getInvoiceNum() == invoiceNum)
				{
					addInvoicesUpdate.remove(invocie);
				}
			}
		}
		
		
		return addInvoicesUpdate;
	}
	
	/**
	 * Returns the procedures to be added with no duplicates
	 */
	public static ArrayList checkInvoiceProceduresForDuplicates(ArrayList deletedInPros, ArrayList addedInvPros)
	{
		ArrayList updateInvoiceProAdd = addedInvPros;
		
		for(int deleteInvoiceProIndex=0; deleteInvoiceProIndex < deletedInPros.size(); deleteInvoiceProIndex++)
		{

			for(int addInvoiceProIndex=0; addInvoiceProIndex < addedInvPros.size(); addInvoiceProIndex++)
			{

				Procedure proc  = (Procedure) addedInvPros.get(addInvoiceProIndex);
				int invoiceProc = (Integer) deletedInPros.get(deleteInvoiceProIndex);
				if(proc.getProcNum() == invoiceProc)
				{
					updateInvoiceProAdd.remove(proc);
				}
			}
		}
		
		return addedInvPros;
	}
	

}
