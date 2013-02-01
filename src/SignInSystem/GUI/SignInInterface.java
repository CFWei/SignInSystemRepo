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
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import SignInSystem.database.ConnectDatabase;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JSpinner;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.JLabel;

public class SignInInterface extends JFrame {
	private static final long serialVersionUID = 1L;
	private String formName="���W���";
	private String dataBase="sample1.db";
	
	private JTable jTable=null;
	private String[] columnName;
	private String[] jComboBoxColumnName; 
	private ResultSet resultSet;
	private Object[] data=null;
	private DefaultTableModel model ;
	private JScrollPane scrollPane ;
	private int i=0;
	
	private ConnectDatabase db;
	private JPanel controlPanel;
	private JComboBox comboBox;
	private JLabel alertLabel;
	private String modeColumn;
	
	public SignInInterface(ArrayList<String> columnName,ResultSet resultSet,String modeColumn){
		db=new ConnectDatabase();
		db.connect(dataBase);
		
		this.columnName=new String[columnName.size()+1];
		jComboBoxColumnName=new String[columnName.size()];
		for(int i=0;i<columnName.size();i++){
			this.columnName[i]=columnName.get(i);
			this.jComboBoxColumnName[i]=columnName.get(i);
		}
		this.columnName[columnName.size()]=modeColumn;
		//this.columnName[columnName.size()+1]="Button";
		if(resultSet!=null){
			this.resultSet=resultSet;
		}
		
		this.modeColumn=modeColumn;
		setTitle(modeColumn);
		setGUI();
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
		JTableConstraints.weighty=9;
		getContentPane().add(jtaelPanel,JTableConstraints);
		
		/*New a JPanel for controlPanel*/
		controlPanel=new JPanel();
		controlPanel.setBackground(Color.black);
		setControlPanel(controlPanel);
		
		/*set GridBagConstraints of controlPanel*/
		GridBagConstraints gbc_controlPanel=new GridBagConstraints();
		gbc_controlPanel.fill=GridBagConstraints.BOTH;
		gbc_controlPanel.gridx=0;
		gbc_controlPanel.gridy=1;
		gbc_controlPanel.weighty=1;
		getContentPane().add(controlPanel,gbc_controlPanel);
		
		/*Add combo box to conrtol panel*/
		comboBox = new JComboBox(jComboBoxColumnName);
		controlPanel.add(comboBox);
		
		final JTextField textField = new JTextField();
		controlPanel.add(textField);
		textField.addActionListener(new ActionListener() {
				String selectColumnName=null;
				String textFieldText=null;
				
				public void appendDataToJTable() throws SQLException{
					String selectColumn=new String();
					for(int i=0;i<columnName.length;i++){
						if(i!=0)
							selectColumn+=",";
						selectColumn+=columnName[i];
						System.out.println(columnName[i]);
					}
					ResultSet testResult = db.executeQuery("SELECT "+selectColumn+" from "+formName+" where "+selectColumnName+" ='"+textFieldText+"'");
					while(testResult.next()){
						Object[] appendData=new String[columnName.length];
						for(int i=0;i<columnName.length;i++)
							appendData[i]=testResult.getString(i+1);	
						setData(appendData);
					}	
					
				}
				
				
				public void scrollToBottom(){
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							int dataCount= jTable.getRowCount();
							jTable.getSelectionModel().setSelectionInterval(dataCount, dataCount);
							jTable.scrollRectToVisible(new Rectangle(jTable.getCellRect(dataCount, 0, true)));
								
						}
					});
					
				}
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					
					/*get value from comboBox*/
					selectColumnName=(String) comboBox.getSelectedItem();
					textFieldText=textField.getText();
					
					try {	
							/*Get result set count*/
							ResultSet rs=db.executeQuery("SELECT COUNT(*) AS rowcount FROM "+formName+" where "+selectColumnName+" ='"+textFieldText+"' and "+modeColumn+" = 0");
							rs.next();
							int ResultCount = rs.getInt("rowcount");
							
							/*if there is any data from this query , do following thing */
							if(ResultCount!=0){
								String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
								db.executeUpdate("Update "+formName+" set "+modeColumn+" = '"+timeStamp+"' where "+selectColumnName+" = '"+textFieldText+"'");
								/*clean the text field*/
								textField.setText("");
								alertLabel.setText("");
								
								/*execute query and append data to JTable*/
								appendDataToJTable();
								
								/*revalidate Jtable*/	
								jTable.revalidate();
								
								/*Let the scroll panel scroll to the bottom*/
								scrollToBottom();
							}
							
							/* if there is no data from query ,do the following thing  */
							else{
								alertLabel.setText("no "+selectColumnName+" = "+textFieldText+" and "+modeColumn+" = 0");
								
							}
					}catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	

					}
		});
		
		
		
		textField.setColumns(10);
		
		alertLabel = new JLabel("New label");
		alertLabel.setForeground(Color.YELLOW);
		controlPanel.add(alertLabel);

	}
	
	public void setControlPanel(JPanel panel){
		
		
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
					Object[] appendData=new String[columnName.length];
					for(int i=0;i<columnName.length;i++){
						appendData[i]=resultSet.getString(i+1);
					
					}
					
				
					setData(appendData);
				} 
			
			}
			
		jTable=new JTable(model);
		
		jTable.setEnabled(false);
		jTable.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				if(e.getButton()==java.awt.event.MouseEvent.BUTTON3){
					int rowPoint=jTable.rowAtPoint(e.getPoint());
					final int columnPoint=jTable.columnAtPoint(e.getPoint());
					
					String pointColumnName=jTable.getColumnName(columnPoint);
					String pointColumnValue=jTable.getValueAt(rowPoint,columnPoint).toString();
					/*
					//create popup menu
					JMenuItem deleteButton = new JMenuItem("�R��("+pointColumnName+":"+pointColumnValue+")"); 
					JPopupMenu poMenu = new JPopupMenu();
					poMenu.add(deleteButton);
					
					//add eventlistener for delete data
					deleteButton.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent arg0) {
							deleteData(columnPoint);
							
						}
						
						public void deleteData(int columnPoint){
							
							
						}
						
					});
					
					
					poMenu.show(e.getComponent(),e.getX(),e.getY());
					*/
				}
				
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				
				
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				
				
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				
				
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				
			}
		});
		
		}
		
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	
	public void setData(Object[] a){
		model.addRow(a);
	}
	


}
