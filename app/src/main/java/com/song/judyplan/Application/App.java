package com.song.judyplan.Application;

import android.app.Application;

import com.song.judyplan.entity.DaoMaster;
import com.song.judyplan.entity.DaoSession;

import org.greenrobot.greendao.database.Database;

/**
 * Created by Judy on 2017/4/22.
 */

public class App extends Application {
    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "plan-db");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
