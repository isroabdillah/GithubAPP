package com.isrodicoding.githubapp.ui.detail


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.isrodicoding.githubapp.*
import com.isrodicoding.githubapp.data.DetailUser
import com.isrodicoding.githubapp.data.GithubUser
import com.isrodicoding.githubapp.databinding.ActivityDetailBinding
import com.isrodicoding.githubapp.SectionsPagerAdapter
import com.isrodicoding.githubapp.ui.FavoriteUserActivity
import com.isrodicoding.githubapp.viewModels.DetailViewModel
import com.isrodicoding.githubapp.viewModels.UserFavoriteViewModelFactory

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel
    private var isFav: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        binding.viewPager.adapter = sectionsPagerAdapter

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f

        detailViewModel = obtainViewModel(this)

        val user = intent.getParcelableExtra<GithubUser>(EXTRA_USER) as GithubUser
        detailViewModel.userlogin = user.login

        detailViewModel.detailuser.observe(this) {
            setDetailUser(it)
        }

        detailViewModel.getAllUsers().observe(this) {
            isFav = it.contains(user)
            if (isFav) {
                binding.favIcon.setImageDrawable(resources.getDrawable(R.drawable.favorite_red_24))
            } else {
                binding.favIcon.setImageDrawable(resources.getDrawable(R.drawable.favorite_red_border_24))
            }
        }

        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        binding.favIcon.setOnClickListener {
            if (isFav) {
                detailViewModel.delete(user)
                Snackbar.make(
                    binding.root,
                    StringBuilder(user.login + " ").append(resources.getString(R.string.deleteFromFav)),
                    Snackbar.LENGTH_LONG
                ).setAction(
                    resources.getString(R.string.undo)
                ) {
                    detailViewModel.insert(user)
                    Toast.makeText(this, resources.getString(R.string.undo), Toast.LENGTH_SHORT).show()
                }.show()

            } else {
                detailViewModel.insert(user)
                Snackbar.make(
                    binding.root,
                    StringBuilder(user.login + " ").append(resources.getString(R.string.addToFav)),
                    Snackbar.LENGTH_LONG
                ).setAction(
                    resources.getString(R.string.seeFavorite)
                ) { startActivity(Intent(this, FavoriteUserActivity::class.java)) }.show()
            }
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): DetailViewModel {
        val factory = UserFavoriteViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[DetailViewModel::class.java]
    }


    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar2.visibility = View.VISIBLE
            resetDetailUser()
        } else {
            binding.progressBar2.visibility = View.GONE
        }
    }

    private fun setDetailUser(detailuser: DetailUser) {
        binding.name.text = detailuser.name
        binding.urlUser.text = detailuser.htmlUrl ?: " - "
        binding.jumlahRepo.text = detailuser.publicRepos
        binding.jumlahFollowers.text = detailuser.followers
        binding.jumlahFollowing.text = detailuser.following
        val actionBar = supportActionBar

        actionBar!!.title = detailuser.login.toUsernameFormat()
        Glide.with(this)
            .load(detailuser.avatarUrl)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(binding.imgAvatar)
    }

    private fun resetDetailUser() {
        binding.name.text = "-"

        binding.urlUser.text = "-"
        binding.jumlahRepo.text = "-"
        binding.jumlahFollowers.text = "-"
        binding.jumlahFollowing.text = "-"
        val actionBar = supportActionBar
        actionBar!!.title = this.title
        Glide.with(this)
            .load(R.drawable.ic_launcher_foreground)
            .into(binding.imgAvatar)
    }


    private fun String.toUsernameFormat(): String {
        return StringBuilder("@").append(this).toString()
    }



    companion object {
        const val EXTRA_USER = "extra_user"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab1,
            R.string.tab2
        )
    }
}