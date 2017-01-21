package com.remy;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.print.PageFormat;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.ServiceUI;
import javax.print.SimpleDoc;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.undo.UndoManager;

public class Notepad {
	private JFrame mainFrame; // ������
	private Container con; // �����ڵ�����
	private JMenuBar menuBar; // �˵���
	private JMenu fileMenu; // �ļ��˵�
	private JMenu editMenu; // �༭�˵�
	private JMenu formatMenu; // ��ʽ�˵�
	private JMenu viewMenu; // ��ͼ�˵�
	private JMenu helpMenu; // �����˵�
	private JScrollPane scrollPane; // �����ı���Ĺ������
	private JPanel panel; // ��ֹ״̬�������
	private JTextArea text; // �ı���
	private JLabel stateLabel; // ״̬������ʾ�����������ı�ǩ
	private JMenuItem newFile; // �½��˵���
	private JMenuItem openFile; // �򿪲˵���
	private JMenuItem saveFile; // ����˵���
	private JMenuItem saveAsFile; // ����Ϊ�˵���
	private JMenuItem pageSetting; // ҳ�����ò˵���
	private JMenuItem print; // ��ӡ�˵���
	private JMenuItem exit; // �˳��˵���
	private JMenuItem backout; // �����˵���
	private JMenuItem cut; // ���в˵���
	private Font menuFont; // �˵�������
	private Font textFont; // �ı�������
	private JMenuItem copy; // ���Ʋ˵���
	private JMenuItem paste; // ճ���˵���
	private JMenuItem delete; // ɾ���˵���
	private JMenuItem find; // ���Ҳ˵���
	private JMenuItem findNext; // ������һ���˵���
	private JMenuItem replace; // �滻�˵���
	private JMenuItem go; // ת���˵���
	private JMenuItem selectAll; // ȫѡ�˵���
	private JMenuItem date; // ����/ʱ��˵���
	private JMenuItem font; // ����˵���
	private JCheckBoxMenuItem stateBar; // ״̬��ѡ��˵���
	private JMenuItem getHelp; // ��ȡ�����˵���
	private JMenuItem aboutNotepad; // ���ڲ˵���
	private JCheckBoxMenuItem autoNewLine; // �Զ�����ѡ��˵���
	private PopupMenu rightClick; // �Ҽ��˵�
	private MenuItem backoutRight; // �Ҽ������˵���
	private MenuItem cutRight; // �Ҽ����в˵���
	private MenuItem copyRight; // �Ҽ����Ʋ˵���
	private MenuItem pasteRight; // �Ҽ�ճ���˵���
	private MenuItem deleteRight; // �Ҽ�ɾ���˵���
	private MenuItem selectAllRight; // �Ҽ�ȫѡ�˵���
	private UndoManager undoManager; // ��������
	private FontDialog myFontDialog = null; // ��������Ի���
	private File file = null; // �ѱ༭���ı������ļ�
	private boolean changed = false; // �ı��Ƿ�ı�
	private boolean haveName = false; // �ļ��Ƿ����ļ���

	public Notepad() {
		initialize(); // ��ʼ��notepad
		WindowUtil.setFrameCenter(mainFrame); // ���������ھ���
	}

	private void initialize() {
		mainFrame = new JFrame(); // ʵ����������
		con = mainFrame.getContentPane(); // ʵ��������������
		menuFont = new Font("΢���ź�", Font.PLAIN, 12); // ʵ�����˵�����
		textFont = new Font("΢���ź�", Font.PLAIN, 16); // ʵ�����ı�����
		mainFrame.setTitle("�ޱ��� - ���±�"); // ����������Ĭ�ϱ���
		mainFrame.setSize(1440, 767); // ���ô���Ĭ�ϴ�С
		mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // ���ùرհ�ťĬ�Ϲ���Ϊ�������ڹر�ʱ�������κδ���
		mainFrame.addWindowListener(new WindowClickClose()); // ���Ӵ��ڼ����¼�

		createMenu(); // �����˵���
		createOther(); // �����ı���״̬����
		createPopupMenu(); // �����Ҽ��˵�
	}

	public void createMenu() {
		menuBar = new JMenuBar(); // ʵ�����˵���
		menuBar.setBackground(Color.WHITE); // ���ò˵�������Ϊ��ɫ
		mainFrame.setJMenuBar(menuBar); // Ϊ���������Ӳ˵���

		fileMenu = new JMenu("�ļ�(F)"); // ʵ�����ļ��˵�
		fileMenu.setFont(menuFont); // �����ļ��˵�����
		fileMenu.setMnemonic('F'); // ���ü������Ƿ�����Alt���ʱ������˰�ť
		fileMenu.addMouseListener(new MouseClick()); // �����������¼�
		menuBar.add(fileMenu); // ���ļ��˵�����˵���

		newFile = new JMenuItem("�½�(N)"); // ʵ�����½��˵���
		newFile.setFont(menuFont); // �����½��˵�������
		newFile.setMnemonic('N'); // ���ü������Ƿ�����Alt���ʱ������˰�ť
		newFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK)); // ���ÿ�ݼ�ΪCtrl+N
		newFile.addActionListener(new ActionAdapter()); // ���Ӳ��������¼�
		fileMenu.add(newFile); // ���½��˵�������ļ��˵�

		openFile = new JMenuItem("��(O)..."); // ʵ�����򿪲˵���
		openFile.setFont(menuFont); // ���ô򿪲˵�������
		openFile.setMnemonic('O'); // ���ü������Ƿ�����Alt���ʱ������˰�ť
		openFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK)); // ���ÿ�ݼ�ΪCtrl+O
		openFile.addActionListener(new ActionAdapter()); // ���Ӳ��������¼�
		fileMenu.add(openFile); // ���򿪲˵�������ļ��˵�

		saveFile = new JMenuItem("����(S)"); // ʵ��������˵���
		saveFile.setFont(menuFont); // ���ñ���˵�������
		saveFile.setMnemonic('S'); // ���ü������Ƿ�����Alt���ʱ������˰�ť
		saveFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK)); // ���ÿ�ݼ�ΪCtrl+S
		saveFile.addActionListener(new ActionAdapter()); // ���Ӳ��������¼�
		fileMenu.add(saveFile); // ������˵�������ļ��˵�

		saveAsFile = new JMenuItem("����Ϊ(A)..."); // ʵ��������Ϊ�˵���
		saveAsFile.setFont(menuFont); // ��������Ϊ�˵�������
		saveAsFile.setMnemonic('A'); // ���ü������Ƿ�����Alt���ʱ������˰�ť
		saveAsFile.addActionListener(new ActionAdapter()); // ���Ӳ��������¼�
		fileMenu.add(saveAsFile); // ������Ϊ�˵�������ļ��˵�

		fileMenu.addSeparator(); // ����һ���ָ���

		pageSetting = new JMenuItem("ҳ������(U)..."); // ʵ����ҳ�����ò˵���
		pageSetting.setFont(menuFont); // ����ҳ�����ò˵�������
		pageSetting.setMnemonic('U'); // ���ü������Ƿ�����Alt���ʱ������˰�ť
		pageSetting.addActionListener(new ActionAdapter()); // ���Ӳ��������¼�
		fileMenu.add(pageSetting); // ��ҳ�����ò˵�������ļ��˵�

		print = new JMenuItem("��ӡ(P)..."); // ʵ������ӡ�˵���
		print.setFont(menuFont); // ���ô�ӡ�˵�������
		print.setMnemonic('P'); // ���ü������Ƿ�����Alt���ʱ������˰�ť
		print.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_DOWN_MASK)); // ���ÿ�ݼ�ΪCtrl+P
		print.addActionListener(new ActionAdapter()); // ���Ӳ��������¼�
		fileMenu.add(print); // ����ӡ�˵�������ļ��˵�

		fileMenu.addSeparator(); // ����һ���ָ���

		exit = new JMenuItem("�˳�(X)..."); // ʵ�����˳��˵���
		exit.setFont(menuFont); // �����˳��˵�������
		exit.setMnemonic('X'); // ���ü������Ƿ�����Alt���ʱ������˰�ť
		exit.addActionListener(new ActionAdapter()); // ���Ӳ�������ʱ��
		fileMenu.add(exit); // ���˳��˵�������ļ��˵�

		editMenu = new JMenu("�༭(E)"); // ʵ�����༭�˵�
		editMenu.setFont(menuFont); // ���ñ༭�˵�����
		editMenu.setMnemonic('E'); // ���ü������Ƿ�����Alt���ʱ������˰�ť
		editMenu.addMouseListener(new MouseClick()); // ������������¼�
		menuBar.add(editMenu); // ���༭�˵�����˵���

		backout = new JMenuItem("����(U)"); // ʵ���������˵���
		backout.setFont(menuFont); // ���ó����˵�������
		backout.setMnemonic('U'); // ���ü������Ƿ�����Alt���ʱ������˰�ť
		backout.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK)); // ���ÿ�ݼ�ΪCtrl+Z
		backout.addActionListener(new ActionAdapter()); // ���Ӳ��������¼�
		backout.setEnabled(false); // Ĭ������Ϊ������
		editMenu.add(backout); // �������˵������༭�˵�

		editMenu.addSeparator(); // ����һ���ָ���

		cut = new JMenuItem("����(T)"); // ʵ�������в˵���
		cut.setFont(menuFont); // ���ü��в˵�������
		cut.setMnemonic('T'); // ���ü������Ƿ�����Alt���ʱ������˰�ť
		cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK)); // ���ÿ�ݼ�ΪCtrl+X
		cut.addActionListener(new ActionAdapter()); // ���Ӳ��������¼�
		cut.setEnabled(false); // Ĭ������Ϊ������
		editMenu.add(cut); // �����в˵������༭�˵�

		copy = new JMenuItem("����(C)"); // ʵ�������Ʋ˵���
		copy.setFont(menuFont); // ���ø��Ʋ˵�������
		copy.setMnemonic('C'); // ���ü������Ƿ�����Alt���ʱ������˰�ť
		copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK)); // ���ÿ�ݼ�ΪCtrl+C
		copy.addActionListener(new ActionAdapter()); // ���Ӳ��������¼�
		copy.setEnabled(false); // Ĭ������Ϊ������
		editMenu.add(copy); // �����Ʋ˵������༭�˵�

		paste = new JMenuItem("ճ��(V)"); // ʵ����ճ���˵���
		paste.setFont(menuFont); // ����ճ���˵�������
		paste.setMnemonic('V'); // ���ü������Ƿ�����Alt���ʱ������˰�ť
		paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK)); // ���ÿ�ݼ�ΪCtrl+V
		paste.addActionListener(new ActionAdapter()); // ���Ӳ��������¼�
		editMenu.add(paste); // ��ճ���˵������༭�˵�

		delete = new JMenuItem("ɾ��(L)"); // ʵ����ɾ���˵���
		delete.setFont(menuFont); // ����ɾ���˵�������
		delete.setMnemonic('L'); // ���ü������Ƿ�����Alt���ʱ������˰�ť
		delete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0)); // ���ÿ�ݼ�ΪDelete
		delete.addActionListener(new ActionAdapter()); // ���Ӳ��������¼�
		delete.setEnabled(false); // Ĭ������Ϊ������
		editMenu.add(delete); // ��ɾ���˵������༭�˵�

		editMenu.addSeparator(); // ����һ���ָ���

		find = new JMenuItem("����(F)"); // ʵ�������Ҳ˵���
		find.setFont(menuFont); // ���ò��Ҳ˵�������
		find.setMnemonic('F'); // ���ü������Ƿ�����Alt���ʱ������˰�ť
		find.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_DOWN_MASK)); // ���ÿ�ݼ�ΪCtrl+F
		find.addActionListener(new ActionAdapter()); // ���Ӳ��������¼�
		find.setEnabled(false); // Ĭ������Ϊ������
		editMenu.add(find); // �����Ҳ˵������༭�˵�

		findNext = new JMenuItem("������һ��(N)"); // ʵ����������һ���˵���
		findNext.setFont(menuFont); // ���ò�����һ���˵�������
		findNext.setMnemonic('N'); // ���ü������Ƿ�����Alt���ʱ������˰�ť
		findNext.setAccelerator(KeyStroke.getKeyStroke((char) KeyEvent.VK_F3, 0)); // ���ÿ�ݼ�ΪF3
		findNext.addActionListener(new ActionAdapter()); // ���Ӳ��������¼�
		findNext.setEnabled(false); // Ĭ������Ϊ������
		editMenu.add(findNext); // ��������һ���˵������༭�˵�

		replace = new JMenuItem("�滻(R)..."); // ʵ�����滻�˵���
		replace.setFont(menuFont); // �����滻�˵�������
		replace.setMnemonic('R'); // ���ü������Ƿ�����Alt���ʱ������˰�ť
		replace.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.CTRL_DOWN_MASK)); // ���ÿ�ݼ�ΪCtrl+H
		replace.addActionListener(new ActionAdapter()); // ���Ӳ��������¼�
		editMenu.add(replace); // ���滻�˵������༭�˵�

		go = new JMenuItem("ת��(G)..."); // ʵ����ת���˵���
		go.setFont(menuFont); // ����ת���˵�������
		go.setMnemonic('G'); // ���ü������Ƿ�����Alt���ʱ������˰�ť
		go.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.CTRL_DOWN_MASK)); // ���ÿ�ݼ�����ΪCtrl+G
		go.addActionListener(new ActionAdapter()); // ���Ӳ��������¼�
		editMenu.add(go); // ��ת���˵������༭�˵�

		editMenu.addSeparator(); // ����һ���ָ���

		selectAll = new JMenuItem("ȫѡ(A)"); // ʵ����ȫѡ�˵���
		selectAll.setFont(menuFont); // ����ȫѡ�˵�������
		selectAll.setMnemonic('A'); // ���ü������Ƿ�����Alt���ʱ������˰�ť
		selectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK)); // ���ÿ�ݼ�ΪCtrl+A
		selectAll.addActionListener(new ActionAdapter()); // ���Ӳ��������¼�
		editMenu.add(selectAll); // ��ȫѡ�˵������༭�˵�

		date = new JMenuItem("ʱ��/����(D)"); // ʵ����ʱ��/���ڲ˵���
		date.setFont(menuFont); // ����ʱ��/���ڲ˵�������
		date.setMnemonic('D'); // ���ü������Ƿ�����Alt���ʱ������˰�ť
		date.setAccelerator(KeyStroke.getKeyStroke((char) KeyEvent.VK_F5, 0)); // ���ÿ�ݼ�ΪF5
		date.addActionListener(new ActionAdapter()); // ���Ӳ��������¼�
		editMenu.add(date); // ��ʱ��/���ڲ˵������༭�˵�

		formatMenu = new JMenu("��ʽ(O)"); // ʵ������ʽ�˵�
		formatMenu.setFont(menuFont); // ���ø�ʽ�˵�����
		formatMenu.setMnemonic('O'); // ���ü������Ƿ�����Alt���ʱ������˰�ť
		formatMenu.addMouseListener(new MouseClick()); // �����������¼�
		menuBar.add(formatMenu); // ����ʽ�˵�����˵���

		autoNewLine = new JCheckBoxMenuItem("�Զ�����(W)"); // ʵ�����Զ����в˵���
		autoNewLine.setFont(menuFont); // �����Զ����в˵�������
		autoNewLine.setMnemonic('N'); // ���ü������Ƿ�����Alt���ʱ������˰�ť
		autoNewLine.addItemListener(new JMenuItemCheckBoxClick()); // ����������¼�
		formatMenu.add(autoNewLine); // ���Զ����в˵�������ʽ�˵�

		font = new JMenuItem("����(F)..."); // ʵ��������˵���
		font.setFont(menuFont); // ��������˵�������
		font.setMnemonic('F'); // ���ü������Ƿ�����Alt���ʱ������˰�ť
		font.addActionListener(new ActionAdapter()); // ���Ӳ��������¼�
		formatMenu.add(font); // ������˵�������ʽ�˵�

		viewMenu = new JMenu("�鿴(V)"); // ʵ�����鿴�˵�
		viewMenu.setFont(menuFont); // ���ò鿴�˵�����
		viewMenu.setMnemonic('V'); // ���ü������Ƿ�����Alt���ʱ������˰�ť
		viewMenu.addMouseListener(new MouseClick()); // �����������¼�
		menuBar.add(viewMenu); // ���鿴�˵�����˵���

		stateBar = new JCheckBoxMenuItem("״̬��(S)"); // ʵ����״̬���˵���
		stateBar.setFont(menuFont); // ����״̬���˵�������
		stateBar.setMnemonic('S'); // ���ü������Ƿ�����Alt���ʱ������˰�ť
		stateBar.addItemListener(new JMenuItemCheckBoxClick()); // ����������¼�
		viewMenu.add(stateBar); // ��״̬���˵������鿴�˵�

		helpMenu = new JMenu("����(H)"); // ʵ���������˵�
		helpMenu.setFont(menuFont); // ���ð����˵�����
		helpMenu.setMnemonic('H'); // ���ü������Ƿ�����Alt���ʱ������˰�ť
		helpMenu.addMouseListener(new MouseClick()); // �����������¼�
		menuBar.add(helpMenu); // �������˵�����˵���

		getHelp = new JMenuItem("�鿴����(H)"); // ʵ�����鿴�����˵���
		getHelp.setFont(menuFont); // ���ò鿴�����˵�������
		getHelp.setMnemonic('H'); // ���ü������Ƿ�����Alt���ʱ������˰�ť
		getHelp.addActionListener(new ActionAdapter()); // ���Ӳ��������¼�
		helpMenu.add(getHelp); // ���鿴�����˵����������˵�

		aboutNotepad = new JMenuItem("����notepad(A)"); // ʵ��������notepad�˵���
		aboutNotepad.setFont(menuFont); // ���ù���notepad�˵�������
		aboutNotepad.setMnemonic('A'); // ���ü������Ƿ�����Alt���ʱ������˰�ť
		aboutNotepad.addActionListener(new ActionAdapter()); // ���Ӳ��������¼�
		helpMenu.add(aboutNotepad); // ������notepad�˵����������˵�
	}

	private void createOther() {
		scrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS); // ʵ��������ˮƽ�������ʹ�ֱ�������Ĺ������
		con.add(scrollPane, BorderLayout.CENTER); // ����������������������������

		panel = new JPanel(); // ʵ����״̬�����
		panel.setVisible(false); // Ĭ������Ϊ���ɼ�
		con.add(panel, BorderLayout.SOUTH); // ��״̬��������������������������

		text = new JTextArea(); // ʵ�����ı���
		text.addMouseListener(new MouseClick()); // �ı��������������¼�
		text.addKeyListener(new HandleKey()); // �ı������Ӽ��̼����¼�
		text.getDocument().addDocumentListener(new JTextAeraChanged()); // ���ı��������ģ�������ĵ������¼�
		text.addCaretListener(new JTextAreaPosition()); // �ı������Ӳ���������¼�
		text.setFont(textFont); // �����ı����Ĭ������
		undoManager = new UndoManager(); // ʵ������������
		text.getDocument().addUndoableEditListener(undoManager); // ���ı�����ĵ����ӳ����༭�����¼�
		scrollPane.setViewportView(text); // ���ı������ӵ��������
		panel.setLayout(new BorderLayout()); // ����״̬����岼��Ϊ�߿򲼾�

		stateLabel = new JLabel("��1�У���1��"); // ʵ����״̬����ǩ
		stateLabel.setFont(menuFont); // ����״̬����ǩ����
		panel.add(stateLabel); // ��״̬����ǩ����״̬�����
	}

	public void createPopupMenu() {
		rightClick = new PopupMenu(); // ʵ�����Ҽ��˵�

		backoutRight = new MenuItem("    ����(U)"); // ʵ�����Ҽ������˵���
		backoutRight.setFont(menuFont); // �����Ҽ������˵�������
		backoutRight.setEnabled(false); // Ĭ������Ϊ������
		backoutRight.addActionListener(new ActionAdapter()); // ���Ӳ��������¼�
		rightClick.add(backoutRight); // ���Ҽ������˵������ӵ��Ҽ��˵�

		rightClick.addSeparator(); // ���ӷָ���

		cutRight = new MenuItem("    ����(T)"); // ʵ�����Ҽ����в˵���
		cutRight.setFont(menuFont); // �����Ҽ����в˵�������
		cutRight.setEnabled(false); // Ĭ������Ϊ������
		cutRight.addActionListener(new ActionAdapter()); // ���Ӳ��������¼�
		rightClick.add(cutRight); // ���Ҽ����в˵������ӵ��Ҽ��˵�

		copyRight = new MenuItem("    ����(C)"); // ʵ�����Ҽ����Ʋ˵���
		copyRight.setFont(menuFont); // �����Ҽ����Ʋ˵�������
		copyRight.setEnabled(false); // Ĭ������Ϊ������
		copyRight.addActionListener(new ActionAdapter()); // ���Ӳ��������¼�
		rightClick.add(copyRight); // ���Ҽ����Ʋ˵������ӵ��Ҽ��˵�

		pasteRight = new MenuItem("    ճ��(P)"); // ʵ�����Ҽ�ճ���˵���
		pasteRight.setFont(menuFont); // �����Ҽ�ճ���˵�������
		pasteRight.addActionListener(new ActionAdapter()); // ���Ӳ��������¼�
		rightClick.add(pasteRight); // ���Ҽ�ճ���˵������ӵ��Ҽ��˵�

		deleteRight = new MenuItem("    ɾ��(D)"); // ʵ�����Ҽ�ɾ���˵���
		deleteRight.setFont(menuFont); // �����Ҽ�ɾ���˵�������
		deleteRight.setEnabled(false); // Ĭ������Ϊ������
		deleteRight.addActionListener(new ActionAdapter()); // ���Ӳ��������¼�
		rightClick.add(deleteRight); // ���Ҽ�ɾ���˵������ӵ��Ҽ��˵�

		rightClick.addSeparator(); // ���ӷָ���

		selectAllRight = new MenuItem("    ȫѡ(A)"); // ʵ�����Ҽ�ȫѡ�˵���
		selectAllRight.setFont(menuFont); // �����Ҽ�ȫѡ�˵�������
		selectAllRight.addActionListener(new ActionAdapter()); // ���Ӳ��������¼�
		rightClick.add(selectAllRight); // ���Ҽ�ȫѡ�˵�������Ҽ��˵�

		text.add(rightClick); // ���Ҽ��˵������ı���
	}

	public void doNewFile() {
		int select; // �û���ѡ��
		int flag = 0; // �Ƿ��½��ļ���1��ʾ�½��ļ���0��ʾ���½���

		// �ж��ı��Ƿ�ı䣬����ı䣬����ʾδ���棬���δ�ı䣬���½��ļ�
		if (changed) {
			select = JOptionPane.showConfirmDialog(mainFrame, "�ļ��޸ĺ���δ���̣��Ƿ񱣴棿");

			// �����û���ѡ��ִ����Ӧ�Ĳ���
			switch (select) {
			case JOptionPane.YES_OPTION:
				flag = doSave(); // �û�ѡ���ǣ�ִ�б������
				break;
			case JOptionPane.NO_OPTION:
				flag = 1; // �û�ѡ�񲻱��棬���½��ļ�
				break;
			default:
				flag = 0; // �û�ѡ��ȡ�������½��ļ�
				break;
			}
		} else {
			flag = 1;
		}

		// �Ƿ��½��ļ�
		if (flag == 1) {
			mainFrame.setTitle("�ޱ��� - ���±�");
			text.setText("");
			changed = false;
			haveName = false;
		}
	}

	public void doOpen() {
		int select; // �û���ѡ��
		int flag = 0; // �Ƿ�����ļ�
		File tmpFile = null; // ��ʱ�ļ�
		FileNameExtensionFilter filter; // ʹ��ָ������չ�����Ͻ��й��ˡ��ļ�����չ����ָ�ļ������һ����.������Ĳ��֡����Ʋ�������.�����ļ�û���ļ���չ�����ļ���չ���ıȽϲ����ִ�Сд��
		JFileChooser chooser; // �ļ�ѡ����
		FileInputStream fis; // ������
		byte[] buf; // �ֽڻ�������

		// �ж��ı��Ƿ�ı䣬����ı䣬����ʾδ���棬���δ�ı䣬������ļ�
		if (changed) {
			select = JOptionPane.showConfirmDialog(mainFrame, "�ļ��޸ĺ���δ���̣��շ��޸ģ�");

			// �����û���ѡ��ִ����Ӧ�Ĳ���
			switch (select) {
			case JOptionPane.YES_OPTION: // �û�ѡ���ǣ���ִ�б������
				flag = doSave();
				break;
			case JOptionPane.NO_OPTION: // �û�ѡ����򲻱��棬�������ļ�
				flag = 1;
				break;
			default: // �û�ѡ��ȡ�����򲻴����ļ�
				flag = 0;
				break;
			}
		} else {
			flag = 1;
		}

		// �ж��Ƿ�����ļ�
		if (flag == 1) {
			changed = false;
			filter = new FileNameExtensionFilter("�ı��ļ�(*.txt)", "txt"); // ʵ�����ı��ļ�����������׺��Ϊtxt

			// �����ǰ�ļ��ѱ�����Ӳ�̣���ʹ�õ�ǰ�ļ���·��������һ���ļ�ѡ���������򣬹���һ��ָ���û�Ĭ��Ŀ¼���ļ�ѡ����
			if (file != null) {
				chooser = new JFileChooser(file.getPath());
			} else {
				chooser = new JFileChooser();
			}

			chooser.setFileFilter(filter); // ���õ�ǰ�ļ�������
			select = chooser.showOpenDialog(mainFrame); // ����һ�����ļ�ѡ�����Ի���

			// ���ѡ���
			if (select == JFileChooser.APPROVE_OPTION) {
				tmpFile = chooser.getSelectedFile(); // ����ѡ�е��ļ�

				try {
					if (tmpFile != null) {
						fis = new FileInputStream(tmpFile); // ��һ������ʱ�ļ�������������һ��������
						buf = new byte[(int) tmpFile.length()]; // ����ʱ�ļ��ĳ��ȴ���һ���ֽڻ�������
						fis.read(buf); // ���������н����buf.length���ֽڵ����ݶ���buf������
						fis.close(); // �ر�������

						text.setText(new String(buf)); // ���ı�����ı�����Ϊ�ֽڻ�������ת���ɵ��ַ���
						file = tmpFile; // ����ʱ�ļ���ֵ��Ҫ�༭���ļ�
						mainFrame.setTitle(file.getName() + " - ���±�");
						changed = false;
						haveName = true;
					}
				} catch (FileNotFoundException e) {
					JOptionPane.showMessageDialog(mainFrame, "ָ�����ļ����ƻ����������⣡");
				} catch (IOException e) {
					JOptionPane.showMessageDialog(mainFrame, "�޷���ȡ�ļ��������ļ��Ƿ�������");
				}
			}
		}
	}

	public int doSave() {
		FileOutputStream fos; // �����
		byte[] content; // �ֽ�����
		int flag; // �Ƿ�򿪻��½��ļ�

		// ���û���ļ�������ִ������Ϊ�����������ж��Ƿ�ı䣬����ı䣬�򱣴棬���򣬲�����
		if (!haveName) {
			flag = doSaveAs();
		} else if (changed) {
			try {
				fos = new FileOutputStream(file); // ��һ������ǰ���ļ�������������һ�������
				content = text.getText().getBytes(); // ���ı�������ִ洢���ֽ�������
				fos.write(content); // ��content.length���ֽڴ�contentд�뵱ǰ���ļ����������
				fos.close(); // �ر������
				changed = false;
				flag = 1;
			} catch (FileNotFoundException e) {
				JOptionPane.showConfirmDialog(mainFrame, "ָ�����ļ����ƻ����������⣡");
				flag = 0;
			} catch (IOException e) {
				JOptionPane.showConfirmDialog(mainFrame, "�޷�д�ļ��������ļ��Ƿ�������");
				flag = 0;
			}
		} else {
			flag = 1;
		}

		return flag;
	}

	public int doSaveAs() {
		FileOutputStream fos; // �����
		byte[] content; // �ֽ�����
		int flag = 0; // �Ƿ񱣴�
		File tmpFile = null; // ��ʱ�ļ�
		FileNameExtensionFilter filter = new FileNameExtensionFilter("�ı��ļ�(*.txt)", "txt"); // ʹ��ָ������չ�����Ͻ��й���
		JFileChooser chooser; // �ļ�ѡ����

		// �����ǰ�ļ��ѱ�����Ӳ�̣���ʹ�õ�ǰ�ļ���·��������һ���ļ�ѡ���������򣬹���һ��ָ���û�Ĭ��Ŀ¼���ļ�ѡ����
		if (file != null) {
			chooser = new JFileChooser(file.getPath());
		} else {
			chooser = new JFileChooser();
		}

		chooser.setFileFilter(filter); // ���õ�ǰ�ļ�������
		flag = chooser.showSaveDialog(mainFrame); // ����һ�����ļ�ѡ�����Ի���

		// ���ѡ�񱣴�
		if (flag == JFileChooser.APPROVE_OPTION) {
			tmpFile = chooser.getSelectedFile(); // ����ѡ�е��ļ�
			String fname = chooser.getName(tmpFile); // �����ļ���

			// ���û�к�׺��������ϡ�.txt����׺
			if (fname.indexOf(".") == -1) {
				tmpFile = new File(chooser.getCurrentDirectory(), fname + ".txt");
			}

			// ����ļ��Ѵ��ڣ�����ʾ�Ƿ񸲸�
			if (tmpFile.exists()) {
				if (JOptionPane.showConfirmDialog(mainFrame, "�ļ��Ѿ����ڣ��Ƿ񸲸ǣ�", "����",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					flag = 1;
				} else {
					flag = 0;
				}
			} else {
				flag = 1;
			}
		} else {
			flag = 0;
		}

		if (flag == 1) {
			try {
				fos = new FileOutputStream(tmpFile);
				content = text.getText().getBytes();
				fos.write(content);
				fos.close();
				flag = 1;
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(mainFrame, "ָ�����ļ����ƻ����������⣡");
				flag = 0;
			} catch (IOException e) {
				JOptionPane.showMessageDialog(mainFrame, "�޷���д�ļ��������ļ��Ƿ�������");
				flag = 0;
			}
		}

		if (flag == 1) {
			file = tmpFile;
			mainFrame.setTitle(file.getName() + " - ���±�");
			changed = false;
			haveName = true;
		}

		return flag;
	}

	public void doPageSetting() {
		PageFormat pf = new PageFormat(); // ����Ҫ��ӡ��ҳ���С�ͷ���
		PrinterJob.getPrinterJob().pageDialog(pf); // ���������س�ʼ��ʱ��Ĭ�ϴ�ӡ��������PrinterJob��Ȼ����ʾ�����޸�PageFormatʵ���ĶԻ���
	}

	public void doPrint() {
		try {
			PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
			DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
			PrintService[] printServices = PrintServiceLookup.lookupPrintServices(flavor, pras);
			PrintService defaultService = PrintServiceLookup.lookupDefaultPrintService();
			PrintService service = null;
			service = ServiceUI.printDialog(null, 100, 100, printServices, defaultService, flavor, pras);

			if (service != null) {
				DocPrintJob job = service.createPrintJob();
				DocAttributeSet das = new HashDocAttributeSet();
				Doc doc = new SimpleDoc(text.getText().getBytes(), flavor, das);
				job.print(doc, pras);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(mainFrame, "��ӡ�����޷���ɣ�");
		}
	}

	public void doExit() {
		int select;

		if (!changed) {
			System.exit(0);
		} else {
			select = JOptionPane.showConfirmDialog(mainFrame, "�Ƿ񽫸��ı��浽 " + "��", "notepad",
					JOptionPane.YES_NO_CANCEL_OPTION);

			switch (select) {
			case JOptionPane.YES_OPTION:
				select = doSave();
				if (select == 1) {
					System.exit(0);
				}
				break;

			case JOptionPane.NO_OPTION:
				System.exit(0);
				break;
			case JOptionPane.CANCEL_OPTION:
				break;
			}
		}
	}

	public void doBackout() {
		if (undoManager.canUndo()) {
			undoManager.undo();
		} else {
			undoManager.redo();
		}
	}

	public void doCut() {
		text.cut();
	}

	public void doCopy() {
		text.copy();
	}

	public void doPaste() {
		text.paste();
	}

	public void doDelete() {
		text.replaceSelection("");
	}

	public void doFind() {
		new FindDialog(mainFrame, text);
	}

	public void doFindNext() {
		new FindDialog(mainFrame, text);
	}

	public void doReplace() {
		new ReplaceDialog(mainFrame, text);
	}

	public void doGo() {
		new GoDialog(mainFrame, text);
	}

	public void doSelectAll() {
		text.selectAll();
	}

	public void doDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		text.append(sdf.format(new Date()));
	}

	public void doFont() {
		if (myFontDialog == null) {
			myFontDialog = new FontDialog(mainFrame);
		}

		if (myFontDialog.showFontDialog() == FontDialog.OK) {
			text.setFont(myFontDialog.getFont());
		}
	}

	public void doGetHelp() {
		Runtime rt = Runtime.getRuntime();

		try {
			rt.exec("rundll32 url.dll,FileProtocolHandler " + "https://www.baidu.com");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void doAboutNotepad() {
		new AboutDialog(mainFrame);
	}

	public void checkText() {
		if (text.getSelectedText() == null) {
			cut.setEnabled(false);
			cutRight.setEnabled(false);
			copy.setEnabled(false);
			copyRight.setEnabled(false);
			delete.setEnabled(false);
			deleteRight.setEnabled(false);
		} else {
			cut.setEnabled(true);
			cutRight.setEnabled(true);
			copy.setEnabled(true);
			copyRight.setEnabled(true);
			delete.setEnabled(true);
			deleteRight.setEnabled(true);
		}
	}

	public class ActionAdapter implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Object obj = e.getSource();

			if (obj == newFile) {
				doNewFile();
			} else if (obj == openFile) {
				doOpen();
			} else if (obj == saveFile) {
				doSave();
			} else if (obj == saveAsFile) {
				doSaveAs();
			} else if (obj == pageSetting) {
				doPageSetting();
			} else if (obj == print) {
				doPrint();
			} else if (obj == exit) {
				doExit();
			} else if (obj == backout) {
				doBackout();
			} else if (obj == cut) {
				doCut();
			} else if (obj == copy) {
				doCopy();
			} else if (obj == paste) {
				doPaste();
			} else if (obj == delete) {
				doDelete();
			} else if (obj == find) {
				doFind();
			} else if (obj == findNext) {
				doFindNext();
			} else if (obj == replace) {
				doReplace();
			} else if (obj == go) {
				doGo();
			} else if (obj == selectAll) {
				doSelectAll();
			} else if (obj == date) {
				doDate();
			} else if (obj == font) {
				doFont();
			} else if (obj == getHelp) {
				doGetHelp();
			} else if (obj == aboutNotepad) {
				doAboutNotepad();
			} else if (obj == backoutRight) {
				doBackout();
			} else if (obj == copyRight) {
				doCopy();
			} else if (obj == cutRight) {
				doCut();
			} else if (obj == pasteRight) {
				doPaste();
			} else if (obj == deleteRight) {
				doDelete();
			} else if (obj == selectAllRight) {
				doSelectAll();
			}
		}
	}

	public class MouseClick extends MouseAdapter {
		@Override
		public void mouseEntered(MouseEvent e) {
			Object obj = e.getSource();

			if (obj == fileMenu || obj == editMenu || obj == formatMenu || obj == viewMenu || obj == helpMenu) {
				((JComponent) obj).setOpaque(true);
				((Component) obj).setBackground(new Color(174, 238, 238));
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			Object obj = e.getSource();

			if (obj == fileMenu || obj == editMenu || obj == formatMenu || obj == viewMenu || obj == helpMenu) {
				((Component) obj).setBackground(Color.WHITE);
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			Object obj = e.getSource();
			int mods = e.getModifiers();

			if (obj == text) {
				if ((mods & InputEvent.BUTTON3_MASK) != 0) {
					rightClick.show(text, e.getX(), e.getY());
				}
			}
			checkText();
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			checkText();
		}
	}

	public class HandleKey extends KeyAdapter {
		@Override
		public void keyTyped(KeyEvent e) {
			checkText();
		}
	}

	public class JMenuItemCheckBoxClick implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent e) {
			Object obj = e.getSource();

			if (obj == stateBar) {
				if (((JCheckBoxMenuItem) obj).getState()) {
					panel.setVisible(true);
				} else {
					panel.setVisible(false);
				}
			}

			if (obj == autoNewLine) {
				if (((JCheckBoxMenuItem) obj).getState()) {
					text.setLineWrap(true);
					scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
				} else {
					text.setLineWrap(false);
					scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
				}
			}
		}
	}

	public class WindowClickClose extends WindowAdapter {
		@Override
		public void windowClosing(WindowEvent e) {
			if (e.getID() == WindowEvent.WINDOW_CLOSING) {
				doExit();
			}
		}
	}

	public class JTextAeraChanged implements DocumentListener {
		@Override
		public void insertUpdate(DocumentEvent e) {
			changed = true;
			backout.setEnabled(true);
			backoutRight.setEnabled(true);

			if (!text.getText().equals("")) {
				find.setEnabled(true);
				findNext.setEnabled(true);
			} else {
				find.setEnabled(false);
				findNext.setEnabled(false);
			}
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			changed = true;
			backout.setEnabled(true);
			backoutRight.setEnabled(true);

			if (!text.getText().equals("")) {
				find.setEnabled(true);
				findNext.setEnabled(true);
			} else {
				find.setEnabled(false);
				findNext.setEnabled(false);
			}
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
		}
	}

	public class JTextAreaPosition implements CaretListener {
		@Override
		public void caretUpdate(CaretEvent e) {
			try {
				int pos = text.getCaretPosition();
				// ��ȡ����
				int row = text.getLineOfOffset(pos) + 1;
				// ��ȡ����
				int col = pos - text.getLineStartOffset(row - 1) + 1;
				stateLabel.setText("��" + row + "�У���" + col + "��");
			} catch (Exception ex) {
				stateLabel.setText("�޷���õ�ǰ���λ�ã�");
			}
		}
	}

	public static void main(String[] args) {
		WindowUtil.beautyWindow();

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Notepad window = new Notepad();
					window.mainFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}