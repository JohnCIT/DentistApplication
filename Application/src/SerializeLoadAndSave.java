import java.util.ArrayList;
import java.io.*;

public class SerializeLoadAndSave {


	public void save(ArrayList<Patient> patList)
	{
				try
			      {
			         FileOutputStream fileOut = new FileOutputStream("Serialize.txt");
			         ObjectOutputStream out   = new ObjectOutputStream(fileOut);
			         out.writeObject(patList);
			         out.close();
			         fileOut.close();
			      }catch(IOException i)
			      {
			          i.printStackTrace();
			      }
	}
	
	public PatientList load()
	{
		
		PatientList patList = new PatientList();
		try
	      {
	         FileInputStream fileIn = new FileInputStream("Serialize.txt");
	         ObjectInputStream in   = new ObjectInputStream(fileIn);
	         patList                =  (PatientList) in.readObject();
	         in.close();
	         fileIn.close();
	      }catch(IOException i)
	      {
	         i.printStackTrace();
	       
	      }catch(ClassNotFoundException c)
	      {
	         c.printStackTrace();
	       
	      }
		
		return patList;
	}

}
