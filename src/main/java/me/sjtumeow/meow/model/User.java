package me.sjtumeow.meow.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import com.fasterxml.jackson.annotation.JsonView;
import javax.persistence.*;

@Entity(name = "users") // table name
public class User extends BaseEntity {
    
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue
    @JsonView(Views.Public.class)
    @Column(nullable = false)
    private Long id;
	
	@JsonView(Views.Public.class)
    @Column(unique = true, nullable = false)
    private String phone;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @OneToOne(mappedBy = "user")
    @JsonBackReference
    private Profile profile;
    
    public User(String phone, String password) {
    	this.phone = phone;
    	this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public static class Views {
        public interface Public { }
    }
}

