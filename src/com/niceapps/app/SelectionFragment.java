package com.niceapps.app;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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

public class SelectionFragment extends Fragment implements OnItemClickListener {
	View view;
	ListView disksListView;
	private static final String DISKS_URL = "http://niceapps.herokuapp.com/disks.json";
	private ProfilePictureView profilePictureView;
	private TextView userNameView;

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

	private void onSessionStateChange(final Session session,
			SessionState state, Exception exception) {
		if (session != null && session.isOpened()) {
			// Get the user's data.
			makeMeRequest(session);
		}
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
								// Set the Textview's text to the user's name.
								userNameView.setText(user.getName());
							}
						}
						if (response.getError() != null) {
							// Handle errors, will do so later.
						}
					}
				});
		request.executeAsync();
	}

	public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
		Disk selected_disk = (Disk) parent.getItemAtPosition(pos);
		Intent intent = new Intent(this.getActivity(), Item_details.class);
		intent.putExtra("disk", selected_disk);
		Log.w("[DISK DETAILS]", selected_disk.getTitle());
		Log.w("[DISK DETAILS]", selected_disk.getConditions());
		Log.w("[DISK DETAILS]", selected_disk.getInterest());
		startActivity(intent);
	}

	/** Called when the user clicks the 'More' button */
	public void more_items(View view) {
		// Do something in response to button
		Intent intent = new Intent(this.getActivity(), YourItems.class);
		startActivity(intent);
	}	

	private void loadDisksFromAPI(String url, View view) {
		GetDisksTask getDisksTask = new GetDisksTask(view.getContext());
		getDisksTask.setMessageLoading("Loading Disks...");
		getDisksTask.execute(url);
	}

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
					CustomAdapter customAdapter = new CustomAdapter(
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
