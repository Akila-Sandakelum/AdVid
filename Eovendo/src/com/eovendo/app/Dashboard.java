package com.eovendo.app;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class Dashboard  extends FragmentActivity{
	
	private MyAdapter	mAdapter;
	private ViewPager	mPager;
	private int user_id;
	private String session_id;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dashboard_view);	 
		
		ActionBar bar=getActionBar();
		bar.hide();
		
		Intent intent = getIntent();
		user_id=intent.getExtras().getInt("user_id");
		session_id=intent.getExtras().getString("session_id");
		
		Log.i("userID", user_id+"");
		Log.i("session", session_id);
		
		Toast.makeText(this, user_id+" "+session_id, 2000).show();
		
		mAdapter = new MyAdapter(getSupportFragmentManager());
		mPager = (ViewPager) findViewById(R.id.pager1);
		mPager.setAdapter(mAdapter);

	}

	class MyAdapter extends FragmentPagerAdapter{

		private Bundle bundle;
		
		public MyAdapter(FragmentManager fm){
			super(fm);
			bundle=new Bundle();
			bundle.putInt("user_id", user_id);
			bundle.putString("session_id",session_id );
			
			Log.i("inside Myadapter", user_id+" "+session_id);
			  //set Fragmentclass Arguments
			/*Fragmentclass fragobj=new Fragmentclass();
			fragobj.setArguments(bundle);*/

		}

		@Override
		public int getCount( ){
			return 4;
		}

		@Override
		public Fragment getItem(int position){
			switch(position){
			case 0:
				
				WatchFragment watchFrag=new WatchFragment();
				watchFrag.setArguments(bundle);
				Log.i("Inside Fragment","" +bundle.getInt("userID")+" "+bundle.getString("session_id"));
				return watchFrag;
						
			case 1:
				
				HistoryFragment histFrag=new HistoryFragment();
				histFrag.setArguments(bundle);				
				return histFrag;
				
			case 2:
				
				BalanceFragment balFrag=new BalanceFragment();
				balFrag.setArguments(bundle);
				return balFrag;
				
			case 3:
				
				SettingsFragment setFrag=new SettingsFragment();
				setFrag.setArguments(bundle);
				return setFrag;
				
			default:
				return null;
			}

		}

		String	pages[]	= { "Watchlist", "History","Balance","Settings"};

		@Override
		public CharSequence getPageTitle(int position){
			return pages[position];
		}

	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_logout:
	        	logout();
	            return true;
	        case R.id.action_compose:
	            composeMessage();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	private void logout(){
		
		AlertDialog.Builder builder=new AlertDialog.Builder(this); 
		
		builder.setTitle("Logout message").setMessage("Do you want to logout?");
		builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				/*Intent intent=new Intent(EditProfile.this, Activity3.class);	
				intent.putExtra("credential_id", value);
				intent.putExtra("userType", "Basic User");
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);*/
				Toast.makeText(getApplicationContext(), "you are logged out", 1000).show();
				
			}
		});
		
		builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				dialog.dismiss();
				
			}
		}).show();
	}

	private void composeMessage(){
		Toast.makeText(this, "Now you have been logout", 1000).show();
	}
	
}
