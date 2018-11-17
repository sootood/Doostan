package com.jabizparda.cartools.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 123 on 4/15/2018.
 */
@Entity(tableName ="car_tbl")
public class CarData {

    @PrimaryKey
    @ColumnInfo(name = "uid")
    @SerializedName("fldPkCar")
    private int id;

    @ColumnInfo(name = "name")
    @SerializedName("fldCarName")
    private String type;

    @ColumnInfo(name = "car_id")
    int idCar;

    public void setId(int id) {
        this.id = id;
    }

    public void setIdCar(int idCar) {
        this.idCar = idCar;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public int getIdCar() {
        return idCar;
    }

    public String getType() {
        return type;
    }
}
