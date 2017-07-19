package me.sjtumeow.meow.model.form;

public class UpdateBannerForm {
    protected Integer displayOrder;
    protected String image;
    protected Long itemId;
    protected Integer itemType;

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public String getImage() {
        return image;
    }

    public Long getItemId() {
        return itemId;
    }

    public Integer getItemType() {
        return itemType;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public void setItemType(Integer itemType) {
        this.itemType = itemType;
    }
}
