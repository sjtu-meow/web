package me.sjtumeow.meow.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
public class Moment extends Item {

    private static final long serialVersionUID = 1L;

    public Moment() {
        type = Item.ITEM_TYPE_MOMENT;
    }

    String content;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "moment")
    Set<Media> medias;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Set<Media> getMedias() {
        return medias;
    }

    public void setMedias(Set<Media> medias) {
        this.medias = medias;
    }
}
