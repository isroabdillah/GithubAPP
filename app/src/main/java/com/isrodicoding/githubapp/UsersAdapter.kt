package com.isrodicoding.githubapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.isrodicoding.githubapp.data.GithubUser

class UsersAdapter(private val listUsers: List<GithubUser>) :
    RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(callback: OnItemClickCallback) {
        onItemClickCallback = callback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(viewGroup.context).inflate(R.layout.item_users, viewGroup, false)
    )

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        val user = listUsers[position]
        viewHolder.usernameTV.text = user.login
        viewHolder.profileUrlTV.text = user.htmlUrl
        Glide.with(viewHolder.avatarIV.context)
            .load(user.avatarUrl)
            .into(viewHolder.avatarIV)


        viewHolder.itemView.setOnClickListener {
            onItemClickCallback?.onItemClicked(user)
        }
    }

    override fun getItemCount() = listUsers.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val usernameTV: TextView = view.findViewById(R.id.usernameTV)
        val avatarIV: ImageView = view.findViewById(R.id.avatarIV)
        val profileUrlTV: TextView = view.findViewById(R.id.profileUrlTV)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: GithubUser)
    }
}


//
