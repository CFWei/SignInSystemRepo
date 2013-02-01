package SignInSystem.GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import SignInSystem.database.ConnectDatabase;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;

public class ViewAllData extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private String databaseName="sample1.db";
	private String tableName="報名資料";
	

	/**
	 * Create the frame.
	 */
	public ViewAllData() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setTitle("檢視資料");
		contentPane = new JPanel();
		//contentPane.setBorder(new EmptyBorder(1, 1, 1, 1));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JPanel JTablePanel = new JPanel();
		
		GridBagConstraints gbc_JTablePanel = new GridBagConstraints();
		//gbc_JTablePanel.insets = new Insets(0, 0, 5, 0);
		gbc_JTablePanel.fill = GridBagConstraints.BOTH;
		gbc_JTablePanel.gridx = 0;
		gbc_JTablePanel.gridy = 0;
		gbc_JTablePanel.weighty=15;
		contentPane.add(JTablePanel, gbc_JTablePanel);
		JTablePanel.setLayout(new GridLayout(1, 0, 0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		JTablePanel.add(scrollPane);
		
		
		
		
		
		
		ConnectDatabase db=new ConnectDatabase();
		db.connect(databaseName);
		ArrayList<String> columnNameList=db.getColumnName(tableName);
		String[] columnName=new String[columnNameList.size()];
		for(int i=0;i<columnNameList.size();i++){
			columnName[i]=columnNameList.get(i);
		}
		
		DefaultTableModel model=new DefaultTableModel(columnName,0);
		
		ResultSet existData = db.executeQuery("SELECT * from "+tableName);
		int countData=0;
			try {
				if(existData!=null){
					while(existData.next()){
						Object[] appendData=new String[columnName.length];
						for(int i=0;i<columnName.length;i++){
							appendData[i]=existData.getString(i+1);
						}
						
						model.addRow(appendData);
						countData++;
					}
					
					
					
				}
			} catch (SQLException e) {
				System.out.println("error");
			
			}
			
			
		table = new JTable(model);
		scrollPane.setViewportView(table);
		
		
		JPanel detailDataPanel = new JPanel();
		detailDataPanel.setBackground(Color.yellow);
		GridBagConstraints gbc_detailDataPanel = new GridBagConstraints();
		gbc_detailDataPanel.fill = GridBagConstraints.BOTH;
		gbc_detailDataPanel.gridx = 0;
		gbc_detailDataPanel.gridy = 1;
		gbc_detailDataPanel.weighty=1;
		contentPane.add(detailDataPanel, gbc_detailDataPanel);
		detailDataPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblNewLabel = new JLabel("總資料筆數:");
		lblNewLabel.setFont(new Font("標楷體", Font.PLAIN, 30) );
		detailDataPanel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel(String.valueOf(countData));
		lblNewLabel_1.setFont(new Font("標楷體", Font.PLAIN, 30));
	
		detailDataPanel.add(lblNewLabel_1);
	}

}
