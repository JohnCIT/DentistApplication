import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;


public class Invoice implements Serializable{
	private int     invoiceNum;
	private double  invoiceAmt;
	private Date    invoiceDate;
	private boolean invoiceIsPaid;
	
	ArrayList <Procedure> procList;
	ArrayList <Procedure> allPros = ProcedureStorage.getProcedureList();
	
	public Invoice() {}
	
	public Invoice(int invoiceNum, String procName, Date invoiceDate, boolean invoiceIspaid)
	{
		this.invoiceNum = invoiceNum;
		setInvoiceAmt(procName);
		this.invoiceDate = invoiceDate;
		this.invoiceIsPaid = invoiceIspaid;
		procList = new ArrayList<Procedure>();
	}


	//Set
	public void setInvoiceNum(int invoiceNum) 
	{
		this.invoiceNum = invoiceNum;
	}

	
	private void setInvoiceAmt(String procName) 
	{
					
		for(int i=0; i<allPros.size(); i++)
		{
			Procedure pro = allPros.get(i);
										
			if(pro.getProcName().equals(procName))
			{
				this.invoiceAmt = pro.getProcCost();
			}
		}
	}


	public void setInvoiceDate(Date invoiceDate) 
	{
		this.invoiceDate = invoiceDate;
	}


	public void setInvoiceIsPaid(boolean invoiceIsPaid) 
	{
		this.invoiceIsPaid = invoiceIsPaid;
	}
	
	public void addProcedureToList(Procedure procedure)
	{		
		procList.add(procedure);
	}
	
	public void addProcedureViaName(String name)
	{
		for(int i=0; i<allPros.size(); i++)
		{
			Procedure procedure = allPros.get(i);
		
			if(procedure.getProcName().equals(name))
			{
				procList.add(procedure);
			}
		}
	}
	
	//Get
	public int getInvoiceNum() 
	{
		return invoiceNum;
	}
	
	public double getInvoiceAmt() 
	{
		double invoiceAmt = 0.0;
		
		
		for(int i=0; i<procList.size(); i++)
		{
			Procedure proc = procList.get(i);
			invoiceAmt += proc.getProcCost();			
		}
		
		return invoiceAmt;
	}
	
	public Date getInvoiceDate() 
	{
		return invoiceDate;
	}
	
	public boolean getInvoiceIsPaid() 
	{
		return invoiceIsPaid;
	}
	
	public ArrayList<Procedure> getProcList()
	{
		return procList;
	}
	
	public String toString()
	{
		return "InvoiceNo: " + getInvoiceNum() + 
				"\nInvoice amount: " + getInvoiceAmt() + 
				"\nInvoiceDate " + getInvoiceDate() + 
				"\nPaid: " +  getInvoiceIsPaid();  
	}
	
	public void print()
	{
		System.out.println( toString() );
	}


	
	
	

}
