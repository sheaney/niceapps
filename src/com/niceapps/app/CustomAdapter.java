package com.niceapps.app;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Class that will help represent content for a List View.
 * 
 * @author Abigail S Hdz, Samuel Heaney
 *
 * @param <T> generic type parameter that will be of either type Disk or Message
 */
public class CustomAdapter<T> extends BaseAdapter {

	  private LayoutInflater inflater;
	  private ArrayList<T> objects; // Custom Adapter container for custom elements

	  /**
	   * View Holder that will hold the element contained in a layout
	   * 
	   * @author Abigail S Hdz, Samuel Heaney
	   *
	   */
	   private class ViewHolder {
	      TextView textView1;
	      TextView textView2;
	      ImageView image;
	   }

	   /**
	    * CustomAdapter contructor that will initialize class members
	    *  
	    * @param context
	    * @param objects custom user defined element container
	    */
	   public CustomAdapter(Context context, ArrayList<T> objects) {
	      inflater = LayoutInflater.from(context);
	      this.objects = objects;
	   }

	   /**
	    * @return the CustomAdapter container size
	    */
	   public int getCount() {
	      return objects.size();
	   }

	   /**
	    * @return the element at the specified position
	    */
	   public T getItem(int position) {
	      return objects.get(position);
	   }

	   /**
	    * @return the position of the specified element
	    */
	   public long getItemId(int position) {
	      return position;
	   }

	   /**
	    * Set up a list view layout with the field of a user defined class.
	    * Currently this method only accepts objects of type Disk or Message
	    */
	   @SuppressWarnings("unchecked")
	public View getView(int position, View convertView, ViewGroup parent) {
	      if (objects.get(position) instanceof Disk) {
	    	  ViewHolder holder = null;
		      if(convertView == null) {
		        holder = new ViewHolder();
		        convertView = inflater.inflate(R.layout.list_row_vinyl, null);
		        holder.textView1 = (TextView) convertView.findViewById(R.id.title);
		        holder.textView2 = (TextView) convertView.findViewById(R.id.artist);
		         convertView.setTag(holder);
		      } else {
		         holder = (ViewHolder) convertView.getTag();
		      }
	    	  
	    	  Disk disk = (Disk) objects.get(position);
	    	  
		      holder.textView1.setText(disk.getTitle());
		      holder.textView2.setText(disk.getArtist());
		      
		      // decode image & set it up in the view
		      decodeBase64ImageAndSetHolder(convertView, holder, disk.getImageEncoding());
	      } else if (objects.get(position) instanceof Message) {
	    	  ViewHolder holder = null;
		      if(convertView == null) {
		        holder = new ViewHolder();
		        convertView = inflater.inflate(R.layout.list_row_message, null);
		        holder.textView1 = (TextView) convertView.findViewById(R.id.title);
		        holder.textView2 = (TextView) convertView.findViewById(R.id.message_content);
		         convertView.setTag(holder);
		      } else {
		         holder = (ViewHolder) convertView.getTag();
		      }
	    	  
	    	  Message message = (Message) objects.get(position);
	    	  
		      holder.textView1.setText(message.getTitle());
		      holder.textView2.setText(message.getContent());
		      
		      // decode image & set it up in the view
		      decodeBase64ImageAndSetHolder(convertView, holder, message.getImageEncoding());
	      }
	      
	      return convertView;
	   }
	   
	   /**
	    * Decodes a base 64 string into an Image View and sets it up in its corresponding layout
	    * 
	    * @param view the current view that is calling the method
	    * @param holder container for the layout elements
	    * @param imageStringBase64 the string that will be decoded
	    */
	   public void decodeBase64ImageAndSetHolder(View view, ViewHolder holder, String imageStringBase64) {
		   byte[] imageAsBytes = Base64.decode(imageStringBase64, Base64.DEFAULT);
		   holder.image = (ImageView) view.findViewById(R.id.list_image);
		   holder.image.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
	   }
	}