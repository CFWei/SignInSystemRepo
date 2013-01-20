package SignInSystem.GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
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
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JList;

import SignInSystem.database.ConnectDatabase;

public class SettingDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	JList list;

	public SettingDialog() {
		
		final ConnectDatabase db=new ConnectDatabase();
		db.connect("sample1.db");
		final ArrayList<String> columnNameList=db.getColumnName("報名資料");;
		
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(2, 1, 0, 0));
		{
			JScrollPane scrollPane = new JScrollPane();
			contentPanel.add(scrollPane);
			{	
				
				
				
				DefaultListModel listmodel=new DefaultListModel<String>();
				/*
				for(String columnName:columnNameList){
					listmodel.addElement(columnName);
				}
				*/
				list = new JList(listmodel);
				
				scrollPane.setViewportView(list);
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
						//System.out.println(list.getSelectedValue());
						
						ResultSet existData = db.executeQuery("SELECT * from 報名資料");
						StartSignInInterfaceRunnable smir=new StartSignInInterfaceRunnable(columnNameList,existData);
						SwingUtilities.invokeLater(smir);
						dispose();
						
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
	public StartSignInInterfaceRunnable(ArrayList<String> columnName,ResultSet testResult){
		this.columnName=columnName;
		this.testResult=testResult;
	}
	
	@Override
	public void run() {
		SignInInterface frame = new SignInInterface(columnName,testResult);
		
		frame.setSize(new Dimension(800,600));
		frame.setVisible(true);
		
		
	}
	
}
