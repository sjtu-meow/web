package me.sjtumeow.meow.model;

import com.fasterxml.jackson.databind.ser.Serializers;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Banner extends BaseEntity {
    @Id
    Long id;

    String url;

    @OneToOne
    @JoinColumn
    Item item;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
