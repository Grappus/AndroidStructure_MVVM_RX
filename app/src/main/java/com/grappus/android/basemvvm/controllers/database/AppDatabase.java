package com.grappus.android.basemvvm.controllers.database;

import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import android.content.Context;

import com.grappus.android.basemvvm.R;
import com.grappus.android.basemvvm.controllers.database.converter.ListTypeConverter;
import com.grappus.android.basemvvm.controllers.database.dao.UserDao;
import com.grappus.android.basemvvm.controllers.database.entities.User;

/**
 * Created by chandrapratapsingh on 1/11/18.
 */

@Database(entities = {User.class}, version = 4)
@TypeConverters({ListTypeConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    //Migration
    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE 'User' "
                    + " ADD COLUMN 'address' TEXT");
        }
    };


    //DB Instance
    private static AppDatabase dbInstance;

    public static AppDatabase getInstance(Context context) {
        if (dbInstance == null) {
            dbInstance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class,
                    context.getString(R.string.db_name))
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration() //TODO .addMigrations(MIGRATION_1_2)
                    .build();
        }
        return dbInstance;
    }

    public static void destroyInstance() {
        dbInstance = null;
    }


    //Dao
    public abstract UserDao getUserDao();
}
