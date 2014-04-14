import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextArea;


public class RBNInstanceSimulationResults {

	private JFrame frame;
	public String separation;
	public String fadingMemory;
	public String computationalCapability;
	public String K;
	public String L;
	public String N;
	public String changePath;
	public String statePath;
	public String T; //  This is a number used in calculating the Separation measure.  It tells how many characters are different at the end of the string
	public String inputString;


	/**
	 * Create the application.
	 */
	public RBNInstanceSimulationResults(String separation, String fadingMemory, String computationalCapability, String K, String L, String N, String changePath, String statePath, String T, String inputString) {
		this.separation = separation;
		this.fadingMemory = fadingMemory;
		this.computationalCapability = computationalCapability;
		this.K = K;
		this.L = L;
		this.N = N;
		this.changePath = changePath;
		this.statePath = statePath;
		this.T = T;
		this.inputString = inputString;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 622, 589);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		ImagePanel imagePanel = new ImagePanel(this.statePath);
		imagePanel.setBounds(321, 128, 266, 253);
		frame.getContentPane().add(imagePanel);
		
		ImagePanel imagePanel_1 = new ImagePanel(this.changePath);
		imagePanel_1.setBounds(26, 128, 266, 253);
		frame.getContentPane().add(imagePanel_1);
		
		JLabel label = new JLabel("RBN Change Space");
		label.setBounds(101, 100, 122, 16);
		frame.getContentPane().add(label);
		
		JLabel label_1 = new JLabel("RBN State Space");
		label_1.setBounds(399, 100, 122, 16);
		frame.getContentPane().add(label_1);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(0, 0, 622, 74);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblRbnInstanceSimulation = new JLabel("RBN Instance Simulation Results:");
		lblRbnInstanceSimulation.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblRbnInstanceSimulation.setBounds(6, 6, 242, 16);
		panel.add(lblRbnInstanceSimulation);
		
		JTextArea txtrThisDialogShows = new JTextArea();
		txtrThisDialogShows.setText("This dialog shows simulation results for the chosen RBN instance. ");
		txtrThisDialogShows.setBounds(37, 34, 560, 16);
		panel.add(txtrThisDialogShows);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		panel_1.setBounds(0, 540, 622, 27);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Instance Settings: ");
		lblNewLabel.setBounds(6, 6, 128, 16);
		panel_1.add(lblNewLabel);
		
		JLabel label_2 = new JLabel("|    <K> = ");
		label_2.setBounds(146, 6, 74, 16);
		panel_1.add(label_2);
		
		JLabel lblK = new JLabel(this.K);
		lblK.setBounds(219, 6, 15, 16);
		panel_1.add(lblK);
		
		JLabel lblLthing = new JLabel("|    L = ");
		lblLthing.setBounds(244, 6, 49, 16);
		panel_1.add(lblLthing);
		
		JLabel lblL = new JLabel(this.L);
		lblL.setBounds(294, 6, 36, 16);
		panel_1.add(lblL);
		
		JLabel lblNthing = new JLabel("|    N = ");
		lblNthing.setBounds(330, 6, 49, 16);
		panel_1.add(lblNthing);
		
		JLabel lblN = new JLabel(this.N);
		lblN.setBounds(382, 6, 36, 16);
		panel_1.add(lblN);
		
		JLabel lblT = new JLabel("|    T = ");
		lblT.setBounds(411, 6, 49, 16);
		panel_1.add(lblT);
		
		JLabel lbl_T = new JLabel(this.T);
		lbl_T.setBounds(463, 6, 36, 16);
		panel_1.add(lbl_T);
		
		JLabel lblSeparation = new JLabel("Separation:");
		lblSeparation.setBounds(37, 410, 75, 16);
		frame.getContentPane().add(lblSeparation);
		
		JLabel lblFadingMemory = new JLabel("Fading Memory:");
		lblFadingMemory.setBounds(37, 437, 105, 16);
		frame.getContentPane().add(lblFadingMemory);
		
		JLabel lblComputationalCapability = new JLabel("Computational Capability:");
		lblComputationalCapability.setBounds(37, 469, 171, 16);
		frame.getContentPane().add(lblComputationalCapability);
		
		JLabel lblSeparationValue = new JLabel(this.separation);
		lblSeparationValue.setBounds(114, 410, 153, 16);
		frame.getContentPane().add(lblSeparationValue);
		
		JLabel lblFadingValue = new JLabel(this.fadingMemory);
		lblFadingValue.setBounds(144, 437, 153, 16);
		frame.getContentPane().add(lblFadingValue);
		
		JLabel lblComutationalValue = new JLabel(this.computationalCapability);
		lblComutationalValue.setBounds(208, 469, 153, 16);
		frame.getContentPane().add(lblComutationalValue);
		
		JLabel lblInputString = new JLabel("Input String:");
		lblInputString.setBounds(37, 497, 82, 16);
		frame.getContentPane().add(lblInputString);
		
		JTextArea txtInputString = new JTextArea(this.inputString);
		txtInputString.setBounds(127, 497, 460, 19);
		frame.getContentPane().add(txtInputString);
		
		frame.setVisible(true);
	}
}
