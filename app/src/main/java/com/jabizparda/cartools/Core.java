package com.jabizparda.cartools;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.jabizparda.cartools.room.AgantData;
import com.jabizparda.cartools.room.AppDatabase;
import com.jabizparda.cartools.room.BoughtToolsData;
import com.jabizparda.cartools.room.CarData;
import com.jabizparda.cartools.room.CategoryData;
import com.jabizparda.cartools.room.CityData;
import com.jabizparda.cartools.room.MaintenceDataSAvingVErsion;
import com.jabizparda.cartools.room.MaintenceDoa;
import com.jabizparda.cartools.room.ProvinceData;
import com.jabizparda.cartools.room.TypeData;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * Created by Sotode on 2/28/2018.
 */

public class Core {
    Context context;

    public  Core(Context context){
        this.context=context;
    }



    public void GetProductbyType(JsonObject jsonObject, FutureCallback<JsonObject> callback){
        Ion.with(context)
                .load("POST","http://91.92.190.54:1095/api/friend/GetProductbyType")
                .setTimeout(30000)
                .setJsonObjectBody(jsonObject)
                .asJsonObject()
                .setCallback(callback);

    }

    public void GetDetailGroup(JsonObject jsonArray, FutureCallback<JsonObject> callback){
        Ion.with(context)
                .load("POST","http://91.92.190.54:1095/api/friend/GetDetailGroup")
                .setTimeout(30000)
                .setJsonObjectBody(jsonArray)
                .asJsonObject()
                .setCallback(callback);

    }

    public void getAllCars(FutureCallback<JsonObject> callback){
        Ion.with(context)
                .load("GET","http://91.92.190.54:1095/api/friend/GetAllCar")
                .setTimeout(30000)
                .asJsonObject()
                .setCallback(callback);
    }


    public void getAllCity(int province,FutureCallback<JsonObject> callback){
        Ion.with(context)
                .load("GET","http://91.92.190.54:1095/api/friend/GetAllCity?province="+province)
                .setTimeout(30000)
                .asJsonObject()
                .setCallback(callback);
    }

    public void getAllProvince(FutureCallback<JsonObject> callback){
        Ion.with(context)
                .load("GET","http://91.92.190.54:1095/api/friend/AllProvince")
                .setTimeout(30000)
                .asJsonObject()
                .setCallback(callback);
    }

//    public void insertFirebase(JsonObject jsonObject, FutureCallback<JsonObject> callback){
//        Ion.with(context)
//                .load("POST","http://84.241.27.138:1095/api/friend/insertFirebase")
//                .setTimeout(30000)
//                .setJsonObjectBody(jsonObject)
//                .asJsonObject()
//                .setCallback(callback);
//
//    }
    private final static String persian_numbers[] = {"۰" , "۱" , "۲" ,"۳" , "۴" ,"۵" , "۶" , "۷"  , "۸" , "۹" };

    public static String toPersianStatic(String str) {
        String string = str;
        for(int i = 0 ; i < 10 ; i++){
            string = string.replace(i +"", persian_numbers[i]);
        }
        return string;
    }

    public void insetAllCategory(List<CategoryData> address) {
        AppDatabase.getAppDatabase(context.getApplicationContext()).categoryDoa().deleteAll();
        AppDatabase.getAppDatabase(context.getApplicationContext()).categoryDoa().insertAll(address);

    }

    public void insertAllCars(List<CarData> cars) {
        AppDatabase.getAppDatabase(context.getApplicationContext()).carDoa().deleteAll();
        AppDatabase.getAppDatabase(context.getApplicationContext()).carDoa().insertAll(cars);
    }

    public void insertAllProvince(List<ProvinceData> provinceDatas) {
        AppDatabase.getAppDatabase(context.getApplicationContext()).provinceDoa().deleteAll();
        AppDatabase.getAppDatabase(context.getApplicationContext()).provinceDoa().insertAll(provinceDatas);
    }

    public void insertCity(List<CityData> cityDatas) {
        AppDatabase.getAppDatabase(context.getApplicationContext()).cityDoa().deleteAll();
        AppDatabase.getAppDatabase(context.getApplicationContext()).cityDoa().insertAll(cityDatas);
    }

    public void insertAgant(AgantData agants) {
        AppDatabase.getAppDatabase(context.getApplicationContext()).agantDoa().insert(agants);
    }

    public void updateallCategory(JsonObject jsonObject,FutureCallback<JsonObject> callback) {
        Ion.with(context.getApplicationContext())
                .load("POST","http://91.92.190.54:1095/api/friend/GetAllGroup")
                .setTimeout(30000)
                .setJsonObjectBody(jsonObject)
                .asJsonObject()
                .setCallback(callback);
    }

    public void insertAllType(List<TypeData> typeDatas) {
        AppDatabase.getAppDatabase(context.getApplicationContext()).typeDoa().deleteAll();
        AppDatabase.getAppDatabase(context.getApplicationContext()).typeDoa().insertAll(typeDatas);

    }

    public void insertallMaintence(List<MaintenceDataSAvingVErsion> maintence) {
        AppDatabase.getAppDatabase(context.getApplicationContext()).maintenceDoa().deleteAll();
        AppDatabase.getAppDatabase(context.getApplicationContext()).maintenceDoa().insertAll(maintence);
    }

    public void insertToolToBasket(BoughtToolsData tools) {
        AppDatabase.getAppDatabase(context.getApplicationContext()).boughtToolsDoa().insertTools(tools);
    }

    public void deleteToolFromBasket(String codeTools) {
        AppDatabase.getAppDatabase(context.getApplicationContext()).boughtToolsDoa().deleteOnce(codeTools);
    }
    public void deletAllBasket() {
        AppDatabase.getAppDatabase(context.getApplicationContext()).boughtToolsDoa().deleteAll();
    }

    public Integer getBasketCount() {
      return  AppDatabase.getAppDatabase(context.getApplicationContext()).boughtToolsDoa().countBought();
    }

    public void setNewCountProduct(String code, int countCahnged) {
          AppDatabase.getAppDatabase(context.getApplicationContext()).boughtToolsDoa().changeCountValue(code,countCahnged);
    }

    public List<BoughtToolsData> getAllbasket() {
        return  AppDatabase.getAppDatabase(context.getApplicationContext()).boughtToolsDoa().getAll();
    }
    public void updateType(JsonObject jsonObject,FutureCallback<JsonObject> callback) {
        Ion.with(context)
                .load("POST","http://91.92.190.54:1095/api/friend/GetDetailGroup")
                .setTimeout(30000)
                .setJsonObjectBody(jsonObject)
                .asJsonObject()
                .setCallback(callback);

    }

    public void insertIntoOrder(JsonObject jsonObject,FutureCallback<JsonObject> callback) {
        Ion.with(context)
                .load("POST","http://91.92.190.54:1095/api/friend/InsertIntoOrder")
                .setTimeout(30000)
                .setJsonObjectBody(jsonObject)
                .asJsonObject()
                .setCallback(callback);

    }

    public void SelectEndOrder(JsonObject jsonObject,FutureCallback<JsonObject> callback) {
        Ion.with(context)
                .load("POST","http://91.92.190.54:1095/api/friend/SelectEndOrder")
                .setTimeout(30000)
                .setJsonObjectBody(jsonObject)
                .asJsonObject()
                .setCallback(callback);

    }

    public void createAgent(JsonObject jsonObject, FutureCallback<JsonObject> callback){
        Ion.with(context)
                .load("POST","http://91.92.190.54:1095/api/friend/CreateAgent")
                .setTimeout(30000)
                .setJsonObjectBody(jsonObject)
                .asJsonObject()
                .setCallback(callback);

    }

    public void saveRegisterdCode(int value) {
        SharedPreferences preferences;
        preferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        preferences.edit().putInt("RegisterdDriver", value).commit();
    }

    public int getRegisterdCode() {
        SharedPreferences preferences;
        preferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        return preferences.getInt("RegisterdDriver", 0);
    }

    public void saveCredit(Boolean value) {
        SharedPreferences preferences;
        preferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        preferences.edit().putBoolean("credit", value).commit();
    }

    public boolean getCredit() {
        SharedPreferences preferences;
        preferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        return preferences.getBoolean("credit", false);
    }

    public void  tryAgainSMS(String phN,String rndm,FutureCallback<JsonObject> callback){
        Ion.with(context.getApplicationContext())
                .load("GET", "http://91.92.190.54:1095/api/friend/SendSms?number="+phN+"&note="+rndm)
                .setTimeout(30000)
                .asJsonObject()
                .setCallback(callback);
    }
}
