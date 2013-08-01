package ua.org.zasadnyy.partybudget;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import ua.org.zasadnyy.partybudget.dao.DaoMaster;
import ua.org.zasadnyy.partybudget.dao.DaoSession;

/**
 * Created by vitaliyzasadnyy on 21.07.13.
 */
public class PartyBudgetApplication extends Application {

    private static DaoSession sDaoSession;

    public static DaoSession getDaoSession() {
        if (sDaoSession != null) {
            return sDaoSession;
        }
        throw new IllegalArgumentException("Application should be initialized");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, Config.DB_NAME, null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        sDaoSession = daoMaster.newSession();
    }

}
