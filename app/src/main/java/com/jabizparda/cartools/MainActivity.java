package com.jabizparda.cartools;

import android.app.TaskStackBuilder;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NavUtils;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.jabizparda.cartools.adapter.CheckBoxAdapter;
import com.jabizparda.cartools.adapter.EventGoBackToMain;
import com.jabizparda.cartools.adapter.MaintenceActivity;
import com.jabizparda.cartools.adapter.MaintenceAdapter;

import com.jabizparda.cartools.adapter.Model;
import com.jabizparda.cartools.adapter.TypeAdapter;
import com.jabizparda.cartools.adapter.YearData;
import com.jabizparda.cartools.room.AppDatabase;
import com.jabizparda.cartools.room.BoughtToolsData;
import com.jabizparda.cartools.room.CarData;
import com.jabizparda.cartools.room.CategoryData;
import com.jabizparda.cartools.room.MaintenceDataSAvingVErsion;
import com.jabizparda.cartools.room.TypeData;
import com.jabizparda.cartools.room.TypeDoa;
import com.koushikdutta.async.future.FutureCallback;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import devlight.io.library.ntb.NavigationTabBar;
import fr.ganfra.materialspinner.MaterialSpinner;
import me.relex.circleindicator.CircleIndicator;

public class MainActivity extends HappyCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private String mTitle;
    int typeCode;
    MaterialSpinner spinner1;
    MaterialSpinner spinner3;
    MaterialSpinner spinner4;
    MaterialSpinner spinner5;

    private boolean shown = false;
    Menu menu;

    List<SearchSuggestion> listSearch = null;
    Bitmap image;
    ExpandableRelativeLayout expandableLayout1, expandableLayout2, expandableLayout3, expandableLayout4;
    Core core;
    ArrayList<CategoryData> carCategory, mainCarList;
    List<CategoryData> linkedListCat;
    LinkedList<TypeData> linkedListType;
    LinkedList<MaintenceDataSAvingVErsion> maiitenceDataVS;
    LinkedList<MaintenceDataSAvingVErsion> maiitenceDataVSAll;
    CheckBoxAdapter adapter;
    TypeAdapter adapterT;
    String[] catname;
    String[] typeName;
    String[] year;
    List<YearData> yearDatas;
    MaintenceAdapter maintenceAll;
    private List<Model> mModelList;
    List<Integer> carCategorys, typecategory;
    TextView title;
    float textSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        core = new Core(this);
        title = (TextView) findViewById(R.id.title);
        Logger.d(getResources().getDisplayMetrics().heightPixels);

        float density = getResources().getDisplayMetrics().density;

        if (density == 0.75f) {
            // LDPI
            title.setTextSize(4 * getResources().getDisplayMetrics().density);
            textSize = 4 * getResources().getDisplayMetrics().density;

        } else if (density >= 1.0f && density < 1.5f) {
            // MDPI
            title.setTextSize(4 * getResources().getDisplayMetrics().density);
            textSize = 4 * getResources().getDisplayMetrics().density;

        } else if (density == 1.5f) {
            // HDPI
            title.setTextSize(5 * getResources().getDisplayMetrics().density);
            textSize = 5 * getResources().getDisplayMetrics().density;

        } else if (density > 1.5f && density <= 2.0f) {
            // XHDPI
            title.setTextSize(5 * getResources().getDisplayMetrics().density);
            textSize = 5 * getResources().getDisplayMetrics().density;

        } else if (density > 2.0f && density <= 3.0f) {
            // XXHDPI
            title.setTextSize(6 * getResources().getDisplayMetrics().density);
            textSize = 6 * getResources().getDisplayMetrics().density;

        } else {
            // XXXHDPI
            title.setTextSize(7 * getResources().getDisplayMetrics().density);
            textSize = 7 * getResources().getDisplayMetrics().density;

        }
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        carCategorys = new ArrayList<>();
        typecategory = new ArrayList<>();
        final ArrayList<CategoryData> parish = new ArrayList<CategoryData>(AppDatabase.getAppDatabase(getApplicationContext()).categoryDoa().getAll());
// carCategory.addAll(parish);
        linkedListCat = new LinkedList<>();
        typeName = new String[parish.size()];
        for (int i = 0; i < parish.size(); i++) {
            linkedListCat.add(parish.get(i));
            typeName[i] = parish.get(i).getCarCategory();
        }
        final ArrayList<MaintenceDataSAvingVErsion> maintenseVS = new ArrayList<MaintenceDataSAvingVErsion>(AppDatabase.getAppDatabase(getApplicationContext()).maintenceDoa().getAll());
        maiitenceDataVS = new LinkedList<>();
        maiitenceDataVSAll = new LinkedList<>();
        for (int i = 0; i < maintenseVS.size(); i++) {
            maiitenceDataVS.add(maintenseVS.get(i));
            maiitenceDataVSAll.add(maintenseVS.get(i));
        }

        final ArrayList<CarData> carsData = new ArrayList<CarData>(AppDatabase.getAppDatabase(getApplicationContext()).carDoa().getAll());
        catname = new String[carsData.size()];
        for (int i = 0; i < carsData.size(); i++) {
            catname[i] = carsData.get(i).getType();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.vp_horizontal_ntb);
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return 2;
            }

            @Override
            public boolean isViewFromObject(final View view, final Object object) {
                return view.equals(object);
            }

            @Override
            public void destroyItem(final View container, final int position, final Object object) {
                ((ViewPager) container).removeView((View) object);
            }


            @Override
            public Object instantiateItem(ViewGroup container, int position) {
//
                Logger.e(String.valueOf(position));
                View view = null;

                switch (position) {
                    case 2:
                        view = LayoutInflater.from(getBaseContext()).inflate(R.layout.item_vp, null, false);

                        break;
                    case 1:
                        if (getResources().getDisplayMetrics().heightPixels < 1920)
                            view = LayoutInflater.from(getBaseContext()).inflate(R.layout.image_two, null, false);
                        else
                            view = LayoutInflater.from(getBaseContext()).inflate(R.layout.item_main_row, null, false);
                        spinner3 = view.findViewById(R.id.spinner3);
                        spinner4 = view.findViewById(R.id.spinner4);
                        spinner1 = view.findViewById(R.id.spinner1);
                        TextView titleFCard = view.findViewById(R.id.titlef);
                        TextView titleTypeCar = view.findViewById(R.id.titleTypeCar);
                        TextView titleYearCar = view.findViewById(R.id.titleYearCar);
                        TextView titleToolsType = view.findViewById(R.id.titleToolsType);
                        TextView titleSrchByCode = view.findViewById(R.id.titleSrchByCode);
                        TextView titleSrchByName = view.findViewById(R.id.titleSrchByName);
                        TextView searchBtnText = view.findViewById(R.id.serchBtnText);

                        TextView codeSearch = view.findViewById(R.id.codeSearch);
                        TextView wordSearch = view.findViewById(R.id.wordSearch);
                        final AutoCompleteTextView word = view.findViewById(R.id.autoCompName);
                        final AutoCompleteTextView code = view.findViewById(R.id.autoCompCode);
                        TextView textCode = (TextView) view.findViewById(R.id.staticCode);
                        textCode.setText("1660");
                        titleFCard.setTextSize(textSize);
                        spinner1.setArrowSize(textSize);
                        titleTypeCar.setTextSize(textSize);
                        titleToolsType.setTextSize(textSize);
                        titleYearCar.setTextSize(textSize);
                        titleSrchByCode.setTextSize(textSize);
                        titleSrchByName.setTextSize(textSize);
                        codeSearch.setTextSize(textSize);
                        wordSearch.setTextSize(textSize);
                        textCode.setTextSize(textSize);
                        searchBtnText.setTextSize(textSize);
                        word.setTextSize(textSize);
                        code.setTextSize(textSize);

                        final int[] catI = new int[1];
                        final long[] carI = new long[1];
                        final int[] yearI = new int[1];
                        yearI[0] = -1;
                        catI[0] = -1;
                        view.findViewById(R.id.searchCard).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                int id = view.getId();
                                Logger.d(view.getId());
                                if (id == R.id.searchCard) {

                                    if (carsData.size() != 0 || linkedListCat.size() != 0) {
//                                    Logger.d(linkedListCat.get(catI[0]).getId());
                                        Intent intent1 = new Intent(MainActivity.this, MaintenceActivity.class);
                                        intent1.putExtra("code", String.valueOf(carI[0] != -1 ? carsData.get((int) carI[0]).getId() : 0));
                                        intent1.putExtra("type", String.valueOf(catI[0] != -1 ? linkedListCat.get(catI[0]).getId() : 0));
                                        intent1.putExtra("year", String.valueOf(yearI[0] != -1 ? yearDatas.get(yearI[0]).getId() : 0));
                                        startActivity(intent1);
                                    } else {
                                        showToast(MainActivity.this, "لطفا نوع کالا را انتخاب کنید.");
                                    }
                                }
                            }
                        });
//
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("Array", 0);
                        core.GetDetailGroup(jsonObject, new FutureCallback<JsonObject>() {
                            @Override
                            public void onCompleted(Exception e, JsonObject result) {
                                Logger.d(result);
                                if (result != null) {
                                    yearDatas = new Gson().fromJson(result.get("data").getAsJsonArray(), new TypeToken<List<YearData>>() {
                                    }.getType());
                                    year = new String[yearDatas.size()];
                                    for (int i = 0; i < yearDatas.size(); i++) {
                                        year[i] = Core.toPersianStatic(yearDatas.get(i).getYearName());
                                    }

                                } else {
                                    year = new String[1];
                                    year[0] = "0";
                                }
                                final ArrayAdapter<String> adapterYear = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, year);
                                spinner4.setAdapter(adapterYear);
                            }
                        });
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, catname);
                        final ArrayAdapter<String> adapterMain = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, typeName);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        initSpinnerOnlyHint(view, adapter);
                        initSpinnerNoHintNoFloatingLabel3(view, adapter);
                        initSpinnerScrolling(view, adapter);
                        initSpinnerHintAndCustomHintView(view, adapter);
                        initEmptyArray(view, adapter);

                        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, final View view, int i, long l) {
                                Logger.d(adapterView.getSelectedItemPosition());
                                carI[0] = i;

                                if (i != -1) {
                                    showProgress(true);
                                    JsonObject jsonObject = new JsonObject();
                                    jsonObject.addProperty("CarPk", l);
                                    core.updateallCategory(jsonObject, new FutureCallback<JsonObject>() {
                                        @Override
                                        public void onCompleted(Exception e, JsonObject result) {
                                            Logger.d(result);
                                            if (result != null) {
                                                linkedListCat = new Gson().fromJson(result.get("data").getAsJsonArray(), new TypeToken<List<CategoryData>>() {
                                                }.getType());
                                                typeName = new String[linkedListCat.size()];
                                                for (int i = 0; i < linkedListCat.size(); i++) {

                                                    typeName[i] = linkedListCat.get(i).getCarCategory();
                                                }
                                                showProgress(false);
                                                final ArrayAdapter<String> replaceAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, typeName);
                                                spinner1.setAdapter(replaceAdapter);

                                            } else {
                                                showToast(MainActivity.this, "لطفا دوباره تلاش کنید.");
                                            }
                                        }
                                    });
                                    JsonObject jsonObjectDetail = new JsonObject();
                                    jsonObjectDetail.addProperty("Array", l);
                                    core.GetDetailGroup(jsonObjectDetail, new FutureCallback<JsonObject>() {
                                        @Override
                                        public void onCompleted(Exception e, JsonObject result) {
                                            Logger.d(result);
                                            if (result != null) {
                                                yearDatas = new Gson().fromJson(result.get("data").getAsJsonArray(), new TypeToken<List<YearData>>() {
                                                }.getType());
                                                year = new String[yearDatas.size()];
                                                for (int i = 0; i < yearDatas.size(); i++) {
                                                    year[i] = Core.toPersianStatic(yearDatas.get(i).getYearName());
                                                }

                                            } else {
                                                year = new String[1];
                                                year[0] = "0";
                                            }
                                            final ArrayAdapter<String> adapterYear = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, year);
                                            spinner4.setAdapter(adapterYear);
                                        }
                                    });

                                } else {
                                    JsonObject jsonObject = new JsonObject();
                                    jsonObject.addProperty("CarPk", 0);
                                    core.updateallCategory(jsonObject, new FutureCallback<JsonObject>() {
                                        @Override
                                        public void onCompleted(Exception e, JsonObject result) {
                                            Logger.d(result);
                                            if (result != null) {
                                                linkedListCat = new Gson().fromJson(result.get("data").getAsJsonArray(), new TypeToken<List<CategoryData>>() {
                                                }.getType());
                                                typeName = new String[linkedListCat.size()];
                                                for (int i = 0; i < linkedListCat.size(); i++) {

                                                    typeName[i] = linkedListCat.get(i).getCarCategory();
                                                }
                                                showProgress(false);
                                                final ArrayAdapter<String> replaceAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, typeName);
                                                spinner1.setAdapter(replaceAdapter);

                                            } else {
                                                showProgress(false);
                                                showToast(MainActivity.this, "لطفا دوباره تلاش کنید.");
                                            }
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });

                        spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                Logger.d(adapterView.getSelectedItemPosition());
                                yearI[0] = i;
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });

                        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, final View view, int i, long l) {
                                Logger.d(adapterView.getSelectedItemPosition());
                                catI[0] = i;
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                        initSpinnerHintAndFloatingLabel(view, adapterMain);
                        view.findViewById(R.id.wordSearch).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                checkWord(word.getText().toString());
                            }
                        });

                        view.findViewById(R.id.codeSearch).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                searchCode(code.getText().toString());
                            }
                        });
                        break;
                    case 0:
                        view = LayoutInflater.from(getBaseContext()).inflate(R.layout.image_view_pager, null, false);
                        RecyclerView allRV = (RecyclerView) view.findViewById(R.id.maintenceAllRV);
                        SearchView searchView = (SearchView) view.findViewById(R.id.searchField);
                        searchView.onActionViewExpanded();
                        searchView.clearFocus();
                        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                            @Override
                            public boolean onQueryTextSubmit(String query) {
                                maintenceAll.filter(query, maiitenceDataVSAll);
                                return true;
                            }

                            @Override
                            public boolean onQueryTextChange(String newText) {
                                maintenceAll.filter(newText, maiitenceDataVSAll);
                                return true;
                            }
                        });
                        allTools(allRV);
                        break;

                }
//
                container.addView(view);
                return view;
            }


        });

//        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
//        ImageView image = (ImageView) findViewById(R.id.imageView1);
//
//        image.setImageBitmap(Bitmap.createScaledBitmap(bmp, image.getWidth(),
//                image.getHeight(), false)));
        final String[] colors = getResources().getStringArray(R.array.polluted_waves);
        final NavigationTabBar navigationTabBar = (NavigationTabBar) findViewById(R.id.ntb);
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.gallery),
                        Color.parseColor(colors[0])
                ).title("محصولات")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.search),
                        Color.parseColor(colors[1])
                )
                        .title("جستجوی پیشرفته")
                        .build()
        );


        navigationTabBar.setModels(models);
        navigationTabBar.setViewPager(viewPager, 3);

//        navigationTabBar.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(final int position) {
//                navigationTabBar.getModels().get(position).hideBadge();
//            }
//
//            @Override
//            public void onPageScrollStateChanged(final int state) {
//
//            }
//        });
        navigationTabBar.setTitleMode(NavigationTabBar.TitleMode.ACTIVE);
        navigationTabBar.setIsBadged(false);
        navigationTabBar.setIsTitled(false);
        navigationTabBar.setIsTinted(true);
        navigationTabBar.setIsSwiped(true);


        // navigationTabBar.setTitleSize(20);
        navigationTabBar.setIconSizeFraction((float) 0.8);

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        this.menu = menu;
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
////        if (id == R.id.action_settings) {
////            return true;
////        }
//
//        return super.onOptionsItemSelected(item);
//    }

//    public void expandableButton1(View view) {
//        expandableLayout1 = (ExpandableRelativeLayout) findViewById(R.id.expandableLayoutCar);
//        expandableLayout2 = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout2);
//        expandableLayout2.collapse();
//        expandableLayout1.toggle(); // toggle expand and collapse
//    }
//
//    public void expandableButton2(View view) {
//        expandableLayout1 = (ExpandableRelativeLayout) findViewById(R.id.expandableLayoutCar);
//        expandableLayout2 = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout2);
//        expandableLayout1.collapse();
//        expandableLayout2.toggle(); // toggle expand and collapse
//    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        setCount(this, core.getBasketCount().toString());

        return true;
    }

    public void setCount(Context context, String count) {
        MenuItem menuItem = menu.getItem(0);
        LayerDrawable icon = (LayerDrawable) menuItem.getIcon();

        CountDrawable badge;

        // Reuse drawable if possible
        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_group_count);
        if (reuse != null && reuse instanceof CountDrawable) {
            badge = (CountDrawable) reuse;
        } else {
            badge = new CountDrawable(context);
        }
        badge.setCount(Core.toPersianStatic(count));
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_group_count, badge);
    }

    public void updateType(List<Integer> typeIdArray, final RecyclerView rv) {
        JsonObject jsonObject = new JsonObject();
        JsonArray jsonArray = new JsonArray();
        if (typeIdArray != null) {

            for (int i = 0; i < typeIdArray.size(); i++) {

                JsonObject object = new JsonObject();
                object.addProperty("codeType", typeIdArray.get(i));
                if (!jsonArray.contains(object))
                    jsonArray.add(object.get("codeType"));

            }
        } else
            jsonArray.add("");
        Logger.d(jsonArray.toString());
        String jsonString = jsonArray.toString();
        jsonObject.addProperty("Array", jsonString);
        Logger.d(jsonObject);
        core.updateType(jsonObject, new FutureCallback<JsonObject>() {
            @Override
            public void onCompleted(Exception e, JsonObject result) {
                Logger.d(result);
                if (result != null) {
                    if (result.has("code") && result.get("code").getAsInt() == 200) {
                        if (result.has("data")) {
                            Logger.d(result.get("data"));
                            List<TypeData> typeDatas = new Gson().fromJson(result.get("data").getAsJsonArray(), new TypeToken<List<TypeData>>() {
                            }.getType());
                            core.insertAllType(typeDatas);
                            linkedListType = new LinkedList<>();
                            for (int i = 0; i < typeDatas.size(); i++) {
                                linkedListType.add(typeDatas.get(i));
                            }
                            Logger.d(typeDatas);

                        } else {
                        }

                        adapterT = new TypeAdapter(MainActivity.this, linkedListType, new TypeAdapter.IViewHolderClicks() {
                            @Override
                            public void onTextClick(TypeData v, int pos) {
                                Logger.e(String.valueOf(v.getId()));
                                if (v.getStateSelect() == 1) {
                                    typeCode = v.getId();
                                    typecategory.add(v.getId());
                                } else {
                                    if (typecategory.size() == 1) {
                                        typecategory.clear();
                                    } else
                                        typecategory.remove(pos);
                                }
                            }
                        });
                        rv.setAdapter(adapterT);
                        LinearLayoutManager linearLayoutManagerT = new LinearLayoutManager(MainActivity.this);
                        rv.setLayoutManager(linearLayoutManagerT);


                    }
                    Logger.json(result.toString());
                } else {
                    Logger.e(e.toString());
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.ic_group) {

            Intent intent = new Intent(MainActivity.this, Basket.class);
            intent.putExtra("from", "bar");
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private List<Model> getListData() {
        mModelList = new ArrayList<>();

        for (int i = 0; i <= 10000; i++) {
            mModelList.add(new Model("TextView " + i));
        }
        return mModelList;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_call) {
//            Intent intent = new Intent(Intent.ACTION_DIAL);
//            intent.setData(Uri.parse("tel:" + "02166506037"));
//            startActivity(intent);

            dialogChangeLang();

        } else if (id == R.id.nav_recently) {

            Intent intent = new Intent(MainActivity.this, Basket.class);
            startActivity(intent);

        } else if (id == R.id.nav_about) {
            Intent intent = new Intent(MainActivity.this, About_Doostan.class);
            startActivity(intent);
        } else if (id == R.id.nav_signUp_co) {

            Intent intent = new Intent(MainActivity.this, SignUpCo.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getResult(EventGoBackToMain eventGoBackToMain) {


    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    public void allTools(final RecyclerView rv) {


        maintenceAll = new MaintenceAdapter(getListData(),textSize ,MainActivity.this, maiitenceDataVS, new MaintenceAdapter.IViewHolderClicks() {
            @Override
            public void onToolClick(MaintenceDataSAvingVErsion v, int pos) {

                if (v.getStateSelect() == 1) {
                    showGetCountDialog(v);

                } else {
                    core.deleteToolFromBasket(v.getCodeMaintence());
                    setCount(MainActivity.this, String.valueOf(core.getBasketCount()));

                }
            }

            @Override
            public void onImageClick(String image, int pos) {
                showImage(image);
            }
        });


        rv.setAdapter(maintenceAll);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        rv.setLayoutManager(linearLayoutManager);


    }

    public void checkWord(String word) {

        if (word.equals("")) {
            showToast(MainActivity.this, "لطفا کلمه مورد نظر خود را وارد کنید.");
        } else {


            Intent intent = new Intent(MainActivity.this, MaintenceActivity.class);
            intent.putExtra("code", "#" + word);
            startActivity(intent);

        }

    }


    public void searchCode(String code) {

        if (code.equals("")) {
            showToast(MainActivity.this, "لطفا کد مورد نظر خود را وارد کنید.");
        } else {


            Intent intent = new Intent(MainActivity.this, MaintenceActivity.class);
            intent.putExtra("code", "&1660" + code);
            startActivity(intent);

        }

    }

    AlertDialog imageDialog;

    public void showImage(String img) {

        if (img != null) {
            View view = getLayoutInflater().inflate(R.layout.image_dialog, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomDialog);
            ImageButton closeImage = (ImageButton) view.findViewById(R.id.closeImage);
            builder.setView(view);
            new DownloadImageTask((PhotoView) view.findViewById(R.id.imageBig))
                    .execute(img);

            builder.setCancelable(true);
            imageDialog = builder.create();
            imageDialog.show();
            closeImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageDialog.dismiss();
                }
            });
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.copyFrom(imageDialog.getWindow().getAttributes());
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
            imageDialog.getWindow().setAttributes(layoutParams);
        } else {
            showToast(MainActivity.this, "عکس محصول موجود نیست.");
        }

    }

    private void initSpinnerHintAndCustomHintView(View view, ArrayAdapter adapter) {

    }

    private void initSpinnerHintAndFloatingLabel(View view, ArrayAdapter adapter) {
        spinner1 = view.findViewById(R.id.spinner1);
        spinner1.setAdapter(adapter);
        spinner1.setPaddingSafe(10, 0, 0, 0);
    }

    private void initSpinnerOnlyHint(View view, ArrayAdapter adapter) {
//        spinner2 = view.findViewById(R.id.spinner2);
//        spinner1.setAdapter(adapter);
//        spinner2.setHint("نوع کالا");
//        spinner1.setPaddingSafe(10, 0, 0, 0);
    }

    private void initSpinnerNoHintNoFloatingLabel3(View view, ArrayAdapter adapter) {
        spinner3 = view.findViewById(R.id.spinner3);
        spinner3.setAdapter(adapter);
    }


    private void initSpinnerScrolling(View view, ArrayAdapter adapter) {

    }

    private void initEmptyArray(View view, ArrayAdapter adapter) {
//        String[] emptyArray = {};
//        spinner7 = view.findViewById(R.id.spinner7);
//        spinner7.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, emptyArray));
    }

    private static final String ERROR_MSG = "Very very very long error message to get scrolling or multiline animation when the error button is clicked";

    public void activateError(View view) {
        if (!shown) {
            spinner4.setError(ERROR_MSG);
            spinner5.setError(ERROR_MSG);
        } else {
            spinner4.setError(null);
            spinner5.setError(null);
        }
        shown = !shown;

    }

    @Override
    protected void onPause() {
        super.onPause();
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

    AlertDialog alertCount;

    public void showGetCountDialog(MaintenceDataSAvingVErsion data) {
        View view = getLayoutInflater().inflate(R.layout.dialog_number, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomDialog);
        final TextView counterText = (TextView) view.findViewById(R.id.textCount);
        final int[] number = {Integer.valueOf(counterText.getText().toString())};
        final BoughtToolsData tool = new BoughtToolsData();
        tool.setCode(data.getPkMaintence());
        tool.setName(data.getNameMaintence());
        tool.setPrice(data.getPricemaintence());
        view.findViewById(R.id.minesBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (number[0] == 1) {
                    counterText.setText(Core.toPersianStatic("1"));
                } else {
                    number[0] -= 1;
                    counterText.setText(Core.toPersianStatic(String.valueOf(number[0])));
                }
            }
        });
        view.findViewById(R.id.plusBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.d("plus");
                number[0] += 1;
                counterText.setText(Core.toPersianStatic(String.valueOf(number[0])));
            }
        });
        view.findViewById(R.id.doneBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tool.setCount(String.valueOf(Integer.valueOf(counterText.getText().toString())));
                core.insertToolToBasket(tool);
                setCount(MainActivity.this, String.valueOf(core.getBasketCount()));
                alertCount.dismiss();
            }
        });
        builder.setView(view);
        builder.setCancelable(true);
        alertCount = builder.create();
        alertCount.show();
    }


    AlertDialog alertLangDialog;

    public void dialogChangeLang() {
        View view = getLayoutInflater().inflate(R.layout.dialog_lang, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomDialog);
        builder.setView(view);
        final CardView faCard = (CardView) view.findViewById(R.id.persianCard);
        final CardView enCard = (CardView) view.findViewById(R.id.engCard);
        final TextView faText = (TextView) view.findViewById(R.id.text2);
        final TextView enText = (TextView) view.findViewById(R.id.text1);
        faText.setText(Core.toPersianStatic("02166506038"));
        enText.setText(Core.toPersianStatic("02166506037"));
        final Resources res = getApplicationContext().getResources();
        final DisplayMetrics dm = res.getDisplayMetrics();


        view.findViewById(R.id.persianCard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + "02166506037"));
                startActivity(intent);
            }
        });
        view.findViewById(R.id.engCard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + "02166506037"));
                startActivity(intent);

            }
        });

        builder.setCancelable(true);
        alertLangDialog = builder.create();
        alertLangDialog.show();

    }
}
