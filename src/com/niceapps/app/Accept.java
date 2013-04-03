package com.niceapps.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Accept extends Activity {

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accept_offer);
        
      //Accion a realizar en caso de que se oprima el boton Accept
        ((Button) findViewById(R.id.button2)).setOnClickListener(new OnClickListener() {
        	public void onClick(View view){
        		accept_offer(); // Manda llamar al método accept_offer
        	}
        });
        
    }
	
	private void accept_offer(){
		String msg = "Offer accepted, message sent";
		Toast.makeText(getApplicationContext(),
				msg, Toast.LENGTH_SHORT).show();
	}
}
