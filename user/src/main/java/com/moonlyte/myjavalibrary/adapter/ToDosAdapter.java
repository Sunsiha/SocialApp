package com.moonlyte.myjavalibrary.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.moonlyte.myjavalibrary.R;
import com.moonlyte.myjavalibrary.model.Todos;

import java.util.List;


public class ToDosAdapter extends RecyclerView.Adapter<ToDosAdapter.MyViewHolder> {
    private List<Todos> todosList;

    public ToDosAdapter(List<Todos> toDosList) {
        this.todosList = toDosList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_todos, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Todos todosModel = todosList.get(position);
        holder.bind(todosModel);
    }

    @Override
    public int getItemCount() {
        return todosList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView titleTV;
        public CardView card_view;


        public MyViewHolder(View v) {
            super(v);
            titleTV = (TextView) v.findViewById(R.id.titleTV);
            card_view = (CardView) v.findViewById(R.id.card_view);
        }

        public void bind(final Todos todosModel) {
            titleTV.setText(todosModel.title);
            /*In the To-dos section, the to-do items should be shown as a list with the completed
             item shown as Green and the uncompleted item as grey
             */
            if (todosModel.completed.booleanValue()) {
                /*green color*/
                card_view.setCardBackgroundColor(Color.parseColor("#008000"));
            } else {
                /*grey color*/
                card_view.setCardBackgroundColor(Color.parseColor("#808080"));
            }

        }
    }
}
