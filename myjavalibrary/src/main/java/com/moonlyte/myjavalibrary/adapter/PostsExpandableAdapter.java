package com.moonlyte.myjavalibrary.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.moonlyte.myjavalibrary.R;
import com.moonlyte.myjavalibrary.model.Posts;

import java.util.List;

public class PostsExpandableAdapter extends BaseExpandableListAdapter {

    private Context context;
    List<Posts> postsCall;

    public PostsExpandableAdapter(Context context, List<Posts> postsCalls) {
        this.context = context;
        this.postsCall = postsCalls;
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return postsCall.get(listPosition).commentsList.get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }


    @Override
    public View getChildView(int listPosition, int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_posts_child, null);
        }
        TextView commentsTV = (TextView) convertView
                .findViewById(R.id.commentsTV);
        commentsTV.setText(postsCall.get(listPosition).commentsList.get(expandedListPosition).body);
        return convertView;
    }
    @Override
    public int getChildrenCount(int listPosition) {
        return this.postsCall.get(listPosition).commentsList.size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.postsCall.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.postsCall.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_post_group, null);
        }
        TextView listTitleTextView =  convertView.findViewById(R.id.titleTv);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(postsCall.get(listPosition).title);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }
}
