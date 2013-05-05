package com.niceapps.app;

import java.io.Serializable;

/**
 * Represents a Serializable class that will be passed around through
 * an Android Intent
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
	private String status;
	private String image_encoding; // Base 64 string encoding of the image
	
	/**
	 * Constructor for the Disk class that will set up all the class members on
	 * initialization
	 * 
	 * @param id current disk index id
	 * @param title 
	 * @param artist
	 * @param image_encoding base 64 string representation of an image for the disk
	 * @param status indicates if disk is available or not
	 */
	public Disk(int id, String title, String artist, String image_encoding, String status){
		this.title = title;
		this.artist = artist;
		this.id = id;
		this.status = status;
		this.image_encoding = image_encoding;
	}
	
	/**
	 * 
	 * @return current disk id
	 */
	public int getId() {
		return id;
	}

	/**
	 * 
	 * @return the disk title
	 */
	public String getTitle(){
		return title;
	}
	
	/**
	 * 
	 * @return the disk artist
	 */
	public String getArtist(){
		return artist;
	}
	
	/**
	 * 
	 * @return the status of the disk representing it's current availability
	 */
	public String getStatus(){
		return status;
	}
	
	/**
	 * 
	 * @return a string representing the image encoding of the disk
	 */
	public String getImageEncoding(){
		return image_encoding;
	}
}
