package com.jabizparda.cartools;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 123 on 3/11/2018.
 */

public class BasketData implements Parcelable {

    @SerializedName("fldNameProduct")
    String choosenName;
    @SerializedName("flProductCode")
    String choosenCode;
    @SerializedName("fldPrice")
    String choosenPrice;
    @SerializedName("fldCount")
    String choosenCountMaintence;

    @SerializedName("selectState")
    int stateSelect;

    BasketData(String code, String name, String price,String count){
        this.choosenCode = code;
        this.choosenName = name;
        this.choosenPrice = price;
        this.choosenCountMaintence = count;
    }

    protected BasketData(Parcel in) {

        choosenCode= in.readString();
        choosenName=in.readString();
        choosenPrice=in.readString();
        choosenCountMaintence= in .readString();


    }

    public static final Creator<BasketData> CREATOR = new Creator<BasketData>() {
        @Override
        public BasketData createFromParcel(Parcel in) {
            return new BasketData(in);
        }

        @Override
        public BasketData[] newArray(int size) {
            return new BasketData[size];
        }
    };

    public String getChoosenCode() {
        return choosenCode;
    }

    public String getChoosenName() {
        return choosenName;
    }

    public String getChoosenPrice() {
        return choosenPrice;
    }

    public void setChoosenCode(String choosenCode) {
        this.choosenCode = choosenCode;
    }

    public void setChoosenName(String choosenName) {
        this.choosenName = choosenName;
    }

    public void setChoosenPrice(String choosenPrice) {
        this.choosenPrice = choosenPrice;
    }

    public String getChoosenCountMaintence() {
        return choosenCountMaintence;
    }

    public void setChoosenCountMaintence(String choosenCountMaintence) {
        this.choosenCountMaintence = choosenCountMaintence;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(choosenPrice);
        parcel.writeString(choosenCode);
        parcel.writeString(choosenName);
        parcel.writeString(choosenCountMaintence);

    }


}
