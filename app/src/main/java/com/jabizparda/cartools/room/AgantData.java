package com.jabizparda.cartools.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 123 on 4/18/2018.
 */
@Entity(tableName ="agant_tbl")
public class AgantData  {

    @ColumnInfo(name = "uid")
    private  int id;

    @ColumnInfo(name = "firstName")
    @SerializedName("firstName")
    private String firstName;

    @ColumnInfo(name = "lastName")
    @SerializedName("lastName")
    private String lastName;

    @PrimaryKey
    @ColumnInfo(name = "nationalCode")
    @SerializedName("nationalCode")
    private String nationalCode;

    @ColumnInfo(name = "mobile")
    @SerializedName("mobile")
    private String mobile;

    @ColumnInfo(name = "tel")
    @SerializedName("tel")
    private String tel;

    @ColumnInfo(name = "provinceId")
    @SerializedName("provinceId")
    private String provinceId;

    @ColumnInfo(name = "city")
    @SerializedName("city")
    private String city;


    @ColumnInfo(name = "address")
    @SerializedName("address")
    private String address;

    @ColumnInfo(name = "postCode")
    @SerializedName("postCode")
    private String postCode;

    @ColumnInfo(name = "telegram")
    @SerializedName("telegram")
    private String telegram;

    @ColumnInfo(name = "transportName")
    @SerializedName("transportName")
    private String transportName;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }

    public String getNationalCode() {
        return nationalCode;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getTelegram() {
        return telegram;
    }

    public void setTelegram(String telegram) {
        this.telegram = telegram;
    }

    public String getTel() {
        return tel;
    }

    public void setTransportName(String transportName) {
        this.transportName = transportName;
    }

    public String getTransportName() {
        return transportName;
    }

}
