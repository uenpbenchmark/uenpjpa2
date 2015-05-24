package com.uenpjpa1;

import java.util.HashSet;

import javax.persistence.Entity;

import com.google.appengine.api.datastore.Key;

import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToMany;

import org.datanucleus.api.jpa.annotations.PersistenceAware;
import org.datanucleus.store.types.sco.simple.Set;

@Entity
public class User {
    String name;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Key key;
    
    @OneToMany(targetEntity=Article.class, mappedBy="parent", fetch=FetchType.LAZY)
    private HashSet<Article> children = new HashSet<Article>();
    
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Key getKey() {
		return key;
	}

	public HashSet<Article> getChildren() {
		return children;
	}

	public void addChild(Article article) {
		this.children.add(article);
	}
    
}