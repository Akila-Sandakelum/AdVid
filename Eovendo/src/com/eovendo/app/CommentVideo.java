package com.eovendo.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.eovendo.app.resources.PropertyCaller;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

public class CommentVideo extends Activity {

	CheckBox unclrMsg, clrMsg, irrelvnt, relavnt, untrust, credble, bored,
			 entertn, unique, common;
	EditText comments;
	RatingBar ratingbar;
	Button submitBtn;
	String user_id,session_id,moreComments,checkdComnt="",videoId,rate;
	TextView textview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rating_video);
		
		Properties property=PropertyCaller.getInstance(CommentVideo.this);
		final String path=property.getProperty("connection-url");
		
		Intent intent=getIntent();
		session_id=intent.getExtras().getString("session_id");
		user_id=intent.getExtras().getString("user_id");
		videoId=intent.getExtras().getString("video_id");
		
		unclrMsg = (CheckBox) findViewById(R.id.unclr_msgCB);
		clrMsg = (CheckBox) findViewById(R.id.clr_msgCB);
		irrelvnt = (CheckBox) findViewById(R.id.irrelavntCB);
		relavnt = (CheckBox) findViewById(R.id.relavntCB);
		untrust = (CheckBox) findViewById(R.id.untrustCB);
		credble = (CheckBox) findViewById(R.id.credbleCB);
		bored = (CheckBox) findViewById(R.id.boreCB);
		entertn = (CheckBox) findViewById(R.id.entertnCB);
		unique = (CheckBox) findViewById(R.id.uniqueCB);
		common = (CheckBox) findViewById(R.id.commonCB);
		comments = (EditText) findViewById(R.id.commentBox);
		ratingbar=(RatingBar) findViewById(R.id.video_ratingBar);
		submitBtn = (Button) findViewById(R.id.submitCmnt);
		textview=(TextView) findViewById(R.id.commntTV);
		
		textview.setVisibility(View.GONE);

		final CheckBox check[] = new CheckBox[] { unclrMsg, clrMsg, irrelvnt,
				relavnt, untrust, credble, bored, entertn, common, unique };

		submitBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				for (int i = 0; i < check.length; i++) {

					if (check[i].isChecked()) {
						Log.i("CHECKED", "1");
						checkdComnt+="1";
					} else {
						Log.i("UNCHECKED", "0");
						checkdComnt+="0";
					}

				}
				
				Log.i("FINAL COMMENT", checkdComnt);
				
				moreComments=comments.getText().toString();
				Float rating=ratingbar.getRating();
				rate=Float.toString(rating);
				
				if(checkdComnt.equals("0000000000")){
					new AlertDialog.Builder(CommentVideo.this).setTitle("Action report")
					.setMessage("Please fill out the check boxes!")
					.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							
							checkdComnt="";
							dialog.dismiss();
							
						}
					})
					.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							
							try {
								int res=new SaveWatchlist().execute(user_id,session_id,videoId,path).get();
								if(res==1){
									Intent intent=new Intent(CommentVideo.this, Dashboard.class);
									intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
									intent.putExtra("user_id",Integer.parseInt(user_id) );
									intent.putExtra("session_id", session_id);
									startActivity(intent);
								}
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (ExecutionException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							
							
						}
					})
					.show();
				}else{
					
					
					try {
						
					int response=new SaveComments().execute(checkdComnt,moreComments,path,rate,videoId,user_id,session_id).get();
					
					if(response==1){
					
						new AlertDialog.Builder(CommentVideo.this).setTitle("Action report").setMessage("Your comments are successsfully recorded").setNeutralButton("Ok", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								
								Intent intent=new Intent(CommentVideo.this, Dashboard.class);
								intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
								intent.putExtra("user_id",Integer.parseInt(user_id) );
								intent.putExtra("session_id", session_id);
								startActivity(intent);
								
							}
						}).show();
						
					}else{
						new AlertDialog.Builder(CommentVideo.this).setTitle("Action report").setMessage("Error occured. Try again !").setNeutralButton("Ok", null).show();
					}
						
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					
				}
				
				
			}
			
			
		});
		
		

	}

}

class SaveComments extends AsyncTask<String, Void, Integer>{
	
	String response = "";
	int output;
	@Override
	protected Integer doInBackground(String... params) {
		
		Log.i("ASNCTASK INPUT", params[0]+" "+params[1]+" "+" "+params[2]+" "+ params[3]+" "+params[4]+" "+params[5]+" "+params[6]);
		
		try {
			
			String URL=params[2]+"SaveCommentsInfo";
			Log.i("URL", URL);
	         
			JsonObject commentJob=new JsonObject();
			commentJob.addProperty("checkedCommnt", params[0]);
			commentJob.addProperty("moreCommnt", params[1]);
			commentJob.addProperty("rate", params[3]);
			commentJob.addProperty("video_id", params[4]);
			commentJob.addProperty("user_id", params[5]);
			commentJob.addProperty("session_id", params[6]);
			
			
			Log.i("input..........",commentJob.toString());
			
			
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(URL);
           
            httpPost.setEntity(new StringEntity(commentJob.toString()));
            httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-type", "application/json");
            HttpResponse httpResponse = httpClient.execute(httpPost);
                
            //Get hold of the response entity (-> the data):
            
            HttpEntity httpEntity = httpResponse.getEntity();
            
            InputStream is = httpEntity.getContent();
            
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line = null;
            
            
           while ((line = rd.readLine()) != null) // Read the response line-by-line from the bufferedReader
            {
             response += line;
             
            }
           
           Log.i("server output..........",response);
          // MainActivity.res.setText("server output  "+response);
           is.close();
           
           JsonParser par = new JsonParser();
           JsonObject jObj = (JsonObject)par.parse(response);
           JsonElement elem1=jObj.get("result");
           
           output=elem1.getAsInt();
                   
        } catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return output;
	}
	
}

class SaveWatchlist extends AsyncTask<String, Void, Integer>{

	String response = "";
	int output;
	
	@Override
	protected Integer doInBackground(String... params) {
		
		try {
			
			String URL=params[3]+"SaveWatchedInfo";
			Log.i("URL", URL);
	         
			JsonObject watchedJob=new JsonObject();
			watchedJob.addProperty("user_id", params[0]);
			watchedJob.addProperty("session_id", params[1]);
			watchedJob.addProperty("video_id", params[2]);
			
			Log.i("input..........",watchedJob.toString());
			
			
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(URL);
           
            httpPost.setEntity(new StringEntity(watchedJob.toString()));
            httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-type", "application/json");
            HttpResponse httpResponse = httpClient.execute(httpPost);
                
            //Get hold of the response entity (-> the data):
            
            HttpEntity httpEntity = httpResponse.getEntity();
            
            InputStream is = httpEntity.getContent();
            
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line = null;
            
            
           while ((line = rd.readLine()) != null) // Read the response line-by-line from the bufferedReader
            {
             response += line;
             
            }
           
           Log.i("server output..........",response);
          // MainActivity.res.setText("server output  "+response);
           is.close();
           
           JsonParser par = new JsonParser();
           JsonObject jObj = (JsonObject)par.parse(response);
           JsonElement elem1=jObj.get("result");
           
           output=elem1.getAsInt();
                   
        } catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return output;
	}
	
}















