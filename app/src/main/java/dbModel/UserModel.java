package dbModel;

import android.app.Application;

import database.GoGagnerRoomDB;

public class UserModel {

    public void addUser(Application context, User userData) {
        long longs = GoGagnerRoomDB.getInstance(context).userDao().insert(userData);
    }


    public void deleteUser(Application context,String mobile){
        GoGagnerRoomDB.getInstance(context).userDao().deleteUserByMobile(mobile);
    }

    public void updateUser(Application context, User user) {
        GoGagnerRoomDB.getInstance(context).userDao().update(user);
    }

    public User getUserbyID(Application context,String mobile){
        return GoGagnerRoomDB.getInstance(context).userDao().searchUserById(mobile);
    }
}
