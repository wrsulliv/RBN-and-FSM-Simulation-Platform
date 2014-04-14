

//  In this program a discrete variable is defined as having a type, start, end, and increment or multiple
public class SweepingDiscreteVariable 
{

	public DiscreteVariableType type;
	public int start;
	public int end;
	public int increment;
	public int multiple;
	public boolean useIncrement = false; //  Tell whether to use the multiple or the increment
	public SweepingDiscreteVariable(DiscreteVariableType type, int start, int end, int increment, int multiple, boolean useIncrement)
	{
		this.type = type;
		this.start = start;
		this.end = end;
		this.multiple = multiple;
		this.increment = increment;
		this.useIncrement = useIncrement;

	}
	
	//  Increment / Multiply depending on setup
	public int next(int i)
	{
		if(useIncrement)
		{
			return i + increment;
		}
		else
		{
			return i*multiple;
		}
		
	}
	
	
}
