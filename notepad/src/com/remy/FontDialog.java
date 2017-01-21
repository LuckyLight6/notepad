package com.remy;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class FontDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	public static final int CANCLE = 0;
	public static final int OK = 1;
	private static final String[] style = { "正常", "斜体", "粗体", "粗斜体" };
	private static final String[] size = { "8", "9", "10", "11", "12", "14", "16", "18", "20", "22", "24", "26", "28",
			"36", "48", "72" };
	private Font userFont = null;
	private int userSelect = CANCLE;
	private JFrame parent = null;
	private Container con;
	private JScrollPane namePane;
	private JScrollPane stylePane;
	private JScrollPane sizePane;
	private JPanel[] panel;
	private JLabel nameLabel;
	private JLabel styleLabel;
	private JLabel sizeLabel;
	private JTextField nameField;
	private JTextField styleField;
	private JTextField sizeField;
	private JList<String> nameList;
	private JList<String> styleList;
	private JList<String> sizeList;
	private JButton okButton;
	private JButton cancleButton;

	public FontDialog(JFrame owner) {
		super(owner, true);
		parent = owner;
		setTitle("字体");
		con = getContentPane();
		BoxLayout box = new BoxLayout(con, BoxLayout.Y_AXIS);
		con.setLayout(box);
		panel = new JPanel[4];
		for (int i = 0; i < 3; i++) {
			panel[i] = new JPanel();
			panel[i].setLayout(new GridLayout(1, 3));
		}
		panel[3] = new JPanel();
		panel[3].setLayout(new FlowLayout());
		nameLabel = new JLabel("字体");
		styleLabel = new JLabel("字形");
		sizeLabel = new JLabel("大小");
		panel[0].add(nameLabel);
		panel[0].add(styleLabel);
		panel[0].add(sizeLabel);
		nameField = new JTextField("Consolas");
		nameField.setColumns(12);
		nameField.setEditable(false);
		styleField = new JTextField("正常");
		styleField.setColumns(5);
		styleField.setEditable(false);
		sizeField = new JTextField("14");
		sizeField.setColumns(5);
		sizeField.setEditable(false);
		panel[1].add(nameField);
		panel[1].add(styleField);
		panel[1].add(sizeField);
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		String[] availableFonts = ge.getAvailableFontFamilyNames();
		nameList = new JList<String>(availableFonts);
		nameList.addListSelectionListener(new ListSelectionAdapter());
		namePane = new JScrollPane(nameList);
		styleList = new JList<String>(style);
		styleList.addListSelectionListener(new ListSelectionAdapter());
		stylePane = new JScrollPane(styleList);
		sizeList = new JList<String>(size);
		sizeList.addListSelectionListener(new ListSelectionAdapter());
		sizePane = new JScrollPane(sizeList);
		panel[2].add(namePane);
		panel[2].add(stylePane);
		panel[2].add(sizePane);
		okButton = new JButton("确定");
		okButton.addActionListener(new ActionAdapter());
		cancleButton = new JButton("取消");
		cancleButton.addActionListener(new ActionAdapter());
		panel[3].add(okButton);
		panel[3].add(cancleButton);
		for (int i = 0; i < 4; i++) {
			con.add(panel[i]);
		}
	}
	
	public int showFontDialog() {
		int x;
		int y;
		
		setSize(300, 300);
		if(parent != null) {
			x = parent.getX() + 30;
			y = parent.getY() + 30;
		} else {
			x = 150;
			y = 150;
		}
		
		setLocation(new Point(x, y));
		setVisible(true);
		
		return userSelect;
	}
	
	public Font getFont() {
		return userFont;
	}

	public class ListSelectionAdapter implements ListSelectionListener {
		@Override
		public void valueChanged(ListSelectionEvent e) {
			Object obj = e.getSource();
			
			if (obj == nameList) {
				nameField.setText((String)nameList.getSelectedValue());
			} 
			
			if (obj == styleList) {
				styleField.setText((String)styleList.getSelectedValue());
			}
			
			if (obj == sizeList) {
				sizeField.setText((String)sizeList.getSelectedValue());
			}
		}
	}

	public class ActionAdapter implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			int styleIndex = Font.PLAIN;
			int fontSize;
			
			if (e.getSource() == okButton) {
				if (styleField.getText().equals("正常")) {
					styleIndex = Font.PLAIN;
				}
				
				if (styleField.getText().equals("斜体")) {
					styleIndex = Font.ITALIC;
				}
				
				if (styleField.getText().equals("粗体")) {
					styleIndex = Font.BOLD;
				}
				
				if (styleField.getText().equals("粗斜体")) {
					styleIndex = Font.BOLD | Font.ITALIC;
				}
				
				fontSize = Integer.parseInt(sizeField.getText());
				userFont = new Font(nameField.getText(), styleIndex, fontSize);
				userSelect = OK;
				setVisible(false);
			} else {
				userSelect = CANCLE;
				setVisible(false);
			}
		}
	}
}
