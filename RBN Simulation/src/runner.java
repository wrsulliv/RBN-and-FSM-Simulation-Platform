import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;

import org.leores.plot.DataTableSet;
import org.leores.plot.JGnuplot;
import org.leores.plot.JGnuplot.Plot;

import Jama.Matrix;
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
		
		//  Run Tests
		TestSuite.runTests();
		
		//  Set the workspace
		String pathToWorkspace = "/Users/willimac/Documents/College/Senior!/DH Research/Test Files/";
		
		//  Set the irreducible folder
		String pathToIrreducible = "IrreducibleRBNs/";
		
//		//  Create the CustomRandom Generator
		CustomRandom randomGenerator = new CustomRandom();
		
		//  Loop through and create RBNs one at a time
//		int totalSuccess = 0;
//		int totalTest = 1000;
//		for(int i = 0; i < totalTest; i++)
//		{
//			ShannonSource ss = new ShannonSource(1000, 0.5f);
//			RBN rbn = new RBN(1, 4, 2, randomGenerator);
//			FSM fsm = RBN_FSM_Helper.generateFSMFromRBN(rbn);
//			//fsm.showVisualizationWithAllAtractors();
//			//ArrayList<ArrayList<Integer>> atrList = fsm.getAllAtractors();
//			//System.out.println("Attractors: " + atrList.size());
//			String inputString = ss.getCurrentString();
//			int finalState = fsm.runInputString(0, inputString);
//			Attractor atr = fsm.getAttractor_ViaAlgorithm(finalState);
//			if(!(atr == null))
//			{
//				totalSuccess++;
//				fsm.showFSMVisualizationWithAttractor(atr);
//				System.out.println(i + ": Success\n");
//				int m = 0;
//			}
//			else
//			{
//				System.out.println(i + ": Fail\n");
//			}
//			
//		}
//		System.out.println("Success Rate: " + (double)totalSuccess / (double)totalTest);
//		
//		//  Create the irreducible RBN generator
		IrreducibleRBNManager irm = new IrreducibleRBNManager(pathToIrreducible, pathToWorkspace, randomGenerator);
//		
		JGnuplot jg = new JGnuplot();
		Plot plot0 = new Plot("asdf");
		double[] x = { 1, 2, 3, 4, 5 }, y1 = { 2, 4, 6, 8, 10 }, y2 = { 3, 6, 9, 12, 15 };
		org.leores.util.DataTableSet dts = plot0.addNewDataTableSet("Simple plot");
		dts.addNewDataTable("2x", x, y1);
		dts.addNewDataTable("3x", x, y2);
		jg.execute(plot0, jg.plot2d);
		
		//irm.BatchIrreducibleExport(1, 6, 6, 6, 1, 3, 50);
//		
//		System.out.println("Done");
		
		//RBN rbn = new RBN(2, 5, 4, randomGenerator);
		//RBN_FSM_Helper.generateFSMFromRBN(rbn).showFSMVisualization();
		//FSM fsm2 = new FSM("/Users/willimac/Documents/College/Senior!/DH Research/Test Files/irreducible_fsm.csv");
		
		//fsm2.showFSMVisualization();
		//System.out.println("Is Irreducible: " + fsm2.isIrreducible(0.5f));
		//System.out.println("Dissipation Bound: " + fsm2.getEnergyDissipation(75, 0.5f));
		
		//TestSuite.runTests();
		
		
		//  Create the sweeping variables
		ArrayList<SweepingDiscreteVariable> sweepingVariables = new ArrayList<SweepingDiscreteVariable>();
		sweepingVariables.add(new SweepingDiscreteVariable(DiscreteVariableType.K_avg, 1, 3, 1, 0, true)); //  Test each k first
		sweepingVariables.add(new SweepingDiscreteVariable(DiscreteVariableType.L, 3, 3, 1, 0, true));  //  Then sweep through the values of L
		sweepingVariables.add(new SweepingDiscreteVariable(DiscreteVariableType.N, 5, 5, 1, 0, true)); //  Test the N=5 networks
		sweepingVariables.add(new SweepingDiscreteVariable(DiscreteVariableType.Tau, 2, 2, 1, 0, true)); //  Test with tau=1
		sweepingVariables.add(new SweepingDiscreteVariable(DiscreteVariableType.Averager, 100, 100, 1, 0, true));
		
		//  Create the constant variables (L=2)
		ArrayList<ConstantDiscreteVariable> constantVariables = new ArrayList<ConstantDiscreteVariable>();
		constantVariables.add(new ConstantDiscreteVariable(DiscreteVariableType.InputLength, 5));
		
		DependentVariableType dependentVariableType = DependentVariableType.attractorCount;
		String csvFilePath = "/Users/willimac/Documents/College/Senior!/DH Research/attractorCount.csv";
		//String csvFilePath = "/Users/willimac/Documents/College/Senior!/DH Research/NonIrreducibleTest.csv";
		
		
		//randomGenerator.writeHistogramToCSV("/Users/willimac/Documents/College/Senior!/DH Research/histogram.csv", 100, 1000000000);
		
		BatchSimulator batchSim = new BatchSimulator(sweepingVariables,constantVariables ,dependentVariableType ,csvFilePath ,randomGenerator, irm);
		//batchSim.GenerateCCMap();
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
