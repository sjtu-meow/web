package me.sjtumeow.meow.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Moment extends Item {

    private static final long serialVersionUID = 1L;

    public Moment() {
        type = Item.ITEM_TYPE_MOMENT;
    }

    @Column(nullable = false)
    String content;

    @OneToMany(mappedBy = "moment")
    List<Media> medias = new ArrayList<>();

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Media> getMedias() {
        return medias;
    }

    public void setMedias(List<Media> medias) {
        this.medias = medias;
    }

    public void addMedia(Media media) {
        this.medias.add(media);
    }
}
