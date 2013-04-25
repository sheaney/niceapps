package com.niceapps.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphUser;
import com.facebook.widget.ProfilePictureView;

public class UploadItem extends Activity {
	private ProfilePictureView profilePictureView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.upload_item);

		// Find the user's profile picture custom view
		profilePictureView = (ProfilePictureView) findViewById(R.id.profile_pic);
		profilePictureView.setCropped(false);
		Session session = Session.getActiveSession();
		if (session != null && session.isOpened()) {
	        // Get the user's data
	        makeMeRequest(session);
	    }

		((Button) findViewById(R.id.upload))
				.setOnClickListener(new OnClickListener() {
					public void onClick(View view) {
						upload(view);
					}
				});

	}

	/** Called when the user clicks the 'Upload' button */
	public void upload(View view) {
		// Do something in response to button
		Intent intent = new Intent(this, YourItems.class);
		startActivity(intent);
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