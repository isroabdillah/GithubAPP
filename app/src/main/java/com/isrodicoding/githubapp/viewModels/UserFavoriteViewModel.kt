package com.isrodicoding.githubapp.viewModels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.isrodicoding.githubapp.data.GithubUser
import com.isrodicoding.githubapp.repo.UserRepository


class UserFavoriteViewModel(application: Application) : ViewModel() {
    private val mUserRepository: UserRepository = UserRepository(application)

    fun getAllUsers(): LiveData<List<GithubUser>> = mUserRepository.getAllUsers()
}