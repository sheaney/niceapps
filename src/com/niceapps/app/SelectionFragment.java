package com.niceapps.app;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.ProfilePictureView;

public class SelectionFragment extends Fragment implements OnItemClickListener {
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
	
	ListView listView;
	ArrayAdapter<String> adapter;
	List<String> strs;

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
		View view = inflater.inflate(R.layout.selection, container, false);
		
		// Hide soft keyboard when user logs into app
		//InputMethodManager inputManager = (InputMethodManager) view.getContext().getSystemService(Context.WINDOW_SERVICE);
		//inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);

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
	    
	    strs = new ArrayList<String>();
        strs.add("Vinyl 1");
        strs.add("Vinyl 2");
        strs.add("Vinyl 3");
        
        ListView lv = (ListView) view.findViewById(R.id.my_items);
        
        adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.list_row_vinyl, R.id.artist, strs);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(this);
        
        ((Button) view.findViewById(R.id.new_item)).setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				new_item(view);
			}
		});
        
        ((Button) view.findViewById(R.id.more_items)).setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				more_items(view);
			}
		});
        
        ((Button) view.findViewById(R.id.button1)).setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				see_offers(view);
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
		Intent intent = new Intent(this.getActivity(), Item_details.class);
		startActivity(intent);
	}
	
	/** Called when the user clicks the 'New Item' button */
	public void new_item(View view) {
		// Do something in response to button
		Intent intent = new Intent(this.getActivity(), UploadItem.class);
		startActivity(intent);
	}
	
	/** Called when the user clicks the 'More' button */
	public void more_items(View view) {
		// Do something in response to button
		Intent intent = new Intent(this.getActivity(), YourItems.class);
		startActivity(intent);
	}	
	
	/** Called when the user clicks the 'Go to items..' button */
	public void see_offers(View view) {
		// Do something in response to button
		Intent intent = new Intent(this.getActivity(), YourOffers.class);
		startActivity(intent);
	}

}
