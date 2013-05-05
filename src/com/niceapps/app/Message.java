package com.niceapps.app;

import java.io.Serializable;

/**
 * Represents a Serializable class that will be passed around through
 * an Intent
 * 
 * @author Abigail S Hdz, Samuel Heaney
 *
 */
public class Message implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2808898572124387287L;
	private String imageEncoding; // Base 64 string encoding of the image
	private String title;
	private String content;

	public Message(String diskImage, String artist, String content) {
		this.imageEncoding = diskImage;
		this.title = artist;
		this.content = content;
	}

	public String getImageEncoding() {
		return this.imageEncoding;
	}

	public String getTitle() {
		return this.title;
	}

	public String getContent() {
		return this.content;
	}
}
