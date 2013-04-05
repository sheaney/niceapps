package com.niceapps.app;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;


public class Profile extends Activity implements OnItemClickListener {
	
	ListView listView;
	ArrayAdapter<String> adapter;
	List<String> strs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        
        strs = new ArrayList<String>();
        strs.add("Vinyl 1");
        strs.add("Vinyl 2");
        strs.add("Vinyl 3");
        
        ListView lv = (ListView) findViewById(R.id.my_items);
        
        adapter = new ArrayAdapter<String>(this, R.layout.list_row_vinyl, R.id.artist, strs);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(this);
        
        ((Button) findViewById(R.id.new_item)).setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				new_item(view);
			}
		});
        
        ((Button) findViewById(R.id.more_items)).setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				more_items(view);
			}
		});
        
        ((Button) findViewById(R.id.button1)).setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				see_offers(view);
			}
		});
	}
	
	public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
		Intent intent = new Intent(this, Item_details.class);
		startActivity(intent);
	}
	
	/** Called when the user clicks the 'New Item' button */
	public void new_item(View view) {
		// Do something in response to button
		Intent intent = new Intent(this, UploadItem.class);
		startActivity(intent);
	}
	
	/** Called when the user clicks the 'More' button */
	public void more_items(View view) {
		// Do something in response to button
		Intent intent = new Intent(this, YourItems.class);
		startActivity(intent);
	}	
	
	/** Called when the user clicks the 'Go to items..' button */
	public void see_offers(View view) {
		// Do something in response to button
		Intent intent = new Intent(this, YourOffers.class);
		startActivity(intent);
	}	
	
}
