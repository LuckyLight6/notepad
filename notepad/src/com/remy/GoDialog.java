package com.remy;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;

public class GoDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	private Container con;
	private JPanel panel1;
	private JPanel panel2;
	private JTextArea text;
	private JLabel label;
	private JTextField field;
	private JButton goButton;
	private JButton cancleButton;

	public GoDialog(JFrame owner, JTextArea text) {
		super(owner, false);

		this.text = text;
		panel1 = new JPanel();
		panel1.setLayout(new FlowLayout());
		panel2 = new JPanel();
		panel2.setLayout(new FlowLayout());
		label = new JLabel("行号：");
		field = new JTextField(12);
		field.addKeyListener(new VoteElectKeyListener());
		panel1.add(label);
		panel1.add(field);
		goButton = new JButton("转到");
		goButton.addActionListener(new ActionAdapter());
		cancleButton = new JButton("取消");
		cancleButton.addActionListener(new ActionAdapter());
		panel2.add(goButton);
		panel2.add(cancleButton);
		con = getContentPane();
		con.setLayout(new FlowLayout());
		con.add(panel1);
		con.add(panel2);
		setTitle("转到指定行");
		setSize(300, 130);
		setVisible(true);
		WindowUtil.setFrameCenter(this);
	}

	public void go() {
		int line = Integer.parseInt(field.getText()) - 1;

		if (line >= 0 && line < text.getLineCount()) {
			try {
				text.setCaretPosition(text.getLineStartOffset(line));
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		} else {
			JOptionPane.showMessageDialog(this, "行数超过了总行数！");
			field.setText(String.valueOf(text.getLineCount()));
		}
	}

	public class ActionAdapter implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == goButton) {
				go();
			} else {
				dispose();
			}
		}
	}

	public class VoteElectKeyListener extends KeyAdapter {
		@Override
		public void keyTyped(KeyEvent e) {
			int keyChar = e.getKeyChar();
			if (keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9) {
				
			} else {
				e.consume();
			}
		}
	}
}
