package com.niceapps.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class Profile extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        
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
				see_items(view);
			}
		});
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
	public void see_items(View view) {
		// Do something in response to button
		Intent intent = new Intent(this, YourItems.class);
		startActivity(intent);
	}	
	
}
