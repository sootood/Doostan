package com.jabizparda.cartools.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by 123 on 4/18/2018.
 */
@Dao
public interface CityDoa {

    @Query("SELECT * FROM city_tbl")
    List<CityData> getAll();
//
//    @Query("SELECT * FROM type_tbl where uid=1 LIMIT 1")
//    List<CategoryData> gettype();

    @Query("DELETE FROM city_tbl")
    void deleteAll();

    @Insert(onConflict = REPLACE)
    void insertAll(CityData... cityDatas);

    @Insert(onConflict = REPLACE)
    void insertAll(List<CityData> cityDatas);


}
