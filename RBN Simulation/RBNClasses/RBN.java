import java.util.ArrayList;
import java.util.Random;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;
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

public class RBN implements Serializable {

	public int N; //  The number of nodes
	public int L; // Number of input nodes
	public int K_avg; // Average connectivity
	ArrayList<Node> nodes;
	ArrayList<Integer> inputConnections;
	int total_connections;
	CustomRandom randomGenerator;
	public RBN(int K_avg, int N, int L, CustomRandom randomGenerator)
	{

		this.N = N;
		this.L = L;
		this.nodes = new ArrayList<Node>();
		this.total_connections = K_avg * N;
		this.randomGenerator = randomGenerator;
		//int[] binArray = MathHelper.convertDecToBinArray(13, 10); // TESTING TODO:  make a test suite
		//int dec = MathHelper.convertBinaryArrayToDec(binArray); // TESTING TODO:  make a test suite
		generateNodes();
		setupInputConnections();
		connectNodes();
		setupNodeLogicFunctions();
		
	}
	

	
	
	//  Get an Integer array representing the state of the internal nodes
	public int[] getInternalState()
	{
		int[] networkState = new int[this.nodes.size() - 2];
		for(int i = 2; i < this.nodes.size(); i++)
		{
			
			networkState[i - 2] = this.nodes.get(i).currentState;
		}
		
		return networkState;
	}
	
	//  TODO:  Make the classes clonable so we don't have to deal with int arrays
	//  Returns the number of bits differing in the internal nodes of the two networks.  The networks must have the same number of nodes.
	public int getHammingDistance(RBN rbn1, RBN rbn2) throws HammingNodeCountException
	{
		if(!(rbn1.nodes.size() == rbn2.nodes.size()))
		{
			
			throw new HammingNodeCountException();
		}
		int difCount = 0;
		for(int i = 0; i < rbn1.nodes.size(); i++)
		{
			
			if(!(rbn1.nodes.get(i).currentState == rbn2.nodes.get(i).currentState))
			{
				difCount++;
			}
		}
		
		return difCount;
	}
	
	public static int getHammingDistance(int[] states1, int[] states2) throws HammingNodeCountException
	{
		if(!(states1.length == states2.length))
		{
			
			throw new HammingNodeCountException();
		}
		int difCount = 0;
		for(int i = 0; i < states1.length; i++)
		{
			
			if(!(states1[i] == states2[i]))
			{
				difCount++;
			}
		}
		
		return difCount;
	}
	
	//  Return the Perturbance Spreading of two differing input strings (Variables from Snyder's paper)
	//  This is a number between 0 and 1 which tells how far each node is on average from the other
	public static float calculatePerturbanceSpreading(RBN m, int[] uA, int[] uB)
	{
		//  Calculate the final state of each input string first
		m.RunSimulation(uA, "/Users/willimac/Desktop/uA_State.csv");
		int[] resultState_uA = m.getInternalState();
		m.RunSimulation(uB, "/Users/willimac/Desktop/uB_State.csv");
		int[] resultState_uB = m.getInternalState();
		
		//  Get the Hamming distance
		int hammingDistance;
		try {
			hammingDistance = getHammingDistance(resultState_uA, resultState_uB);
			
			//  Calculate the perturbation spreading
			float perturbance = (float)hammingDistance / (float)(m.nodes.size() - 2);
			
			return perturbance;
			
		} catch (HammingNodeCountException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1; //  Error
		}
		
		
		
	}
	
	
	public static float calculateSeparation(RBN m, int tau, int[] inputString)
	{
		//  Get the number of digits which should be different
		int numberOfDiffering = inputString.length - tau;
		
		//  Calculate the complimentary input string
		int[] newInputString = new int[inputString.length];
		for(int i = 0; i < inputString.length; i++)
		{
			if(i < numberOfDiffering)
			{
				newInputString[i] = Math.abs(inputString[i] - 1);
			}
			else
			{
				newInputString[i] = inputString[i];
			}
			
		}
		
		return calculatePerturbanceSpreading(m, inputString, newInputString);
	}
	
	public static float calculateFadingMemory(RBN m, int[] inputString)
	{
		return calculateSeparation(m, inputString.length - 1, inputString);
		
	}
	
	public static float calculateComputationalCapability(RBN m, int[] inputString, int tau)
	{
		float separation = calculateSeparation(m, tau, inputString);
		float fadingMemory = calculateFadingMemory(m, inputString);
		return separation - fadingMemory;
	}
	public void regenerateInternalNodes()
	{
		
		//  First make a mark of which nodes have input connections
		for(int i = 0; i < this.nodes.size(); i++)
		{
			if(this.nodes.get(0).hasInputNode(0))
			{
				this.nodes.get(i).inputNodes = new ArrayList<Integer>();
				this.nodes.get(i).inputNodes.add(0); //  Re-add the input node
			}
			else
			{
				this.nodes.get(i).inputNodes = new ArrayList<Integer>();
			}
		}
		
		connectNodes();
	}

	//  Runs the simulation for a certain number of iterations and outputs to a save file
	public void RunSimulation(int[] inputString, String saveFile)
	{
		
		this.reinitialize(0, inputString[0]);
		
		//  Loop through each input string item and determine the next state.
		for(int i = 0; i < inputString.length; i++)
		{
			//  Go to the next state
			this.nextState(inputString[i], saveFile);
		}
		
	}
	

	/**
	 * This method generates a BMP of the state space by going through each state transition with the given input string.
	 * 
	 * @param inputString A string of 0s and 1s which represent the input
	 * @param bmpPath A file path to the destination BMP
	 */
	public void generateStateSpaceMap(int[] inputString, String bmpPath)
	{
		
		this.reinitialize(0, inputString[0]);
		
		//  Create the actual image object.  The number of rows is 1 more than the number of inputs because of the initial state.
		BufferedImage bf = new BufferedImage(this.N, inputString.length, BufferedImage.TYPE_INT_RGB);
		
		//  Loop through each input string item and determine the next state.
		for(int i = 0; i < inputString.length; i++)
		{
		
			//  Update the bitmap
			for(int j = 0; j < N; j++)
			{

				bf.setRGB(j, i, this.nodes.get(j).currentState*(255|(255<<8)|(255<<16))); // White = 1, Black = 0
			}
			
			//  Go to the next state 
			this.nextState(inputString[i],"/Users/willimac/Desktop/STATEFILE.csv");
			
		}
		
		//  Save the bitmap
		File outputFile = new File(bmpPath);
		try {
			ImageIO.write(bf, "bmp", outputFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

	/**
	 * This method will generate a BMP on the fly by going through every state in the network using the given input string.  
	 * This method does not generate a state space BMP, but a node change BMP, and so the colors are red and green
	 * 
	 * @param inputString A string of 0s and 1s which represent the input
	 * @param bmpPath A file path to the destination BMP
	 */
	public void generateChangeMap(int[] inputString, String bmpPath)
	{
		
		this.reinitialize(0, inputString[0]);
		
		//  Create the actual image object.  The number of rows is 1 more than the number of inputs because of the initial state.
		BufferedImage bf = new BufferedImage(this.N, inputString.length, BufferedImage.TYPE_INT_RGB);
		
		//  Loop through each input string item and determine the next state.
		for(int i = 0; i < inputString.length; i++)
		{
			int previousState[] = new int[N]; //  Hold the current state
			
			//  Record the current state (Shows the last submitted input)
			for(int j = 0; j < N; j++)
			{
				previousState[j] = this.nodes.get(j).currentState;
			}
			
			//  Go to the next state 
			this.nextState(inputString[i],"/Users/willimac/Desktop/STATEFILE.csv");
				
			//  Update the bitmap
			for(int j = 0; j < N; j++)
			{
				if((this.nodes.get(j).currentState - previousState[j]) == 0)
				{
					bf.setRGB(j, i, (0|(0<<8)|(255<<16))); //  Red = same
				}
				else
				{
					bf.setRGB(j, i, (0|(255<<8)|(0<<16))); // Green = changed
				}

			}
			
			
			
		}
		
		//  Save the bitmap
		File outputFile = new File(bmpPath);
		try {
			ImageIO.write(bf, "bmp", outputFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	public RBN(String csvFile)
	{
	
		ArrayList<String[]> csvImport = MathHelper.spliceCSVToArrayList(csvFile);
		this.nodes = new ArrayList<Node>();
		this.N = csvImport.size() - 2; // Subtract 2 for the input / output nodes
		
		// Create the input and output nodes
		this.nodes.add(new Node());
		this.nodes.add(new Node());
		
		//  Create the reservoir nodes
		for (int i = 2; i < csvImport.size(); i++)
		{
			this.nodes.add(new Node());
			
			//  Add the input nodes
			for(int j = 1; j < csvImport.get(i).length - 1; j++) // Skip over the node ID and the boolean function
			{
				this.nodes.get(i).inputNodes.add(Integer.decode(csvImport.get(i)[j]));
				
			}
			
			//  Extract the boolean function from the CSV
			String functString = csvImport.get(i)[csvImport.get(i).length - 1]; // Get the function in the form of a binary string
			functString = functString.substring(1, functString.length() - 1);  // Remove the brackets
			
			//  Turn the string into a boolean array
			int[] boolArray = new int[functString.length()];
			for(int j = 0; j < functString.length(); j++)
			{
				//  [1000] is read left to right, so 1 maps to an input of {0,0} and the last 0 on the right maps to {1,1}
				boolArray[j] = Integer.decode(Character.toString(functString.charAt(j)));
				
			}
			
			// Add the function to the node
			this.nodes.get(i).logicFunction = boolArray;

			
		}
		
		
	}
	
	
	//  Setup the logic functions
	private void setupNodeLogicFunctions()
	{
		//  A function has to define what the output of a node will be given every possible state.  
		//  A 2 bit input will have these possible input combinations
		//  00|0000000011111111
		//  01|0000111100001111
		//  10|0011001100110011
		//  11|0101010101010101
		
		//  So, this function will choose 1 out of 2^2^K functions.  
		//  2^K = Number of input combinations
		//  2^2^K = Number of possible functions.  
		
		
		for(int i = 2; i < N + 2; i++)
		{
			int input_node_count = this.nodes.get(i).inputNodes.size();
			int num_input_combinations = (int)Math.pow(2,input_node_count);
			int num_functions = (int)Math.pow(2, (num_input_combinations));
			int rnd_function = this.rndGenerator(num_functions - 1); // Subtract 1 because all 0s counts as a function
			int[] functArray = MathHelper.convertDecToBinArray(rnd_function, num_input_combinations);
			this.nodes.get(i).logicFunction = functArray;
			
		}
	}

	
	//  Give L input connections randomly to the reservoir nodes
	private void setupInputConnections()
	{
		for(int i = 0; i < L; i++)
		{
			int randomNode = rndGenerator(N - 1) + 2;
			
			//  If the input node is already there then try again
			if(nodes.get(randomNode).hasInputNode(0))
			{
				i--;
			}
			else
			{
				nodes.get(randomNode).addInputNode(0);
			}
		}
	}
	
	//  Create an input, and N nodes with random Ki values
	private void generateNodes()
	{
		//  Generate input and output nodes, input = index 0, output = index 1
		nodes.add(new Node());
		nodes.add(new Node());
		 
		// Generate N different nodes
		for (int i = 0; i < N; i++)
		{
			Node n = new Node();
			nodes.add(n);
		}
		
	}
	
	//  Connect all nodes within the reservoir based on their Ki values
	private void connectNodes()
	{
		int remainingConnections = this.total_connections;
		int nodeIndex = 0; // Start at 0 because we will add 2 in the loop
		while(remainingConnections > 0)
		{
			nodeIndex += 1;
			remainingConnections -= 1;
			
			int inputNodeIndex = rndGenerator(N - 1) + 2; // Subtract 1 since we start at 0 index, add 2 because of the input / output nodes
			//  As long as the connection doesn't already exist, connect the node
			if (!(nodes.get((nodeIndex % this.N) + 2).hasInputNode(inputNodeIndex)))
			{
				nodes.get((nodeIndex % this.N) + 2).addInputNode(inputNodeIndex);	
			}
			else
			{
				remainingConnections += 1;  // Try a different connection with this same node because this one already existed
				nodeIndex -= 1;
			}	
		}
	}
	
	//  Gets the current state of the network based only on the node values in the reservoir
	public int generateCurrentStateOutput()
	{
		return 1; // TODO:  Actually make this work!
		
	}
	//  Write network state to file
	public void writeNetworkSateToFile(String fileName)
	{
		PrintWriter writer;
		try {
				writer = new PrintWriter(new FileWriter(fileName, true));
				for(int i = 0; i < N + 2; i++) // N+2 because of the input/output nodes
				{
					writer.print(Integer.toString(this.nodes.get(i).currentState) + " ");
				}
				writer.print("\n");
				writer.close();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	//  Write network to a file
	public void writeNetworkSetupToFile(String fileName)
	{
		PrintWriter writer;
		try {

			writer = new PrintWriter(fileName, "UTF-8");
			
			for(int i = 0; i < N + 2; i++) // N+2 because of the input/output nodes
			{
				//  Print a column with the nodes ID
				writer.print(Integer.toString(i) + ",");

				//  Print columns with the nodes input connections
				for(int j = 0; j < nodes.get(i).inputNodes.size(); j++)
				{
					writer.print(Integer.toString(nodes.get(i).inputNodes.get(j)) + ",");
				}
				
				//  Write the binary function to the csv file if the node is part of the reservoir
				if(i >= 2)
				{
					int[] binFunctArray = nodes.get(i).logicFunction;
					String binaryFunctString = "["; // Add brackets so excel will read this as text instead of a number (could have added anything)
					for(int j = 0; j < binFunctArray.length; j++) //  Start at the lower index which maps to 0, and write left to right.  So, left maps to 0, right maps to highest number
					{
						binaryFunctString = binaryFunctString + binFunctArray[j];
						
					}
					writer.print(binaryFunctString + "]");
				}
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
	
	//  Generate a random number between 0 and the maxValue both inclusive
	private int rndGenerator(int maxValue)
	{
		return this.randomGenerator.nextInt(maxValue + 1); //  Add 1 because the nextInt function is exclusive of the maxValue
	}
	
	//  Reinitialize the network given a decimal number which also acts as a binaryArray
	public void reinitialize(int decimalState, int input)
	{
		int[] stateArray = MathHelper.convertDecToBinArray(decimalState, N);
		for(int i = 2; i < N + 2; i++)
		{
			//  Reset each state based on the decimal number sent in
			this.nodes.get(i).currentState = stateArray[i - 2]; // Subtract 2 because of the input and output noes
			
			
		}
		
		//  Set the input to the desired input.  
		this.nodes.get(0).currentState = input;
		
		//  When the nextState function is called, then the output and states will be based on this setup
		
	}
	
	//  Increment all nodes to the next state
	public void nextState(int input, String saveFile)
	{
		
		//  Actually set the input node.  This always happens first before any changes can occur.
		this.nodes.get(0).currentState = input;
		
		//  For each node in the network
		for(int i = 2; i < N + 2; i++)
		{
			
			//  Create a boolean array of the input values
			int[] boolInputArray = new int[this.nodes.get(i).inputNodes.size()];
			
			//  then, loop through each node in the input list and add its state value to the input value array
			for(int j = 0; j < this.nodes.get(i).inputNodes.size(); j++)
			{
				boolInputArray[j] = nodes.get(nodes.get(i).inputNodes.get(j)).currentState;
			}
			
			//  Determine the nextState of the node by using the input combinations, and the selected logic function
			
			//  Start by getting the row of the input combination within the logic function (Imagine a table)
			int inputRow = MathHelper.convertBinaryArrayToDec(boolInputArray);
			int nextState = nodes.get(i).logicFunction[inputRow];
			nodes.get(i).nextState = nextState;
		}
		
		//  Set the new input state
		this.nodes.get(0).nextState = input;
		//  Set the new output state
		this.nodes.get(1).nextState = this.generateCurrentStateOutput();
		
		//  Commit the new input/output states
		this.nodes.get(0).commitNextState();
		this.nodes.get(1).commitNextState();
		
		//  Loop through all internal nodes and commit the new state
		for(int i = 2; i < N + 2; i++)
		{
			nodes.get(i).commitNextState();
		}
		
		//  Write the current state after switching, so the input which cause the state is shown next to the state (initial state of 0 not shown)
		//this.writeNetworkSateToFile(saveFile);
		
	}
}

