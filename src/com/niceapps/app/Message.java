package com.niceapps.app;

import java.io.Serializable;

public class Message implements Serializable {
	String imageEncoding;
	String title;
	String content;

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
