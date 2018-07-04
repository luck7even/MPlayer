package com.MP3Player;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.preference.Preference.OnPreferenceClickListener;

public class MyPreference extends PreferenceActivity {
	/**
	 * @uml.property  name="check_Accelometer"
	 * @uml.associationEnd  
	 */
	private CheckBoxPreference check_Accelometer;
	/**
	 * @uml.property  name="bassBoost"
	 * @uml.associationEnd  
	 */
	private CheckBoxPreference bassBoost;
	/**
	 * @uml.property  name="virtualizer"
	 * @uml.associationEnd  
	 */
	private CheckBoxPreference virtualizer;
	/**
	 * @uml.property  name="sleepTime"
	 * @uml.associationEnd  
	 */
	private ListPreference SleepTime;
	/**
	 * @uml.property  name="setting_EQ"
	 * @uml.associationEnd  
	 */
	private ListPreference setting_EQ;
	/**
	 * @uml.property  name="mEQ"
	 * @uml.associationEnd  
	 */
	private MyPrefence_EQsetup MEQ;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preference_activity);

		/* 가속도 체크여부 */
		check_Accelometer = (CheckBoxPreference) findPreference("check_Accelometer");
		check_Accelometer.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			public boolean onPreferenceClick(Preference preference) {
				return false;
			}

		});
		/* 취침예약 체크여부 */
		SleepTime = (ListPreference) findPreference("SleepTime");
		SleepTime.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
			public boolean onPreferenceChange(Preference preference, Object newValue) {
				if (!newValue.equals("0")) {
					SleepTime(Integer.valueOf(String.valueOf(newValue)));
				}
				return true;
			}
		});

		if (Integer.parseInt(Build.VERSION.SDK) > 9) // API 9이상일 경우 Eqaulizer 실행
		{
			MEQ = MyPrefence_EQsetup.getInstance();
			
			/* bassBoost 세팅 */
			bassBoost = (CheckBoxPreference) findPreference("bassBoost");
			bassBoost.setOnPreferenceClickListener(new OnPreferenceClickListener() {
				public boolean onPreferenceClick(Preference preference) {
					if (bassBoost.isChecked())
						MEQ.setupBassBoost(true);
					else
						MEQ.setupBassBoost(false);
					return false;
				}

			});

			/* virtualizer 세팅 */
			virtualizer = (CheckBoxPreference) findPreference("virtualizer");
			virtualizer.setOnPreferenceClickListener(new OnPreferenceClickListener() {
				public boolean onPreferenceClick(Preference preference) {
					if (virtualizer.isChecked())
						MEQ.setupmVirtualizer(true);
					else
						MEQ.setupmVirtualizer(false);
					return false;
				}

			});

			/* Eqaulizer 세팅 */
			setting_EQ = (ListPreference) findPreference("setting_EQ");
			setting_EQ.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
				public boolean onPreferenceChange(Preference preference, Object newValue) {
					MEQ.setupEqualizer(Integer.valueOf(String.valueOf(newValue)));
					return true;
				}
			});
		}
	}

	/* 취침예약 체크시 intent화면 이동 */
	private void SleepTime(int time) {
		finish();
		Intent intent = new Intent(MyPreference.this, MP3PlayerActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
		intent.putExtra("KILL_ACT", true);
		intent.putExtra("KILL_TIME", time);
		startActivity(intent);
	}
}
