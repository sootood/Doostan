package com.jabizparda.cartools.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sotode on 4/5/2018.
 */
@Entity(tableName = "user_tbl")
public class User {

    @PrimaryKey
    @ColumnInfo(name = "uid")
    private int id;

    @ColumnInfo(name="phoneNumber")
    private String phoneNumber;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
