import java.io.Console;
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
		
		
		//  Test the FSM Code
		FSM fsm = new FSM("/Users/willimac/Documents/College/Senior!/DH Research/Test Files/irreducible_fsm.csv");
		if(fsm.isIrreducibleViaAlgorithm())
		{
			
			System.out.println("PASS: Irreducible");
		}
		else
		{
			System.out.println("FAIL: Irreducible");
			
		}
		
		//  Test the FSM Code
		fsm = new FSM("/Users/willimac/Documents/College/Senior!/DH Research/Test Files/not_irreducible_fsm.csv");
		if(!fsm.isIrreducibleViaAlgorithm())
		{
			
			System.out.println("PASS: Not Irreducible");
		}
		else
		{
			System.out.println("FAIL: Not Irreducible");
			
		}
		
		//fsm.writeToFile("/Users/willimac/Desktop/fsm_test.csv");
	}
}
