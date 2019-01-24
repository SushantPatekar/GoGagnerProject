package database;

import android.app.Application;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;

import dbModel.State;
import dbModel.StateDao;
import dbModel.User;
import dbModel.UserDao;
import utility.Constants;

@Database(entities = {User.class,
        State.class
        },
        exportSchema = false,
        version = 1)
public abstract class GoGagnerRoomDB extends RoomDatabase {

    public static GoGagnerRoomDB goGagnerRoomDB;

    public abstract UserDao userDao();

public abstract StateDao stateDao();

    public synchronized static GoGagnerRoomDB getInstance(Application context) {
        if (goGagnerRoomDB == null) {
            goGagnerRoomDB = Room.databaseBuilder(context, GoGagnerRoomDB.class, Constants.DB.NAME)
                    .allowMainThreadQueries()
                    /*.addMigrations(MIGRATION_1_2)*/
                    .build();
        }
        return goGagnerRoomDB;
    }

    /*static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
        }
    };*/
}
