package com.niceapps.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Offer_for_item extends Activity{
	private TextView title;
	private EditText offer;
	private Disk disk;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offer_for_item);
        
        disk = (Disk) getIntent().getSerializableExtra("disk");
        
        title = (TextView) findViewById(R.id.textView1);
        offer = (EditText) findViewById(R.id.editText1);
        
        title.setText(disk.getTitle());
        
      //Accion a realizar en caso de que se oprima el boton Send Offer
        ((Button) findViewById(R.id.button1)).setOnClickListener(new OnClickListener() {
        	public void onClick(View view){
        		String offer_msg = offer.getText().toString();
        		send_offer(); // Manda llamar al método send_offer
        	}
        });
    }
	
	private void send_offer() {
		// Se tiene que crear nueva oferta aqui
		String msg = "Offer for " + disk.getTitle() + " sent..";
		Toast.makeText(getApplicationContext(),
				msg, Toast.LENGTH_SHORT).show();
	}

}
