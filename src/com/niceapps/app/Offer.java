package com.niceapps.app;

import java.io.Serializable;
import java.util.Date;

public class Offer implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9065772718830425182L;
	String status;
	String message;
	String reply_msg;
	int receiver;
	int sender;
	int disk_id;
	int id;
	Date date;
	
	public Offer(int id, String status, String message, int receiver, int sender, int disk_id){
		this.id = id;
		this.status = status;
		this.message = message;
		this.receiver = receiver;
		this.sender = sender;
		this.disk_id = disk_id;
	}

	public String getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

	public int getReceiver() {
		return receiver;
	}

	public int getSender() {
		return sender;
	}

	public int getDisk_id() {
		return disk_id;
	}

	public Date getDate() {
		return date;
	}
	
}
