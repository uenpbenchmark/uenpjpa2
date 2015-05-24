package com.uenpjpa1;

import java.util.HashSet;

import javax.persistence.Entity;

import com.google.appengine.api.datastore.Key;

import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceProperty;
import javax.persistence.OneToMany;

@Entity
public class Article {
    String name;
    String text;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Key key;
    
    @ManyToOne(fetch=FetchType.LAZY, targetEntity=User.class)
    //@OneToMany(targetEntity=Comment.class, mappedBy="parent", fetch=FetchType.LAZY)
    //private HashSet<Comment> children = new HashSet<Comment>();
    
    private User parent;
    
	public Key getKey() {
		return key;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public User getParent() {
		return parent;
	}
	public void setParent(User parent) {
		this.parent = parent;
	}
    
}