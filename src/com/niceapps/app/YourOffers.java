package com.niceapps.app;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class YourOffers extends Activity implements OnItemClickListener {
	
	ListView listView;
	ArrayAdapter<String> adapter;
	List<String> strs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.your_items);
        
        strs = new ArrayList<String>();
        strs.add("Vinyl 1");
        strs.add("Vinyl 2");
        strs.add("Vinyl 3");
        
        ListView lv = (ListView) findViewById(R.id.list);
        
        adapter = new ArrayAdapter<String>(this, R.layout.list_row_offer, R.id.artist, strs);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(this);
	}
	
	public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
		Intent intent = new Intent(this, Offer_for_item.class);
		startActivity(intent);
	}
	
}
