/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.aoyuanbo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.poi.hslf.blip.Metafile.Header;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class GetData {
	/**
	 * 读取Excel的内容，第一维数组存储的是一行中格列的值，二维数组存储的是多少个行
	 * 
	 * @param file
	 *            读取数据的源Excel
	 * @param ignoreRows
	 *            读取数据忽略的行数，比喻行头不需要读入 忽略的行数为1
	 * @return 读出的Excel中数据的内容
	 * @throws FileNotFoundException
	 * @throws IOException
	 */

	public static String[][] getData(File file, int ignoreRows) throws FileNotFoundException, IOException {
		List<String[]> result = new ArrayList<String[]>();
		int rowSize = 0;
		String fileName = file.getPath();
		boolean isE2007 = false; // 判断是否是excel2007格式
		if (fileName.endsWith("xlsx"))
			isE2007 = true;
		try {
			InputStream input = new FileInputStream(fileName); // 建立输入流
			Workbook wb = null;
			// 根据文件格式(2003或者2007)来初始化
			if (isE2007) {
				XSSFWorkbook wb1 = new XSSFWorkbook(input);
				XSSFCell cell = null;
				for (int sheetIndex = 0; sheetIndex < wb1.getNumberOfSheets(); sheetIndex++) {
					XSSFSheet st = wb1.getSheetAt(sheetIndex);
					// 第一行为标题，不取
					for (int rowIndex = ignoreRows; rowIndex <= st.getLastRowNum(); rowIndex++) {
						XSSFRow row = st.getRow(rowIndex);
						if (row == null) {
							continue;
						}
						int tempRowSize = row.getLastCellNum() + 1;
						if (tempRowSize > rowSize) {
							rowSize = tempRowSize;
						}
						String[] values = new String[rowSize];
						Arrays.fill(values, "");
						boolean hasValue = false;
						for (short columnIndex = 0; columnIndex <= row.getLastCellNum(); columnIndex++) {
							String value = "";
							cell = row.getCell(columnIndex);
							if (cell != null) {
								// 注意：一定要设成这个，否则可能会出现乱码

								switch (cell.getCellType()) {
								case Cell.CELL_TYPE_STRING:
									value = cell.getStringCellValue();
									break;
								case Cell.CELL_TYPE_NUMERIC:
									if (DateUtil.isCellDateFormatted(cell)) {
										Date date = cell.getDateCellValue();
										if (date != null) {
											value = new SimpleDateFormat("yyyy-MM-dd").format(date);
										} else {
											value = "";
										}
									} else {
										value = new DecimalFormat("0").format(cell.getNumericCellValue());
									}
									break;
								case Cell.CELL_TYPE_FORMULA:
									// 导入时如果为公式生成的数据则无值
									if (!cell.getStringCellValue().equals("")) {
										value = cell.getStringCellValue();
									} else {
										value = cell.getNumericCellValue() + "";
									}
									break;
								case Cell.CELL_TYPE_BLANK:
									break;
								case Cell.CELL_TYPE_ERROR:
									value = "";
									break;
								case Cell.CELL_TYPE_BOOLEAN:
									value = (cell.getBooleanCellValue() == true ? "Y" : "N");
									break;
								default:
									value = "";
								}
							}
							if (columnIndex == 0 && value.trim().equals("")) {
								break;
							}
							values[columnIndex] = rightTrim(value);
							hasValue = true;
						}

						if (hasValue) {
							result.add(values);
						}
					}
				}
				input.close();

			}

			else {
				HSSFWorkbook wb2 = new HSSFWorkbook(input);
				HSSFCell cell = null;
				for (int sheetIndex = 0; sheetIndex < wb2.getNumberOfSheets(); sheetIndex++) {
					HSSFSheet st = wb2.getSheetAt(sheetIndex);
					// 第一行为标题，不取
					for (int rowIndex = ignoreRows; rowIndex <= st.getLastRowNum(); rowIndex++) {
						HSSFRow row = st.getRow(rowIndex);
						if (row == null) {
							continue;
						}
						int tempRowSize = row.getLastCellNum() + 1;
						if (tempRowSize > rowSize) {
							rowSize = tempRowSize;
						}
						String[] values = new String[rowSize];
						Arrays.fill(values, "");
						boolean hasValue = false;
						for (short columnIndex = 0; columnIndex <= row.getLastCellNum(); columnIndex++) {
							String value = "";
							cell = row.getCell(columnIndex);
							if (cell != null) {
								// 注意：一定要设成这个，否则可能会出现乱码

								switch (cell.getCellType()) {
								case Cell.CELL_TYPE_STRING:
									value = cell.getStringCellValue();
									break;
								case Cell.CELL_TYPE_NUMERIC:
									if (DateUtil.isCellDateFormatted(cell)) {
										Date date = cell.getDateCellValue();
										if (date != null) {
											value = new SimpleDateFormat("yyyy-MM-dd").format(date);
										} else {
											value = "";
										}
									} else {
										value = new DecimalFormat("0").format(cell.getNumericCellValue());
									}
									break;
								case Cell.CELL_TYPE_FORMULA:
									// 导入时如果为公式生成的数据则无值
									if (!cell.getStringCellValue().equals("")) {
										value = cell.getStringCellValue();
									} else {
										value = cell.getNumericCellValue() + "";
									}
									break;
								case Cell.CELL_TYPE_BLANK:
									break;
								case Cell.CELL_TYPE_ERROR:
									value = "";
									break;
								case Cell.CELL_TYPE_BOOLEAN:
									value = (cell.getBooleanCellValue() == true ? "Y" : "N");
									break;
								default:
									value = "";
								}
							}
							if (columnIndex == 0 && value.trim().equals("")) {
								break;
							}
							values[columnIndex] = rightTrim(value);
							hasValue = true;
						}

						if (hasValue) {
							result.add(values);
						}
					}
				}
				input.close();
			}
		}catch (FileNotFoundException e) {
			// TODO: handle exception
			System.out.println("未发现文件");
			System.exit(1);
		} 
		catch (IOException ex) {
			ex.printStackTrace();
		}
		String[][] returnArray = new String[result.size()][rowSize];
		for (int i = 0; i < returnArray.length; i++) {
			returnArray[i] = result.get(i);
		}
		return returnArray;

	}

	/**
	 * 去掉字符串右边的空格
	 * 
	 * @param str
	 *            要处理的字符串
	 * @return 处理后的字符串 0x20 空格
	 */
	public static String rightTrim(String str) {
		if (str == null) {
			return "";
		}
		int length = str.length();
		for (int i = length - 1; i >= 0; i--) {
			if (str.charAt(i) != 0x20) {
				break;
			}
			length--;
		}
		return str.substring(0, length);
	}
	
	/** 
	 *获取文件的列名 
	 * 
	 * @param file
	 * 				文件路径
	 * 
	 * @return 读取的文件列名
	 *  
	 */
	public static String[] getHeader(File file){
		Workbook wb = null;  
		Sheet sheet;  
		Row row;
		String filepath=file.toString();
        String ext = filepath.substring(filepath.lastIndexOf("."));  
        try {  
            InputStream is = new FileInputStream(filepath);  
            if(".xls".equals(ext)){  
                wb = new HSSFWorkbook(is);  
            }else if(".xlsx".equals(ext)){  
                wb = new XSSFWorkbook(is);  
            }else{  
                wb=null;  
            }  
        } catch (FileNotFoundException e) {  
            
        } catch (IOException e) {  
            
        } 
        sheet = wb.getSheetAt(0);  
        row = sheet.getRow(0);  
        int colNum = row.getPhysicalNumberOfCells();  
        System.out.println("colNum:" + colNum);  
        String[] title = new String[colNum];  
        for (int i = 0; i < colNum; i++) {    
            title[i] = row.getCell(i).getStringCellValue();  
        }  
        return title; 
	} 
}
