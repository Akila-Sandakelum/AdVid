package com.eovendo.app.resources;

import java.util.Properties;

import android.content.Context;

public class PropertyCaller {
	
	static Properties property;
	
	public static Properties getInstance(Context context){
	
		PropertyCreater creater=new PropertyCreater(context);
		property=creater.getProperties("config.properties");
		return property;
		
	}

}
