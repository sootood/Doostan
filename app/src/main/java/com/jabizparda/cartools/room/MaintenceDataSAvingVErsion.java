package com.jabizparda.cartools.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 123 on 3/11/2018.
 */
@Entity(tableName ="tools")
public class MaintenceDataSAvingVErsion {

    @ColumnInfo(name = "uid")
    @SerializedName("fldPkGroup")
    private int id;

    @ColumnInfo(name = "name")
    @SerializedName("fldGroupName")
    private String carCategory;
    @ColumnInfo(name = "category")
    @SerializedName("fldCarName")
     private  String typeMaintence;

    @ColumnInfo(name = "codeCat")
    @SerializedName("flProductCode")
    private String codeMaintence;

    @ColumnInfo(name = "fldNameProduct")
    @SerializedName("fldNameProduct")
    private String nameMaintence;

    @ColumnInfo(name = "fldCountInPack")
    @SerializedName("fldCountInPack")
   private int countMaintence;

    @ColumnInfo(name = "fldPrice")
    @SerializedName("fldPrice")
    private String pricemaintence;

    @ColumnInfo(name = "fldImage")
    @SerializedName("fldImage")
    private String imageMaintence;

    @SerializedName("selectState")
    private int stateSelect;
    @PrimaryKey
    @ColumnInfo(name = "fldPkProduct")
    @SerializedName("fldPkProduct")
    private int pkMaintence;

    @SerializedName("counter_m")
    private int counterMaintence;

    public int getCountMaintence() {
        return countMaintence;
    }

    public void setCountMaintence(int countMaintence) {
        this.countMaintence = countMaintence;
    }

    public String getCarCategory() {
        return carCategory;
    }

    public void setCarCategory(String carCategory) {
        this.carCategory = carCategory;
    }

    public String getCodeMaintence() {
        return codeMaintence;
    }

    public void setCodeMaintence(String codeMaintence) {
        this.codeMaintence = codeMaintence;
    }

    public String getImageMaintence() {
            if (imageMaintence==null)
                return null;
        else
            return imageMaintence;
    }

    public void setImageMaintence(String imageMaintence) {
        this.imageMaintence = imageMaintence;
    }

    public String getNameMaintence() {
        return nameMaintence;
    }

    public void setNameMaintence(String nameMaintence) {
        this.nameMaintence = nameMaintence;
    }

    public String getPricemaintence() {
        return pricemaintence;
    }

    public void setPricemaintence(String pricemaintence) {
        this.pricemaintence = pricemaintence;
    }

    public String getTypeMaintence() {
        return typeMaintence;
    }

    public void setTypeMaintence(String typeMaintence) {
        this.typeMaintence = typeMaintence;
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
    public void setPkMaintence(int pkMaintence) {
        this.pkMaintence = pkMaintence;
    }

    public int getPkMaintence() {
        return pkMaintence;
    }

    public int getCounterMaintence() {
        return counterMaintence;
    }

    public void setCounterMaintence(int counterMaintence) {
        this.counterMaintence = counterMaintence;
    }
}
