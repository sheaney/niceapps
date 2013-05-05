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
 * 
 * @author Abigail S Hdz, Samuel Heaney
 *
 */
public class Item_details extends Activity {
	
	private String fbusername;
	private TextView title, artist, status;
	private ImageView diskImage;
	private Disk disk;
	private ProfilePictureView profilePictureView;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.item_details);
		
		Intent intent = getIntent();
		disk = (Disk) intent.getSerializableExtra("disk");
		
		if(intent.hasExtra("fbusername")){
			fbusername = intent.getStringExtra("fbusername");
		}
		else {
			fbusername = "Not available";
		}
		
		title = (TextView) findViewById(R.id.textView1);
		artist = (TextView) findViewById(R.id.artist);
		status = (TextView) findViewById(R.id.status);
		diskImage = (ImageView) findViewById(R.id.disk_image);
		

		title.setText(disk.getTitle());
		artist.setText(disk.getArtist());
		status.setText(disk.getStatus());
		
		// Set up image in view
		byte[] imageAsBytes = Base64.decode(disk.getImageEncoding(), Base64.DEFAULT);
		diskImage.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));

		// Find the user's profile picture custom view
		profilePictureView = (ProfilePictureView) findViewById(R.id.item_details_profile_pic);
		profilePictureView.setCropped(false);
		Session session = Session.getActiveSession();
		if (session != null && session.isOpened()) {
			// Get the user's data
			makeMeRequest(session);
		}

		// Set up action that will be triggered when user presses the Send button
		((Button) findViewById(R.id.button2))
				.setOnClickListener(new OnClickListener() {
					public void onClick(View view) {
						send_message();
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
	 * Will make a POST request that will insert the message data into the server's DB 
	 */
	private void send_message() {
		Intent intent = new Intent(getBaseContext(), Offer_for_item.class);
		intent.putExtra("disk", disk);
		intent.putExtra("fbusername", fbusername);
		startActivity(intent);
	}
	
	/**
	 * Will close the current activity and take the user to the initial Main Activity
	 */
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
