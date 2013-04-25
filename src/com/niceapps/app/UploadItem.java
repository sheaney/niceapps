package com.niceapps.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class UploadItem extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_item);
        
        ((Button) findViewById(R.id.upload)).setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				upload(view);
			}
		});
        
	}
	
	/** Called when the user clicks the 'Upload' button */
	public void upload(View view) {
		// Do something in response to button
		Intent intent = new Intent(this, YourItems.class);
		startActivity(intent);
	}
	
}
