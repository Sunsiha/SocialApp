package com.moonlyte.socialapp.features.post.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "posts")
data class Posts(
    @ColumnInfo(name = "userId") @SerializedName("userId") val userId: Int,
    @PrimaryKey(autoGenerate = false)  @SerializedName("id") val id: Int,
    @ColumnInfo(name = "title") @SerializedName("title") val title: String,
    @ColumnInfo(name = "body") @SerializedName("body") val body: String
) {


}
