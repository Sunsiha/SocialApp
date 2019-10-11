package com.moonlyte.myjavalibrary.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.moonlyte.myjavalibrary.AlbumsActivity;
import com.moonlyte.myjavalibrary.PostsActivity;
import com.moonlyte.myjavalibrary.R;
import com.moonlyte.myjavalibrary.ToDosActivity;
import com.moonlyte.myjavalibrary.interfaces.ListAction;
import com.moonlyte.myjavalibrary.model.Users;

import java.util.List;


public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.MyViewHolder> {
    private List<Users> mUsersList;
    private ListAction listAction;

    public UsersAdapter(List<Users> usersList, ListAction listActions) {
        this.mUsersList = usersList;
        this.listAction = listActions;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_users_list_card_view, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Users usersModel = mUsersList.get(position);
        holder.bind(usersModel);
    }

    @Override
    public int getItemCount() {
        return mUsersList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_userName, tv_emailAddress, tv_Address;
        LinearLayout postsLL,albumLL,todosLL;


        public MyViewHolder(View v) {
            super(v);
            tv_userName = (TextView) v.findViewById(R.id.tv_userName);
            tv_emailAddress = (TextView) v.findViewById(R.id.tv_emailAddress);
            tv_Address = (TextView) v.findViewById(R.id.tv_Address);
            postsLL = (LinearLayout) v.findViewById(R.id.postsLL);
            albumLL = (LinearLayout) v.findViewById(R.id.albumLL);
            todosLL = (LinearLayout) v.findViewById(R.id.todosLL);
        }

        public void bind(final Users usersModel) {
            tv_userName.setText(usersModel.name);
            tv_emailAddress.setText(usersModel.email);
            String address = "Address: "+usersModel.address.street + "\n" + usersModel.address.suite + "\n"
                    + usersModel.address.city + "\n" + usersModel.address.zipcode;
            tv_Address.setText(address);
            postsLL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    redirectingActivities(usersModel, view, "Posts");
                }
            });
            albumLL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    redirectingActivities(usersModel, view, "Albums");
                }
            });
            todosLL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    redirectingActivities(usersModel, view, "ToDos");
                }
            });
        }

        public void redirectingActivities(Users usersModel, View view, String intentDirection) {
            /*There will be options in the user list to go to the posts, albums or to-dos of the specific user
             */
            listAction.onItemClick(usersModel.id);
            Intent intent = null;
            if (intentDirection.equalsIgnoreCase("Posts")) {
                intent = new Intent(view.getContext(), PostsActivity.class);
            } else if (intentDirection.equalsIgnoreCase("Albums")) {
                intent = new Intent(view.getContext(), AlbumsActivity.class);
            } else if (intentDirection.equalsIgnoreCase("ToDos")) {
                intent = new Intent(view.getContext(), ToDosActivity.class);
            }
            intent.putExtra("UserId", usersModel.id);
            view.getContext().startActivity(intent);
        }
    }
}



