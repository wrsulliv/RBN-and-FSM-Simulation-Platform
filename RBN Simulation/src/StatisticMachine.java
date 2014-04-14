import java.util.ArrayList;


//  Used to easily average a set of numbers
public class StatisticMachine {
	
	public ArrayList<Double> numbers;
	public StatisticMachine()
	{
		
		this.numbers = new ArrayList<Double>();
	}
	
	public void add(double number)
	{
		
		this.numbers.add(number);
	}
	
	public void clear()
	{
		this.numbers = new ArrayList<Double>();
		
	}
	
	public double getAverage()
	{
		double total = 0;
		for(int i = 0; i < this.numbers.size(); i++)
		{
			total += this.numbers.get(i);		
		}
		
		return total / (double)this.numbers.size();
	}

}
