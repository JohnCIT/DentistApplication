import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Loads the procedures into the list
 *
 */
public class ProcedureStorage {

	static ArrayList <Procedure> procedureList = new ArrayList<Procedure>();
	
	public static ArrayList<Procedure> getProcedureList()
	{	
		return procedureList;//Return the list
	}
	
	/**
	 * Reads the file and populates the array list
	 */
	public static void getProceduresFromFile()
	{
		
		try{
			//Create the objects to read the file
			File procedureLoad = new File("procedures.txt");
			Scanner readProcedures = new Scanner(procedureLoad);
			
			
			
			while(readProcedures.hasNext() )
			{
				int idNum = readProcedures.nextInt();
				String line = readProcedures.nextLine();//Read the line of the file
				String procName  = getProcName(line);
				double cost = getCost(line);
				
				Procedure procedure = new Procedure(idNum, procName, cost);
				
				procedureList.add(procedure);
			}
			
			readProcedures.close();//Close the file
			
		}
		catch (Exception e) 
		{
			System.out.println("Procedure File failed to load");//Alert the user with about the problem
		}
		
	}
	
	public static void saveProcedure()
	{
		String stringToSave = "";
				
		for(int i=0; i<procedureList.size(); i++)
		{
			Procedure proc = procedureList.get(i);
			stringToSave += proc.getProcNum() + " " + proc.getProcName() + ">" +  proc.getProcCost() + "\n";			
		}
		
		try
		{
			//Open the file.
			PrintWriter procedureSave = new PrintWriter("procedures.txt");
			
			procedureSave.print(stringToSave);

			//Closes the file
			procedureSave.close();
		}
		catch(IOException e)
		{
			System.out.println("File does not exist");
		}
	}
	
	
	/**
	 * Gets the procedure name
	 * @param line
	 * @return
	 */
	public static String getProcName(String line)
	{
		String name = "";
		
		int arrow = line.indexOf(">");
		
		name = line.substring(1, arrow);
		return name;
	}
	
	
	/**
	 * Gets the double from the line passed into it
	 * @param line
	 * @return	The cost of the procedure
	 */
	public static double getCost(String line)
	{
		double cost = 0.00;
		
		int arrow = line.indexOf(">");
		cost = Double.parseDouble(line.substring(arrow+1) );
	
		return cost;
	}
	
}
