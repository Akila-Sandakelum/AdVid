package com.eovendo.app;

import java.io.IOException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class PlayVideo extends Activity {
	
	VideoView videoview;
	ProgressDialog pDialog;
	String videoPath;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.play_video);
		
		Intent intent=getIntent();
		videoPath=intent.getExtras().getString("video_path");
		
		videoview=(VideoView) findViewById(R.id.playVideo_view);
		
		pDialog= new ProgressDialog(PlayVideo.this);
		pDialog.setTitle("Video Streaming");
		pDialog.setMessage("Buffering...");
		pDialog.setIndeterminate(false);
	    pDialog.setCancelable(false);
	    pDialog.show();
	    
	    try{
	    	
	    	MediaController controller= new MediaController(PlayVideo.this);
		    controller.setAnchorView(videoview);
		    controller.hide();
		    
		    Uri videoUri=Uri.parse(videoPath);
		    videoview.setMediaController(controller);
		    videoview.setVideoURI(videoUri);
	    	
	    }catch(Exception e){
	    	Log.i("Error", e.getMessage());
	    	e.printStackTrace();
	    }
	    
	    videoview.requestFocus();
	   
	    videoview.setOnPreparedListener(new OnPreparedListener() {
            // Close the progress bar and play the video
            public void onPrepared(MediaPlayer mp) {
            	
                try {
                	
                    pDialog.dismiss();
                    videoview.start();
                    //mp.pause();
                    
                   
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
               
            }
        });
	    
	    for(long i=0;i<=500;i++){
       	 videoview.pause();
       }
	    videoview.resume();
	   
	    videoview.setOnCompletionListener(new OnCompletionListener() {
			
			@Override
			public void onCompletion(MediaPlayer mp) {
				
				Toast.makeText(PlayVideo.this, "Video is finished", 2000).show();
				
			}
		});
	   // videoview.setClickable(false);
	    
		
	}

}

