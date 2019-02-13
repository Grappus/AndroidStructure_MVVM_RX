package com.grappus.android.basemvvm.controllers.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import com.grappus.android.basemvvm.controllers.database.entities.User;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveUser(User... users);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveUsersList(List<User> users);

    @Update
    void updateUser(User... users);


    @Query("SELECT * FROM user WHERE id = :userId")
    LiveData<User> getUserDetails(String userId);


    @Delete
    void deleteUser(User... users);

    @Query("DELETE FROM user")
    void deleteUser();
}