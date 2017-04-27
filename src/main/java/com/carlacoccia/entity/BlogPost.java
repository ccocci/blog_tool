package com.carlacoccia.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Id;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;

/**
 * Created by Carla on 27.04.17.
 */

@Entity
public class BlogPost implements Serializable {
    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    @Column
    private String title;

    @Column
    private Date date;

    @Column
    private String text;

    public BlogPost() {
    }


    public BlogPost(String title,Date date, String text){
        this.title=title;
        this.date=date;
        this.text=text;
    }

    @JsonIgnore
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
