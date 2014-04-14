import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public class IrreducibleRBNManager {
	String pathToIrreducibleFolder; //  Relative path from the workspace which holds the irreducible networks pulled randomly
	String pathToWorkspace; // Holds the absolute path to the current workspace
	CustomRandom randomGenerator; //  Generates random numbers in a reliably uniform distribution
	
	public IrreducibleRBNManager(String pathToIrreducibleFolder, String pathToWorkspace, CustomRandom randomGenerator)
	{
		this.pathToIrreducibleFolder = pathToIrreducibleFolder;
		this.pathToWorkspace = pathToWorkspace;
		this.randomGenerator = randomGenerator;
		
	}
	
	//  Regenerate RBNs of certain sizes and export
	public void BatchIrreducibleExport(int lowL, int highL, int lowN, int highN, int lowK, int highK, int count)
	{
		for(int l = lowL; l <= highL; l++)
		{
			for(int n = lowN; n <= highN; n++)
			{
				for(int k = lowK; k <= highK; k++)
				{
					ExportIrreducibleRBNs(k, n,l,count);
					
				}
			}	
			
		}
		
	}
//  Export Irreducible RBNs until the count is reached.
	public void ExportIrreducibleRBNs(int K_avg, int N, int L, int count)
	{
		//  Create an arrays of irreducible networks
		ArrayList<RBN> rbnList = new ArrayList<RBN>();
		int total = 0;
		while(true)
		{
			RBN rbn = new RBN(K_avg, N, L, randomGenerator);
			if(RBN_FSM_Helper.generateFSMFromRBN(rbn).isIrreducibleViaAlgorithm()) //  This test should work for all probOfZero > 0
			{
				rbnList.add(rbn);
			}
			
			total++;
			if(rbnList.size() == count)
			{
				try {
					
					String filePath = SerializedPath_IrreducibleFromInputParams(K_avg, N, L, count);
					File yourFile = new File(filePath);
					if(!yourFile.exists()) {
					    yourFile.createNewFile();
					} 

					//  Write the array of arrays as a serialized object
			        FileOutputStream fos = new FileOutputStream(filePath);
			        ObjectOutputStream oos = new ObjectOutputStream (fos);
			        oos.writeObject (rbnList);
			        oos.close ();
			        System.out.println(Integer.toString(total) + " :: " + "K_" +Integer.toString(K_avg) + "N_" + Integer.toString(N) + "L_" + Integer.toString(L) + "Count_" + Integer.toString(count) + ".ser");
			        return;
			    } catch ( Exception ex ) {
			        ex.printStackTrace ();
			        return;
			    }
			}
		}
		
		
	}
	
	private String SerializedPath_IrreducibleFromInputParams(int K_avg, int N, int L, int count)
	{
		 return pathToWorkspace + pathToIrreducibleFolder + "K_" +Integer.toString(K_avg) + "N_" + Integer.toString(N) + "L_" + Integer.toString(L) + "Count_" + Integer.toString(count) + ".ser";
	}
	
	
	//  Import Irreducible RBNs into an ArrayList
	public ArrayList<RBN> ImportIrreducibleRBNs(int K_avg, int N, int L, int count) 
	{
		// Create an arrays of irreducible networks
		ArrayList<RBN> rbnList = new ArrayList<RBN>();

		try {

			FileInputStream fileIn = new FileInputStream(
					SerializedPath_IrreducibleFromInputParams(K_avg, N, L,
							count));
			ObjectInputStream in = new ObjectInputStream(fileIn);
			rbnList = (ArrayList<RBN>) in.readObject();
			in.close();
			fileIn.close();
			return rbnList;

		} catch (ClassNotFoundException ex) {
			System.out.println("Cannot perform input. Class not found.");
			return null;
		} catch (IOException ex) {
			System.out.println("Cannot perform input.");
			return null;
		}

	}
	
}

