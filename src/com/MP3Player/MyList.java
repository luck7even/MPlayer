package com.MP3Player;

import java.util.ArrayList;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class MyList extends Activity {
	/**
	 * @uml.property  name="arGeneral"
	 */
	ArrayList<String> arGeneral;
	static DatabaseHelper mHelper = null;
	static Cursor mCursor = null;
	/**
	 * @uml.property  name="mylist"
	 * @uml.associationEnd  
	 */
	private ListView mylist;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mylist);
		mylist = (ListView) findViewById(R.id.mylist);
		mHelper = new DatabaseHelper(this);

		mCursor = mHelper.getWritableDatabase().rawQuery("SELECT _ID, name, date FROM singers ORDER BY _ID", null);

		ListAdapter adapter = new SimpleCursorAdapter(this, R.layout.mylistrow, mCursor,
				new String[] { DatabaseHelper.NAME }, new int[] { R.id.name });

		mylist.setAdapter(adapter);
		mylist.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				if (position == 0) {
					add();
				} else {
					Intent intent = new Intent(MyList.this, MyListEdit.class);
					intent.putExtra("DATE", arg0.getItemIdAtPosition(position));
					startActivity(intent);
				}
			}
		});
		registerForContextMenu(mylist);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mCursor.close();
		mHelper.close();
	}

	private void add() {
		LayoutInflater inflater = LayoutInflater.from(this);
		View addView = inflater.inflate(R.layout.add_edit, null);
		final DialogWrapper wrapper = new DialogWrapper(addView);

		new AlertDialog.Builder(this).setTitle("재생목록 추가").setView(addView)
				.setPositiveButton("확인", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						if (!wrapper.getCode().replace(" ", "").equals("")) {
							Intent intent = new Intent(MyList.this, MyListMusic.class);
							intent.putExtra("wrapper", wrapper.getCode());
							startActivity(intent);
						} else {

						}
					}
				}).setNegativeButton("취소", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						// ignore, just dismiss
					}
				}).show();
	}

	class DialogWrapper {
		EditText nameField = null;
		View view = null;

		DialogWrapper(View view) {
			this.view = view;
		}

		String getCode() {
			return (getCodeField().getText().toString());
		}

		EditText getCodeField() {
			if (nameField == null) {
				nameField = (EditText) view.findViewById(R.id.name);
			}
			return (nameField);
		}
	}
}
