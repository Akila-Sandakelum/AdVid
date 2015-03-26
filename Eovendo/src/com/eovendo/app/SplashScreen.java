package com.eovendo.app;



import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class SplashScreen extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_screen);
		
		
		 TextView text = (TextView) findViewById(R.id.textView1);
		 Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/RobotoCondensed-Light.ttf");
		 text.setTypeface(tf);
		 
		ActionBar bar=getActionBar();
		bar.hide();
		
		Handler handler=new Handler();
		handler.postDelayed(new MyHandler(),3000);
		
	}

	
	class MyHandler implements Runnable{

		@Override
		public void run() {
			startActivity(new Intent(getApplication(),MainActivity.class));
			
		}
		
		
	}

}
