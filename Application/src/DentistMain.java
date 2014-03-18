//John Murphy R00059277

//Fix reports


import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class DentistMain {
		
	public static void main (String [] args)
	{
		PatientList patList = new PatientList();
		
		ProcedureStorage.getProceduresFromFile(); //Loads the main procedures list
		
		int choice  = saveChoiceLoad();	
		
		switch(choice)
		{
			case 0:
				loadDemoData(patList);
				break;
				
			case 1:
				SerializeLoadAndSave load = new SerializeLoadAndSave();
				patList = load.load();
				break;
				
			case 2:
				Database db = new Database();
				patList = db.loadFromDB();
				break;
		}
				
				
		//Make the GUI and the sections to go with it
		Model 	methods = new Model(patList);
		View 	GUI     = new View();
		Control con  	= new Control(methods, GUI, patList, choice);
		
	}
	
	
	
	/**
	 * Loads dummy data
	 * @param patList 
	 */
	public static void loadDemoData(PatientList patList)
	{
		//Set up a date
				Date todaysDate = new Date();
				Date lastYearDate = new Date();
				lastYearDate.setYear(todaysDate.getYear()-1);

				//Add some stuff to the list
				Patient patientLF = new Patient(0, "Little Finger", "Kings landing", "000111");
				Patient patientPB = new Patient(1, "Peter Baylish", "The vale", "56556565");
				Patient patientSt = new Patient(2, "Ned Stark", "Winterfell", "00000000000");
				Patient patientN  = new Patient(3, "nub man", "nubbington", "123456789");
				Patient patientD  = new Patient(4, "Dude man", "Over there", "987654321");

				//Make some history for the characters
				History histLF1  = new History(1, "something", todaysDate, "Cream");
				History histLF2  = new History(2, "Flu", lastYearDate, "Antibiotics");
				History histLF3  = new History(3, "bad hair day", todaysDate, "Hair spray");
				History histLF4  = new History(4, "Leg fell off", todaysDate, "Salt");
				History histPB1  = new History(1, "arm fell off", todaysDate, "stitch");

				//Add things to the patients history list
				patientLF.p_History.add(histLF1);
				patientLF.p_History.add(histLF2);
				patientLF.p_History.add(histLF3);
				patientLF.p_History.add(histLF4);
				patientPB.p_History.add(histPB1);


				//Add patients to the patient list
				patList.add(patientLF);
				patList.add(patientPB); 
				patList.add(patientSt);
				patList.add(patientN);
				patList.add(patientD);



				//Make some invoices
				Invoice invoiceLf1  = new Invoice(1, "tooth drilled", todaysDate, false);
				Invoice invoiceLf2  = new Invoice(2, "hammer to teeth", todaysDate, false);
				invoiceLf1.addProcedureViaName("some sort of tooth surgery");
				patientLF.p_Invoice.add(invoiceLf1); //Add the invoice to the patient
				patientLF.p_Invoice.add(invoiceLf2);
	}
	
	
	/**
	 * The choice about saving will be got from here
	 * 0 = non-persistent, 1 = Serialised, 2 = Database
	 */
	public static int saveChoiceLoad()
	{
		try {
	        // Set System L&F
	        UIManager.setLookAndFeel(
	            UIManager.getSystemLookAndFeelClassName());
	    }
		catch (Exception e) {
			
		}
		
		JPanel panel = new JPanel();
				
		Object[] saveOptions = {"Non-persistent", "Serialised", "Database"};
		String Stringchoice = (String)JOptionPane.showInputDialog(panel,"Select a saving option, if none is selected it will default to non-persistent",null,
													   JOptionPane.PLAIN_MESSAGE, null, saveOptions, "Non-persistent");
		if(Stringchoice.equals(null))
		{
			return 0;
		}		
		else if(Stringchoice.equals("Non-persistent"))
		{
			return 0;
		}
		else if(Stringchoice.equals("Serialised"))
		{
			return 1;
		}
		else if(Stringchoice.equals("Database"))
		{
			return 2;
		}
		else
		{
			return 0;
		}
		
	}

}
