package me.sjtumeow.meow.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import org.hibernate.Hibernate;
import org.hibernate.id.enhanced.HiLoOptimizer;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Item extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    Long id;

    @OneToOne
    @JoinColumn
    Profile profile;
}
