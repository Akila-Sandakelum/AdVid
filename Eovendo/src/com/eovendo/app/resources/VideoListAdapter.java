package com.eovendo.app.resources;

import java.util.List;

import com.eovendo.app.PlayVideo;
import com.eovendo.app.R;
import com.eovendo.app.VideoPlayerActivity;
import com.eovendo.app.WatchFragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class VideoListAdapter extends ArrayAdapter<VideoListRawitem> {
	
	Context context;

	public VideoListAdapter(Context context, int textViewResourceId,
			List<VideoListRawitem> objects) {
		super(context, textViewResourceId, objects);
		
		this.context=context;
	}
	
	private class ViewHolder{
		
		ImageView imageview;
		TextView titleTxt;
		TextView authorTxt;
		TextView descriptionTxt;
			
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	
		final int index=position;
		ViewHolder holder=null;
		VideoListRawitem rawItem=getItem(position);
		
		LayoutInflater inflater=(LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		
		if(convertView==null){
			
			convertView = inflater.inflate(R.layout.video_item, null);
			holder=new ViewHolder();
			holder.authorTxt=(TextView) convertView.findViewById(R.id.author);
			holder.titleTxt=(TextView) convertView.findViewById(R.id.name);
			holder.descriptionTxt=(TextView) convertView.findViewById(R.id.description);
			holder.imageview=(ImageView) convertView.findViewById(R.id.thumbnail);
			
			convertView.setTag(holder);
			
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		
		holder.authorTxt.setText(rawItem.getAuthor());
		holder.titleTxt.setText(rawItem.getTitle());
		holder.descriptionTxt.setText(rawItem.getDescription());
		holder.imageview.setImageResource(rawItem.getImageId());
		
		holder.imageview.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(v.getContext(), "Clicked", 2000).show();
								
				 Intent intent = new Intent(v.getContext(), VideoPlayerActivity.class);
				 intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				 String path=WatchFragment.videoinfolist.get(index).getVideoPath();
				 String fullPath=WatchFragment.vPath+path;
				 String videoId=WatchFragment.videoinfolist.get(index).getVideoId();
				 intent.putExtra("video_path", fullPath);
				 intent.putExtra("session_id", WatchFragment.session_id);
				 intent.putExtra("user_id", WatchFragment.user_id);
				 intent.putExtra("video_id", videoId);
				 intent.putExtra("class_name", "VideoListAdapter");
				 v.getContext().startActivity(intent);
				
			}
		});
			
		return convertView;
	}
	
	/*@Override
	public boolean areAllItemsEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public boolean isEnabled(int position) {
		// TODO Auto-generated method stub
		return true;
	}*/
}
