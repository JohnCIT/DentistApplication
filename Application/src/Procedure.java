import java.io.Serializable;


public class Procedure implements Serializable{
	
	private int       procNum;
	private String    procName;
	private double    procCost;
	
	
	public Procedure() {}
	
	public Procedure(int procNum, String procName, double procCost)
	{
		this.procNum  = procNum;
		this.procName = procName;
		this.procCost = procCost;
	}
	
	
	//Set
	public void setProcNum(int procNum)
	{
		this.procNum = procNum;
	}
	
	public void setProcName(String procName)
	{
		this.procName = procName;
	}
	
	public void setProcCost(double procCost)
	{
		this.procCost = procCost;
	}
	
	//Get
	public int getProcNum()
	{
		return procNum;				
	}
	
	public String getProcName()
	{
		return procName;
	}
	
	public double getProcCost()
	{
		return procCost;
	}
	
	public String toString()
	{
		return "Procedure number: " + getProcNum() + 
				"\nProcdure name: " + getProcName() + 
				"\nProcedureCost: " + getProcCost();
	}
	
	public void print()
	{
		System.out.println( toString() );
	}

}
