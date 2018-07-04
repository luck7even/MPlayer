package com.MP3Player;

import java.util.ArrayList;
import java.util.StringTokenizer;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class MyListMusic extends Activity {
	/**
	 * @uml.property  name="songList"
	 * @uml.associationEnd  inverse="this$0:com.MP3Player.MyListMusic$MusicInformation"
	 */
	private MusicInformation songList;
	/**
	 * @uml.property  name="mylistadd"
	 * @uml.associationEnd  
	 */
	private ListView mylistadd;
	/**
	 * @uml.property  name="addMusicOk"
	 * @uml.associationEnd  
	 */
	private Button addMusicOk;
	/**
	 * @uml.property  name="addMusicCancle"
	 * @uml.associationEnd  
	 */
	private Button addMusicCancle;
	/**
	 * @uml.property  name="addMusicAll"
	 * @uml.associationEnd  
	 */
	private Button addMusicAll;
	/**
	 * @uml.property  name="check"
	 */
	private int check = 0;
	/**
	 * @uml.property  name="editCheck"
	 */
	private boolean editCheck = false;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mylistadd);
		mylistadd = (ListView) findViewById(R.id.mylistadd);
		addMusicOk = (Button) findViewById(R.id.addMusicOk);
		addMusicCancle = (Button) findViewById(R.id.addMusicCancle);
		addMusicAll = (Button) findViewById(R.id.addMusicAll);
		songList = new MusicInformation(this, R.layout.mylistaddfile, MP3PlayerActivity.mList);
		mylistadd.setAdapter(songList);
		mylistadd.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		resizeParentLayoutHeight(songList);

		Intent intent = new Intent(getIntent());
		String data = intent.getStringExtra("data");
		try {
			StringTokenizer st = new StringTokenizer(data, ",");
			while (st.hasMoreTokens()) {
				String temp = st.nextToken();
				for (int i = 0; i < MP3PlayerActivity.mList.size(); i++) {
					if (MP3PlayerActivity.mList.get(i).getPath().equals(temp)) {
						mylistadd.setItemChecked(i, true);
						break;
					}
				}
			}
			editCheck = true;
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		addMusicAll.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (check == 0) {
					for (int i = 0; i < mylistadd.getCount(); i++)
						mylistadd.setItemChecked(i, true);
					check = 1;
					addMusicAll.setText("전체선택 해제");
				} else if (check == 1) {
					for (int i = 0; i < mylistadd.getCount(); i++)
						mylistadd.setItemChecked(i, false);
					check = 0;
					addMusicAll.setText("전체선택");
				}
			}
		});
		addMusicOk.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(getIntent());
				if (editCheck == false) {
					ContentValues values = new ContentValues(2);
					values.put(DatabaseHelper.NAME, intent.getStringExtra("wrapper"));
					values.put(DatabaseHelper.DATE, CheckData());
					MyList.mHelper.getWritableDatabase().insert("singers", DatabaseHelper.NAME, values);
					MyList.mCursor.requery();
				} else {
					ContentValues values = new ContentValues(1);
					values.put(DatabaseHelper.DATE, CheckData());
					String[] args = { String.valueOf(intent.getLongExtra("rowId", -1)) };
					MyList.mHelper.getWritableDatabase().update("singers", values, "_ID=?", args);
					MyList.mCursor.requery();
					finish();
				}
				finish();
				onBackPressed();
			}
		});

		addMusicCancle.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
				onBackPressed();
			}
		});

	}

	private String CheckData() {
		String data = "";
		SparseBooleanArray Selected = mylistadd.getCheckedItemPositions();
		for (int i = 0; i < MP3PlayerActivity.mList.size(); i++) {
			if (Selected.get(i) == true) {
				if (data.equals(""))
					data += MP3PlayerActivity.mList.get(i).getPath();
				else
					data = data + "," + MP3PlayerActivity.mList.get(i).getPath();
			}
		}
		return data;
	}

	/**
	 * Adapter 를 넘기면 행 갯수 추출하여 MAX_LISTVIEW_ROW_HEIGHT 값을 곱하여 높이를 계산하고,<br/>
	 * ListView 를 감싸고 있는 layout 의 height 값을 조절하면 ListView 의 실제 길이를 계산할 수 있게 된다.<br/>
	 * 실제 길이가 계산되면 감싸고 있는 layout 도 다시 재조절 함으로써 정확하게 리스트뷰를 출력할 수 있다.
	 * 
	 * @param adapter
	 */
	private void resizeParentLayoutHeight(Adapter adapter) {
		if (adapter != null) {
			int listCount = adapter.getCount();
			if (listCount > 0) {
				int height = 500 * listCount;
				resizeParentLayoutHeight(height);
				mylistadd.post(new Runnable() {
					public void run() {
						int height = mylistadd.getMeasuredHeight();
						if (height > 0) {
							resizeParentLayoutHeight(height);
						}
					}
				});
			}
		}
	}

	private void resizeParentLayoutHeight(int height) {
		final LinearLayout layout = (LinearLayout) findViewById(R.id.listViewLayout);
		if (layout != null) {
			final ViewGroup.LayoutParams params = layout.getLayoutParams();
			if (params != null) {
				if (mylistadd != null) {
					// 현재 뷰의 margin 을 계산한다.
					MarginLayoutParams margin = (MarginLayoutParams) mylistadd.getLayoutParams();
					if (margin != null) {
						height += (margin.topMargin + margin.bottomMargin);
					}
				}
				params.height = height;
				runOnUiThread(new Runnable() {
					public void run() {
						layout.setLayoutParams(params);
					}
				});
			}
		}
	}

	public class MusicInformation extends ArrayAdapter<MP3Music> {
		private ArrayList<MP3Music> items;

		private MusicInformation(Context context, int textViewResourceId, ArrayList<MP3Music> items) {
			super(context, textViewResourceId, items);
			this.items = items;
		}

		@Override
		public View getView(int position, View view, ViewGroup parent) {
			View v = view;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.mylistaddfile, null);
			}

			MP3Music m = items.get(position);
			if (m != null) {
				ImageView imageview = (ImageView) v.findViewById(R.id.addAlbum);
				TextView tt = (TextView) v.findViewById(R.id.addArtist);
				TextView bt = (TextView) v.findViewById(R.id.addTitle);
				CheckBox checkbox = (CheckBox) v.findViewById(R.id.addCheck);

				checkbox.setFocusable(false);
				checkbox.setClickable(false);
				checkbox.setChecked(((ListView) parent).isItemChecked(position));
				if (tt != null) {
					tt.setText(m.getGasu() + " - " + m.getJemok());
				}
				if (bt != null) {
					bt.setText(m.getAlbumName());
				}
				if (m.getBackground() != null) {
					imageview.setImageBitmap(m.getIcon());
				} else {
					imageview.setImageResource(R.drawable.noimageicon);
				}
			}
			return v;
		}
	}
}
