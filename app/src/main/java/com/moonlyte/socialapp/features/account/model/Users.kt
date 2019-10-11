package com.moonlyte.socialapp.features.account.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "user")
data class Users(@PrimaryKey(autoGenerate = false) @SerializedName("id") val id: Int,
                 @ColumnInfo(name = "first_name") @SerializedName("name") val name: String,
                 @ColumnInfo(name = "username") @SerializedName("username") val username: String,
                 @ColumnInfo(name = "email") @SerializedName("email") val email: String,
                 @Embedded @SerializedName("address") val address: Address,
                 @ColumnInfo(name = "phone") @SerializedName("phone") val phone: String,
                 @ColumnInfo(name = "website") @SerializedName("website") val website: String,
                 @Embedded @SerializedName("company") val company: Company
) {

    data class Address(@ColumnInfo(name = "street") @SerializedName("street") val street: String,
                       @ColumnInfo(name = "suite") @SerializedName("suite") val suite: String,
                       @ColumnInfo(name = "city") @SerializedName("city") val city: String,
                       @ColumnInfo(name = "zipcode") @SerializedName("zipcode") val zipcode: String,
                       @Embedded @SerializedName("geo") val geo: Geo
    ) {

        data class Geo(@ColumnInfo(name = "lat") @SerializedName("lat") val lat: String,
                       @ColumnInfo(name = "lng") @SerializedName("lng") val lng: String)
    }

    data class Company(@ColumnInfo(name = "name") @SerializedName("name") val lat: String,
                       @ColumnInfo(name = "catchPhrase") @SerializedName("catchPhrase") val lng: String,
                       @ColumnInfo(name = "bs") @SerializedName("bs") val bs: String)
}
