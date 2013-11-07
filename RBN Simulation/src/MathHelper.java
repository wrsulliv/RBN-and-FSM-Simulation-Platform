
public class MathHelper {

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
}
