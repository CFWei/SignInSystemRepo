import java.awt.Dimension;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.SwingUtilities;

import SignInSystem.GUI.MainGUI;
import SignInSystem.GUI.SignInInterface;
import SignInSystem.GUI.SettingDialog;
import SignInSystem.component.ParseResult;
import SignInSystem.database.ConnectDatabase;
import SignInSystem.parser.ParseXML;


public class Main {
	
	public static void main(String[] args) {
		//Parse XML to get result data
		//ParseXML p=new ParseXML();
		//ParseResult result=p.parse("test1.xml");
		//connectDatabase db=new connectDatabase();
		//db.connect("sample.db");
		//db.addParseResult("test2",result);
		//db.getColumnName("test2");
		//final ResultSet testResult = db.executeQuery("SELECT * from test2");
		//final ArrayList<String> columnName=db.getColumnName("test2");

//		db.executeUpdate("Insert into TestTable (Name,StudentID) values('ÃQÂ@¦|','f74981183')");
		
		//StartMainInterfaceRunnable smir=new StartMainInterfaceRunnable(columnName,testResult);
		//SwingUtilities.invokeLater(smir);
		
		
		//SettingDialog dialog = new SettingDialog();
		//dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		//dialog.setVisible(true);
		
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				MainGUI frame = new MainGUI();
				frame.setResizable(false);
				frame.setVisible(true);
				
			}
		});
		
		
	}

}




