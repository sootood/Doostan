package com.jabizparda.cartools.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by 123 on 3/11/2018.
 */
@Dao
public interface MaintenceDoa {

    @Query("SELECT * FROM tools")
    List<MaintenceDataSAvingVErsion> getAll();

    @Query("SELECT * FROM tools where uid=1 LIMIT 1")
    List<MaintenceDataSAvingVErsion> getCat();

    @Query("DELETE FROM tools")
    void deleteAll();

    @Insert(onConflict = REPLACE)
    void insertAll(MaintenceDataSAvingVErsion... maintence);

    @Insert(onConflict = REPLACE)
    void insertAll(List<MaintenceDataSAvingVErsion> maintence);

}
