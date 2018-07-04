package com.MP3Player;

import android.media.audiofx.BassBoost;
import android.media.audiofx.Equalizer;
import android.media.audiofx.Virtualizer;

public class MyPrefence_EQsetup {
	/**
	 * @uml.property  name="mEqualizer"
	 * @uml.associationEnd  
	 */
	private Equalizer mEqualizer;
	/**
	 * @uml.property  name="mBassBoost"
	 * @uml.associationEnd  
	 */
	private BassBoost mBassBoost;
	/**
	 * @uml.property  name="mVirtualizer"
	 * @uml.associationEnd  
	 */
	private Virtualizer mVirtualizer;
	static MyPrefence_EQsetup instance;

	MyPrefence_EQsetup() {
	}

	public static MyPrefence_EQsetup getInstance() {
		if (instance == null) {
			instance = new MyPrefence_EQsetup();
		}
		return instance;
	}

	void setupBassBoost(boolean check) {
		if (check == true) {
			try {
				mBassBoost = null;
				mBassBoost = new BassBoost(0, MP3PlayerActivity.sessionId);
				mBassBoost.setEnabled(true);
				mBassBoost.setStrength((short) 1000); // on
			} catch (UnsupportedOperationException e) {
				e.printStackTrace();
			}
		} else {
			try {
				mBassBoost.release();
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}
	}

	void setupmVirtualizer(boolean check) {
		if (check == true) {
			try {
				mVirtualizer = null;
				mVirtualizer = new Virtualizer(0, MP3PlayerActivity.sessionId);
				mVirtualizer.setEnabled(true);
				mVirtualizer.setStrength((short) 1000); // on
			} catch (UnsupportedOperationException e) {
				e.printStackTrace();
			}
		} else {
			try {
				mVirtualizer.release();
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}

	}

	void setupEqualizer(int sel) {
		switch (sel) {
		case 0:
			try {
				mEqualizer.setBandLevel((short) 0, (short) (0));
				mEqualizer.setBandLevel((short) 1, (short) (0));
				mEqualizer.setBandLevel((short) 2, (short) (0));
				mEqualizer.setBandLevel((short) 3, (short) (0));
				mEqualizer.setBandLevel((short) 4, (short) (0));
				mEqualizer.release();
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
			break;
		case 1:
			settingEqualizer();
			modeEqualizer(450, 100, 150, 400, 150);
			break;
		case 2:
			settingEqualizer();
			modeEqualizer(400, 200, 0, 0, 0);
			break;
		case 3:
			settingEqualizer();
			modeEqualizer(-450, -300, 0, 0, 0);
			break;
		case 4:
			settingEqualizer();
			modeEqualizer(400, 250, -200, 300, 350);
			break;
		case 5:
			settingEqualizer();
			modeEqualizer(600, 0, 300, 300, 0);
			break;
		case 6:
			settingEqualizer();
			modeEqualizer(300, 100, 250, -350, -500);
			break;
		case 7:
			settingEqualizer();
			modeEqualizer(300, 0, 250, 350, 450);
			break;
		case 8:
			settingEqualizer();
			modeEqualizer(0, 0, 0, 0, 0);
			break;
		case 9:
			settingEqualizer();
			modeEqualizer(350, 250, -150, 150, 300);
			break;
		case 10:
			settingEqualizer();
			modeEqualizer(300, 250, -150, 300, 350);
			break;
		case 11:
			settingEqualizer();
			modeEqualizer(300, 0, -150, 300, 400);
			break;
		case 12:
			settingEqualizer();
			modeEqualizer(350, 0, 0, 450, 50);
			break;
		case 13:
			settingEqualizer();
			modeEqualizer(-150, 100, 250, 150, 100);
			break;
		case 14:
			settingEqualizer();
			modeEqualizer(150, 250, 150, 300, 350);
			break;
		case 15:
			settingEqualizer();
			modeEqualizer(-150, 200, 350, -150, -200);
			break;
		case 16:
			settingEqualizer();
			modeEqualizer(650, 150, -150, 300, 350);
			break;
		case 17:
			settingEqualizer();
			modeEqualizer(350, 150, -150, 300, 350);
			break;
		case 18:
			settingEqualizer();
			modeEqualizer(350, 250, 0, -350, -450);
			break;
		case 19:
			settingEqualizer();
			modeEqualizer(-50, 50, 450, 250, 0);
			break;
		case 20:
			settingEqualizer();
			modeEqualizer(0, 0, 100, 350, 550);
			break;
		case 21:
			settingEqualizer();
			modeEqualizer(0, 0, -150, -450, -600);
			break;
		case 22:
			settingEqualizer();
			modeEqualizer(-300, 100, 300, 0, -150);
			break;
		default:
			break;
		}
	}

	void settingEqualizer() {
		try {
			mEqualizer = null;
			mEqualizer = new Equalizer(0, MP3PlayerActivity.sessionId);
			mEqualizer.setEnabled(true);
		} catch (UnsupportedOperationException e) {
			e.printStackTrace();
		}
	}

	void modeEqualizer(int first, int second, int three, int four, int five) {
		short bands = mEqualizer.getNumberOfBands();
		if (bands >= 5) {
			mEqualizer.setBandLevel((short) 0, (short) first);
			mEqualizer.setBandLevel((short) 1, (short) second);
			mEqualizer.setBandLevel((short) 2, (short) three);
			mEqualizer.setBandLevel((short) 3, (short) four);
			mEqualizer.setBandLevel((short) 4, (short) five);
		}
	}
}
