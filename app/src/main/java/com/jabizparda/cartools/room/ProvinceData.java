package com.jabizparda.cartools.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 123 on 4/18/2018.
 */
@Entity(tableName ="province_tbl")
public class ProvinceData {

    @PrimaryKey
    @ColumnInfo(name = "uid")
    @SerializedName("fldPkProvince")
    private int id;

    @ColumnInfo(name = "name")
    @SerializedName("fldProvinceName")
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
