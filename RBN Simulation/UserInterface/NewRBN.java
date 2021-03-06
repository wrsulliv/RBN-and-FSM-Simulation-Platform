import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class NewRBN extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txt_NodeCount;
	private JTextField txt_Inputs;
	private JTextField txt_Average;
	private JTextField txtTau;
	private JTextField txtChance;


	/**
	 * Create the dialog.
	 */
	public NewRBN() {
		setBounds(100, 100, 326, 288);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		txt_NodeCount = new JTextField();
		txt_NodeCount.setText("300");
		txt_NodeCount.setColumns(10);
		txt_NodeCount.setBounds(210, 105, 69, 28);
		contentPanel.add(txt_NodeCount);
		
		JLabel label = new JLabel("Node Count (N):");
		label.setBounds(97, 111, 101, 16);
		contentPanel.add(label);
		
		JLabel label_1 = new JLabel("Input Connections (L):");
		label_1.setBounds(59, 77, 139, 16);
		contentPanel.add(label_1);
		
		txt_Inputs = new JTextField();
		txt_Inputs.setText("40");
		txt_Inputs.setColumns(10);
		txt_Inputs.setBounds(210, 71, 69, 28);
		contentPanel.add(txt_Inputs);
		
		txt_Average = new JTextField();
		txt_Average.setText("2");
		txt_Average.setColumns(10);
		txt_Average.setBounds(210, 37, 69, 28);
		contentPanel.add(txt_Average);
		
		JLabel label_2 = new JLabel("Average Connectivity <K>:");
		label_2.setBounds(24, 43, 174, 16);
		contentPanel.add(label_2);
		
		JLabel lblOppositeCharst = new JLabel("Opposite Chars (T):");
		lblOppositeCharst.setBounds(78, 147, 121, 16);
		contentPanel.add(lblOppositeCharst);
		
		txtTau = new JTextField();
		txtTau.setText("3");
		txtTau.setColumns(10);
		txtTau.setBounds(210, 141, 69, 28);
		contentPanel.add(txtTau);
		
		txtChance = new JTextField();
		txtChance.setText("3");
		txtChance.setColumns(10);
		txtChance.setBounds(210, 175, 69, 28);
		contentPanel.add(txtChance);
		
		JLabel lblChanceOf = new JLabel("Chance of 1:");
		lblChanceOf.setBounds(119, 181, 79, 16);
		contentPanel.add(lblChanceOf);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	public int getAverageConnectivity()
	{
		return Integer.parseInt(txt_Average.getText());
	}
	
	public int getInputConnectionCount()
	{
		return Integer.parseInt(txt_Inputs.getText());
	}
	
	public int getNodeCount()
	{
		return Integer.parseInt(txt_NodeCount.getText());
	}
	
	public int getTau()
	{
		return Integer.parseInt(txtTau.getText());
	}
	
	public float getChance()
	{
		return Float.parseFloat(txtChance.getText());
	}
}

