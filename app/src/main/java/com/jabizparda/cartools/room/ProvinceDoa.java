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
public interface ProvinceDoa {

    @Query("SELECT * FROM province_tbl")
    List<ProvinceData> getAll();
//
//    @Query("SELECT * FROM type_tbl where uid=1 LIMIT 1")
//    List<CategoryData> gettype();

    @Query("DELETE FROM province_tbl")
    void deleteAll();

    @Insert(onConflict = REPLACE)
    void insertAll(ProvinceData... provinceDatas);

    @Insert(onConflict = REPLACE)
    void insertAll(List<ProvinceData> provinceDatas);

}
