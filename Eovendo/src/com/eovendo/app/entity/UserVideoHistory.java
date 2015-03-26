package com.eovendo.app.entity;

public class UserVideoHistory {
	
	private String title;
	private String period;
	private String videoPath;	
	private String videoId;
			
	public UserVideoHistory(String title, String period, String videoPath,
			String videoId) {
		super();
		this.title = title;
		this.period = period;
		this.videoPath = videoPath;
		this.videoId = videoId;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public String getVideoPath() {
		return videoPath;
	}
	public void setVideoPath(String videoPath) {
		this.videoPath = videoPath;
	}
	public String getVideoId() {
		return videoId;
	}
	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}

	@Override
	public String toString() {
		return "UserVideoHistory [title=" + title + ", period=" + period
				+ ", videoPath=" + videoPath + ", videoId=" + videoId + "]";
	}
	
	

}
