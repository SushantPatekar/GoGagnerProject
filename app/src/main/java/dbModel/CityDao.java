package dbModel;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import database.BaseDao;

@Dao
public interface CityDao extends BaseDao<City> {

    @Query("SELECT *  FROM  City ORDER BY id ASC" )
    List<City> getAllCity();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(City city);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertAll(List<City> cityList);


    @Query("SELECT *  FROM  City WHERE id =:id" )
    City getCity(int id);

}
