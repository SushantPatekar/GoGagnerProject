package database;

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Update;

import java.util.List;

public interface BaseDao<T> {


    @Insert
    long insert(T data);

    @Delete
    void delete(T data);

    @Update
    int update(T data);

    @Update
    int updateList(List<T> list);
}

