package dbModel;

import android.app.Application;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;

import java.util.List;

import database.GoGagnerRoomDB;

public class StateModel {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void addState(Application context, State state) {
        long longs = GoGagnerRoomDB.getInstance(context).stateDao().insert(state);
    }
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void addAllState(Application context, List<State> stateList){
        long []silongs = GoGagnerRoomDB.getInstance(context).stateDao().insertAll(stateList);
    }

    public List<State> getAllState(Application context){
        return GoGagnerRoomDB.getInstance(context).stateDao().getAllState();
    }

    public State getState(Application context,int _id ){
        return GoGagnerRoomDB.getInstance(context).stateDao().getState(_id);
    }

    public void deleteAll(Application context){
         GoGagnerRoomDB.getInstance(context).stateDao().deleteAll();
    }
}
