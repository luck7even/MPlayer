package com.MP3Player;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MyFolder extends Activity {
	/**
	 * @uml.property  name="al"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="com.MP3Player.Folder"
	 */
	private ArrayList<Folder> al;
	/**
	 * @uml.property  name="listview"
	 * @uml.associationEnd  
	 */
	private ListView listview;
	/**
	 * @uml.property  name="adapter"
	 * @uml.associationEnd  inverse="this$0:com.MP3Player.MyFolder$GroupAdapter"
	 */
	private GroupAdapter adapter;
	/**
	 * @uml.property  name="state"
	 */
	private boolean state = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myfolder_list);
		listview = (ListView) findViewById(R.id.myfolder_list);
		al = new ArrayList<Folder>();

		for (int i = 0; i < MP3PlayerActivity.mList.size(); i++) {
			MP3Music m = MP3PlayerActivity.mList.get(i);
			String path = m.getPath().substring(4, m.getPath().lastIndexOf("/") + 1);
			if (al.isEmpty()) {
				al.add(new Folder(i, path));
			} else if (!al.isEmpty()) {
				for (int k = 0; k < al.size(); k++) {
					if (al.get(k).getPath().equals(path)) {
						al.get(k).setmIdx(i);
						state = false;
						break;
					}
					state = true;
				}
				if (state == true) {
					al.add(new Folder(i, path));
					state = false;
				}
			}
		}
		adapter = new GroupAdapter(this, R.layout.myfolder, al);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				MP3PlayerActivity.temp.clear();
				for (int i = 0; i < al.get(position).mIdx.size(); i++) {
					MP3PlayerActivity.temp.add(MP3PlayerActivity.mList.get(al.get(position).mIdx.get(i)));				
				}
				finish();
				Intent intent = new Intent(MyFolder.this, MP3PlayerActivity.class);
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
		private ArrayList<Folder> item;
		/**
		 * @uml.property  name="temp"
		 * @uml.associationEnd  
		 */
		private Folder temp;
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public GroupAdapter(Context ctx, int resourceID, ArrayList item) {
			super(ctx, resourceID, item);
			this.item = item;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.myfolder, null);
			}

			temp = item.get(position);
			if (temp != null) {

				TextView group_text = (TextView) v.findViewById(R.id.group_text);
				TextView group_size = (TextView) v.findViewById(R.id.group_size);
				group_text.setText(temp.getPath());
				group_size.setText("³ë·¡ " + temp.getSize() + "°î");
				
			}

			return v;
		}

	}
}

class Folder {
	/**
	 * @uml.property  name="path"
	 */
	String path;
	/**
	 * @uml.property  name="mIdx"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="java.lang.Integer"
	 */
	ArrayList<Integer> mIdx = new ArrayList<Integer>();

	Folder(int number, String path) {
		this.mIdx.add(number);
		this.path = path;
	}

	public void setmIdx(int number) {
		this.mIdx.add(number);
	}

	/**
	 * @return
	 * @uml.property  name="path"
	 */
	public String getPath() {
		return path;
	}

	public int getSize() {
		return mIdx.size();
	}
}