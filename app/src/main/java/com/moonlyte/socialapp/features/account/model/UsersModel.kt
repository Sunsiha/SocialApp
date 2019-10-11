package com.moonlyte.socialapp.features.account.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.moonlyte.socialapp.BR

class UsersModel : BaseObservable() {

    @get: Bindable
    var userList: List<Users> = emptyList()
    set(value) {
        field = value
        notifyPropertyChanged(BR.userList)
    }
}