import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

//  Just an FSM, this has nothing to do with RBN, the conversion is done in a helper class
//  This is the class for a Moore FSM
public class FSM {

	ArrayList<State> states;
	int numStates;
	public FSM(int numStates, int numInputs)
	{
		this.states = new ArrayList<State>();
		this.numStates = numStates;
		this.generateStates(numInputs);
	}
	
	public void generateStates(int numInputs)
	{
		for(int i = 0; i < this.numStates; i++)
		{
			states.add(new State(numInputs));
			
		}
	}
	
	//  Write state transition table to a file
	public void writeToFile(String fileName)
	{
		PrintWriter writer;
		try {
				writer = new PrintWriter(fileName, "UTF-8");
				for(int i = 0; i < states.size(); i++)
				{
					writer.print(Integer.toString(i) + ","); // Write the state
					writer.print(Integer.toString(states.get(i).nextStateArray[0]) + ","); // Write the 0 input state transition
					writer.print(Integer.toString(states.get(i).nextStateArray[1]) + ","); // Write the 1 input state transition
					writer.print(Integer.toString(states.get(i).currentStateOutput) + ","); // Write the current state output
					writer.print("\n");
				}

				writer.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
