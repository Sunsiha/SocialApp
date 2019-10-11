package com.moonlyte.socialapp.features.account

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moonlyte.socialapp.features.account.model.Users
import com.moonlyte.socialapp.features.account.model.UsersModel
import com.moonlyte.socialapp.features.account.repository.UserRepository
import com.moonlyte.socialapp.persistence.DataSource

class UsersViewModel : ViewModel() {

    private val usersModel = UsersModel()
    private val userRepository = UserRepository()

    val userLiveData = MutableLiveData<List<Users>>()

    fun loadUsers(dataSource: DataSource) {
        this.userRepository.getUsers(dataSource, {
            if (it.isSuccessful) {
                if (!it.body().isNullOrEmpty()) {
                    // notify user list through data binding
                    this.usersModel.userList = it.body()!!

                    // notify UI through live data
                    this.userLiveData.postValue(it.body()!!)
                } else {
                    // Show Empty View.
                }
            } else {
                // Show error.
                Log.e("UsersViewModel", "User service response unsuccessful.")
            }
        }, { list, t ->
            if (!list.isNullOrEmpty()) {
                // notify user list through data binding
                this.usersModel.userList = list

                // notify UI through live data
                this.userLiveData.postValue(list)
            } else {
                // Show error.
                Log.e("UsersViewModel", "loadUsers: ${t.message}")
            }
        })
    }

    fun getUsersModel(): UsersModel {
        return this.usersModel
    }
}
