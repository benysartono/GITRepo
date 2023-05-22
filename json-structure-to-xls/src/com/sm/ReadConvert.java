package com.sm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
//import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

//import org.apache.poi.hssf.usermodel.HSSFRow;
//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
//import org.apache.poi.ss.usermodel.DateUtil;
//import org.apache.poi.ss.usermodel.FillPatternType;
//import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
//import org.apache.poi.xssf.usermodel.XSSFFont;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadConvert {
	
	static Workbook workbook = new HSSFWorkbook();
	static Sheet sheet;
	static Row rowhead;

	public static void main(String[] args) {

		String outFile = args[0];
		String inFile = args[1];
		String sheetNm = args[2];
		String moduleNm = args[3];
		String ddId = args[4];
		String ifId = args[5];
		String entityNm = args[6];
		String entityCall = args[7];
		String msgType = args[8];

		BufferedReader reader;
		sheet = workbook.createSheet(sheetNm);

		int currRow = 2;
		int currCell = 9;
		String cellContent = "";

		// XSSFWorkbook workbook = new XSSFWorkbook();
		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setVerticalAlignment(VerticalAlignment.TOP);
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setFillBackgroundColor(IndexedColors.DARK_BLUE.getIndex());
		
		rowhead = sheet.createRow(currRow);
		rowhead.createCell(2).setCellValue("Module");
		rowhead.createCell(3).setCellValue("Detailed Design ID");
		rowhead.createCell(4).setCellValue("Mapping Specification ID");
		rowhead.createCell(5).setCellValue("Entity(System)");
		rowhead.createCell(7).setCellValue("Message");
		currRow = currRow +1;
		rowhead = sheet.createRow(currRow);
		rowhead.createCell(5).setCellValue("Name");
		rowhead.createCell(6).setCellValue("Caller/Callee");
		rowhead.createCell(7).setCellValue("Type");
		rowhead.createCell(8).setCellValue("No");
		rowhead.createCell(9).setCellValue("Paramter");
		rowhead.createCell(16).setCellValue("Type/Length");
		rowhead.createCell(17).setCellValue("Multiplicity");
		rowhead.createCell(18).setCellValue("Attribute Name");
		rowhead.createCell(19).setCellValue("Description");
		rowhead.createCell(20).setCellValue("Code Value");
		rowhead.createCell(21).setCellValue("Key Name (0 Level)");
		currRow = currRow +1;
		rowhead = sheet.createRow(currRow);
		rowhead.createCell(2).setCellValue("1");
		rowhead.createCell(3).setCellValue("2");
		rowhead.createCell(4).setCellValue("3");
		rowhead.createCell(5).setCellValue("4");
		rowhead.createCell(6).setCellValue("5");
		rowhead.createCell(7).setCellValue("6");
		rowhead.createCell(8).setCellValue("7");
		rowhead.createCell(16).setCellValue("8");
		rowhead.createCell(17).setCellValue("9");
		rowhead.createCell(18).setCellValue("10");
		rowhead.createCell(19).setCellValue("11");
		rowhead.createCell(20).setCellValue("12");
		rowhead.createCell(21).setCellValue("13");
		currRow = currRow +1;
		rowhead = sheet.createRow(currRow);
		
		//sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol));
		sheet.addMergedRegionUnsafe(new CellRangeAddress(2, 3, 2, 2));
		sheet.addMergedRegionUnsafe(new CellRangeAddress(2, 3, 3, 3));
		sheet.addMergedRegionUnsafe(new CellRangeAddress(2, 3, 4, 4));
		sheet.addMergedRegionUnsafe(new CellRangeAddress(2, 2, 5, 6));
		sheet.addMergedRegionUnsafe(new CellRangeAddress(2, 2, 7, 20));
		sheet.addMergedRegionUnsafe(new CellRangeAddress(3, 3, 9, 15));
		sheet.addMergedRegionUnsafe(new CellRangeAddress(4, 4, 9, 15));
		
		/*
		 * HSSFWorkbook workbook = new HSSFWorkbook(); HSSFSheet sheet =
		 * workbook.createSheet("IF_CLB_080"); HSSFRow rowhead;
		 */

		try {
			FileOutputStream fileOut = new FileOutputStream(outFile);
			reader = new BufferedReader(new FileReader(inFile));
			int c = 0;
			int prevC = 0;
			int itemCnt = 0;

			while ((c = reader.read()) != -1) { // Read char by Char
				char character = (char) c; // converting integer to char
				if (c == 123) { // {
					// rowhead.createCell(currCell).setCellValue(cellContent);
					rowhead.createCell(16).setCellValue("Object");
					writeProperty(rowhead, moduleNm, ddId, ifId, entityNm, entityCall, msgType, ++itemCnt);
					currRow = currRow + 1;
					currCell = currCell + 1;
					cellContent = "";
					rowhead = sheet.createRow(currRow);
				} else if (c == 125) { // }
					rowhead.createCell(16).setCellValue(cellContent);
					writeProperty(rowhead, moduleNm, ddId, ifId, entityNm, entityCall, msgType, ++itemCnt);
					currCell = currCell - 1;
					cellContent = "";
					if ((prevC != 93) && (prevC != 125)) {
						currRow = currRow + 1;
						rowhead = sheet.createRow(currRow);
					}
				} else if (c == 91) { // [
					// rowhead.createCell(currCell).setCellValue(cellContent);
					rowhead.createCell(16).setCellValue("List");
					writeProperty(rowhead, moduleNm, ddId, ifId, entityNm, entityCall, msgType, ++itemCnt);
					currRow = currRow + 1;
					currCell = currCell + 1;
					cellContent = "";
					rowhead = sheet.createRow(currRow);
				} else if (c == 93) { // ]
					rowhead.createCell(currCell).setCellValue(cellContent);
					writeProperty(rowhead, moduleNm, ddId, ifId, entityNm, entityCall, msgType, ++itemCnt);
					currCell = currCell - 1;
					cellContent = "";
					if ((prevC != 93) && (prevC != 125)) {
						currRow = currRow + 1;
						rowhead = sheet.createRow(currRow);
					}
				} else if (c == 34) { // "
				} else if (c == 58) { // :
					rowhead.createCell(currCell).setCellValue(cellContent);
					writeProperty(rowhead, moduleNm, ddId, ifId, entityNm, entityCall, msgType, ++itemCnt);
					cellContent = "";
				} else if (c == 44) { // ,
					rowhead.createCell(16).setCellValue(cellContent);
					writeProperty(rowhead, moduleNm, ddId, ifId, entityNm, entityCall, msgType, ++itemCnt);
					currRow = currRow + 1;
					cellContent = "";
					rowhead = sheet.createRow(currRow);
				} else {
					cellContent = cellContent + String.valueOf(character);
				}
				prevC = c;
			}

			workbook.write(fileOut);
			// closing the Stream
			fileOut.close();
			workbook.close();
			// closing the workbook
			// workbook.close();
			// prints the message on the console
			System.out.println("Excel file has been generated successfully.");

			// reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void writeProperty(Row rowhead, String moduleNm, String ddId, String ifId, String entityNm, String entityCall, String msgType, int itemCnt) {
		rowhead.createCell(2).setCellValue(moduleNm);
		rowhead.createCell(3).setCellValue(ddId);
		rowhead.createCell(4).setCellValue(ifId);
		rowhead.createCell(5).setCellValue(entityNm);
		rowhead.createCell(6).setCellValue(entityCall);
		rowhead.createCell(7).setCellValue(msgType);
		rowhead.createCell(8).setCellValue(itemCnt);
	}

}
