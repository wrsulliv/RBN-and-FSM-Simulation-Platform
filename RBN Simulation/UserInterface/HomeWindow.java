

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class HomeWindow {

	public String workspacePath;
	private JFrame frame;

	/**
	 * Create the application.
	 */
	public HomeWindow(String workspacePath) {
		this.workspacePath = workspacePath;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 539, 358);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(0, 0, 539, 89);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Tool Selection Home:\n");
		lblNewLabel.setBounds(22, 6, 159, 22);
		panel.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		
		JTextArea txtrWelcomeToThe = new JTextArea();
		txtrWelcomeToThe.setWrapStyleWord(true);
		txtrWelcomeToThe.setLineWrap(true);
		txtrWelcomeToThe.setText("Welcome to the SimBN simulator.  This simulator can be used to analyze random Boolean networks and their equivalent finite state automata.");
		txtrWelcomeToThe.setBounds(32, 37, 480, 52);
		panel.add(txtrWelcomeToThe);
		
		JButton btnNewButton = new JButton("Batch Simulator");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				BatchSimulatorGui bs = new BatchSimulatorGui();
				
			}
		});
		btnNewButton.setBounds(64, 140, 117, 98);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnRbnInstanceTool = new JButton("RBN Instance");
		btnRbnInstanceTool.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				RBNInstanceExplorer rbnExp = new RBNInstanceExplorer();
			}
		});
		btnRbnInstanceTool.setBounds(203, 140, 117, 98);
		frame.getContentPane().add(btnRbnInstanceTool);
		
		JButton btnFsmInstance = new JButton("FSM Instance");
		btnFsmInstance.setBounds(345, 140, 117, 98);
		frame.getContentPane().add(btnFsmInstance);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		panel_1.setBounds(0, 308, 539, 28);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblWorkspace = new JLabel("Workspace: ");
		lblWorkspace.setBounds(24, 6, 76, 16);
		panel_1.add(lblWorkspace);
		
		JLabel lblWorkspacePath = new JLabel(this.workspacePath);
		lblWorkspacePath.setBounds(101, 6, 387, 16);
		panel_1.add(lblWorkspacePath);
		frame.setVisible(true);
	}
}
