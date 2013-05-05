package com.niceapps.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Fragment that is displayed when the user has not been authenticated by Facebook
 * 
 * @author Abigail S Hdz, Samuel Heaney
 *
 */
public class SplashFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, 
	        ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
	    View view = inflater.inflate(R.layout.splash, 
	            container, false);
	    return view;
	}
}
