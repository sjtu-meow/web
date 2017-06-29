package me.sjtumeow.meow.model;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import org.hibernate.annotations.Cascade;

@Entity
public class Moment extends Item {
    String content;

    @OneToMany
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
