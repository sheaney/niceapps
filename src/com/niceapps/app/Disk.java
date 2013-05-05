package com.niceapps.app;

import java.io.Serializable;

/**
 * Represents a Serializable class that will be passed around through
 * an Intent
 * 
 * @author Abigail S Hdz, Samuel Heaney
 *
 */
public class Disk implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6654019162381403220L;
	private int id;
	private String title;
	private String artist;
	private String conditions;
	private String interest;
	private String status;
	private String image_encoding; // Base 64 string encoding of the image
	
	
	public Disk(int id, String title, String artist, String image_encoding, String status){
		this.title = title;
		this.artist = artist;
		this.id = id;
		this.status = status;
		this.image_encoding = image_encoding;
	}
	
	public int getId() {
		return id;
	}

	public String getPic_path() {
		return image_encoding;
	}

	public String getTitle(){
		return title;
	}
	
	public String getArtist(){
		return artist;
	}
	
	public String getConditions(){
		return conditions;
	}
	
	public String getInterest(){
		return interest;
	}
	
	public String getStatus(){
		return status;
	}
	
	public String getImageEncoding(){
		return image_encoding;
	}
}
