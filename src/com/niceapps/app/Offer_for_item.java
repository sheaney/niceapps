package com.niceapps.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Offer_for_item extends Activity{
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offer_for_item);
        
        
      //Accion a realizar en caso de que se oprima el boton Send Offer
        ((Button) findViewById(R.id.button1)).setOnClickListener(new OnClickListener() {
        	public void onClick(View view){
        		send_offer(); // Manda llamar al método send_offer
        	}
        });
    }
	
	private void send_offer() {
		String msg = "Offer Sent..";
		Toast.makeText(getApplicationContext(),
				msg, Toast.LENGTH_SHORT).show();
	}

}
