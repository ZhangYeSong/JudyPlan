package com.song.judyplan.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;

/**
 * Created by Judy on 2017/4/22.
 */

@Entity
public class Plan {
    @Id(autoincrement = true)
    private Long id;

    private String text;
    private boolean isCompleted;
    private Date date;
    @Generated(hash = 411032403)
    public Plan(Long id, String text, boolean isCompleted, Date date) {
        this.id = id;
        this.text = text;
        this.isCompleted = isCompleted;
        this.date = date;
    }
    @Generated(hash = 592612124)
    public Plan() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getText() {
        return this.text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public boolean getIsCompleted() {
        return this.isCompleted;
    }
    public void setIsCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }
    public Date getDate() {
        return this.date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    

}
