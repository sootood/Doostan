package com.jabizparda.cartools.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by Sotode on 4/5/2018.
 */
@Dao
public interface UserDoa {

    @Query("SELECT * FROM user_tbl where uid=1 LIMIT 1")
    List<User> getUser();

    @Query("DELETE FROM user_tbl")
    void deleteAll();

    @Insert(onConflict = REPLACE)
    void insert(User user);

    @Insert(onConflict = REPLACE)
    void insertAll(List<User> users);
}
