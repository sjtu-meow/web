package me.sjtumeow.meow.model;

import java.util.List;
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
    List<Media> medias;

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
}
