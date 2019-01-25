package dbModel;

import android.app.Application;

import java.util.List;

import database.GoGagnerRoomDB;

public class CityModel {

    public void addCity(Application context, City City) {
        long longs = GoGagnerRoomDB.getInstance(context).CityDao().insert(City);
    }
    public void addAllCity(Application context, List<City> CityList){
        long []silongs = GoGagnerRoomDB.getInstance(context).CityDao().insertAll(CityList);
    }

    public List<City> getAllCity(Application context){
        return GoGagnerRoomDB.getInstance(context).CityDao().getAllCity();
    }

    public City getCity(Application context,int _id ){
        return GoGagnerRoomDB.getInstance(context).CityDao().getCity(_id);
    }
}
