package com.hawkesa.lyricrecorderws.application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class LyricRecorderFile {

	
	
	private String title;
	private String artist;
	private String album;
	private String uniqueId;
	private String downloadId;
	private String unsynchronisedLyrics;
	private String lyricRecorderSynchronisedLyrics;
	private String md5Hash;
	private String originalFileName;
	
	private String coordinates = null;

	
	
	
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public String getDownloadId() {
		return downloadId;
	}

	public void setDownloadId(String downloadId) {
		this.downloadId = downloadId;
	}

	public String getUnsynchronisedLyrics() {
		return unsynchronisedLyrics;
	}

	public void setUnsynchronisedLyrics(String unsynchronisedLyrics) {
		this.unsynchronisedLyrics = unsynchronisedLyrics;
	}

	public String getLyricRecorderSynchronisedLyrics() {
		return lyricRecorderSynchronisedLyrics;
	}

	public void setLyricRecorderSynchronisedLyrics(String lyricRecorderSynchronisedLyrics) {
		this.lyricRecorderSynchronisedLyrics = lyricRecorderSynchronisedLyrics;
	}

	public String getMd5Hash() {
		return md5Hash;
	}

	public void setMd5Hash(String md5Hash) {
		this.md5Hash = md5Hash;
	}

	public String getOriginalFileName() {
		return originalFileName;
	}

	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}

	public String getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates;
	}

	public String toJSON() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(this);
	}

}
