package com.jabizparda.cartools.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by Sotode on 3/4/2018.
 */
@Dao
public interface TypeDoa {

    @Query("SELECT * FROM type_tbl")
    List<TypeData> getAll();
//
//    @Query("SELECT * FROM type_tbl where uid=1 LIMIT 1")
//    List<CategoryData> gettype();

    @Query("DELETE FROM type_tbl")
    void deleteAll();

    @Insert(onConflict = REPLACE)
    void insertAll(TypeData... typeDatas);

    @Insert(onConflict = REPLACE)
    void insertAll(List<TypeData> typeDatas);

}
