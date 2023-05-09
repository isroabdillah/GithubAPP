package com.isrodicoding.githubapp.viewModels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class UserFavoriteViewModelFactory(private val mApplication: Application) :
    ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var INSTANCE: UserFavoriteViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application): UserFavoriteViewModelFactory {
            if (INSTANCE == null) {
                synchronized(UserFavoriteViewModelFactory::class.java) {
                    INSTANCE = UserFavoriteViewModelFactory(application)
                }
            }
            return INSTANCE as UserFavoriteViewModelFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserFavoriteViewModel::class.java)) {
            return UserFavoriteViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(mApplication) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}