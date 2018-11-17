package com.jabizparda.cartools.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sotode on 3/4/2018.
 */
@Entity(tableName ="type_tbl")
public class TypeData {

    @PrimaryKey
    @ColumnInfo(name = "uid")
    @SerializedName("fldPkDetailGroup")
    private int id;

    @ColumnInfo(name = "type")
    @SerializedName("fldNameDetailGroup")
    private String type;
    @SerializedName("selectState")
    int stateSelect;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStateSelect() {
        return stateSelect;
    }

    public void setStateSelect(int stateSelect) {
        this.stateSelect = stateSelect;
    }
}
