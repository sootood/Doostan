package com.jabizparda.cartools;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.jabizparda.cartools.adapter.MaintenceActivity;
import com.jabizparda.cartools.room.AppDatabase;
import com.jabizparda.cartools.room.CarData;
import com.jabizparda.cartools.room.CategoryData;
import com.jabizparda.cartools.room.MaintenceDataSAvingVErsion;
import com.jabizparda.cartools.room.TypeData;
import com.jabizparda.cartools.room.User;
import com.koushikdutta.async.future.FutureCallback;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Starter extends HappyCompatActivity {

//    @BindView(R.id.reMainStarter)
//    RelativeLayout raMainStarter;
    RelativeLayout remainStarter;
    Boolean firebase=false,maintance=false,cars=false;
    List<CategoryData> categoryDatas;
    List<CarData> carList;
    List<MaintenceDataSAvingVErsion> maintenceDatas;
    @BindView(R.id.llBtnLogin)
    LinearLayout llBtnLogin;
    @BindView(R.id.llregister)
    LinearLayout llRegistration;
    boolean creditInsert=false;
    @BindView(R.id.llregisterCode)
    LinearLayout llRegistrationCode;
    Core core;
    @BindView(R.id.numberEdit)
    EditText numberE;
    @BindView(R.id.codeE)
    EditText codeE;
    @BindView(R.id.lotties)
    LottieAnimationView lottieStarter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starter);
        remainStarter=(RelativeLayout) findViewById(R.id.reMainStarter);
        ButterKnife.bind(this);
        core= new Core(this);
        Logger.addLogAdapter(new AndroidLogAdapter());
        final ImageView imageView= (ImageView) findViewById(R.id.logo);
//        final ImageView slogan= (ImageView) findViewById(R.id.slogen);
        final Animation fadeoutAnim = AnimationUtils.loadAnimation(this, R.anim.fadein);
        imageView.startAnimation(fadeoutAnim);

        final Handler handlerfirst = new Handler();
        handlerfirst.postDelayed(new Runnable() {
            @Override
            public void run() {
                imageView.clearAnimation();
//                slogan.setVisibility(View.VISIBLE);
//                slogan.startAnimation(fadeoutAnim);
            }
        }, 2000);

        remainStarter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.d(AppDatabase.getAppDatabase(getApplicationContext()).maintenceDoa().getAll().size());
//
//                }else {
//                    showToast(Starter.this,"شما برای اولین بار است که وارد میشوید لطفا تا دریافت اطلاعات صبر کنید.");
//                }
            }
        });



            final Handler handler = new Handler();
            Logger.d(AppDatabase.getAppDatabase(getApplicationContext()).userDoa().getUser());
            if (!AppDatabase.getAppDatabase(getApplicationContext()).userDoa().getUser().isEmpty() && core.getCredit()==true){

                updateCars();
                updateCategory();
                adaptMaintence();
                if (!AppDatabase.getAppDatabase(getApplicationContext()).maintenceDoa().getAll().isEmpty()){
                handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                        Intent intent= new Intent(Starter.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();

                }
            }, 4000);}else {
                    updateCars();
                    updateCategory();
                    adaptMaintence();
                }
            }else{

                final Handler btnHandler = new Handler();
                btnHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        slogan.clearAnimation();
                        llBtnLogin.setVisibility(View.VISIBLE);
                        llBtnLogin.startAnimation(fadeoutAnim);
                    }
                }, 4000);
            }
//        if (AppDatabase.getAppDatabase(getApplicationContext()).maintenceDoa().getAll().size()==0){
//
//        }else{}

    }

    @OnClick(R.id.login)
    public void loginClicked(View view){
        if (view.getId()== R.id.login){
            final Animation fadeoutAnim = AnimationUtils.loadAnimation(this, R.anim.fade_out);
            llBtnLogin.startAnimation(fadeoutAnim);
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    llBtnLogin.setVisibility(View.GONE);
                    llRegistration.setVisibility(View.VISIBLE);
                }
            }, 2000);
        }

    }


    @OnClick(R.id.sendnumber)
    public void sendNumber (View view){
        showProgress(true);
        if (view.getId()== R.id.sendnumber){
            String phoneNumber= numberE.getText().toString();
            if (!phoneNumber.equals("") && phoneNumber.length()==11 && phoneNumber.startsWith("09")){
                JsonObject jsonObject= new JsonObject();
                jsonObject.addProperty("FirstName","");
                jsonObject.addProperty("LastName","");
                jsonObject.addProperty("NationalCode","");
                jsonObject.addProperty("Mobile",phoneNumber);
                jsonObject.addProperty("Tel","");
                jsonObject.addProperty("ProvinceId","");
                jsonObject.addProperty("City","");
                jsonObject.addProperty("Address","");
                jsonObject.addProperty("PostCode","");
                jsonObject.addProperty("IsAgency",0);
                jsonObject.addProperty("Telegram","");
                jsonObject.addProperty("Bearing","");
                jsonObject.addProperty("FirebaseId", FirebaseInstanceId.getInstance().getToken());
                Logger.json(jsonObject.toString());
                core.createAgent(jsonObject, new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        Logger.d(result);
                        if (result != null) {
                            if (result.has("code") && result.get("code").getAsInt() == 200) {
                                if (result.get("data") != null) {
                                    core.saveRegisterdCode(result.get("message").getAsInt());
                                    final Animation fadeoutAnim = AnimationUtils.loadAnimation(Starter.this, R.anim.fade_out);
                                    llRegistration.startAnimation(fadeoutAnim);
                                    final Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            User user = new User();
                                            user.setId(1);
                                            user.setPhoneNumber(numberE.getText().toString());
                                            AppDatabase.getAppDatabase(Starter.this).userDoa().insert(user);
                                            llRegistration.setVisibility(View.GONE);
                                            llRegistrationCode.setVisibility(View.VISIBLE);
                                        }
                                    }, 2000);
                                    showProgress(false);
                                }
                            } else {
                                showProgress(false);
                                showToast(Starter.this, "لطفا دوباره تلاش کنید.");

                            }
                        }else{
                            showProgress(false);
                            showToast(Starter.this,"دوباره تلاش کنید");

                        }
                    }
                });
            }else{
                showProgress(false);

                showToast(Starter.this,"لطفا شماره تلفن خود را به صورت صحیح وارد کنید.");
            }

            }
        }
    @OnClick(R.id.sendCode)
    public void checkRegisterCode (View view){
//        showProgress(true);
            if (view.getId()== R.id.sendCode){
                String codeEdit= codeE.getText().toString();
                if (!codeEdit.equals("") && codeEdit.length()>3 && Integer.parseInt(codeEdit)==core.getRegisterdCode() ){
//                    core.saveRegisterdCode(Integer.valueOf(codeEdit));
                    final Animation fadeoutAnim = AnimationUtils.loadAnimation(this, R.anim.fade_out);
                    llRegistrationCode.startAnimation(fadeoutAnim);
                    core.saveCredit(true);
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            llRegistrationCode.setVisibility(View.GONE);
//                            Intent intent= new Intent(Starter.this, MainActivity.class);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NEW_TASK);
//                            startActivity(intent);
//                            finish();
                            lottieStarter.setVisibility(View.VISIBLE);
                            updateCars();
                            updateCategory();
                            adaptMaintence();
                            showToast(Starter.this,"در حال بارگذاری اطلاعات داخلی اپلیکیشن");
//
                        }
                    }, 2000);
                }else{
                    showToast(Starter.this,"لطفا کد را صحیح وارد کنید.");
                }
//                showProgress(false);
            }
    }

    public void updateCategory() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("CarPk",0);
        core.updateallCategory(jsonObject,new FutureCallback<JsonObject>() {
            @Override
            public void onCompleted(Exception e, JsonObject result) {
                if(result != null){
                    if(result.has("code") && result.get("code").getAsInt() == 200){
                        if(result.has("data")){
                            Logger.d(result.get("data"));
                            categoryDatas = new Gson().fromJson(result.get("data").getAsJsonArray(), new TypeToken<List<CategoryData>>(){}.getType());
                            core.insetAllCategory(categoryDatas);
                            cars=true;


                        } else {
                            Logger.e("RESULT DONTHAVE DATA");
                        }

                    }
                } else {
                    Logger.e(e.toString());
                }
            }
        });
    }


    public void updateCars() {
        core.getAllCars(new FutureCallback<JsonObject>() {
            @Override
            public void onCompleted(Exception e, JsonObject result) {
                if(result != null){
                    if(result.has("code") && result.get("code").getAsInt() == 200){
                        if(result.has("data")){
                            Logger.d(result.get("data"));
                            carList = new Gson().fromJson(result.get("data").getAsJsonArray(), new TypeToken<List<CarData>>(){}.getType());
                            core.insertAllCars(carList);
                        } else {
                            Logger.e("RESULT DONTHAVE DATA");
                        }

                    }
                } else {
                    Logger.e(e.toString());
                }
            }
        });
    }





    public void adaptMaintence(){
        Logger.d(AppDatabase.getAppDatabase(Starter.this).maintenceDoa().getAll().size());
//        if (AppDatabase.getAppDatabase(Starter.this).maintenceDoa().getAll().size()==0){
        JsonObject jsonObject=new JsonObject();
            jsonObject.addProperty("Array","0");
            jsonObject.addProperty("ArrayGroup","0");
            jsonObject.addProperty("ArrayDetailGroup","0");
            jsonObject.addProperty("FirebaseId", String.valueOf(FirebaseInstanceId.getInstance()));
        Logger.d(jsonObject);
        core.GetProductbyType(jsonObject, new FutureCallback<JsonObject>() {
            @Override
            public void onCompleted(Exception e, JsonObject result) {
                Logger.d(result);
                if (result != null) {
                    Logger.d(result);if(((JsonObject) result).has("code") && ((JsonObject) result).get("code").getAsInt() == 200) {
                        Logger.d(result.get("data"));
                        maintenceDatas = new Gson().fromJson(result.get("data").getAsJsonArray(), new TypeToken<List<MaintenceDataSAvingVErsion>>(){}.getType());
                        core.insertallMaintence(maintenceDatas);
                        Logger.d(maintenceDatas);
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                Intent intent= new Intent(Starter.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();


                            }
                        }, 5000);




                    }else{
                        Logger.e("DONT HAVE 200 CODE ");

                    }
                }
                else {
                    showProgress(false);
                    Log.i("data", "null");
                    showToast(Starter.this,"لطفا اینترنت خود را بررسی کنید.");
                }
            }
        });

//        }else{
//        }

    }
    int counter = 0;
    @OnClick(R.id.sendAganCode)
    public void onClickTry(View view) {

        Random rnd = new Random();
        final int newCode = 10000 + rnd.nextInt(90000);
        int id = view.getId();
        final long[] currennTime = new long[1];
        if (id == R.id.sendAganCode) {
            if (counter < 6) {
                Logger.d(AppDatabase.getAppDatabase(getApplicationContext()).userDoa().getUser().get(0).getPhoneNumber());
                core.tryAgainSMS(AppDatabase.getAppDatabase(getApplicationContext()).userDoa().getUser().get(0).getPhoneNumber(), String.valueOf(newCode), new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            Logger.d(result);
                            if (result.has("code") && result.get("code").getAsInt()==200){
                                currennTime[0] = System.currentTimeMillis();
                                counter++;
                                core.saveRegisterdCode(newCode);
                                Logger.d(result);
                                showToast(Starter.this,"کد برای شما ارسال شد.");
                            }else {
                                Logger.d(e);
                                showToast(Starter.this,"خطا سمت سرور");
                            }
                        }
                });
            } else {
                Calendar today = Calendar.getInstance();
                today.set(Calendar.HOUR_OF_DAY,3);
                today.set(Calendar.MINUTE, 0);
                today.set(Calendar.SECOND, 0);
                Timer timer = new Timer();
                TimerTask timertask = new TimerTask() {
                    @Override
                    public void run() {
                        counter=0;
                    }
                };
                timer.schedule(timertask, today.getTime(), TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS)); // period: 1 day
                showToast(Starter.this, core.toPersianStatic("تعداد دفعات ارسال پیامک در هر روز 5 بار می باشد لطفا بعدا تلاش کنید."));
            }


        }
    }

}
