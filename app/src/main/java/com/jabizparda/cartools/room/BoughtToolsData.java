package com.jabizparda.cartools.room;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName ="bought_tbl")
public class BoughtToolsData {

    @PrimaryKey
    @ColumnInfo(name = "flProductCode")
//    @SerializedName("fldPkCar")
    private int code;

    @ColumnInfo(name = "fldNameProduct")
//    @SerializedName("fldCarName")
    private String name;

    @ColumnInfo(name = "fldPrice")
//    @SerializedName("fldCarName")
    private String price;

    @ColumnInfo(name = "fldCount")
//    @SerializedName("fldCarName")
    private String count;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
