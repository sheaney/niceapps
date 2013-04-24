package com.niceapps.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Accept extends Activity {
	
	private String title;
	private TextView title_text;
	private EditText message;
	private Offer offer;

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accept_offer);
        
        title = getIntent().getStringExtra("disk_title");
        
        title_text = (TextView) findViewById(R.id.title);
        title_text.setText(title);
        
        message = (EditText) findViewById(R.id.message);
        
        offer = (Offer) getIntent().getSerializableExtra("offer");
        
      //Accion a realizar en caso de que se oprima el boton Accept
        ((Button) findViewById(R.id.button2)).setOnClickListener(new OnClickListener() {
        	public void onClick(View view){
        		offer.reply_msg = "" + message.getText();
        		offer.status = "accepted";
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
