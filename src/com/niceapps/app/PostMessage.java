package com.niceapps.app;

import org.json.JSONObject;

import android.os.AsyncTask;

public class PostMessage extends AsyncTask<JSONObject, Void, JSONObject> {
	private static final String URL = "http://niceapps.herokuapp.com/messages/";
	
	protected JSONObject doInBackground(JSONObject... jsonObjSend) {
		try {
			return HttpClient.SendHttpPost(URL, jsonObjSend[0]);
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

}
