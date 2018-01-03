package com.aoyuanbo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;

import javax.swing.JPanel;
import javax.swing.table.AbstractTableModel;

import com.csvreader.CsvReader;

public class PreviewPanel extends JPanel {
	private String fileType;
	private Character separator;
	private String graphType;
	private String encodingFormat;
	private Object[][] data;
	private String[] rowName;
	private File file;

	/**
	 * 获取ImportFileFrame界面的设置
	 * 
	 * @param fileType
	 *            文件类型
	 * @param graphType
	 *            图类型
	 * @param separator
	 *            分隔符
	 * @param encodingFormat
	 *            编码格式
	 * @param file
	 *            文件路径
	 *
	 */
	public void setAttr(String fileType, String graphType, String separator, String encodingFormat, File file) {

		this.fileType = fileType;
		this.graphType = graphType;
		switch (separator) {
		case "逗号":
			this.separator = ',';
			break;
		case "分号":
			this.separator = ';';
			break;
		case "制表符Tab":
			this.separator = '	';
			break;
		case "空格":
			this.separator = ' ';
			break;
		default:
			break;
		}
		this.encodingFormat = encodingFormat;
		this.file = file;

	}

	public PreviewPanel() {
		setSize(500, 450);
		setLayout(null);

	}

	/**
	 * 将读入的文件显示出来
	 * 
	 * 
	 */
	public class MyTable extends AbstractTableModel {

		int row = readFile(file).length;
		int column = readFile(file)[0].length;
		
		@Override
		public int getRowCount() {
			return row;
		}

		@Override
		public int getColumnCount() {
			return column;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			data = readFile(file);
			return data[rowIndex][columnIndex];
		}

	}

	/**
	 * 读取文件
	 * 
	 */
	public Object[][] readFile(File file) {
		String data[][] = null;
		try {

			CsvReader cReader = new CsvReader(file.toString(), separator, Charset.forName(encodingFormat));
			String[][] data1 = new String[1][cReader.getColumnCount()];
			int i = 0;
			while (cReader.readRecord()) {
				if (data1.length > i) {
					data1[i] = cReader.getRawRecord().split(",");
					i++;
				} else {
					String[][] datatemp = new String[data1.length + 1][data1[0].length];
					System.arraycopy(data1, 0, datatemp, 0, data1.length);
					data1 = datatemp;
					data1[i] = cReader.getRawRecord().split(",");
					i++;
				}

			}
			data = data1;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return data;
	}
}
