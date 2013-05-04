package com.niceapps.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
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

public class Message_detail extends Activity {
	
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
		/*if (session != null && session.isOpened()) {
			// Get the user's data
			makeMeRequest(session);
		}*/

		// Accion a realizar en caso de que se oprima el boton Im interested
		((Button) findViewById(R.id.button2))
				.setOnClickListener(new OnClickListener() {
					public void onClick(View view) {
						back_to_msgs(); // Manda llamar al m�todo send_offer
					}
				});
		
		((ImageButton)findViewById(R.id.home))
		.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				go_home(); // Manda llamar al m�todo send_offer
			}
		});
	}

	private void back_to_msgs() {
		// Create Intent and add username as as extra
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
