package com.remy;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class FindDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	private Container con;
	private JPanel panel1;
	private JPanel panel2;
	private JTextArea text;
	private JLabel label;
	private JTextField findEdit;
	private JCheckBox checkBox;
	private JRadioButton upButton;
	private JRadioButton downButton;
	private ButtonGroup buttonGroup;
	private JButton okButton;
	private JButton cancleButton;
	private int start;
	private int index;

	public FindDialog(JFrame owner, JTextArea text) {
		super(owner, false);

		start = 0;
		index = -1;
		this.text = text;
		panel1 = new JPanel();
		panel1.setLayout(new FlowLayout());
		panel2 = new JPanel();
		panel2.setLayout(new FlowLayout());
		label = new JLabel("查找内容");
		findEdit = new JTextField(12);
		okButton = new JButton("查找下一个");
		okButton.addActionListener(new ActionAdapter());
		panel1.add(label);
		panel1.add(findEdit);
		panel1.add(okButton);
		checkBox = new JCheckBox("区分大小写");
		checkBox.setSelected(true);
		upButton = new JRadioButton("向上");
		downButton = new JRadioButton("向下", true);
		buttonGroup = new ButtonGroup();
		buttonGroup.add(upButton);
		buttonGroup.add(downButton);
		cancleButton = new JButton("取消");
		cancleButton.addActionListener(new ActionAdapter());
		panel2.add(checkBox);
		panel2.add(upButton);
		panel2.add(downButton);
		panel2.add(cancleButton);
		con = getContentPane();
		con.setLayout(new FlowLayout());
		con.add(panel1);
		con.add(panel2);
		setTitle("查找");
		setSize(300, 120);
		setVisible(true);
		WindowUtil.setFrameCenter(this);
	}

	public void findDown() {
		if (index != start) {
			start = text.getCaretPosition();
		}

		if (checkBox.isSelected()) {
			index = text.getText().indexOf(findEdit.getText(), start);
		} else {
			index = text.getText().toUpperCase().indexOf(findEdit.getText().toUpperCase(), start);
		}

		if (index == -1) {
			JOptionPane.showMessageDialog(this, "找不到" + findEdit.getText());
		} else {
			text.setSelectionStart(index);
			text.setSelectionEnd(index + findEdit.getText().length());
			start = index + findEdit.getText().length();
		}
	}

	public void findUp() {
		if (index != start) {
			start = text.getCaretPosition();
		}

		if (checkBox.isSelected()) {
			index = text.getText().lastIndexOf(findEdit.getText(), start - 1);
		} else {
			index = text.getText().toUpperCase().lastIndexOf(findEdit.getText().toUpperCase(), start - 1);
		}

		if (index == -1) {
			JOptionPane.showMessageDialog(this, "找不到" + findEdit.getText());
		} else {
			text.setSelectionStart(index);
			text.setSelectionEnd(index + findEdit.getText().length());
			start = index;
		}
	}

	public class ActionAdapter implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == okButton) {
				if (downButton.isSelected()) {
					findDown();
				}

				if (upButton.isSelected()) {
					findUp();
				}
			} else {
				dispose();
			}
		}
	}
}
