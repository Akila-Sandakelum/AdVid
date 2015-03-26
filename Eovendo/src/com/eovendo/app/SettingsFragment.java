package com.eovendo.app;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class SettingsFragment extends Fragment{
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		final View view = inflater.inflate(R.layout.settings_view, container,
				false);

		String session_id=getArguments().getString("session_id");
		int user_id=getArguments().getInt("user_id");
		
		Context context = getActivity().getApplicationContext();
        //Toast.makeText(context, "Hello toast!", Toast.LENGTH_LONG).show();
		Toast.makeText(context, "SettingFrag "+session_id+" "+user_id+" end", 2000).show();
		
		return view;
	}
}
