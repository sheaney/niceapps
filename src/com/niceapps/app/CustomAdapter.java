package com.niceapps.app;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {

	  private LayoutInflater inflater;
	  private ArrayList<Disk> objects;

	   private class ViewHolder {
	      TextView textView1;
	      TextView textView2;
	   }

	   public CustomAdapter(Context context, ArrayList<Disk> objects) {
	      inflater = LayoutInflater.from(context);
	      this.objects = objects;
	   }

	   public int getCount() {
	      return objects.size();
	   }

	   public Disk getItem(int position) {
	      return objects.get(position);
	   }

	   public long getItemId(int position) {
	      return position;
	   }

	   public View getView(int position, View convertView, ViewGroup parent) {
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
	      holder.textView1.setText(objects.get(position).getTitle());
	      holder.textView2.setText(objects.get(position).getArtist());
	      return convertView;
	   }
	}