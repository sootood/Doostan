package com.jabizparda.cartools.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by Karo on 9/10/2017.
 */

@Database(entities = {CategoryData.class,TypeData.class,MaintenceDataSAvingVErsion.class,User.class,CarData.class, ProvinceData.class,CityData.class,AgantData.class,BoughtToolsData.class}, version = 4)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract CategoryDoa categoryDoa();
    public abstract TypeDoa typeDoa();
    public abstract MaintenceDoa maintenceDoa();
    public abstract UserDoa userDoa();
    public abstract CarDoa carDoa();
    public abstract ProvinceDoa provinceDoa();
    public abstract CityDoa cityDoa();
    public abstract AgantDoa agantDoa();
    public abstract BoughtToolsDoa boughtToolsDoa();



    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "dynamic_db")
                            // allow queries on the main thread.
                            // Don't do this on a real app! See PersistenceBasicSample for an example.
                            .allowMainThreadQueries()
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}