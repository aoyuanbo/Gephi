package com.aoyuanbo.frame;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.aoyuanbo.GetData;

class ImportExcelFrame {
	private static Frame mainframe;
	
	static private JTable dataTable = new JTable();
	static private Boolean ischoose = false;
	static private Boolean columnChoose = false;
	static private Font font = new Font("微软雅黑", Font.PLAIN, 13);
	static private JMenuItem insertIdMenuItem;
	static private JMenuItem targetMenuItem;
	static private JMenuItem addSourceMenuItem;
	static private JMenuItem addTargetMenuItem;	
	static private DefaultTableModel nodeTableModel;
	static private DefaultTableModel edgeTableModel;
	static private ShowDataFrame showDataFrame;
	
	static private String graphType;
	static private String encodingFormat;

	public static Frame getMainframe() {
		return mainframe;
	}


	public static void setMainframe(Frame mainframe) {
		ImportExcelFrame.mainframe = mainframe;
	}
	
	public static String getGraphType() {
		if(graphType==null){
			graphType="有向图";
		}
		return graphType;
	}

	public static String getEncodingFormat() {
		if(encodingFormat==null){
			encodingFormat="GBK";
		}
		return encodingFormat;
	}

	public static void importExcel(String graphType, String encondingFormat, File file) {		
		
		ImportExcelFrame.graphType=graphType;
		ImportExcelFrame.encodingFormat=encondingFormat;
		String[][] data = null;
		try {

			data = GetData.getData(file, 1);
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		String[] column=null;
		for(int i=0;i<data[0].length;i++){
			if(data[0][i].equals("")){
				column=new String[i];
			}
		}
		for (int i = 0; i < column.length; i++) {
				column[i]="";
			
		}

		JPopupMenu rightClickJp = new JPopupMenu();
		JMenuItem idMenuItem = new JMenuItem("复制到Id列");
		idMenuItem.setFont(font);
		idMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// 按列选取
				if (columnChoose == Boolean.TRUE) {
					int k;
					int[] column = dataTable.getSelectedColumns();
					List<String> dataSelected = new ArrayList<>();
					for (int i = 0; i < column.length; i++) {
						k = dataTable.convertColumnIndexToModel(column[i]);
						for (int j = 0; j < dataTable.getRowCount(); j++) {
							dataSelected.add((String) dataTable.getValueAt(j, k));
						}
					}
					Object[] tempData = dataSelected.toArray();
					int temp = JOptionPane.showConfirmDialog(null, "是否将内容复制到Label列", "", JOptionPane.YES_NO_OPTION); // 是返回0，否返回1

					if (nodeTableModel == null) {
						nodeTableModel = new DefaultTableModel();
					} else {
						nodeTableModel.setColumnCount(0);
						nodeTableModel.setRowCount(0);
					}
					if (temp == 0) {

						nodeTableModel.addColumn("Id", tempData);
						nodeTableModel.addColumn("Label", tempData);
					} else {
						nodeTableModel.addColumn("Id", tempData);
						nodeTableModel.addColumn("Label");
					}
					if (showDataFrame == null) {
						showDataFrame = new ShowDataFrame(nodeTableModel);
					} else {
						showDataFrame.setDefaultTableModel(nodeTableModel);
						showDataFrame.show();
					}
					insertIdMenuItem.setEnabled(true);
				}
				// 自由选取
				else {
					int[] column = dataTable.getSelectedColumns();
					int[] row = dataTable.getSelectedRows();
					List<String> dataSelected = new ArrayList<>();
					for (int i = 0; i < column.length; i++) {
						for (int j = 0; j < row.length; j++) {
							dataSelected.add((String) dataTable.getValueAt(row[j], column[i]));
						}
					}

					Object[] tempData = dataSelected.toArray();

					// 是返回0，否返回1
					int temp = JOptionPane.showConfirmDialog(null, "是否将内容复制到Label列", "", JOptionPane.YES_NO_OPTION);

					// 判断节点表是否存在
					if (nodeTableModel == null) {
						nodeTableModel = new DefaultTableModel();
						if (temp == 0) {
							nodeTableModel.addColumn("Id", tempData);
							nodeTableModel.addColumn("Label", tempData);
						} else {
							nodeTableModel.addColumn("Id", tempData);
							nodeTableModel.addColumn("Label");
						}
					} else {
						if (temp == 0) {
							nodeTableModel.setColumnCount(0);
							nodeTableModel.setRowCount(0);
							nodeTableModel.addColumn("Id", tempData);
							nodeTableModel.addColumn("Label", tempData);
						} else {
							nodeTableModel.setColumnCount(0);
							nodeTableModel.setRowCount(0);
							nodeTableModel.addColumn("Id", tempData);
							nodeTableModel.addColumn("Label");
						}

					}

					if (showDataFrame == null) {
						showDataFrame = new ShowDataFrame(nodeTableModel);
					} else {
						showDataFrame.setDefaultTableModel(nodeTableModel);
						showDataFrame.show();
					}
					insertIdMenuItem.setEnabled(true);
				}
			}
		});

		insertIdMenuItem = new JMenuItem("插入到Id列");
		insertIdMenuItem.setFont(font);
		insertIdMenuItem.setEnabled(false);
		insertIdMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (columnChoose == Boolean.TRUE) {
					int k;
					int[] column = dataTable.getSelectedColumns();
					List<String> dataSelected = new ArrayList<>();
					for (int i = 0; i < column.length; i++) {
						k = dataTable.convertColumnIndexToModel(column[i]);
						for (int j = 0; j < dataTable.getRowCount(); j++) {
							dataSelected.add((String) dataTable.getValueAt(j, k));
						}
					}
					Object[] tempData = dataSelected.toArray();

					// 是返回0，否返回1
					int temp = JOptionPane.showConfirmDialog(null, "是否将内容插入到Label列", "", JOptionPane.YES_NO_OPTION);

					if (temp == 0) {
						int oldRowCount = nodeTableModel.getRowCount();
						nodeTableModel.setRowCount(oldRowCount + dataSelected.size());
						for (int i = oldRowCount, j = 0; i < nodeTableModel.getRowCount(); i++, j++) {
							nodeTableModel.setValueAt(dataSelected.get(j), i, 0);
							nodeTableModel.setValueAt(dataSelected.get(j), i, 1);
						}
					} else {
						int oldRowCount = nodeTableModel.getRowCount();
						nodeTableModel.setRowCount(oldRowCount + dataSelected.size());
						for (int i = oldRowCount, j = 0; i < nodeTableModel.getRowCount(); i++, j++) {
							nodeTableModel.setValueAt(dataSelected.get(j), i, 0);
						}
					}
					if (showDataFrame == null) {
						showDataFrame = new ShowDataFrame(nodeTableModel);
					} else {
						showDataFrame.setDefaultTableModel(nodeTableModel);
						showDataFrame.show();
					}
				} else {
					int[] column = dataTable.getSelectedColumns();
					int[] row = dataTable.getSelectedRows();
					List<String> dataSelected = new ArrayList<>();
					for (int i = 0; i < column.length; i++) {
						for (int j = 0; j < row.length; j++) {
							dataSelected.add((String) dataTable.getValueAt(row[j], column[i]));
						}
					}
					Object[] tempData = dataSelected.toArray();
					int temp = JOptionPane.showConfirmDialog(null, "是否将内容插入到Label列", "", JOptionPane.YES_NO_OPTION); // 是返回0，否返回1

					if (temp == 0) {
						int oldRowCount = nodeTableModel.getRowCount();
						nodeTableModel.setRowCount(oldRowCount + dataSelected.size());
						for (int i = oldRowCount, j = 0; i < nodeTableModel.getRowCount(); i++, j++) {
							nodeTableModel.setValueAt(dataSelected.get(j), i, 0);
							nodeTableModel.setValueAt(dataSelected.get(j), i, 1);
						}
					} else {
						int oldRowCount = nodeTableModel.getRowCount();
						nodeTableModel.setRowCount(oldRowCount + dataSelected.size());
						for (int i = oldRowCount, j = 0; i < nodeTableModel.getRowCount(); i++, j++) {
							nodeTableModel.setValueAt(dataSelected.get(j), i, 0);
						}
					}
					if (showDataFrame == null) {
						showDataFrame = new ShowDataFrame(nodeTableModel);
					} else {
						showDataFrame.setDefaultTableModel(nodeTableModel);
						showDataFrame.show();
					}
				}

			}
		});

		JMenuItem sourceMenuItem = new JMenuItem("复制到Source列");
		sourceMenuItem.setFont(font);
		sourceMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// 按列选取
				if (columnChoose == Boolean.TRUE) {
					int k;
					int[] column = dataTable.getSelectedColumns();
					List<String> dataSelected = new ArrayList<>();
					for (int i = 0; i < column.length; i++) {
						k = dataTable.convertColumnIndexToModel(column[i]);
						for (int j = 0; j < dataTable.getRowCount(); j++) {
							dataSelected.add((String) dataTable.getValueAt(j, k));
						}
					}
					Object[] tempData = dataSelected.toArray();

					if (edgeTableModel == null) {
						edgeTableModel = new DefaultTableModel();
						edgeTableModel.addColumn("Source", tempData);
					} else {
						int columnCount = edgeTableModel.getColumnCount();
						while (columnCount != 0) {
							if (edgeTableModel.getColumnName(columnCount - 1).equals("Target")) {
								edgeTableModel.addColumn("Source", tempData);
							} else {
								edgeTableModel.setColumnCount(0);
								edgeTableModel.addColumn("Source", tempData);
							}
							columnCount--;
						}
					}

					if (showDataFrame == null) {
						showDataFrame = new ShowDataFrame(edgeTableModel);
					} else {
						showDataFrame.setDefaultTableModel(edgeTableModel);
						showDataFrame.show();
					}

				}
				// 自由选取
				else {
					int[] column = dataTable.getSelectedColumns();
					int[] row = dataTable.getSelectedRows();
					List<String> dataSelected = new ArrayList<>();
					for (int i = 0; i < column.length; i++) {
						for (int j = 0; j < row.length; j++) {
							dataSelected.add((String) dataTable.getValueAt(row[j], column[i]));
						}
					}

					Object[] tempData = dataSelected.toArray();
					System.out.println(dataSelected.size());

					// 判断节点表是否存在
					if (edgeTableModel == null) {
						edgeTableModel = new DefaultTableModel();
						edgeTableModel.addColumn("Source", tempData);
					} else {
						int columnCount = edgeTableModel.getColumnCount();
						while (columnCount != 0) {
							if (edgeTableModel.getColumnName(columnCount - 1).equals("Target")) {
								edgeTableModel.addColumn("Source", tempData);
							} else {
								edgeTableModel.setColumnCount(0);
								edgeTableModel.addColumn("Source", tempData);
							}
							columnCount--;
						}
					}

					if (showDataFrame == null) {
						showDataFrame = new ShowDataFrame(edgeTableModel);
					} else {
						showDataFrame.setDefaultTableModel(edgeTableModel);
						showDataFrame.show();
					}

				}
				targetMenuItem.setEnabled(true);
				addSourceMenuItem.setEnabled(true);
				addTargetMenuItem.setEnabled(false);
			}
		});

		targetMenuItem = new JMenuItem("复制到Target列");
		targetMenuItem.setFont(font);
		targetMenuItem.setEnabled(false);
		targetMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				// 按列选取
				if (columnChoose == Boolean.TRUE) {
					int k;
					int[] column = dataTable.getSelectedColumns();
					List<String> dataSelected = new ArrayList<>();
					for (int i = 0; i < column.length; i++) {
						k = dataTable.convertColumnIndexToModel(column[i]);
						for (int j = 0; j < dataTable.getRowCount(); j++) {
							dataSelected.add((String) dataTable.getValueAt(j, k));
						}
					}
					Object[] tempData = dataSelected.toArray();

					if (edgeTableModel == null) {
						edgeTableModel = new DefaultTableModel();
						edgeTableModel.addColumn("Target", tempData);
					} else {
						int columnCount = edgeTableModel.getColumnCount();
						while (columnCount != 0) {
							if (edgeTableModel.getColumnName(columnCount - 1).equals("Source")) {
								edgeTableModel.addColumn("Target", tempData);

							} else {
								edgeTableModel.setColumnCount(0);
								edgeTableModel.addColumn("Target", tempData);
							}
							columnCount--;
						}
					}

					if (showDataFrame == null) {
						showDataFrame = new ShowDataFrame(edgeTableModel);
					} else {
						showDataFrame.setDefaultTableModel(edgeTableModel);
						showDataFrame.show();
					}

				}
				// 自由选取
				else {
					int[] column = dataTable.getSelectedColumns();
					int[] row = dataTable.getSelectedRows();
					List<String> dataSelected = new ArrayList<>();
					for (int i = 0; i < column.length; i++) {
						for (int j = 0; j < row.length; j++) {
							dataSelected.add((String) dataTable.getValueAt(row[j], column[i]));
						}
					}

					Object[] tempData = dataSelected.toArray();
					System.out.println(dataSelected.size());
					// 是返回0，否返回1

					// 判断节点表是否存在
					if (edgeTableModel == null) {
						edgeTableModel = new DefaultTableModel();
						edgeTableModel.addColumn("Target", tempData);
					} else {
						int columnCount = edgeTableModel.getColumnCount();
						while (columnCount != 0) {
							if (edgeTableModel.getColumnName(columnCount - 1).equals("Source")) {
								edgeTableModel.addColumn("Target", tempData);

							} else {
								edgeTableModel.setColumnCount(0);
								edgeTableModel.addColumn("Target", tempData);
							}
							columnCount--;
						}
					}

					if (showDataFrame == null) {
						showDataFrame = new ShowDataFrame(edgeTableModel);
					} else {
						showDataFrame.setDefaultTableModel(edgeTableModel);
						showDataFrame.show();
					}
					insertIdMenuItem.setEnabled(true);
				}
				addTargetMenuItem.setEnabled(true);
				targetMenuItem.setEnabled(false);
			}
		});

		addSourceMenuItem = new JMenuItem("插入到Source列");
		addSourceMenuItem.setFont(font);
		addSourceMenuItem.setEnabled(false);
		addSourceMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (columnChoose == Boolean.TRUE) {
					int k;
					int[] column = dataTable.getSelectedColumns();
					List<String> dataSelected = new ArrayList<>();
					for (int i = 0; i < column.length; i++) {
						k = dataTable.convertColumnIndexToModel(column[i]);
						for (int j = 0; j < dataTable.getRowCount(); j++) {
							dataSelected.add((String) dataTable.getValueAt(j, k));
						}
					}

					int oldRowCount = edgeTableModel.getRowCount();
					int finalRow=0;
					edgeTableModel.setRowCount(oldRowCount + dataSelected.size());
					for (int i = 0, j = 0; j < dataSelected.size(); i++) {
						if (edgeTableModel.getValueAt(i, 0)==null) {
							edgeTableModel.setValueAt(dataSelected.get(j), i, 0);
							j++;
						}
						finalRow=i;
					}
					
					if(finalRow>oldRowCount){
						edgeTableModel.setRowCount(finalRow);
					}else {
						edgeTableModel.setRowCount(oldRowCount);
					}
					
					if (showDataFrame == null) {
						showDataFrame = new ShowDataFrame(edgeTableModel);
					} else {
						showDataFrame.setDefaultTableModel(edgeTableModel);
						showDataFrame.show();
					}
				} else {
					int[] column = dataTable.getSelectedColumns();
					int[] row = dataTable.getSelectedRows();
					List<String> dataSelected = new ArrayList<>();
					for (int i = 0; i < column.length; i++) {
						for (int j = 0; j < row.length; j++) {
							dataSelected.add((String) dataTable.getValueAt(row[j], column[i]));
						}
					}

					int oldRowCount = edgeTableModel.getRowCount();
					int finalRow=0;
					edgeTableModel.setRowCount(oldRowCount + dataSelected.size());
					for (int i = 0, j = 0; j < dataSelected.size(); i++) {
						if (edgeTableModel.getValueAt(i, 0)==null) {
							edgeTableModel.setValueAt(dataSelected.get(j), i, 0);
							j++;
						}
						finalRow=i;
					}
					
					if(finalRow>oldRowCount){
						edgeTableModel.setRowCount(finalRow);
					}else {
						edgeTableModel.setRowCount(oldRowCount);
					}

				}
				if (showDataFrame == null) {
					showDataFrame = new ShowDataFrame(edgeTableModel);
				} else {
					showDataFrame.setDefaultTableModel(edgeTableModel);
					showDataFrame.show();
				}
			}

		});

		addTargetMenuItem = new JMenuItem("插入到Target列");
		addTargetMenuItem.setFont(font);
		addTargetMenuItem.setEnabled(false);
		addTargetMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (columnChoose == Boolean.TRUE) {
					int k;
					int[] column = dataTable.getSelectedColumns();
					List<String> dataSelected = new ArrayList<>();
					for (int i = 0; i < column.length; i++) {
						k = dataTable.convertColumnIndexToModel(column[i]);
						for (int j = 0; j < dataTable.getRowCount(); j++) {
							dataSelected.add((String) dataTable.getValueAt(j, k));
						}
					}
					int oldRowCount = edgeTableModel.getRowCount();
					int finalRow=0;
					edgeTableModel.setRowCount(oldRowCount + dataSelected.size());
					for (int i = 0, j = 0; j < dataSelected.size(); i++) {
						if (edgeTableModel.getValueAt(i, 1)==null) {
							edgeTableModel.setValueAt(dataSelected.get(j), i, 1);
							j++;
						}
						finalRow=i;
					}
					
					if(finalRow>oldRowCount){
						edgeTableModel.setRowCount(finalRow);
					}else {
						edgeTableModel.setRowCount(oldRowCount);
					}

					if (showDataFrame == null) {
						showDataFrame = new ShowDataFrame(edgeTableModel);
					} else {
						showDataFrame.setDefaultTableModel(edgeTableModel);
						showDataFrame.show();
					}
				} else {
					int[] column = dataTable.getSelectedColumns();
					int[] row = dataTable.getSelectedRows();
					List<String> dataSelected = new ArrayList<>();
					for (int i = 0; i < column.length; i++) {
						for (int j = 0; j < row.length; j++) {
							dataSelected.add((String) dataTable.getValueAt(row[j], column[i]));
						}
					}
					
					int oldRowCount = edgeTableModel.getRowCount();
					int finalRow=0;
					edgeTableModel.setRowCount(oldRowCount + dataSelected.size());
					for (int i = 0, j = 0; j < dataSelected.size(); i++) {
						if (edgeTableModel.getValueAt(i, 1)==null) {
							edgeTableModel.setValueAt(dataSelected.get(j), i, 1);
							j++;
						}
						finalRow=i;
					}
					
					if(finalRow>oldRowCount){
						edgeTableModel.setRowCount(finalRow);
					}else {
						edgeTableModel.setRowCount(oldRowCount);
					}

				}
				if (showDataFrame == null) {
					showDataFrame = new ShowDataFrame(edgeTableModel);
				} else {
					showDataFrame.setDefaultTableModel(edgeTableModel);
					showDataFrame.show();
				}

			}
		});

		DefaultTableModel myModel = new DefaultTableModel(data, column) {

			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		dataTable.setModel(myModel);
		dataTable.setCellSelectionEnabled(true);
		dataTable.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {

				if (ischoose == false) {
					dataTable.setColumnSelectionAllowed(false);
					dataTable.setRowSelectionAllowed(false);
				} else {
					// 单击鼠标右键事件
					if (e.isMetaDown())
						// 显示菜单
						rightClickJp.show(dataTable, e.getX(), e.getY());
				}

			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}
		});

		JPopupMenu jPopupMenu = new JPopupMenu();
		JMenuItem jMenuItem_column = new JMenuItem("按列选择");
		jMenuItem_column.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ischoose = true;
				columnChoose = true;
				dataTable.setCellSelectionEnabled(false);
				dataTable.setColumnSelectionAllowed(true);
			}
		});

		JMenuItem jMenuItem_free = new JMenuItem("自由选取");
		jMenuItem_free.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ischoose = true;
				columnChoose = false;
				dataTable.setCellSelectionEnabled(true);
			}
		});
		
		JRadioButton createClassColumn=new JRadioButton();
		jPopupMenu.add(jMenuItem_column);
		jPopupMenu.add(jMenuItem_free);

		JScrollPane dataScrollPane = new JScrollPane(dataTable);
		dataScrollPane.setSize(500, 500);

		JButton nodeButton = new JButton("节点设置▼");
		nodeButton.setBounds(100, 415, 100, 30);
		nodeButton.add(jPopupMenu);
		nodeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				jPopupMenu.show(nodeButton, 10, nodeButton.getHeight());
				rightClickJp.add(idMenuItem);
				rightClickJp.add(insertIdMenuItem);
				rightClickJp.remove(sourceMenuItem);
				rightClickJp.remove(targetMenuItem);
				rightClickJp.remove(addSourceMenuItem);
				rightClickJp.remove(addTargetMenuItem);

			}
		});

		JButton edgeButton = new JButton("边设置▼");
		edgeButton.setBounds(500, 415, 100, 30);
		edgeButton.add(jPopupMenu);
		edgeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				jPopupMenu.show(edgeButton, 10, edgeButton.getHeight());
				rightClickJp.remove(idMenuItem);
				rightClickJp.remove(insertIdMenuItem);
				rightClickJp.add(sourceMenuItem);
				rightClickJp.add(targetMenuItem);
				rightClickJp.add(addSourceMenuItem);
				rightClickJp.add(addTargetMenuItem);
			}
		});

		JFrame jFrame = new JFrame("导入表格");
		jFrame.setLayout(null);
		jFrame.setLocation(350, 100);
		jFrame.setSize(700, 500);
		jFrame.add(dataScrollPane);
		jFrame.add(nodeButton);
		jFrame.add(edgeButton);
		jFrame.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				dataScrollPane.setSize(jFrame.getWidth() - 15, jFrame.getHeight() - 100);
				nodeButton.setLocation(jFrame.getWidth() / 6, jFrame.getHeight() - 85);
				edgeButton.setLocation(jFrame.getWidth() * 2 / 3, jFrame.getHeight() - 85);
			}
		});
		jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		jFrame.show();

	}

}