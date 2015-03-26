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
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

public class ViewComments extends Activity{
	
	CheckBox unclrMsg, clrMsg, irrelvnt, relavnt, untrust, credble, bored,
	 entertn, unique, common;
	RatingBar ratingbar;
	String user_id,session_id,moreComments,checkdComnt="",videoId,rate;
	TextView comments;
	EditText commntET;
	Button submitBtn;
	
	CheckBox check[] = new CheckBox[] { unclrMsg, clrMsg, irrelvnt,
			relavnt, untrust, credble, bored, entertn, common, unique };
	char[] node;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rating_video);
		
		
		ActionBar actionBar = getActionBar();
		 actionBar.hide();

		 Intent intent=getIntent();
		session_id=intent.getExtras().getString("session_id");
		user_id=intent.getExtras().getString("user_id");
		videoId=intent.getExtras().getString("video_id");
		
		Log.i("Params",session_id+" "+user_id+" "+videoId );
		
		 
		
		unclrMsg = (CheckBox) findViewById(R.id.unclr_msgCB);
		unclrMsg.setChecked(false);
		clrMsg = (CheckBox) findViewById(R.id.clr_msgCB);
		clrMsg.setChecked(false);
		irrelvnt = (CheckBox) findViewById(R.id.irrelavntCB);
		irrelvnt.setChecked(false);
		relavnt = (CheckBox) findViewById(R.id.relavntCB);
		relavnt.setChecked(false);
		untrust = (CheckBox) findViewById(R.id.untrustCB);
		untrust.setChecked(false);
		credble = (CheckBox) findViewById(R.id.credbleCB);
		credble.setChecked(false);
		bored = (CheckBox) findViewById(R.id.boreCB);
		bored.setChecked(false);
		entertn = (CheckBox) findViewById(R.id.entertnCB);
		entertn.setChecked(false);
		unique = (CheckBox) findViewById(R.id.uniqueCB);
		unique.setChecked(false);
		common = (CheckBox) findViewById(R.id.commonCB);
		common.setChecked(false);
		comments = (TextView) findViewById(R.id.commntTV);
		ratingbar=(RatingBar) findViewById(R.id.video_ratingBar);
		commntET=(EditText) findViewById(R.id.commentBox);
		submitBtn = (Button) findViewById(R.id.submitCmnt);
		
		commntET.setVisibility(View.GONE);
		submitBtn.setVisibility(View.GONE);
		
		
		Properties property=PropertyCaller.getInstance(ViewComments.this);
		String path=property.getProperty("connection-url");
		
		try {
			
			String result[]=new TaskLoadCommentsInfo().execute(videoId,user_id,session_id,path).get();
			String rateString=result[0];
			String checkString=result[1];
			String commentString=result[2];
			
			float rate=Float.parseFloat(rateString);
			ratingbar.setRating(rate);
			ratingbar.setIsIndicator(true);
			
			node=checkString.toCharArray();
			
			for(int i=0;i < node.length;i++){
				
				switch( i){
				case 0 : check(unclrMsg,i);
				case 1 : check(clrMsg,i);
				case 2 : check(irrelvnt,i);
				case 3 : check(relavnt,i);
				case 4 : check(untrust,i);
				case 5 : check(credble,i);
				case 6 : check(bored,i);
				case 7 : check(entertn,i);
				case 8 : check(unique,i);
				case 9 : check(common,i);
				
				
				}
				
					
			}
				/*if(node[i]=='1'){
					check[i].setChecked(true);
					Log.i("setChecked(true)", ""+node[i]);
				}else{
					
					Log.i("setChecked(false)", ""+node[i]);
				}*/
				
			
			if(commentString==null){
				comments.setText(commentString);
			}
			else{
				comments.setText(" No comments...");
			}
			
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	private void check(CheckBox checkBox , int i){
		if(node[i]=='1'){
			checkBox.setChecked(true);
			checkBox.setClickable(false);
			Log.i("setChecked(true)", ""+node[i]);
		}else{
			checkBox.setChecked(false);
			checkBox.setClickable(false);
			Log.i("setChecked(false)", ""+node[i]);
		}
	}

}

class TaskLoadCommentsInfo extends AsyncTask<String, Void, String[]>{

	String response = "";
	String[] output;
	
	@Override
	protected String[] doInBackground(String... params) {
		
try {
			
			String URL=params[3]+"LoadCommentsInfo";
			Log.i("URL", URL);
	         
			JsonObject commentJob=new JsonObject();		
			commentJob.addProperty("video_id", params[0]);
			commentJob.addProperty("user_id", params[1]);
			commentJob.addProperty("session_id", params[2]);
			
			
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
           
           output=new String[3];
           
           JsonParser par = new JsonParser();
           JsonObject jObj = (JsonObject)par.parse(response);
           JsonElement elem1=jObj.get("rate");
           JsonElement elem2=jObj.get("chk_comment");
           JsonElement elem3=jObj.get("comment");
           
          output[0]=elem1.getAsString();
          output[1]=elem2.getAsString();
          output[2]=elem3.getAsString();
        	 
                   
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






