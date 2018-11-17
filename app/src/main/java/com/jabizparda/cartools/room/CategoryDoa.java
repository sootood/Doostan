package com.jabizparda.cartools.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by 123 on 3/4/2018.
 */

@Dao
public interface CategoryDoa  {

    @Query("SELECT * FROM category_tbl")
    List<CategoryData> getAll();

    @Query("SELECT * FROM category_tbl where uid=1 LIMIT 1")
    List<CategoryData> getCat();

    @Query("DELETE FROM category_tbl")
    void deleteAll();

    @Insert(onConflict = REPLACE)
    void insertAll(CategoryData... categoryDatas);

    @Insert(onConflict = REPLACE)
    void insertAll(List<CategoryData> categoryDatas);

}
