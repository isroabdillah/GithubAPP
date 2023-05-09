package com.isrodicoding.githubapp

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.isrodicoding.githubapp.ui.detail.FollowersFragment
import com.isrodicoding.githubapp.ui.detail.FollowingsFragment

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowersFragment()
            1 -> fragment = FollowingsFragment()
        }
        return fragment as Fragment
    }

    override fun getItemCount(): Int {
        return 2
    }

}