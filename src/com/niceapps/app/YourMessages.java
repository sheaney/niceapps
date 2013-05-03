package com.niceapps.app;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.savagelook.android.UrlJsonAsyncTask;

public class YourMessages extends Activity implements OnItemClickListener {
	private static final String MESSAGES_URL = "http://niceapps.herokuapp.com/msg_disk/";

	ListView listView;
	ArrayAdapter<String> adapter;
	List<String> strs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.your_messages);
		
		String username = getIntent().getStringExtra("username");
		
		loadMessagesFromAPI(MESSAGES_URL + "Abigail%20S%20Hdz" + ".json");
		//loadMessagesFromAPI(MESSAGES_URL + username + ".json");

	}

	// This will not work for now
	public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
		Message selected_message = (Message) parent.getItemAtPosition(pos);
		Intent intent = new Intent(this, Item_details.class);
		//intent.putExtra("disk", selected_disk);
		startActivity(intent);
	}

	private void loadMessagesFromAPI(String url) {
		GetMessagesTask getMessagesTask = new GetMessagesTask(YourMessages.this);
		getMessagesTask.setMessageLoading("Loading Inbox...");
		getMessagesTask.execute(url);
	}

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
