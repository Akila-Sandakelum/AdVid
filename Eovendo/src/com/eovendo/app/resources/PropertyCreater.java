package com.eovendo.app.resources;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import android.content.Context;
import android.content.res.AssetManager;


public class PropertyCreater {
	
	private Context context;
	private AssetManager manager;
	private Properties property;
	
	public PropertyCreater(Context context){
		
		this.context=context;
		property=new Properties();
		
	}

	public Properties getProperties(String fileName){
		
		manager=context.getAssets();
		
		try {
			
			InputStream reader=manager.open(fileName);
			property.load(reader);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return property;
		
	}

}
