package com.niceapps.app;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import com.savagelook.android.UrlJsonAsyncTask;

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


public class YourItems extends Activity implements OnItemClickListener {
	
	//private static final String DISKS_URL = "http://niceapps.herokuapp.com/disk_list/(userid).json";
	private static final String DISKS_URL = "http://niceapps.herokuapp.com/disks.json";
	
	ListView listView;
	ArrayAdapter<String> adapter;
	List<String> strs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.your_items);
        
        loadDisksFromAPI(DISKS_URL);
        
	}
	
	public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
		Disk selected_disk = (Disk) parent.getItemAtPosition(pos);
		Intent intent = new Intent(this, Item_details.class);
		intent.putExtra("disk", selected_disk);
		startActivity(intent);
	}
	
	private void loadDisksFromAPI(String url) {
	    GetDisksTask getDisksTask = new GetDisksTask(YourItems.this);
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
	                JSONArray jsonDisks = json.getJSONObject("data").getJSONArray("disks");
	                int length = jsonDisks.length();
	                ArrayList<Disk> disks = new ArrayList<Disk>(length);
	                
	                for (int i = 0; i < length; i++) {
	                    disks.add(new Disk((jsonDisks.getJSONObject(i).getInt("id")),
	                    					jsonDisks.getJSONObject(i).getString("title"),
	                    					jsonDisks.getJSONObject(i).getString("artist"),
	                    					jsonDisks.getJSONObject(i).getString("pic_path"),
	                    					jsonDisks.getJSONObject(i).getString("conditions"),
	                    					jsonDisks.getJSONObject(i).getString("interest")));
	                }

	                ListView disksListView = (ListView) findViewById (R.id.list);
	                if (disksListView != null) {
	                	CustomAdapter customAdapter = new CustomAdapter(YourItems.this, disks);
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
