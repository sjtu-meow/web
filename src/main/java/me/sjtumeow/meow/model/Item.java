package me.sjtumeow.meow.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Item extends BaseEntity {
    
	private static final long serialVersionUID = 1L;
	
	public static int ITEM_TYPE_MOMENT = 0;
	public static int ITEM_TYPE_ARTICLE = 1;
	public static int ITEM_TYPE_QUESTION = 2;
	public static int ITEM_TYPE_ANSWER = 3;
	
	@Id
    @GeneratedValue(strategy = GenerationType.TABLE)
	@Column(nullable = false)
    Long id;
	
	@Column(nullable = false)
	Long type;

    @OneToOne
    @JoinColumn
    Profile profile;
}
