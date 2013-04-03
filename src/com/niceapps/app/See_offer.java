package com.niceapps.app;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class See_offer extends Activity{

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.see_offer);
        
      //Accion a realizar en caso de que se oprima el boton Accept
        ((Button) findViewById(R.id.button2)).setOnClickListener(new OnClickListener() {
        	public void onClick(View view){
        		accept_offer(); // Manda llamar al método accept_offer
        	}
        });
        
      //Accion a realizar en caso de que se oprima el boton Reject
        ((Button) findViewById(R.id.button1)).setOnClickListener(new OnClickListener() {
        	public void onClick(View view){
        		reject_offer(); // Manda llamar al método reject_offer
        	}
        });
    }
	
	private void accept_offer(){
		Intent i = new Intent(getBaseContext(), Accept.class);
		startActivity(i);
	}
	
	private void reject_offer(){
		Intent i = new Intent(getBaseContext(), Reject.class);
		startActivity(i);
	}
	
// Llave finaliza actividad
}
