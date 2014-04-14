
public class ShannonSource implements InformationSource{

	String information = "";
	ShannonSource(int inputLength, float chanceOf1)
	{
		
		for (int i = 0; i < inputLength; i++)
		{
			
			if(Math.random() <= chanceOf1)
			{
				information += "1";
			}
			else
			{
				information += "0";
			}
		}
	}
	public String getCurrentString()
	{
		return information;
		
	}
}
