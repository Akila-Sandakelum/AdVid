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
import com.eovendo.app.resources.PropertyCreater;
import com.eovendo.app.resources.VideoListAdapter;
import com.eovendo.app.resources.VideoListRawitem;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class WatchFragment extends Fragment {

	private PropertyCreater creater;
	private Properties p;
	private Context context;
	private VideoListAdapter adapter;
	private List<VideoListRawitem> rawItems;
	private ListView listview;
	public static ArrayList<UserVideo> videoinfolist;
	public static String vPath;
	public static String session_id;
	public static String user_id;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.video_listview, container,
				false);

		session_id = getArguments().getString("session_id");
		int userID=getArguments().getInt("user_id");
		user_id = Integer.toString(getArguments().getInt("user_id"));
		
		
		Log.i("Inside watch fragment", "watch fragment called");
		Log.i("results", ""+session_id+" "+user_id+" "+userID);

		listview = (ListView) view.findViewById(R.id.videio_list);
		rawItems = new ArrayList<VideoListRawitem>();

		context = getActivity().getApplicationContext();
		creater = new PropertyCreater(context);
		p = creater.getProperties("config.properties");
		String connURL = p.getProperty("connection-url");
		vPath=p.getProperty("video-path");

		try {

			videoinfolist=new TaskLoadVideos().execute(user_id, session_id, connURL).get();
			
			for(int i=0;i<videoinfolist.size();i++){
				VideoListRawitem rowItem = new VideoListRawitem(R.drawable.thumbnail,videoinfolist.get(i).getAuthor(), videoinfolist.get(i).getTitle(),videoinfolist.get(i).getDescription());				
				rawItems.add(rowItem);
				
			}

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * Toast.makeText(context, "Hello toast!", Toast.LENGTH_LONG).show();
		 * Toast.makeText(context, "WatchFrag "+session_id+" "+user_id+" end",
		 * 2000).show();
		 */

		adapter = new VideoListAdapter(context, R.layout.video_item, rawItems);
		//adapter.areAllItemsEnabled();
		
		listview.setAdapter(adapter);
		
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
				Toast.makeText(view.getContext(), "Clicked triggered", 2000).show();
				 Intent intent = new Intent(view.getContext(), PlayVideo.class);
				 String path=videoinfolist.get(position).getVideoPath();
				 String fullPath=vPath+path;
				 intent.putExtra("video_path", fullPath);
				 startActivity(intent);
			}
			
		});
		
	
		return view;
	}

}

class TaskLoadVideos extends AsyncTask<String, Void, ArrayList<UserVideo>> {

	String response = "";
	String output[];

	@Override
	protected ArrayList<UserVideo> doInBackground(String... params) {
		// Toast.makeText(this, "username"+params[0],Toast.LENGTH_LONG).show();
		try {

			JsonObject login = new JsonObject();
			login.addProperty("user_id", params[0]);
			login.addProperty("session_id", params[1]);

			//Log.i("output..........", login.toString());

			String URL = params[2] + "LoadVideoList";

			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(URL);

			httpPost.setEntity(new StringEntity(login.toString()));
			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-type", "application/json");
			HttpResponse httpResponse = httpClient.execute(httpPost);

			Log.i("output..........", login.toString());
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

		ArrayList<UserVideo> videoInfo = new ArrayList<UserVideo>();
		for (JsonElement video : list) {
			UserVideo aVideo = myGson.fromJson(video, UserVideo.class);
			videoInfo.add(aVideo);
		}

		for (UserVideo v : videoInfo) {
			Log.i("video", v.toString());
		}

		return videoInfo;

	}

}
