package com.MP3Player;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;

public class SplashScreenActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);

		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
		SharedPreferences.Editor editor = pref.edit();
		
		//ȯ�漳�� ��ħ���� �ʱ�ȭ
		editor.putString("SleepTime", "0");
		editor.commit();
		
		//��⿡ ����� mediascan�� �ҷ���
		sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://"
				+ Environment.getExternalStorageDirectory())));
		
		final int welcomeScreenDisplay = 3000; //�ε�ȭ�� 3��
		Thread welcomeThread = new Thread() {
			int wait = 0;
			@Override
			public void run() {
				try {
					super.run();

					while (wait < welcomeScreenDisplay) {
						sleep(100);
						wait += 100;
					}
				} catch (Exception e) {
					System.out.println("EXc=" + e);
				} finally {
					startActivity(new Intent(SplashScreenActivity.this,
							MP3PlayerActivity.class));
					finish();
				}
			}
		};
		welcomeThread.start();
	}
}