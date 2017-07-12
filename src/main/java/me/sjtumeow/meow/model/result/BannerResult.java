package me.sjtumeow.meow.model.result;

import me.sjtumeow.meow.model.Banner;

public class BannerResult {
	
	protected Long id;
	protected Integer displayOrder;
	protected String image;
	protected Long itemId;
	protected Integer itemType;
	
	public BannerResult(Banner banner) {
		this.id = banner.getId();
		this.displayOrder = banner.getDisplayOrder();
		this.image = banner.getImage();
		this.itemId = banner.getItem().getId();
		this.itemType = banner.getItem().getType();
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}
	
	public String getImage() {
		return image;
	}
	
	public void setImage(String image) {
		this.image = image;
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
