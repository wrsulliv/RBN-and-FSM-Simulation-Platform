import java.util.ArrayList;


public class Node {

	public ArrayList<Integer> inputNodes; 
	public int[] logicFunction;
	public int currentState; //  The output of the node at the current state
	public int nextState; //  The output of the node at the next state
	
	public Node()
	{
		inputNodes = new ArrayList<Integer>();
		
	}
	
	public boolean hasInputNode(int inputNodeIndex)
	{
		for(int i = 0; i < inputNodes.size(); i++)
		{
			
			if(inputNodes.get(i) == inputNodeIndex)
			{
				return true;
				
			}
		}
		
		return false;
		
	}
	
	public void addInputNode(int inputNodeIndex)
	{
		
		inputNodes.add(inputNodeIndex);
	}
	
	public void commitNextState()
	{
		this.currentState = this.nextState;
		
	}
	
}