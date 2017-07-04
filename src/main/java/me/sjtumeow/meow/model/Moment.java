package me.sjtumeow.meow.model;

import java.util.Collection;
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
    Collection<Media> medias;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Collection<Media> getMedias() {
        return medias;
    }

    public void setMedias(Collection<Media> medias) {
        this.medias = medias;
    }
}
