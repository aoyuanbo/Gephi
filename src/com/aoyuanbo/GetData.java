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
	 * ��ȡExcel�����ݣ���һά����洢����һ���и��е�ֵ����ά����洢���Ƕ��ٸ���
	 * 
	 * @param file
	 *            ��ȡ���ݵ�ԴExcel
	 * @param ignoreRows
	 *            ��ȡ���ݺ��Ե�������������ͷ����Ҫ���� ���Ե�����Ϊ1
	 * @return ������Excel�����ݵ�����
	 * @throws FileNotFoundException
	 * @throws IOException
	 */

	public static String[][] getData(File file, int ignoreRows) throws FileNotFoundException, IOException {
		List<String[]> result = new ArrayList<String[]>();
		int rowSize = 0;
		String fileName = file.getPath();
		boolean isE2007 = false; // �ж��Ƿ���excel2007��ʽ
		if (fileName.endsWith("xlsx"))
			isE2007 = true;
		try {
			InputStream input = new FileInputStream(fileName); // ����������
			Workbook wb = null;
			// �����ļ���ʽ(2003����2007)����ʼ��
			if (isE2007) {
				XSSFWorkbook wb1 = new XSSFWorkbook(input);
				XSSFCell cell = null;
				for (int sheetIndex = 0; sheetIndex < wb1.getNumberOfSheets(); sheetIndex++) {
					XSSFSheet st = wb1.getSheetAt(sheetIndex);
					// ��һ��Ϊ���⣬��ȡ
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
								// ע�⣺һ��Ҫ��������������ܻ��������

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
									// ����ʱ���Ϊ��ʽ���ɵ���������ֵ
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
					// ��һ��Ϊ���⣬��ȡ
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
								// ע�⣺һ��Ҫ��������������ܻ��������

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
									// ����ʱ���Ϊ��ʽ���ɵ���������ֵ
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
			System.out.println("δ�����ļ�");
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
	 * ȥ���ַ����ұߵĿո�
	 * 
	 * @param str
	 *            Ҫ������ַ���
	 * @return �������ַ��� 0x20 �ո�
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
	 *��ȡ�ļ������� 
	 * 
	 * @param file
	 * 				�ļ�·��
	 * 
	 * @return ��ȡ���ļ�����
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
