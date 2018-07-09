package com.grappus.android.basemvvm.controllers.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

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