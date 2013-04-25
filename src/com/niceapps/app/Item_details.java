package com.niceapps.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Item_details extends Activity {
	private static String DISK_URL = "http://niceapps.herokuapp.com/disks/";
	private TextView title, condition, interests;
	private Disk disk;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_details);
        
        disk = (Disk) getIntent().getSerializableExtra("disk");
        DISK_URL += disk.getId() + ".json";
        
        
        title = (TextView) findViewById(R.id.textView1);
        condition = (TextView) findViewById(R.id.condition);
        interests = (TextView) findViewById(R.id.interest);
        
        title.setText(disk.getTitle());
        condition.setText(disk.getConditions());
        interests.setText(disk.getInterest());
        
      //Accion a realizar en caso de que se oprima el boton Im interested
        ((Button) findViewById(R.id.button2)).setOnClickListener(new OnClickListener() {
        	public void onClick(View view){
        		send_offer(); // Manda llamar al método send_offer
        	}
        });
    }
	
	private void send_offer(){
		Intent intent = new Intent(getBaseContext(), Offer_for_item.class);
		intent.putExtra("disk", disk);
		startActivity(intent);
	}

}
