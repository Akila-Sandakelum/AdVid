package com.eovendo.app.entity;

public class UserVideo {
	
	private String author;
	private String title;
	private String videoPath;
	private String description;
	private String videoId;
	
	
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getVideoPath() {
		return videoPath;
	}
	public void setVideoPath(String videoPath) {
		this.videoPath = videoPath;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}	
	public String getVideoId() {
		return videoId;
	}
	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}
	
	@Override
	public String toString() {
		return "UserVideo [author=" + author + ", title=" + title
				+ ", videoPath=" + videoPath + ", description=" + description
				+ ", videoId=" + videoId + "]";
	}

	
	

}

