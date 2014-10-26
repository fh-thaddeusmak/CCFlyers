package com.thaddroid.apps.ccflyers;

public class SuperMarket {
	private String name;
	private int bannerResId;
	private int flyerPages;
	private String website[];
	private String date;
	private int startDate;
	private int endDate;
	private Flyers flyer;
	private boolean update;
	private int server;
	private String ccp;

	public SuperMarket(String n) {
		ccp="";
		flyer = new Flyers(1);
		name = n;
		website = new String[]{"NA", "NA","http://www.dushi.ca/flyer/Toronto/SupperMarket/","NA"};
		date="0";
		update=true;
		bannerResId = 0;
	}
	
	public void setBannerResId(int id){
		this.bannerResId = id;
	}
	
	public void setPages(int page){
		flyerPages = page;
	}
	
	public void setURL(String url, int i){
		website[i] = url;
	}
	
	public void setDate(String d){
		date = d;
	}
	
	public void setImgSrc(String s){
		flyer.setImgSrc(s);
	}
	
	public void setServer(int s){
		server = s;
	}
	
	public void setCCP(String c){
		ccp = c;
	}
	
	public String getCCP(){
		return ccp;
	}
	
	public int getServer(){
		return server;
	}
	
	public String getImgSrc(){
		return flyer.getImgSrc();
	}
	
	public String getURL(int i){
		return website[i];
	}
	
	public String getName(){
		return name;
	}
	
	public int getPages(){
		return flyerPages;
	}
	
	public int getBannerResId(){
		return bannerResId;
	}
	
	public String getDate(){
		return date;
	}
	
	public void setPDFSrc(String s){
		flyer.setPDFSrc(s);
	}
	
	public String getPDFSrc(){
		return flyer.getPDFSrc();
	}
	
	public boolean isUpdated(){
		return update;
	}
	
	public void Updated(boolean b){
		update = b;
	}
}
