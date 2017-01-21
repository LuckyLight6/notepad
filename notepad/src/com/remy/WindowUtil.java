package com.remy;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.UIManager;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

/**
 * ��Խ�����в����Ĺ�����
 *
 * @author Remy
 */
public class WindowUtil {
	/**
	 * �������ô�����С�
	 *
	 * ������
	 * 
	 * A����ȡ��Ļ�Ŀ�͸�
	 * 
	 * B����ȡ�����õĴ���Ŀ�͸�
	 * 
	 * C��������������((��Ļ�Ŀ� - ����Ŀ�) / 2, (��Ļ�ĸ� - ����ĸ�) / 2)��Ϊ�����������
	 */
	private WindowUtil() {
	}

	public static void setFrameCenter(Window window) {
		// ��ȡ��Ļ�Ŀ�͸�
		// ��ȡ��Ļ����
		// ��ȡawt�����Ĺ��߰�����

		// public static Toolkit getDefaultToolkit()
		// ��ȡ���߰�����
		Toolkit t = Toolkit.getDefaultToolkit();

		// ��ȡ��Ļ����
		// public sbstract Dimension getScreenSize()
		Dimension d = t.getScreenSize();

		// ��ȡ��Ļ��
		int screenWidth = d.width;

		// ��ȡ��Ļ��
		int screenHeight = d.height;

		// ��ȡ����Ŀ�
		int jfWidth = window.getWidth();

		// ��ȡ����ĸ�
		int jfHeight = window.getHeight();

		// ���ô��������
		window.setLocation((screenWidth - jfWidth) / 2, (screenHeight - jfHeight) / 2);
	}

	public static void beautyWindow() {
		try {
			BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.generalNoTranslucencyShadow;
			UIManager.put("RootPane.setupButtonVisible", false);
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}