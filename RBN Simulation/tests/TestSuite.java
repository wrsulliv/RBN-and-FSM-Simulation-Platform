import java.io.Console;
import java.util.ArrayList;
import java.util.Arrays;


public class TestSuite {

	
	public static void runTests()
	{

		
		//  Open the RBN Test File
		RBN rbn = new RBN("/Users/willimac/Documents/College/Senior!/DH Research/Test Files/rbn_test_updated.csv");
		
		int[] inputString = MathHelper.convertStringToIntArray("0011");
		int[] inputString2 = MathHelper.convertStringToIntArray("0101");
		
		//  Test Hamming Distance
		int num;
		try {
			
			num = RBN.getHammingDistance(inputString, inputString2);
			if(num == 2)
			{
				
				System.out.println("PASS: Hamming Distance");
			}
			else
			{
				System.out.println("FAIL: Hamming Distance");
				
			}
		} catch (HammingNodeCountException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		
		//  Test the network simulator with the first input string  - Checked with pencil and paper
		rbn.RunSimulation(inputString, "/Users/willimac/Desktop/dummy.csv");
		int[] erInputString1 = new int[3];  //  er stands for Expected Result
		erInputString1[0] = 1;
		erInputString1[1] = 1;
		erInputString1[2] = 0;
		int[] arInputString1 = rbn.getInternalState(); //  ar stands for Actual Result
		if(Arrays.equals(arInputString1, erInputString1))
		{
			
			System.out.println("PASS: Input String 1");
		}
		else
		{
			System.out.println("FAIL: Input String 1");
			
		}
		
		
		//  Test the network simulator with the second input string  - Checked with pencil and paper
		rbn.RunSimulation(inputString2, "/Users/willimac/Desktop/dummy.csv"); //  er stands for Expected Result
		int[] erInputString2 = new int[3];
		erInputString2[0] = 0;
		erInputString2[1] = 1;
		erInputString2[2] = 0;
		int[] arInputString2 = rbn.getInternalState();
		if(Arrays.equals(arInputString2, erInputString2))
		{
			
			System.out.println("PASS: Input String 2");
		}
		else
		{
			System.out.println("FAIL: Input String 2");
			
		}
		
		//  Test the Perturbance Spreading Measure - Checked with pencil and paper
		//  Use the previous two input strings.  The first outputs a state of "011" and the second outputs "010"
		//  So, the hamming distance should be 1.  The number of nodes is 3, so perturbance spreading should be...
		//  1 / 3 = .333
		float erPerturbance = (float)((float)1/(float)3);
		float arPerturbance = RBN.calculatePerturbanceSpreading(rbn, inputString, inputString2);
		if(arPerturbance == erPerturbance)
		{
			
			System.out.println("PASS: Perturbance Spreading");
		}
		else
		{
			System.out.println("FAIL: Perturbance Spreading");
			
		}
		
		
		//  Test the Separation Measure - Checked with pencil and paper
		//  Use the second input string of "0101" which outputs "010".
		//  With a tau of 1, the input will be "1011" which outputs "010"
		//  So, Separation should be 0.
		float erSeparation = 0f;
		float arSeparation = RBN.calculateSeparation(rbn, 1, inputString2);
		if(erSeparation == arSeparation)
		{
			
			System.out.println("PASS: Separation");
		}
		else
		{
			System.out.println("FAIL: Separation");
			
		}
		
		
		//  Test My personal irreducibility test algorithm for success
		FSM fsm = new FSM("/Users/willimac/Documents/College/Senior!/DH Research/Test Files/irreducible_fsm.csv");
		if(fsm.isIrreducibleViaAlgorithm())
		{
			
			System.out.println("PASS: Algorithm - Irreducible");
		}
		else
		{
			System.out.println("FAIL: Algorithm - Irreducible");
			
		}
		
		//  Test My personal irreducibility test algorithm for failure
		fsm = new FSM("/Users/willimac/Documents/College/Senior!/DH Research/Test Files/not_irreducible_fsm.csv");
		if(!fsm.isIrreducibleViaAlgorithm())
		{
			
			System.out.println("PASS: Algorithm - Not Irreducible");
		}
		else
		{
			System.out.println("FAIL: Algorithm - Not Irreducible");
			
		}
		
		//  Test the Eigenvalue / Eigenvector irreducibility test for success
		fsm = new FSM("/Users/willimac/Documents/College/Senior!/DH Research/Test Files/irreducible_fsm.csv");
		if(fsm.isIrreducible(0.5f))
		{
			
			System.out.println("PASS: Eigenvalue / Eigenvector - Irreducible");
		}
		else
		{
			System.out.println("FAIL: Eigenvalue / Eigenvector - Irreducible");
			
		}
		
		//  Test the Eigenvalue / Eigenvector irreducibility test for failure
		fsm = new FSM("/Users/willimac/Documents/College/Senior!/DH Research/Test Files/not_irreducible_fsm.csv");
		if(!fsm.isIrreducible(0.5f))
		{
			
			System.out.println("PASS: Eigenvalue / Eigenvector - Not Irreducible");
		}
		else
		{
			System.out.println("FAIL: Eigenvalue / Eigenvector - Not Irreducible");
			
		}
		
		
		//  Test the power dissipation functions
		fsm = new FSM("/Users/willimac/Documents/College/Senior!/DH Research/Test Files/reversible_fsm.csv");
		if(fsm.getEnergyDissipation(0.5f) == 0)
		{
			
			System.out.println("PASS: Power Dissipation - Reversible");
		}
		else
		{
			System.out.println("FAIL: Power Dissipation - Reversible");
			
		}
		
		
		//  Test the power dissipation functions
		fsm = new FSM("/Users/willimac/Documents/College/Senior!/DH Research/Test Files/natesh_example_fsm.csv");
		double dissipation = fsm.getEnergyDissipation(0.5f);
		if(dissipation == 0.8201119644709262)
		{	
			System.out.println("PASS: Power Dissipation - Natesh Example");
		}
		else
		{
			System.out.println("FAIL: Power Dissipation - Natesh Example");
			
		}
		
		//  Test the specific Attractor finding code
		fsm = new FSM("/Users/willimac/Documents/College/Senior!/DH Research/Test Files/natesh_example_fsm.csv");
		Attractor atr = fsm.getAttractor_ViaAlgorithm(2);
		//fsm.showFSMVisualizationWithAttractor(atr);
		//atr.subFSM.showFSMVisualization();
		if(atr.subFSM.states.size() == atr.parentFSM.states.size())
		{	
			System.out.println("PASS: Attractor Locating - Natesh Example");
		}
		else
		{
			System.out.println("FAIL: Attractor Locating - Natesh Example");
			
		}
		
		
		//  Test the specific Attractor finding code - Circuit of 1 (CO1) Test
		fsm = new FSM("/Users/willimac/Documents/College/Senior!/DH Research/Test Files/not_irreducible_fsm.csv");
		atr = fsm.getAttractor_ViaAlgorithm(0);
		//System.out.println(atr.subFSM.getEnergyDissipation(0.5f));
		//fsm.showFSMVisualizationWithAttractor(atr);
		//atr.subFSM.showFSMVisualization();
		if(atr.subFSM.states.size() == 1)
		{	
			System.out.println("PASS: Attractor Locating - Self Loop");
		}
		else
		{
			System.out.println("FAIL: Attractor Locating - Self Loop");
		}
		
		//  Test the specific Attractor finding code - Circuit of 3 (CO3) Test
		fsm = new FSM("/Users/willimac/Documents/College/Senior!/DH Research/Test Files/CO3_attractor_fsa.csv");
		atr = fsm.getAttractor_ViaAlgorithm(1);
		//System.out.println(atr.subFSM.getEnergyDissipation(0.5f));
		//fsm.showFSMVisualizationWithAttractor(atr);
		//fsm.showFSMVisualization();
		if(atr.subFSM.states.size() == 3)
		{	
			System.out.println("PASS: Attractor Locating - Circuit of 3");
		}
		else
		{
			System.out.println("FAIL: Attractor Locating - Circuit of 3");
		}
		
		
		//  Test the getllAtractors code for  - 2 Attractors Test
		fsm = new FSM("/Users/willimac/Documents/College/Senior!/DH Research/Test Files/3_attractors_fsa.csv");
		ArrayList<Attractor> attractors = fsm.getAllAtractors_ViaAlgorithm();
		//System.out.println(atr.subFSM.getEnergyDissipation(0.5f));
		//fsm.showFSMVisualizationWithAttractor(attractors.get(2));
		//fsm.showFSMVisualization();
		if(attractors.size() == 3)
		{	
			System.out.println("PASS: Get All Atractors - 3 Attractors");
		}
		else
		{
			System.out.println("FAIL: Get All Atractors - 3 Attractors");
		}
		
	}
}
