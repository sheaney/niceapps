package com.niceapps.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphUser;
import com.facebook.widget.ProfilePictureView;

public class Item_details extends Activity {
	
	private static String DISK_URL = "http://niceapps.herokuapp.com/disks/";
	private String fbusername;
	private TextView title, artist, status;
	private Disk disk;
	private ProfilePictureView profilePictureView;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.item_details);
		
		Intent intent = getIntent();
		disk = (Disk) intent.getSerializableExtra("disk");
		DISK_URL += disk.getId() + ".json";
		
		if(intent.hasExtra("fbusername")){
			fbusername = intent.getStringExtra("fbusername");
		}
		else {
			fbusername = "Not available";
		}
		
		title = (TextView) findViewById(R.id.textView1);
		artist = (TextView) findViewById(R.id.artist);
		status = (TextView) findViewById(R.id.status);

		title.setText(disk.getTitle());
		artist.setText(disk.getArtist());
		status.setText(disk.getStatus());

		// Find the user's profile picture custom view
		profilePictureView = (ProfilePictureView) findViewById(R.id.item_details_profile_pic);
		profilePictureView.setCropped(false);
		Session session = Session.getActiveSession();
		if (session != null && session.isOpened()) {
			// Get the user's data
			makeMeRequest(session);
		}

		// Accion a realizar en caso de que se oprima el boton Im interested
		((Button) findViewById(R.id.button2))
				.setOnClickListener(new OnClickListener() {
					public void onClick(View view) {
						send_message(); // Manda llamar al m�todo send_offer
					}
				});
		
		((ImageButton)findViewById(R.id.home))
		.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				go_home(); // Manda llamar al m�todo send_offer
			}
		});
	}

	private void send_message() {
		Intent intent = new Intent(getBaseContext(), Offer_for_item.class);
		intent.putExtra("disk", disk);
		intent.putExtra("fbusername", fbusername);
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
