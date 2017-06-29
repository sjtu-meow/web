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
}
