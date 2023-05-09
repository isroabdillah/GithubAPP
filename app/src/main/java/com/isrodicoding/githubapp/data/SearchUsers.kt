package com.isrodicoding.githubapp.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class GithubUser(
    @PrimaryKey
    @ColumnInfo(name = "login")
    val login: String,

    @ColumnInfo(name = "avatarUrl")
    @field:SerializedName("avatar_url")
    val avatarUrl: String?,

    @ColumnInfo(name = "htmlUrl")
    @field:SerializedName("html_url")
    val htmlUrl: String?,

    ) : Parcelable


data class SearchUsers(

    @field:SerializedName("total_count")
    val totalCount: Int? = null,

    @field:SerializedName("incomplete_results")
    val incompleteResults: Boolean? = null,

    @field:SerializedName("items")
    val items: List<GithubUser>
)


data class DetailUser(
    var login: String,
    var name: String,
    @SerializedName("html_url")
    var htmlUrl: String?,

    @SerializedName("public_repos")
    var publicRepos: String,

    var followers: String,
    var following: String,

    @SerializedName("avatar_url")
    var avatarUrl: String
)