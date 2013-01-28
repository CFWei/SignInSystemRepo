package SignInSystem.outputdata;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import SignInSystem.database.ConnectDatabase;

public class OutputHtml {
	private String database="sample1.db";
	private String formName="報名資料";
	ConnectDatabase db=new ConnectDatabase();
	ArrayList<HashMap<String, String> > allPersonData=new ArrayList<HashMap<String,String>>();
	ArrayList<String> columnName;
	
	
	public void output(){
		
		db.connect(database);
		getData();
		outputToFile();
		
	}
	
	public void outputToFile(){
		 try {
			 	PrintStream pstream = new PrintStream(new BufferedOutputStream(new FileOutputStream("output.html", false)));
			 	PrintWriter out = new PrintWriter(pstream, true);
			 	/*print column*/
			 	out.print("<table style=\"border:1px solid;\"><tr>");
			 	for(String eachColumnName:columnName){
			 		out.print("<td style=\"border:1px solid;\">"+eachColumnName+"</td>");
			 		
			 	}
			 	out.println("</tr>");
			 	/*print all data*/
			 	for(HashMap<String,String> eachPerson:allPersonData){
			 		out.print("<tr>");
			 		for(String eachColumnName:columnName){
			 			out.print("<td style=\"border:1px solid;\">"+eachPerson.get(eachColumnName)+"</td>");
			 		}
			 		out.print("</tr>");
			 	}
			 	out.print("</table>");
			 	out.close();
			 	
			 	
			 	
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 
		 
		 
		 
		 
		
	}
	
	public void getData(){
		
		try {
			columnName=db.getColumnName(formName);
			ResultSet result=db.executeQuery("SELECT * FROM "+formName);
			
			/*Store all data*/
			while(result.next()){
				HashMap<String, String> personData=new HashMap<String, String>();
				for(int i=0;i<columnName.size();i++){
					personData.put(columnName.get(i), result.getString(i+1));
				
				}
				
				allPersonData.add(personData);
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

}
