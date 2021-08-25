package com.example.myapplication;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceDataStore;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.multidex.MultiDex;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.ref.SoftReference;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



/**
 * Created on 2014-8-21
 * <p>
 * Title: [系统描述]_[模块描述]_[类描述]
 * </p>
 * <p>
 * Description:此类用来存放全局的一些变量类似web中的application
 * </p>
 * <p>
 * Copyright: Copyright (c) 2014
 * </p>
 * <p>
 * Company: 上海期货信息技术有限公司
 * </p>
 * <p>
 * Department: 期货事业部
 * </p>
 *
 * @author dong.bing
 * @update 2014-8-21
 */
public class MyApplication extends Application {

	//private static Context mContext;
	private static MyApplication instance;

	private static SoftReference<Activity> currActivityRef;

	private PreferenceDataStore preferenceDataStore;
	/**
	 * 获取当前最顶层的Activity.
	 *
	 * @return Activity
	 */
	public static Activity getCurrentActivity() {
		if (currActivityRef != null) {
			return currActivityRef.get();
		}
		return null;
	}

	public static MyApplication getInstance() {
		return instance;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;

		if (isNeedCaughtException) {
			catchException();
		}
	}

	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);
		// add by zhangshuai:多包支持
		MultiDex.install(this);
	}

	/**
	 * 获取Context.
	 *
	 * @return ApplicationContext实例。
	 */
	public static Context getContext() {
		return instance;
	}

	/**
	 * 获取应用独自占用的外部存储目录绝对路径。
	 *
	 * @return 应用独自占用的外部存储目录绝对路径
	 */
	public static String getExternalFilesDirPath() {
		// No additional permissions are required for the calling app to read or write files under the returned path.
		// File dir = MobileApp.getInstance().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
		File rootFile = MyApplication.getInstance().getExternalFilesDir(null);
		if (rootFile != null) {
			String path = rootFile.getAbsolutePath();
			if (!path.endsWith(File.separator)) {
				return path;
			} else {
				int lastIndex = path.lastIndexOf(File.pathSeparator);
				// 如果路径中带有“/”，则删除该"/"
				return path.substring(0, lastIndex);
			}
		} else {
			return null;
		}

	}



	// 异常捕获
	private boolean isNeedCaughtException = true;// 是否捕获未知异常
	private PendingIntent restartIntent;
	private MyUncaughtExceptionHandler uncaughtExceptionHandler;

	// -------------------异常捕获-----捕获异常后重启系统-----------------//
	private void catchException() {
		// 程序崩溃时触发线程
		uncaughtExceptionHandler = new MyUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(uncaughtExceptionHandler);
	}

	// 创建服务用于捕获崩溃异常
	private class MyUncaughtExceptionHandler implements
			UncaughtExceptionHandler {
		@Override
		public void uncaughtException(@NonNull Thread t, @NonNull Throwable e) {
			saveCatchInfo2File(e);
			// 重启应用
			AlarmManager mgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
			if (mgr != null) {
				mgr.set(AlarmManager.RTC, System.currentTimeMillis(), restartIntent);
			}
			//关闭当前应用
			android.os.Process.killProcess(android.os.Process.myPid());
		}
	}


	/**
	 * 保存异常信息到文件中
	 * @param ex 异常信息
	 */
	public void saveCatchInfo2File(Throwable ex) {
		Writer writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		ex.printStackTrace(printWriter);
		Throwable cause = ex.getCause();
		while (cause != null) {
			cause.printStackTrace(printWriter);
			cause = cause.getCause();
		}
		printWriter.close();
		String sb = writer.toString();
		Log.e("error", sb);
		try {
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
			String time = formatter.format(new Date());
			String fileName = time + ".txt";
			System.out.println("fileName:" + fileName);
			if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
				String filePath = MyApplication.getExternalFilesDirPath()  + "bugs/";
				File dir = new File(filePath);
				if (!dir.exists()) {
					if (!dir.mkdirs()) {
						// 创建目录失败: 一般是因为SD卡被拔出了
						return;
					}
				}
				FileOutputStream fos = new FileOutputStream(filePath + fileName);
				PackageInfo info = getPackageManager().getPackageInfo(this.getPackageName(), 0);
				String str = "versionCode:" + info.versionCode + "\n";
				fos.write(str.getBytes());
				fos.write(sb.getBytes());
				fos.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
