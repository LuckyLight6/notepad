package com.remy;

import java.awt.Container;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AboutDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	private Container con;
	private JPanel panel1;
	private JPanel panel2;
	private JLabel projectName;
	private JLabel developer;

	public AboutDialog(JFrame owner) {
		super(owner, false);
		panel1 = new JPanel();
		panel1.setLayout(new FlowLayout());
		projectName = new JLabel("notepad");
		panel1.add(projectName);
		panel2 = new JPanel();
		panel2.setLayout(new FlowLayout());
		developer = new JLabel("开发者：remy");
		panel2.add(developer);
		con = getContentPane();
		con.setLayout(new BoxLayout(con, BoxLayout.Y_AXIS));
		con.add(panel1);
		con.add(panel2);
		setTitle("关于notepad");
		setSize(200, 100);
		setVisible(true);
		WindowUtil.setFrameCenter(this);
	}
}
