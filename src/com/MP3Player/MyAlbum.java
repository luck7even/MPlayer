package com.MP3Player;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MyAlbum extends Activity {
	/**
	 * @uml.property  name="al"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="com.MP3Player.Album"
	 */
	private ArrayList<Album> al;
	/**
	 * @uml.property  name="listview"
	 * @uml.associationEnd  
	 */
	private ListView listview;
	/**
	 * @uml.property  name="adapter"
	 * @uml.associationEnd  inverse="this$0:com.MP3Player.MyAlbum$GroupAdapter"
	 */
	private GroupAdapter adapter;
	/**
	 * @uml.property  name="state"
	 */
	private boolean state = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myalbum_list);
		listview = (ListView) findViewById(R.id.myalbum_list);
		al = new ArrayList<Album>();

		for (int i = 0; i < MP3PlayerActivity.mList.size(); i++) {
			MP3Music m = MP3PlayerActivity.mList.get(i);
			String albumName = m.getAlbumName();
			Bitmap albumImage = m.getIcon();
			if (al.isEmpty()) {
				al.add(new Album(i, albumName, albumImage));
			} else if (!al.isEmpty()) {
				for (int k = 0; k < al.size(); k++) {
					if (al.get(k).getalbumName().equals(albumName)) {
						al.get(k).setmIdx(i);
						state = false;
						break;
					}
					state = true;
				}
				if (state == true) {
					al.add(new Album(i, albumName,albumImage));
					state = false;
				}
			}
		}
		adapter = new GroupAdapter(this, R.layout.myalbum, al);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				MP3PlayerActivity.temp.clear();
				for (int i = 0; i < al.get(position).mIdx.size(); i++) {
					MP3PlayerActivity.temp.add(MP3PlayerActivity.mList.get(al.get(position).mIdx.get(i)));				
				}
				finish();
				Intent intent = new Intent(MyAlbum.this, MP3PlayerActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
				intent.putExtra("newMusic", true);
				startActivity(intent);
			}
		});
	}

	/**
	 * @author  yamaia
	 */
	private class GroupAdapter extends ArrayAdapter<Object> {
		private ArrayList<Album> item;
		/**
		 * @uml.property  name="temp"
		 * @uml.associationEnd  
		 */
		private Album temp;
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public GroupAdapter(Context ctx, int resourceID, ArrayList item) {
			super(ctx, resourceID, item);
			this.item = item;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.myalbum, null);
			}

			temp = item.get(position);
			if (temp != null) {
				TextView album_text = (TextView) v.findViewById(R.id.album_text);
				TextView album_size = (TextView) v.findViewById(R.id.album_size);
				ImageView albumimage = (ImageView) v.findViewById(R.id.albumimage);
				
				album_text.setText(temp.getalbumName());
				album_size.setText("¾Ù¹ü " + temp.getSize() + "°î");
				if(temp.albumImage!=null)
					albumimage.setImageBitmap(temp.albumImage);
				else
					albumimage.setImageResource(R.drawable.noimageicon);
			}

			return v;
		}

	}
}

class Album {
	/**
	 * @uml.property  name="albumName"
	 */
	String albumName;
	/**
	 * @uml.property  name="mIdx"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="java.lang.Integer"
	 */
	ArrayList<Integer> mIdx = new ArrayList<Integer>();
	/**
	 * @uml.property  name="albumImage"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	Bitmap albumImage;
	Album(int number, String path, Bitmap albumImage) {
		this.mIdx.add(number);
		this.albumName = path;
		this.albumImage = albumImage;
	}

	public void setmIdx(int number) {
		this.mIdx.add(number);
	}

	public String getalbumName() {
		return albumName;
	}

	public int getSize() {
		return mIdx.size();
	}
}