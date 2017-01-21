package com.remy;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.UIManager;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

/**
 * 针对界面进行操作的工具类
 *
 * @author Remy
 */
public class WindowUtil {
	/**
	 * 需求：设置窗体居中。
	 *
	 * 分析：
	 * 
	 * A：获取屏幕的宽和高
	 * 
	 * B：获取被设置的窗体的宽和高
	 * 
	 * C：把这两个数据((屏幕的宽 - 窗体的宽) / 2, (屏幕的高 - 窗体的高) / 2)作为窗体的新坐标
	 */
	private WindowUtil() {
	}

	public static void setFrameCenter(Window window) {
		// 获取屏幕的宽和高
		// 获取屏幕对象
		// 获取awt操作的工具包对象

		// public static Toolkit getDefaultToolkit()
		// 获取工具包对象
		Toolkit t = Toolkit.getDefaultToolkit();

		// 获取屏幕对象
		// public sbstract Dimension getScreenSize()
		Dimension d = t.getScreenSize();

		// 获取屏幕宽
		int screenWidth = d.width;

		// 获取屏幕高
		int screenHeight = d.height;

		// 获取窗体的宽
		int jfWidth = window.getWidth();

		// 获取窗体的高
		int jfHeight = window.getHeight();

		// 设置窗体的坐标
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