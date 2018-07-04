package com.MP3Player;

import java.util.ArrayList;
import java.util.StringTokenizer;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MyListEdit extends Activity {
	/**
	 * @uml.property  name="al"
	 */
	private ArrayList<EditMusic> al;
	/**
	 * @uml.property  name="mylistedit"
	 * @uml.associationEnd  
	 */
	private ListView mylistedit;
	/**
	 * @uml.property  name="adapter"
	 * @uml.associationEnd  inverse="this$0:com.MP3Player.MyListEdit$GroupAdapter"
	 */
	private GroupAdapter adapter;
	/**
	 * @uml.property  name="editMusic"
	 * @uml.associationEnd  
	 */
	private Button editMusic;
	/**
	 * @uml.property  name="editMusicDelete"
	 * @uml.associationEnd  
	 */
	private Button editMusicDelete;
	/**
	 * @uml.property  name="editMusicRandomPlay"
	 * @uml.associationEnd  
	 */
	private Button editMusicRandomPlay;
	/**
	 * @uml.property  name="rowId"
	 */
	private Long rowId;
	/**
	 * @uml.property  name="data"
	 */
	private String data;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mylistedit);
		mylistedit = (ListView) findViewById(R.id.mylistedit);
		editMusic = (Button) findViewById(R.id.editMusic);
		editMusicDelete = (Button) findViewById(R.id.editMusicDelete);
		editMusicRandomPlay = (Button) findViewById(R.id.editMusicRandomPlay);

		al = new ArrayList<EditMusic>();
		Intent intent = new Intent(getIntent());
		rowId = intent.getLongExtra("DATE", -1);
		Cursor cursor = MyList.mHelper.getWritableDatabase().rawQuery("select date from singers where _ID=" + rowId,
				null);
		cursor.moveToFirst();
		data = cursor.getString(0);
		StringTokenizer st = new StringTokenizer(data, ",");
		while (st.hasMoreTokens()) {
			String data = st.nextToken();
			for (int i = 0; i < MP3PlayerActivity.mList.size(); i++) {
				if (MP3PlayerActivity.mList.get(i).getPath().equals(data)) {
					al.add(new EditMusic(MP3PlayerActivity.mList.get(i).getIcon(), MP3PlayerActivity.mList.get(i)
							.getGasu() + " - " + MP3PlayerActivity.mList.get(i).getJemok(), MP3PlayerActivity.mList
							.get(i).getAlbumName(), MP3PlayerActivity.mList.get(i).getPath()));
					break;
				}
			}
		}

		editMusic.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(MyListEdit.this, MyListMusic.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
				intent.putExtra("data", data);
				intent.putExtra("rowId", rowId);
				startActivity(intent);
				finish();
			}
		});
		editMusicDelete.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				processDelete(rowId);
			}
		});
		editMusicRandomPlay.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (al.size() != 0) {
					MP3PlayerActivity.temp.clear();
					for (int i = 0; i < al.size(); i++) {
						for (int k = 0; i < MP3PlayerActivity.mList.size(); k++)
							if (MP3PlayerActivity.mList.get(k).getPath().equals(al.get(i).getEditMusicPath())) {
								MP3PlayerActivity.temp.add(MP3PlayerActivity.mList.get(k));
								break;
							}
					}
					finish();
					Intent intent = new Intent(MyListEdit.this, MP3PlayerActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
					intent.putExtra("newMusic", true);
					intent.putExtra("cp", ((int) (Math.random() * (al.size())) + 1) - 1);
					startActivity(intent);
				}
			}
		});

		adapter = new GroupAdapter(this, R.layout.mp3_list, al);
		mylistedit.setAdapter(adapter);
		mylistedit.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				MP3PlayerActivity.temp.clear();
				for (int i = 0; i < al.size(); i++) {
					for (int k = 0; i < MP3PlayerActivity.mList.size(); k++)
						if (MP3PlayerActivity.mList.get(k).getPath().equals(al.get(i).getEditMusicPath())) {
							MP3PlayerActivity.temp.add(MP3PlayerActivity.mList.get(k));
							break;
						}
				}
				finish();
				Intent intent = new Intent(MyListEdit.this, MP3PlayerActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
				intent.putExtra("newMusic", true);
				intent.putExtra("cp", position);
				startActivity(intent);
			}
		});
	}

	private void processDelete(final long rowId) {
		AlertDialog.Builder aDialog = new AlertDialog.Builder(MyListEdit.this);
		aDialog.setTitle("재생목록을 삭제하시겠습니까?");
		aDialog.setIcon(R.drawable.icon);
		aDialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				String[] args = { String.valueOf(rowId) };
				MyList.mHelper.getWritableDatabase().delete("singers", "_ID=?", args);
				MyList.mCursor.requery();
				finish();

				new AlertDialog.Builder(MyListEdit.this).setMessage("삭제하였습니다.").setTitle("목록 삭제")
						.setPositiveButton("확인", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								finish();
							}
						}).show();
			}
		});
		aDialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		aDialog.show();
	}

	/**
	 * @author  yamaia
	 */
	private class GroupAdapter extends ArrayAdapter<Object> {
		private ArrayList<EditMusic> item;
		/**
		 * @uml.property  name="temp"
		 * @uml.associationEnd  
		 */
		private EditMusic temp;

		@SuppressWarnings({ "rawtypes", "unchecked" })
		public GroupAdapter(Context ctx, int resourceID, ArrayList item) {
			super(ctx, resourceID, item);
			this.item = item;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.mp3_list, null);
			}

			temp = item.get(position);
			if (temp != null) {
				ImageView imageview = (ImageView) v.findViewById(R.id.row_album_art);
				TextView tt = (TextView) v.findViewById(R.id.row_artist);
				TextView bt = (TextView) v.findViewById(R.id.row_title);
				if(temp.getEditMusicImage()!=null)
					imageview.setImageBitmap(temp.getEditMusicImage());
				else
					imageview.setImageResource(R.drawable.noimageicon);
				
				tt.setText(temp.getEditMusicTitle());
				bt.setText(temp.getEditMusicAlbum());
				
			}

			return v;
		}

	}
}

class EditMusic {
	/**
	 * @uml.property  name="editMusicImage"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private Bitmap editMusicImage;
	/**
	 * @uml.property  name="editMusicTitle"
	 */
	private String editMusicTitle;
	/**
	 * @uml.property  name="editMusicAlbum"
	 */
	private String editMusicAlbum;
	/**
	 * @uml.property  name="editMusicPath"
	 */
	private String editMusicPath;

	EditMusic(Bitmap editMusicImage, String editMusicTitle, String editMusicAlbum, String editMusicPath) {
		this.editMusicImage = editMusicImage;
		this.editMusicTitle = editMusicTitle;
		this.editMusicAlbum = editMusicAlbum;
		this.editMusicPath = editMusicPath;
	}

	/**
	 * @return
	 * @uml.property  name="editMusicImage"
	 */
	public Bitmap getEditMusicImage() {
		return editMusicImage;
	}

	/**
	 * @param editMusicImage
	 * @uml.property  name="editMusicImage"
	 */
	public void setEditMusicImage(Bitmap editMusicImage) {
		this.editMusicImage = editMusicImage;
	}

	/**
	 * @return
	 * @uml.property  name="editMusicTitle"
	 */
	public String getEditMusicTitle() {
		return editMusicTitle;
	}

	/**
	 * @param editMusicTitle
	 * @uml.property  name="editMusicTitle"
	 */
	public void setEditMusicTitle(String editMusicTitle) {
		this.editMusicTitle = editMusicTitle;
	}

	/**
	 * @return
	 * @uml.property  name="editMusicAlbum"
	 */
	public String getEditMusicAlbum() {
		return editMusicAlbum;
	}

	/**
	 * @param editMusicAlbum
	 * @uml.property  name="editMusicAlbum"
	 */
	public void setEditMusicAlbum(String editMusicAlbum) {
		this.editMusicAlbum = editMusicAlbum;
	}

	/**
	 * @return
	 * @uml.property  name="editMusicPath"
	 */
	public String getEditMusicPath() {
		return editMusicPath;
	}

	/**
	 * @param editMusicPath
	 * @uml.property  name="editMusicPath"
	 */
	public void setEditMusicPath(String editMusicPath) {
		this.editMusicPath = editMusicPath;
	}
}