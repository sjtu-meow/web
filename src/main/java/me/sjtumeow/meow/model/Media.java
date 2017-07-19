package me.sjtumeow.meow.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Media extends BaseEntity {

    private static final long serialVersionUID = 1L;

    public enum MediaType {
        Image, Video
    }

    @Id
    @GeneratedValue
    Long id;

    MediaType type;
    String url;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(nullable = false)
    Moment moment;

    public Media() {
    }

    public Media(MediaType type, String url, Moment moment) {
        this.type = type;
        this.url = url;
        this.moment = moment;
    }

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Moment getMoment() {
        return moment;
    }

    public void setMoment(Moment moment) {
        this.moment = moment;
    }
}
