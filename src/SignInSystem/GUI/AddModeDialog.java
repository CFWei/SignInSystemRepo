package SignInSystem.GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;

import SignInSystem.database.ConnectDatabase;

public class AddModeDialog extends JDialog {
	private String tableName="ModeTable";
	private String dataTableName="報名資料";
	private final JPanel contentPanel = new JPanel();
	private JTextField modeName;

	public void addNewMode(){
		ConnectDatabase db=new ConnectDatabase();
		db.connect("sample1.db");
		
		
		try {
			//check whether the value already exist in table
			ResultSet rs=db.executeQuery("SELECT COUNT(*) AS rowcount FROM ModeTable where ModeName ='"+modeName.getText()+"'");
			rs.next();
			int resultCount = rs.getInt("rowcount");
			
			if(resultCount==0){
				
				String query="alter table "+dataTableName+" add column "+modeName.getText()+" default 0";
				db.executeUpdate(query);
				
				query="Insert into "+tableName+"(ModeName) values('"+modeName.getText()+"')";
				db.executeUpdate(query);
			}
			
			
			
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
		//System.out.print(query);
		
		
	}

	public AddModeDialog() {
		setBounds(100, 100, 300, 150);
		
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JLabel lblNewLabel = new JLabel("模式名稱");
			contentPanel.add(lblNewLabel);
		}
		{
			modeName = new JTextField();
			contentPanel.add(modeName);
			modeName.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("新增");
				
				okButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						addNewMode();
						dispose();
						
					}
				});
				/*Add New Mode To List*/
				
				
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("取消");
				
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
