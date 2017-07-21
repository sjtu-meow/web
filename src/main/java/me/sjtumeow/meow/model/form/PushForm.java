package me.sjtumeow.meow.model.form;

public class PushForm {
    protected Long itemId;
    protected Integer itemType;
    protected String text;

    public Long getItemId() {
        return itemId;
    }

    public Integer getItemType() {
        return itemType;
    }

    public String getText() {
        return text;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public void setItemType(Integer itemType) {
        this.itemType = itemType;
    }

    public void setText(String text) {
        this.text = text;
    }
}
