import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.graph.util.EdgeType;


public class runner {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//  Set the workspace
		String pathToWorkspace = "/Users/willimac/Documents/College/Senior!/DH Research/Test Files/";
		
		//  Set the irreducible folder
		String pathToIrreducible = "IrreducibleRBNs/";
		
//		//  Create the CustomRandom Generator
		CustomRandom randomGenerator = new CustomRandom();
//		
//		//  Create the irreducible RBN generator
		IrreducibleRBNManager irm = new IrreducibleRBNManager(pathToIrreducible, pathToWorkspace, randomGenerator);
//		
//		irm.BatchIrreducibleExport(3, 4, 3, 5, 1, 3, 50);
//		
//		System.out.println("Done");
		
		//RBN rbn = new RBN(2, 5, 4, randomGenerator);
		//RBN_FSM_Helper.generateFSMFromRBN(rbn).showFSMVisualization();
		//FSM fsm2 = new FSM("/Users/willimac/Documents/College/Senior!/DH Research/Test Files/irreducible_fsm.csv");
		
		//fsm2.showFSMVisualization();
		//System.out.println("Is Irreducible: " + fsm2.isIrreducible(0.5f));
		//System.out.println("Dissipation Bound: " + fsm2.getEnergyDissipation(75, 0.5f));
		
		//TestSuite.runTests();
		
		//  More general way to see how many irreducible networks!
		
		//  Create the sweeping variables
		ArrayList<SweepingDiscreteVariable> sweepingVariables = new ArrayList<SweepingDiscreteVariable>();
		sweepingVariables.add(new SweepingDiscreteVariable(DiscreteVariableType.K_avg, 1, 3, 1, 0, true)); //  Test each k first
		sweepingVariables.add(new SweepingDiscreteVariable(DiscreteVariableType.N, 5, 5, 1, 0, true)); //  Test the N=5 networks
		sweepingVariables.add(new SweepingDiscreteVariable(DiscreteVariableType.Tau, 3, 3, 1, 0, true)); //  Test with tau=10
		sweepingVariables.add(new SweepingDiscreteVariable(DiscreteVariableType.IrreducibleFile_Averager, 50, 50, 1, 0, true));
		
		//  Create the constant variables (L=1) because input nodes don't matter for this test
		ArrayList<ConstantDiscreteVariable> constantVariables = new ArrayList<ConstantDiscreteVariable>();
		constantVariables.add(new ConstantDiscreteVariable(DiscreteVariableType.L, 2));
		
		DependentVariableType dependentVariableType = DependentVariableType.computationalCapability;
		String csvFilePath = "/Users/willimac/Documents/College/Senior!/DH Research/ComputationalCapability_K1_N5.csv";
		
		//randomGenerator.writeHistogramToCSV("/Users/willimac/Documents/College/Senior!/DH Research/histogram.csv", 100, 1000000000);
		
		BatchSimulator batchSim = new BatchSimulator(sweepingVariables,constantVariables ,dependentVariableType ,csvFilePath ,randomGenerator, irm);
		batchSim.generateCSV();
		
		
		
/*		//  See how many Irreducible RBNs are created
		int numberOfTestNetworks = 5000;
		int irreducibilityCount = 0;
		double checkpointIncrement = (double)numberOfTestNetworks / (double)10;
		int nextCheckpoint = 1;
		for (int i = 0; i < numberOfTestNetworks; i++)
		{
			if(i >= nextCheckpoint*checkpointIncrement)
			{
				double percentageDone = (nextCheckpoint*checkpointIncrement) / (double)numberOfTestNetworks;
				System.out.println("Checkpoint: " + Double.toString(percentageDone) + "%...");
				nextCheckpoint++;
			}
			RBN rbn = new RBN(2, 4, 1, randomGenerator);
			FSM fsm = RBN_FSM_Helper.generateFSMFromRBN(rbn);
			if(fsm.isIrreducibleViaAlgorithm())
			{
				irreducibilityCount++;
				
			}

		}
		
		double irreduciblePercentage = ((double)irreducibilityCount / (double)numberOfTestNetworks)*100;
		System.out.println("Irreducibility Percentage: " + Double.toString(irreduciblePercentage) + "%");*/
		
		
		
		//  Create the RBN with N, Ki, and L

		//rbn.writeNetworkSetupToFile("/Users/willimac/Desktop/rbn_test.csv");
		//int[] inputString = MathHelper.convertStringToIntArray("00011101010010010001001110101001010101010100000101101011100011001");
		//rbn.generateChangeMap(inputString, "/Users/willimac/Desktop/testPic.bmp");
		
		
		//int[] inputString2 = MathHelper.convertStringToIntArray("110111");
		//try {
			//int num = rbn.getHammingDistance(inputString, inputString2);
			//float test = rbn.calculatePerturbanceSpreading(rbn, inputString, inputString2);
			//int i = 0;
			//i++;
	//	} catch (HammingNodeCountException e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
		//}
		

		//fsm.writeToFile("/Users/willimac/Desktop/fsm_test.csv");
		
		 //Show that a CSV can be read back into the program 
		//RBN rbn2 = new RBN("/Users/willimac/Documents/College/Senior!/DH Research/Test Files/rbn_test_updated.csv");
		//rbn2.writeNetworkSetupToFile("/Users/willimac/Desktop/rbn_test_2.csv");
		
		//fsm.showFSMVisualization();
		
		//FSM fsm = RBN_FSM_Helper.generateFSMFromRBN(rbn);
		//fsm.writeToFile("/Users/willimac/Desktop/fsm_test.csv");
		

	}

}
