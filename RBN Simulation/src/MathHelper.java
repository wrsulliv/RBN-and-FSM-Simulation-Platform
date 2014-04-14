import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class MathHelper {

	//  Converts a string of 0's and 1's into a an integer array
	public static int[] convertStringToIntArray(String str)
	{
		int[] array = new int[str.length()];
		for(int i = 0; i < str.length(); i++)
		{
			
			array[i] = Integer.parseInt(Character.toString(str.charAt(i)));
		}
		
		return array;
	}
	public static int[] convertDecToBinArray(int decimal, int digits)
	{
		int[] binArray; //  The binary array where the 0th index is the least significant binary digit
		binArray = new int[digits]; // Make an array with the specified number of binary digits
		int currentIndex = 0;
		while(decimal > 0)
		{
			int remainder = decimal % 2;
			decimal = decimal / 2;
			if(remainder == 1) binArray[currentIndex] = 1;
			currentIndex += 1;
		}
		return binArray;
	}
	
	public static int convertBinaryArrayToDec(int[] binArray)
	{
		int decimal = 0;
		int multiplier = 1;
		for(int i = 0; i < binArray.length; i++)
		{
			//  The 0th index is the least significant digit so...
			decimal += multiplier * binArray[i];
			multiplier = multiplier *2;
			
		}
		return decimal;
	}
	
	//  Splice a CSV file to an array where each line is a new entry in the array list, and the comma separated values in a line are entries
	//  in the String[] array.
	public static ArrayList<String[]> spliceCSVToArrayList(String csvFile)
	{
		
		BufferedReader br = null;
		String line = "";
	 
		ArrayList<String[]> csvImport = new ArrayList<String[]>();
		try {
	 
			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) 
			{
			    // use comma as separator
				String[] csvLineArray = line.split(",");
				csvImport.add(csvLineArray);
			}
	 
			
		//  Error handling and file closing stuff
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return csvImport;
	}
}
