package com.niceapps.app;


import org.json.JSONObject;
import com.savagelook.android.UrlJsonAsyncTask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class See_offer extends Activity{
	private static String OFFER_URL = "http://niceapps.herokuapp.com/offers/";
	private String title;
	private TextView title_text;
	private EditText message;
	private Offer offer;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.see_offer);
        
       title = getIntent().getStringExtra("disk_title");
       
       OFFER_URL += getIntent().getStringExtra("disk_id") + ".json";
       
       title_text = (TextView) findViewById(R.id.title);
       title_text.setText(title);
       
       message = (EditText) findViewById(R.id.message);
       
       //loadOfferFromAPI(OFFER_URL);
       
      //Accion a realizar en caso de que se oprima el boton Accept
        ((Button) findViewById(R.id.accept)).setOnClickListener(new OnClickListener() {
        	public void onClick(View view){
        		accept_offer(); // Manda llamar al método accept_offer
        	}
        });
        
      //Accion a realizar en caso de que se oprima el boton Reject
        ((Button) findViewById(R.id.reject)).setOnClickListener(new OnClickListener() {
        	public void onClick(View view){
        		reject_offer(); // Manda llamar al método reject_offer
        	}
        });
    }
	
	private void accept_offer(){
		Intent intent = new Intent(getBaseContext(), Accept.class);
		intent.putExtra("disk_title", title);
		intent.putExtra("offer", offer);
		startActivity(intent);
	}
	
	private void reject_offer(){
		Intent intent = new Intent(getBaseContext(), Reject.class);
		intent.putExtra("disk_title", title);
		intent.putExtra("offer", offer);
		startActivity(intent);
	}
	
	private void loadOfferFromAPI(String url) {
	    GetOffersTask getOffersTask = new GetOffersTask(See_offer.this);
	    getOffersTask.setMessageLoading("Loading Offer...");
	    getOffersTask.execute(url);
	}
	
	private class GetOffersTask extends UrlJsonAsyncTask {
	    public GetOffersTask(Context context) {
	        super(context);
	    }
	    
	    @Override
        protected void onPostExecute(JSONObject json) {
            try {
                JSONObject jsonOffer = json.getJSONObject("data").getJSONObject("offer");
                offer = new Offer(jsonOffer.getInt("id"), jsonOffer.getString("status"),
                    			   jsonOffer.getString("message"), jsonOffer.getInt("receiver"),
                    			   jsonOffer.getInt("sender"), jsonOffer.getInt("disk_id"));
                message.setText(offer.getMessage());

            } catch (Exception e) {
            Toast.makeText(context, e.getMessage(),
                Toast.LENGTH_LONG).show();
        } finally {
            super.onPostExecute(json);
        }
    }
}
	
// Llave finaliza actividad
}
