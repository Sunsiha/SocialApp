package com.moonlyte.socialapp.features.post.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.moonlyte.socialapp.BR

class PostsModel : BaseObservable() {

    @get: Bindable
    var postsList: List<Posts> = emptyList()
        set(value) {
            field = value
            notifyPropertyChanged(BR.postsList)
        }

}