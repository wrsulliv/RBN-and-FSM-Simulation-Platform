
public class State {


	public int[] nextStateArray; // The next state given that the index is the input to the current state
	public int currentStateOutput; // The current output given the current state of the network
	public State(int numInputs)
	{
		nextStateArray = new int[(int)Math.pow(2,numInputs)];
		
	}
	
}
