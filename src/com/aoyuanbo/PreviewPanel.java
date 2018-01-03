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
	 * ��ȡImportFileFrame���������
	 * 
	 * @param fileType
	 *            �ļ�����
	 * @param graphType
	 *            ͼ����
	 * @param separator
	 *            �ָ���
	 * @param encodingFormat
	 *            �����ʽ
	 * @param file
	 *            �ļ�·��
	 *
	 */
	public void setAttr(String fileType, String graphType, String separator, String encodingFormat, File file) {

		this.fileType = fileType;
		this.graphType = graphType;
		switch (separator) {
		case "����":
			this.separator = ',';
			break;
		case "�ֺ�":
			this.separator = ';';
			break;
		case "�Ʊ��Tab":
			this.separator = '	';
			break;
		case "�ո�":
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
	 * ��������ļ���ʾ����
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
	 * ��ȡ�ļ�
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
