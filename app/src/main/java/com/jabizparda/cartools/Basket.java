package com.jabizparda.cartools;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.jabizparda.cartools.adapter.MaintenceActivity;
import com.jabizparda.cartools.adapter.MaintenceAdapter;
import com.jabizparda.cartools.room.BoughtToolsData;
import com.jabizparda.cartools.room.CategoryData;
import com.koushikdutta.async.future.FutureCallback;
import com.orhanobut.logger.Logger;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Basket extends HappyCompatActivity {

    LinkedList<BasketData> basketDatas;
    BasketAdapter adaptor;
    Core core;
    @BindView(R.id.basketRV)
    RecyclerView basketRv;

    @BindView(R.id.finalOrder)
    Button finalOrder;

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
                Intent intent = new Intent(Basket.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
                finish();
            }
        });
        ButterKnife.bind(this);
        core = new Core(this);
        basketDatas = new LinkedList<>();
        Intent fromIntent = getIntent();



        if (fromIntent.hasExtra("from")) {


            adaptor = new BasketAdapter(this, basketDatas, 1, new BasketAdapter.IViewHolderClicks() {
                @Override
                public void onTextClick(CategoryData v, int pos) {

                }

                @Override
                public void deletOrder(BasketData tools,int pos) {
                    core.deleteToolFromBasket(tools.getChoosenCode());
                    basketDatas.remove(tools);
                    adaptor.notifyDataSetChanged();
                }
            });
            localBasket();

        } else {
            finalOrder.setVisibility(View.GONE);
            adaptor = new BasketAdapter(this, basketDatas, 0, new BasketAdapter.IViewHolderClicks() {
                @Override
                public void onTextClick(CategoryData v, int pos) {

                }

                @Override
                public void deletOrder(BasketData tools, int pos) {

                }
            });
            getLastFActor();

        }


    }

    public void getLastFActor() {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("FirebaseId", FirebaseInstanceId.getInstance().getToken());
        Logger.d(jsonObject);
        core.SelectEndOrder(jsonObject, new FutureCallback<JsonObject>() {
            @Override
            public void onCompleted(Exception e, JsonObject result) {
                Logger.d(result);
                if (result != null) {
                    Logger.d(result);
                    if (((JsonObject) result).has("code") && ((JsonObject) result).get("code").getAsInt() == 200) {
                        basketDatas.clear();
                        if (result.get("data") != null) {
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
                        } else {

                            showToast(Basket.this, "هیچ سفارشی ثبت نشده");

                        }
                    } else {
                        Logger.e("DONT HAVE 200 CODE ");

                    }
                    basketRv.setAdapter(adaptor);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Basket.this);
                    basketRv.setLayoutManager(linearLayoutManager);
                    showProgress(false);
                } else {
                    showToast(Basket.this, "خطا در برقراری ارتباط با سرور");
                    showProgress(false);
                    Log.i("data", "null");
                }
            }
        });


    }

    public void localBasket() {

        Logger.d(core.getAllbasket());

        List<BoughtToolsData> localData = core.getAllbasket();
        BasketData title = new BasketData("کد",
                "نام کالا",
                "قیمت",
                "تعداد");
        basketDatas.add(title);
        for (int i = 0; i < core.getAllbasket().size(); i++) {
            BasketData object = new BasketData(String.valueOf(localData.get(i).getCode()),
                    localData.get(i).getName(),
                    localData.get(i).getPrice(),
                    localData.get(i).getCount());
            basketDatas.add(object);
        }
        adaptor.notifyDataSetChanged();
        basketRv.setAdapter(adaptor);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Basket.this);
        basketRv.setLayoutManager(linearLayoutManager);
    }

    @OnClick(R.id.finalOrder)
    public void onFinalOrderClick(View view){

        int id = view.getId();
        JsonArray jsonArray = new JsonArray();
        JsonArray codeArray = new JsonArray();
        for (int i = 1; i < basketDatas.size(); i++) {

            JsonObject object = new JsonObject();
            object.addProperty("codeTools", basketDatas.get(i).getChoosenCode());
            object.addProperty("countTools", String.valueOf(!basketDatas.get(i).getChoosenCountMaintence().equals("0") ? basketDatas.get(i).getChoosenCountMaintence() : 1));

            if (!jsonArray.contains(object))
                jsonArray.add(object.get("codeTools"));

            codeArray.add(object.get("countTools"));
        }
        if (id == R.id.finalOrder) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("FirebaseId", FirebaseInstanceId.getInstance().getToken());
            String jsonArrayString = jsonArray.toString();
            jsonObject.addProperty("Array", jsonArrayString);
            jsonObject.addProperty("ArrayGroup", codeArray.toString());
            Logger.d(jsonObject);
            showProgress(true);
            core.insertIntoOrder(jsonObject, new FutureCallback<JsonObject>() {
                @Override
                public void onCompleted(Exception e, JsonObject result) {
                    Logger.d(result);
                    Logger.d(e);
                    if (result.has("code") && result.get("code").getAsInt() == 200) {
                        showProgress(false);
//                       EventBus.getDefault().post(new EventGoBackToMain());
                        showToast(Basket.this, "لیست سفارش خود را از منو مشاهده کنید.");
                        Intent intent = new Intent(Basket.this, Basket.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(intent);
                        finish();

                    } else {
                        showProgress(false);
                        showToast(Basket.this, "لطفا دوباره تلاش کنید.");
                    }

                }
            });

        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Basket.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }
}
