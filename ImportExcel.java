package nl.tue.generator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;

public class ImportExcel {
	
	private String document;
	public ImportExcel(String file_path){
		this.document = file_path;
	}
	
	ArrayList<ArrayList<String>> extract2(){
		ArrayList<ArrayList<String>> return_list = new ArrayList<ArrayList<String>>();
		return return_list;
	}
	
	
	ArrayList<ArrayList<String>> extract() throws IOException{
		FileInputStream fis = new FileInputStream(new File(this.document) );
		ArrayList<ArrayList<String>> return_list = new ArrayList<ArrayList<String>>();
		
		
		//Create workbook
		HSSFWorkbook wb = new HSSFWorkbook(fis);
		
		//create a sheet object to retrieve the sheet
		HSSFSheet sheet = wb.getSheetAt(0);
		
		//that is for evaluate the cell type
		FormulaEvaluator formulaEvaluator = wb.getCreationHelper().createFormulaEvaluator();
	
		for(Row row : sheet){ 
			ArrayList<String> aRow = new ArrayList<String>();
			for(Cell cell : row) { 
				switch(formulaEvaluator.evaluateInCell(cell).getCellType())
				{ 
				case Cell.CELL_TYPE_NUMERIC:
					break; 
				
				case Cell.CELL_TYPE_STRING:
					aRow.add(cell.getStringCellValue());
					break;
				
				case Cell.CELL_TYPE_BLANK:
					aRow.add("");
					break;
				}	
			
			}
			return_list.add(aRow);	
			
		}
		return return_list;
	}
}
		
