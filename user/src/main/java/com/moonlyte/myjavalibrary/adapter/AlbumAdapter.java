package com.moonlyte.myjavalibrary.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.moonlyte.myjavalibrary.PhotosActivity;
import com.moonlyte.myjavalibrary.R;
import com.moonlyte.myjavalibrary.interfaces.ListAction;
import com.moonlyte.myjavalibrary.model.Albums;

import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.MyViewHolder>{
    private List<Albums> mAlbumnsList;
    private ListAction listAction;
    int[] rainbow;

    public AlbumAdapter(List<Albums> mAlbumnsLists,ListAction listActions,int[] rainbow) {
        this.mAlbumnsList = mAlbumnsLists;
        this.listAction=listActions;
        this.rainbow=rainbow;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_albums_title, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Albums albumsModel = mAlbumnsList.get(position);
        holder.bind(albumsModel);
    }

    @Override
    public int getItemCount() {
        return mAlbumnsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_albumTitle;
        public LinearLayout itemViewLL,viewDetailLL;


        public MyViewHolder(View v) {
            super(v);
            tv_albumTitle = (TextView) v.findViewById(R.id.tv_albumTitle);
            itemViewLL=(LinearLayout)v.findViewById(R.id.itemViewLL);
            viewDetailLL=(LinearLayout)v.findViewById(R.id.viewDetailLL);
        }

        public void bind(final Albums albumsModel) {
            int color = rainbow[albumsModel.title.length() % rainbow.length];
            viewDetailLL.setBackgroundColor(color);
            tv_albumTitle.setText(albumsModel.title);
            /*In the Albums section, each album is shown as a tile.
             The user can select an album and this will navigate him to the photos in the album
             together with title using a Card components.
             */
            itemViewLL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listAction.onItemClick(albumsModel.id);
                    Intent intent = new Intent(view.getContext(), PhotosActivity.class);
                    intent.putExtra("albumId", albumsModel.id);
                    view.getContext().startActivity(intent);
                }
            });
        }
    }
}
