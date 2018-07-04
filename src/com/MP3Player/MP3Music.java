package com.MP3Player;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

class MP3Music {
	/**
	 * @uml.property  name="gasu"
	 */
	private String gasu;
	/**
	 * @uml.property  name="jemok"
	 */
	private String jemok;
	/**
	 * @uml.property  name="albumName"
	 */
	private String albumName;
	/**
	 * @uml.property  name="path"
	 */
	private String path;
	/**
	 * @uml.property  name="albumID"
	 */
	private String albumID;
	/**
	 * @uml.property  name="background"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private Drawable background;
	/**
	 * @uml.property  name="icon"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private Bitmap icon = null;

	public MP3Music(String _gasu, String _jemok, String _albumName, String _path, String _albumID, Drawable _background) {
		this.gasu = _gasu;
		this.jemok = _jemok;
		this.albumName = _albumName;
		this.path = _path;
		this.albumID = _albumID;
		this.background = _background;
		if(_background != null)
			BitMapToDrawable(50, 50);
	}

	/**
	 * @return
	 * @uml.property  name="albumID"
	 */
	public String getAlbumID() {
		return albumID;
	}

	/**
	 * @return
	 * @uml.property  name="path"
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @return
	 * @uml.property  name="gasu"
	 */
	public String getGasu() {
		return gasu;
	}

	/**
	 * @return
	 * @uml.property  name="jemok"
	 */
	public String getJemok() {
		return jemok;
	}

	/**
	 * @return
	 * @uml.property  name="albumName"
	 */
	public String getAlbumName() {
		return albumName;
	}

	/**
	 * @return
	 * @uml.property  name="background"
	 */
	public Drawable getBackground() {
		return background;
	}

	public void BitMapToDrawable(int w, int h) {
		icon = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(icon);
		background.setBounds(0, 0, w, h);
		background.draw(canvas);
	}

	/**
	 * @return
	 * @uml.property  name="icon"
	 */
	public Bitmap getIcon() {
		return icon;
	}
}