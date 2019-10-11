package com.moonlyte.socialapp.features.post.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.moonlyte.socialapp.BR
import com.moonlyte.socialapp.R
import com.moonlyte.socialapp.common.BindableAdapter
import com.moonlyte.socialapp.features.post.model.Posts

class PostAdapter : RecyclerView.Adapter<PostAdapter.ViewHolder>(),
    BindableAdapter<Posts> {

    private var postsList: List<Posts> = emptyList()

    override fun setDataList(postsList: List<Posts>?) {
        postsList?.let {
            this.postsList = it
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewDataBinding: ViewDataBinding = DataBindingUtil.inflate(inflater, viewType, parent, false)

        return ViewHolder(viewDataBinding)
    }

    override fun getItemCount(): Int = postsList.size

    override fun getItemViewType(position: Int): Int = R.layout.item_post_groups

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(postsList[position])
    }

    class ViewHolder(private val viewDataBinding: ViewDataBinding) : RecyclerView.ViewHolder(viewDataBinding.root) {

        fun bind(posts: Posts) {
            this.viewDataBinding.setVariable(BR.posts,posts)
        }
    }
}