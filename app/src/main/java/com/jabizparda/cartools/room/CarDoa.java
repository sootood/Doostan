package com.jabizparda.cartools.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by 123 on 4/15/2018.
 */
@Dao
public interface CarDoa {

    @Query("SELECT * FROM car_tbl")
    List<CarData> getAll();
//
//    @Query("SELECT * FROM type_tbl where uid=1 LIMIT 1")
//    List<CategoryData> gettype();

    @Query("DELETE FROM car_tbl")
    void deleteAll();

    @Insert(onConflict = REPLACE)
    void insertAll(CarData... carDatas);

    @Insert(onConflict = REPLACE)
    void insertAll(List<CarData> carDataList);
}
