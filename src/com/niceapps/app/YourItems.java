package com.niceapps.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class YourItems extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.your_items);
        
        ((Button) findViewById(R.id.new_item)).setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				new_item(view);
			}
		});
	}
	
	/** Called when the user clicks the 'New Item' button */
	public void new_item(View view) {
		// Do something in response to button
		Intent intent = new Intent(this, UploadItem.class);
		startActivity(intent);
	}
	
}
