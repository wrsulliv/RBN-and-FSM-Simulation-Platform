import java.awt.EventQueue;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Color;

import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JTextArea;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;


public class BatchSimulatorGui {

	private JFrame frame;



	/**
	 * Create the application.
	 */
	public BatchSimulatorGui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 527, 359);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(0, 0, 527, 94);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblBatchSimulator = new JLabel("Batch Simulator:");
		lblBatchSimulator.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblBatchSimulator.setBounds(6, 6, 147, 16);
		panel.add(lblBatchSimulator);
		
		JTextArea txtrUseThisDialog = new JTextArea();
		txtrUseThisDialog.setWrapStyleWord(true);
		txtrUseThisDialog.setLineWrap(true);
		txtrUseThisDialog.setText("Use this dialog to perform multiple tests and collect lots of data at one time.  \n");
		txtrUseThisDialog.setBounds(29, 34, 466, 47);
		panel.add(txtrUseThisDialog);
		
		JButton btnNewButton = new JButton("Simulate {L, T, \u2206}");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int K_avg = 2;
				int N = 200;
				int L = 50;
				float chanceOf1 = 0.5f;
				int tau = 2;
				ShannonSource ss = new ShannonSource(10, chanceOf1);
				CustomRandom randomGenerator = new CustomRandom();
				//RBN rbn =  new RBN(K_avg, N, L);
				
				ArrayList<DataPoint3D> dataPoints = new ArrayList<DataPoint3D>();
				
				for(tau = 1; tau <= 8; tau++)
				{
					for (L = 1; L <= 100; L++)
					{
						float ccTotal = 0;
						for(int i = 0; i < 3; i++)
						{
		
							RBN rbn =  new RBN(K_avg, N, L, randomGenerator);
							int[] inputIntArray = MathHelper.convertStringToIntArray(ss.getCurrentString());
							float computationalCapability = RBN.calculateComputationalCapability(rbn, inputIntArray, tau);
							ccTotal += computationalCapability;
						}
						
						DataPoint3D dp3d = new DataPoint3D(tau, L, ccTotal / 3);
						dataPoints.add(dp3d);
					}
				}
				
				
				
	/*			PrintWriter writer;
				try {
						writer = new PrintWriter(new FileWriter("/Users/willimac/Desktop/batch.csv", true));
						writer.print("L,");
						for(int i = 0; i < LValues.size(); i++) 
						{
							writer.print(LValues.get(i) + ",");
						}
						writer.print("\n");
						
						writer.print("T,");
						for(int i = 0; i < TauValues.size(); i++) 
						{
							writer.print(TauValues.get(i) + ",");
						}
						writer.print("\n");
						
						writer.print("C,");
						for(int i = 0; i < CompValues.size(); i++)
						{
							writer.print(CompValues.get(i) + ",");
						}
						writer.print("\n");
						
						writer.close();
						
					} catch (IOException ex) {
						// TODO Auto-generated catch block
						ex.printStackTrace();
					}*/
				
				ChartGenerator.generateHeatMapFromDataPoint3DList(dataPoints, tau - 1, L - 1, 1); //  Subtract 1 because the loop added one during the last iteration
			}
		});
		btnNewButton.setBounds(10, 106, 168, 29);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnGenerateHeatTest = new JButton("Generate Heat Test");
		btnGenerateHeatTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				writeColorTestBMP();
				
			}
		});
		btnGenerateHeatTest.setBounds(10, 139, 168, 29);
		frame.getContentPane().add(btnGenerateHeatTest);
		frame.setVisible(true);
	}
	
	public enum RBNVariable
	{
		N, I, K_avg, tau
	}
	
	public static void runBatchSimulation(String xVar, String yVar, String heatVar)
	{
		int K_avg = 2;
		int N = 200;
		int L = 50;
		float chanceOf1 = 0.5f;
		int tau = 2;
		ShannonSource ss = new ShannonSource(10, chanceOf1);
		CustomRandom randomGenerator = new CustomRandom();
		//RBN rbn =  new RBN(K_avg, N, L);
		
		ArrayList<DataPoint3D> dataPoints = new ArrayList<DataPoint3D>();
		
		for(tau = 1; tau <= 8; tau++)
		{
			for (L = 1; L <= 100; L++)
			{
				float ccTotal = 0;
				for(int i = 0; i < 3; i++)
				{

					RBN rbn =  new RBN(K_avg, N, L, randomGenerator);
					int[] inputIntArray = MathHelper.convertStringToIntArray(ss.getCurrentString());
					float computationalCapability = RBN.calculateComputationalCapability(rbn, inputIntArray, tau);
					ccTotal += computationalCapability;
				}
				
				DataPoint3D dp3d = new DataPoint3D(tau, L, ccTotal / 3);
				dataPoints.add(dp3d);
			}
		}
		
		
		
/*			PrintWriter writer;
		try {
				writer = new PrintWriter(new FileWriter("/Users/willimac/Desktop/batch.csv", true));
				writer.print("L,");
				for(int i = 0; i < LValues.size(); i++) 
				{
					writer.print(LValues.get(i) + ",");
				}
				writer.print("\n");
				
				writer.print("T,");
				for(int i = 0; i < TauValues.size(); i++) 
				{
					writer.print(TauValues.get(i) + ",");
				}
				writer.print("\n");
				
				writer.print("C,");
				for(int i = 0; i < CompValues.size(); i++)
				{
					writer.print(CompValues.get(i) + ",");
				}
				writer.print("\n");
				
				writer.close();
				
			} catch (IOException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}*/
		
		//generateHeatMapFromDataPoint3DList(dataPoints, tau - 1, L - 1, 1); //  Subtract 1 because the loop added one during the last iteration
	}

	
	public void writeColorTestBMP()
	{
		//  Create the actual image object.  The number of rows is 1 more than the number of inputs because of the initial state.
		BufferedImage bf = new BufferedImage(255, 255, BufferedImage.TYPE_INT_RGB);
		
		//  Loop through each input string item and determine the next state.
		for(int i = 0; i < 255; i++)
		{
			for(int j = 0; j < 255; j++)
			{
				Color heatColor;
				heatColor = ChartGenerator.rgbFull(0, 255, i);
				int RGB = heatColor.getRGB();
				bf.setRGB(i, j, RGB); 
				
			}
		}
		
		//  Save the bitmap
		File outputFile = new File("/Users/willimac/Desktop/HeatTest.bmp");
		try {
			ImageIO.write(bf, "bmp", outputFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	public Color rgb(float minimum, float maximum, float value)
	{
	    
	    float halfmax = ((float)minimum + (float)maximum) / 2;
	    int b = (int)(Math.max(0, 255*(1 - (float)(value)/halfmax)));
	    int r = (int)(Math.max(0, 255*((float)(value)/halfmax - 1)));
	    int g = 255 - b - r;
	    
	    Color color = new Color(r,g,b);
	    return color;
	}
	

}
