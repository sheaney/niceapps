package com.niceapps.app;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.ProfilePictureView;
import com.savagelook.android.UrlJsonAsyncTask;

/**
 * 
 * @author Abigail S Hdz, Samuel Heaney
 *
 */
public class SelectionFragment extends Fragment implements OnItemClickListener {
	View view;
	ListView disksListView;
	
	// URLs used for making requests to the web app
	private static final String DISKS_URL = "http://niceapps.herokuapp.com/disks.json";
	private static final String USERS_URL = "http://niceapps.herokuapp.com/users/";
	
	private ProfilePictureView profilePictureView;
	private TextView userNameView;
	
	private String fbusername;

	private static final int REAUTH_ACTIVITY_CODE = 100;

	private UiLifecycleHelper uiHelper;
	private Session.StatusCallback callback = new Session.StatusCallback() {
		@Override
		public void call(final Session session, final SessionState state,
				final Exception exception) {
			onSessionStateChange(session, state, exception);
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		uiHelper = new UiLifecycleHelper(getActivity(), callback);
		uiHelper.onCreate(savedInstanceState);
	}

	@Override
	public void onResume() {
		super.onResume();
		uiHelper.onResume();
	}

	@Override
	public void onSaveInstanceState(Bundle bundle) {
		super.onSaveInstanceState(bundle);
		uiHelper.onSaveInstanceState(bundle);
	}

	@Override
	public void onPause() {
		super.onPause();
		uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		uiHelper.onDestroy();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		view = inflater.inflate(R.layout.selection, container, false);

		// Find the user's profile picture custom view
		profilePictureView = (ProfilePictureView) view
				.findViewById(R.id.selection_profile_pic);
		profilePictureView.setCropped(false);

		// Find the user's name view
		userNameView = (TextView) view.findViewById(R.id.selection_user_name);

		// Check for an open session
	    Session session = Session.getActiveSession();
	    if (session != null && session.isOpened()) {
	        // Get the user's data
	        makeMeRequest(session);
	    }
	    loadDisksFromAPI(DISKS_URL, view);
	    disksListView = (ListView) view.findViewById (R.id.my_items);
    	disksListView.setOnItemClickListener(this);
    	
    	// Add a button listener to the 'Inbox' button in the selection layout
    	((Button) view.findViewById(R.id.inbox)).setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				view_messages(view);
			}
		});

    	// Add a button listener to the 'More...' button in the selection layout
        ((Button) view.findViewById(R.id.more_items)).setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				more_items(view);
			}
		});
        
		return view;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REAUTH_ACTIVITY_CODE) {
			uiHelper.onActivityResult(requestCode, resultCode, data);
		}
	}

	/**
	 * Verifies that the user is logged in, if not it will
	 * call makeMeRequest() which will log the user in.
	 * 
	 * @param session is used to authenticate the user
	 * @param state of the session
	 * @param exception that might be thrown
	 */
	private void onSessionStateChange(final Session session,
			SessionState state, Exception exception) {
		if (session != null && session.isOpened()) {
			// Get the user's data.
			makeMeRequest(session);
		}
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
								// Set the Textview's text to the user's name.
								userNameView.setText(user.getName());
								
								// Set up user variables
								fbusername = user.getName();

								// Save user into DB
								saveUserToDB();
							}
						}
						if (response.getError() != null) {
							// Handle errors, will do so later.
						}
					}
				});
		request.executeAsync(); // Run request in the background
	}

	/*
	 * (non-Javadoc)
	 * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View, int, long)
	 */
	public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
		Disk selected_disk = (Disk) parent.getItemAtPosition(pos);
		Intent intent = new Intent(this.getActivity(), Item_details.class);
		intent.putExtra("disk", selected_disk);
		intent.putExtra("fbusername", normalizeString(fbusername));
		startActivity(intent);
	}

	/**
	 * Called when the user clicks the 'More' button
	 *  
	 * @param view represents the View from where the method was called
	 */
	public void more_items(View view) {
		// Do something in response to button
		Intent intent = new Intent(this.getActivity(), YourItems.class);
		intent.putExtra("fbusername", normalizeString(fbusername));
		startActivity(intent);
	}	
	
	/**
	 * Called when the user clicks 'See Messages' button
	 * 
	 * @param view represents the View from where the method was called
	 */
	public void view_messages(View view) {
		// Create Intent and add username as as extra
		Intent intent = new Intent(this.getActivity(), YourMessages.class);
		intent.putExtra("username", normalizeString(fbusername));
		startActivity(intent);

	}

	/**
	 * 
	 * Will instantiate a GetDisksTask which will use an AsycTask to fetch
	 * all the disks stored in a server side DB. Side effects include setting up 
	 * a ListView with the proper disk information and a "Loading Disks..." message. 
	 * 
	 * @param url is the address where the GET request will be sent to
	 * @param view represents the ListView that will be populated
	 */
	private void loadDisksFromAPI(String url, View view) {
		GetDisksTask getDisksTask = new GetDisksTask(view.getContext());
		getDisksTask.setMessageLoading("Loading Disks...");
		getDisksTask.execute(url);
	}
	
	/**
	 * This method will save the user into the server's DB.
	 * The server will validate that the user is unique
	 */
	private void saveUserToDB() {
		// JSON object to hold the information, which is sent to the server
		JSONObject jsonObjSend = new JSONObject();
		
		try {
			// Add key/value pairs
			jsonObjSend.put("username", normalizeString(fbusername));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		// Send the HttpPostRequest and receive a JSONObject in return
		HttpClient.SendHttpPost(USERS_URL, jsonObjSend);		

	}
	
	/**
	 * Will return a new String in the format 'xxx-yyy'
	 * e.g. normalizeString("Foo Bar.") => "foo-bar"
	 * 
	 * @param str is the String that will be normalized
	 * @return a new immutable String replacing spaces with '-'
	 */
	private String normalizeString(String str) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c == ' ')
				sb.append('-');
			else if (c != '.')
				sb.append(Character.toLowerCase(c));
		}
		
		return sb.toString();
	}

	/**
	 * Represents a task that will execute asynchronously and fetch a JSONObject
	 * representing Disks
	 * 
	 * @author Abigail S Hdz, Samuel Heaney
	 *
	 */
	private class GetDisksTask extends UrlJsonAsyncTask {
		public GetDisksTask(Context context) {
			super(context);
		}

		@Override
		protected void onPostExecute(JSONObject json) {
			try {
				JSONArray jsonDisks = json.getJSONObject("data").getJSONArray(
						"disks");
				int length = 3; // 3 disks max
				ArrayList<Disk> disks = new ArrayList<Disk>(length);

				for (int i = 0; i < jsonDisks.length() && i < length; i++) {
					disks.add(new Disk(
							(jsonDisks.getJSONObject(i).getInt("id")),
							jsonDisks.getJSONObject(i).getString("title"),
							jsonDisks.getJSONObject(i).getString("artist"),
							jsonDisks.getJSONObject(i).getString("image_encoding"),
							jsonDisks.getJSONObject(i).getString("status")));
				}

				if (disksListView != null) {
					CustomAdapter<Disk> customAdapter = new CustomAdapter<Disk>(
							view.getContext(), disks);
					disksListView.setAdapter(customAdapter);
				}
			} catch (Exception e) {
				Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG)
						.show();
			} finally {
				super.onPostExecute(json);
			}
		}
	}

}
