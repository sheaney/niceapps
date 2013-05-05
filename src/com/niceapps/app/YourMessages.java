package com.niceapps.app;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.savagelook.android.UrlJsonAsyncTask;

/**
 * 
 * @author Abigail S Hdz, Samuel Heaney
 *
 */
public class YourMessages extends Activity implements OnItemClickListener {
	private static final String MESSAGES_URL = "http://niceapps.herokuapp.com/msg_disk/";
	private static final String NO_MESSAGES = "Your inbox is empty";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.your_messages);
		
		String username = getIntent().getStringExtra("username");
		
		loadMessagesFromAPI(MESSAGES_URL + username + ".json");
	}

	@Override
	public void onStop() {
		super.onStop();
		finish();
	}

	/*
	 * (non-Javadoc)
	 * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View, int, long)
	 */
	public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
		Message selected_message = (Message) parent.getItemAtPosition(pos);
		Intent intent = new Intent(this, Message_detail.class);
		intent.putExtra("message", selected_message);
		startActivity(intent);
	}

	/**
	 * 
	 * Will instantiate a GetMessagesTask which will use an AsycTask to fetch
	 * all the messages associated to a user and a disk stored in a server side DB. 
	 * Side effects include setting up a ListView with the proper disk information 
	 * and a "Loading Inbox..." message. 
	 * 
	 * @param url is the address where the GET request will be sent to
	 * @param view represents the ListView that will be populated
	 */
	private void loadMessagesFromAPI(String url) {
		GetMessagesTask getMessagesTask = new GetMessagesTask(YourMessages.this);
		getMessagesTask.setMessageLoading("Loading Inbox...");
		getMessagesTask.execute(url);
	}

	/**
	 * Represents a task that will execute asynchronously and fetch a JSONObject
	 * representing Messages
	 * 
	 * @author Abigail S Hdz, Samuel Heaney
	 *
	 */
	private class GetMessagesTask extends UrlJsonAsyncTask {
		public GetMessagesTask(Context context) {
			super(context);
		}

		@Override
		protected void onPostExecute(JSONObject json) {
			try {
				JSONArray jsonMessages = json.getJSONObject("data")
						.getJSONArray("messages");
				int length = jsonMessages.length();
				ArrayList<Message> messages = new ArrayList<Message>(length);
				
				// If the user has no messages then display a toast indicating that
				if (length <= 0) {
					Toast.makeText(context, NO_MESSAGES, Toast.LENGTH_LONG).show();
				}

				for (int i = 0; i < length; i++) {
					// JSON message object
					JSONObject jsonMessage = jsonMessages.getJSONObject(i);
					// JSON disk object
					JSONObject jsonDisk = jsonMessage.getJSONObject("disk");

					messages.add(new Message(jsonDisk
							.getString("image_encoding"), jsonDisk
							.getString("title"), jsonMessage
							.getString("content")));
				}

				ListView messagesListView = (ListView) findViewById(R.id.messages_list);
				if (messagesListView != null) {
					CustomAdapter<Message> customAdapter = new CustomAdapter<Message>(
							YourMessages.this, messages);
					messagesListView.setAdapter(customAdapter);
					messagesListView.setOnItemClickListener(YourMessages.this);
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
