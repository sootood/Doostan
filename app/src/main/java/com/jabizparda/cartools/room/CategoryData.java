package com.jabizparda.cartools.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 123 on 3/4/2018.
 */
@Entity(tableName = "category_tbl")
public class CategoryData  {

    @PrimaryKey
    @ColumnInfo(name = "uid")
    @SerializedName("fldPkGroup")
    private int id;

    @ColumnInfo(name = "car_cat")
    @SerializedName("fldGroupName")

    private String carCategory;
    @SerializedName("selectState")
    int stateSelect;

    public String getCarCategory() {
        return carCategory;
    }

    public void setCarCategory(String carCategory) {
        this.carCategory = carCategory;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStateSelect(int stateSelect) {
        this.stateSelect = stateSelect;
    }

    public int getStateSelect() {
        return stateSelect;
    }
}
