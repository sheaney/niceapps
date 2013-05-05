package com.niceapps.app;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphUser;
import com.facebook.widget.ProfilePictureView;

/**
 * 
 * @author Abigail S Hdz, Samuel Heaney
 *
 */
public class Offer_for_item extends Activity {
	
	private static final String URL = "http://niceapps.herokuapp.com/messages/";
	private static final String TAG = "Msg_to_admin";
	
	private String fbusername;
	private TextView title;
	private ImageView diskImage;
	private EditText message;
	private Disk disk;

	private ProfilePictureView profilePictureView;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.offer_for_item);

		disk = (Disk) getIntent().getSerializableExtra("disk");
		
		if(getIntent().hasExtra("fbusername")){
			fbusername = getIntent().getStringExtra("fbusername");
		}
		else {
			fbusername = "Not available";
		}

		title = (TextView) findViewById(R.id.textView1);
		message = (EditText) findViewById(R.id.editText1);
		diskImage = (ImageView) findViewById(R.id.offer_disk_image);

		title.setText(disk.getTitle());

		// Find the user's profile picture custom view
		profilePictureView = (ProfilePictureView) findViewById(R.id.offer_profile_pic);
		profilePictureView.setCropped(false);
		Session session = Session.getActiveSession();
		if (session != null && session.isOpened()) {
			// Get the user's data
			makeMeRequest(session);
			
		}
		
		// Set up image in view
		byte[] imageAsBytes = Base64.decode(disk.getImageEncoding(), Base64.DEFAULT);
		diskImage.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
		
		
		// Set up action that will be triggered when user presses the Send button
		((Button) findViewById(R.id.button1))
				.setOnClickListener(new OnClickListener() {
					public void onClick(View view) {
						send_msg();
					}
				});
		
		// Set up action that will be triggered when user pressed the Home button
		((ImageButton) findViewById(R.id.home))
		.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				go_home();
			}
		});
	}
	
	/**
	 * Will close the current activity and take the user to the initial Main Activity
	 */
	private void go_home() {
		finish();
	}
	

	/**
	 * Will send a POST request to the web server with the message data
	 * that the user inserted in the layout
	 */
	private void send_msg() {
		// JSON object to hold the information, which is sent to the server
		JSONObject jsonObjSend = new JSONObject();

		try {
			// Add key/value pairs
			jsonObjSend.put("content", message.getText().toString());
			jsonObjSend.put("disk_id", disk.getId());
			jsonObjSend.put("username", fbusername);

			// Output the JSON object we're sending to Logcat:
			Log.i(TAG, jsonObjSend.toString(2));

		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		// Send the HttpPostRequest and receive a JSONObject in return
		HttpClient.SendHttpPost(URL, jsonObjSend);
		
		//if(jsonObjRecv.get(name))
		String msg = "Message for " + disk.getTitle() + " sent to Musicbox..";
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
		message.setText("");
	}
	
	/**
	 * Will make a request to the facebook API and return with the
	 * some information about the user. It will then set some layout
	 * text fields with the user image and username. It will also
	 * make a request to the server for saving the username into 
	 * the server's database.
	 * 
	 * @param session is the current state or session for the user 
	 */
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
