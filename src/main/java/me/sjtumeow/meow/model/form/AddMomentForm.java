package me.sjtumeow.meow.model.form;

import java.util.List;

public class AddMomentForm {
    protected String content;
    protected List<MediaForm> medias;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<MediaForm> getMedias() {
        return medias;
    }

    public void setMedias(List<MediaForm> medias) {
        this.medias = medias;
    }
}
