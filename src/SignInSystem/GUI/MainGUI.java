package SignInSystem.GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JButton;

import SignInSystem.component.ParseResult;
import SignInSystem.database.ConnectDatabase;
import SignInSystem.parser.ParseXML;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainGUI extends JFrame {

	private JPanel buttonContentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainGUI frame = new MainGUI();
					frame.setResizable(false);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setBounds(100, 100, 450, 300);
		buttonContentPane = new JPanel();
		buttonContentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(buttonContentPane);
		buttonContentPane.setLayout(new GridLayout(3, 1, 0, 0));
		
		JPanel buttonPanel1 = new JPanel();
		buttonContentPane.add(buttonPanel1);
		buttonPanel1.setLayout(new GridLayout(0, 1, 0, 0));
		
		JButton inputDataButton = new JButton("輸入報名資料");
		inputDataButton.setFont(new Font("標楷體", Font.PLAIN, 42));
		inputDataButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				ParseXML p=new ParseXML();
				ParseResult result=p.parse("test1.xml");
				
				ConnectDatabase db=new ConnectDatabase();
				db.connect("sample1.db");
				db.createTable("報名資料",result.getTitle().getAllTitle());
				db.addParseResult("報名資料",result);
				dispose();
			}
		});
		
		buttonPanel1.add(inputDataButton);
		
		JPanel buttonPanel2 = new JPanel();
		buttonContentPane.add(buttonPanel2);
		buttonPanel2.setLayout(new GridLayout(1, 0, 0, 0));
		
		JButton signInButton = new JButton("簽到系統");
		signInButton.setFont(new Font("標楷體", Font.PLAIN, 42));
		buttonPanel2.add(signInButton);
		
		JPanel buttonPanel3 = new JPanel();
		buttonContentPane.add(buttonPanel3);
		buttonPanel3.setLayout(new GridLayout(1, 0, 0, 0));
		
		JButton outputDataButton = new JButton("輸出簽到結果");
		outputDataButton.setFont(new Font("標楷體", Font.PLAIN, 42));
		buttonPanel3.add(outputDataButton);
	}

}
