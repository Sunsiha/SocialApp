package com.moonlyte.socialapp.persistence

import android.content.Context
import androidx.annotation.NonNull
import androidx.room.Room

class DataSource private constructor(@NonNull private val context: Context) {

    private val appDatabase: AppDatabase = Room.databaseBuilder(this.context, AppDatabase::class.java, "Assignment").allowMainThreadQueries().build()

    companion object {

        private var instance: DataSource? = null

        fun getInstance(@NonNull context: Context): DataSource {
            if (instance == null) {
                instance = DataSource(context)
            }

            return instance!!
        }
    }

    fun getAppDatabase(): AppDatabase = this.appDatabase
}