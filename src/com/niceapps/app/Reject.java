package com.niceapps.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Reject extends Activity {

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reject_offer);
        
      //Accion a realizar en caso de que se oprima el boton Reject
        ((Button) findViewById(R.id.button2)).setOnClickListener(new OnClickListener() {
        	public void onClick(View view){
        		reject_offer(); // Manda llamar al método accept_offer
        	}
        });
        
    }
	
	private void reject_offer(){
		String msg = "Offer rejected, message sent";
		Toast.makeText(getApplicationContext(),
				msg, Toast.LENGTH_SHORT).show();
	}
}
