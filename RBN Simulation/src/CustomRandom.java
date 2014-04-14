import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Random;


//  A wrapper around different random number generators which can be exchanged to make coding easier
public class CustomRandom implements Serializable{

	Random randomGenerator;
	long numbersGenerated = 0; //  Used to make a new generator when too many numbers are generated
	long generationsBeforeReset = 2^48; // This taken from here: http://www.math.utah.edu/~beebe/java/random/README
	public CustomRandom()
	{
		
		randomGenerator = new Random();
	}
	
	public int nextInt(int maxValueExclusive)
	{
		if(numbersGenerated >= generationsBeforeReset)
		{
			
			randomGenerator = new Random(); //  Create a new random object
			numbersGenerated = 0;
		}
		
		numbersGenerated++; //  Increase the numbers generated count because we're generating another number.
		return randomGenerator.nextInt(maxValueExclusive);
	}
	
	public void writeHistogramToCSV(String filePath, int maxHistogramValue, int numberOfSamples)
	{
		
		//  Generate the histogram
		int[] histogramBuckets = new int[maxHistogramValue+1];
		
		for(int i = 0; i < numberOfSamples; i++)
		{
			histogramBuckets[this.nextInt(maxHistogramValue+1)]++;
			
		}
		
		PrintWriter writer;
		try {
				writer = new PrintWriter(new FileWriter(filePath, false));
				for(int i = 0; i < maxHistogramValue+1; i++)
				{
					writer.println(Integer.toString(i) + ", "+ Double.toString(histogramBuckets[i]));
				}
				writer.close();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
}
