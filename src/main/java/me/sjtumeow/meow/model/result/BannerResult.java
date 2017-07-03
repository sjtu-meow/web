package me.sjtumeow.meow.model.result;

public class BannerResult {
	
	protected Long id;
	protected String url;
	protected Long itemId;
	protected Integer itemType;
	
	public BannerResult(Long id, String url, Long itemId, Integer itemType) {
		this.id = id;
		this.url = url;
		this.itemId = itemId;
		this.itemType = itemType;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public Long getItemId() {
		return itemId;
	}
	
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	
	public Integer getItemType() {
		return itemType;
	}
	
	public void setItemType(Integer itemType) {
		this.itemType = itemType;
	}
	
}
