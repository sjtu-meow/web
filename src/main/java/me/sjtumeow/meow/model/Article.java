package me.sjtumeow.meow.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
public class Article extends Item {
    String title, summary, readCount;

    @OneToOne(mappedBy = "item")
    @JsonBackReference
    Banner banner;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getReadCount() {
        return readCount;
    }

    public void setReadCount(String readCount) {
        this.readCount = readCount;
    }

    public Banner getBanner() {
        return banner;
    }

    public void setBanner(Banner banner) {
        this.banner = banner;
    }
}
