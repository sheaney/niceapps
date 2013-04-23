package com.niceapps.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SplashFragment extends Fragment {
	private static final String TAG = "SelectionFragment";

	@Override
	public View onCreateView(LayoutInflater inflater, 
	        ViewGroup container, Bundle savedInstanceState) {
		// calling the super was not here before, this may not go here
		super.onCreateView(inflater, container, savedInstanceState);
	    View view = inflater.inflate(R.layout.splash, 
	            container, false);
	    return view;
	}
}
