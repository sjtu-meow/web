package me.sjtumeow.meow.model.form;

import me.sjtumeow.meow.model.Media.MediaType;

public class MediaForm {
	protected MediaType type;
	protected String url;
	
	public MediaType getType() {
		return type;
	}
	
	public void setType(MediaType type) {
		this.type = type;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
}
