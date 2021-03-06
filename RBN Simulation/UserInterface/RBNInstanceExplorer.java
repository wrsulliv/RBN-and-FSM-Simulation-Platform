import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.AbstractAction;

import java.awt.event.ActionEvent;

import javax.swing.Action;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.event.MenuKeyListener;
import javax.swing.event.MenuKeyEvent;

import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.JLabel;


public class RBNInstanceExplorer {

	private JFrame frame;
	private final Action action = new SwingAction();
	private RBN rbn;
	private JMenu mnSimulator;
	private JMenu mnTools;
	private JMenuItem mntmLoadRbn;
	private int tau;
	private float chanceOf1;

	/**
	 * Create the application.
	 */
	public RBNInstanceExplorer() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 632, 433);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmNewWorkspace = new JMenuItem("New RBN");
		mntmNewWorkspace.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				NewRBN newRBN = new NewRBN();
				newRBN.setModal(true);
				newRBN.setVisible(true);
				tau = newRBN.getTau();
				chanceOf1 = newRBN.getChance();
				CustomRandom randomGenerator = new CustomRandom();
				rbn = new RBN(newRBN.getAverageConnectivity(), newRBN.getNodeCount(), newRBN.getInputConnectionCount(), randomGenerator);
				
				//  Enable Rule 1 (Simulation and tools) buttons
				//setRule1(true);
			}
		
		});
		mnFile.add(mntmNewWorkspace);
		
		JMenuItem mntmLoadWorkspace = new JMenuItem("Import RBN");
		mntmLoadWorkspace.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				final JFileChooser fc = new JFileChooser();

				fc.setDialogTitle("Select an RBN .csv File");
				
				//In response to a button click:
				int returnVal = fc.showOpenDialog(frame);
				

				rbn = new RBN(fc.getSelectedFile().getPath());
				
			}
		});
		mnFile.add(mntmLoadWorkspace);
		
		JMenuItem mntmExportRbn = new JMenuItem("Export RBN");
		mnFile.add(mntmExportRbn);
		
		mnSimulator = new JMenu("Simulator");
		menuBar.add(mnSimulator);
		
		JMenuItem mntmRunNetwork = new JMenuItem("Run Network");
		mntmRunNetwork.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				ShannonSource ss = new ShannonSource(100, chanceOf1);
				String inputString = ss.getCurrentString();
				int[] inputIntArray = MathHelper.convertStringToIntArray(inputString);
				rbn.generateChangeMap(inputIntArray, "/Users/willimac/Desktop/changeMap.bmp");
				rbn.generateStateSpaceMap(inputIntArray, "/Users/willimac/Desktop/stateMap.bmp");
				String separation = Float.toString(RBN.calculateSeparation(rbn, tau, inputIntArray));
				String fadingMemory = Float.toString(RBN.calculateFadingMemory(rbn, inputIntArray));
				String computationalCapability = Float.toString(RBN.calculateComputationalCapability(rbn, inputIntArray, tau));
				
				RBNInstanceSimulationResults resultsWindow = new RBNInstanceSimulationResults(separation, fadingMemory, computationalCapability, Integer.toString(rbn.K_avg), Integer.toString(rbn.L), Integer.toString(rbn.N), "/Users/willimac/Desktop/changeMap.bmp", "/Users/willimac/Desktop/stateMap.bmp", Integer.toString(tau), inputString);
			}
		});
		mnSimulator.add(mntmRunNetwork);
		
		mnTools = new JMenu("Converter");
		menuBar.add(mnTools);
		
		JMenuItem mntmExportFsm = new JMenuItem("Export FSM");
		mnTools.add(mntmExportFsm);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmManual = new JMenuItem("Manual");
		mnHelp.add(mntmManual);
		frame.getContentPane().setLayout(null);
		
		//
		//  Here Is my code for setting up the program logic
		//
		
		//  Certain buttons should not be highlighted until the right time, so when the program starts remove some buttons.
		//  Rule 1:  The simulator and tools menus, along with file menu buttons should not be available until a workspace is selected.
		//setRule1(false);
		
		frame.setVisible(true);
	}
	
	private void setRule1(boolean b)
	{
		if(b)
		{
			mnSimulator.setEnabled(true);
			mnTools.setEnabled(true);
			mntmLoadRbn.setEnabled(true);
		}
		else
		{
			mnSimulator.setEnabled(false);
			mnTools.setEnabled(false);
			mntmLoadRbn.setEnabled(false);
		}
		
	}
	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
}


