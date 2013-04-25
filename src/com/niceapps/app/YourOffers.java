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
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class YourOffers extends Activity implements OnItemClickListener {
	//private static final String OFFERS_URL = "http://niceapps.herokuapp.com/offers.json"; offers by userId
	private static final String OFFERS_URL = "http://niceapps.herokuapp.com/disks.json";
	
	ListView listView;
	ArrayAdapter<String> adapter;
	List<String> strs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.your_offers);
        
        loadOffersFromAPI(OFFERS_URL);
	}
	
	public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
		Disk selected_disk = (Disk) parent.getItemAtPosition(pos);
		Intent intent = new Intent(this, See_offer.class);
		intent.putExtra("disk_id", selected_disk.getId());
		intent.putExtra("disk_title", selected_disk.getTitle());
		intent.putExtra("offer_id", 1); //Selected_offer
		startActivity(intent);
	}
	
	private void loadOffersFromAPI(String url) {
	    GetOffersTask getOffersTask = new GetOffersTask(YourOffers.this);
	    getOffersTask.setMessageLoading("Loading Offers...");
	    getOffersTask.execute(url);
	}
	
	private class GetOffersTask extends UrlJsonAsyncTask {
	    public GetOffersTask(Context context) {
	        super(context);
	    }
	    
	    @Override
        protected void onPostExecute(JSONObject json) {
            try {
                JSONArray jsonOffers = json.getJSONObject("data").getJSONArray("disks");
                int length = jsonOffers.length();
                ArrayList<Disk> disks = new ArrayList<Disk>(length);
                
                for (int i = 0; i < length; i++) {
                    disks.add(new Disk((jsonOffers.getJSONObject(i).getInt("id")),
                    					jsonOffers.getJSONObject(i).getString("title"),
                    					jsonOffers.getJSONObject(i).getString("artist"),
                    					jsonOffers.getJSONObject(i).getString("pic_path"),
                    					jsonOffers.getJSONObject(i).getString("conditions"),
                    					jsonOffers.getJSONObject(i).getString("interest")));

                }

                ListView disksListView = (ListView) findViewById (R.id.list);
                if (disksListView != null) {
                	CustomAdapter customAdapter = new CustomAdapter(YourOffers.this, disks);
                	disksListView.setAdapter(customAdapter);
                	disksListView.setOnItemClickListener(YourOffers.this);
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
