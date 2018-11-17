package com.jabizparda.cartools.adapter;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 123 on 4/21/2018.
 */

public class NaqsheSokhtData implements Parcelable {

    @SerializedName("fldTitel")
    String titleTool;

    @SerializedName("fldPkDetailGroup")
    int codeNaqshe;

    @SerializedName("fldImage")
    String imageTool;

    protected NaqsheSokhtData(Parcel in) {

        titleTool=in.readString();
        imageTool= in.readString();
        codeNaqshe = in.readInt();

    }

    public static final Creator<NaqsheSokhtData> CREATOR = new Creator<NaqsheSokhtData>() {
        @Override
        public NaqsheSokhtData createFromParcel(Parcel in) {
            return new NaqsheSokhtData(in);
        }

        @Override
        public NaqsheSokhtData[] newArray(int size) {
            return new NaqsheSokhtData[size];
        }
    };

    public String getImageTool() {
        return imageTool;
    }

    public void setImageTool(String imageTool) {
        this.imageTool = imageTool;
    }

    public String getTitleTool() {
        return titleTool;
    }

    public void setTitleTool(String titleTool) {
        this.titleTool = titleTool;
    }

    public void setCodeNaqshe(int codeNaqshe) {
        this.codeNaqshe = codeNaqshe;
    }

    public int getCodeNaqshe() {
        return codeNaqshe;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(titleTool);
        parcel.writeString(imageTool);
        parcel.writeInt(codeNaqshe);
    }
}
