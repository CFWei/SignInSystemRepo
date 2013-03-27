package SignInSystem.GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;

public class SignInInterfaceType2 extends JFrame {

	private JPanel contentPane;
	private ArrayList<JTextField> recordTextField=new ArrayList<JTextField>();
	ArrayList<String> columnName ;
	JPanel dataPanel;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		/*
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SignInInterfaceType2 frame = new SignInInterfaceType2();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		*/
	}
	
	public void addColumn(String labelName){
		
		JLabel lblNewLabel = new JLabel(labelName);
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.fill = GridBagConstraints.VERTICAL;
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		dataPanel.add(lblNewLabel, gbc_lblNewLabel);
		
		JTextField textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 0;
		dataPanel.add(textField, gbc_textField);
		textField.setColumns(10);
		
		recordTextField.add(textField);
		
	}
	
	
	public SignInInterfaceType2(ArrayList<String> columnName,ResultSet resultSet,String modeColumn) {
		
		
		setFrame();
		this.columnName=columnName;
		for(String column:columnName){
			addColumn(column);
		}
		
		
	}
	public void setFrame(){
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 664, 231);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		dataPanel = new JPanel();
		GridBagConstraints gbc_dataPanel = new GridBagConstraints();
		gbc_dataPanel.weighty = 7.0;
		gbc_dataPanel.insets = new Insets(0, 0, 5, 0);
		gbc_dataPanel.fill = GridBagConstraints.BOTH;
		gbc_dataPanel.gridx = 0;
		gbc_dataPanel.gridy = 0;
		contentPane.add(dataPanel, gbc_dataPanel);
		GridBagLayout gbl_dataPanel = new GridBagLayout();
		gbl_dataPanel.columnWidths = new int[]{0, 0, 0};
		gbl_dataPanel.rowHeights = new int[]{0, 0, 0};
		gbl_dataPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_dataPanel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		dataPanel.setLayout(gbl_dataPanel);
		

		
		JPanel controlPanel = new JPanel();
		GridBagConstraints gbc_controlPanel = new GridBagConstraints();
		gbc_controlPanel.weighty = 1.0;
		gbc_controlPanel.fill = GridBagConstraints.BOTH;
		gbc_controlPanel.gridx = 0;
		gbc_controlPanel.gridy = 1;
		contentPane.add(controlPanel, gbc_controlPanel);
		controlPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JComboBox comboBox = new JComboBox();
		controlPanel.add(comboBox);
		
		JTextField textField_2 = new JTextField();
		controlPanel.add(textField_2);
		textField_2.setColumns(10);
		
		JButton submitButton = new JButton("\u78BA\u8A8D");
		controlPanel.add(submitButton);
		
		JButton clearButton = new JButton("\u6E05\u9664");
		controlPanel.add(clearButton);
		
	}

}
