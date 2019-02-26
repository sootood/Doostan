package com.jabizparda.cartools.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.NavigationMenuItemView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.jabizparda.cartools.Basket;
import com.jabizparda.cartools.Core;
import com.jabizparda.cartools.HappyCompatActivity;
import com.jabizparda.cartools.MainActivity;
import com.jabizparda.cartools.R;
import com.jabizparda.cartools.room.MaintenceDataSAvingVErsion;
import com.koushikdutta.async.future.FutureCallback;
import com.orhanobut.logger.Logger;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

public class MaintenceActivity extends HappyCompatActivity {

    @BindView(R.id.maintence)
    RecyclerView rVMAitence;
    @BindView(R.id.searchField)
    SearchView search;
    LinkedList<MaintenceDataSAvingVErsion> maintenceDatas, selectedData, allData;
    LinkedList<NaqsheSokhtData> naqsheDatas;

    Core core;
    Context context;
    MaintenceAdapter adaptor;
    NaqsheAdapter naqsheadaptor;

    private List<Model> mModelList;
    @BindView(R.id.navBtn)
    BottomNavigationItemView getList;
    String code, codeTypeCar, year;

    private ScaleGestureDetector mScaleGestureDetector;
    private float mScaleFactor = 1.0f;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintence);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MaintenceActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
                finish();
            }
        });
        ButterKnife.bind(this);
        core = new Core(this);
        showProgress(true);
        Intent intent = getIntent();
        code = intent.getStringExtra("code");
        codeTypeCar = intent.getStringExtra("type");
        year = intent.getStringExtra("year");
        context = this;
        maintenceDatas = new LinkedList<>();
        naqsheDatas = new LinkedList<>();
        allData = new LinkedList<>();
        selectedData = new LinkedList<>();
        adaptor = new MaintenceAdapter(getListData(), context, maintenceDatas, new MaintenceAdapter.IViewHolderClicks() {
            @Override
            public void onToolClick(MaintenceDataSAvingVErsion v, int pos) {

                showGetCountDialog(v);
                if (v.getStateSelect() == 1) {
                    selectedData.add(v);
                } else {
                    selectedData.remove(v);
                }
                if (selectedData != null) {
                    getList.setVisibility(View.VISIBLE);
                } else {
                    getList.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onImageClick(String image, int pos) {
                showImage(image);
            }


        });
        naqsheadaptor = new NaqsheAdapter(context, naqsheDatas, new NaqsheAdapter.IViewHolderClicks() {
            @Override
            public void onImageClick(String image, int pos) {
                if (image != null)
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                showImage(image);
            }

            @Override
            public void onRelatedBtnClicked(NaqsheSokhtData data, int pos) {
                Logger.d(data);
                Intent intent1 = new Intent(MaintenceActivity.this, MaintenceActivity.class);
                intent1.putExtra("code", "*");
                intent1.putExtra("type", String.valueOf(4));
                intent1.putExtra("year", String.valueOf(data.getCodeNaqshe()));
                startActivity(intent1);
            }
        });

        adaptMaintence();
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        search.onActionViewExpanded();

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adaptor.filter(query, allData);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adaptor.filter(newText, allData);
                return true;
            }

        });
    }

    private List<Model> getListData() {
        mModelList = new ArrayList<>();

        for (int i = 0; i <= 10000; i++) {
            mModelList.add(new Model("TextView " + i));
        }
        return mModelList;
    }


    Boolean isNaqshe = false;

    public void adaptMaintence() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("Array", (code == null) ? "" : code);
        jsonObject.addProperty("ArrayGroup", (codeTypeCar == null) ? "" : codeTypeCar);
        jsonObject.addProperty("ArrayDetailGroup", year == null ? "" : year);
        jsonObject.addProperty("FirebaseId", String.valueOf(FirebaseInstanceId.getInstance().getToken()));
        Logger.json(jsonObject.toString());
        core.GetProductbyType(jsonObject, new FutureCallback<JsonObject>() {
            @Override
            public void onCompleted(Exception e, JsonObject result) {
//                Logger.json(result.toString());
                if (e == null) {
                    Logger.d(result);
                    if (((JsonObject) result).has("code") && ((JsonObject) result).get("code").getAsInt() == 200) {
                        maintenceDatas.clear();
                        if (result.get("data").getAsJsonArray().size() != 0) {
                            if (result.get("data").getAsJsonArray().get(0).getAsJsonObject().has("fldTitel")) {
                                JsonArray array = ((JsonObject) result).get("data").getAsJsonArray();
                                Log.i("size", String.valueOf(array.size()));
                                if (array.size() == 1) {
                                    showProgress(false);
                                    showToast(MaintenceActivity.this, "محصولی برای جستجوی شما موجود نیست.");
                                }
                                for (int i = 1; i < (array).size(); i++) {
                                    JsonObject object = (array).get(i).getAsJsonObject();
                                    naqsheDatas.add(new Gson().fromJson(object, NaqsheSokhtData.class));
                                }

                            } else {
                                JsonArray array = ((JsonObject) result).get("data").getAsJsonArray();
                                Log.i("size", String.valueOf(array.size()));
                                for (int i = 0; i < (array).size(); i++) {
                                    JsonObject object = (array).get(i).getAsJsonObject();
                                    maintenceDatas.add(new Gson().fromJson(object, MaintenceDataSAvingVErsion.class));
                                    allData.add(new Gson().fromJson(object, MaintenceDataSAvingVErsion.class));

                                }
                            }
                        } else {
                            showProgress(false);
                            showToast(MaintenceActivity.this, "محصولی برای جستجوی شما موجود نیست.");


                        }

//                        for (int i = factorList.size() - 1; i >= 0; i--) {
//                            if (factorList.get(i).getTaxiState() == 1) {
//                                FactorData factor = factorList.get(i);
//                                factorList.remove(i);
//                                factorList.addFirst(factor);
//                            }
//                        }
                    } else {
                        Logger.e("DONT HAVE 200 CODE ");

                    }
                    naqsheadaptor.notifyDataSetChanged();
                    adaptor.notifyDataSetChanged();
                    showProgress(false);
                } else {
                    showToast(MaintenceActivity.this, "خطا در برقراری ارتباط با سرور");
                    Log.i("data", "null");
                    showProgress(false);
                }
            }

        });
        if (codeTypeCar != null) {
            if (!codeTypeCar.equals("4")) {
                if (code.equals("0") && codeTypeCar.equals("0"))
                    rVMAitence.setAdapter(naqsheadaptor);
                else
                    rVMAitence.setAdapter(adaptor);
            } else {
                if (code.equals("*"))
                    rVMAitence.setAdapter(adaptor);
                else
                    rVMAitence.setAdapter(naqsheadaptor);


            }
        } else {
            rVMAitence.setAdapter(adaptor);

        }
//        rVMAitence.setAdapter(adaptor);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rVMAitence.setLayoutManager(linearLayoutManager);

    }

    AlertDialog imageDialog;

    public void showImage(String img) {
        if (img != null) {
            View view = getLayoutInflater().inflate(R.layout.image_dialog, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomDialog);
            ImageButton closeImage = (ImageButton) view.findViewById(R.id.closeImage);

            builder.setView(view);
            new MaintenceActivity.DownloadImageTask((PhotoView) view.findViewById(R.id.imageBig))
                    .execute(img);

            builder.setCancelable(false);
            imageDialog = builder.create();
            imageDialog.show();
            closeImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageDialog.dismiss();
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

                }
            });
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.copyFrom(imageDialog.getWindow().getAttributes());
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
            imageDialog.getWindow().setAttributes(layoutParams);

        } else {
            showToast(MaintenceActivity.this, "عکس محصول موجود نیست.");
        }

    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }


    @OnClick(R.id.navBtn)
    public void goToOrder(View view) {
        int id = view.getId();
        JsonArray jsonArray = new JsonArray();
        JsonArray codeArray = new JsonArray();
        for (int i = 0; i < selectedData.size(); i++) {

            JsonObject object = new JsonObject();
            object.addProperty("codeTools", selectedData.get(i).getPkMaintence());
            object.addProperty("countTools", selectedData.get(i).getCounterMaintence() != 0 ? selectedData.get(i).getCounterMaintence() : 1);

            if (!jsonArray.contains(object))
                jsonArray.add(object.get("codeTools"));

            codeArray.add(object.get("countTools"));
        }
        if (id == R.id.navBtn) {
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
                        showToast(MaintenceActivity.this, "لیست سفارش خود را از منو مشاهده کنید.");
                        Intent intent = new Intent(MaintenceActivity.this, Basket.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(intent);
                        finish();

                    } else {
                        showProgress(false);
                        showToast(MaintenceActivity.this, "لطفا دوباره تلاش کنید.");
                    }

                }
            });

        }
    }

    AlertDialog alertCount;

    public void showGetCountDialog(final MaintenceDataSAvingVErsion data) {
        View view = getLayoutInflater().inflate(R.layout.dialog_number, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomDialog);
        final EditText counterText = (EditText) view.findViewById(R.id.textCount);
        counterText.setText(Core.toPersianStatic("1"));
        view.findViewById(R.id.minesBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.valueOf(counterText.getText().toString()) == 1) {
                    counterText.setText(Core.toPersianStatic("1"));
                } else {
                    int number = Integer.valueOf(counterText.getText().toString());
                    number -= 1;
                    counterText.setText(Core.toPersianStatic(String.valueOf(number)));
                }
            }
        });
        view.findViewById(R.id.plusBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.d("plus");
                int number = Integer.valueOf(counterText.getText().toString());
                number += 1;
                counterText.setText(Core.toPersianStatic(String.valueOf(number)));
            }
        });
        view.findViewById(R.id.doneBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.setCounterMaintence(Integer.valueOf(counterText.getText().toString()));
                alertCount.dismiss();
            }
        });
        builder.setView(view);
        builder.setCancelable(true);
        alertCount = builder.create();
        alertCount.show();
    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(MaintenceActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();
    }

}

