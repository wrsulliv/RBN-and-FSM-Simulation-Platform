import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;

import javax.swing.JFrame;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.SpringLayout;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;


public class RBN_FSM_Helper {

	//  Return a boolean array of the current state of the RBN (the states of the reservoir nodes from 0 to N-1)
	public static int[] getFSMStateFromRBN(RBN rbn)
	{
		int[] boolStateArray = new int[rbn.nodes.size() - 2];
		for(int i = 2; i < rbn.nodes.size(); i++) // 2 of the nodes are for input and output
		{
			boolStateArray[i] = rbn.nodes.get(i).currentState;
			
		}
		
		return boolStateArray;
		
	}
	
	public static FSM generateFSMFromRBN(RBN rbn)
	{	
		//  First, get the number of nodes in the RBN
		int numNodes = rbn.nodes.size() - 2; 
		
		//  The number of states in the FSM is 2^(# of nodes in the RBN)
		//  This means the states in the FSM range from when the nodes in the RBN are all 0s to all 1s
		int numStates = (int)Math.pow(2, numNodes);
		 
		//  Create the FSM Object, this will add the states to the FSM
		FSM fsm = new FSM(numStates, 1);
		
		//  Start looping through each state and testing what the next state is given a 0 or 1 input
		for(int i = 0; i < numStates; i++)
		{
			//  First determine what output is created for the current state
			rbn.reinitialize(i, 0);
			int currentStateOutput = rbn.generateCurrentStateOutput();
			
			//  Test input = 0
			rbn.reinitialize(i, 0);
			rbn.nextState(0, "/Users/willimac/Desktop/STATEFILE.csv"); // Doesn't matter what the next input is because we reset it
			int zero_nextState = getCurrentRBNState(rbn);
			
			//  Test input = 1
			rbn.reinitialize(i, 1);
			rbn.nextState(1, "/Users/willimac/Desktop/STATEFILE.csv"); //  Doesn't matter what the next input is because we're done testing this state
			int one_nextState = getCurrentRBNState(rbn);
			
			//  Submit the states and current state output to the FSM state object
			fsm.states.get(i).nextStateArray[0] = zero_nextState;
			fsm.states.get(i).nextStateArray[1] = one_nextState;
			fsm.states.get(i).currentStateOutput = currentStateOutput;
		}
		
		return fsm;
		
		
		
	}

	//  Generate a decimal number which represents the current FSM state of the RBN network
	private static int getCurrentRBNState(RBN rbn)
	{
		
		//  Make a boolean array to hold the RBN node values
		int[] stateArray = new int[rbn.nodes.size() - 2];
		
		//  Loop through each node and record in the boolean array
		for(int i = 2; i < rbn.nodes.size(); i++) // 2 of the nodes are for input and output
		{
			//  Populate the state array with the RBN node values
			stateArray[i - 2] = rbn.nodes.get(i).currentState;
		}
		
		//  Convert the state array to a decimal number
		int decimalState = MathHelper.convertBinaryArrayToDec(stateArray);
		
		//  Return the decimal state
		return decimalState;
		
	}
	
	public static void showRBNVisualization(RBN rbn)
	{
		
		// Graph<V, E> where V is the type of the vertices 
		 // and E is the type of the edges
		 Graph<Integer, String> g = new DirectedSparseMultigraph<Integer, String>();
		 
		 // Add as many vertices as there are states
		 for(int i = 0; i < rbn.nodes.size(); i++)
		 {
			 
			 g.addVertex(i);
		 }

		 
		 //  Loop through and add edges to the graph
		 for(int i = 0; i < rbn.nodes.size(); i++)
		 {
			 for(int inputNode : rbn.nodes.get(i).inputNodes)
			 {
				 
				 g.addEdge(Integer.toString(i) + Integer.toString(inputNode), inputNode, i, EdgeType.DIRECTED);
			 }
			 	 
		 }

		 
		 //  Set style properties for the graph
		 Transformer<Integer,Paint> vertexPaint = new Transformer<Integer,Paint>() {
			 public Paint transform(Integer i) {
			 return Color.blue;
			 }
			 }; 

		 
		 
		// The Layout<V, E> is parameterized by the vertex and edge types
		Layout<Integer, String> layout = new SpringLayout<Integer, String>(g);
		layout.setSize(new Dimension(750,750)); // sets the initial size of the space
		// The BasicVisualizationServer<V,E> is parameterized by the edge types
		VisualizationViewer<Integer,String> vv = 
				 new VisualizationViewer<Integer,String>(layout);


		vv.setPreferredSize(new Dimension(750,750)); //Sets the viewing area size
		vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);

		vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
		 vv.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR); 

		 
		JFrame frame = new JFrame("Simple Graph View");
		DefaultModalGraphMouse gm = new DefaultModalGraphMouse();
		 gm.setMode(ModalGraphMouse.Mode.TRANSFORMING);
		 vv.setGraphMouse(gm); 
			vv.setBackground(Color.WHITE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.getContentPane().add(vv);
		frame.pack();
		frame.setVisible(true);
	}
	
}
