
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelParser {
	// Excel Control
	private FileInputStream excelFile;
	private Row excelRow;
	private Cell excelCell;
	private Sheet excelSheet;
	private Workbook workbook;
	// Data from Excel
	private String[] contents; // �׸��
	

	// ������
	public ExcelParser(String fileName, BinarySearchTree tmp) {
		// fileName : ���� ���� �̸�
		try {
			excelFile = new FileInputStream(fileName);
			contents = null;
			workbook = WorkbookFactory.create(excelFile);
			excelSheet = workbook.getSheetAt(0);
			excelData tmpRow;

			int rows = excelSheet.getPhysicalNumberOfRows();
			for (int rowIndex = this.findStartRow(); rowIndex < rows; rowIndex++) {
				excelRow = excelSheet.getRow(rowIndex);
				tmpRow = new excelData();
				if (excelRow != null) {
					int cells = excelRow.getPhysicalNumberOfCells();
					for (int colIndex = 0; colIndex < cells; colIndex++) {
						excelCell = excelRow.getCell(colIndex);
						tmpRow.setData(contents[colIndex], this.getDataFromExcel());
					}
					tmp.insertBST(tmpRow);
				}
			}
			workbook.close();
			excelFile.close();
		} catch (Exception e) {
			// Swing���� ����â ����� �� ������ ���ڽ��ϴ�.
			e.printStackTrace();
		}
	}

	// ���� ���Ͽ��� �� ���� ������ Ÿ���� �Ǵ��Ͽ� �˸°� ����
	private String getDataFromExcel() {
		String value = null;
		switch (excelCell.getCellType()) {
		case Cell.CELL_TYPE_FORMULA:
			value = excelCell.getCellFormula();
			break;
		case Cell.CELL_TYPE_NUMERIC:
			value = excelCell.getNumericCellValue() + "";
			break;
		case Cell.CELL_TYPE_STRING:
			value = excelCell.getStringCellValue() + "";
			break;
		case Cell.CELL_TYPE_BLANK:
			value = excelCell.getBooleanCellValue() + "";
			break;
		case Cell.CELL_TYPE_ERROR:
			value = excelCell.getErrorCellValue() + "";
			break;
		}
		return value;
	}

	// ���� ���Ͽ��� ǥ�� ���� ������ ã�Ƴ�
	// ���� ���Ϸκ��� �׸��� ����
	private int findStartRow() {
		int rows = excelSheet.getPhysicalNumberOfRows();
		int rowIndex;
		int cells;
		for (rowIndex = 0; rowIndex < rows; rowIndex++) {
			excelRow = excelSheet.getRow(rowIndex);
			if (excelRow == null)
				continue;
			cells = excelRow.getPhysicalNumberOfCells();
			if (cells <= 5)
				continue;
			else {
				this.contents = new String[cells];
				for (int columnindex = 0; columnindex < cells; columnindex++) {
					excelCell = excelRow.getCell(columnindex);
					contents[columnindex] = this.getDataFromExcel();
				}
				break;
			}
		}
		return rowIndex + 1;
	}
	
	public String[] getContents(){
		return this.contents;
	}
	
	static public boolean writeExcelFile(ArrayList<excelData> dataList){
		/*-�Է�-*/
		// outExcelFile : ����� ���� ���� 
		// contents : ����� �����͵��� �׸��
		// dataList : ����� ������ ����Ʈ
		/*-���-*/
		// true : ���� ���� ����
		// false : ���� ���� ����
		// ��� ��� ��
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Sheet1");
		XSSFRow row;
		
		long time = System.currentTimeMillis();
		SimpleDateFormat dayTime = new SimpleDateFormat("yyyyMMdd_HHmmss");
		String outExcelFile = dayTime.format(new Date(time));
		outExcelFile += "_�������OUTPUT.xlsx";
		
		String[] contents = {"���ID","����","��/��","��/��/��","��/��/��(��)","����","����","�浵","Pot ��","RSRP ���"};
		
		int i, j;

		i = 0;
		row = sheet.createRow(0);
		for(String ctt : contents){
			row.createCell(i).setCellValue(ctt);
			i++;
		}
		
		i = 1;
		for(excelData data : dataList){
			row = sheet.createRow(i);
			j = 0;
			for(String ctt : contents){
				row.createCell(j).setCellValue(data.getData(ctt));
				j++;
			}
			i++;
		}
		
		FileOutputStream outFile;
		try{
			outFile = new FileOutputStream(outExcelFile);
			workbook.write(outFile);
			outFile.close();
			workbook.close();
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
}