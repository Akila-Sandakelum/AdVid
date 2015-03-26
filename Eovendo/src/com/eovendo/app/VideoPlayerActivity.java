package com.eovendo.app;

import java.io.IOException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.FrameLayout;
import android.widget.Toast;

public class VideoPlayerActivity extends Activity implements
		SurfaceHolder.Callback, MediaPlayer.OnPreparedListener,
		MediaPlayer.OnCompletionListener,
		VideoControllerView.MediaPlayerControl {
	
	SurfaceView videoSurface;
	MediaPlayer player;
	VideoControllerView controller;
	String videoPath,user_id,session_id,videoId,class_name;
	ProgressDialog pDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video_player);

		Intent intent=getIntent();
		videoPath=intent.getExtras().getString("video_path");
		session_id=intent.getExtras().getString("session_id");
		user_id=intent.getExtras().getString("user_id");
		videoId=intent.getExtras().getString("video_id");
		class_name=intent.getExtras().getString("class_name");
		
		videoSurface = (SurfaceView) findViewById(R.id.videoSurface);
		SurfaceHolder videoHolder = videoSurface.getHolder();
		videoHolder.addCallback(this);
		player = new MediaPlayer();
		controller = new VideoControllerView(this);
		
		pDialog= new ProgressDialog(VideoPlayerActivity.this);
		pDialog.setTitle("Video is Streaming");
		pDialog.setMessage("Buffering...");
		pDialog.setIndeterminate(false);
	    pDialog.setCancelable(false);
	    pDialog.show();

		try {
			player.setAudioStreamType(AudioManager.STREAM_MUSIC);
			//player.setDataSource(this, Uri.parse("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"));
			//player.setDataSource(this, Uri.parse("http://192.168.43.168:8080/small.mp4"));
			player.setDataSource(this, Uri.parse(videoPath));
			player.setOnPreparedListener(this);
			player.setOnCompletionListener(this);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		controller.show();
		return false;
	}

	// Implement VideoMediaController.MediaPlayerControl 
	@Override
	public void start() {
		
		player.start();

	}

	@Override
	public void pause() {
		
		player.pause();

	}

	@Override
	public int getDuration() {
		
		return player.getDuration();
	}

	@Override
	public int getCurrentPosition() {
		
		return player.getCurrentPosition();
	}

	@Override
	public void seekTo(int pos) {
		
		player.seekTo(pos);

	}

	@Override
	public boolean isPlaying() {
		
		return player.isPlaying();
	}

	@Override
	public int getBufferPercentage() {
		
		return 0;
	}

	@Override
	public boolean canPause() {
		
		return true;
	}

	@Override
	public boolean canSeekBackward() {
		
		return true;
	}

	@Override
	public boolean canSeekForward() {
		
		return true;
	}

	@Override
	public boolean isFullScreen() {
		
		return false;
	}

	@Override
	public void toggleFullScreen() {
		

	}
	// End VideoMediaController.MediaPlayerControl
	

	// Implement SurfaceHolder.Callback
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		player.setDisplay(holder);
		player.prepareAsync();

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		

	}
	// End SurfaceHolder.Callback

	// Implement MediaPlayer.OnPreparedListener

	@Override
	public void onPrepared(MediaPlayer mp) {
		pDialog.dismiss();
		controller.setMediaPlayer(this);
		controller.setAnchorView((FrameLayout) findViewById(R.id.videoSurfaceContainer));
		player.start();

	}
	// End MediaPlayer.OnPreparedListener
	
	
	// Implement MediaPlayer.OnCompletionListener
	
	@Override
	public void onCompletion(MediaPlayer mp) {
		Log.i("MSG", "successfully finished");
		Toast.makeText(VideoPlayerActivity.this, "Video is finished", 2000).show();
		if(class_name.equals("VideoListAdapter")){
		Intent intent = new Intent(VideoPlayerActivity.this, CommentVideo.class);
		intent.putExtra("session_id", session_id);
		intent.putExtra("user_id", user_id);
		intent.putExtra("video_id", videoId);
		startActivity(intent);
		}
	}

	// End MediaPlayer.OnCompletionListener

	
}