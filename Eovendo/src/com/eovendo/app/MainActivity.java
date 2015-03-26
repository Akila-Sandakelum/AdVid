package com.eovendo.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.eovendo.app.entity.LoginResponce;
import com.eovendo.app.entity.User;
import com.eovendo.app.resources.PropertyCreater;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private PropertyCreater creater;
	private Properties p;
	private Context context;
	RequestQueue queue;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 setContentView(R.layout.activity_main);
		queue = Volley.newRequestQueue(getApplicationContext());
		
		 propertyInit();
		
		 ActionBar actionBar = getActionBar();
		 /*actionBar.setDisplayShowHomeEnabled(false);
		 actionBar.setDisplayShowTitleEnabled(false);*/
		 actionBar.setDisplayHomeAsUpEnabled(true);
		 actionBar.hide();
		

		
		 final EditText nameET = (EditText) findViewById(R.id.usernameET);
		 
		 final EditText passET = (EditText) findViewById(R.id.passwordET);			
		 passET.measure(0, 0);
		 
		 int height = passET.getMeasuredHeight();
		 
		 
	
		 TextView text = (TextView) findViewById(R.id.appTheme);
		 Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/RobotoCondensed-Light.ttf");
		 text.setTypeface(tf);
		 
		 Button b=(Button) findViewById(R.id.signIn);
		 b.getLayoutParams().height=height;
	
		 b.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				final String name=nameET.getText().toString();
				final String password=passET.getText().toString();
				
				String values[]=new String[2];
				String  tag_string_req = "string_req";
				 
				String url = "http://192.168.43.14:8080/EovendoService/UserLogin";
				         
				  
				         
				StringRequest strReq = new StringRequest(Method.POST,
				                url, new Response.Listener<String>() {
				 
				                    @Override
				                    public void onResponse(String response) {
				                        Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
				                        Gson gson = new Gson();
				                        LoginResponce res = gson.fromJson(response, LoginResponce.class);
				                        String sessionId = res.getSessionId();
				                        int userId = res.getUserId();
				                        manageSession(sessionId, userId);
				                    }
				                }, new Response.ErrorListener() {
				 
				                    @Override
				                    public void onErrorResponse(VolleyError error) {
				                    	Toast.makeText(getApplicationContext(), error.toString() + error.getMessage() + error.getStackTrace(), Toast.LENGTH_LONG).show();
				       				 
				                    }
				                }){
					
					@Override
		            protected Map<String, String> getParams() {
		                Map<String, String> params = new HashMap<String, String>();
		                params.put("username", name);
		                params.put("password", password);
		                Log.i("Json string ", params.toString());
		                return params;
		            }
					
				};
				 
				// Adding request to request queue
				queue.add(strReq);
			
			}
		});
		
		
	}
	
	private void manageSession(String sessionId, int userId){
		if(userId == -1){
	  		
	  		new AlertDialog.Builder(getApplicationContext()).setTitle("Logging Failure").setMessage("Enter correct username or password").setNeutralButton("Ok", null).show();
	  		
	  	}else{
	  		
	  		Intent intent=new Intent(MainActivity.this, Dashboard.class);
	  		Log.i("Inside intent",userId+" "+sessionId );
	  		intent.putExtra("user_id", userId);
	  		intent.putExtra("session_id", sessionId);
			startActivity(intent);
	  		
	  	}
	}
	
	public void propertyInit(){
		context=this;
		creater=new PropertyCreater(context);
		p=creater.getProperties("config.properties");
		
		Toast.makeText(context, p.getProperty("connection-url"), 2000).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

class TaskUserLogin extends AsyncTask<String, Void, String[]>{
	
	final String URL="http://192.168.43.14:8080/UserLogin";
	String  response="";
	String output[];

	@Override
	protected String[] doInBackground(String... params) {
		
		Log.i("user..........", params[0]+"  pass "+params[1]);
		//Toast.makeText(this, "username"+params[0],Toast.LENGTH_LONG).show();
		try {
	         
			JSONObject object = new JSONObject();
			try {
				object.put("username", "akila");
				object.put("password", "akila123");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			 Log.i("output..........",object.toString());
			
			
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(URL);
           
            httpPost.setEntity(new StringEntity(object.toString()));
            httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-type", "application/json");
            HttpResponse httpResponse = httpClient.execute(httpPost);
            
            //Log.i("output..........",login.toString());
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
           JsonElement elem1=jObj.get("user_id");
           JsonElement elem2=jObj.get("session_id");
           
           output=new String[2];
           output[0]=elem1.getAsString();
           if(output[0].equals("-1")){
        	   output[1]="";
           }else{
        	   output[1]=elem2.getAsString(); 
           }
           
           
           
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

