package com.remy;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ReplaceDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	private Container con;
	private JPanel panel1;
	private JPanel panel2;
	private JPanel panel3;
	private JTextArea text;
	private JLabel findLabel;
	private JLabel replaceLabel;
	private JTextField findField;
	private JTextField replaceField;
	private JCheckBox checkBox;
	private JButton findButton;
	private JButton replaceButton;
	private JButton replaceAllButton;
	private JButton cancleButton;
	private int start;
	private int index;

	public ReplaceDialog(JFrame owner, JTextArea text) {
		super(owner, false);

		start = 0;
		index = -1;
		this.text = text;
		panel1 = new JPanel();
		panel1.setLayout(new FlowLayout());
		panel2 = new JPanel();
		panel2.setLayout(new FlowLayout());
		panel3 = new JPanel();
		panel3.setLayout(new FlowLayout());
		findLabel = new JLabel("查找内容");
		findField = new JTextField(12);
		findButton = new JButton("查找下一个");
		findButton.addActionListener(new ActionAdapter());
		panel1.add(findLabel);
		panel1.add(findField);
		panel1.add(findButton);
		replaceLabel = new JLabel("替换为");
		replaceField = new JTextField(12);
		replaceButton = new JButton("替换");
		replaceButton.addActionListener(new ActionAdapter());
		panel2.add(replaceLabel);
		panel2.add(replaceField);
		panel2.add(replaceButton);
		checkBox = new JCheckBox("区分大小写");
		replaceAllButton = new JButton("全部替换");
		replaceAllButton.addActionListener(new ActionAdapter());
		cancleButton = new JButton("取消");
		cancleButton.addActionListener(new ActionAdapter());
		panel3.add(checkBox);
		panel3.add(replaceAllButton);
		panel3.add(cancleButton);
		con = getContentPane();
		con.setLayout(new FlowLayout());
		con.add(panel1);
		con.add(panel2);
		con.add(panel3);
		setTitle("替换");
		setSize(320, 180);
		setVisible(true);
		WindowUtil.setFrameCenter(this);
	}

	public void find() {
		if (index != start) {
			start = text.getCaretPosition();
		}

		if (checkBox.isSelected()) {
			index = text.getText().indexOf(findField.getText(), start);
		} else {
			index = text.getText().toUpperCase().indexOf(findField.getText().toUpperCase(), start);
		}

		if (index == -1) {
			JOptionPane.showMessageDialog(this, "找不到" + findField.getText());
		} else {
			text.setSelectionStart(index);
			text.setSelectionEnd(index + findField.getText().length());
			start = index + findField.getText().length();
		}
	}

	public boolean findNext() {
		if (index != start) {
			start = text.getCaretPosition();
		}

		if (checkBox.isSelected()) {
			index = text.getText().indexOf(findField.getText(), start);
		} else {
			index = text.getText().toUpperCase().indexOf(findField.getText().toUpperCase(), start);
		}

		if (index == -1) {
			return false;
		} else {
			text.setSelectionStart(index);
			text.setSelectionEnd(index + findField.getText().length());
			start = index + findField.getText().length();

			return true;
		}
	}

	public void replace() {
		find();
		if (text.getSelectionStart() - text.getSelectionEnd() != 0) {
			text.replaceRange(replaceField.getText(), text.getSelectionStart(), text.getSelectionEnd());
		}
	}

	public class ActionAdapter implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Object obj = e.getSource();

			if (obj == findButton) {
				find();
			} else if (obj == replaceButton) {
				replace();
			} else if (obj == replaceAllButton) {
				while (findNext()) {
					if (text.getSelectionStart() - text.getSelectionEnd() != 0) {
						text.replaceRange(replaceField.getText(), text.getSelectionStart(), text.getSelectionEnd());
					}
				}
			} else {
				dispose();
			}
		}
	}
}
