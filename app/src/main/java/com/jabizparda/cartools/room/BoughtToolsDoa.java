package com.jabizparda.cartools.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;
import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface BoughtToolsDoa {

    @Query("SELECT * FROM bought_tbl")
    List<BoughtToolsData> getAll();

    @Query("SELECT * FROM bought_tbl WHERE flProductCode= :code LIMIT 1")
    List<BoughtToolsData> getOnce(String code);

    @Query("DELETE FROM bought_tbl")
    void deleteAll();


    @Query("DELETE FROM bought_tbl WHERE flProductCode= :code")
    void deleteOnce(String code);

    @Query("UPDATE bought_tbl SET fldCount=:count WHERE flProductCode= :code")
    void changeCountValue(String code, int count);

    @Insert(onConflict = REPLACE)
    void insertAll(BoughtToolsData... boughtToolsDatas);

    @Insert(onConflict = IGNORE)
    void insertTools(BoughtToolsData boughtToolsData);

    @Insert(onConflict = REPLACE)
    void insertAll(List<BoughtToolsData> boughtToolsDatas);



    @Query("SELECT COUNT(*) from bought_tbl")
    int countBought();
}
