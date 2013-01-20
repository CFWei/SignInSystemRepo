package SignInSystem.GUI;

import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JPanel;

import javax.swing.table.DefaultTableModel;


import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import SignInSystem.database.ConnectDatabase;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SignInInterface extends JFrame {
	private static final long serialVersionUID = 1L;
	private JTable jTable=null;
	private String[] columnName;
	private ResultSet resultSet;
	private Object[] data=null;
	private DefaultTableModel model ;
	private JScrollPane scrollPane ;
	private int i=0;
	
	private ConnectDatabase db;
	public SignInInterface(ArrayList<String> columnName,ResultSet resultSet){
		db=new ConnectDatabase();
		db.connect("sample.db");
		
		this.columnName=new String[columnName.size()];
		for(int i=0;i<columnName.size();i++){
			this.columnName[i]=columnName.get(i);
		}
		
		if(resultSet!=null){
			this.resultSet=resultSet;
		}
	}
	
	public void setGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		/*Set gridBagLayout parameter*/
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.rowWeights = new double[]{0.0, 0.0};
		gridBagLayout.columnWeights = new double[]{1.0};
		
		/*Set LayoutManager*/
		getContentPane().setLayout(gridBagLayout);
		
		
		/*New a JPanel for Jtable*/
		JPanel jtaelPanel=new JPanel();
		jtaelPanel.setBackground(Color.blue);
		setJTabePane(jtaelPanel);
		
		/*set GridBagConstraints of jtaelPanel*/
		GridBagConstraints JTableConstraints=new GridBagConstraints();
		JTableConstraints.fill=GridBagConstraints.BOTH;
		JTableConstraints.gridx=0;
		JTableConstraints.gridy=0;
		JTableConstraints.weighty=4;
		getContentPane().add(jtaelPanel,JTableConstraints);
		
		/*New a JPanel for controlPanel*/
		JPanel controlPanel=new JPanel();
		controlPanel.setBackground(Color.black);
		setControlPanel(controlPanel);
		/*set GridBagConstraints of controlPanel*/
		GridBagConstraints controlPanelConstraints=new GridBagConstraints();
		controlPanelConstraints.fill=GridBagConstraints.BOTH;
		controlPanelConstraints.gridx=0;
		controlPanelConstraints.gridy=1;
		controlPanelConstraints.weighty=1;
		getContentPane().add(controlPanel,controlPanelConstraints);

	}
	
	public void setControlPanel(JPanel panel){
		
		final JTextField textField = new JTextField();
		textField.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					ResultSet testResult = db.executeQuery("SELECT * from test2 where 身份證字號 ='"+textField.getText()+"'");
					try {
						while(testResult.next()){
							String[] appendData=new String[columnName.length];
							for(int i=0;i<columnName.length;i++)
								appendData[i]=testResult.getString(i+1);	
							setData(appendData);
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
					textField.setText("");
					
					jTable.revalidate();
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							int dataCount= jTable.getRowCount();
							jTable.getSelectionModel().setSelectionInterval(dataCount, dataCount);
							jTable.scrollRectToVisible(new Rectangle(jTable.getCellRect(dataCount, 0, true)));
							
						}
					});
				}
		});
		
		panel.add(textField);
		textField.setColumns(10);
		
		
	}
	
	public void setJTabePane(JPanel panel){
		panel.setLayout(new GridLayout());
		setJTale();
		scrollPane= new JScrollPane(jTable);
	
		panel.add(scrollPane);
		
		
	}
	
	public void setJTale(){
		model = new DefaultTableModel(columnName,0);
		try {
			/*Set data from query result if resultSet is not null*/
			if(resultSet!=null){
				while(resultSet.next()){
					String[] appendData=new String[columnName.length];
					for(int i=0;i<columnName.length;i++)
						appendData[i]=resultSet.getString(i+1);	
					//setData(appendData);
				} 
			
			}
			
		jTable=new JTable(model);
		jTable.setEnabled(false);
		}
		
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void setData(Object[] a){
		model.addRow(a);
	}
	


}
