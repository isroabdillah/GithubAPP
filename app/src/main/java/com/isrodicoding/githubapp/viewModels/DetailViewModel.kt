package com.isrodicoding.githubapp.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.isrodicoding.githubapp.BuildConfig
import com.isrodicoding.githubapp.BuildConfig.KEY
import com.isrodicoding.githubapp.api.ApiConfig
import com.isrodicoding.githubapp.data.DetailUser
import com.isrodicoding.githubapp.data.GithubUser
import com.isrodicoding.githubapp.repo.UserRepository
import retrofit2.Call
import retrofit2.Response

class DetailViewModel(application: Application) : ViewModel() {

    private val _detailuser = MutableLiveData<DetailUser>()
    val detailuser: LiveData<DetailUser> = _detailuser

    private val _allfollowers = MutableLiveData<List<GithubUser>>()
    val allfollowers: LiveData<List<GithubUser>> = _allfollowers

    private val _allfollowings = MutableLiveData<List<GithubUser>>()
    val allfollowings: LiveData<List<GithubUser>> = _allfollowings

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isLoadingFollower = MutableLiveData<Boolean>()
    val isLoadingFollower: LiveData<Boolean> = _isLoadingFollower

    private val _isLoadingFollowing = MutableLiveData<Boolean>()
    val isLoadingFollowing: LiveData<Boolean> = _isLoadingFollowing

    private val mUserRepository: UserRepository = UserRepository(application)
    fun getAllUsers(): LiveData<List<GithubUser>> = mUserRepository.getAllUsers()
    fun insert(user: GithubUser) {
        mUserRepository.insert(user)
    }

    fun delete(user: GithubUser) {
        mUserRepository.delete(user)
    }

    var userlogin: String = ""
        set(value) {
            field = value
            getDetailUser()
            getDetailUserFollowers()
            getDetailUserFollowings()
        }


    private fun getDetailUser() {
        _isLoading.value = true
        val api = ApiConfig.getApiService().getDetailUser(userlogin, BuildConfig.KEY)
        api.enqueue(object : retrofit2.Callback<DetailUser> {
            override fun onResponse(call: Call<DetailUser>, response: Response<DetailUser>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    _detailuser.value = responseBody!!
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUser>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }


    private fun getDetailUserFollowers() {
        _isLoadingFollower.value = true
        val api = ApiConfig.getApiService().getAllFollowers(userlogin, BuildConfig.KEY)
        api.enqueue(object : retrofit2.Callback<List<GithubUser>> {
            override fun onResponse(
                call: Call<List<GithubUser>>,
                response: Response<List<GithubUser>>
            ) {
                _isLoadingFollower.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    _allfollowers.value = responseBody!!
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<GithubUser>>, t: Throwable) {
                _isLoadingFollower.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }


    private fun getDetailUserFollowings() {
        _isLoadingFollowing.value = true
        val api = ApiConfig.getApiService().getAllFollowings(userlogin, BuildConfig.KEY)
//
        api.enqueue(object : retrofit2.Callback<List<GithubUser>> {
            override fun onResponse(
                call: Call<List<GithubUser>>,
                response: Response<List<GithubUser>>
            ) {
                _isLoadingFollowing.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    _allfollowings.value = responseBody!!
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<GithubUser>>, t: Throwable) {
                _isLoadingFollowing.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }


    companion object {
        private const val TAG = "DetailViewModel"
    }
}