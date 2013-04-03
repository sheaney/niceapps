package com.niceapps.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Login extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		((Button) findViewById(R.id.button_login)).setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				login(view);
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_login, menu);
		return true;
	}
	
	/** Called when the user clicks the Login button */
	public void login(View view) {
		// Do something in response to button
		Intent intent = new Intent(this, Profile.class);
		startActivity(intent);
	}

}
