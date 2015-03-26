package com.eovendo.app.resources;

import java.util.List;

import com.eovendo.app.HistoryFragment;
import com.eovendo.app.R;
import com.eovendo.app.VideoPlayerActivity;
import com.eovendo.app.ViewComments;
import com.eovendo.app.WatchFragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class HistoryListAdapter extends ArrayAdapter<HistoryListRawItem> {
	
	Context context;

	public HistoryListAdapter(Context context, int textViewResourceId,
			List<HistoryListRawItem> objects) {
		super(context, textViewResourceId, objects);
		
		this.context=context;
		
	}
	
	private class ViewHolder{
		
		ImageView imageview1;
		ImageView imageview2;
		TextView titleTxt;
		TextView periodTxt;
		
			
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		final int index=position;
		ViewHolder holder=null;
		HistoryListRawItem rawItem=getItem(position);
		
		LayoutInflater inflater=(LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		
		if(convertView==null){
			
			convertView = inflater.inflate(R.layout.history_list_item, null);
			holder=new ViewHolder();
			holder.periodTxt=(TextView) convertView.findViewById(R.id.hist_time);
			holder.titleTxt=(TextView) convertView.findViewById(R.id.hist_vidTitle);		
			holder.imageview1=(ImageView) convertView.findViewById(R.id.replay_image);
			holder.imageview2=(ImageView) convertView.findViewById(R.id.commnt_image);
			
			convertView.setTag(holder);
			
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		
		holder.periodTxt.setText(rawItem.getPeriod());
		holder.titleTxt.setText(rawItem.getTitle());
		holder.imageview1.setId(rawItem.getImageId1());
		holder.imageview2.setId(rawItem.getImageId2());
		
		
		
		holder.imageview1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				HistoryFragment f=new HistoryFragment();
				
				String path=HistoryFragment.historyinfoList.get(index).getVideoPath();
				String fullPath=HistoryFragment.property.getProperty("video-path")+path;
				String videoId=HistoryFragment.historyinfoList.get(index).getVideoId();
				
				Intent intent = new Intent(v.getContext(), VideoPlayerActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.putExtra("video_path", fullPath);
				intent.putExtra("session_id", HistoryFragment.session_id);
				intent.putExtra("user_id", HistoryFragment.user_id);
				intent.putExtra("video_id", videoId);
				intent.putExtra("class_name", "HistoryListAdapter");
				v.getContext().startActivity(intent);
				
				Log.i("HistoryListAdater params::::::", f.getSessionId()+" "+f.getUserId()+" "+videoId);
				
			}
		});
		
		holder.imageview2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				HistoryFragment f=new HistoryFragment();
				
				Toast.makeText(context, "click imageview", 2000).show();
				
				String videoId=HistoryFragment.historyinfoList.get(index).getVideoId();
				
				Intent intent = new Intent(v.getContext(), ViewComments.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.putExtra("session_id", HistoryFragment.session_id);
				intent.putExtra("user_id",  HistoryFragment.user_id);
				intent.putExtra("video_id", videoId);
				v.getContext().startActivity(intent);
				
				Log.i("HistoryListAdater params", f.getSessionId()+" "+f.getUserId()+" "+videoId);
				
			}
		});
		
		
		return convertView;
	}
	

}
