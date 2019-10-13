package com.moonlyte.socialapp.features.account.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.moonlyte.socialapp.BR
import com.moonlyte.socialapp.R
import com.moonlyte.socialapp.features.account.model.Users
import com.moonlyte.socialapp.common.BindableAdapter
import com.moonlyte.socialapp.databinding.ItemUsersListCardViewBinding

internal class UsersAdapter(private val callback: ((user: Users, int: Int) -> Unit)? = null) :
    RecyclerView.Adapter<UsersAdapter.ViewHolder>(),
    BindableAdapter<Users> {

    companion object {
        const val POST = 1
        const val TODO = 2
    }
    private var userList: List<Users> = emptyList()

    override fun setDataList(userList: List<Users>?) {
        userList?.let {
            this.userList = it
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemUsersListCardViewBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_users_list_card_view,
                parent,
                false
            )

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = userList.size

    override fun getItemViewType(position: Int): Int = R.layout.item_users_list_card_view

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(userList[position])
    }

    inner class ViewHolder(private val viewDataBinding: ItemUsersListCardViewBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {

        fun bind(users: Users) {
            this.viewDataBinding.setVariable(BR.users, users)
            this.viewDataBinding.postsLL.setOnClickListener {
                this@UsersAdapter.callback?.invoke(users, POST)
            }
            this.viewDataBinding.todosLL.setOnClickListener {
                this@UsersAdapter.callback?.invoke(users, TODO)
            }

        }
    }
}