package SignInSystem.GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.JSpinner;
import javax.swing.JScrollBar;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JList;

import SignInSystem.database.ConnectDatabase;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;

public class SettingDialog extends JFrame {

	private final JPanel contentPanel = new JPanel();
	final ConnectDatabase db=new ConnectDatabase();
	private String databaseName="sample1.db";
	JCheckBox[] selectColumnCheckbox;
	JComboBox modeListBox;
	String[] modeNameList;
	
	public void selectModeList() {
		try {
			//set the result count
			db.executeUpdate("create table if not exists ModeTable (ModeName string);");
			ResultSet rs=db.executeQuery("SELECT COUNT(*) AS rowcount FROM ModeTable");
			rs.next();
			int resultCount = rs.getInt("rowcount");
			
			//select the modeName and insert to modeNameList
			ResultSet modeNameResult=db.executeQuery("Select * from ModeTable");
			modeNameList=new String[resultCount];
			int i=0;
			while(modeNameResult.next()){
				modeNameList[i]=modeNameResult.getString(1);
				
				i++;
			}
		
		} 
		
		catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	public SettingDialog() {
		
		
		db.connect(databaseName);
		selectModeList();
		final ArrayList<String> columnNameList=db.getColumnName("報名資料");
		
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(2, 1, 0, 0));
		{
			JPanel selectColumnPanel = new JPanel();
			contentPanel.add(selectColumnPanel);
			
			selectColumnCheckbox=new JCheckBox[columnNameList.size()];
			
			for(int i=0;i<columnNameList.size();i++){
				String columnName=columnNameList.get(i);
				boolean flag=false;
				for(int j=0;j<modeNameList.length;j++){
					if(modeNameList[j].equals(columnName)){
						flag=true;
						break;
					}
				}
				if(flag==false){
					selectColumnCheckbox[i]=new JCheckBox(columnName);
					selectColumnCheckbox[i].setSelected(true);
					selectColumnPanel.add(selectColumnCheckbox[i]);
				}
			}
			
			selectColumnPanel.setLayout(new GridLayout(3, 5, 0, 0));
		}
		{
			JPanel modePanel = new JPanel();
			contentPanel.add(modePanel);
			{
				JLabel label = new JLabel("選擇模式:");
				modePanel.add(label);
			}
			{
					modeListBox = new JComboBox(modeNameList);
					modePanel.add(modeListBox);
				
				
			}
			{
				JButton addNewModeButton = new JButton("新增模式");
			
				addNewModeButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						db.executeUpdate("create table if not exists ModeTable (ModeName string);");
						
						AddModeDialog dialog = new AddModeDialog();
						dialog.setSize(new Dimension(300,150));
						dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
						dialog.setVisible(true);
						
						dispose();
					}
				});
				modePanel.add(addNewModeButton);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				
				okButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						
						ArrayList<String> selectColumn=new ArrayList<String>();
						for(int j=0;j<selectColumnCheckbox.length;j++){
							
							if(selectColumnCheckbox[j]!=null&&selectColumnCheckbox[j].isSelected())
								//System.out.println(selectColumnCheckbox[j].getText());
								selectColumn.add(selectColumnCheckbox[j].getText());
						}
						
						/*check whether the mode list is selected*/
						
						if(modeListBox.getSelectedItem()!=null){
							
							String modeColumn=modeListBox.getSelectedItem().toString();
							String selectColumnString =new String();
							for(int i=0;i<selectColumn.size();i++){
								if(i!=0)
									selectColumnString+=",";
								selectColumnString+=selectColumn.get(i);
								
							}
							selectColumnString+=","+modeColumn;
							
							ResultSet existData = db.executeQuery("SELECT "+selectColumnString+" from 報名資料 where "+modeColumn+"!=0");
							StartSignInInterfaceRunnable smir=new StartSignInInterfaceRunnable(selectColumn,existData,modeColumn);
							SwingUtilities.invokeLater(smir);
							dispose();
						}
						
					}
				});
				
				buttonPane.add(okButton);
				
				//getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						dispose();
						
					}
				});
				buttonPane.add(cancelButton);
			}
		}
	}

}

class StartSignInInterfaceRunnable implements Runnable{
	private ArrayList<String> columnName;
	private ResultSet testResult;
	private String modeColumn;
	public StartSignInInterfaceRunnable(ArrayList<String> columnName,ResultSet testResult,String modeColumn){
		this.columnName=columnName;
		this.testResult=testResult;
		this.modeColumn=modeColumn;
	}
	
	@Override
	public void run() {
		SignInInterface frame = new SignInInterface(columnName,testResult,modeColumn);
		
		frame.setSize(new Dimension(800,600));
		frame.setVisible(true);
		
		
	}
	
}
