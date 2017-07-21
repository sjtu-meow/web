package me.sjtumeow.meow.model.form;

public class ReportForm {
    protected Long itemId;
    protected Integer itemType;
    protected String reason;

    public Long getItemId() {
        return itemId;
    }

    public Integer getItemType() {
        return itemType;
    }

    public String getReason() {
        return reason;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public void setItemType(Integer itemType) {
        this.itemType = itemType;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
