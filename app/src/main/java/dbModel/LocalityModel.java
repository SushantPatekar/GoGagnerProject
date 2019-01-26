package dbModel;

import android.app.Application;

import java.util.List;

import database.GoGagnerRoomDB;

public class LocalityModel {
    public void addLocality(Application context, Locality Locality) {
        long longs = GoGagnerRoomDB.getInstance(context).LocalityDao().insert(Locality);
    }
    public void addAllLocality(Application context, List<Locality> LocalityList){
        long []silongs = GoGagnerRoomDB.getInstance(context).LocalityDao().insertAll(LocalityList);
    }

    public List<Locality> getAllLocality(Application context){
        return GoGagnerRoomDB.getInstance(context).LocalityDao().getAllLocality();
    }

    public Locality getLocality(Application context,int _id ){
        return GoGagnerRoomDB.getInstance(context).LocalityDao().getLocality(_id);
    }

    public void deleteAllLocality(Application context,List<Locality> mList){
        GoGagnerRoomDB.getInstance(context).LocalityDao().deleteAll();
    }

}
