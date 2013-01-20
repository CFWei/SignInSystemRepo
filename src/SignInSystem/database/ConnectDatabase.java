package SignInSystem.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import SignInSystem.component.ParseResult;
import SignInSystem.component.PersonData;
import SignInSystem.component.ReferenceTitle;

public class ConnectDatabase {
	private Connection connection = null;
	private Statement statement = null;
	
	public ConnectDatabase(){
		try {
			Class.forName("org.sqlite.JDBC");
			
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		}
	}
	
	public void connect(String databaseName){
		try {
			
			connection = DriverManager.getConnection("jdbc:sqlite:"+databaseName);
			statement = connection.createStatement();
		    statement.setQueryTimeout(30);  // set timeout to 30 sec.
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}

	}
	
	public void executeUpdate(String query){
		//statement.executeUpdate("create table person (id integer, name string)");
		try {
			statement.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public ResultSet executeQuery(String query){
		try {
			return statement.executeQuery(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	
	
	public ArrayList<String> getColumnName(String tableName){
		ArrayList<String> columnNameList=new ArrayList<String>(); 
		
		try {
			DatabaseMetaData meta = connection.getMetaData();
			ResultSet rsColumns = meta.getColumns(null, null, tableName, null);

		   
		 
		    while (rsColumns.next()){
		    	columnNameList.add(rsColumns.getString("COLUMN_NAME"));
		    }
		    rsColumns.close();
		    
		    
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return columnNameList;
		
	}
	
	
	public void addParseResult(String tableName,ParseResult parseResult){
		
		
		
		ReferenceTitle referenceTitle=parseResult.getTitle();
		ArrayList<String> keyList=referenceTitle.getAllKey();
		
		ArrayList<PersonData> contentList=parseResult.getContentList();
		
		
		//create table
		//createTable("test2",referenceTitle.getAllTitle());
		
		
		
		try {
			Statement stmt = connection.createStatement();
			for(PersonData personData:contentList){
				String query=insertParseData(tableName,personData,keyList);
				//System.out.println(query);
				stmt.addBatch(query);
				
			}
			stmt.executeBatch();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		

		
	
	}
	
	public String insertParseData(String tableName,LinkedHashMap<String,String> personData,ArrayList<String> keyList){
		String query="Insert into "+tableName+" values(";
		
		
		String insertData="";
		boolean flag=false;
		for(String key:keyList){
			if(flag==true)
				insertData+=",";
			else
				flag=true;
			
			String data=personData.get(key);
			insertData+="'"+data+"'";
			
		}
		
		query+=insertData;
		query+=")";
		
		return query;
		
		
	}
	
	
	
	public void createTable(String tableName,ArrayList<String> columnName){
		
		//tableName and Column can not be empty
		if(tableName.isEmpty()||columnName.isEmpty())
			return;
		
		String query="Create table ";
		query+=tableName;
	
		query+=" ";
		query+="(";
		
		
		String insertColumnName="";
		//add all column name and set its type 
		boolean flag=false;
		for(String colum:columnName){
			if(flag==true)
				insertColumnName+=",";
			else
				flag=true;
			insertColumnName+="\""+colum+"\"";
			insertColumnName+=" ";
			insertColumnName+="string";
			
			
			
		}
		insertColumnName=insertColumnName.replace("(", "");
		insertColumnName=insertColumnName.replace(")", "");
		query+=insertColumnName;
		query+=")";
		
		//execute query
		executeUpdate(query);
		//System.out.println(query);

	}
	
	public void setScrollableResultSet(){
		try {
			statement=connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//destructor
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
		connection = null;
		
	}
	
}
