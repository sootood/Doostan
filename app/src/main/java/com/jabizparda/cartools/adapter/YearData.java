package com.jabizparda.cartools.adapter;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.orhanobut.logger.Logger;

/**
 * Created by Karo on 7/8/2017.
 */

public class YearData implements Parcelable {

    @SerializedName("fldNameDetailGroup")
    String yearName;
    @SerializedName("fldPkDetailGroup")
    int id;


    protected YearData(Parcel in) {
        yearName= in.readString();
        id= in.readInt();
    }


    public static final Creator<YearData> CREATOR = new Creator<YearData>() {
        @Override
        public YearData createFromParcel(Parcel in) {
            return new YearData(in);
        }

        @Override
        public YearData[] newArray(int size) {
            return new YearData[size];
        }
    };

    public String getYearName() {
        return yearName;
    }

    public void setYearName(String yearName) {
        this.yearName = yearName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(id);
        dest.writeString(yearName);

    }



}
