package me.sjtumeow.meow.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Banner extends BaseEntity {
    
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue
    @Column(nullable = false)
    Long id;
	
	Integer displayOrder;

    String image;

    @OneToOne
    @JoinColumn
    Item item;
    
    public Banner() {}
    
    public Banner(Integer displayOrder, String image, Item item) {
    	this.displayOrder = displayOrder;
    	this.image = image;
    	this.item = item;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

}
