package com.niceapps.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphUser;
import com.facebook.widget.ProfilePictureView;

/**
 * Activity that will represent the details of a message
 * 
 * @author Abigail S Hdz, Samuel Heaney
 *
 */
public class MessageDetail extends Activity {
	
	private String fbusername;
	private TextView title, msg_content;
	private ImageView diskImage;
	private Message message;
	private ProfilePictureView profilePictureView;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.message_detail);
		
		Intent intent = getIntent();
		message = (Message) intent.getSerializableExtra("message");
		
		if(intent.hasExtra("fbusername")){
			fbusername = intent.getStringExtra("fbusername");
		}
		else {
			fbusername = "Not available";
		}
		
		title = (TextView) findViewById(R.id.textView1);
		msg_content = (TextView) findViewById(R.id.message);
		diskImage = (ImageView) findViewById(R.id.disk_image);
		

		title.setText(message.getTitle());
		msg_content.setText(message.getContent());
		
		//Set up image in view
		byte[] imageAsBytes = Base64.decode(message.getImageEncoding(), Base64.DEFAULT);
		diskImage.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));

		// Find the user's profile picture custom view
		profilePictureView = (ProfilePictureView) findViewById(R.id.message_detail_profile_pic);
		profilePictureView.setCropped(false);
		Session session = Session.getActiveSession();
		if (session != null && session.isOpened()) {
			// Get the user's data
			makeMeRequest(session);
		}

		// Set up action that will be triggered when user presses the I'm interested button
		((Button) findViewById(R.id.button2))
				.setOnClickListener(new OnClickListener() {
					public void onClick(View view) {
						back_to_msgs();
					}
				});
		
		// Set up action that will be triggered when user presses the Home button
		((ImageButton)findViewById(R.id.home))
		.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				go_home();
			}
		});
	}

	/**
	 * Create an Intent that adds a username string
	 */
	private void back_to_msgs() {
		Intent intent = new Intent(this, YourMessages.class);
		intent.putExtra("username", fbusername);
		startActivity(intent);
	}
	
	private void go_home() {
		finish();
	}
	
	@Override
	public void onStop() {
		super.onStop();
		finish();
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
