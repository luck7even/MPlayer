package com.MP3Player;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

public class LayoutCenter extends LinearLayout {
	public LayoutCenter(Context context, AttributeSet attrs) {
		super(context, attrs);
		setOrientation(HORIZONTAL);
		setGravity(Gravity.CENTER);
		setWeightSum(1.0f);
		
		LayoutInflater.from(context).inflate(R.layout.layoutcenter, this, true);
	}
}
