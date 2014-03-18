import java.util.ArrayList;


public class AdditionalRemoves {
	
	
	/**
	 * When the patient is deleted everything to do with that patient should be deleted
	 */
	public static void patientRemove(Patient patient, ArrayList histToBeDeleted, ArrayList invToDeleted, ArrayList procsToBeDeleted)
	{
		for(int i=0; i<patient.p_History.size(); i++)
		{
			History hist = patient.p_History.get(i);
			
			histToBeDeleted.add(hist.getHistID());					
		}
		
		for(int i=0; i<patient.p_Invoice.size(); i++)
		{
			Invoice inv = patient.p_Invoice.get(i);
			
			for(int invoiceIndex=0; invoiceIndex<inv.procList.size(); invoiceIndex++)
			{
				Procedure proc = inv.procList.get(invoiceIndex);
				procsToBeDeleted.add(proc.getProcNum());
			}
			
			invToDeleted.add(inv.getInvoiceNum());
			
		}
		
	}
	
	
	
	/**
	 * When the invoice is deleted mark its procedures for remove
	 */
	public static void invoiceRemove(Invoice invoice, ArrayList invProcsDelete)
	{
		for(int i=0; i<invoice.procList.size(); i++)
		{
			Procedure proc = invoice.procList.get(i);
			invProcsDelete.add(proc.getProcNum());			
		}
	}

}
