package com.aoyuanbo.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

import com.aoyuanbo.Utils.IndexUtils;
import com.aoyuanbo.action.SearchAction;

import javax.swing.JTextArea;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SearchDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField searchText;
	private JTextArea resultText;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	
	private SearchAction searchAction=new SearchAction();	
	private String searchString;
	private String fieldString;
	private TopDocs resulTopDocs;
	
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		try {
//			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//			SearchDialog dialog = new SearchDialog();
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * Create the dialog.
	 */
	public SearchDialog() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e2) {
			System.err.println("´íÎó");
		}
		setResizable(false);
		setTitle("ËÑË÷");
		setBounds(450, 100, 500, 400);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panel_1 = new JPanel();
			contentPanel.add(panel_1, BorderLayout.NORTH);
			panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			{
				JLabel lblNewLabel = new JLabel("\u641C\u7D22\u5185\u5BB9\uFF1A");
				lblNewLabel.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 12));
				panel_1.add(lblNewLabel);
			}
			{
				searchText = new JTextField();
				searchText.setBackground(new Color(255, 255, 255));
				panel_1.add(searchText);
				searchText.setColumns(15);
			}
			{
				JRadioButton nameRadioButton = new JRadioButton("\u59D3\u540D");
				nameRadioButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						fieldString="name";
					}
				});
				buttonGroup.add(nameRadioButton);
				nameRadioButton.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 12));
				panel_1.add(nameRadioButton);
			}
			{
				JRadioButton birthRadioButton = new JRadioButton("\u51FA\u751F\u65E5\u671F");
				birthRadioButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						fieldString="birth";
					}
				});
				buttonGroup.add(birthRadioButton);
				birthRadioButton.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 12));
				panel_1.add(birthRadioButton);
			}
			{
				JRadioButton phoneRadioButton = new JRadioButton("\u7535\u8BDD");
				phoneRadioButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						fieldString="phone";
					}
				});
				buttonGroup.add(phoneRadioButton);
				phoneRadioButton.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 12));
				panel_1.add(phoneRadioButton);
			}
			{
				JRadioButton addressRadioButton = new JRadioButton("\u5730\u5740");
				addressRadioButton.addActionListener(
						(e)->fieldString="address");
				buttonGroup.add(addressRadioButton);
				addressRadioButton.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 12));
				panel_1.add(addressRadioButton);
			}
		}
		{
			JScrollPane scrollPane = new JScrollPane();
			contentPanel.add(scrollPane, BorderLayout.CENTER);
			{
				resultText = new JTextArea();
				resultText.setEditable(false);
				scrollPane.setViewportView(resultText);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("\u786E\u5B9A");
				//È·¶¨°´Å¥¶¯×÷
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						searchString=searchText.getText();
						searchAction.setIndexFile(IndexUtils.getIndexFile());
						try {
							if(!searchString.equals("")){
							resulTopDocs=searchAction.search(searchString, fieldString);
							IndexSearcher searcher=searchAction.getSearcher();
							for (ScoreDoc scoreDoc : resulTopDocs.scoreDocs) {		
									Document document=searcher.doc(scoreDoc.doc);
									resultText.setText(document.get(fieldString));
								}
							}else {
								JOptionPane.showMessageDialog(null, "ÇëÊäÈëËÑË÷ÄÚÈÝ£¡");
							}
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(null, "Î´¹¹½¨Ë÷Òý");
						}
						
					}
				});
				okButton.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 12));
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("\u53D6\u6D88");
				cancelButton.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 12));
				//È¡Ïû°´Å¥
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						SearchDialog.this.dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}

}
