import java.io.Serializable;
import java.util.Date;


public class History implements Serializable{
	private int      histID;
	private String   conditionName;
	private Date     dateOccured;
	private String   medication;
	
	
	public History() {}
	
	public History(int histID, String conditionName, Date dateOccured, String medication) 
	{
		this.histID = histID;
		this.conditionName = conditionName;
		this.dateOccured = dateOccured;
		this.medication = medication;
	}
	
	//Set
	public void setHistID(int histID)
	{
		this.histID = histID;
	}
	
	public void setConditionName(String conditionName)
	{
		this.conditionName = conditionName;
	}
	
	public void setDateOccured(Date dateOccured)
	{
		this.dateOccured = dateOccured;
	}
	
	public void setMediaction(String medication)
	{
		this.medication = medication;
	}
	
	//Get
	public int getHistID()
	{
		return histID;
	}
	
	public String getConditionName()
	{
		return conditionName;
	}
	
	public Date getDateOccured()
	{
		return dateOccured;
	}
	
	public String getMedication()
	{
		return medication;
	}
	
	public String toString()
	{
		return "HistoryID: " + getHistID() + 
				"\nCondition name: " + getConditionName() +
				"\nDate occured: " + getDateOccured() +
				"\nMedication: " + getMedication() + "\n\n";
	}
	
	public void print()
	{
		System.out.println( toString() );
	}
	
	
	
	
	
}
