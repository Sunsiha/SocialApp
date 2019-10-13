package com.moonlyte.myjavalibrary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.moonlyte.myjavalibrary.R;
import com.moonlyte.myjavalibrary.interfaces.ListAction;
import com.moonlyte.myjavalibrary.model.Photos;

import java.util.List;


public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.MyViewHolder> {

    private Context mContext;
    private List<Photos> photosList;
    private ListAction listAction;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
        }
    }


    public PhotosAdapter(Context mContext, List<Photos> photosLists, ListAction listAction) {
        this.mContext = mContext;
        this.photosList = photosLists;
        this.listAction = listAction;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_album_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Photos album = photosList.get(position);
        holder.title.setText(album.title);
        // loading album cover using Glide library
        Glide.with(mContext).load(album.thumbnailUrl).into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return photosList.size();
    }
}
