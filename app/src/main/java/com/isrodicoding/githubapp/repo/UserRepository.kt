package com.isrodicoding.githubapp.repo

import android.app.Application
import androidx.lifecycle.LiveData
import com.isrodicoding.githubapp.data.GithubUser
import com.isrodicoding.githubapp.db.UserDao
import com.isrodicoding.githubapp.db.UserRoomDatabase

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class UserRepository(application: Application) {
    private val mUsersDao: UserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = UserRoomDatabase.getDatabase(application)
        mUsersDao = db.userDao()
    }

    fun getAllUsers(): LiveData<List<GithubUser>> = mUsersDao.getAllUsers()
    fun insert(user: GithubUser) {
        executorService.execute { mUsersDao.insert(user) }
    }

    fun delete(user: GithubUser) {
        executorService.execute { mUsersDao.delete(user) }
    }
}