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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.savagelook.android.UrlJsonAsyncTask;

/**
 * Activity that will render a List View representing all the Items that are in the
 * server's DB
 * 
 * @author Abigail S Hdz, Samuel Heaney
 *
 */
public class YourItems extends Activity implements OnItemClickListener {
	
	private static final String DISKS_URL = "http://niceapps.herokuapp.com/disks.json";
	
	String fbusername;
	ListView listView;
	ArrayAdapter<String> adapter;
	List<String> strs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.your_items);
        
        if(getIntent().hasExtra("fbusername")){
			fbusername = getIntent().getStringExtra("fbusername");
		}
		else {
			fbusername = "Not available";
		}
        
		// Set up action that will be triggered when user presses the Home button
		((ImageButton)findViewById(R.id.home))
		.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				go_home();
			}
		});
		
		// Load Disks from server response
        loadDisksFromAPI(DISKS_URL);
        
	}
	
	/**
	 * Will close the current activity and take the user to the initial Main Activity
	 */
	private void go_home() {
		finish();
	}
	
	/*
	 * (non-Javadoc)
	 * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View, int, long)
	 */
	public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
		Disk selected_disk = (Disk) parent.getItemAtPosition(pos);
		Intent intent = new Intent(this, ItemDetails.class);
		intent.putExtra("disk", selected_disk);
		intent.putExtra("fbusername", fbusername);
		startActivity(intent);
	}
	
	@Override
	public void onStop() {
		super.onStop();
		finish();
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
	private void loadDisksFromAPI(String url) {
	    GetDisksTask getDisksTask = new GetDisksTask(YourItems.this);
	    getDisksTask.setMessageLoading("Loading Disks...");
	    getDisksTask.execute(url);
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
	                JSONArray jsonDisks = json.getJSONObject("data").getJSONArray("disks");
	                int length = jsonDisks.length();
	                ArrayList<Disk> disks = new ArrayList<Disk>(length);
	                
	                for (int i = 0; i < length; i++) {
	                    disks.add(new Disk((jsonDisks.getJSONObject(i).getInt("id")),
	                    					jsonDisks.getJSONObject(i).getString("title"),
	                    					jsonDisks.getJSONObject(i).getString("artist"),
	                    					jsonDisks.getJSONObject(i).getString("image_encoding"),
	                    					jsonDisks.getJSONObject(i).getString("status")));
	                }

	                ListView disksListView = (ListView) findViewById (R.id.list);
	                if (disksListView != null) {
	                	CustomAdapter<Disk> customAdapter = new CustomAdapter<Disk>(YourItems.this, disks);
	                	disksListView.setAdapter(customAdapter);
	                	disksListView.setOnItemClickListener(YourItems.this);
	                }
	            } catch (Exception e) {
	            Toast.makeText(context, e.getMessage(),
	                Toast.LENGTH_LONG).show();
	        } finally {
	            super.onPostExecute(json);
	        }
	    }
	}
}
