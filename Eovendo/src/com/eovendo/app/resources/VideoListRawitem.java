package com.eovendo.app.resources;

public class VideoListRawitem {
	
	private String author;
	private int imageId;
	private String title;	
	private String description;
	
	public VideoListRawitem( int imageId,String author, String title,String description) {
		super();
		this.author = author;
		this.imageId = imageId;
		this.title = title;
		this.description = description;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getImageId() {
		return imageId;
	}

	public void setImageId(int imageId) {
		this.imageId = imageId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "VideoListRawitem [author=" + author + ", imageId=" + imageId
				+ ", title=" + title + ", description=" + description + "]";
	}
	
	
	

}
