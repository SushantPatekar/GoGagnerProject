package dbModel;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import database.BaseDao;

@Dao
public interface UserDao extends BaseDao<User> {



    @Query("Delete FROM User WHERE mobile =:mob")
    void deleteUserByMobile(String mob);

    @Query("SELECT * FROM user WHERE mobile =:username")
    User searchUserById(String username);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(User user);

    @Query("UPDATE User SET accessToken = :accessToke WHERE Id IN (:Id)")
    void updateAccessToken(String accessToke,String Id);
}
