package dbModel;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import database.BaseDao;

@Dao
public interface LocalityDao extends BaseDao<Locality> {

    @Query("SELECT *  FROM  Locality ORDER BY id ASC" )
    List<Locality> getAllLocality();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Locality Locality);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertAll(List<Locality> LocalityList);


    @Query("SELECT *  FROM  Locality WHERE id =:id" )
    Locality getLocality(int id);

}
