package com.jabizparda.cartools;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 123 on 2/14/2018.
 */

public class Tools implements Parcelable {

    @SerializedName("fldOnvancode")
    private String code;
    @SerializedName("fldAzNumber")
    private String image;
    @SerializedName("fldNumber")
    private String toolsName;
    @SerializedName("fldSaf")
    private String type;
    @SerializedName("peijTime")
    private String date;
    @SerializedName("fldTedad")
    private String count;

    public Tools() {
    }

    protected Tools(Parcel in) {
        code = in.readString();
        image = in.readString();
        toolsName = in.readString();
        type = in.readString();
        date = in.readString();
        count = in.readString();
    }

    public static final Parcelable.Creator<Tools> CREATOR = new Parcelable.Creator<Tools>() {
        @Override
        public Tools createFromParcel(Parcel in) {
            return new Tools(in);
        }

        @Override
        public Tools[] newArray(int size) {
            return new Tools[size];
        }
    };

    public String getcode() {
        return code;
    }

    public void setcode(String code) {
        this.code = code;
    }

    public String getimage() {
        return image;
    }

    public void setimage(String image) {
        this.image = image;
    }

    public String gettoolsName() {
        return toolsName;
    }

    public void settoolsName(String toolsName) {
        this.toolsName = toolsName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(code);
        dest.writeString(image);
        dest.writeString(toolsName);
        dest.writeString(type);
        dest.writeString(date);
        dest.writeString(count);
    }
}
