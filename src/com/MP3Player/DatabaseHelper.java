package com.MP3Player;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "singer.db";
	static final String NAME = "name";
	static final String DATE = "date";
	
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, 1);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE singers (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, date TEXT);");
		
		ContentValues cv = new ContentValues();
		
		cv.put(NAME, "재생목록 추가..");
		cv.put(DATE, "");
		db.insert("singers", NAME, cv);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS singers");
		onCreate(db);
	}
}