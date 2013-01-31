package SignInSystem.outputdata.excel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import SignInSystem.database.ConnectDatabase;

public class ParseExcelFile {
	private String fileName="testExcel.xlsx";
	private File parseFile;
	private String databaseName="sample1.db";
	private String tableName="報名資料";
	
	public ParseExcelFile(File parseFile){
		this.parseFile=parseFile;
		
	}
	
	public void createTable(ArrayList<String> titleName){
		ConnectDatabase db=new ConnectDatabase();
		db.connect(databaseName);
		db.createTable(tableName,titleName);
	}
	
	public void addDataToTable(ArrayList<ArrayList<String>> dataSet){
		ConnectDatabase db=new ConnectDatabase();
		db.connect(databaseName);
		db.addDataToTable(tableName,dataSet);
	}
	
	public void parse() {
		try {
			Workbook wb = WorkbookFactory.create(parseFile);
			Sheet sheet = wb.getSheetAt(0);
			boolean flag=false;
			ArrayList<ArrayList<String>> dataSet=new ArrayList<ArrayList<String>>();
			for(Row row: sheet){
				//get title
				if(flag==false){
					ArrayList<String> titleName=new ArrayList<String>();
					for(Cell cell:row)
						titleName.add(cell.toString());
					createTable(titleName);
					flag=true;
				}
				
				//get content 
				else{
					ArrayList<String> data=new ArrayList<String>();
 					for(Cell cell:row)
 						data.add(cell.toString());
 					dataSet.add(data);
 					
				}
				
			}
			addDataToTable(dataSet);

			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		

	}

}
