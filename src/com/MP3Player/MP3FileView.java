package com.MP3Player;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class MP3FileView extends Activity {
	/**
	 * @uml.property  name="path"
	 */
	private String path;
	/**
	 * @uml.property  name="fileEdit"
	 */
	private Boolean fileEdit = null;
	/**
	 * @uml.property  name="jaudiotagger"
	 * @uml.associationEnd  
	 */
	private MP3JAudioTagger jaudiotagger;
	/**
	 * @uml.property  name="aLBUM"
	 * @uml.associationEnd  
	 */
	private EditText ALBUM;
	/**
	 * @uml.property  name="aRTIST"
	 * @uml.associationEnd  
	 */
	private EditText ARTIST;
	/**
	 * @uml.property  name="cOMMENT"
	 * @uml.associationEnd  
	 */
	private EditText COMMENT;
	/**
	 * @uml.property  name="eNCODER"
	 * @uml.associationEnd  
	 */
	private EditText ENCODER;
	/**
	 * @uml.property  name="gENRE"
	 * @uml.associationEnd  
	 */
	private EditText GENRE;
	/**
	 * @uml.property  name="lYRICS"
	 * @uml.associationEnd  
	 */
	private EditText LYRICS;
	/**
	 * @uml.property  name="tITLE"
	 * @uml.associationEnd  
	 */
	private EditText TITLE;
	/**
	 * @uml.property  name="yEAR"
	 * @uml.associationEnd  
	 */
	private EditText YEAR;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mp3fileview);
		ALBUM = (EditText) findViewById(R.id.ALBUM);
		ARTIST = (EditText) findViewById(R.id.ARTIST);
		COMMENT = (EditText) findViewById(R.id.COMMENT);
		ENCODER = (EditText) findViewById(R.id.ENCODER);
		GENRE = (EditText) findViewById(R.id.GENRE);
		LYRICS = (EditText) findViewById(R.id.LYRICS);
		TITLE = (EditText) findViewById(R.id.TITLE);
		YEAR = (EditText) findViewById(R.id.YEAR);
		ImageView fileAlbumImage = (ImageView) findViewById(R.id.fileAlbumImage);

		Button modifyOk = (Button) findViewById(R.id.modifyOk);
		modifyOk.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				AlertDialog.Builder aDialog = new AlertDialog.Builder(MP3FileView.this);
				aDialog.setTitle("입력한 내용을 수정하시겠습니까?");
				aDialog.setIcon(R.drawable.icon);
				aDialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						jaudiotagger.setALBUM(ALBUM.getText().toString());
						jaudiotagger.setARTIST(ARTIST.getText().toString());
						jaudiotagger.setCOMMENT(COMMENT.getText().toString());
						jaudiotagger.setENCODER(ENCODER.getText().toString());
						jaudiotagger.setGENRE(GENRE.getText().toString());
						jaudiotagger.setTITLE(TITLE.getText().toString());
						jaudiotagger.setYEAR(YEAR.getText().toString());
						jaudiotagger.setLYRICS(LYRICS.getText().toString());
						jaudiotagger.tagModify();

						new AlertDialog.Builder(MP3FileView.this)
								.setMessage("수정되었습니다. 수정된 파일 정보는 MPlayer가 종료된 후  적용됩니다.").setTitle("파일 수정")
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
		});

		Button modifyCancle = (Button) findViewById(R.id.modifyCancle);
		modifyCancle.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
				onBackPressed();
			}
		});

		Intent intent = new Intent(this.getIntent());
		path = intent.getStringExtra("path");
		fileEdit = intent.getBooleanExtra("fileEdit", false);

		if (fileEdit == true) {
			ALBUM.setEnabled(true);
			ARTIST.setEnabled(true);
			COMMENT.setEnabled(true);
			ENCODER.setEnabled(true);
			GENRE.setEnabled(true);
			TITLE.setEnabled(true);
			YEAR.setEnabled(true);
			LYRICS.setEnabled(true);
			modifyOk.setVisibility(View.VISIBLE);
			modifyCancle.setVisibility(View.VISIBLE);
			modifyOk.setText("확인");
			modifyCancle.setText("취소");

		} else {
			ALBUM.setEnabled(false);
			ARTIST.setEnabled(false);
			COMMENT.setEnabled(false);
			ENCODER.setEnabled(false);
			GENRE.setEnabled(false);
			TITLE.setEnabled(false);
			YEAR.setEnabled(false);
			LYRICS.setEnabled(false);
			modifyOk.setVisibility(View.GONE);
			modifyCancle.setVisibility(View.VISIBLE);
			modifyCancle.setText("확인");
		}

		jaudiotagger = com.MP3Player.MP3JAudioTagger.getInstance(path);
		jaudiotagger.init();
		try {
			ALBUM.setText(jaudiotagger.getALBUM());
			ARTIST.setText(jaudiotagger.getARTIST());
			COMMENT.setText(jaudiotagger.getCOMMENT());
			ENCODER.setText(jaudiotagger.getENCODER());
			GENRE.setText(jaudiotagger.getGENRE());
			TITLE.setText(jaudiotagger.getTITLE());
			YEAR.setText(jaudiotagger.getYEAR());
			fileAlbumImage.setImageDrawable(jaudiotagger.getBackground());
			LYRICS.setText(jaudiotagger.getLYRICS());
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

	}
}
