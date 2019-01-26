package dbModel;

import android.app.Application;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import database.BaseDao;
import database.GoGagnerRoomDB;

@Dao
public interface StateDao  extends BaseDao<State> {

    @Query("SELECT *  FROM  State ORDER BY id ASC" )
    List<State> getAllState();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(State state);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertAll(List<State> stateList);

    @Query("SELECT *  FROM  State WHERE id =:id" )
    State getState(int id);


    @Query("Delete FROM State" )
    void deleteAll();


}
