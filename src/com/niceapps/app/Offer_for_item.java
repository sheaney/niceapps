package com.niceapps.app;

import org.json.JSONException;
import org.json.JSONObject;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphUser;
import com.facebook.widget.ProfilePictureView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Offer_for_item extends Activity {
	
	private static final String URL = "http://niceapps.herokuapp.com/messages/";
	private static final String TAG = "Msg_to_admin";
	
	private TextView title;
	private EditText message;
	private Disk disk;

	private ProfilePictureView profilePictureView;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.offer_for_item);

		disk = (Disk) getIntent().getSerializableExtra("disk");

		title = (TextView) findViewById(R.id.textView1);
		message = (EditText) findViewById(R.id.editText1);

		title.setText(disk.getTitle());

		// Find the user's profile picture custom view
		profilePictureView = (ProfilePictureView) findViewById(R.id.offer_profile_pic);
		profilePictureView.setCropped(false);
		Session session = Session.getActiveSession();
		if (session != null && session.isOpened()) {
			// Get the user's data
			makeMeRequest(session);
			
		}
		
		
		
		// Accion a realizar en caso de que se oprima el boton Send Offer
		((Button) findViewById(R.id.button1))
				.setOnClickListener(new OnClickListener() {
					public void onClick(View view) {
						send_msg(); // Manda llamar al m�todo send_offer
					}
				});
	}

	private void send_msg() {
		// JSON object to hold the information, which is sent to the server
		JSONObject jsonObjSend = new JSONObject();

		try {
			// Add key/value pairs
			jsonObjSend.put("content", message.getText().toString());
			jsonObjSend.put("disk_id", disk.getId());
			jsonObjSend.put("user_id", 1); //Hardcodeado

			// Output the JSON object we're sending to Logcat:
			Log.i(TAG, jsonObjSend.toString(2));

		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		// Send the HttpPostRequest and receive a JSONObject in return
		JSONObject jsonObjRecv = HttpClient.SendHttpPost(URL, jsonObjSend);
		
		// Se tiene que crear nueva oferta aqui
		
		String msg = "Message for " + disk.getTitle() + " sent to Musicbox..";
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
	}
	
	private void makeMeRequest(final Session session) {
		// Make an API call to get user data and define a
		// new callback to handle the response.
		Request request = Request.newMeRequest(session,
				new Request.GraphUserCallback() {
					@Override
					public void onCompleted(GraphUser user, Response response) {
						// If the response is successful
						if (session == Session.getActiveSession()) {
							if (user != null) {
								// Set the id for the ProfilePictureView
								// view that in turn displays the profile
								// picture.
								profilePictureView.setProfileId(user.getId());
							}
						}
						if (response.getError() != null) {
							// Handle errors, will do so later.
						}
					}
				});
		request.executeAsync();
	}

}
