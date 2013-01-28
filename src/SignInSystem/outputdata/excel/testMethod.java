package SignInSystem.outputdata.excel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class testMethod {
	
	public static void main(String[] args) {

		try {
			Workbook wb = WorkbookFactory.create(new File("testExcel.xlsx"));
			Sheet sheet = wb.getSheetAt(0);
			for(Row row: sheet){
				//System.out.println(row.getPhysicalNumberOfCells());
				System.out.println(row.getRowNum());
				
				for(Cell cell:row){
					
					 System.out.println(cell.toString());
					 
				}
				
			}
			
			
			
		    
			
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
