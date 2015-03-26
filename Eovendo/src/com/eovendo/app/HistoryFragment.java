package com.eovendo.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.eovendo.app.entity.UserVideo;
import com.eovendo.app.entity.UserVideoHistory;
import com.eovendo.app.resources.HistoryListAdapter;
import com.eovendo.app.resources.HistoryListRawItem;
import com.eovendo.app.resources.PropertyCaller;
import com.eovendo.app.resources.VideoListAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ListView;
import android.widget.Toast;


public class HistoryFragment extends Fragment{
	
	private ListView listview;
	private List<HistoryListRawItem>rawItems;
	private HistoryListAdapter adapter;
	public static String session_id;
	public static String user_id;
	public static Properties property;
	public static ArrayList<UserVideoHistory>historyinfoList;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		final View view = inflater.inflate(R.layout.history_listview, container,
				false);

		session_id=getArguments().getString("session_id");
		int userId=getArguments().getInt("user_id");
		user_id=Integer.toString(userId);
		
		Context context = getActivity().getApplicationContext();  
		
		Log.i("HistryFra", "Inside History Fragment..");
		Toast.makeText(context, "Inside History Fragment", 2000).show();
		
		     
		Toast.makeText(context, "HistFrag "+session_id+" "+user_id+" end", 2000).show();
		
		property=PropertyCaller.getInstance(context);
		final String path=property.getProperty("connection-url");
		
		listview=(ListView) view.findViewById(R.id.history_list);
		rawItems=new ArrayList<HistoryListRawItem>();
		
		try {
			
			historyinfoList=new TaskLoadVideoHistory().execute(user_id,session_id,path).get();
			
			for(int i=0;i<historyinfoList.size();i++){
				HistoryListRawItem rowItem=new HistoryListRawItem(historyinfoList.get(i).getTitle(),
						historyinfoList.get(i).getPeriod(),R.drawable.replay, R.drawable.comments);
				
				rawItems.add(rowItem);
			}
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		adapter=new HistoryListAdapter(context, R.layout.history_list_item, rawItems);
		listview.setAdapter(adapter);
		
		return view;
	}

	
	
	
	public String getSessionId(){
		return this.session_id;
	}
	
	public String getUserId(){
		return this.user_id;
	}
}

class TaskLoadVideoHistory extends AsyncTask<String, Void, ArrayList<UserVideoHistory>>{

	String response = "";
	String output[];
	
	@Override
	protected ArrayList<UserVideoHistory> doInBackground(String... params) {
		
		try{
		
			Log.i("ASYNC VideoHistory", "Inside asynctask");
			
			JsonObject histryJob = new JsonObject();
			histryJob.addProperty("user_id", params[0]);
			histryJob.addProperty("session_id", params[1]);
			
			String URL = params[2] + "LoadHistoryInfo";
	
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(URL);
	
			httpPost.setEntity(new StringEntity(histryJob.toString()));
			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-type", "application/json");
			HttpResponse httpResponse = httpClient.execute(httpPost);
	
			Log.i("output..........", histryJob.toString());
			// Get hold of the response entity (-> the data):
	
			HttpEntity httpEntity = httpResponse.getEntity();
	
			InputStream is = httpEntity.getContent();
	
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			String line = null;
	
			while ((line = rd.readLine()) != null) // Read the response
													// line-by-line from the
													// bufferedReader
			{
				response += line;
	
			}
	
			Log.i("server output..........", response);
			// MainActivity.res.setText("server output  "+response);
			is.close();
	
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		JsonParser par = new JsonParser();
		JsonElement jObj = (JsonElement) par.parse(response);
		//JsonElement elem = jObj.get("videoList");
		JsonArray list = jObj.getAsJsonArray();

		Gson myGson = new Gson();

		ArrayList<UserVideoHistory> videoInfo = new ArrayList<UserVideoHistory>();
		for (JsonElement video : list) {
			UserVideoHistory aVideo = myGson.fromJson(video, UserVideoHistory.class);
			videoInfo.add(aVideo);
		}

		for (UserVideoHistory v : videoInfo) {
			Log.i("video", v.toString());
		}

		return videoInfo;
		}
	
}











