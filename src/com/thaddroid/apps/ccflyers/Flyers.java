package com.thaddroid.apps.ccflyers;

public class Flyers {
	int pages;			//how many pages for a flyer
	String src = new String();		//Image source for each page of the flyer
	String pdfSrc = new String();

	public Flyers(int p) {
		pages = p;
		src = "<p></p>";
		pdfSrc = "";
	}
	
	public void setPDFSrc(String s){
		pdfSrc =  s;
	}
	
	public String getPDFSrc(){
		return pdfSrc;
	}
	
	public void setImgSrc(String s){
		src =  s;
	}
	
	public String getImgSrc(){
		return src;
	}

}
