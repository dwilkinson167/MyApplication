package com.example.myapplication;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface SettingsDAO {

    @Query("SELECT * FROM SettingsEntity WHERE email IN (:emails)")
    LiveData<List<SettingsEntity>> loadAllByIds(String[] emails);

    @Update
    void updateSettings(SettingsEntity... settingsEntities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(SettingsEntity... settingsEntities);
}
