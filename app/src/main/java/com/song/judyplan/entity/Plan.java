package com.song.judyplan.entity;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

import java.util.Date;

/**
 * Created by Judy on 2017/4/22.
 */

@Entity
public class Plan implements Parcelable {
    @Id(autoincrement = true)
    private Long id;

    @NotNull private String text;
    private String content;
    private boolean isCompleted;
    @NotNull private Date date;
    @Generated(hash = 1665382446)
    public Plan(Long id, @NotNull String text, String content, boolean isCompleted,
            @NotNull Date date) {
        this.id = id;
        this.text = text;
        this.content = content;
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
    public String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.text);
        dest.writeString(this.content);
        dest.writeByte(isCompleted ? (byte) 1 : (byte) 0);
        dest.writeLong(date != null ? date.getTime() : -1);
    }

    private Plan(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.text = in.readString();
        this.content = in.readString();
        this.isCompleted = in.readByte() != 0;
        long tmpDate = in.readLong();
        this.date = tmpDate == -1 ? null : new Date(tmpDate);
    }

    public static final Parcelable.Creator<Plan> CREATOR = new Parcelable.Creator<Plan>() {
        public Plan createFromParcel(Parcel source) {
            return new Plan(source);
        }

        public Plan[] newArray(int size) {
            return new Plan[size];
        }
    };
}
