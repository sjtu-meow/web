package me.sjtumeow.meow.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Media {
    enum MediaType {Image, Video}

    @Id
    Long id;

    MediaType type;
    String thumbnail, url;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MediaType getType() {
        return type;
    }

    public void setType(MediaType type) {
        this.type = type;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}