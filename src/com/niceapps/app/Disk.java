package com.niceapps.app;

import java.io.Serializable;

public class Disk implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6654019162381403220L;
	int id;
	int user_id;
	String title;
	String artist;
	String conditions;
	String interest;
	String status;
	String image_encoding;
	
	
	public Disk(int id, String title, String artist, String image_encoding, String conditions, String interest){
		this.title = title;
		this.artist = artist;
		this.id = id;
		//this.user_id = user_id;
		this.conditions = conditions;
		this.interest = interest;
		//this.status = status;
		this.image_encoding = image_encoding;
	}
	
	public int getId() {
		return id;
	}

	public int getUser_id() {
		return user_id;
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
