package com.isrodicoding.githubapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.isrodicoding.githubapp.data.GithubUser

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: GithubUser)

    @Delete
    fun delete(user: GithubUser)

    @Query("SELECT * from GithubUser ORDER BY login ASC")
    fun getAllUsers(): LiveData<List<GithubUser>>
}