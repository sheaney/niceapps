package com.niceapps.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Item_details extends Activity {
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_details);
        
        
      //Accion a realizar en caso de que se oprima el boton Im interested
        ((Button) findViewById(R.id.button2)).setOnClickListener(new OnClickListener() {
        	public void onClick(View view){
        		send_offer(); // Manda llamar al método send_offer
        	}
        });
    }
	
	private void send_offer(){
		Intent i = new Intent(getBaseContext(), Offer_for_item.class);
		startActivity(i);
	}

}
