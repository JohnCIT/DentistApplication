import java.io.Serializable;
import java.util.ArrayList;



public class Patient implements Serializable {

	private int    patientNo;
	private String patientName;
	private String patientAddress;
	private String patientPhone;
	
	ArrayList<History> p_History;
	ArrayList<Invoice> p_Invoice;
	
	//Empty constructor
	public Patient() {}
	
	public Patient(int patientNo, String name, String address, String phone)
	{
		this.patientNo      = patientNo;
		setName(name);
		this.patientAddress = address;
		this.patientPhone   = phone; 
		
		p_History = new ArrayList();
		p_Invoice = new ArrayList();
	}
	
	//Set
	public void setPatientNo(int paitentNo)
	{
		this.patientNo = paitentNo;
	}
	
	public void setName(String name)
	{
		if(name.equals("") ) //Checks if the name is empty, This stops a empty string being displayed on the GUI
		{
			this.patientName = "New patient";
		}
		else
		{
			this.patientName = name;
		}
		
	}
	
	public void setAddress(String address)
	{
		this.patientAddress = address;
	}
	
	public void setPhone(String phone)
	{
		this.patientPhone = phone;
	}
	
	
	//Get
	public int getPatientNo()
	{
		return patientNo;
	}
	
	public String getName()
	{
		return patientName;
	}
	
	public String getAddress()
	{
		return patientAddress;
	}
	
	public String getPhone()
	{
		return patientPhone;
	}
	
	public ArrayList getPatientHistoryList()
	{
		return p_History;
	}
	
	public ArrayList getPatientInvoiceList()
	{
		return p_Invoice;
	}
	
	//To string
	public String toString()
	{
		return "\nPatientNo" + getPatientNo() +  
				"\nName: " + getName() + 
				"\nAddress: " + getAddress() + 
				"\nPhone: " + getPhone();
	}
	
	public void print()
	{
		System.out.println( toString() );
	}
	
	
	
	
}
