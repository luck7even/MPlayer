package com.MP3Player;

import java.io.File;
import java.io.IOException;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldDataInvalidException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.KeyNotFoundException;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import android.graphics.drawable.Drawable;

public class MP3JAudioTagger {
	/**
	 * @uml.property name="audioFile"
	 * @uml.associationEnd
	 */
	private AudioFile audioFile;
	/**
	 * @uml.property name="tag"
	 * @uml.associationEnd
	 */
	private Tag tag;
	/**
	 * @uml.property name="path"
	 */
	private String path;

	/**
	 * @uml.property name="aLBUM"
	 */
	private String ALBUM = null;
	/**
	 * @uml.property name="aRTIST"
	 */
	private String ARTIST = null;
	/**
	 * @uml.property name="cOMMENT"
	 */
	private String COMMENT = null;
	/**
	 * @uml.property name="eNCODER"
	 */
	private String ENCODER = null;
	/**
	 * @uml.property name="gENRE"
	 */
	private String GENRE = null;
	/**
	 * @uml.property name="lYRICS"
	 */
	private String LYRICS = null;
	/**
	 * @uml.property name="tITLE"
	 */
	private String TITLE = null;
	/**
	 * @uml.property name="yEAR"
	 */
	private String YEAR = null;
	/**
	 * @uml.property name="background"
	 * @uml.associationEnd
	 */
	private Drawable Background;

	static MP3JAudioTagger instance;

	private MP3JAudioTagger(String path) {
		this.path = path;
	}

	public static MP3JAudioTagger getInstance(String path) {
		if (instance == null) {
			instance = new MP3JAudioTagger(path);
		} else {
			instance.path = path;
		}
		return instance;
	}

	public void init() {
		try {
			File file = new File(path);
			audioFile = AudioFileIO.read(file);
			tag = audioFile.getTag();

			ALBUM = tag.getFirst(FieldKey.ALBUM).toString();
			ARTIST = tag.getFirst(FieldKey.ARTIST).toString();
			COMMENT = tag.getFirst(FieldKey.COMMENT).toString();
			ENCODER = tag.getFirst(FieldKey.ENCODER).toString();
			GENRE = tag.getFirst(FieldKey.GENRE).toString();
			LYRICS = tag.getFirst(FieldKey.LYRICS).toString();
			TITLE = tag.getFirst(FieldKey.TITLE).toString();
			YEAR = tag.getFirst(FieldKey.YEAR).toString();

		} catch (CannotReadException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TagException e) {
			e.printStackTrace();
		} catch (ReadOnlyFileException e) {
			e.printStackTrace();
		} catch (InvalidAudioFrameException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	public void tagModify() {
		try {
			tag.setField(FieldKey.ALBUM, this.getALBUM());
			tag.setField(FieldKey.ARTIST, this.getARTIST());
			tag.setField(FieldKey.COMMENT, this.getCOMMENT());
			tag.setField(FieldKey.ENCODER, this.getENCODER());
			tag.setField(FieldKey.GENRE, this.getGENRE());
			tag.setField(FieldKey.TITLE, this.getTITLE());
			tag.setField(FieldKey.YEAR, this.getYEAR());
			tag.setField(FieldKey.LYRICS, this.getLYRICS());
			AudioFileIO.write(audioFile);
		} catch (KeyNotFoundException e) {
			e.printStackTrace();
		} catch (FieldDataInvalidException e) {
			e.printStackTrace();
		} catch (CannotWriteException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return
	 * @uml.property name="aLBUM"
	 */
	public String getALBUM() {
		return ALBUM;
	}

	/**
	 * @param aLBUM
	 * @uml.property name="aLBUM"
	 */
	public void setALBUM(String aLBUM) {
		ALBUM = aLBUM;
	}

	/**
	 * @return
	 * @uml.property name="aRTIST"
	 */
	public String getARTIST() {
		return ARTIST;
	}

	/**
	 * @param aRTIST
	 * @uml.property name="aRTIST"
	 */
	public void setARTIST(String aRTIST) {
		ARTIST = aRTIST;
	}

	/**
	 * @return
	 * @uml.property name="cOMMENT"
	 */
	public String getCOMMENT() {
		return COMMENT;
	}

	/**
	 * @param cOMMENT
	 * @uml.property name="cOMMENT"
	 */
	public void setCOMMENT(String cOMMENT) {
		COMMENT = cOMMENT;
	}

	/**
	 * @return
	 * @uml.property name="eNCODER"
	 */
	public String getENCODER() {
		return ENCODER;
	}

	/**
	 * @param eNCODER
	 * @uml.property name="eNCODER"
	 */
	public void setENCODER(String eNCODER) {
		ENCODER = eNCODER;
	}

	/**
	 * @return
	 * @uml.property name="gENRE"
	 */
	public String getGENRE() {
		return GENRE;
	}

	/**
	 * @param gENRE
	 * @uml.property name="gENRE"
	 */
	public void setGENRE(String gENRE) {
		GENRE = gENRE;
	}

	/**
	 * @return
	 * @uml.property name="lYRICS"
	 */
	public String getLYRICS() {
		return LYRICS;
	}

	/**
	 * @param lYRICS
	 * @uml.property name="lYRICS"
	 */
	public void setLYRICS(String lYRICS) {
		LYRICS = lYRICS;
	}

	/**
	 * @return
	 * @uml.property name="tITLE"
	 */
	public String getTITLE() {
		return TITLE;
	}

	/**
	 * @param tITLE
	 * @uml.property name="tITLE"
	 */
	public void setTITLE(String tITLE) {
		TITLE = tITLE;
	}

	/**
	 * @return
	 * @uml.property name="yEAR"
	 */
	public String getYEAR() {
		return YEAR;
	}

	/**
	 * @param yEAR
	 * @uml.property name="yEAR"
	 */
	public void setYEAR(String yEAR) {
		YEAR = yEAR;
	}

	/**
	 * @return
	 * @uml.property name="background"
	 */
	public Drawable getBackground() {
		return Background;
	}

	/**
	 * @param background
	 * @uml.property name="background"
	 */
	public void setBackground(Drawable background) {
		Background = background;
	}
}