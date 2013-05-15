package com.niceapps.app;

import org.json.JSONObject;

import android.os.AsyncTask;

public class PostUser extends AsyncTask<JSONObject, Void, JSONObject> {
	private static final String USERS_URL = "http://niceapps.herokuapp.com/users/";
	
	protected JSONObject doInBackground(JSONObject... jsonObjSend) {
		try {
			return HttpClient.SendHttpPost(USERS_URL, jsonObjSend[0]);
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

}
