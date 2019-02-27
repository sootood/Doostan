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
public interface AgantDoa {

    @Query("SELECT * FROM agant_tbl where uid=1 LIMIT 1")
    List<AgantData> getAgant();

    @Query("DELETE FROM agant_tbl")
    void deleteAll();

    @Insert(onConflict = REPLACE)
    void insert(AgantData agantData);

    @Insert(onConflict = REPLACE)
    void insertAll(List<AgantData> agantDatas);



}
