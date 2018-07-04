package com.MP3Player;

import java.io.*;
import java.util.*;

import android.app.*;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.*;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.net.Uri;

import android.os.*;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.*;
import android.view.GestureDetector.OnGestureListener;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.AnimationUtils;
import android.widget.*;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class MP3PlayerActivity extends ListActivity implements SensorEventListener {
	/* ----------------mp3player ���̾ƿ� ���� ����---------------- */

	/**
	 * @uml.property  name="remaintime"
	 * @uml.associationEnd  
	 */
	private TextView remaintime;
	/**
	 * @uml.property  name="playtime"
	 * @uml.associationEnd  
	 */
	private TextView playtime;
	/**
	 * @uml.property  name="subject"
	 * @uml.associationEnd  
	 */
	private TextView subject;
	/**
	 * @uml.property  name="start"
	 * @uml.associationEnd  
	 */
	private TextView start;
	/**
	 * @uml.property  name="end"
	 * @uml.associationEnd  
	 */
	private TextView end;
	/**
	 * @uml.property  name="lyricText"
	 * @uml.associationEnd  
	 */
	private TextView lyricText;
	/**
	 * @uml.property  name="listview"
	 * @uml.associationEnd  
	 */
	private ListView listview;
	/**
	 * @uml.property  name="centerimg"
	 * @uml.associationEnd  
	 */
	private ImageView centerimg;
	/**
	 * @uml.property  name="lyricView"
	 * @uml.associationEnd  
	 */
	private ScrollView lyricView;
	/**
	 * @uml.property  name="mp3Tapmode"
	 * @uml.associationEnd  
	 */
	private ImageButton mp3Tapmode;
	/**
	 * @uml.property  name="shuffle"
	 * @uml.associationEnd  
	 */
	private ImageButton shuffle;
	/**
	 * @uml.property  name="playlist"
	 * @uml.associationEnd  
	 */
	private ImageButton playlist;
	/**
	 * @uml.property  name="playpause"
	 * @uml.associationEnd  
	 */
	private ImageButton playpause;
	/**
	 * @uml.property  name="rewindtostart"
	 * @uml.associationEnd  
	 */
	private ImageButton rewindtostart;
	/**
	 * @uml.property  name="forwardtoend"
	 * @uml.associationEnd  
	 */
	private ImageButton forwardtoend;
	/**
	 * @uml.property  name="playmode"
	 * @uml.associationEnd  
	 */
	private ImageButton playmode;
	/**
	 * @uml.property  name="playbar"
	 * @uml.associationEnd  
	 */
	private RelativeLayout playbar;
	/**
	 * @uml.property  name="playlistbar"
	 * @uml.associationEnd  
	 */
	private RelativeLayout playlistbar;
	/**
	 * @uml.property  name="bottom"
	 * @uml.associationEnd  
	 */
	private RelativeLayout bottom;
	/**
	 * @uml.property  name="title"
	 * @uml.associationEnd  
	 */
	private RelativeLayout title;
	/**
	 * @uml.property  name="lyric"
	 * @uml.associationEnd  
	 */
	private RelativeLayout lyric;
	/**
	 * @uml.property  name="center"
	 * @uml.associationEnd  
	 */
	private ViewFlipper center;
	/**
	 * @uml.property  name="playingBar"
	 * @uml.associationEnd  
	 */
	private SeekBar playingBar;
	/**
	 * @uml.property  name="mToast"
	 * @uml.associationEnd  
	 */
	private Toast mToast;
	/* ----------------mp3player ��� ���� üũ �� ����� ��ġ �Ǻ� ���� ����---------------- */

	/**
	 * @uml.property  name="wasPlaying"
	 */
	private boolean wasPlaying;
	/**
	 * @uml.property  name="shuffleCheck"
	 */
	private boolean shuffleCheck;
	/**
	 * @uml.property  name="notificationCheck"
	 */
	private boolean notificationCheck;
	/**
	 * @uml.property  name="position"
	 */
	private int position;
	/**
	 * @uml.property  name="fileOptionCheck"
	 */
	private int fileOptionCheck;
	/**
	 * @uml.property  name="currentPostion"
	 */
	private int currentPostion;
	/**
	 * @uml.property  name="playmodeCheck"
	 */
	private int playmodeCheck = 0;
	/**
	 * @uml.property  name="notifiTemp"
	 */
	private String notifiTemp;

	/* ----------------mp3player Ŭ���� ���� �� ���� ���� ����---------------- */

	/**
	 * @uml.property  name="m"
	 * @uml.associationEnd  
	 */
	private MP3Music m; // mp3music Ŭ������ ����
	static ArrayList<MP3Music> mList; // SDcard�ȿ� ���� ��ü �� ����Ʈ
	static ArrayList<MP3Music> temp; // �����忡 ���� ����Ʈ

	/**
	 * @uml.property  name="songList"
	 * @uml.associationEnd  inverse="this$0:com.MP3Player.MP3PlayerActivity$MusicInformation"
	 */
	private MusicInformation songList; // ���� ����� ����Ʈ��
	/**
	 * @uml.property  name="mPlayer"
	 * @uml.associationEnd  
	 */
	private MediaPlayer mPlayer; // �̵�� �÷��̾�
	/**
	 * @uml.property  name="randomNumber"
	 * @uml.associationEnd  
	 */
	private RandomNumber randomNumber; // ��ü�� ���� ���� ����
	/**
	 * @uml.property  name="jaudiotagger"
	 * @uml.associationEnd  
	 */
	private MP3JAudioTagger jaudiotagger; // jaudio ���� ���̺귯���� ���Ѻ���

	/* ----------------Lyric ���̾ƿ� ��ġ�̺�Ʈ ���� ����---------------- */

	/**
	 * @uml.property  name="gd"
	 * @uml.associationEnd  
	 */
	private GestureDetector gd;
	/**
	 * @uml.property  name="touchY"
	 */
	private float touchY = 0.0f;

	/* ----------------Bitmap ���� ���� ����---------------- */

	private static final BitmapFactory.Options sBitmapOptionsCache = new BitmapFactory.Options();
	private static final Uri sArtworkUri = Uri.parse("content://media/external/audio/albumart");
	/**
	 * @uml.property  name="mContext"
	 * @uml.associationEnd  
	 */
	private Context mContext;

	/* ----------------Eqaulizer ���� ����---------------- */

	/**
	 * @uml.property  name="mEQ"
	 * @uml.associationEnd  
	 */
	private MyPrefence_EQsetup MEQ;
	static int sessionId;

	/* ----------------Notification ���� ����---------------- */

	/**
	 * @uml.property  name="nm"
	 * @uml.associationEnd  
	 */
	private NotificationManager nm;
	/**
	 * @uml.property  name="notification"
	 * @uml.associationEnd  
	 */
	private Notification notification;

	/* ----------------ȯ�漳�� ���� ����---------------- */

	/**
	 * @uml.property  name="pref"
	 * @uml.associationEnd  
	 */
	private SharedPreferences pref;
	/**
	 * @uml.property  name="editor"
	 * @uml.associationEnd  
	 */
	private SharedPreferences.Editor editor;

	/* ----------------���ӵ� ���� ���� ����---------------- */

	/**
	 * @uml.property  name="lastTime"
	 */
	private long lastTime;
	/**
	 * @uml.property  name="speed"
	 */
	private float speed;
	/**
	 * @uml.property  name="lastX"
	 */
	private float lastX;
	/**
	 * @uml.property  name="lastY"
	 */
	private float lastY;
	/**
	 * @uml.property  name="lastZ"
	 */
	private float lastZ;

	/**
	 * @uml.property  name="x"
	 */
	private float x;
	/**
	 * @uml.property  name="y"
	 */
	private float y;
	/**
	 * @uml.property  name="z"
	 */
	private float z;
	private static final int SHAKE_THRESHOLD = 3000;

	private static final int DATA_X = SensorManager.DATA_X;
	private static final int DATA_Y = SensorManager.DATA_Y;
	private static final int DATA_Z = SensorManager.DATA_Z;

	/**
	 * @uml.property  name="sensorManager"
	 * @uml.associationEnd  
	 */
	private SensorManager sensorManager;
	/**
	 * @uml.property  name="accelerormeterSensor"
	 * @uml.associationEnd  
	 */
	private Sensor accelerormeterSensor;

	/* ----------------Activity onCreate()---------------- */

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mp3player);
		updateMusic();
		initViews();
	}

	/* Notification -> Activity �̵��� Notification ���� */

	@Override
	protected void onResume() {
		super.onResume();
		nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		nm.cancel(1234);
	}

	/* ----------------Activity�� pause�� notification ���� ȣ�� ---------------- */

	@Override
	public void onPause() {
		super.onPause();
		notifi();
	}

	/* ----------------Activity�� ���� �� ---------------- */

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	/* ----------------Activity�� start()�� ���ӵ� ���������� ���� ---------------- */

	@Override
	public void onStart() {
		super.onStart();
		if (accelerormeterSensor != null)
			sensorManager.registerListener(this, accelerormeterSensor, SensorManager.SENSOR_DELAY_GAME);
	}

	/* ----------------Activity�� ������ ---------------- */

	@Override
	public void onStop() {
		super.onStop();
	}

	/* Back �� ������ �̺�Ʈ */
	@Override
	public void onBackPressed() {
		AlertDialog.Builder Al_dl = null;
		Al_dl = new AlertDialog.Builder(MP3PlayerActivity.this);
		Al_dl.setTitle("�����Ͻðڽ��ϱ�?");
		Al_dl.setIcon(R.drawable.icon);
		Al_dl.setPositiveButton("Ȯ��", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				close(0);
			}
		});
		Al_dl.setNegativeButton("���", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
			}
		}).show();
	};

	/* resume �ÿ� notification���� */
	private void notifi() {
		if (notificationCheck == false) {
			nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
			Intent intent = new Intent(this, MP3PlayerActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP
					| Intent.FLAG_ACTIVITY_SINGLE_TOP);
			PendingIntent pendingIntent = PendingIntent.getActivity(MP3PlayerActivity.this, 0, intent, 0);

			notification = new Notification(R.drawable.notifi, subject.getText(), System.currentTimeMillis());
			notification.setLatestEventInfo(MP3PlayerActivity.this, subject.getText(), notifiTemp, pendingIntent);

			nm.notify(1234, notification);

		}
	}

	@SuppressWarnings("unchecked")
	private void updateMusic() {
		String title, artist, album, path, albumID;
		Drawable d;
		mContext = this;

		mList = new ArrayList<MP3Music>(); // ����Ʈ �迭 ��ü����
		mPlayer = new MediaPlayer(); // �̵�� �÷��̾� ��ü����
		listview = getListView();

		String[] mCursorCols = new String[] { MediaStore.Audio.Media._ID, MediaStore.Audio.Media.ALBUM_ID,
				MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.ALBUM,
				MediaStore.Audio.Media.DATA };
		String sort = MediaStore.Audio.Media.TITLE + " COLLATE LOCALIZED ASC";

		Cursor cur = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, mCursorCols, null, null,
				sort);

		int artistColumn = cur.getColumnIndex(MediaStore.Audio.Media.ARTIST);
		int albumIDCol = cur.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID);
		int titleColumn = cur.getColumnIndex(MediaStore.Audio.Media.TITLE);
		int albumColumn = cur.getColumnIndex(MediaStore.Audio.Media.ALBUM);
		int pathColumn = cur.getColumnIndex(MediaStore.Audio.Media.DATA);

		if (cur.moveToFirst()) {
			do {
				artist = cur.getString(artistColumn);
				title = cur.getString(titleColumn);
				album = cur.getString(albumColumn);
				path = cur.getString(pathColumn);
				albumID = cur.getString(albumIDCol);
				
				Bitmap albumArt = MP3PlayerActivity.getArtworkQuick(mContext, Integer.parseInt(albumID), 300, 300);
				if (albumArt != null)
					d = (Drawable) new BitmapDrawable(albumArt);
				else
					d = null;

				mList.add(new MP3Music(artist, title, album, path, albumID, d));
			} while (cur.moveToNext());
		}
		if(mList.size()==0){
			Toast.makeText(MP3PlayerActivity.this, "���������� �������� �ʽ��ϴ�.", Toast.LENGTH_LONG).show();
			close(0);
		}
		temp = (ArrayList<MP3Music>) mList.clone();
		songList = new MusicInformation(this, R.layout.mp3_list, temp);
		setListAdapter(songList);
	}

	private void initViews() {
		centerimg = (ImageView) findViewById(R.id.centerimg);
		remaintime = (TextView) findViewById(R.id.remaintime);
		playtime = (TextView) findViewById(R.id.playtime);
		subject = (TextView) findViewById(R.id.subject);
		playbar = (RelativeLayout) findViewById(R.id.playbar);
		playlistbar = (RelativeLayout) findViewById(R.id.playlistbar);
		bottom = (RelativeLayout) findViewById(R.id.bottom);
		title = (RelativeLayout) findViewById(R.id.title);
		lyric = (RelativeLayout) findViewById(R.id.lyric);
		subject = (TextView) findViewById(R.id.subject);
		start = (TextView) findViewById(R.id.start);
		end = (TextView) findViewById(R.id.end);
		lyricText = (TextView) findViewById(R.id.lyricText);
		lyricView = (ScrollView) findViewById(R.id.lyricView);
		mToast = Toast.makeText(this, "null", Toast.LENGTH_SHORT);
		pref = PreferenceManager.getDefaultSharedPreferences(this);
		editor = pref.edit();

		randomNumber = RandomNumber.getInstance(temp.size());
		randomNumber.sort();

		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		accelerormeterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		
		/* API 9�̻��� ��� Eqaulizer ���� */

		if (nSDKVersionCheck() > 9) {
			MEQ = MyPrefence_EQsetup.getInstance();
			if (pref.getBoolean("bassBoost", false) == true)
				MEQ.setupBassBoost(pref.getBoolean("bassBoost", false));
			if (pref.getBoolean("virtualizer", false) == true)
				MEQ.setupmVirtualizer(pref.getBoolean("virtualizer", false));
			if (Integer.valueOf(pref.getString("presetReverbs", "0")) != 0)
				MEQ.setupEqualizer(Integer.valueOf(pref.getString("setting_EQ", "0")));
		}

		/* ������� ���� Seekbar ������ */

		playingBar = (SeekBar) findViewById(R.id.playingBar);
		playingBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				if (fromUser) {
					mPlayer.seekTo(progress);
				}
			}

			public void onStartTrackingTouch(SeekBar seekBar) {
				wasPlaying = mPlayer.isPlaying();
				if (wasPlaying)
					mPlayer.pause();
			}

			public void onStopTrackingTouch(SeekBar seekBar) {
			}
		});

		/* mp3Tab ���� �̵��� */

		mp3Tapmode = (ImageButton) findViewById(R.id.mp3Tapmode);
		mp3Tapmode.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent(MP3PlayerActivity.this, MP3PlayerTab.class));
			}
		});

		/* ������� ���ϸ���Ʈ�� Ŭ���� �̺�Ʈ */

		center = (ViewFlipper) findViewById(R.id.center);
		playlist = (ImageButton) findViewById(R.id.playlist);
		playlist.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
				if (playlistbar.getVisibility() != 0) {
					center.setInAnimation(AnimationUtils.loadAnimation(MP3PlayerActivity.this, R.anim.push_left_in));
					center.setOutAnimation(AnimationUtils.loadAnimation(MP3PlayerActivity.this, R.anim.push_left_out));
					center.showNext();
					bottom.setVisibility(View.GONE);
					playbar.setVisibility(View.GONE);
					playlistbar.setVisibility(View.VISIBLE);
					setSelection(position);
				} else if (playlistbar.getVisibility() == 0) {
					center.setInAnimation(AnimationUtils.loadAnimation(MP3PlayerActivity.this, R.anim.push_right_in));
					center.setOutAnimation(AnimationUtils.loadAnimation(MP3PlayerActivity.this, R.anim.push_right_out));
					center.showPrevious();
					bottom.setVisibility(View.VISIBLE);
					playbar.setVisibility(View.VISIBLE);
					playlistbar.setVisibility(View.GONE);
				}
			}
		});

		/* �������� ���� Ȯ�� �� ������ longŬ�� �̺�Ʈ */

		listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> av, View v, final int pos, final long id) {
				final String items[] = { "��������", "��������" };

				m = temp.get(pos);
				AlertDialog.Builder ab = new AlertDialog.Builder(v.getContext());
				ab.setTitle("�� ���� �޴�");
				ab.setIcon(R.drawable.playlist);
				ab.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						fileOptionCheck = whichButton;
					}
				}).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						Intent intent = new Intent(MP3PlayerActivity.this, MP3FileView.class);
						intent.putExtra("path", m.getPath());
						jaudiotagger.setBackground(m.getBackground());
						if (fileOptionCheck == 0)
							intent.putExtra("fileEdit", false);
						else
							intent.putExtra("fileEdit", true);
						startActivity(intent);
					}
				}).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
					}
				});
				ab.show();
				return false;
			}
		});

		/* Lyrics ���̾ƿ� Ŭ���� */

		center.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				if (lyric.getVisibility() != 0) {
					lyric.setVisibility(View.VISIBLE);
				}
				return false;
			}
		});

		/* lyricview ��ġ ������ */

		GestureListener Listener = new GestureListener();
		gd = new GestureDetector(Listener);
		lyricView.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				float y = event.getY();
				if (touchY != 0.0f && (Math.abs(touchY - y) > 15.0f)) {
					touchY = y;
					return onTouchEvent(event);
				} else {
					touchY = y;
					gd.onTouchEvent(event);
				}
				return true;
			}
		});

		/* �� �÷��� ��� - ���, 1��ݺ�, ��ü�ݺ��� */

		playmode = (ImageButton) findViewById(R.id.playmode);
		playmode.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (playmodeCheck == 0) {
					playmodeCheck = 1;
					playmode.setImageResource(R.drawable.redo);
					mToast.setText("���� �뷡�� �ݺ��մϴ�.");
					mToast.show();
				} else if (playmodeCheck == 1) {
					playmodeCheck = 2;
					playmode.setImageResource(R.drawable.loopback);
					mToast.setText("��� �뷡�� �ݺ��մϴ�.");
					mToast.show();
				} else {
					playmodeCheck = 0;
					playmode.setImageResource(R.drawable.refresh);
					mToast.setText("�ݺ��� ������� �ʽ��ϴ�.");
					mToast.show();
				}
				editor.putInt("playmodeCheck", playmodeCheck);
				editor.commit();
			}
		});

		/* �� ���ø��� */

		shuffle = (ImageButton) findViewById(R.id.shuffle);
		shuffle.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (shuffleCheck == false) {
					shuffleCheck = true;
					shuffle.setImageResource(R.drawable.shuffle_on);
					mToast.setText("���ø��");
					mToast.show();
				} else {
					shuffleCheck = false;
					shuffle.setImageResource(R.drawable.shuffle_off);
					mToast.setText("���ø�� ����");
					mToast.show();
				}
				editor.putBoolean("shuffleCheck", shuffleCheck);
				editor.commit();
			}
		});

		/* �� �������� ����ڰ� �����ߴ� ��� �ҷ����� */

		shuffleCheck = pref.getBoolean("shuffleCheck", false);
		playmodeCheck = pref.getInt("playmodeCheck", 0);

		if (shuffleCheck == true)
			shuffle.setImageResource(R.drawable.shuffle_on);
		else
			shuffle.setImageResource(R.drawable.shuffle_off);
		if (playmodeCheck == 0)
			playmode.setImageResource(R.drawable.refresh);
		else if (playmodeCheck == 1)
			playmode.setImageResource(R.drawable.redo);
		else if (playmodeCheck == 2)
			playmode.setImageResource(R.drawable.loopback);

		/* �����ư Ŭ���� */

		playpause = (ImageButton) findViewById(R.id.playpause);
		playpause.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (mPlayer.isPlaying() == false) // ������� �ƴҶ� (�Ͻ������϶�)
				{
					mPlayer.start();
					if (nSDKVersionCheck() > 9) // API 9�̻��� ��� Eqaulizer ����
					{
						sessionId = mPlayer.getAudioSessionId();
					}
					playpause.setImageResource(R.drawable.pause);
				} else // ������϶�
				{
					mPlayer.pause();
					playpause.setImageResource(R.drawable.play);
				}
			}
		});

		/* �ڷΰ���(rewind) ��ư Ŭ���� */

		rewindtostart = (ImageButton) findViewById(R.id.rewindtostart);
		rewindtostart.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				wasPlaying = mPlayer.isPlaying();
				if (wasPlaying) {
					if (shuffleCheck == true && playmodeCheck == 0) {
						try {
							currentPostion = randomNumber.RegetNumber();
							RepeatMode(true);
						} catch (IndexOutOfBoundsException e) {
							randomNumber.i++;
							currentPostion = 0;
							RepeatMode(true);
						}
					} else if (shuffleCheck == false && playmodeCheck == 0) {
						currentPostion = (currentPostion == 0 ? currentPostion : currentPostion - 1);
						RepeatMode(true);
					} else if (shuffleCheck == true && playmodeCheck == 1) {
						RepeatMode(true);
					} else if (shuffleCheck == false && playmodeCheck == 1) {
						RepeatMode(true);
					} else if (shuffleCheck == true && playmodeCheck == 2) {
						int swap = (int) (Math.random() * (temp.size())) + 1;
						currentPostion = swap - 1;
						RepeatMode(true);
					} else if (shuffleCheck == false && playmodeCheck == 2) {
						currentPostion = (currentPostion == 0 ? temp.size() - 1 : currentPostion - 1);
						RepeatMode(true);
					}
				} else {
					if (shuffleCheck == true && playmodeCheck == 0) {
						try {
							currentPostion = randomNumber.RegetNumber();
							RepeatMode(false);
						} catch (IndexOutOfBoundsException e) {
							randomNumber.i++;
							currentPostion = 0;
							RepeatMode(false);
						}
					} else if (shuffleCheck == false && playmodeCheck == 0) {
						currentPostion = (currentPostion == 0 ? currentPostion : currentPostion - 1);
						RepeatMode(false);
					} else if (shuffleCheck == true && playmodeCheck == 1) {
						RepeatMode(false);
					} else if (shuffleCheck == false && playmodeCheck == 1) {
						RepeatMode(false);
					} else if (shuffleCheck == true && playmodeCheck == 2) {
						int swap = (int) (Math.random() * (temp.size())) + 1;
						currentPostion = swap - 1;
						RepeatMode(false);
					} else if (shuffleCheck == false && playmodeCheck == 2) {
						currentPostion = (currentPostion == 0 ? temp.size() - 1 : currentPostion - 1);
						RepeatMode(false);
					}
				}
			}
		});

		/* �����ΰ���(forward) ��ư Ŭ���� */

		forwardtoend = (ImageButton) findViewById(R.id.forwardtoend);
		forwardtoend.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				wasPlaying = mPlayer.isPlaying();
				if (wasPlaying) {
					if (shuffleCheck == true && playmodeCheck == 0) {
						try {
							currentPostion = randomNumber.getNumber();
							RepeatMode(true);
						} catch (IndexOutOfBoundsException e) {
							randomNumber.i--;
							RepeatMode(true);
						}
					} else if (shuffleCheck == false && playmodeCheck == 0) {
						currentPostion = (currentPostion == temp.size() - 1 ? currentPostion : currentPostion + 1);
						RepeatMode(true);
					} else if (shuffleCheck == true && playmodeCheck == 1) {
						RepeatMode(true);
					} else if (shuffleCheck == false && playmodeCheck == 1) {
						RepeatMode(true);
					} else if (shuffleCheck == true && playmodeCheck == 2) {
						int swap = (int) (Math.random() * (temp.size())) + 1;
						currentPostion = swap - 1;
						RepeatMode(true);
					} else if (shuffleCheck == false && playmodeCheck == 2) {
						currentPostion = (currentPostion == temp.size() - 1 ? 0 : currentPostion + 1);
						RepeatMode(true);
					}
				} else {
					if (shuffleCheck == true && playmodeCheck == 0) {
						try {
							currentPostion = randomNumber.getNumber();
							RepeatMode(false);
						} catch (IndexOutOfBoundsException e) {
							randomNumber.i--;
							RepeatMode(false);
						}
					} else if (shuffleCheck == false && playmodeCheck == 0) {
						currentPostion = (currentPostion == temp.size() - 1 ? currentPostion : currentPostion + 1);
						RepeatMode(false);
					} else if (shuffleCheck == true && playmodeCheck == 1) {
						RepeatMode(false);
					} else if (shuffleCheck == false && playmodeCheck == 1) {
						RepeatMode(false);
					} else if (shuffleCheck == true && playmodeCheck == 2) {
						int swap = (int) (Math.random() * (temp.size())) + 1;
						currentPostion = swap - 1;
						RepeatMode(false);
					} else if (shuffleCheck == false && playmodeCheck == 2) {
						currentPostion = (currentPostion == temp.size() - 1 ? 0 : currentPostion + 1);
						RepeatMode(false);
					}
				}
			}
		});

		/* ������� ���� �Ϸ�� */

		mPlayer.setOnCompletionListener(new OnCompletionListener() {
			public void onCompletion(MediaPlayer mp) {
				if (shuffleCheck == true && playmodeCheck == 0) {
					try {
						currentPostion = randomNumber.getNumber();
						RepeatMode(true);
					} catch (IndexOutOfBoundsException e) {
						randomNumber.i--;
						currentPostion = 0;
						RepeatMode(false);
						playpause.setImageResource(R.drawable.play);
					}
				} else if (shuffleCheck == false && playmodeCheck == 0) {
					currentPostion = (currentPostion == temp.size() - 1 ? 0 : currentPostion + 1);
					if (currentPostion != 0)
						RepeatMode(true);
					else {
						currentPostion = temp.size() - 1;
						RepeatMode(false);
						playpause.setImageResource(R.drawable.play);
					}
				} else if (shuffleCheck == true && playmodeCheck == 1) {
					RepeatMode(true);
				} else if (shuffleCheck == false && playmodeCheck == 1) {
					RepeatMode(true);
				} else if (shuffleCheck == true && playmodeCheck == 2) {
					int swap = (int) (Math.random() * (temp.size())) + 1;
					currentPostion = swap - 1;
					RepeatMode(true);
				} else if (shuffleCheck == false && playmodeCheck == 2) {
					currentPostion = (currentPostion == temp.size() - 1 ? 0 : currentPostion + 1);
					RepeatMode(true);
				}
			}
		}); // ��� �Ϸ�
		mPlayer.setOnSeekCompleteListener(new OnSeekCompleteListener() {
			public void onSeekComplete(MediaPlayer mp) {
				if (wasPlaying) // ������϶� ��ũ�� �̵��Ǹ��̸�
					mPlayer.start(); // �̵��Ŀ��� ���
			}
		}); // ������ġ �̵� �Ϸ�
		mProgressHandler.sendEmptyMessageDelayed(0, 200); // �ڵ鷯(Ÿ�̸� �ֱ�)

		/* �� �������� ����ڰ� �����ߴ� �� ��ġ �ҷ����� */

		currentPostion = pref.getInt("currentPostion", 0);
		if (LoadMedia(currentPostion) == false) {
			currentPostion = 0;
			LoadMedia(0);
		}
	}

	/* mp3Activity Lyric ���̾ƿ� ������ */
	private class GestureListener implements OnGestureListener {
		/* Lyric ���̾ƿ� ��ġ�� */
		public boolean onDown(MotionEvent e) {
			lyric.setVisibility(View.GONE); // ȭ�� �����
			return false;
		}

		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			return false;
		}

		public void onLongPress(MotionEvent e) {
		}

		/* Lyric ��ũ�� �̺�Ʈ �� */
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
			return false;
		}

		public void onShowPress(MotionEvent e) {
		}

		public boolean onSingleTapUp(MotionEvent e) {
			return false;
		}
	}

	/* �� �غ� �� ������� �Ǻ� */

	private void RepeatMode(Boolean repeat) {
		mPlayer.reset();
		LoadMedia(currentPostion);
		if (repeat == true)
			mPlayer.start();
	}

	/* ����غ� */

	private boolean LoadMedia(int idx) {
		try {
			m = temp.get(idx);
			mPlayer.setDataSource(m.getPath());
		} catch (IllegalArgumentException e) {
			return false;
		} catch (IllegalStateException e) {
			return false;
		} catch (IOException e) {
			return false;
		} catch (IndexOutOfBoundsException e) {
			return false;
		}
		if (Prepare() == false) {
			return false;
		}
		position = idx;
		jaudiotagger = com.MP3Player.MP3JAudioTagger.getInstance(m.getPath());
		jaudiotagger.init(); // �ʱ�ȭ
		lyricText.setText(jaudiotagger.getLYRICS()); // �������
		if (lyricText.getText() == "")
			lyricText.setText("���簡 �����ϴ�");

		if (m.getBackground() != null)
			centerimg.setImageDrawable(m.getBackground());
		else
			centerimg.setImageResource(R.drawable.noimage);
		
		notifiTemp = m.getAlbumName();
		subject.setText(m.getGasu() + " - " + m.getJemok()); // ����Ʈ���� ���� ���� �ٲ�
		start.setText(String.valueOf(currentPostion + 1)); // �� ����� ���� ������ġ
		end.setText(String.valueOf(temp.size())); // �� ���
		remaintime.setText(changeToMinutes(mPlayer.getDuration())); // ���� �����ѽð�
		playingBar.setMax(mPlayer.getDuration()); // �̵���� �� ��� ���̸� ��ũ���� max������
		editor.putInt("currentPostion", currentPostion); // ��������� ���� �� ��ġ ����
		editor.commit(); // SharePreference �������
		return true;
	}

	/* MediaPlayer �غ�Ȯ�� */

	private boolean Prepare() {
		try {
			mPlayer.prepare(); // �̵�� �غ�
		} catch (IllegalStateException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	/* �������Ʈ �� Ŭ���� ItemClick �̺�Ʈ */

	protected void onListItemClick(ListView l, View v, int position, long id) {
		if (currentPostion != position) {
			wasPlaying = mPlayer.isPlaying(); // ���� ��������� Ȯ��
			mPlayer.reset(); // �̵�� �÷��̾� ��ü �ʱ�ȭ
			currentPostion = position;
			LoadMedia(position);
			if (wasPlaying)
				mPlayer.start();
		}
		/* �ִϸ��̼� ȿ�� */
		center.setInAnimation(AnimationUtils.loadAnimation(MP3PlayerActivity.this, R.anim.push_right_in));
		center.setOutAnimation(AnimationUtils.loadAnimation(MP3PlayerActivity.this, R.anim.push_right_out));
		center.showPrevious();
		bottom.setVisibility(View.VISIBLE);
		title.setVisibility(View.VISIBLE);
		playbar.setVisibility(View.VISIBLE);
		playlistbar.setVisibility(View.GONE); // ����Ʈ�� ȭ�� ����
	}

	/* ������� ����Ʈ�� �׸��� ArrayAdapter */

	/**
	 * @author  yamaia
	 */
	private class MusicInformation extends ArrayAdapter<MP3Music> {
		private ArrayList<MP3Music> items;
		/**
		 * @uml.property  name="m"
		 * @uml.associationEnd  
		 */
		private MP3Music m;

		private MusicInformation(Context context, int textViewResourceId, ArrayList<MP3Music> items) {
			super(context, textViewResourceId, items);
			this.items = items;
		}

		@Override
		public View getView(int position, View view, ViewGroup parent) {
			View v = view;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.mp3_list, null);
			}

			m = items.get(position);
			if (m != null) {
				ImageView imageview = (ImageView) v.findViewById(R.id.row_album_art);
				TextView tt = (TextView) v.findViewById(R.id.row_artist);
				TextView bt = (TextView) v.findViewById(R.id.row_title);
				if (tt != null) {
					tt.setText(m.getGasu() + " - " + m.getJemok());
				}
				if (bt != null) {
					bt.setText(m.getAlbumName());
				}
				if (m.getIcon() != null) {
					imageview.setImageBitmap(m.getIcon());
				} else{
					imageview.setImageResource(R.drawable.noimageicon);
				}
			}
			return v;
		}
	}
	/* bitmap size�� ���̴� �޼ҵ� */

	private static Bitmap getArtworkQuick(Context context, int album_id, int w, int h) {
		w -= 2;
		h -= 2;
		ContentResolver res = context.getContentResolver();
		Uri uri = ContentUris.withAppendedId(sArtworkUri, album_id);
		if (uri != null) {
			ParcelFileDescriptor fd = null;
			try {
				fd = res.openFileDescriptor(uri, "r");
				int sampleSize = 1;

				sBitmapOptionsCache.inJustDecodeBounds = true;
				BitmapFactory.decodeFileDescriptor(fd.getFileDescriptor(), null, sBitmapOptionsCache);
				int nextWidth = sBitmapOptionsCache.outWidth >> 1;
				int nextHeight = sBitmapOptionsCache.outHeight >> 1;
				while (nextWidth > w && nextHeight > h) {
					sampleSize <<= 1;
					nextWidth >>= 1;
					nextHeight >>= 1;
				}
				sBitmapOptionsCache.inDither = true;
				sBitmapOptionsCache.inPurgeable = true;
				sBitmapOptionsCache.inSampleSize = sampleSize;
				sBitmapOptionsCache.inJustDecodeBounds = false;
				Bitmap b = BitmapFactory.decodeFileDescriptor(fd.getFileDescriptor(), null, sBitmapOptionsCache);

				if (b != null) {
					if (sBitmapOptionsCache.outWidth != w || sBitmapOptionsCache.outHeight != h) {
						Bitmap tmp = Bitmap.createScaledBitmap(b, w, h, true);
						b.recycle();
						b = tmp;
					}
				}

				return b;
			} catch (FileNotFoundException e) {
			} finally {
				try {
					if (fd != null)
						fd.close();
				} catch (IOException e) {
				}
			}
		}
		return null;
	}

	/* �ð� ��ȯ �޼ҵ� */

	private String changeToMinutes(int mseconds) {
		int min = 0;
		int sec = 0;
		String minStr = "";
		String secStr = "";

		min = (int) Math.floor(mseconds / (1000 * 60));
		sec = (int) Math.floor((mseconds - (1000 * 60) * min) / 1000);

		minStr = min < 10 ? "0" + min : "" + min;
		secStr = sec < 10 ? "0" + sec : "" + sec;

		return minStr + ":" + secStr;
	}

	/* ���� �߻��� �޽��� ��� */

	/**
	 * @uml.property  name="mOnError"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	MediaPlayer.OnErrorListener mOnError = new MediaPlayer.OnErrorListener() {
		public boolean onError(MediaPlayer mp, int what, int extra) {
			String err = "OnError occured.what = " + what + " ,extra = " + extra;
			Toast.makeText(MP3PlayerActivity.this, err, Toast.LENGTH_LONG).show();
			return false;
		}
	};

	/* 200ms�� �ѹ��� �����ġ ���� */

	/**
	 * @uml.property  name="mProgressHandler"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	Handler mProgressHandler = new Handler() {
		public void handleMessage(Message msg) {
			if (mPlayer == null)
				return;
			if (mPlayer.isPlaying()) {
				playingBar.setProgress(mPlayer.getCurrentPosition());
				playtime.setText(changeToMinutes(mPlayer.getCurrentPosition()));
			}
			mProgressHandler.sendEmptyMessageDelayed(0, 200); // 0.2�ʾ� ������
		}
	};

	/* ��� ������� üũ */

	private int nSDKVersionCheck() {
		int nSDKVersion = Integer.parseInt(Build.VERSION.SDK);
		return nSDKVersion;
	}

	/* PlayerActivty reLoad�� */

	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		boolean isKill = intent.getBooleanExtra("KILL_ACT", false);
		boolean newMusic = intent.getBooleanExtra("newMusic", false);
		int sleepTime = intent.getIntExtra("KILL_TIME", 0);
		currentPostion = intent.getIntExtra("cp", 0);
		if (isKill)
			close(sleepTime);
		if (newMusic) {
			randomNumber = RandomNumber.getInstance(MP3PlayerActivity.temp.size());
			randomNumber.sort();
			wasPlaying = mPlayer.isPlaying(); // ���� ��������� Ȯ��
			mPlayer.reset(); // �̵�� �÷��̾� ��ü �ʱ�ȭ
			LoadMedia(currentPostion);
			if (wasPlaying)
				mPlayer.start();
			songList.items = temp;
			setListAdapter(songList);
			bottom.setVisibility(View.VISIBLE);
			playbar.setVisibility(View.VISIBLE);
			playlistbar.setVisibility(View.GONE);
		}

	}

	/* ���ø����̼� ���� �޼ҵ� */

	private void close(int sleepTime) {
		Handler mHandler = new Handler();
		mHandler.postDelayed(new Runnable() {
			public void run() {
				finish();
				notificationCheck = true;
				if (nSDKVersionCheck() < 8) // 2.3����
				{
					ActivityManager actMng = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
					actMng.restartPackage(getPackageName());
				} else {
					new Thread(new Runnable() {
						public void run() {
							ActivityManager actMng = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
							String strProcessName = getApplicationInfo().processName;
							while (true) {
								List<RunningAppProcessInfo> list = actMng.getRunningAppProcesses();
								for (RunningAppProcessInfo rap : list) {
									if (rap.processName.equals(strProcessName)) {
										if (rap.importance >= RunningAppProcessInfo.IMPORTANCE_BACKGROUND)
											actMng.restartPackage(getPackageName());
										Thread.yield();
										break;
									}
								}
							}
						}
					}, "Process Killer").start();
				}
			}
		}, sleepTime * 6000); // �ڵ鷯 �ð� �Ŀ� ����
	}

	/* ���ӵ� ���� ��Ȯ�� ��ȭ �޼ҵ� */

	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}

	/* ���ӵ� ���� �� ��ȭ �޼ҵ� */

	public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
			long currentTime = System.currentTimeMillis();
			long gabOfTime = (currentTime - lastTime);
			if (gabOfTime > 100) {
				lastTime = currentTime;
				x = event.values[SensorManager.DATA_X]; // ��� x��
				y = event.values[SensorManager.DATA_Y]; // ��� y��
				z = event.values[SensorManager.DATA_Z]; // ��� z��
				speed = Math.abs(x + y + z - lastX - lastY - lastZ) / gabOfTime * 10000;
				/* Shake ���� ���� ȯ�漳�� ���ӵ����� üũ���� ������϶� �������� �ٸ��� ��� */
				if (speed > SHAKE_THRESHOLD && pref.getBoolean("check_Accelometer", false) && mPlayer.isPlaying()) {
					int swap = (int) (Math.random() * (temp.size())) + 1; // ������
																			// ����
					currentPostion = swap - 1; // ������ postion ����
					RepeatMode(true); // ���
				}
				lastX = event.values[DATA_X];
				lastY = event.values[DATA_Y];
				lastZ = event.values[DATA_Z];
			}
		}
	}
}