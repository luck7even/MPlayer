package com.MP3Player;

import net.daum.mobilead.AdConfig;
import net.daum.mobilead.AdHttpListener;
import net.daum.mobilead.MobileAdView;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.app.TabActivity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import android.widget.TabHost.OnTabChangeListener;

public class MP3PlayerTab extends TabActivity implements AdHttpListener {
	/** Called when the activity is first created. */
	private static TabHost tabHost;
	/**
	 * @uml.property  name="backNowplaying"
	 * @uml.associationEnd  
	 */
	private ImageButton backNowplaying;
	/**
	 * @uml.property  name="tapSubject"
	 * @uml.associationEnd  
	 */
	private TextView tapSubject;
	/**
	 * @uml.property  name="adView"
	 * @uml.associationEnd  
	 */
	private MobileAdView adView = null;
	/**
	 * @uml.property  name="adLayout"
	 * @uml.associationEnd  
	 */
	private FrameLayout adLayout;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mp3playertap);
		tapSubject = (TextView) findViewById(R.id.tapSubject);


		// 탭 불러오기
		ConnectivityManager connect = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		if (connect.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting()
				|| connect.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting()) {
			// 할당 받은 clientId 설정
			adLayout = (FrameLayout) findViewById(R.id.adLayout);
			AdConfig.setClientId("ce1Z1OT1318937f697");
			// Ad@m sdk 초기화 시작
			adLayout.setVisibility(View.VISIBLE);
			adView = (MobileAdView) findViewById(R.id.adview);
			// adView.setRequestInterval(30);
			adView.setAdListener(this);
			adView.setVisibility(View.VISIBLE);
		} else {

		}
		MP3PlayerTab.tabHost = getTabHost();
		TabHost.TabSpec spec;

		// 재생목록
		Intent intent;

		intent = new Intent().setClass(this, MyList.class);
		spec = tabHost.newTabSpec("재생목록").setIndicator("재생목록", getResources().getDrawable(R.drawable.mylist))
				.setContent(intent);
		tabHost.addTab(spec);
		// 앨범
		intent = new Intent().setClass(this, MyAlbum.class);
		spec = tabHost.newTabSpec("앨범목록").setIndicator("앨범목록", getResources().getDrawable(R.drawable.album))
				.setContent(intent);

		tabHost.addTab(spec);
		// 노래
		intent = new Intent().setClass(this, MyFolder.class);
		spec = tabHost.newTabSpec("폴더목록").setIndicator("폴더목록", getResources().getDrawable(R.drawable.folder))
				.setContent(intent);
		tabHost.addTab(spec);

		// 환경설정
		intent = new Intent().setClass(this, MyPreference.class);
		spec = tabHost.newTabSpec("환경설정").setIndicator("환경설정", getResources().getDrawable(R.drawable.preferences))
				.setContent(intent);
		tabHost.addTab(spec);
		tabHost.setCurrentTab(0);
		tapSubject.setText("재생목록");

		backNowplaying = (ImageButton) findViewById(R.id.backNowplaying);
		backNowplaying.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
		tabHost.setOnTabChangedListener(new OnTabChangeListener() {
			public void onTabChanged(String tabId) {
				tapSubject.setText(tabHost.getCurrentTabTag());
			}
		});
	}

	public void didDownloadAd_AdListener() {
	}

	public void failedDownloadAd_AdListener(int arg0, String arg1) {
	}
}
