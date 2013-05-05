package com.niceapps.app;

import java.io.Serializable;

/**
 * Represents a Serializable class that will be passed around through
 * an Android Intent
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

	/**
	 * Constructor for the Message class that will set up all the class members on
	 * initialization
	 * 
	 * @param imageEncoding represents the string encoding of the disk image associated with the message
	 * @param artist represents the artist of the disk associated with the message
	 * @param content represents the text of the message
	 */
	public Message(String imageEncoding, String artist, String content) {
		this.imageEncoding = imageEncoding;
		this.title = artist;
		this.content = content;
	}

	/**
	 * 
	 * @return the disk image string encoding
	 */
	public String getImageEncoding() {
		return this.imageEncoding;
	}

	/**
	 * 
	 * @return the disk title
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * 
	 * @return message string content
	 */
	public String getContent() {
		return this.content;
	}
}
