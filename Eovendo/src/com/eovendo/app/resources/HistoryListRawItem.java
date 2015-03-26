package com.eovendo.app.resources;

public class HistoryListRawItem {
	
	private String title;
	private String period;
	private int imageId1;
	private int imageId2;
	
		
	
	public HistoryListRawItem(String title, String period, int imageId1,
			int imageId2) {
		super();
		this.title = title;
		this.period = period;
		this.imageId1 = imageId1;
		this.imageId2 = imageId2;
		
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
	public int getImageId1() {
		return imageId1;
	}
	public void setImageId1(int imageId1) {
		this.imageId1 = imageId1;
	}
	public int getImageId2() {
		return imageId2;
	}
	public void setImageId2(int imageId2) {
		this.imageId2 = imageId2;
	}

	@Override
	public String toString() {
		return "HistoryListRawItem [title=" + title + ", period=" + period
				+ ", imageId1=" + imageId1 + ", imageId2=" + imageId2 + "]";
	}
	
	
	
	

}
