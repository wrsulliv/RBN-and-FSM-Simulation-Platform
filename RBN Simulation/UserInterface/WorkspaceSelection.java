import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenu;

import java.awt.GridBagLayout;

import javax.swing.JButton;

import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import java.awt.Color;

import javax.swing.JLabel;

import java.awt.Font;
import java.util.Vector;

import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.JComboBox;

public class WorkspaceSelection {

	private JComboBox comboBoxFile;
	private JFrame frame;
	DefaultComboBoxModel model;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WorkspaceSelection window = new WorkspaceSelection();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public WorkspaceSelection() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 618, 244);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnChoose = new JButton("Choose...");
		btnChoose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//  TODO:  Add previous workspaces to a file to be added to the combobox
				//Create a file chooser
				final JFileChooser fc = new JFileChooser();

				fc.setDialogTitle("Select a Workspace Directory");
				
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				
				//In response to a button click:
				int returnVal = fc.showOpenDialog(frame);
				
				//  Set the comboBox text
				model.addElement(fc.getSelectedFile());
			}
		});
		btnChoose.setBounds(493, 111, 102, 29);
		frame.getContentPane().add(btnChoose);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(0, 0, 618, 84);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblSelectAWorkspace = new JLabel("Select a Workspace:");
		lblSelectAWorkspace.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblSelectAWorkspace.setBounds(16, 6, 138, 16);
		panel.add(lblSelectAWorkspace);
		
		JTextArea txtrSimbnWillStore = new JTextArea();
		txtrSimbnWillStore.setWrapStyleWord(true);
		txtrSimbnWillStore.setLineWrap(true);
		txtrSimbnWillStore.setText("SimBN will store all your project files in a folder called a workspace.  Please choose a workspace below.");
		txtrSimbnWillStore.setBounds(51, 34, 561, 49);
		panel.add(txtrSimbnWillStore);
		
		Vector comboBoxItems = new Vector();
		model = new DefaultComboBoxModel(comboBoxItems);
		comboBoxFile = new JComboBox(model);
		comboBoxFile.setBounds(26, 112, 451, 27);
		frame.getContentPane().add(comboBoxFile);
	
		
		JButton btnContinue = new JButton("Continue");
		btnContinue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//  The user pressed 'Continue', so move onto the next window
				HomeWindow hw = new HomeWindow(comboBoxFile.getSelectedItem().toString());
				frame.setVisible(false);
			}
		});
		btnContinue.setBounds(356, 175, 117, 29);
		frame.getContentPane().add(btnContinue);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//  The user pressed 'Cancel' so close the program
				System.exit(0);
			}
		});
		btnCancel.setBounds(478, 175, 117, 29);
		frame.getContentPane().add(btnCancel);
	}
}
