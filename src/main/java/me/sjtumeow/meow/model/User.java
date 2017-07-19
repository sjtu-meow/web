package me.sjtumeow.meow.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Set;

import javax.persistence.*;

@Entity
public class User extends BaseEntity {
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(nullable = false)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String phone;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Boolean isAdmin = false;
    
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "user")
    private Profile profile;
    
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<Favorite> favorite;
    
    public User() {}
    
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
    
    public Boolean getIsAdmin() {
		return isAdmin;
	}
    
    public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

	public Set<Favorite> getFavorite() {
		return favorite;
	}
	
	public void setFavorite(Set<Favorite> favorite) {
		this.favorite = favorite;
	}
	
}

