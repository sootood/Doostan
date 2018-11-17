package com.jabizparda.cartools;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.jabizparda.cartools.adapter.MaintenceActivity;
import com.jabizparda.cartools.adapter.MaintenceAdapter;
import com.jabizparda.cartools.room.CategoryData;
import com.koushikdutta.async.future.FutureCallback;
import com.orhanobut.logger.Logger;

import java.util.LinkedList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Basket extends HappyCompatActivity {

    LinkedList<BasketData> basketDatas;
    BasketAdapter adaptor;
    Core core;
    @BindView(R.id.basketRV)
    RecyclerView basketRv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Basket.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
                finish();
            }
        });
        ButterKnife.bind(this);
        core= new Core(this);
        basketDatas=new LinkedList<>();
        getLastFActor();
        adaptor= new BasketAdapter(this, basketDatas, new BasketAdapter.IViewHolderClicks() {
            @Override
            public void onTextClick(CategoryData v, int pos) {

            }
        });
    }

    public void getLastFActor(){

        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("FirebaseId", FirebaseInstanceId.getInstance().getToken());
        Logger.d(jsonObject);
        core.SelectEndOrder(jsonObject, new FutureCallback<JsonObject>() {
            @Override
            public void onCompleted(Exception e, JsonObject result) {
                Logger.d(result);
                if (result != null) {
                    Logger.d(result);
                    if(((JsonObject) result).has("code") && ((JsonObject) result).get("code").getAsInt() == 200) {
                        basketDatas.clear();
                        if (result.get("data")!= null){
                        JsonArray array = ((JsonObject) result).get("data").getAsJsonArray();
                        Log.i("size", String.valueOf(array.size()));
                        for (int i = 0; i < (array).size(); i++) {
                            JsonObject object = (array).get(i).getAsJsonObject();
                            basketDatas.add(new Gson().fromJson(object, BasketData.class));
                        }

//                        for (int i = factorList.size() - 1; i >= 0; i--) {
//                            if (factorList.get(i).getTaxiState() == 1) {
//                                FactorData factor = factorList.get(i);
//                                factorList.remove(i);
//                                factorList.addFirst(factor);
//                            }
//                        }
                         }else{

                            showToast(Basket.this,"هیچ سفارشی ثبت نشده");

                        }
                    }else{
                        Logger.e("DONT HAVE 200 CODE ");

                    }
                    basketRv.setAdapter(adaptor);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Basket.this);
                    basketRv.setLayoutManager(linearLayoutManager);
                    showProgress(false);
                }
                else {
                    showToast(Basket.this,"خطا در برقراری ارتباط با سرور");
                    showProgress(false);
                    Log.i("data", "null");
                }
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent= new Intent(Basket.this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }
}
