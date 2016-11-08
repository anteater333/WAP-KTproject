
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
	private String[] contents; // 항목명
	

	// 생성자
	public ExcelParser(String fileName, BinarySearchTree tmp) {
		// fileName : 엑셀 파일 이름
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
			// Swing으로 에러창 출력할 수 있으면 좋겠습니다.
			e.printStackTrace();
		}
	}

	// 엑셀 파일에서 각 열의 데이터 타입을 판단하여 알맞게 리턴
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

	// 엑셀 파일에서 표의 시작 지점을 찾아냄
	// 엑셀 파일로부터 항목을 결정
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
		/*-입력-*/
		// outExcelFile : 출력할 엑셀 파일 
		// contents : 출력할 데이터들의 항목들
		// dataList : 출력할 데이터 리스트
		/*-출력-*/
		// true : 파일 생성 성공
		// false : 파일 생성 실패
		// 출력 없어도 됨
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Sheet1");
		XSSFRow row;
		
		long time = System.currentTimeMillis();
		SimpleDateFormat dayTime = new SimpleDateFormat("yyyyMMdd_HHmmss");
		String outExcelFile = dayTime.format(new Date(time));
		outExcelFile += "_검증결과OUTPUT.xlsx";
		
		String[] contents = {"장비ID","장비명","시/도","시/군/구","읍/면/동(리)","번지","위도","경도","Pot 수","RSRP 평균"};
		
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