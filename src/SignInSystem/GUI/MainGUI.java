package SignInSystem.GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.View;

import java.awt.GridLayout;
import javax.swing.JButton;

import SignInSystem.component.ParseResult;
import SignInSystem.database.ConnectDatabase;
import SignInSystem.outputdata.OutputHtml;
import SignInSystem.outputdata.excel.ParseExcelFile;
import SignInSystem.parser.ParseXML;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MainGUI extends JFrame {

	private JPanel buttonContentPane;

	/**
	 * Create the frame.
	 */
	public MainGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("簽到系統");
		
		setBounds(100, 100, 450, 300);
		buttonContentPane = new JPanel();
		buttonContentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(buttonContentPane);
		buttonContentPane.setLayout(new GridLayout(4, 1, 0, 0));
		
		JPanel buttonPanel1 = new JPanel();
		buttonContentPane.add(buttonPanel1);
		buttonPanel1.setLayout(new GridLayout(0, 1, 0, 0));
		
		JButton inputDataButton = new JButton("載入報名資料(從檔案)");
		inputDataButton.setFont(new Font("標楷體", Font.PLAIN, 35));
		inputDataButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				/*
				ParseXML p=new ParseXML();
				ParseResult result=p.parse("test1.xml");
				
				ConnectDatabase db=new ConnectDatabase();
				db.connect("sample1.db");
				db.createTable("報名資料",result.getTitle().getAllTitle());
				db.addParseResult("報名資料",result);
				dispose();
				*/
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
					        "Excel File(.xlsx)","xlsx");
				chooser.setFileFilter(filter);
				int returnVal = chooser.showOpenDialog(MainGUI.this);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
						File file=chooser.getSelectedFile();
						ParseExcelFile pef=new ParseExcelFile(file);
						pef.parse();
					   
				    }		
				AlertMessage dialog = new AlertMessage("載入資料完成");
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
				
			}
		});
		
		buttonPanel1.add(inputDataButton);
		
		JPanel panel = new JPanel();
		buttonContentPane.add(panel);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JButton btnNewButton = new JButton("檢視資料庫資料");
		btnNewButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ViewAllData frame = new ViewAllData();
				frame.setVisible(true);
				dispose();
				
			}
		});
		btnNewButton.setFont(new Font("標楷體", Font.PLAIN, 35));
		panel.add(btnNewButton);
		
		JPanel buttonPanel2 = new JPanel();
		buttonContentPane.add(buttonPanel2);
		buttonPanel2.setLayout(new GridLayout(1, 0, 0, 0));
		
		JButton signInButton = new JButton("簽到系統");
		signInButton.setFont(new Font("標楷體", Font.PLAIN, 35));
		buttonPanel2.add(signInButton);
		signInButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				SettingDialog dialog = new SettingDialog();
				dialog.setResizable(false);
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
				dispose();
				
			}
		});
		
		
		JPanel buttonPanel3 = new JPanel();
		buttonContentPane.add(buttonPanel3);
		buttonPanel3.setLayout(new GridLayout(1, 0, 0, 0));
		
		JButton outputDataButton = new JButton("輸出簽到結果");
		outputDataButton.setFont(new Font("標楷體", Font.PLAIN, 35));
		outputDataButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				OutputHtml oh=new OutputHtml();
				oh.output();
				
				AlertMessage dialog = new AlertMessage("輸出簽到結果完成");
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			}
		});
		buttonPanel3.add(outputDataButton);
	}

}
