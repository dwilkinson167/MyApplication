package com.example.myapplication;
import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import java.util.List;

public class SettingsViewModel extends ViewModel {

    public LiveData<List<SettingsEntity>> loadAllByIds(Context context, String[] emailIds) {
        AppDatabase db = AppDatabaseSingleton.getDatabase(context);
        return db.settingsDAO().loadAllByIds(emailIds);
    }

    public void updateSettings(Context context, SettingsEntity... settingsEntities) {
        AppDatabase db = AppDatabaseSingleton.getDatabase(context);
        db.getTransactionExecutor().execute(() -> db.settingsDAO().updateSettings(settingsEntities));
    }


    public void insertAll(Context context, SettingsEntity... settingsEntities) {
        AppDatabase db = AppDatabaseSingleton.getDatabase(context);
        db.getTransactionExecutor().execute(() -> db.settingsDAO().insertAll(settingsEntities));
    }

}
